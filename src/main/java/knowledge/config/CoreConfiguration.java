package knowledge.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Import({
        SwaggerConfiguration.class,
        SecurityConfiguration.class
})
public class CoreConfiguration implements WebMvcConfigurer {

}
