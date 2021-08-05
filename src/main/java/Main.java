import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.boot.CommandLineRunner;

@RestController
@EnableAutoConfiguration
public class Main implements CommandLineRunner {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Value("${hello.world}") 
    private String helloWorld;

    @RequestMapping("/")
    String home() {
        return helloWorld;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
    
        //jdbcTemplate.execute("CREATE TABLE customers(" +
        //        "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");
    
        //jdbcTemplate.update("INSERT INTO customers(first_name, last_name) VALUES ('John','Woo')");
    }

}
