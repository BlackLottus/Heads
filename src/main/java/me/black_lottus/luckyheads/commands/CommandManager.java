package me.black_lottus.luckyheads.commands;

import me.black_lottus.luckyheads.LuckyHeads;
import me.black_lottus.luckyheads.commands.subcommands.*;
import me.black_lottus.luckyheads.file.Files;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class CommandManager implements CommandExecutor {

    private final ArrayList<CommandInterface> commands = new ArrayList<>();
    private final LuckyHeads plugin = LuckyHeads.getInstance();
    private final Files lang = plugin.getLang();

    public CommandManager() {}

    //Sub Commands
    public String luckyHeads = "luckyHeads";
    public String wand = "wand", add = "add", remove = "remove", removeId = "removeId", removeData = "removeData", list = "list", help = "help";

    public void setup() {
        Objects.requireNonNull(plugin.getCommand(luckyHeads)).setExecutor(this);

        this.commands.add(new WandCMD());
        this.commands.add(new AddCMD());
        this.commands.add(new RemoveCMD());
        this.commands.add(new RemoveIdCMD());
        this.commands.add(new RemoveDataCMD());
        this.commands.add(new ListCMD());
        this.commands.add(new HelpCMD());
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) { // Check if sender is Player -->> Else cancel!
            sender.sendMessage(lang.get("only_player"));
            return true;
        }
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase(luckyHeads)) { // Check if command is 'LuckyHeads'
            if (args.length == 0) {
                sendHelp(player); // Send help cmd! To display all commands!
                return true;
            }

            CommandInterface targetCmd = this.get(args[0]);

            if (targetCmd == null) {
                sendHelp(player); // Send help cmd! To display all commands!
                return true;
            }

            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(args));
            arrayList.remove(0);

            try{
                if(!player.hasPermission(targetCmd.permission())){ // Check Permissions
                    player.sendMessage(lang.get("no_perms"));
                    return true;
                }
                for(Integer i : targetCmd.length()){ // Check if length is correct!
                    if(args.length == i){
                        // Send command!
                        targetCmd.onCommand(player,args);
                        return true;
                    }
                }
                player.sendMessage(lang.get("cmd_usage").replace("%cmd%",targetCmd.usage()));
                return true;
            }catch (Exception e){
                player.sendMessage(ChatColor.RED + "An error has occurred. Report to dev Black_Lottus.");
                e.printStackTrace();
            }
        }
        return true;
    }

    // Private method to add aliases and Commands to the Array!
    private CommandInterface get(String name) {
        for (CommandInterface sc : this.commands) {
            if (sc.name().equalsIgnoreCase(name)) return sc;

            String[] aliases;
            int length = (aliases = sc.aliases()).length;
            for (int var5 = 0; var5 < length; ++var5) {
                String alias = aliases[var5];
                if (name.equalsIgnoreCase(alias)) return sc;
            }
        }
        return null;
    }

    // Private method to help to display HELP COMMAND LIST!
    private void sendHelp(Player p){
        lang.getList("help_message").forEach(p::sendMessage);
    }
}