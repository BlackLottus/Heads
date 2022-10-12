package me.black_lottus.luckyheads.utils;

import me.black_lottus.luckyheads.LuckyHeads;
import me.black_lottus.luckyheads.data.Data;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class PlaceHolder extends PlaceholderExpansion {
    public String getIdentifier() {
        return "lucky";
    }
    public String getPlugin() { return null; }
    public String getAuthor() {
        return "Black_Lottus";
    }
    public String getVersion() {
        return LuckyHeads.getInstance().getDescription().getVersion();
    }

    //TODO...........................................
    // *********************************************/
    //            PLACEHOLDER METHOD              */
    // *******************************************/
    // This class create a new PlaceHolder like 'lucky_heads'
    // getting the values from 'TotalHeads' HashMap<UUID, Integer>
    // This placeholder shows the number of heads that the specific player has collected.

    public String onPlaceholderRequest(final Player p, final String identifier) {
        if (identifier.equalsIgnoreCase("heads")) {
            if(Data.getTotalHeads().containsKey(p.getUniqueId())) return Data.getTotalHeads().get(p.getUniqueId())+"";
        }
        return null;
    }
}
