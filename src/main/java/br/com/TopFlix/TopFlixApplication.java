package br.com.TopFlix;

import br.com.TopFlix.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TopFlixApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TopFlixApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Principal principal = new Principal();
        principal.exibeMenu();


    }

}
