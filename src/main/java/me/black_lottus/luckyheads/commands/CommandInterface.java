package me.black_lottus.luckyheads.commands;

import org.bukkit.entity.Player;

public abstract class CommandInterface {

    //TODO...........................................
    // *********************************************/
    //             COMMAND INTERFACE              */
    // *******************************************/
    // Create a multiple methods to use in a different command types!

    public CommandInterface() {}

    public abstract void onCommand(Player player, String[] args);
    public abstract String name();
    public abstract String usage();
    public abstract String[] aliases();
    public abstract String permission();
    public abstract Integer[] length();
}
