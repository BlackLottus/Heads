package me.black_lottus.heads.data;

import me.black_lottus.heads.Heads;
import me.black_lottus.heads.utils.Effects;
import me.black_lottus.heads.utils.Sounds;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Methods {

    public static void sendClaimSound(Player p, Location loc){
        new BukkitRunnable(){
            int i = 10;
            @Override
            public void run(){
                if(i==0){
                    p.playSound(p.getLocation(), Sounds.EXPLODE.bukkitSound(), 1, 1);
                    p.playSound(p.getLocation(), Sounds.FIREWORK_LARGE_BLAST.bukkitSound(), 1, 1);
                    p.playSound(p.getLocation(), Sounds.FIREWORK_LARGE_BLAST2.bukkitSound(), 1, 1);

                    Effects.runExplosion(loc);

                    this.cancel();
                    return;
                }else{
                   // p.playSound(p.getLocation(), Sounds.FIREWORK_LAUNCH.bukkitSound(), 1, 2);

                    p.playSound(p.getLocation(), Sounds.NOTE_PIANO.bukkitSound(), 1, 1);
                    p.playSound(p.getLocation(), Sounds.NOTE_BASS_GUITAR.bukkitSound(), 1, 1);
                    p.playSound(p.getLocation(), Sounds.NOTE_PLING.bukkitSound(), 1, 2);
                    p.playSound(p.getLocation(), Sounds.NOTE_PLING.bukkitSound(), 1, 0);

                    Effects.runHelix(loc.add(0,0.2,0));
                }
                i--;
            }
        }.runTaskTimerAsynchronously(Heads.getInstance(), 0L, 2L);

        //
    }
}
