package me.black_lottus.heads.data;

import me.black_lottus.heads.Heads;
import me.black_lottus.heads.file.Files;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Wand {

    private static final Heads plugin = Heads.getInstance();
    private static final Files lang = plugin.getLang();

    public static ItemStack createWand() {
        ItemStack a = new ItemStack(lang.getMaterial("wand.material"), 1);
        ItemMeta meta = a.getItemMeta();
        meta.setDisplayName(lang.getWithoutPrefix("wand.name"));
        meta.setLore(lang.getList("wand.lore"));
        a.setItemMeta(meta);

        return a;

    }
}
