package com.docsehr.flowerhub.controller;

import com.docsehr.flowerhub.model.mysql.Favorite;
import com.docsehr.flowerhub.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;
    @PostMapping("/{userId}/{productId}")
    public Favorite addFavorite(@PathVariable String userId, @PathVariable String productId) {
        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setProductId(productId);
        return favoriteService.saveFavorite(favorite);
    }

    @GetMapping("/{userId}")
    public List<Favorite> getFavorites(@PathVariable String userId) {
        return favoriteService.getFavoritesByUser(userId);
    }
}

