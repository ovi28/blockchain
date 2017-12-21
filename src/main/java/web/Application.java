package web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        System.getProperties().put( "server.port", 8080 );
        //System.getProperties().put( "server.port", 8081 );
        //System.getProperties().put( "server.port", 8082 );
        //System.getProperties().put( "server.port", 8083 );
        SpringApplication.run(Application.class, args);

    }
}