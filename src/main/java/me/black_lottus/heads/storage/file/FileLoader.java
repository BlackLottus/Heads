package me.black_lottus.heads.storage.file;

import me.black_lottus.heads.Heads;
import me.black_lottus.heads.file.Files;
import me.black_lottus.heads.storage.StorageManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileLoader implements StorageManager {

    private static final SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private final Files fileConfig;
    public FileLoader() {
        fileConfig = new Files(Heads.getInstance(), "data", false, false);
    }


    @Override
    public HashMap<Location, Integer> listLocations() {
        HashMap<Location, Integer> locations = new HashMap<>();
        for(String key : Objects.requireNonNull(fileConfig.getConfig().getConfigurationSection("locations")).getKeys(false)){
            Location loc = stringToLocation(fileConfig.getWithoutPrefix("locations."+key));
            locations.put(loc,Integer.parseInt(key));
        }
        return locations;
    }

    @Override
    public ArrayList<Integer> getPlayerHeads(UUID uuid) {
        ArrayList<Integer> heads = new ArrayList<>();
        if(fileConfig.isSet("players."+uuid)){
            String s = fileConfig.getWithoutPrefix("players."+uuid);
            for(String st : s.split(":")){
                heads.add(Integer.parseInt(st));
            }
        }
        return heads;
    }

    @Override
    public Integer getTotalHeads(UUID uuid) {
        int heads = 0;
        String[] a = fileConfig.getConfig().getString("players."+ uuid).split(":");
        heads = a.length;
        return heads;
    }

    @Override
    public void addLocation(Integer id, Location loc) {
        fileConfig.set("locations."+id, stringFromLocation(loc));
        fileConfig.save();
    }

    @Override
    public void addHead(UUID uuid, Integer id) {
        String s;
        if(fileConfig.getConfig().isSet("players."+ uuid.toString())){
            s = fileConfig.getConfig().getString("players."+ uuid) + ":"+id;
        } else {
            s = ""+id;
        }
        fileConfig.set("players."+uuid, s);
        fileConfig.save();
    }

    @Override
    public void addPlayer(UUID uuid, String nickname) {}

    @Override
    public void removeHead(Integer id, UUID uuid) {
        String s;
        if(fileConfig.getConfig().isSet("players."+ uuid.toString())){
            s = fileConfig.getConfig().getString("players."+ uuid.toString());
            String[] str = s.split(":");
            String newVar = "";
            int count = 1;
            for(String key : str){
                if(Integer.parseInt(key) != id){
                    Bukkit.broadcastMessage(key);
                    if(str.length == count) {
                        newVar = newVar + key;
                    }else {
                        newVar = newVar + key + ":";
                    }
                }
                count++;
            }
            fileConfig.set("players."+ uuid, newVar);
            fileConfig.save();
        }
    }

    @Override
    public void removePlayer(UUID uuid) {
        fileConfig.set("players."+uuid.toString(), null);
        fileConfig.save();
    }

    @Override
    public void removeLocation(Integer id) {
        fileConfig.set("locations."+id, null);
        fileConfig.save();
    }


    private Location stringToLocation(String s){
        String[] a = s.split(":");
        return new Location(Bukkit.getWorld(a[0]),Double.parseDouble(a[1]),Double.parseDouble(a[2]),Double.parseDouble(a[3]));
    }

    private String stringFromLocation(Location loc){
        return  loc.getWorld().getName() + ":" + loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getBlockZ();
    }
}
