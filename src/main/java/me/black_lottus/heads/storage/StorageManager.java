package me.black_lottus.heads.storage;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public interface StorageManager {

    /**
     * Player methods!
     */

    HashMap<UUID, Integer> listPlayers();

    void savePlayer(UUID uuid);

    void deletePlayer(UUID uuid);

    int getPlayerHeads(UUID uuid);

}
