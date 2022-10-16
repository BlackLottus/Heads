package me.black_lottus.luckyheads.commands.subcommands;

import me.black_lottus.luckyheads.LuckyHeads;
import me.black_lottus.luckyheads.commands.CommandInterface;
import me.black_lottus.luckyheads.data.Data;
import me.black_lottus.luckyheads.data.Permissions;
import me.black_lottus.luckyheads.file.Files;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class RemoveCMD extends CommandInterface {

    private final LuckyHeads plugin = LuckyHeads.getInstance();
    private final Files lang = plugin.getLang();

    //TODO...........................................
    // *********************************************/
    //                   Remove CMD               */
    // *******************************************/
    // When a player with permissions use this command, remove head location data from the storage!
    // Be careful to not confuse with removeID or removeData command!

    @Override
    public void onCommand(Player player, String[] args) {
        int id;
        try {
            id = Integer.parseInt(args[1]); // Check if id is a valid number!
            if(!Data.getLocations().values().stream().toList().contains(id)){ // Check if head locations contains this ID
                player.sendMessage(lang.get("id_not_exists")); // If not contains, send message invalid ID!
                return;
            }
            plugin.storage.removeLocation(id); // Remove from Database storage!
            for(Location loc : Data.getLocations().keySet()){
                if(Data.getLocations().get(loc) == id) {
                    Data.getLocations().remove(loc); // Remove from Memory!
                    // Messages to complete REMOVE Head!
                    if(plugin.getConfig().getBoolean("broadcasts.remove-head.enable")){ // Check if broadcast is enabled!
                        Bukkit.getOnlinePlayers().stream().toList().forEach(p -> p.sendMessage(lang.getWithoutPrefix("prefix") +
                                plugin.getConfig().getString("broadcasts.remove-head.message").replace("&", "§")
                                        .replace("%id%",""+id)));
                    }else player.sendMessage(lang.get("coord_removed").replace("%id%",""+id));
                    // Re calc all players online heads!
                    if(!Bukkit.getOnlinePlayers().isEmpty()){
                        for(Player online : Bukkit.getOnlinePlayers()){ // Due to we remove a head location, we need to re calc players!
                            Data.recalcTotalHeads(online.getUniqueId());
                        }
                    }
                    return;
                }
            }
        } catch (NumberFormatException e) {
            player.sendMessage(lang.get("invalid_id"));
        }
    }

    @Override
    public String name() {
        return plugin.cmdManager.remove;
    }
    @Override
    public String usage() { return "/heads remove <id>"; }
    @Override
    public String[] aliases() { return new String[0]; }
    @Override
    public String permission() { return Permissions.ADMIN_PERM; }
    @Override
    public Integer[] length() { return new Integer[]{2}; }

}
