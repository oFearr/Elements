package me.ofearr.elements.PlayerData;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerDataHandler implements Listener {

    public int getPlayerDeaths(Player player){
        PlayerDataManager dataManager = new PlayerDataManager();
        dataManager.load(player);

        return dataManager.getConfig().getInt("stats.deaths");
    }

    public int getPlayerKillsPlayer(Player player){
        PlayerDataManager dataManager = new PlayerDataManager();
        dataManager.load(player);

        return dataManager.getConfig().getInt("stats.player-kills");
    }

    public int getPlayerKillsMob(Player player){
        PlayerDataManager dataManager = new PlayerDataManager();
        dataManager.load(player);

        return dataManager.getConfig().getInt("stats.mob-kills");
    }

    public int getTotalBlocksMined(Player player){
        PlayerDataManager dataManager = new PlayerDataManager();
        dataManager.load(player);

        return dataManager.getConfig().getInt("stats.total-blocks-mined");
    }

    public int getDiamondsMined(Player player){
        PlayerDataManager dataManager = new PlayerDataManager();
        dataManager.load(player);

        return dataManager.getConfig().getInt("stats.diamonds-mined");
    }

    public int getNetheriteMined(Player player){
        PlayerDataManager dataManager = new PlayerDataManager();
        dataManager.load(player);

        return dataManager.getConfig().getInt("stats.netherite-mined");
    }

    public int getPlayerExpGained(Player player){
        PlayerDataManager dataManager = new PlayerDataManager();
        dataManager.load(player);

        return dataManager.getConfig().getInt("stats.exp-gained");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoinCreateDataFile(PlayerJoinEvent e){
        Player player = e.getPlayer();

        PlayerDataManager dataManager = new PlayerDataManager();
        dataManager.create(player);

        if(dataManager.getConfig().get("stats.deaths") == null){
            dataManager.getConfig().set("stats.deaths", 0);
            dataManager.getConfig().set("stats.player-kills", 0);
            dataManager.getConfig().set("stats.mob-kills", 0);
            dataManager.getConfig().set("stats.total-blocks-mined", 0);
            dataManager.getConfig().set("stats.diamonds-mined", 0);
            dataManager.getConfig().set("stats.netherite-mined", 0);
            dataManager.getConfig().set("stats.exp-gained", 0);

            dataManager.saveConfig();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeathEvent(EntityDeathEvent e){
        if(e.getEntity().getKiller() != null){
            Player player = e.getEntity().getKiller();

            PlayerDataManager dataManager = new PlayerDataManager();
            dataManager.load(player);

            if(e.getEntity() instanceof Player){

                dataManager.getConfig().set("stats.player-kills", getPlayerKillsPlayer(player) + 1);

            } else {
                dataManager.getConfig().set("stats.mob-kills", getPlayerKillsMob(player) + 1);
            }

            dataManager.saveConfig();
        }

        if(e.getEntity() instanceof Player){
            Player player = (Player) e.getEntity();

            PlayerDataManager dataManager = new PlayerDataManager();
            dataManager.load(player);

            dataManager.getConfig().set("stats.deaths", getPlayerDeaths(player) + 1);

            dataManager.saveConfig();
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMineBlockEvent(BlockBreakEvent e){
        Player player = e.getPlayer();
        Block block = e.getBlock();

        PlayerDataManager dataManager = new PlayerDataManager();
        dataManager.load(player);

        dataManager.getConfig().set("stats.total-blocks-mined", getTotalBlocksMined(player) + 1);

        if(block.getType() == Material.DIAMOND_ORE){
            dataManager.getConfig().set("stats.diamonds-mined", getDiamondsMined(player) + 1);

        } else if(block.getType() == Material.ANCIENT_DEBRIS){
            dataManager.getConfig().set("stats.netherite-mined", getNetheriteMined(player) + 1);
        }

        dataManager.saveConfig();

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPickupExp(PlayerExpChangeEvent e){
        Player player = e.getPlayer();
        PlayerDataManager dataManager = new PlayerDataManager();
        dataManager.load(player);

        dataManager.getConfig().set("stats.exp-gained", getPlayerExpGained(player) + e.getAmount());
        dataManager.saveConfig();
    }
}
