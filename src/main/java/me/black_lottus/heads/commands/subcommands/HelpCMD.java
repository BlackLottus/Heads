package me.black_lottus.heads.commands.subcommands;

import me.black_lottus.heads.Heads;
import me.black_lottus.heads.commands.CommandInterface;
import me.black_lottus.heads.data.Permissions;
import me.black_lottus.heads.file.Files;
import org.bukkit.entity.Player;

public class HelpCMD extends CommandInterface {

    private final Heads plugin = Heads.getInstance();
    private final Files lang = plugin.getLang();

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
