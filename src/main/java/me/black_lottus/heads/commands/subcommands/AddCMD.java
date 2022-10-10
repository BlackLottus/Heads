package me.black_lottus.heads.commands.subcommands;

import me.black_lottus.heads.Heads;
import me.black_lottus.heads.commands.CommandInterface;
import me.black_lottus.heads.data.Data;
import me.black_lottus.heads.data.Permissions;
import me.black_lottus.heads.file.Files;
import org.bukkit.entity.Player;

public class AddCMD extends CommandInterface {

    private final Heads plugin = Heads.getInstance();
    private final Files lang = plugin.getLang();

    @Override
    public void onCommand(Player player, String[] args) {
        int id;
        try {
            id = Integer.parseInt(args[1]);
            if(Data.getLocations().values().stream().toList().contains(id)){
                player.sendMessage(lang.get("id_already_exists"));
                return;
            }

            if(Data.getWandPos().containsKey(player.getUniqueId())){
                if(Data.getLocations().containsKey(Data.getWandPos().get(player.getUniqueId()))){

                    player.sendMessage(lang.get("coord_already_exists").replace("%id%",""+Data.getLocations().get(Data.getWandPos().get(player.getUniqueId()))));
                    return;
                }
                plugin.storage.addLocation(id, Data.getWandPos().get(player.getUniqueId()));
                Data.getLocations().put(Data.getWandPos().get(player.getUniqueId()), id);
                Data.getWandPos().remove(player.getUniqueId());
                player.sendMessage(lang.get("coord_added"));
            }else{
                player.sendMessage(lang.get("not_coord_set"));
            }

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
