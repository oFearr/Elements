package me.ofearr.elements.Events.Admin;

import me.ofearr.elements.Elements;
import me.ofearr.elements.PlayerData.PlayerDataHandler;
import me.ofearr.elements.Utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class XrayAlertsHandler implements Listener {

    private Elements plugin;
    public XrayAlertsHandler(Elements elements){
        this.plugin = elements;
    }

    private HashMap<UUID, HashMap<Material, Integer>> xrayMaterialCountTracker = new HashMap<>();
    private List<UUID> currentTrackedPlayers = new ArrayList<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTriggerXrayBound(BlockBreakEvent e){
        Player player = e.getPlayer();
        Material material = e.getBlock().getType();


        if(plugin.getConfig().get("xray-alerts." + material.toString().toLowerCase()) != null){

            if(!xrayMaterialCountTracker.containsKey(player.getUniqueId())){
                HashMap<Material, Integer> trackedBlocksMap = new HashMap<>();

                xrayMaterialCountTracker.put(player.getUniqueId(), trackedBlocksMap);
            }

            if(!xrayMaterialCountTracker.get(player.getUniqueId()).containsKey(material)){
                xrayMaterialCountTracker.get(player.getUniqueId()).put(material, 1);

            } else {
                xrayMaterialCountTracker.get(player.getUniqueId()).put(material, xrayMaterialCountTracker.get(player.getUniqueId()).get(material) +1);
            }

            if(!currentTrackedPlayers.contains(player.getUniqueId())){
                int requiredCount = plugin.getConfig().getInt("xray-alerts." + material.toString().toLowerCase() + ".required-amount");
                long interval = plugin.getConfig().getInt("xray-alerts." + material.toString().toLowerCase() + ".check-interval") * 20;

                currentTrackedPlayers.add(player.getUniqueId());

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        int playerCount = 0;

                        if(xrayMaterialCountTracker.get(player.getUniqueId()).get(material) != null){
                            playerCount = xrayMaterialCountTracker.get(player.getUniqueId()).get(material);
                        }

                        if(playerCount >= requiredCount){

                            PlayerDataHandler playerDataHandler = new PlayerDataHandler();

                            for(Player p : Bukkit.getOnlinePlayers()){
                                if(p.hasPermission("elements.xrayalerts") && playerDataHandler.hasXrayNotifsEnabled(player)){
                                    player.sendMessage(StringUtils.translate("&b&lElements &8[&cXRAY&8] >> &c" + player.getName() + " has mined " + playerCount + " " + material.toString().toLowerCase() + " in the past " + (interval / 20) + "s!"));
                                }
                            }

                            xrayMaterialCountTracker.get(player.getUniqueId()).remove(material);
                        }

                        currentTrackedPlayers.remove(player.getUniqueId());
                    }
                }.runTaskLater(plugin, interval);

            }


        }

    }
}
