package me.black_lottus.luckyheads.storage.file;

import me.black_lottus.luckyheads.LuckyHeads;
import me.black_lottus.luckyheads.file.Files;
import me.black_lottus.luckyheads.storage.StorageManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class FileLoader implements StorageManager {

    private final Files fileConfig;
    public FileLoader() {
        fileConfig = new Files(LuckyHeads.getInstance(), "data", false, false);
    }

    //TODO...........................................
    // *********************************************/
    //             List Location Method           */
    // *******************************************/
    // This method list all locations stored in file storage and put this locations in
    // HashMap<Location, Integer> to work in Memory!

    @Override
    public HashMap<Location, Integer> listLocations() {
        HashMap<Location, Integer> locations = new HashMap<>();
        for(String key : Objects.requireNonNull(fileConfig.getConfig().getConfigurationSection("locations")).getKeys(false)){
            Location loc = stringToLocation(fileConfig.getWithoutPrefix("locations."+key));
            locations.put(loc,Integer.parseInt(key));
        }
        return locations;
    }

    //TODO...........................................
    // *********************************************/
    //             Get PlayerHeads Method         */
    // *******************************************/
    // This method list all heads from the specific player stored in file storage and
    // put this heads on ArrayList<Integer>

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

    //TODO...........................................
    // *********************************************/
    //              TotalHeads Method             */
    // *******************************************/
    // This method returns the total number of heads that the specific player has recollect!

    @Override
    public Integer getTotalHeads(UUID uuid) {
        int heads = 0;
        if(fileConfig.getConfig().isSet("players."+uuid)){
            String[] a = fileConfig.getConfig().getString("players."+ uuid).split(":");
            heads = a.length;
        }
        return heads;
    }

    //TODO...........................................
    // *********************************************/
    //              Add Location Method           */
    // *******************************************/
    // Adds and save (or insert) a new Head location and Store in File storage!

    @Override
    public void addLocation(Integer id, Location loc) {
        fileConfig.set("locations."+id, stringFromLocation(loc));
        fileConfig.save();
    }

    //TODO...........................................
    // *********************************************/
    //               Add Head Method              */
    // *******************************************/
    // Adds or insert a new row in Heads File Storage!
    // This row means that the specific player collect the specific head!

    @Override
    public void addHead(UUID uuid, Integer id) {
        String s;
        if(fileConfig.getConfig().isSet("players."+ uuid.toString()))
            s = fileConfig.getConfig().getString("players."+ uuid) + ":"+id;
        else s = ""+id;

        fileConfig.set("players."+uuid, s);
        fileConfig.save();
    }

    //TODO...........................................
    // *********************************************/
    //              Add Player Method             */
    // *******************************************/
    // In file storage this method isn't necessary!

    @Override
    public void addPlayer(UUID uuid, String nickname) {}

    //TODO...........................................
    // *********************************************/
    //              Remove Head Method            */
    // *******************************************/
    // Remove the specific head to the specific Player with uuid = UUID to the File storage!

    @Override
    public void removeHead(Integer id, UUID uuid) {
        if(fileConfig.getConfig().isSet("players."+ uuid)){
            String[] str = fileConfig.getConfig().getString("players."+ uuid).split(":");
            String newStr = "";
            int count = 1;
            int splitSize = str.length;
            for(String s : str){
                try {
                    if(Integer.parseInt(s) != id){
                        if(splitSize == count) newStr = newStr + Integer.parseInt(s);
                        else newStr = newStr +  Integer.parseInt(s) + ":";
                        count++;
                    }else {
                        if(splitSize == count){
                            if(str.length == 1) newStr = null;
                            else newStr = newStr.substring(0, newStr.length()-1);
                        }
                        splitSize--;
                    }
                    fileConfig.set("players."+uuid, newStr);
                } catch (NumberFormatException ignored) { }
            }
            fileConfig.save();
        }
    }

    //TODO...........................................
    // *********************************************/
    //             Remove Player Method           */
    // *******************************************/
    // Remove player from the File storage!
    // When player is removed, all heads collected are also removed!

    @Override
    public void removePlayer(UUID uuid) {
        fileConfig.set("players."+uuid.toString(), null);
        fileConfig.save();
    }

    //TODO...........................................
    // *********************************************/
    //            Remove Location Method          */
    // *******************************************/
    // Remove Head Location from the File storage!
    // When head location is removed, all players that have the specific head collected are also removed!

    @Override
    public void removeLocation(Integer id) {
        fileConfig.set("locations."+id, null);
        for(String path : fileConfig.getConfig().getConfigurationSection("players").getKeys(true)){
            String[] split = fileConfig.getWithoutPrefix("players."+path).split(":");
            int count = 1;
            int splitSize = split.length;
            String newStr = "";
            for(String s : split){
                try {
                    if(Integer.parseInt(s) != id){
                        if(splitSize == count) newStr = newStr + Integer.parseInt(s);
                        else newStr = newStr +  Integer.parseInt(s) + ":";
                        count++;
                    }else {
                        if(splitSize == count){
                            if(split.length == 1) newStr = null;
                            else newStr = newStr.substring(0, newStr.length()-1);
                        }
                        splitSize--;
                    }
                    fileConfig.set("players."+path, newStr);
                } catch (NumberFormatException ignored) { }
            }
        }
        fileConfig.save();
    }

    //TODO...........................................
    // *********************************************/
    //             String Locations Method        */
    // *******************************************/
    // Two additional methods to convert Locations in String format or vice versa!

    private Location stringToLocation(String s){
        String[] a = s.split(":");
        return new Location(Bukkit.getWorld(a[0]),Double.parseDouble(a[1]),Double.parseDouble(a[2]),Double.parseDouble(a[3]));
    }

    private String stringFromLocation(Location loc){
        return  loc.getWorld().getName() + ":" + loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getBlockZ();
    }
}
