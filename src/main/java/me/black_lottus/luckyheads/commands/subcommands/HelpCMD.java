package me.black_lottus.luckyheads.commands.subcommands;

import me.black_lottus.luckyheads.LuckyHeads;
import me.black_lottus.luckyheads.commands.CommandInterface;
import me.black_lottus.luckyheads.data.Permissions;
import me.black_lottus.luckyheads.file.Files;
import org.bukkit.entity.Player;

public class HelpCMD extends CommandInterface {

    private final LuckyHeads plugin = LuckyHeads.getInstance();
    private final Files lang = plugin.getLang();

    //TODO...........................................
    // *********************************************/
    //                  Help CMD                  */
    // *******************************************/
    // Display help messages and commands usage!

    @Override
    public void onCommand(Player p, String[] args) {
        lang.getList("help_message").forEach(p::sendMessage);
    }

    @Override
    public String name() { return plugin.cmdManager.help; }
    @Override
    public String usage() { return "/heads help"; }
    @Override
    public String[] aliases() { return new String[0]; }
    @Override
    public String permission() { return Permissions.ADMIN_PERM; }
    @Override
    public Integer[] length() { return new Integer[]{1}; }
}
