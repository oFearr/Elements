package me.ofearr.elements.Events.Misc;

import me.ofearr.elements.Elements;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.List;

public class EntityBlacklistHandler implements Listener {

    private Elements plugin;

    public EntityBlacklistHandler(Elements elements){
        this.plugin = elements;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlacklistedMobSpawn(EntitySpawnEvent e){
        List<String> blacklistedEntities = plugin.getConfig().getStringList("blacklisted-entities");

        if(blacklistedEntities.contains(e.getEntityType().toString())){
            e.setCancelled(true);
        }
    }
}
