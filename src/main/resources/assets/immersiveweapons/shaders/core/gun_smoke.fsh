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
layout(location = 5) flat in float puffSeed;

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

float fbm(vec2 p) {
    float sum = 0.0;
    float amplitude = 0.55;
    for (int i = 0; i < 3; i++) {
        sum += valueNoise(p) * amplitude;
        p = p * 2.0 + vec2(5.2, 1.3);
        amplitude *= 0.45;
    }
    return sum;
}

void main() {
    // The particle fades by lowering its vertex alpha, which is used here as the "age" of the puff
    float life = clamp(vertexColor.a, 0.0, 1.0);
    float age = 1.0 - life;

    vec2 p = localUv * 2.0 - 1.0;
    float radius = length(p);

    float seed = fract(puffSeed * 0.618) * 20.0;

    // Two layers of warped, low-frequency noise: one distorts the outline, the other is turned into thin ridges
    // (tendrils) that curl through and out of the body.
    vec2 q = vec2(p.x * 1.3, p.y * 0.7) + seed;
    vec2 warp = vec2(fbm(q * 0.8 + vec2(0.0, age * 0.8)), fbm(q * 0.8 + vec2(4.7, 1.9 - age * 0.6)));
    float n = fbm(q * 0.8 + (warp - 0.5) * 2.4);

    // Ragged, noise-distorted core instead of a round disc
    float body = 1.0 - smoothstep(0.2, 0.75, radius + (n - 0.5) * 1.1);

    // Thin curling streaks reaching toward the edge
    float ridge = 1.0 - abs(2.0 * fbm(q * 1.3 + (warp - 0.5) * 3.0 + 3.0) - 1.0);
    float tendrils = pow(ridge, 4.0) * (1.0 - smoothstep(0.45, 1.0, radius));

    float density = max(body * 0.65, tendrils * 0.9);

    float alpha = density * smoothstep(0.0, 0.45, life) * 0.8;

    // Darker in the thick parts, lighter on the wisps
    float shade = mix(0.9, 0.5, smoothstep(0.35, 0.8, n));
    vec4 color = vec4(vertexColor.rgb * shade, alpha);

    if (color.a < 0.01) {
        discard;
    }
    #ifdef OIT_ALPHA_ONLY
    executeAlphaOnlyPhase(gl_FragCoord.z, color.a);
    #else
    fragColor = calculateFinalColor(color);
    #endif
}
