package me.black_lottus.heads.data;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Items {

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
}
