package me.ncbpfluffybear.islandwipe;

import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;

public class IslandWipe extends JavaPlugin implements SlimefunAddon {

    private static IslandWipe instance;

    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(new Events(), this);
    }

    @Override
    public void onDisable() {
        // Logic for disabling the plugin...
    }

    @Override
    public String getBugTrackerURL() {
        // You can return a link to your Bug Tracker instead of null here
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("islandwipe")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This can only be used from in game.");
            } else {
                Player p = (Player) sender;

                Utils.send(p, "/findghost <radius> (/fg) - Finds all ghost blocks in a specified radius");
            }

        } else if (cmd.getName().equalsIgnoreCase("findghost") || cmd.getName().equalsIgnoreCase("fg")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This can only be used from in game.");

            } else if (!sender.hasPermission("islandwipe.admin")) {
                Utils.send((Player) sender, "&7You do not have permission to do this.");
            } else if (args.length == 1) {
                Player p = (Player) sender;
                int r;

                if (args[0].matches("-?\\d+(\\.\\d+)?")) {
                    r = Integer.parseInt(args[0]);
                } else {
                    Utils.send(p, "&cYou can only use numbers between 0 and 101");
                    return true;
                }

                if (r > 100 || r <= 0) {
                    Utils.send(p, "&cYou can only use numbers between 0 and 101");
                    return true;
                } else {
                    Block o = p.getLocation().getBlock();

                    for (int x = -r; x <= r; x++) {
                        for (int y = -r; y <= r; y++) {
                            for (int z = -r; z <= r; z++) {
                                Block b = o.getRelative(x, y, z);
                                if (BlockStorage.hasBlockInfo(b) && b.getType() == Material.AIR) {
                                    b.setType(Material.STRUCTURE_BLOCK);
                                    Utils.send(p, "&3The block at "
                                        + b.getX() + ", " + b.getY() + ", " + b.getZ()
                                        + " has been replaced with a Structure Block");
                                }
                            }
                        }
                    }
                }
            } else {
                Player p = (Player) sender;
                Utils.send(p, "Please specify an radius");
            }
        }
        return true;

    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    public static IslandWipe getInstance() {
        return instance;
    }
}
