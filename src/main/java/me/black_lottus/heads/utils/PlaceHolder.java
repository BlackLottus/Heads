package me.black_lottus.heads.utils;

import me.black_lottus.heads.Heads;
import me.black_lottus.heads.data.Data;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class PlaceHolder extends PlaceholderExpansion
{
    public String getIdentifier() {
        return "lucky";
    }

    public String getPlugin() {
        return null;
    }

    public String getAuthor() {
        return "Black_Lottus";
    }

    public String getVersion() {
        return Heads.getInstance().getDescription().getVersion();
    }

    public String onPlaceholderRequest(final Player p, final String identifier) {
        if (identifier.equalsIgnoreCase("heads")) {
            if(Data.getTotalHeads().containsKey(p.getUniqueId())) return Data.getTotalHeads().get(p.getUniqueId())+"";
        }
        return null;
    }
}
