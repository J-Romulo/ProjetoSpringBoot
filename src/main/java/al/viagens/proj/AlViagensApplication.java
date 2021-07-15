package al.viagens.proj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@SpringBootApplication
@ComponentScan("al.viagens.controller")
@EnableJpaRepositories("al.viagens.repository")
@EntityScan("al.viagens.model")
public class AlViagensApplication {
	
	@Bean
	public InternalResourceViewResolver resolver() {
	    InternalResourceViewResolver vr = new InternalResourceViewResolver();
	    vr.setPrefix("/WEB-INF/views/");
	    vr.setViewClass(JstlView.class);
	    vr.setSuffix(".jsp");
	    return vr;
	}
	 
	public static void main(String[] args) {
		SpringApplication.run(AlViagensApplication.class, args);
	}

}
