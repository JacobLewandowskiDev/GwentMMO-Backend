package com.jakub_lewandowski.gwent_backend.api;

import com.jakub_lewandowski.gwent_backend.model.Player;
import com.jakub_lewandowski.gwent_backend.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private final PlayerService playerService;
    private final SimpMessageSendingOperations messagingTemplate;

    public GameController(PlayerService playerService, SimpMessageSendingOperations messagingTemplate) {
        this.playerService = playerService;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @GetMapping("/{id}")
    public Optional<Player> getPlayerById(@PathVariable("id") long id) {
        return playerService.getPlayerById(id);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> createPlayer(@RequestBody Player player) {
        ResponseEntity<?> response = playerService.createPlayer(player);

        if (response.getStatusCode().value() == 201 && response.getBody() instanceof Optional<?> optionalBody) {

            if (optionalBody.isPresent() && optionalBody.get() instanceof Player createdPlayer) {
                messagingTemplate.convertAndSend("/topic/player-updates", createdPlayer);
            } else {
                System.out.println("Optional is empty or does not contain a Player");
            }
        }
        return response;
    }
}
