package me.black_lottus.heads.utils;

import me.black_lottus.heads.Heads;
import me.black_lottus.heads.data.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.color.RegularColor;
import xyz.xenondevs.particle.data.texture.BlockTexture;
import xyz.xenondevs.particle.data.texture.ItemTexture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static java.lang.Math.*;

public class Effects {

    public static void runEffects(){
        canClaimEffect();
    }

    public static void runExplosion(Location loc){
        Location loc2 = new Location(loc.getWorld(), loc.getX() + 0.5, loc.getY() + 2, loc.getZ() + 0.5);
        int cont = 1;
        while(cont >= 0){
            new ParticleBuilder(ParticleEffect.EXPLOSION_HUGE, loc2)
                    .display();
            cont--;
        }

        Random rnd = new Random();
        int cont2 = 2;
        while(cont2 >= 0){
            ItemStack item = new ItemStack(Material.IRON_PICKAXE);
            new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc2)
                    .setParticleData(new ItemTexture(item))
                    .setOffsetX(rnd.nextFloat())
                    .setOffsetY(rnd.nextFloat())
                    .setOffsetZ(rnd.nextFloat())
                    .setSpeed(0.1f)
                    .display();

            new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc2)
                    .setParticleData(new ItemTexture(item))
                    .setOffsetX(rnd.nextFloat())
                    .setOffsetY(rnd.nextFloat())
                    .setOffsetZ(-rnd.nextFloat())
                    .setSpeed(0.1f)
                    .display();

            new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc2)
                    .setParticleData(new ItemTexture(item))
                    .setOffsetX(-rnd.nextFloat())
                    .setOffsetY(rnd.nextFloat())
                    .setOffsetZ(rnd.nextFloat())
                    .setSpeed(0.1f)
                    .display();

            new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc2)
                    .setParticleData(new ItemTexture(item))
                    .setOffsetX(-rnd.nextFloat())
                    .setOffsetY(rnd.nextFloat())
                    .setOffsetZ(-rnd.nextFloat())
                    .setSpeed(0.1f)
                    .display();
            cont2 --;
        }

    }

    public static void runHelix(Location loc) {

        Location loc2 = new Location(loc.getWorld(), loc.getX() + 0.5, loc.getY() + 1, loc.getZ() + 0.5);
        new ParticleBuilder(ParticleEffect.REDSTONE, loc2)
                .setParticleData(new RegularColor(255,255,0))
                .display();
        Location loc1 = new Location(loc.getWorld(), loc.getX() + 0.3, loc.getY() + 1, loc.getZ() + 0.4);
        new ParticleBuilder(ParticleEffect.REDSTONE, loc1)
                .setParticleData(new RegularColor(255,255,255))
                .display();

        Random rnd = new Random();

        int cont = 2;
        while(cont >= 0){
            new ParticleBuilder(ParticleEffect.FLAME, loc2)
                    .setOffsetX(rnd.nextFloat())
                    .setOffsetY(-rnd.nextFloat())
                    .setOffsetZ(rnd.nextFloat())
                    .setSpeed(0.1f)
                    .display();

            new ParticleBuilder(ParticleEffect.FLAME, loc2)
                    .setOffsetX(rnd.nextFloat())
                    .setOffsetY(-rnd.nextFloat())
                    .setOffsetZ(-rnd.nextFloat())
                    .setSpeed(0.1f)
                    .display();

            new ParticleBuilder(ParticleEffect.FLAME, loc2)
                    .setOffsetX(-rnd.nextFloat())
                    .setOffsetY(-rnd.nextFloat())
                    .setOffsetZ(rnd.nextFloat())
                    .setSpeed(0.1f)
                    .display();

            new ParticleBuilder(ParticleEffect.FLAME, loc2)
                    .setOffsetX(-rnd.nextFloat())
                    .setOffsetY(-rnd.nextFloat())
                    .setOffsetZ(-rnd.nextFloat())
                    .setSpeed(0.1f)
                    .display();
            cont --;
        }


    }

    private static void create(Location loc, List<Player> players){
        double phi = 0;
        phi += Math.PI/10;
        Random rnd = new Random();
        for (double theta = 0; theta <= 2*Math.PI; theta = theta + Math.PI/40){
            //double r = 1.2;
            double x = 0.2 + (0.9 - 0.2) * rnd.nextDouble();//r*cos(theta)*sin(phi);
            double y = 0.1 + (1.3 - 0.1) * rnd.nextDouble();
            double z = 0.2 + (0.9 - 0.2) * rnd.nextDouble();//r*sin(theta)*sin(phi);
            loc.add(x,y,z);
            //Bukkit.broadcastMessage("Y:"+ y);
            Random random = new Random();
            //Bukkit.broadcastMessage(""+random.nextInt(2));
            int ran = random.nextInt(15);
            if(ran == 1){
                new ParticleBuilder(ParticleEffect.VILLAGER_HAPPY, loc)
                        .display(players);
            }
            loc.subtract(x,y,z);

            if (phi > 8*Math.PI){
               return;
            }
        }
    }

    private static void canClaimEffect(){
        new BukkitRunnable() {

            @Override
            public void run() {
                for(Location loc : Data.getLocations().keySet()){
                    List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
                    for(UUID uuid : Data.getPlayerHeads().keySet()){
                        if(Data.getPlayerHeads().get(uuid).contains(Data.getLocations().get(loc))){
                            onlinePlayers.remove(Bukkit.getPlayer(uuid));
                        }
                    }
                    create(loc, onlinePlayers);

                }
            }
        }.runTaskTimerAsynchronously(Heads.getInstance(), 0L, 20L);

    }
}
