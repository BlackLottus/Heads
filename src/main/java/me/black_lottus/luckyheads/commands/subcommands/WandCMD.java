package me.black_lottus.luckyheads.commands.subcommands;

import me.black_lottus.luckyheads.LuckyHeads;
import me.black_lottus.luckyheads.commands.CommandInterface;
import me.black_lottus.luckyheads.data.Data;
import me.black_lottus.luckyheads.data.Permissions;
import me.black_lottus.luckyheads.file.Files;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class WandCMD extends CommandInterface {

    private final LuckyHeads plugin = LuckyHeads.getInstance();
    private final Files lang = plugin.getLang();

    //TODO...........................................
    // *********************************************/
    //                   Wand CMD                 */
    // *******************************************/
    // When a player with permissions use this command, gets in their inventory a new Item called
    // Configuration Wand and this item works for create new heads locations!

    @Override
    public void onCommand(Player player, String[] args) {
        if(Arrays.stream(player.getInventory().getContents()).toList().contains(Data.getWand())){
            player.sendMessage(lang.get("already_have_wand"));
            return;
        }
        player.getInventory().addItem(Data.getWand());
        player.sendMessage(lang.get("get_wand"));
    }

    @Override
    public String name() {
        return plugin.cmdManager.wand;
    }
    @Override
    public String usage() { return "/heads wand"; }
    @Override
    public String[] aliases() { return new String[0]; }
    @Override
    public String permission() { return Permissions.ADMIN_PERM; }
    @Override
    public Integer[] length() { return new Integer[]{1}; }
}
