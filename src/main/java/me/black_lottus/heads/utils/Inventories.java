package me.black_lottus.heads.utils;

import de.themoep.inventorygui.*;
import me.black_lottus.heads.Heads;
import me.black_lottus.heads.data.Data;
import me.black_lottus.heads.data.Items;
import me.black_lottus.heads.file.Files;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Inventories {

    private static final Heads plugin = Heads.getInstance();
    private static final Files lang = plugin.getLang();

    public static void menuList(Player player){
        String[] guiSetup = {
                "         ",
                " ggggggg ",
                " ggggggg ",
                " ggggggg ",
                "         ",
                " p fdl n "
        };
        InventoryGui gui = new InventoryGui(plugin, null, lang.getWithoutPrefix("list_gui.title"), guiSetup);
        GuiElementGroup group = new GuiElementGroup('g');
        for (Location loc : Data.getLocations().keySet()) {
            // Add an element to the group
            // Elements are in the order they got added to the group and don't need to have the same type.
            //group.addElement((new StaticGuiElement('e', new ItemStack(Material.CHEST), Data.clans.get(text).getClanName())));
            group.addElement(new StaticGuiElement('e',
                    Items.itemStack(Material.SKULL_ITEM, lang.getWithoutPrefix("list_gui.item_name").replace("%id%", Data.getLocations().get(loc)+""), lore(loc)), 1, // Display a number as the item count
                    click -> {
                        player.teleport(loc.add(0.5,0,0.5));
                        player.sendMessage(lang.get("teleport_head"));
                        gui.close();
                        return true; // returning true will cancel the click event and stop taking the item
                    }));
        }
        gui.addElement(group);

        gui.addElement(new DynamicGuiElement('d', (viewer) -> new StaticGuiElement('d', new ItemStack (Material.COMPASS),
                click -> {
                    click.getGui().draw(); // Update the GUI
                    return true;
                })));

        addPaginateButtons(gui, true, true, true, true);
        gui.show(player);
    }

    public static List<String> lore(Location loc){
        List<String> newLst = new ArrayList<>();
        for(String s : lang.getList("list_gui.item_lore")){
            newLst.add(s.replace("%X%",loc.getX()+"")
                    .replace("%Y%",loc.getY()+"")
                    .replace("%Z%",loc.getZ()+""));
        }
        return newLst;
    }
    private static void addPaginateButtons(InventoryGui gui, boolean first, boolean last, boolean next, boolean prev){
        if(first) gui.addElement(new GuiPageElement('f', new ItemStack(lang.getMaterial("list_gui.first_page_material")),
                GuiPageElement.PageAction.FIRST, lang.getWithoutPrefix("list_gui.first_page_name")));
        if(last) gui.addElement(new GuiPageElement('l', new ItemStack(lang.getMaterial("list_gui.last_page_material")),
                GuiPageElement.PageAction.LAST, lang.getWithoutPrefix("list_gui.last_page_name")));
        if(next) gui.addElement(new GuiPageElement('n', new ItemStack(lang.getMaterial("list_gui.next_page_material")),
                GuiPageElement.PageAction.NEXT, lang.getWithoutPrefix("list_gui.next_page_name")));
        if(prev) gui.addElement(new GuiPageElement('p', new ItemStack(lang.getMaterial("list_gui.previous_page_material")),
                GuiPageElement.PageAction.PREVIOUS, lang.getWithoutPrefix("list_gui.previous_page_name")));
    }

}
