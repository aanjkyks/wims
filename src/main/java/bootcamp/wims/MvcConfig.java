package bootcamp.wims;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addViewController("/home").setViewName("forward:/home.html");
//		registry.addViewController("/").setViewName("forward:/mainPage.html");
		//		registry.addViewController("/hello").setViewName("hello");
				registry.addViewController("/login").setViewName("login");
	}

}
