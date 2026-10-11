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
layout(location = 6) out float visibility;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    sphericalVertexDistance = fog_spherical_distance(Position);
    cylindricalVertexDistance = fog_cylindrical_distance(Position);
    texCoord0 = UV0;
    vertexColor = Color * sample_lightmap(Sampler2, UV2);

    // A puff is drawn as a grid of tile quads, and the UVs (which are never used for sampling) carry what each tile
    // needs. The four UV values are shared between corners (corner 0 gets U1 and V1, corner 1 gets U1 and V0, corner 2
    // gets U0 and V0, corner 3 gets U0 and V1), and each one is the smoke visibility at one corner. They are tagged
    // with that corner (0 for U1, 1 for V0, 2 for U0, 3 for V1) so a vertex can tell which of its two values is its
    // own, and which corner it is, from the data alone.
    //
    // gl_VertexIndex can't be used for that, because the vertex buffer is shared with other draws and the base vertex
    // of this one is not necessarily a multiple of four.
    //
    // Each value is (tile code * 4 + corner tag) plus the visibility as the fractional part, where the tile code is
    // tile count * 64 + row * 8 + column.
    float valueA = UV0.x;
    float valueB = UV0.y;
    int baseA = int(floor(valueA) + 0.5);
    int baseB = int(floor(valueB) + 0.5);
    int tagA = baseA & 3;
    int tagB = baseB & 3;

    // Going around the corners, the two tags a vertex sees are its own and the previous corner's
    bool ownsA = ((tagA + 3) & 3) == tagB;
    int corner = ownsA ? tagA : tagB;
    float packedValue = ownsA ? valueA : valueB;
    int code = (ownsA ? baseA : baseB) >> 2;
    visibility = clamp((packedValue - floor(packedValue)) / 0.99, 0.0, 1.0);

    vec2 cornerUv = vec2((corner == 0 || corner == 1) ? 1.0 : 0.0, (corner == 0 || corner == 3) ? 1.0 : 0.0);
    float tileCount = float(code >> 6);
    vec2 tile = vec2(float(code & 7), float((code >> 3) & 7));
    localUv = (tile + cornerUv) / max(tileCount, 1.0);

    // The particle stores a per-puff seed in the low 3 bits of each color channel (0-511), since all puffs share one
    // sprite and have no other per-particle data. This reads it back from the unlit color.
    vec3 channels = floor(Color.rgb * 255.0 + 0.5);
    vec3 bits = mod(channels, 8.0);
    seed = bits.r + bits.g * 8.0 + bits.b * 64.0;
}
