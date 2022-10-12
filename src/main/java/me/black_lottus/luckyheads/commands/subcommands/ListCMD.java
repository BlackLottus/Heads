package me.black_lottus.luckyheads.commands.subcommands;

import me.black_lottus.luckyheads.LuckyHeads;
import me.black_lottus.luckyheads.commands.CommandInterface;
import me.black_lottus.luckyheads.data.Data;
import me.black_lottus.luckyheads.data.Permissions;
import me.black_lottus.luckyheads.file.Files;
import me.black_lottus.luckyheads.utils.Inventories;
import org.bukkit.entity.Player;

public class ListCMD extends CommandInterface {

    private final LuckyHeads plugin = LuckyHeads.getInstance();
    private final Files lang = plugin.getLang();

    //TODO...........................................
    // *********************************************/
    //                  HeadList CMD              */
    // *******************************************/
    // When a player with permissions use this command, display a GUI when you can show
    // all heads and their locations. In addiction you can teleport to this location!

    @Override
    public void onCommand(Player player, String[] args) {
        // Check if Locations HashMap is empty! If map is empty -->> No Locations listed!
        if(Data.getLocations().isEmpty()){
            player.sendMessage(lang.get("no_heads_list"));
            return;
        }
        Inventories.menuList(player); // Create a paginated GUI and list all locations!

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
