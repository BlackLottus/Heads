package me.black_lottus.heads.storage;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public interface StorageManager {

    HashMap<Location, Integer> listLocations();

    Integer getHeads(UUID uuid);

    void addLocation(Location loc);

    void addHead(UUID uuid, Integer id);

    void addPlayer(UUID uuid, String nickname);

    void removeHead(Integer id, UUID uuid);

    void removePlayer(UUID uuid);

    void removeLocation(Integer id);
}
