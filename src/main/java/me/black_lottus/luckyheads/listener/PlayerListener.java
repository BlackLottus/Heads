package me.black_lottus.luckyheads.listener;

import me.black_lottus.luckyheads.LuckyHeads;
import me.black_lottus.luckyheads.data.Data;
import me.black_lottus.luckyheads.data.Methods;
import me.black_lottus.luckyheads.data.Permissions;
import me.black_lottus.luckyheads.file.Files;
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

    private final LuckyHeads plugin = LuckyHeads.getInstance();
    private final Files lang = plugin.getLang();
    public PlayerListener() {}

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();

        //TODO...........................................
        // *********************************************/
        //                COLLECT HEAD                */
        // *******************************************/

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
                    }else p.sendMessage(lang.get("already_collected"));
                    e.setCancelled(true);
                }
            }
        }

        //TODO...........................................
        // *********************************************/
        //                WAND ACTIONS                */
        // *******************************************/

        if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if(p.getItemInHand() != null){
                if(p.getItemInHand().equals(Data.getWand())){
                    if(!p.hasPermission(Permissions.ADMIN_PERM)){ // Check if player have permission to use wand!
                        p.sendMessage(lang.get("cant_use_wand")); // and if player don't have remove want from inventory!
                        p.setItemInHand(new ItemStack(Material.AIR));
                        e.setCancelled(true);
                        return;
                    }
                    Block b = e.getClickedBlock();
                    if(Data.getWandPos().containsKey(p.getUniqueId())){
                        if(Data.getWandPos().get(p.getUniqueId()).equals(b.getLocation())){ // Additional Check for check if is the same location!
                            p.sendMessage(lang.get("already_block_selected"));
                            e.setCancelled(true);
                            return;
                        }else Data.getWandPos().replace(p.getUniqueId(), b.getLocation()); // Adds the new location!
                    }else Data.getWandPos().put(p.getUniqueId(), b.getLocation()); // Adds the new location!

                    // Message to complete location set!
                    p.sendMessage(lang.get("block_selected").replace("%X%", ""+b.getLocation().getX())
                            .replace("%Y%", ""+b.getLocation().getY())
                            .replace("%Z%", ""+b.getLocation().getZ()));
                    e.setCancelled(true);
                }
            }
        }
    }


    //TODO...........................................
    // *********************************************/
    //            Event Player Join               */
    // *******************************************/
    // When player joins the server, adds the uuid and get the amount of heads
    // and save in HashMap, called TotalHeads.

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Data.addTotalHeads(e.getPlayer().getUniqueId());
    }


    //TODO...........................................
    // *********************************************/
    //            Event Player Quit               */
    // *******************************************/
    // The same with the join event but removing data from Memory.
    // When player quits, remove from HashMap.

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Data.removeTotalHeads(e.getPlayer().getUniqueId());
    }

}