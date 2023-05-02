package com.eden.gallery;

import com.eden.gallery.repository.mongo.ConfigRepository;
import com.eden.gallery.repository.sql.ModelRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableMongoRepositories(basePackageClasses = ConfigRepository.class)
@EnableJpaRepositories(basePackageClasses = ModelRepository.class)
public class ModelGalleryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModelGalleryApplication.class, args);
    }
}
