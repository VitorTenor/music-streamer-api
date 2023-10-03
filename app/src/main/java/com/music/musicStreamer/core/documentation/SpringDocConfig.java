package com.music.musicStreamer.core.documentation;

import com.music.musicStreamer.api.exceptionHandler.Problem;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SpringDocConfig {
    private @Value("${document.api.version}") String version;
    private @Value("${document.api.description}") String description;
    private @Value("${document.api.title}") String title;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(title)
                        .version(version)
                        .description(description)
                ).components(new Components()
                        .schemas(generateSchemas())
                ).addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"));
    }

    public Map<String, Schema> generateSchemas() {
        final Map<String, Schema> schemaMap = new HashMap<>();
        Map<String, Schema> problemSchema = ModelConverters.getInstance().read(Problem.class);
        Map<String, Schema> problemObjectSchema = ModelConverters.getInstance().read(Problem.class);
        schemaMap.putAll(problemSchema);
        schemaMap.putAll(problemObjectSchema);
        return schemaMap;
    }
}
