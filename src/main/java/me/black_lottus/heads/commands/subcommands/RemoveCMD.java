package me.black_lottus.heads.commands.subcommands;

import me.black_lottus.heads.Heads;
import me.black_lottus.heads.commands.CommandInterface;
import me.black_lottus.heads.data.Data;
import me.black_lottus.heads.data.Permissions;
import me.black_lottus.heads.file.Files;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class RemoveCMD extends CommandInterface {

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
            plugin.storage.removeLocation(id); // Remove from Database storage!
            for(Location loc : Data.getLocations().keySet()){
                if(Data.getLocations().get(loc) == id) Data.getLocations().remove(loc); // Remove from Memory!
                player.sendMessage(lang.get("coord_removed").replace("%id%",""+id));
                return;
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
