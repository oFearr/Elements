package me.ofearr.elements.Events.Admin;

import me.ofearr.elements.Elements;
import me.ofearr.elements.Utils.StringUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VanishHandler implements Listener {

    private static Elements plugin;
    public VanishHandler(Elements elements){
        this.plugin = elements;
    }

    private static List<UUID> currentVanishedPlayers = new ArrayList<>();

    public static void toggleVanish(Player player){
        if(!currentVanishedPlayers.contains(player.getUniqueId())){
            for(Player p : Bukkit.getOnlinePlayers()){
                if(!p.hasPermission("elements.vanish")) p.hidePlayer(plugin, player);
            }

            player.sendMessage(StringUtils.translate("&b&lElements &8>> &aYou're now vanished!"));
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(StringUtils.translate("&aYou're currently vanished!")));

            currentVanishedPlayers.add(player.getUniqueId());
        } else {
            for(Player p : Bukkit.getOnlinePlayers()){
                p.showPlayer(plugin, player);
            }

            player.sendMessage(StringUtils.translate("&b&lElements &8>> &aYou're no longer vanished!"));
            currentVanishedPlayers.remove(player.getUniqueId());
        }
    }

    public static boolean isPlayerVanished(Player player){

        return currentVanishedPlayers.contains(player.getUniqueId());
    }

    public void startActionBarUpdates(){
        new BukkitRunnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()){
                    if(currentVanishedPlayers.contains(player.getUniqueId())){

                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(StringUtils.translate("&aYou're currently vanished!")));

                    }
                }
            }
        }.runTaskTimer(plugin, 30L, 30L);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoinHideVanished(PlayerJoinEvent e){
        Player player = e.getPlayer();

        if(!player.hasPermission("elements.vanish")){
            for(Player p : Bukkit.getOnlinePlayers()){
                if(currentVanishedPlayers.contains(p.getUniqueId())) player.hidePlayer(plugin, p);
            }
        }
    }

}
