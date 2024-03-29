#version 330 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 texCoord;

uniform int offset;
uniform int frame;
uniform int maxFrames;

out vec2 vTexCoord;

void main()
{
	gl_Position = vec4(position, 1.0);
	vTexCoord = (offset * frame) + (texCoord / maxFrames);
}
