#version 330 core

layout(points) in;
layout(triangle_strip, max_vertices = 4) out;

in vec4 vPosition[];
in vec4 vDimensions[];
in vec3 vOffsets[];

out vec2 gTexCoord;

void main()
{

	gl_Position = vPosition[0] + vec4(0, 0, 0, 1);
	gTexCoord = vec2(vDimensions[0].x, vDimensions[0].y);
	EmitVertex();
	
	gl_Position = vPosition[0] + vec4(0, 1, 0, 1);
	gTexCoord = vec2(vDimensions[0].x + vDimensions[0].z, vDimensions[0].y);
	EmitVertex();
	
	gl_Position = vPosition[0] + vec4(1, 1, 0, 1);
	gTexCoord = vec2(vDimensions[0].x, vDimensions[0].y + vDimensions[0].w);
	EmitVertex();
	
	gl_Position = vPosition[0] + vec4(1, 0, 0, 1);
	gTexCoord = vec2(vDimensions[0].x + vDimensions[0].z, vDimensions[0].y + vDimensions[0].w);
	EmitVertex();
	
	EndPrimitive();

}