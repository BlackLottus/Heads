package me.black_lottus.heads.commands;

import me.black_lottus.heads.Heads;
import me.black_lottus.heads.commands.subcommands.*;
import me.black_lottus.heads.file.Files;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class HeadsManager implements CommandExecutor {

    private final ArrayList<CommandInterface> commands = new ArrayList<>();
    private final Heads plugin = Heads.getInstance();
    private final Files lang = plugin.getLang();

    public HeadsManager() {}

    //Sub Commands
    public String heads = "heads";
    public String wand = "wand", add = "add", remove = "remove", removeData = "removeData", list = "list", help = "help";

    public void setup() {
        Objects.requireNonNull(plugin.getCommand(heads)).setExecutor(this);

        this.commands.add(new WandCMD());
        this.commands.add(new AddCMD());
        this.commands.add(new RemoveCMD());
        this.commands.add(new RemoveDataCMD());
        this.commands.add(new ListCMD());
        this.commands.add(new HelpCMD());

    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(lang.get("only_player"));
            return true;
        }

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase(heads)) {
            if (args.length == 0) {
                sendHelp(player);
                return true;
            }

            CommandInterface targetCmd = this.get(args[0]);

            if (targetCmd == null) {
                sendHelp(player);
                return true;
            }

            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(args));
            arrayList.remove(0);

            try{
                // Check Permissions
                if(!player.hasPermission(targetCmd.permission())){
                    player.sendMessage(lang.get("no_perms"));
                    return true;
                }

                // Check if length is correct!
                for(Integer i : targetCmd.length()){
                    if(args.length == i){
                        // Send command!
                        targetCmd.onCommand(player,args);
                        return true;
                    }
                }
                player.sendMessage(lang.get("cmd_usage").replace("%cmd%",targetCmd.usage()));
                return true;


            }catch (Exception e){
                player.sendMessage(ChatColor.RED + "An error has occurred.");

                e.printStackTrace();
            }
        }

        return true;
    }

    private CommandInterface get(String name) {

        for (CommandInterface sc : this.commands) {
            if (sc.name().equalsIgnoreCase(name)) {
                return sc;
            }

            String[] aliases;
            int length = (aliases = sc.aliases()).length;

            for (int var5 = 0; var5 < length; ++var5) {
                String alias = aliases[var5];
                if (name.equalsIgnoreCase(alias)) {
                    return sc;
                }

            }
        }
        return null;
    }

    private void sendHelp(Player p){
        lang.getList("help_message").forEach(p::sendMessage);
    }
}