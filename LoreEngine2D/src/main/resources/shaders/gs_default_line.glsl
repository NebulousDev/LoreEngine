#version 330

layout(points) in;
layout(line_strip, max_vertices = 2) out;

in vec4 positions[];

uniform mat4 view;
uniform mat4 perspective;
uniform mat4 model;

uniform float startX;
uniform float startY;
uniform float endX;
uniform float endY;

void main()
{
	gl_Position = perspective * view * model * vec4(startX, startY, 0, 1);
	EmitVertex();
	
	gl_Position = perspective * view * model * vec4(endX, endY, 0, 1);
	EmitVertex();
	
	EndPrimitive();
}