package me.black_lottus.heads.commands.subcommands;

import me.black_lottus.heads.Heads;
import me.black_lottus.heads.commands.CommandInterface;
import me.black_lottus.heads.data.Data;
import me.black_lottus.heads.data.Permissions;
import me.black_lottus.heads.file.Files;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class RemoveDataCMD extends CommandInterface {

    private final Heads plugin = Heads.getInstance();
    private final Files lang = plugin.getLang();

    @Override
    public void onCommand(Player player, String[] args) {
        String playerTarget = args[1];
        OfflinePlayer playerOffline = Bukkit.getOfflinePlayer(playerTarget);
        if(!playerOffline.hasPlayedBefore()){
            player.sendMessage(lang.get("player_not_exists"));
            return;
        }
        plugin.storage.removePlayer(playerOffline.getUniqueId());
        player.sendMessage(lang.get("data_removed").replace("%player%",playerOffline.getName()));
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
