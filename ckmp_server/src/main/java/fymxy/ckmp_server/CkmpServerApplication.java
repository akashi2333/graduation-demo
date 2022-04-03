package fymxy.ckmp_server;


import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;


/**
 * @author tangWu
 */
@EnableOpenApi
@EnableKnife4j
@SpringBootApplication
public class CkmpServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CkmpServerApplication.class, args);
	}

}
