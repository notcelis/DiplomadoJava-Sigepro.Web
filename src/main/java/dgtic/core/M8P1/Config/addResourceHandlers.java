package dgtic.core.M8P1.Config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

public class addResourceHandlers {

    //@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}
