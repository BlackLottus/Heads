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

public class RemoveIdCMD extends CommandInterface {

    private final Heads plugin = Heads.getInstance();
    private final Files lang = plugin.getLang();

    @Override
    public void onCommand(Player player, String[] args) {
        int id;
        try {
            id = Integer.parseInt(args[1]);
            if(!Data.getLocations().values().stream().toList().contains(id)){
                player.sendMessage(lang.get("id_not_exists"));
                return;
            }
            String playerTarget = args[2];
            OfflinePlayer playerOffline = Bukkit.getOfflinePlayer(playerTarget);
            if(!playerOffline.hasPlayedBefore()){
                player.sendMessage(lang.get("player_not_exists"));
                return;
            }
            plugin.storage.removeHead(id, playerOffline.getUniqueId()); // Remove from Database storage!
            Data.recalcTotalHeads(playerOffline.getUniqueId());
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
