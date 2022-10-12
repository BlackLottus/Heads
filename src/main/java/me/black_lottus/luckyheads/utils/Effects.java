package me.black_lottus.luckyheads.utils;

import me.black_lottus.luckyheads.LuckyHeads;
import me.black_lottus.luckyheads.data.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.color.RegularColor;
import xyz.xenondevs.particle.data.texture.ItemTexture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Effects {
    static Random rnd = new Random();
    public static void runEffects(){
        canClaimEffect();
    }

    //TODO...........................................
    // *********************************************/
    //            RUN EXPLOSION METHOD            */
    // *******************************************/
    // This method create a various Particle Effects simulating a Explosion Huge in the specific Location.
    // We use multiple loops while for duplicate impact effects.

    public static void runExplosion(Location loc){
        Location loc2 = new Location(loc.getWorld(), loc.getX() + 0.5, loc.getY() + 2, loc.getZ() + 0.5);
        int cont = 1;
        while(cont >= 0){ // Loop while
            new ParticleBuilder(ParticleEffect.EXPLOSION_HUGE, loc2).display(); // Display Particle Effect!
            cont--; // Decrease counter!
        }

        // In the next lines of code I'm trying to simulate one Item breaking down!
        // I use a multiple particle effects inside a loop for this.
        int cont2 = 2;
        while(cont2 >= 0){ // Loop 2-3 times.
            ItemStack item = new ItemStack(Material.IRON_PICKAXE);
            new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc2).setParticleData(new ItemTexture(item)).setOffsetX(rnd.nextFloat())
                    .setOffsetY(rnd.nextFloat()).setOffsetZ(rnd.nextFloat()).setSpeed(0.1f).display();

            new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc2).setParticleData(new ItemTexture(item)).setOffsetX(rnd.nextFloat())
                    .setOffsetY(rnd.nextFloat()).setOffsetZ(-rnd.nextFloat()).setSpeed(0.1f).display();

            new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc2).setParticleData(new ItemTexture(item)).setOffsetX(-rnd.nextFloat())
                    .setOffsetY(rnd.nextFloat()).setOffsetZ(rnd.nextFloat()).setSpeed(0.1f).display();

            new ParticleBuilder(ParticleEffect.ITEM_CRACK, loc2).setParticleData(new ItemTexture(item)).setOffsetX(-rnd.nextFloat())
                    .setOffsetY(rnd.nextFloat()).setOffsetZ(-rnd.nextFloat()).setSpeed(0.1f).display();
            cont2 --; // Decrease counter!
        }
    }

    //TODO...........................................
    // *********************************************/
    //            RUN ANIMATION METHOD            */
    // *******************************************/
    // This method use a various Particle Effects to create a one animation in the specific Location.
    // It's a bit hard to explain, I use a multiple particles and multiple loops for create a nice Animation!
    // Animation is a spiral of fire that grows upward!

    public static void runAnimation(Location loc) {
        // Modify the location to sett just in center of Block!
        Location loc2 = new Location(loc.getWorld(), loc.getX() + 0.5, loc.getY() + 1, loc.getZ() + 0.5);
        new ParticleBuilder(ParticleEffect.REDSTONE, loc2).setParticleData(new RegularColor(255,255,255)).display();

        int cont = 2;
        while(cont >= 0){ // Loop 2-3 times.
            new ParticleBuilder(ParticleEffect.FLAME, loc2).setOffsetX(rnd.nextFloat()).setOffsetY(-rnd.nextFloat())
                    .setOffsetZ(rnd.nextFloat()).setSpeed(0.1f).display();
            new ParticleBuilder(ParticleEffect.FLAME, loc2).setOffsetX(rnd.nextFloat()).setOffsetY(-rnd.nextFloat())
                    .setOffsetZ(-rnd.nextFloat()).setSpeed(0.1f).display();
            new ParticleBuilder(ParticleEffect.FLAME, loc2).setOffsetX(-rnd.nextFloat()).setOffsetY(-rnd.nextFloat())
                    .setOffsetZ(rnd.nextFloat()).setSpeed(0.1f).display();
            new ParticleBuilder(ParticleEffect.FLAME, loc2).setOffsetX(-rnd.nextFloat()).setOffsetY(-rnd.nextFloat())
                    .setOffsetZ(-rnd.nextFloat()).setSpeed(0.1f).display();
            cont --; // Decrease counter!
        }
    }

    //TODO...........................................
    // *********************************************/
    //            RUN ANIMATION METHOD            */
    // *******************************************/
    // This method use a various Particle Effects to create a one animation in the specific Location.
    // It's a bit hard to explain, I use a multiple particles and multiple loops for create a nice Animation!
    // Animation is a spiral of fire that grows upward!

    private static void canCollect(Location loc, List<Player> players){
        double phi = 0; phi += Math.PI/10;
        for (double theta = 0; theta <= 2*Math.PI; theta = theta + Math.PI/40){
            double x = 0.2 + (0.9 - 0.2) * rnd.nextDouble(); double y = 0.1 + (1.3 - 0.1) * rnd.nextDouble();
            double z = 0.2 + (0.9 - 0.2) * rnd.nextDouble(); int ran = rnd.nextInt(15);
            loc.add(x,y,z);
            if(ran == 1) new ParticleBuilder(ParticleEffect.VILLAGER_HAPPY, loc).display(players);
            loc.subtract(x,y,z);
            if (phi > 8*Math.PI) return;
        }
    }

    //TODO...........................................
    // *********************************************/
    //            RUN CLAIM EFFECT METHOD         */
    // *******************************************/
    // Asynchronous Task that checks if ONLINE player have claimed the stored heads in Memory!
    // If some player have some Head to claim, this task display a effect around this head!

    private static void canClaimEffect(){
        new BukkitRunnable() {

            @Override
            public void run() {
                for(Location loc : Data.getLocations().keySet()){
                    List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers()); // Store ONLINE players in a new List!
                    for(UUID uuid : Data.getPlayerHeads().keySet()){
                        // Loop for all Players in HashMap and check their claimed heads!
                        // if some player have some head to claim, remove from List and then display effect to others!
                        if(Data.getPlayerHeads().get(uuid).contains(Data.getLocations().get(loc))) onlinePlayers.remove(Bukkit.getPlayer(uuid));
                    }
                    canCollect(loc, onlinePlayers);
                }
            }
        }.runTaskTimerAsynchronously(LuckyHeads.getInstance(), 0L, 20L); // Task 20L = 1 sec!
    }
}
