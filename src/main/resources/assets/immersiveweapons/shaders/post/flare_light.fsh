#version 330
#extension GL_ARB_separate_shader_objects : require

// Screen-space light for flares. The world position of every pixel is rebuilt from the depth buffer, and each flare
// adds a smooth falloff around its exact position. The result is blended additively on top of the finished frame.
//
// Occlusion comes from a small grid around each flare, built on the CPU by spreading out through the blocks the light
// can pass through. Each cell holds the length of the shortest path from the flare. A pixel in plain view has a path
// about as long as the straight distance, but one behind a wall has a long detour (or none), which dims it.

#define MAX_LIGHTS 8

// Must match FlareLightGrid
#define GRID_SIZE 32
#define GRID_TILES_X 8
#define COST_SCALE 12.0
#define UNREACHABLE 255.0

// The light's reach, in blocks
#define RADIUS 16.0

#ifdef FLAT
// Added as-is, so this is roughly the brightness a surface of no color of its own gets at the flare
#define LIGHT_GAIN 0.3
#else
// Multiplied with what is already there. 1.0 doubles a surface's brightness at the flare. Too high and every channel
// clips to white, losing the tint.
#define LIGHT_GAIN 3.0
#endif

uniform sampler2D DepthSampler;
uniform sampler2D LightGrid;

layout(std140) uniform FlareLights {
    mat4 InvProjMat;
    mat4 InvViewMat;
    // xyz: exact position relative to the camera
    vec4 FlarePos[MAX_LIGHTS];
    // xyz: minimum corner of the grid relative to the camera, w: row of the grid's slot in the image
    vec4 GridOrigin[MAX_LIGHTS];
    // rgb: color pre-multiplied by intensity
    vec4 LightColor[MAX_LIGHTS];
    int LightCount;
};

layout(location = 0) in vec2 texCoord;

layout(location = 0) out vec4 fragColor;

// Path length from the flare to a point, blended between the surrounding cells. Unreachable cells are left out of the
// blend so a wall's edge doesn't smear into the lit air beside it. Returns -1 when nothing around is reachable.
float gridCost(vec3 gridPos, float slotRow) {
    vec3 shifted = gridPos - 0.5;
    vec3 base = floor(shifted);
    vec3 frac = shifted - base;
    ivec3 cell = ivec3(base);

    float total = 0.0;
    float weights = 0.0;
    for (int c = 0; c < 8; c++) {
        ivec3 offset = ivec3(c & 1, (c >> 1) & 1, (c >> 2) & 1);
        ivec3 index = cell + offset;
        if (any(lessThan(index, ivec3(0))) || any(greaterThanEqual(index, ivec3(GRID_SIZE)))) {
            continue;
        }

        vec3 axisWeights = mix(1.0 - frac, frac, vec3(offset));
        float weight = axisWeights.x * axisWeights.y * axisWeights.z;

        ivec2 texel = ivec2(
            (index.z % GRID_TILES_X) * GRID_SIZE + index.x,
            int(slotRow) + (index.z / GRID_TILES_X) * GRID_SIZE + index.y
        );

        float stored = floor(texelFetch(LightGrid, texel, 0).r * 255.0 + 0.5);
        if (stored >= UNREACHABLE) {
            continue;
        }

        total += stored / COST_SCALE * weight;
        weights += weight;
    }

    return weights > 0.001 ? total / weights : -1.0;
}

void main() {
    // Depth is reversed: 0 is the far plane, so nothing was drawn here (sky).
    float depth = texture(DepthSampler, texCoord).r;
    if (depth <= 0.0) {
        discard;
    }

    vec4 viewPos = InvProjMat * vec4(texCoord * 2.0 - 1.0, depth, 1.0);
    viewPos /= viewPos.w;
    vec3 pos = (InvViewMat * vec4(viewPos.xyz, 1.0)).xyz;

    // The camera sits at the origin, so the normal is flipped to face it.
    vec3 normal = normalize(cross(dFdx(pos), dFdy(pos)));
    if (dot(normal, -pos) < 0.0) {
        normal = -normal;
    }

    // Surfaces sit on the edge between a solid cell and an air cell, and only the air cell has a path length. Looking
    // half a block out from the surface lands in it.
    vec3 samplePos = pos + normal * 0.5;

    vec3 light = vec3(0.0);
    for (int i = 0; i < LightCount; i++) {
        vec3 toLight = FlarePos[i].xyz - pos;
        float dist = length(toLight);
        if (dist >= RADIUS) {
            continue;
        }

        float cost = gridCost(samplePos - GridOrigin[i].xyz, GridOrigin[i].w);
        if (cost < 0.0) {
            continue;
        }

        // The grid's path starts at the center of the flare's block, so the part of it that isn't just the straight
        // line there is the detour around whatever is in the way. That is added on top of the exact distance, which
        // keeps the light moving smoothly with the flare instead of stepping from block to block. The small allowance
        // covers the search not being perfectly straight.
        vec3 cellCenter = GridOrigin[i].xyz + float(GRID_SIZE / 2) + 0.5;
        float detour = max(cost - distance(samplePos, cellCenter) * 1.07 - 0.4, 0.0);

        float falloff = clamp(1.0 - (dist + detour) / RADIUS, 0.0, 1.0);
        falloff *= falloff;

        // Surfaces facing the flare are brightest, ones turned away still catch some light bounced around them
        float facing = dot(normal, toLight / max(dist, 0.001));
        float shading = mix(0.35, 1.0, smoothstep(-0.2, 0.4, facing));

        light += LightColor[i].rgb * falloff * shading;
    }

    // The pipeline multiplies this by the color already on screen, so it is a gain on each surface's own color
    // rather than a flat color added on top.
    fragColor = vec4(light * LIGHT_GAIN, 0.0);
}