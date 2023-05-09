package com.eden.gallery.repository.sql;

import com.eden.gallery.model.Nickname;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for nickname entity.
 */
@Repository
public interface NicknameRepository extends JpaRepository<Nickname, Long> {

    /**
     * Check if nickname exist by nick and model
     *
     * @param name    nickname string
     * @param url     nickname url
     * @param modelId model id
     * @return true if exists, else false
     */
    boolean existsByNickAndUrlAndModelId(String name, String url, Long modelId);
}
