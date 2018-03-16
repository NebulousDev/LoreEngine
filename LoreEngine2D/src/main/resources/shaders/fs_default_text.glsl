#version 330 core

layout(location = 0) out vec4 fColor;

in vec2 gTexCoord;

uniform sampler2D fontMap;

void main()
{
	fColor = texture(fontMap, gTexCoord) * vec4(1.0, 0.0, 0.0, 1.0);
}