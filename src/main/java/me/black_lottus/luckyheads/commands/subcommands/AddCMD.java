package me.black_lottus.luckyheads.commands.subcommands;

import me.black_lottus.luckyheads.LuckyHeads;
import me.black_lottus.luckyheads.commands.CommandInterface;
import me.black_lottus.luckyheads.data.Data;
import me.black_lottus.luckyheads.data.Methods;
import me.black_lottus.luckyheads.data.Permissions;
import me.black_lottus.luckyheads.file.Files;
import me.black_lottus.luckyheads.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AddCMD extends CommandInterface {

    private final LuckyHeads plugin = LuckyHeads.getInstance();
    private final Files lang = plugin.getLang();

    //TODO...........................................
    // *********************************************/
    //                  AddHead CMD               */
    // *******************************************/
    // When a player with permissions use this command, adds a new head location to the storage!

    @Override
    public void onCommand(Player player, String[] args) {
        int id;
        try {
            id = Integer.parseInt(args[1]); // Check if id is a valid number!
            if(Data.getLocations().values().stream().toList().contains(id)){ // Check if head locations contains this ID
                player.sendMessage(lang.get("id_already_exists")); // If  contains, send message invalid ID -->> Already TAKE!
                return;
            }

            if(Data.getWandPos().containsKey(player.getUniqueId())){ // Get location to wandPos stored on HashMap!
                if(Data.getLocations().containsKey(Data.getWandPos().get(player.getUniqueId()))){
                    // Check if Locations have this location! If have this loc -->> Position head already exists!
                    player.sendMessage(lang.get("coord_already_exists").replace("%id%",""+Data.getLocations().get(Data.getWandPos().get(player.getUniqueId()))));
                    return;
                }
                plugin.storage.addLocation(id, Data.getWandPos().get(player.getUniqueId())); // ADD Head to the storage!
                Data.getLocations().put(Data.getWandPos().get(player.getUniqueId()), id); // Add location head to the Memory!
                Data.getWandPos().remove(player.getUniqueId()); // Remove location from the HashMap wandPos! -->> Is already saved --> i don't need!

                Methods.sendTitle("titles.add-head.enable",player, // Send Title if is Enabled in Config.
                        ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("titles.add-head.title")),
                        ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("titles.add-head.subtitle")));

                // Messages to complete ADD Head!
                if(plugin.getConfig().getBoolean("broadcasts.add-head.enable")){ // Check if broadcast is enabled!
                    Bukkit.getOnlinePlayers().stream().toList().forEach(p -> p.sendMessage(lang.getWithoutPrefix("prefix") +
                            plugin.getConfig().getString("broadcasts.add-head.message").replace("&", "§")
                                    .replace("%id%",""+id)));
                }else player.sendMessage(lang.get("coord_added"));
            }else player.sendMessage(lang.get("not_coord_set")); // Need to establish coords with Wand!
        } catch (NumberFormatException e) {
            player.sendMessage(lang.get("invalid_id"));
        }
    }

    @Override
    public String name() {
        return plugin.cmdManager.add;
    }
    @Override
    public String usage() { return "/heads add <id>"; }
    @Override
    public String[] aliases() { return new String[0]; }
    @Override
    public String permission() { return Permissions.ADMIN_PERM; }
    @Override
    public Integer[] length() { return new Integer[]{2}; }
}
