#version 330 core

layout(location = 0) in float 	position;
layout(location = 1) in vec4 	dimensions;
layout(location = 2) in vec3 	offsets;

out vec4 vDimensions;
out vec3 vOffsets;

void main()
{
	gl_Position = vec4(position, 0.0, 0.0, 1.0);
	vDimensions = dimensions;
	vOffsets	= offsets;
}