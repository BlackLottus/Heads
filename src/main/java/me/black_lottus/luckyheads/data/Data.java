package me.black_lottus.luckyheads.data;

import lombok.Getter;
import lombok.Setter;
import me.black_lottus.luckyheads.LuckyHeads;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Data {

    @Getter @Setter private static HashMap<Location, Integer> locations;
    @Getter @Setter private static HashMap<UUID, Integer> totalHeads;
    @Getter @Setter private static HashMap<UUID, ArrayList<Integer>> playerHeads;
    @Getter @Setter private static HashMap<UUID, Location> wandPos;
    @Getter private static ItemStack wand;
    private static final LuckyHeads plugin = LuckyHeads.getInstance();

    public static void initialize(){
        wand = Items.createWand(); // Create the ItemStack WAND!
        locations = plugin.storage.listLocations(); // Innit locations with the stored locations on storage!
        wandPos = new HashMap<>(); // Innit wandPos --> WandPos is when store locations when click on wand!
        totalHeads = new HashMap<>(); // Innit TotalHeads -->> UUID (Player) and INTEGER (Amount of heads claimed)
        playerHeads = new HashMap<>(); // Innit PlayerHeads -->> UUID (Player) and ArrayList<Integer> is a list of individual heads claimed!
    }

    //TODO...........................................
    // *********************************************/
    //                Add Heads Method            */
    // *******************************************/
    // This method is called on reloads sv or on player join!
    // TotalHeads -> Put in memory the amount of heads per player ONLINE!
    // PlayerHeads -> Put in memory the list of heads per player ONLINE!

    public static void addTotalHeads(UUID uuid){
        int total = plugin.storage.getTotalHeads(uuid);
        if(getTotalHeads().containsKey(uuid)) getTotalHeads().replace(uuid, total);
        else getTotalHeads().put(uuid, total);

        // Add PlayerHeads
        ArrayList<Integer> listHeads = plugin.storage.getPlayerHeads(uuid);
        if(getPlayerHeads().containsKey(uuid)) getPlayerHeads().replace(uuid, listHeads);
        else getPlayerHeads().put(uuid, listHeads);
    }

    //TODO...........................................
    // *********************************************/
    //             Re calc Heads Method            */
    // *******************************************/
    // This method is called on reloads sv or on player join!
    // TotalHeads -> Re calc values in memory of the amount of heads per player ONLINE!
    // PlayerHeads -> Re calc values in memory of the list of heads per player ONLINE!

    public static void recalcTotalHeads(UUID uuid){
        int total = plugin.storage.getTotalHeads(uuid);
        if(getTotalHeads().containsKey(uuid)) getTotalHeads().replace(uuid, total);

        // Re calc PlayerHeads
        ArrayList<Integer> listHeads = plugin.storage.getPlayerHeads(uuid);
        if(getPlayerHeads().containsKey(uuid)) getPlayerHeads().replace(uuid, listHeads);
    }

    //TODO...........................................
    // *********************************************/
    //             Remove Heads Method            */
    // *******************************************/
    // This method is called on disable sv or on player quit!
    // Remove player data from Memory! TotalHeads & PlayerHeads!

    public static void removeTotalHeads(UUID uuid){
        getTotalHeads().remove(uuid);
        getPlayerHeads().remove(uuid);
    }

}
