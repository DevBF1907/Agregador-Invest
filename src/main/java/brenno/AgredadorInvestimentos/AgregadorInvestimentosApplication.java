package brenno.AgredadorInvestimentos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AgregadorInvestimentosApplication {
	public static void main(String[] args) {
		SpringApplication.run(AgregadorInvestimentosApplication.class, args);
	}
}
