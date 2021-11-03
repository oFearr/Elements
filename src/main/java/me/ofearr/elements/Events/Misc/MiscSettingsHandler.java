package me.ofearr.elements.Events.Misc;

import me.ofearr.elements.Elements;
import me.ofearr.elements.PlayerData.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class MiscSettingsHandler implements Listener {

    private Elements plugin;
    public MiscSettingsHandler(Elements elements){
        this.plugin = elements;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTNTExplode(BlockExplodeEvent e){
        boolean blockDamageEnabled = plugin.getConfig().getBoolean("explosion-block-damage");

        if(!blockDamageEnabled) e.setCancelled(true);

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLoginToggleGlow(PlayerJoinEvent e){
        Player player = e.getPlayer();

        PlayerDataManager dataManager = new PlayerDataManager();
        dataManager.load(player);

        if(dataManager.getConfig().getBoolean("settings.glow")){
            player.setGlowing(true);

        } else {
            player.setGlowing(false);
        }

    }
}
