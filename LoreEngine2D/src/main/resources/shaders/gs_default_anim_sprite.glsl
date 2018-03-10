#version 330 core

layout(points) in;
layout(triangle_strip, max_vertices = 4) out;

uniform mat4 view;
uniform mat4 perspective;
uniform mat4 model;
uniform float frame;
uniform float maxFrames;

in vec4 vPosition[];
in vec2 vTexCoord[];

out vec2 gTexCoord;

void main()
{

	float start = frame / maxFrames;
	float end	= start + (1.0 / maxFrames);

	gl_Position = perspective * view * model * (vPosition[0] + vec4(2, 2, 0, 1));
	gTexCoord = vec2(end, 0);
	EmitVertex();
	
	gl_Position = perspective * view * model * (vPosition[0] + vec4(-2, 2, 0, 1));
	gTexCoord = vec2(start, 0);
	EmitVertex();
	
	gl_Position = perspective * view * model * (vPosition[0] + vec4(2, -2, 0, 1));
	gTexCoord = vec2(end, 1);
	EmitVertex();
	
	gl_Position = perspective * view * model * (vPosition[0] + vec4(-2, -2, 0, 1));
	gTexCoord = vec2(start, 1);
	EmitVertex();
	
	EndPrimitive();
}
