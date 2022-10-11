package me.black_lottus.heads;

import me.black_lottus.heads.commands.HeadsManager;
import me.black_lottus.heads.data.Data;
import me.black_lottus.heads.data.Permissions;
import me.black_lottus.heads.file.Files;
import me.black_lottus.heads.listener.PlayerListener;
import me.black_lottus.heads.storage.StorageLoader;
import me.black_lottus.heads.storage.StorageManager;
import me.black_lottus.heads.utils.PlaceHolder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Heads extends JavaPlugin {

    private static Heads instance;
    private Files lang;
    public StorageManager storage;
    public HeadsManager cmdManager;

    public void onEnable() {
        setInstance(this);

        saveDefaultConfig();
        lang = new Files(this, "lang", false, true);

        StorageLoader.registerLoaders();
        storage = StorageLoader.getAvailableLoader();

        Permissions.initialize();
        Data.initialize();
        new PlaceHolder().register();

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), instance);

        cmdManager = new HeadsManager();
        cmdManager.setup();

        onReload();
    }

    public void onDisable(){
        if(!Bukkit.getOnlinePlayers().isEmpty()){
            for(Player player : Bukkit.getOnlinePlayers()){
                Data.removeTotalHeads(player.getUniqueId());
            }
        }
    }

    private void onReload(){
        if(!Bukkit.getOnlinePlayers().isEmpty()){
            for(Player player : Bukkit.getOnlinePlayers()){
                Data.addTotalHeads(player.getUniqueId());
            }
        }
    }

    public static Heads getInstance() {
        return instance;
    }

    private static void setInstance(Heads instance) {
        Heads.instance = instance;
    }

    public Files getLang() { return lang; }
}
