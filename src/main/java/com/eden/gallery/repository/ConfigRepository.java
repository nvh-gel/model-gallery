package com.eden.gallery.repository;

import com.eden.gallery.model.Config;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for system config stored in mongodb.
 */
@Repository
public interface ConfigRepository extends MongoRepository<Config, String> {
}
