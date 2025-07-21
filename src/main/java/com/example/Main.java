package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.boot.CommandLineRunner;

@Controller
@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/")
    String home() {
        return "index";
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    // 起動時に自動実行されるメソッド
    @Override
    public void run(String... strings) throws Exception {
    
        //jdbcTemplate.execute("CREATE TABLE customers(" +
        //        "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");
    
        //jdbcTemplate.update("INSERT INTO customers(first_name, last_name) VALUES ('John','Woo')");
    }

}
