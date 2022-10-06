package me.black_lottus.heads.storage;

import me.black_lottus.heads.Heads;
import me.black_lottus.heads.file.Files;
import me.black_lottus.heads.storage.file.FileLoader;
import me.black_lottus.heads.storage.mysql.MysqLoader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;


public class StorageLoader {

    private static final Map<String, StorageManager> loaderMap = new HashMap<>();
    private static String loaderName;
    private static final Heads plugin = Heads.getInstance();
    private static final Files lang = plugin.getLang();

    public static void registerLoaders() {

        String storageMode = plugin.getConfig().getString("storage-mode");
        if(storageMode != null){
            // Mysql loader
            if (storageMode.equalsIgnoreCase("mysql")) {
                try{
                    registerLoader("mysql", new MysqLoader(plugin));
                    loaderName = "mysql";
                    Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "[Heads] " + ChatColor.GRAY + ">> " + ChatColor.GRAY +
                            lang.get("selected_storage").replace("%mode%",loaderName));
                    return;
                }catch (Exception e){
                    Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "[Heads] " + ChatColor.GRAY + ">> " + ChatColor.RED +
                            lang.get("mysql_error"));
                }
            }
        }else
            Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "[Heads] " + ChatColor.GRAY + ">> " + ChatColor.RED +
                    lang.get("none_storage"));


        // File loader
        registerLoader("file", new FileLoader());
        loaderName = "file";
        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "[Heads] " + ChatColor.GRAY + ">> " + ChatColor.GRAY +
                lang.get("selected_storage").replace("%mode%",loaderName));
    }

    public static StorageManager getAvailableLoader() {
        return loaderMap.get(loaderName);
    }


    public static String getLoader() {
        return loaderName;
    }

    public static void registerLoader(String dataSource, StorageManager loader) {
        if (loaderMap.containsKey(dataSource)) {
            throw new IllegalArgumentException("Data source " + dataSource + " already has a declared loader!");
        }

        loaderMap.put(dataSource, loader);
    }

}