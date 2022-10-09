package me.black_lottus.heads.commands;

import org.bukkit.entity.Player;

public abstract class CommandInterface {

    /*
    /<command> <subcommand> args[0] args[1]
     */

    public CommandInterface() {
    }

    public abstract void onCommand(Player player, String[] args);

    public abstract String name();

    public abstract String usage();

    public abstract String[] aliases();

    public abstract String permission();

    public abstract Integer[] length();
}
