package com.jakub_lewandowski.gwent_backend;

import com.jakub_lewandowski.gwent_backend.model.Player;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GwentBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GwentBackendApplication.class, args);

		Player player = new Player(1L, "Jakub", 1, 10, 15);
		System.out.println(player.getUsername() + " wins: " + player.getScoreboard().get("wins") + " losses: " + player.getScoreboard().get("losses"));
		player.getScoreboard().replace("wins", 1);
		System.out.println(player.getUsername() + " wins: " + player.getScoreboard().get("wins") + " losses: " + player.getScoreboard().get("losses"));
	}

}
