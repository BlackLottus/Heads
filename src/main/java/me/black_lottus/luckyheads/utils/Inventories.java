package me.black_lottus.luckyheads.utils;

import de.themoep.inventorygui.*;
import me.black_lottus.luckyheads.LuckyHeads;
import me.black_lottus.luckyheads.data.Data;
import me.black_lottus.luckyheads.data.Items;
import me.black_lottus.luckyheads.file.Files;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Inventories {

    private static final LuckyHeads plugin = LuckyHeads.getInstance();
    private static final Files lang = plugin.getLang();

    //TODO...........................................
    // *********************************************/
    //            MENU GUI LIST METHOD            */
    // *******************************************/
    // The following method creates a new extensible and dimensional GUI
    // for storage and display the amount of Heads and Locations for this Heads
    // that this plugin have stored in Storage system.
    // This menu will be open when player executes the /luckyHeads list command.

    public static void menuList(Player player){
        String[] guiSetup = { // Create structure GUI
                "         ",
                " ggggggg ",
                " ggggggg ",
                " ggggggg ",
                "         ",
                " p fdl n "
        };
        InventoryGui gui = new InventoryGui(plugin, null, lang.getWithoutPrefix("list_gui.title"), guiSetup); // Create inventoryGUI
        GuiElementGroup group = new GuiElementGroup('g');
        for (Location loc : Data.getLocations().keySet()) {
            // Add an element to the group
            // Elements are in the order they got added to the group and don't need to have the same type.
            //group.addElement((new StaticGuiElement('e', new ItemStack(Material.CHEST), Data.clans.get(text).getClanName())));
            String id = Data.getLocations().get(loc).toString(); // Gets id from Locations HashMap.
            group.addElement(new StaticGuiElement('e',
                    Items.itemStack(Material.SKULL_ITEM, lang.getWithoutPrefix("list_gui.item_name").replace("%id%", id), lore(loc)), 1, // Display a number as the item count
                    click -> { // Create click event for cancel clicks and move items.
                        Location newLoc = new Location(loc.getWorld(), loc.getBlockX()+0.5,loc.getBlockY(),loc.getBlockZ()+0.5);
                        player.teleport(newLoc); // Teleports player to head location!
                        player.sendMessage(lang.get("teleport_head"));
                        gui.close();
                        return true; // returning true will cancel the click event and stop taking the item
                    }));
        }
        gui.addElement(group);
        gui.addElement(new DynamicGuiElement('d', (viewer) -> new StaticGuiElement('d', new ItemStack (Material.COMPASS),
                click -> {
                    click.getGui().draw(); // Additional item to Update the GUI
                    return true;
                })));

        addPaginateButtons(gui, true, true, true, true); // Add buttons to move inside the GUI.
        gui.show(player); // Display GUI to player!
    }


    //TODO...........................................
    // *********************************************/
    //                 LORE METHOD                */
    // *******************************************/
    // The following method is a additional method created for edit ItemStacks and their Lore!

    private static List<String> lore(Location loc){
        List<String> newLst = new ArrayList<>();
        for(String s : lang.getList("list_gui.item_lore")){
            newLst.add(s.replace("%X%",loc.getX()+"")
                    .replace("%Y%",loc.getY()+"")
                    .replace("%Z%",loc.getZ()+""));
        }
        return newLst;
    }

    //TODO...........................................
    // *********************************************/
    //          PAGINATED BUTTONS METHOD          */
    // *******************************************/
    // This method set the buttons in the List GUI to move between pages! Last Page, Prev Page...
    // This is a additional method and We could simply not place the buttons. But this way it looks nicer.

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
