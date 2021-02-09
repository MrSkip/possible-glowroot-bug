package com.glowroot.possiblebug.conf;

import com.github.cloudyrock.mongock.SpringBootMongock;
import com.github.cloudyrock.mongock.SpringBootMongockBuilder;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfiguration {
    @Value("${spring.data.mongodb.authentication-database}") private String authenticationDatabase;
    @Value("${spring.data.mongodb.database}") private String dbName;
    @Value("${spring.data.mongodb.username}") private String username;
    @Value("${spring.data.mongodb.password}") private String password;
    @Value("${spring.data.mongodb.host}") private String host;
    @Value("${spring.data.mongodb.port}") private int port;

    @Bean
    @Primary
    public MongoClient buildMongoClient() {
        MongoClientURI mongoClientURI =
                new MongoClientURI(
                        "mongodb://"
                                + username
                                + ":"
                                + password
                                + "@"
                                + host
                                + ":"
                                + port
                                + "/"
                                + authenticationDatabase);
        return new MongoClient(mongoClientURI);
    }

    @Bean(name = "mongoTemplate")
    public MongoTemplate buildMongoTemplate() {
        return new MongoTemplate(buildMongoClient(), dbName);
    }

    @Bean(name = "mongock")
    public SpringBootMongock mongock(ApplicationContext springContext, MongoClient mongoClient) {
        return new SpringBootMongockBuilder(
                mongoClient,
                dbName,
                "com.glowroot.possiblebug")
                .setApplicationContext(springContext)
                .setLockQuickConfig()
                .build();
    }
}
