package me.black_lottus.luckyheads;

import me.black_lottus.luckyheads.commands.CommandManager;
import me.black_lottus.luckyheads.data.Data;
import me.black_lottus.luckyheads.data.Permissions;
import me.black_lottus.luckyheads.file.Files;
import me.black_lottus.luckyheads.listener.PlayerListener;
import me.black_lottus.luckyheads.storage.StorageLoader;
import me.black_lottus.luckyheads.storage.StorageManager;
import me.black_lottus.luckyheads.utils.Effects;
import me.black_lottus.luckyheads.utils.PlaceHolder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class LuckyHeads extends JavaPlugin {

    private static LuckyHeads instance;
    private Files lang;
    public StorageManager storage;
    public CommandManager cmdManager;
    public static boolean isNewVersion;

    public void onEnable() {
        setInstance(this);

        saveDefaultConfig();
        lang = new Files(this, "lang", false, true);

        StorageLoader.registerLoaders();
        storage = StorageLoader.getAvailableLoader();

        Permissions.initialize();
        Data.initialize();
        new PlaceHolder().register();
        isNewVersion = Arrays.stream(Material.values()).map(Material::name).toList().contains("PLAYER_HEAD");

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), instance);

        cmdManager = new CommandManager();
        cmdManager.setup();

        onReload();
        Effects.runEffects();
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

    public static LuckyHeads getInstance() {
        return instance;
    }

    private static void setInstance(LuckyHeads instance) {
        LuckyHeads.instance = instance;
    }

    public Files getLang() { return lang; }
}
