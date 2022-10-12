package me.black_lottus.luckyheads.storage;

import me.black_lottus.luckyheads.LuckyHeads;
import me.black_lottus.luckyheads.file.Files;
import me.black_lottus.luckyheads.storage.file.FileLoader;
import me.black_lottus.luckyheads.storage.mysql.MysqLoader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;


public class StorageLoader {

    private static final Map<String, StorageManager> loaderMap = new HashMap<>();
    private static String loaderName;
    private static final LuckyHeads plugin = LuckyHeads.getInstance();
    private static final Files lang = plugin.getLang();

    public static void registerLoaders() {

        String storageMode = plugin.getConfig().getString("storage-mode");
        if(storageMode != null){ // Check if storage mode is different to null!

            // ******************************************* //
            //                 Mysql loader                //
            // ******************************************* //
            if (storageMode.equalsIgnoreCase("mysql")) {
                try{
                    registerLoader("mysql", new MysqLoader(plugin));
                    loaderName = "mysql";
                    Bukkit.getConsoleSender().sendMessage(lang.get("selected_storage").replace("%mode%",loaderName));
                    return;
                }catch (Exception e){ Bukkit.getConsoleSender().sendMessage(lang.get("mysql_error")); }
            }
        }else Bukkit.getConsoleSender().sendMessage(lang.get("none_storage"));


        // ******************************************* //
        //                  File loader                //
        // ******************************************* //
        registerLoader("file", new FileLoader());
        loaderName = "file";
        Bukkit.getConsoleSender().sendMessage(lang.get("selected_storage").replace("%mode%",loaderName));
    }

    public static StorageManager getAvailableLoader() {
        return loaderMap.get(loaderName);
    }
    public static String getLoader() {
        return loaderName;
    }

    public static void registerLoader(String dataSource, StorageManager loader) {
        if (loaderMap.containsKey(dataSource)) { throw new IllegalArgumentException("Data source " + dataSource + " already has a declared loader!"); }
        loaderMap.put(dataSource, loader);
    }

}