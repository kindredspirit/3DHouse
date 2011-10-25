#version 130
in vec4 color;
in vec3 N;
in vec3 L;

out vec4 fColor;

uniform vec4 GlobalAmbient;
uniform vec4 LightAmbient;
uniform vec4 LightDiffuse;

void main()
{
	vec3 NN = normalize(N);
	vec3 LL = normalize(L);
	float Kd= max(dot(LL,NN),0.0);
	
	vec4 ambient = GlobalAmbient+LightAmbient;
	vec4 diffuse = Kd*LightDiffuse;
	
	fColor=(ambient+diffuse)*color;
}