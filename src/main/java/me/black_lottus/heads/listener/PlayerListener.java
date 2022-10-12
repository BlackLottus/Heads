package me.black_lottus.heads.listener;

import me.black_lottus.heads.Heads;
import me.black_lottus.heads.data.Data;
import me.black_lottus.heads.data.Methods;
import me.black_lottus.heads.data.Permissions;
import me.black_lottus.heads.file.Files;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class PlayerListener implements Listener {

    private final Heads plugin = Heads.getInstance();
    private final Files lang = plugin.getLang();
    public PlayerListener() {}



    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();


        if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if(p.hasPermission(Permissions.COLLECT_PERM)){
                if(Data.getLocations().containsKey(e.getClickedBlock().getLocation())){
                    int id = Data.getLocations().get(e.getClickedBlock().getLocation());
                    ArrayList<Integer> heads = plugin.storage.getPlayerHeads(p.getUniqueId());
                    if(!heads.contains(id)){
                        plugin.storage.addPlayer(p.getUniqueId(),p.getName());
                        plugin.storage.addHead(p.getUniqueId(), id);
                        p.sendMessage(lang.get("collect_head"));
                        Data.recalcTotalHeads(p.getUniqueId()); // Add to memory total heads!
                        Methods.sendClaimSound(p, e.getClickedBlock().getLocation());
                    }else{
                        p.sendMessage(lang.get("already_collected"));
                    }

                    e.setCancelled(true);
                }
            }
        }

        if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if(p.getItemInHand() != null){
                if(p.getItemInHand().equals(Data.getWand())){
                    if(!p.hasPermission(Permissions.ADMIN_PERM)){
                        p.sendMessage(lang.get("cant_use_wand"));
                        p.setItemInHand(new ItemStack(Material.AIR));
                        return;
                    }
                    Block b = e.getClickedBlock();
                    Data.getWandPos().put(p.getUniqueId(), b.getLocation());
                    p.sendMessage(lang.get("block_selected").replace("%X%", ""+b.getLocation().getX())
                            .replace("%Y%", ""+b.getLocation().getY())
                            .replace("%Z%", ""+b.getLocation().getZ()));
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Data.addTotalHeads(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Data.removeTotalHeads(e.getPlayer().getUniqueId());
    }

}