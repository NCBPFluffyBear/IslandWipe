package me.ncbpfluffybear.islandwipe;

import com.bgsoftware.superiorskyblock.api.events.IslandDisbandEvent;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class Events implements Listener {

    final WipeTask task = new WipeTask();

    @EventHandler
    public void onIslandClear(IslandDisbandEvent e) {
        new BukkitRunnable() {
            public void run() {
                for (Chunk c : e.getIsland().getAllChunks()) {
                    for (int x = 0; x < 16; x++) {
                        for (int y = 0; y < 256; y++) {
                                // Prevent it from hitting the server all at once
                                Bukkit.getScheduler().runTaskLaterAsynchronously(IslandWipe.getInstance(), task, 5);
                            for (int z = 0; z < 16; z++) {
                                Block b = c.getBlock(x, y, z);
                                if (BlockStorage.checkID(b) != null) {
                                    BlockStorage.clearBlockInfo(b);
                                }
                            }
                        }
                    }
                }
            }
        // The server pauses when this event happens
        }.runTaskAsynchronously(IslandWipe.getInstance());
        Bukkit.getLogger().info("[IslandWipe] " + e.getIsland().getName() + " owned by " +
            e.getPlayer().getName() + " (" + e.getIsland().getCenter(World.Environment.NORMAL).getBlockX() +
            ", " + e.getIsland().getCenter(World.Environment.NORMAL).getBlockZ() + ") has been cleared.");
    }
}
