package me.black_lottus.heads.data;

import me.black_lottus.heads.Heads;
import org.bukkit.configuration.file.FileConfiguration;

public class Permissions {
    private static final FileConfiguration config = Heads.getInstance().getConfig();

    public static String ADMIN_PERM, COLLECT_PERM;

    /** Get permissions from Config.yml **/
    public static void initialize(){
        ADMIN_PERM = config.getString("permissions.admin");
        COLLECT_PERM = config.getString("permissions.collect");
    }
}
