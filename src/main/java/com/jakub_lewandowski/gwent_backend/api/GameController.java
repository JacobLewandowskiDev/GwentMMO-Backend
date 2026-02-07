package com.jakub_lewandowski.gwent_backend.api;

import com.jakub_lewandowski.gwent_backend.model.Player;
import com.jakub_lewandowski.gwent_backend.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        if (response.getStatusCode() == HttpStatus.CREATED && response.getBody() != null) {
            Object body = response.getBody();
            Player createdPlayer = null;

            if (body instanceof Optional<?> optionalBody && optionalBody.isPresent() && optionalBody.get() instanceof Player p) {
                createdPlayer = p;
            } else if (body instanceof Player p) {
                createdPlayer = p;
            }

            if (createdPlayer != null) {
                Map<String, Object> payload = new HashMap<>();
                payload.put("playerId", createdPlayer.getId());
                payload.put("username", createdPlayer.getUsername());
                payload.put("sprite", createdPlayer.getSprite());
                payload.put("positionX", createdPlayer.getPositionX());
                payload.put("positionY", createdPlayer.getPositionY());

                messagingTemplate.convertAndSend("/topic/player-updates", payload);
            }
        }
        return response;
    }
}
