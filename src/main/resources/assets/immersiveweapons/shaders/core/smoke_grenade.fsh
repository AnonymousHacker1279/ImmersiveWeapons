#version 330
#extension GL_ARB_separate_shader_objects : require

#include <minecraft:fog.glsl>
#include <minecraft:dynamictransforms.glsl>
#include <minecraft:oit.glsl>

uniform sampler2D Sampler0;

layout(location = 0) in float sphericalVertexDistance;
layout(location = 1) in float cylindricalVertexDistance;
layout(location = 2) in vec2 texCoord0;
layout(location = 3) in vec4 vertexColor;
layout(location = 4) in vec2 localUv;
layout(location = 5) flat in float seed;
layout(location = 6) in float visibility;

#ifndef OIT_ALPHA_ONLY
layout(location = 0) out vec4 fragColor;
#endif

vec4 calculateFinalColor(vec4 color) {
    #ifdef OIT_ACCUMULATE
    color = sampleColorForAccumulation(color);
    vec4 fogColor = vec4(FogColor.rgb * color.a, FogColor.a);
    #else
    vec4 fogColor = FogColor;
    #endif
    return apply_fog(color, sphericalVertexDistance, cylindricalVertexDistance, FogEnvironmentalStart, FogEnvironmentalEnd, FogRenderDistanceStart, FogRenderDistanceEnd, fogColor);
}

float hash(vec2 p) {
    p = fract(p * vec2(123.34, 456.21));
    p += dot(p, p + 45.32);
    return fract(p.x * p.y);
}

// Smooth (quintic) value noise, so there are no hard cell edges
float valueNoise(vec2 p) {
    vec2 i = floor(p);
    vec2 f = fract(p);
    f = f * f * f * (f * (f * 6.0 - 15.0) + 10.0);
    return mix(mix(hash(i), hash(i + vec2(1.0, 0.0)), f.x),
               mix(hash(i + vec2(0.0, 1.0)), hash(i + vec2(1.0, 1.0)), f.x), f.y);
}

float fbm(vec2 p, int octaves) {
    float sum = 0.0;
    float amplitude = 0.55;
    for (int i = 0; i < octaves; i++) {
        sum += valueNoise(p) * amplitude;
        p = p * 2.0 + vec2(5.2, 1.3);
        amplitude *= 0.45;
    }
    return sum;
}

void main() {
    if (visibility <= 0.01) {
        discard;
    }

    vec2 p = localUv * 2.0 - 1.0;
    float radius = length(p);

    // Nothing is drawn outside the inscribed circle, so skip the noise work for the corners
    if (radius > 1.0) {
        discard;
    }

    // Each puff gets its own region of the noise field
    vec2 q = p * 1.5 + vec2(fract(seed * 0.0618) * 40.0, fract(seed * 0.381) * 40.0);

    // Domain-warped noise gives soft, billowing lobes instead of a round disc
    vec2 warp = vec2(fbm(q * 0.9, 2), fbm(q * 0.9 + vec2(5.2, 1.3), 2));
    float n = fbm(q + (warp - 0.5) * 1.8, 2);

    float edge = radius + (n - 0.45) * 0.9;
    float density = 1.0 - smoothstep(0.35, 0.95, edge);

    // Fake lighting: the top of a puff catches more light, and the thick parts are darker than the thin edges
    float top = 0.5 - p.y * 0.5;
    float thick = smoothstep(0.35, 0.7, n);
    float shade = mix(0.6, 1.0, top) * mix(1.0, 0.7, thick);

    // vertexColor already carries the tint, the block/sky light at the puff, and the particle's own fade-out
    vec4 color = vec4(vertexColor.rgb * shade, density * 0.95 * clamp(vertexColor.a, 0.0, 1.0) * visibility);

    if (color.a < 0.01) {
        discard;
    }
    #ifdef OIT_ALPHA_ONLY
    executeAlphaOnlyPhase(gl_FragCoord.z, color.a);
    #else
    fragColor = calculateFinalColor(color);
    #endif
}
