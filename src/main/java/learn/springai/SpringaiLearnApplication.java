package learn.springai;

import learn.springai.functions.WeatherConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(WeatherConfigProperties.class)
@SpringBootApplication
public class SpringaiLearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringaiLearnApplication.class, args);
	}

}
