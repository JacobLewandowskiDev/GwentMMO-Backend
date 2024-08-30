package com.jakub_lewandowski.gwent_backend.service;

import com.jakub_lewandowski.gwent_backend.model.Player;
import com.jakub_lewandowski.gwent_backend.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

//@Autowired
//    private final PlayerRepository playerRepository;
//
//    public PlayerService(PlayerRepository playerRepository) {
//        this.playerRepository = playerRepository;
//    }

    public long createPlayer(Player player) {
        System.out.println("createPlayer() called: " + player.getUsername() + ", profileImg: " + player.getSprite() + ", x: " + player.getPositionX() + ", y:" + player.getPositionY());
        return 1L;
    }
}
