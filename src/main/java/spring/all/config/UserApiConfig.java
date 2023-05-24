package spring.all.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class UserApiConfig {
	  
	@Bean
	public OpenAPI springShopOpenAPI() {
	    final String securitySchemeName = "bearerAuth";
	    return new OpenAPI()
	        .info(new Info()
	            .title("APIs")
	            .description("Sample application")
	            .version("v0.0.1")
	            .license(new License().name("Prat11k").url("http://github.com/Prat11k")))
	        .externalDocs(new ExternalDocumentation()
	            .description("Documentation")
	            .url("https://github.com/Prat11k/EmailDemo"))
	        .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
	        .components(new Components()
	            .addSecuritySchemes(securitySchemeName, new SecurityScheme()
	                .name(securitySchemeName)
	                .type(SecurityScheme.Type.HTTP)
	                .scheme("bearer")
	                .bearerFormat("JWT"))
	        	.addRequestBodies("multipartFormData", new RequestBody()
	                        .content(new Content()
	                                .addMediaType("multipart/form-data", new MediaType()
	                                        .schema(new Schema<>()
	                                                .type("string")
	                                                .format("binary"))))));
	}
}
