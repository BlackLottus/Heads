package me.black_lottus.luckyheads.data;

import me.black_lottus.luckyheads.LuckyHeads;
import org.bukkit.configuration.file.FileConfiguration;

public class Permissions {
    private static final FileConfiguration config = LuckyHeads.getInstance().getConfig();

    public static String ADMIN_PERM, COLLECT_PERM;

    //TODO...........................................
    // *********************************************/
    //                PERMISSIONS                 */
    // *******************************************/
    // Getting permissions from Config.yml and save in variable!

    public static void initialize(){
        ADMIN_PERM = config.getString("permissions.admin");
        COLLECT_PERM = config.getString("permissions.collect");
    }
}
