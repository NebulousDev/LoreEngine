#version 330 core

in vec2 vTexCoord;

out vec4 outColor;

uniform sampler2D texture0;

void main()
{
	vec4 color = texture(texture0, vTexCoord);
	outColor = color * vec4(1.0, 1.0, 1.0, 1.0);
}