package me.ofearr.elements.GUI;

import me.ofearr.elements.Elements;
import me.ofearr.elements.Utils.GUIPagingUtil;
import me.ofearr.elements.Utils.NBTEditor;
import me.ofearr.elements.Utils.SpawnEggUtils;
import me.ofearr.elements.Utils.StringUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChangeSpawnerTypeGUI {

    private Elements plugin;
    public ChangeSpawnerTypeGUI(Elements elements){
        this.plugin = elements;
    }

    public Inventory GUI(Player player, int page){
        Inventory inv = Bukkit.createInventory(null, 54, "Change Spawner Type");

        ItemStack fillerItem = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemStack fillerBar = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);

        List<ItemStack> typeItems = new ArrayList<>();

        SpawnEggUtils spawnEggUtils = new SpawnEggUtils();
        Set<String> enabledEntityTypes = plugin.getConfig().getConfigurationSection("spawners.spawner-types-shop").getKeys(false);

        Set<Material> transparentTypes = new HashSet<>();

        transparentTypes.add(Material.AIR);
        transparentTypes.add(Material.WATER);

        Block block = player.getTargetBlock(transparentTypes, 7);

        if(block.getType() == Material.SPAWNER){
            CreatureSpawner creatureSpawner = (CreatureSpawner) block.getState();

            EntityType entityType = creatureSpawner.getSpawnedType();

            enabledEntityTypes.remove(entityType.name().toLowerCase());
        }

        try {
            for(String typeString : enabledEntityTypes){

                int cost = plugin.getConfig().getInt("spawners.spawner-types-shop." + typeString + ".cost");
                String displayName = plugin.getConfig().getString("spawners.spawner-types-shop." + typeString + ".display-name");
                typeString = typeString.toUpperCase();

                if(EntityType.valueOf(typeString) != null && (spawnEggUtils.getEggFromType(EntityType.valueOf(typeString)) != null)){
                    ItemStack eggItem = spawnEggUtils.getEggFromType(EntityType.valueOf(typeString));

                    ItemMeta itemMeta = eggItem.getItemMeta();
                    itemMeta.setDisplayName(StringEscapeUtils.unescapeJava(StringUtils.translate(displayName)));

                    List<String> itemLore = new ArrayList<>();

                    itemLore.add(" ");
                    itemLore.add(StringUtils.translate("&aExp Cost: " + cost));
                    itemLore.add(StringUtils.translate("&eClick to select!"));

                    itemMeta.setLore(itemLore);
                    eggItem.setItemMeta(itemMeta);

                    eggItem = NBTEditor.set(eggItem, typeString, "entity_type");

                    typeItems.add(eggItem);
                } else {
                    System.out.println(typeString);
                }

            }
        }catch (IllegalArgumentException e){

        }

        ItemStack backItem = new ItemStack(Material.ARROW);
        ItemStack forwardItem = new ItemStack(Material.ARROW);

        ItemMeta backMeta = backItem.getItemMeta();
        backMeta.setDisplayName(StringUtils.translate("&aClick to go back a page."));
        List<String> backLore = new ArrayList<>();
        backLore.add(" ");
        backLore.add(StringUtils.translate("&bGo to page " + (page - 1)));
        backMeta.setLore(backLore);
        backItem.setItemMeta(backMeta);

        ItemMeta forwardMeta = forwardItem.getItemMeta();
        forwardMeta.setDisplayName(StringUtils.translate("&aClick to go forward a page."));
        List<String> forwardLore = new ArrayList<>();
        forwardLore.add(" ");
        forwardLore.add(StringUtils.translate("&bGo to page " + (page + 1)));
        forwardMeta.setLore(forwardLore);
        forwardItem.setItemMeta(forwardMeta);

        if(GUIPagingUtil.isPageValid(typeItems, page -1, 45)){
            inv.setItem(45, backItem);
        }

        if(GUIPagingUtil.isPageValid(typeItems, page +1, 45)){
            inv.setItem(53, forwardItem);
        }

        for(ItemStack customItem : GUIPagingUtil.getPageItems(typeItems, page, 45)){
            if(customItem.getType() != Material.AIR){
                inv.setItem(inv.firstEmpty(), customItem);
            }

        }


        for(int i = 45; i < inv.getSize(); i++){
            inv.setItem(i, fillerBar);
        }

        for(int i = 0; i < inv.getSize(); i++){
            if(inv.getItem(i) == null) inv.setItem(i, fillerItem);
        }

        return inv;
    }
}
