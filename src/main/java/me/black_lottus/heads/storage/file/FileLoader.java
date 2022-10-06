package me.black_lottus.heads.storage.file;

import me.black_lottus.heads.Heads;
import me.black_lottus.heads.file.Files;
import me.black_lottus.heads.storage.StorageManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileLoader implements StorageManager {

    private static final SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private final Files fileConfig;
    public FileLoader() {
        fileConfig = new Files(Heads.getInstance(), "clans", false, false);
    }


    @Override
    public HashMap<UUID, Integer> listPlayers() {
        return null;
    }

    @Override
    public void savePlayer(UUID uuid) {

    }

    @Override
    public void deletePlayer(UUID uuid) {

    }

    @Override
    public int getPlayerHeads(UUID uuid) {
        return 0;
    }
}
