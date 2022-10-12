package me.black_lottus.heads.data;

import lombok.Getter;
import lombok.Setter;
import me.black_lottus.heads.Heads;
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
    private static final Heads plugin = Heads.getInstance();

    public static void initialize(){
        wand = Wand.createWand();
        locations = plugin.storage.listLocations();
        wandPos = new HashMap<>();
        totalHeads = new HashMap<>();
        playerHeads = new HashMap<>();
    }

    public static void addTotalHeads(UUID uuid){
        int total = plugin.storage.getTotalHeads(uuid);
        if(getTotalHeads().containsKey(uuid)) getTotalHeads().replace(uuid, total);
        else getTotalHeads().put(uuid, total);

        // Add PlayerHeads
        ArrayList<Integer> listHeads = plugin.storage.getPlayerHeads(uuid);
        if(getPlayerHeads().containsKey(uuid)) getPlayerHeads().replace(uuid, listHeads);
        else getPlayerHeads().put(uuid, listHeads);
    }

    public static void recalcTotalHeads(UUID uuid){
        int total = plugin.storage.getTotalHeads(uuid);
        if(getTotalHeads().containsKey(uuid)) getTotalHeads().replace(uuid, total);

        // Re calc PlayerHeads
        ArrayList<Integer> listHeads = plugin.storage.getPlayerHeads(uuid);
        if(getPlayerHeads().containsKey(uuid)) getPlayerHeads().replace(uuid, listHeads);
    }

    public static void removeTotalHeads(UUID uuid){
        getTotalHeads().remove(uuid);
        getPlayerHeads().remove(uuid);
    }

}
