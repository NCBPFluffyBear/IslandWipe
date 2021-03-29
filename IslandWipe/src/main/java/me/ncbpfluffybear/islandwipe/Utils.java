package me.ncbpfluffybear.islandwipe;

import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class Utils {

    public static void send(Player p, String message) {
        p.sendMessage(ChatColor.GRAY + "[IslandWipe] " + ChatColors.color(message));
    }

}
