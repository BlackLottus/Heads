package me.black_lottus.luckyheads.data;

import me.black_lottus.luckyheads.LuckyHeads;
import me.black_lottus.luckyheads.utils.Effects;
import me.black_lottus.luckyheads.utils.Sounds;
import me.black_lottus.luckyheads.utils.Title;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Methods {

    //TODO...........................................
    // *********************************************/
    //              Claim Sound Method            */
    // *******************************************/
    // This method display a sound in the current location to the current player!
    // The sound determines and mark when player claim or collect a new head!

    public static void sendClaimSound(Player p, Location loc){
        new BukkitRunnable(){ // Create async runnable!
            int i = 10;
            @Override
            public void run(){
                if(i==0){ // When i==0 is when runnable stops!
                    p.playSound(p.getLocation(), Sounds.EXPLODE.bukkitSound(), 1, 1);
                    p.playSound(p.getLocation(), Sounds.FIREWORK_LARGE_BLAST.bukkitSound(), 1, 1);
                    p.playSound(p.getLocation(), Sounds.FIREWORK_LARGE_BLAST2.bukkitSound(), 1, 1);
                    Effects.runExplosion(loc);
                    this.cancel();
                    return;
                }else{
                    // Display sounds 9 times!
                    p.playSound(p.getLocation(), Sounds.NOTE_PIANO.bukkitSound(), 1, 1);
                    p.playSound(p.getLocation(), Sounds.NOTE_BASS_GUITAR.bukkitSound(), 1, 1);
                    p.playSound(p.getLocation(), Sounds.NOTE_PLING.bukkitSound(), 1, 2);
                    p.playSound(p.getLocation(), Sounds.NOTE_PLING.bukkitSound(), 1, 0);

                    Effects.runAnimation(loc.add(0,0.2,0));
                }
                i--;
            }
        }.runTaskTimerAsynchronously(LuckyHeads.getInstance(), 0L, 2L);
    }

    //TODO...........................................
    // *********************************************/
    //               Send Title Method            */
    // *******************************************/
    // This method display a title in the current location to the current player!

    public static void sendTitle(String path, Player p, String title, String subtitle){
        if(LuckyHeads.getInstance().getConfig().getBoolean(path)){
            if(LuckyHeads.isNewVersion) p.sendTitle(title.replace("&","§"),subtitle.replace("&","§"));
            else new Title(title.replace("&","§"), subtitle.replace("&","§")).send(p);
        }
    }
}
