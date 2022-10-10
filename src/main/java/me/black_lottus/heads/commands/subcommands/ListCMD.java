package me.black_lottus.heads.commands.subcommands;

import me.black_lottus.heads.Heads;
import me.black_lottus.heads.commands.CommandInterface;
import me.black_lottus.heads.data.Data;
import me.black_lottus.heads.data.Permissions;
import me.black_lottus.heads.file.Files;
import me.black_lottus.heads.utils.Inventories;
import org.bukkit.entity.Player;

public class ListCMD extends CommandInterface {

    private final Heads plugin = Heads.getInstance();
    private final Files lang = plugin.getLang();

    @Override
    public void onCommand(Player player, String[] args) {
        // Check if Locations HashMap is empty! If map is empty -->> No Locations listed!
        if(Data.getLocations().isEmpty()){

            player.sendMessage(lang.get("no_heads_list"));
            return;
        }
        // Create a paginated GUI and list all locations!
        Inventories.menuList(player);

        // Messages to complete List!
        player.sendMessage(lang.get("list_heads"));
    }

    @Override
    public String name() {
        return plugin.cmdManager.list;
    }

    @Override
    public String usage() { return "/heads list"; }

    @Override
    public String[] aliases() { return new String[0]; }

    @Override
    public String permission() { return Permissions.ADMIN_PERM; }

    @Override
    public Integer[] length() { return new Integer[]{1}; }
}
