package me.ofearr.elements.GUI;

import me.ofearr.elements.PlayerData.PlayerDataHandler;
import me.ofearr.elements.Utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PlayerStatsGUI {

    public Inventory GUI(Player player){
        Inventory inv = Bukkit.createInventory(null, 36, player.getName() + "'s Stats");

        ItemStack fillerItem = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);

        ItemStack deathsItem = new ItemStack(Material.SKELETON_SKULL);
        ItemStack killsItem = new ItemStack(Material.DIAMOND_SWORD);
        ItemStack blocksItem = new ItemStack(Material.DIAMOND_ORE);
        ItemStack expItem = new ItemStack(Material.EXPERIENCE_BOTTLE);

        PlayerDataHandler playerDataHandler = new PlayerDataHandler();

        ItemMeta deathsMeta = deathsItem.getItemMeta();
        deathsMeta.setDisplayName(StringUtils.translate("&cTotal Deaths"));

        List<String> deathsLore = new ArrayList<>();
        deathsLore.add(" ");
        deathsLore.add(StringUtils.translate("&aDeaths: " + playerDataHandler.getPlayerDeaths(player)));
        deathsMeta.setLore(deathsLore);
        deathsItem.setItemMeta(deathsMeta);

        ItemMeta killsMeta = killsItem.getItemMeta();
        killsMeta.setDisplayName(StringUtils.translate("&aTotal Kills"));
        List<String> killsLore = new ArrayList<>();

        killsLore.add(" ");
        killsLore.add(StringUtils.translate("&aPlayer Kills: " + playerDataHandler.getPlayerKillsPlayer(player)));
        killsLore.add(StringUtils.translate("&aMob Kills: " + playerDataHandler.getPlayerKillsMob(player)));
        killsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        killsMeta.setLore(killsLore);
        killsItem.setItemMeta(killsMeta);

        ItemMeta blocksMeta = blocksItem.getItemMeta();
        blocksMeta.setDisplayName(StringUtils.translate("&eTotal Blocks Mined"));
        List<String> blocksLore = new ArrayList<>();

        blocksLore.add(" ");
        blocksLore.add(StringUtils.translate("&aTotal Blocks: " +  playerDataHandler.getTotalBlocksMined(player)));
        blocksLore.add(StringUtils.translate("&aDiamonds: " + playerDataHandler.getDiamondsMined(player)));
        blocksLore.add(StringUtils.translate("&aNetherite: " + playerDataHandler.getNetheriteMined(player)));

        blocksMeta.setLore(blocksLore);
        blocksItem.setItemMeta(blocksMeta);

        ItemMeta expMeta = expItem.getItemMeta();
        expMeta.setDisplayName(StringUtils.translate("&bTotal Exp Collected"));
        List<String> expLore = new ArrayList<>();

        expLore.add(" ");
        expLore.add(StringUtils.translate("&aCollected: " + playerDataHandler.getPlayerExpGained(player)));

        expMeta.setLore(expLore);
        expItem.setItemMeta(expMeta);

        for(int i = 0; i < inv.getSize(); i++){
            inv.setItem(i, fillerItem);
        }

        inv.setItem(10, deathsItem);
        inv.setItem(12, blocksItem);
        inv.setItem(14, expItem);
        inv.setItem(16, killsItem);

        return inv;
    }
}
