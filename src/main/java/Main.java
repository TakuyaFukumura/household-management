import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

@RestController
@EnableAutoConfiguration
public class Main {

    @Value("${hello.world}") 
    private String helloWorld;

    @RequestMapping("/")
    String home() {
        return helloWorld;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
