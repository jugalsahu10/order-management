package com.docsehr.flowerhub.repository;

import com.docsehr.flowerhub.model.mysql.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserId(String userId);
}
