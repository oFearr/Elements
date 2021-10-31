package me.ofearr.elements.Events.Misc;

import me.ofearr.elements.Elements;
import me.ofearr.elements.Utils.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class CustomJoinMessageHandler implements Listener {
    private Elements plugin;

    public CustomJoinMessageHandler(Elements elements){
        this.plugin = elements;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void setJoinMessage(PlayerJoinEvent e){
        Player player = e.getPlayer();

        String joinMessage = plugin.getConfig().getString("join-message");
        joinMessage = joinMessage.replace("<player-name>", player.getName()).replace("<prefix>", plugin.luckPermsUtils.getPlayerPrefix(player));

        e.setJoinMessage(StringUtils.translate(joinMessage));
    }
}
