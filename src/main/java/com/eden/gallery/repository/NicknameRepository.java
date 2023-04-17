package com.eden.gallery.repository;

import com.eden.gallery.model.Nickname;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for nickname entity.
 */
@Repository
public interface NicknameRepository extends JpaRepository<Nickname, Long> {
}
