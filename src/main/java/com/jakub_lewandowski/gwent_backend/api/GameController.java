package com.jakub_lewandowski.gwent_backend.api;

import com.jakub_lewandowski.gwent_backend.model.Player;
import com.jakub_lewandowski.gwent_backend.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private final PlayerService playerService;

    public GameController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @GetMapping("/{id}")
    public Optional<Player> getPlayerById(@PathVariable("id") long id) {
        return playerService.startGame(id);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> createPlayer(@RequestBody Player player) {
        return playerService.createPlayer(player);
    }
}
