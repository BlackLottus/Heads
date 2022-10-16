package me.black_lottus.luckyheads.commands.subcommands;

import me.black_lottus.luckyheads.LuckyHeads;
import me.black_lottus.luckyheads.commands.CommandInterface;
import me.black_lottus.luckyheads.data.Data;
import me.black_lottus.luckyheads.data.Permissions;
import me.black_lottus.luckyheads.file.Files;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Objects;

public class RemoveDataCMD extends CommandInterface {

    private final LuckyHeads plugin = LuckyHeads.getInstance();
    private final Files lang = plugin.getLang();

    //TODO...........................................
    // *********************************************/
    //                 RemoveData CMD             */
    // *******************************************/
    // When a player with permissions use this command, remove all data from the specific Player!

    @Override
    public void onCommand(Player player, String[] args) {
        String playerTarget = args[1];
        OfflinePlayer playerOffline = Bukkit.getOfflinePlayer(playerTarget); // Check if player exists!
        if(!playerOffline.hasPlayedBefore()){ // If player have playedBefore == exists!
            player.sendMessage(lang.get("player_not_exists"));
            return;
        }
        plugin.storage.removePlayer(playerOffline.getUniqueId()); // Remove from storage!
        Data.recalcTotalHeads(playerOffline.getUniqueId()); // Re calc values from Memory!

        // Messages complete removeData!
        player.sendMessage(lang.get("data_removed").replace("%player%", Objects.requireNonNull(playerOffline.getName())));
    }

    @Override
    public String name() {
        return plugin.cmdManager.removeData;
    }
    @Override
    public String usage() { return "/heads removeData <player>"; }
    @Override
    public String[] aliases() { return new String[0]; }
    @Override
    public String permission() { return Permissions.ADMIN_PERM; }
    @Override
    public Integer[] length() { return new Integer[]{2}; }
}
