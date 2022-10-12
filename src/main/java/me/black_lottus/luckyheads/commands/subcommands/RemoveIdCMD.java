package me.black_lottus.luckyheads.commands.subcommands;

import me.black_lottus.luckyheads.LuckyHeads;
import me.black_lottus.luckyheads.commands.CommandInterface;
import me.black_lottus.luckyheads.data.Data;
import me.black_lottus.luckyheads.data.Permissions;
import me.black_lottus.luckyheads.file.Files;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class RemoveIdCMD extends CommandInterface {

    private final LuckyHeads plugin = LuckyHeads.getInstance();
    private final Files lang = plugin.getLang();

    //TODO...........................................
    // *********************************************/
    //                 RemoveID CMD               */
    // *******************************************/
    // When a player with permissions use this command, remove from storage the specific
    // head for the specific player!

    @Override
    public void onCommand(Player player, String[] args) {
        int id;
        try {
            id = Integer.parseInt(args[1]); // Check if id is a valid number!
            if(!Data.getLocations().values().stream().toList().contains(id)){ // Check if head locations contains this ID
                player.sendMessage(lang.get("id_not_exists")); // If not contains, send message invalid ID!
                return;
            }
            String playerTarget = args[2];
            OfflinePlayer playerOffline = Bukkit.getOfflinePlayer(playerTarget); // Gets offline player to check if exists!
            if(!playerOffline.hasPlayedBefore()){ // If player have playedBefore == exists!
                player.sendMessage(lang.get("player_not_exists"));
                return;
            }
            plugin.storage.removeHead(id, playerOffline.getUniqueId()); // Remove from storage!
            Data.recalcTotalHeads(playerOffline.getUniqueId()); // Re calc values from Memory!

            // Send Messages to complete removeID!
            player.sendMessage(lang.get("id_player_removed").replace("%id%",""+id).replace("%player%",playerOffline.getName()));

        } catch (NumberFormatException e) {
            player.sendMessage(lang.get("invalid_id"));
        }
    }

    @Override
    public String name() {
        return plugin.cmdManager.removeId;
    }
    @Override
    public String usage() { return "/heads removeId <id> <player>"; }
    @Override
    public String[] aliases() { return new String[0]; }
    @Override
    public String permission() { return Permissions.ADMIN_PERM; }
    @Override
    public Integer[] length() { return new Integer[]{3}; }
}
