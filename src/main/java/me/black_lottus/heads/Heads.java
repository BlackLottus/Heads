package me.black_lottus.heads;

import me.black_lottus.heads.data.Permissions;
import me.black_lottus.heads.file.Files;
import me.black_lottus.heads.storage.StorageLoader;
import me.black_lottus.heads.storage.StorageManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Heads extends JavaPlugin {

    private static Heads instance;
    private Files lang;
    public StorageManager storage;

    public void onEnable() {
        setInstance(this);

        saveDefaultConfig();
        lang = new Files(this, "lang", false, true);

        Permissions.initialize();

        StorageLoader.registerLoaders();
        storage = StorageLoader.getAvailableLoader();

        // Bukkit.getPluginManager().registerEvents(new PlayerListener(), instance);

    }

    public static Heads getInstance() {
        return instance;
    }

    private static void setInstance(Heads instance) {
        Heads.instance = instance;
    }

    public Files getLang() { return lang; }
}
