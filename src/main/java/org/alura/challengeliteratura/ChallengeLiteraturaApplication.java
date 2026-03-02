package org.alura.challengeliteratura;

import org.alura.challengeliteratura.menu.Menu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChallengeLiteraturaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChallengeLiteraturaApplication.class, args);
    }

    @Bean
    CommandLineRunner run(Menu menu) {
        return args -> menu.mostrar();
    }
}
