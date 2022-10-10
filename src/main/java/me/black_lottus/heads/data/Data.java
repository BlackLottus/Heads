package me.black_lottus.heads.data;

import lombok.Getter;
import lombok.Setter;
import me.black_lottus.heads.Heads;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class Data {

    @Getter @Setter private static HashMap<Location, Integer> locations;
    @Getter @Setter private static HashMap<UUID, Location> wandPos;
    @Getter private static ItemStack wand;
    private static final Heads plugin = Heads.getInstance();

    public static void initialize(){
        wand = Wand.createWand();
        locations = plugin.storage.listLocations();
        wandPos = new HashMap<>();

    }

}
