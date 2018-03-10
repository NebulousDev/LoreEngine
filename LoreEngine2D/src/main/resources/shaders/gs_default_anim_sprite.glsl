#version 330 core

layout(points) in;
layout(triangle_strip, max_vertices = 4) out;

uniform mat4 view;
uniform mat4 perspective;
uniform mat4 model;
uniform int offset;
uniform int frame;
uniform int maxFrames;

in vec4 vPosition[];
in vec2 vTexCoord[];

out vec2 gTexCoord;

void main()
{

	gl_Position = perspective * view * model * (vPosition[0] + vec4(1, 1, 0, 1));
	gTexCoord = vec2(1, 1);
	EmitVertex();
	
	gl_Position = perspective * view * model * (vPosition[0] + vec4(-1, 1, 0, 1));
	gTexCoord = vec2(0, 1);
	EmitVertex();
	
	gl_Position = perspective * view * model * (vPosition[0] + vec4(1, -1, 0, 1));
	gTexCoord = vec2(1, 0);
	EmitVertex();
	
	gl_Position = perspective * view * model * (vPosition[0] + vec4(-1, -1, 0, 1));
	gTexCoord = vec2(0, 0);
	EmitVertex();
	
	EndPrimitive();
}
