package me.black_lottus.heads.storage;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public interface StorageManager {

    HashMap<Location, Integer> listLocations();

    ArrayList<Integer> getPlayerHeads(UUID uuid);

    Integer getTotalHeads(UUID uuid);

    void addLocation(Integer id, Location loc);

    void addHead(UUID uuid, Integer id);

    void addPlayer(UUID uuid, String nickname);

    void removeHead(Integer id, UUID uuid);

    void removePlayer(UUID uuid);

    void removeLocation(Integer id);
}
