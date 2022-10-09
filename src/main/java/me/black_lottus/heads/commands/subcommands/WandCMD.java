package me.black_lottus.heads.commands.subcommands;

import me.black_lottus.heads.Heads;
import me.black_lottus.heads.commands.CommandInterface;
import me.black_lottus.heads.data.Permissions;
import org.bukkit.entity.Player;

public class WandCMD extends CommandInterface {

    private final Heads plugin = Heads.getInstance();

    @Override
    public void onCommand(Player player, String[] args) {

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
    public String permission() { return Permissions.USE_PERM; }

    @Override
    public Integer[] length() { return new Integer[]{1}; }
}
