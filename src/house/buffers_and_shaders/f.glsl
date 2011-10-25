#version 130
//
// sets the fragment color as passed through from the vertex shader
//

in vec3 E;
in vec3 L;
in vec3 N;
in vec2 texCoord;
in vec4 color;
out vec4 fColor;
uniform sampler2D texture;
uniform vec4 GlobalAmbient;
uniform vec4 AmbientProduct;
uniform vec4 DiffuseProduct;
uniform vec4 SpecularProduct;
uniform vec3 LightDirection;
uniform float Shininess;
uniform float CutoffAngle;
uniform float LightIntensity;

void main()
{
	vec3 D;
	vec3 H;
	
	//process the spotlight
	D = normalize(LightDirection);
	H = normalize(L+E); //normalize the sum of Light and Eye vectors
	
	vec4 ambient = vec4(0,0,0,0);
	vec4 diffuse = vec4(0,0,0,1);
	vec4 specular = vec4(0,0,0,1);
	vec4 color = vec4(0,0,0,0);
	
	//spot coefficient
	float Kc = LightIntensity * max(dot(D,-L)-CutoffAngle, 0.0);
	
	//amient component
	ambient = (Kc*AmbientProduct) + ambient + GlobalAmbient;
	
	//diffuse coefficient
	float Kd = max(dot(L,N), 0.0);
	
	//diffuse component
	diffuse = Kc * Kd * DiffuseProduct + diffuse;
	
	//specular coefficient
	float Ks = pow(max(dot(E,H),0.0),Shininess);
	
	//specular component
	if(dot(L,N) >= 0.0)
	{
		specular = Kc*Ks*SpecularProduct + specular;
	}
	
	fColor = (color + ambient + diffuse + specular) * texture2D(texture, texCoord);
	fColor.a = 1.0;
}