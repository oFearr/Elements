package me.ofearr.elements.Events.Misc;

import me.ofearr.elements.Elements;
import me.ofearr.elements.Events.Custom.SpawnerBreakEvent;
import me.ofearr.elements.GUI.ChangeSpawnerTypeGUI;
import me.ofearr.elements.GUI.MainSpawnerGUI;
import me.ofearr.elements.Utils.NBTEditor;
import me.ofearr.elements.Utils.StringUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SpawnerHandlers implements Listener {

    private Elements plugin;
    public SpawnerHandlers(Elements elements){
        this.plugin = elements;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreakSpawner(BlockBreakEvent e){
        Player player = e.getPlayer();

        if(e.getBlock().getType() != Material.SPAWNER) return;
        if(player.getInventory().getItemInMainHand() == null) return;
         if((!player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH)) && (player.getGameMode() != GameMode.CREATIVE)) return;
         if(!player.hasPermission("elements.silkspawner")) return;

         if(!e.isCancelled()){
             Block spawnerBlock = e.getBlock();

             CreatureSpawner creatureSpawner = (CreatureSpawner) spawnerBlock.getState();
             EntityType spawnerType = creatureSpawner.getSpawnedType();

             int tier = 1;
             if(NBTEditor.contains(spawnerBlock, "spawner_tier")){
                 tier = NBTEditor.getInt(spawnerBlock, "spawner_tier");
             }

             e.setExpToDrop(0);

             SpawnerBreakEvent spawnerBreakEvent = new SpawnerBreakEvent(player, spawnerType, spawnerBlock);
             if(!spawnerBreakEvent.isCancelled()){
                 ItemStack spawnerItem = new ItemStack(Material.SPAWNER);
                 ItemMeta itemMeta = spawnerItem.getItemMeta();

                 itemMeta.setDisplayName(StringUtils.translate("&d" + spawnerType.name().toLowerCase().substring(0, 1).toUpperCase() + spawnerType.name().toLowerCase().substring(1) + " Spawner"));
                 List<String> itemLore = new ArrayList<>();

                 itemLore.add(" ");
                 itemLore.add(StringUtils.translate("&bSpawner Tier: " + tier));

                 itemMeta.setLore(itemLore);
                 spawnerItem.setItemMeta(itemMeta);

                 spawnerItem = NBTEditor.set(spawnerItem, spawnerType.toString(), "spawner_type");
                 spawnerItem = NBTEditor.set(spawnerItem, tier, "spawner_tier");

                 if(!player.isSneaking()){
                     Location loc = spawnerBlock.getLocation();
                     loc.getWorld().dropItem(loc, spawnerItem);

                 } else {
                     player.getInventory().addItem(spawnerItem);
                 }

             }

         }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void spawnerPlaceEvent(BlockPlaceEvent e){
        Player player = e.getPlayer();

        if(player.getInventory().getItemInMainHand() == null) return;
        if(player.getInventory().getItemInMainHand().getType() != Material.SPAWNER) return;
        ItemStack spawnerItem = player.getInventory().getItemInMainHand();

        if(!e.isCancelled()){
            if(NBTEditor.contains(spawnerItem, "spawner_type")){
                String spawnerType = NBTEditor.getString(spawnerItem, "spawner_type");
                int tier = NBTEditor.getInt(spawnerItem, "spawner_tier");

                EntityType mobType = EntityType.valueOf(spawnerType);

                if(mobType != null){
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Block placedBlock = e.getBlockPlaced();

                            BlockState blockState = placedBlock.getState();
                            CreatureSpawner creatureSpawner = (CreatureSpawner) blockState;

                            creatureSpawner.setSpawnedType(mobType);
                            blockState.update();

                            placedBlock = NBTEditor.set(placedBlock, tier, "spawner_tier");

                            Location loc = e.getBlock().getLocation();
                            World world = loc.getWorld();

                            world.getBlockAt(loc).setBlockData(placedBlock.getBlockData());

                        }
                    }.runTaskLater(plugin, 5L);
                }

            }
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClickSpawner(PlayerInteractEvent e){
        Player player = e.getPlayer();
        Action action = e.getAction();
        Block block = e.getClickedBlock();

        if(action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.SPAWNER && player.isSneaking()){
            if(player.hasPermission("elements.spawnerupgrades")){
                player.openInventory(new MainSpawnerGUI().GUI());

                e.setCancelled(true);
            }

        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteractWitSpawnerGUI(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();

        ItemStack item = e.getCurrentItem();

        if(item == null) return;
        if(e.getSlotType() == InventoryType.SlotType.OUTSIDE) return;

        if(e.getView().getTitle().equalsIgnoreCase(StringUtils.translate("Spawner Upgrades"))){
            if(item.hasItemMeta() && item.getItemMeta().hasLore()){
                List<String> itemLore = item.getItemMeta().getLore();
                String itemName = item.getItemMeta().getDisplayName();

                if(itemName.equalsIgnoreCase(StringUtils.translate("&dChange Spawner Type"))){
                    player.openInventory(new ChangeSpawnerTypeGUI(plugin).GUI(player,1));

                } else if(itemName.equalsIgnoreCase(StringUtils.translate("&aUpgrade Spawner Tier"))){

                }

            }

            e.setCancelled(true);

        } else if(e.getView().getTitle().equalsIgnoreCase(StringUtils.translate("Change Spawner Type"))){
            if(item.hasItemMeta() && item.getItemMeta().hasLore()){

                if(NBTEditor.contains(item,"entity_type")){
                    String entityType = NBTEditor.getString(item, "entity_type");

                    int cost = plugin.getConfig().getInt("spawners.spawner-types-shop." + entityType.toLowerCase() + ".cost");

                    int playerXPLevel = player.getLevel();

                    if(playerXPLevel >= cost || player.getGameMode() == GameMode.CREATIVE){

                        Set<Material> transparentTypes = new HashSet<>();

                        transparentTypes.add(Material.AIR);
                        transparentTypes.add(Material.WATER);

                        Block block = player.getTargetBlock(transparentTypes, 7);

                        try {
                            if(block.getType() == Material.SPAWNER && EntityType.valueOf(entityType.toUpperCase()) != null){
                                EntityType type = EntityType.valueOf(entityType.toUpperCase());

                                BlockState blockState = block.getState();
                                CreatureSpawner creatureSpawner = (CreatureSpawner) blockState;

                                creatureSpawner.setSpawnedType(type);

                                blockState.update();

                                if(player.getGameMode() != GameMode.CREATIVE) player.setLevel(playerXPLevel - cost);

                                player.sendMessage(StringUtils.translate("&b&lElements &8>> &aSuccessfully changed spawner type to " + type.name().toLowerCase().substring(0, 1).toUpperCase() + type.name().toLowerCase().substring(1) + "!"));
                            }


                        }catch (IllegalArgumentException ex){
                        }


                    } else {
                        player.sendMessage(StringUtils.translate("&b&lElements &8>> &cYou don't have enough Exp to do this!"));
                    }

                }

            }

            e.setCancelled(true);
        }
    }
}
