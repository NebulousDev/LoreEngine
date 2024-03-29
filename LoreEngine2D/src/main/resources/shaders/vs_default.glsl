#version 330

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 texCoord;
layout(location = 2) in vec3 normal;

uniform mat4 view;
uniform mat4 perspective;
uniform mat4 model;

out vec2 vTexCoord;
out vec3 vNormal;

void main()
{
	gl_Position = perspective * view * model * vec4(position, 1.0);
	vTexCoord = texCoord;
	vNormal = normal;
}
