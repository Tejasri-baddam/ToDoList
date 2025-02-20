package com.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.todolist.data")
public class App 
{
    public static void main( String[] args )
    {
        Dotenv dotenv = Dotenv.load();
        System.out.println("MONGO_USER: " + dotenv.get("MONGO_USER"));
        // "mongodb+srv://baddamtejasri:skillarcade@cluster0.cw3xm.mongodb.net/ToDoList?retryWrites=true&w=majority"
        System.setProperty("MONGO_USER", dotenv.get("MONGO_USER"));
        System.setProperty("MONGO_PASSWORD", dotenv.get("MONGO_PASSWORD"));
        System.setProperty("MONGO_CLUSTER", dotenv.get("MONGO_CLUSTER"));
        System.setProperty("MONGO_DBNAME", dotenv.get("MONGO_DBNAME"));
        SpringApplication.run(App.class,args);
    }
}
