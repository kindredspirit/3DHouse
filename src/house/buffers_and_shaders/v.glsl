#version 130

//Author Brittany Alkire

in vec4 		vPosition;					
in vec4			vColor;						
in vec3 		vNormal;					
in vec2			vTexCoord;					
out vec4		color;						
out vec3		E;						
out	vec3 		L;							
out	vec3 		N;							
out vec2		texCoord;					
uniform vec4 	LightPosition;				
uniform mat4 	Projection, Model, View;	

void main()
{
	vec3 pos = (Model * vPosition).xyz;				// set position vertex
	E = (View * vec4(0, 0, 0, 1)).xyz-pos;
	E = normalize(E);								// eye of camera position
	L = normalize(LightPosition.xyz - pos);			// set light vector
	N = normalize(Model * vec4(vNormal, 0.0)).xyz;	// set normal vector

	gl_Position = Projection * View * Model * vPosition;	//view: projection
	
	texCoord = vTexCoord;	//output vector of texture coordinates
	color = vColor;
}
