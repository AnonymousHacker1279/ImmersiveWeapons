#version 330
#extension GL_ARB_separate_shader_objects : require

#include <minecraft:fog.glsl>
#include <minecraft:dynamictransforms.glsl>
#include <minecraft:projection.glsl>
#include <minecraft:sample_lightmap.glsl>

layout(location = 0) in vec3 Position;
layout(location = 1) in vec2 UV0;
layout(location = 2) in vec4 Color;
layout(location = 3) in ivec2 UV2;

uniform sampler2D Sampler2;

layout(location = 0) out float sphericalVertexDistance;
layout(location = 1) out float cylindricalVertexDistance;
layout(location = 2) out vec2 texCoord0;
layout(location = 3) out vec4 vertexColor;
layout(location = 4) out vec2 localUv;
layout(location = 5) flat out float seed;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    sphericalVertexDistance = fog_spherical_distance(Position);
    cylindricalVertexDistance = fog_cylindrical_distance(Position);
    texCoord0 = UV0;
    vertexColor = Color * sample_lightmap(Sampler2, UV2);

    // Quads are drawn with four consecutive vertices going around the corners, so the corner index gives position
    // within the quad without needing any extra vertex data.
    int corner = gl_VertexIndex & 3;
    localUv = vec2((corner == 0 || corner == 1) ? 1.0 : 0.0, (corner == 0 || corner == 3) ? 1.0 : 0.0);

    // The particle hides a per-puff seed (0-511) in the low 3 bits of each color channel, since all puffs share one
    // sprite. This reads it back from the unlit color.
    vec3 bits = mod(floor(Color.rgb * 255.0 + 0.5), 8.0);
    seed = bits.r + bits.g * 8.0 + bits.b * 64.0;
}
