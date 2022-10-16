package me.black_lottus.luckyheads.data;

import me.black_lottus.luckyheads.LuckyHeads;
import me.black_lottus.luckyheads.file.Files;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Items {

    private static final LuckyHeads plugin = LuckyHeads.getInstance();
    private static final Files lang = plugin.getLang();

    //TODO...........................................
    // *********************************************/
    //                 Wand Method                */
    // *******************************************/
    // Create a ItemStack!

    public static ItemStack itemStack(Material material, String name, List<String> lore){
        ItemStack i = new ItemStack(material, 1);
        ItemMeta meta = i.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        if(lore != null) {
            lore.replaceAll(s -> s.replace("&","§"));
            meta.setLore(lore);
        }
        i.setItemMeta(meta);
        return i;
    }

    //TODO...........................................
    // *********************************************/
    //                 Wand Method                */
    // *******************************************/
    // Create the ItemStack WAND! Getting values from lang.yml

    public static ItemStack createWand() {
        ItemStack a = new ItemStack(lang.getMaterial("wand.material"), 1);
        ItemMeta meta = a.getItemMeta();
        assert meta != null;
        meta.setDisplayName(lang.getWithoutPrefix("wand.name"));
        meta.setLore(lang.getList("wand.lore"));
        a.setItemMeta(meta);
        return a;
    }
}
