package me.ofearr.elements.Events.Custom;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SpawnerBreakEvent extends Event implements Cancellable {

    private final Player player;
    private final EntityType spawnerType;
    private final Block brokenSpawner;

    public SpawnerBreakEvent(Player player, EntityType spawnerType, Block brokenSpawner){
        this.player = player;
        this.spawnerType = spawnerType;
        this.brokenSpawner = brokenSpawner;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    private boolean cancelled;

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public Player getPlayer() {
        return player;
    }

    public EntityType getSpawnerType(){
        return spawnerType;
    }

    public Block getBrokenSpawner(){
        return brokenSpawner;
    }
}
