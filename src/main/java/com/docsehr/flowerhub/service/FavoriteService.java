package com.docsehr.flowerhub.service;

import com.docsehr.flowerhub.model.mysql.Favorite;
import com.docsehr.flowerhub.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {
    @Autowired private FavoriteRepository favoriteRepository;

    public List<Favorite> getFavoritesByUser(String userId) {
        return favoriteRepository.findByUserId(userId);
    }

    public Favorite saveFavorite(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }
}

