package me.ofearr.elements.Utils;

import me.ofearr.elements.Elements;
import me.ofearr.elements.PlayerData.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Set;

public class HomeUtils {

    private static Elements plugin;
    public HomeUtils(Elements elements){
        this.plugin = elements;
    }

    public static int getPlayerHomeCount(Player player){
        if(player.hasPermission("elements.homes.max")) return Integer.MAX_VALUE;

        Set<String> homeGroups = plugin.getConfig().getConfigurationSection("homes.").getKeys(false);

        int playerHouses = 0;
        for(String group : homeGroups){
            String permission = plugin.getConfig().getString("homes." + group + ".permission");
            int homeCount = plugin.getConfig().getInt("homes." + group + ".count");

            if(player.hasPermission(permission) && playerHouses > homeCount) playerHouses = homeCount;
        }

        return playerHouses;
    }

    public static boolean createNewPlayerHome(Player player, String homeName){
        PlayerDataManager dataManager = new PlayerDataManager();
        dataManager.load(player);

        if(dataManager.getConfig().get("homes." + homeName) == null){
            Location loc = player.getLocation();

            World world = loc.getWorld();

            double x = loc.getX();
            double y = loc.getY();
            double z = loc.getZ();
            float yaw = loc.getYaw();
            float pitch = loc.getPitch();

            dataManager.getConfig().set("homes." + homeName + ".world", world.getName());
            dataManager.getConfig().set("homes." + homeName + ".x", x);
            dataManager.getConfig().set("homes." + homeName + ".y", y);
            dataManager.getConfig().set("homes." + homeName + ".z", z);
            dataManager.getConfig().set("homes." + homeName + ".yaw", yaw);
            dataManager.getConfig().set("homes." + homeName + ".pitch", pitch);

            dataManager.saveConfig();

            return true;
        }

        return false;
    }

    public static boolean deletePlayerHome(Player player, String homeName){
        PlayerDataManager dataManager = new PlayerDataManager();
        dataManager.load(player);

        if(dataManager.getConfig().get("homes." + homeName) != null){
            dataManager.getConfig().set("homes." + homeName, null);

            dataManager.saveConfig();
            return true;
        }

        return false;
    }

    public static boolean sendPlayerToHome(Player player, String homeName){
        PlayerDataManager dataManager = new PlayerDataManager();
        dataManager.load(player);

        if(dataManager.getConfig().get("homes." + homeName.toLowerCase()) != null){
            World world = Bukkit.getWorld(dataManager.getConfig().getString("homes." + homeName + ".world"));

            double x = dataManager.getConfig().getDouble("homes." + homeName + ".x");
            double y = dataManager.getConfig().getDouble("homes." + homeName + ".y");
            double z = dataManager.getConfig().getDouble("homes." + homeName + ".z");
            float yaw = (float) dataManager.getConfig().getDouble("homes." + homeName + ".yaw");
            float pitch = (float) dataManager.getConfig().getDouble("homes." + homeName + ".pitch");

            Location homeLocation = new Location(world, x, y, z);

            homeLocation.setYaw(yaw);
            homeLocation.setPitch(pitch);

            if(homeLocation == null) return false;

            if((homeLocation.getBlock().getType() != Material.AIR) || (homeLocation.add(0,1,0).getBlock().getType() != Material.AIR)){
                homeLocation.setY(world.getHighestBlockAt((int) x, (int) z).getY());
            }

            player.teleport(homeLocation);

            return true;
        }

        return false;
    }

}
