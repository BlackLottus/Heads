package me.black_lottus.heads.listener;

import me.black_lottus.heads.Heads;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class PlayerListener implements Listener {

    private final Heads plugin = Heads.getInstance();
    public PlayerListener() {}



    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();

        /*plugin.storage.addLocation(p.getLocation().getBlock().getLocation());
        plugin.storage.addPlayer(p.getUniqueId(),p.getName());
*/
        HashMap<Location, Integer> locations = plugin.storage.listLocations();
        Bukkit.broadcastMessage("La ID de la loc es: "+locations.get(p.getLocation().getBlock().getLocation()));

        plugin.storage.removeLocation(locations.get(p.getLocation().getBlock().getLocation()));
        plugin.storage.removePlayer(p.getUniqueId());
        plugin.storage.removeHead(locations.get(p.getLocation().getBlock().getLocation()),p.getUniqueId());
        Bukkit.broadcastMessage("removed");





        /**HashMap<Location, Integer> locations = plugin.storage.listLocations();
        if(locations.containsKey(p.getLocation().getBlock().getLocation().add(1,1,1))){
            p.sendMessage("Esta loc ya está guardada.");
            return;
        }else{
            p.sendMessage("Guardando nueva LOC");
        }
        plugin.storage.addLocation(p.getLocation().getBlock().getLocation().add(1,1,1)); */
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){

    }

}