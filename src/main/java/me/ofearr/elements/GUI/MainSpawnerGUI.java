package me.ofearr.elements.GUI;

import me.ofearr.elements.Utils.SkullGenerator;
import me.ofearr.elements.Utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MainSpawnerGUI {

    public Inventory GUI() {
        Inventory inv = Bukkit.createInventory(null, 54, "Spawner Upgrades");

        ItemStack changeTypeItem = new ItemStack(Material.ZOMBIE_SPAWN_EGG);
        ItemStack upgradeItem = SkullGenerator.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNThmZTI1MWE0MGU0MTY3ZDM1ZDA4MWMyNzg2OWFjMTUxYWY5NmI2YmQxNmRkMjgzNGQ1ZGM3MjM1ZjQ3NzkxZCJ9fX0=");
        ItemStack fillerItem = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);

        ItemMeta changeMeta = changeTypeItem.getItemMeta();
        changeMeta.setDisplayName(StringUtils.translate("&dChange Spawner Type"));
        List<String> changeLore = new ArrayList<>();

        changeLore.add(" ");
        changeLore.add(StringUtils.translate("&7Change the type of"));
        changeLore.add(StringUtils.translate("&7this spawner for a"));
        changeLore.add(StringUtils.translate("&bsmall &7price."));
        changeLore.add(" ");
        changeLore.add(StringUtils.translate("&eClick to open!"));

        changeMeta.setLore(changeLore);
        changeTypeItem.setItemMeta(changeMeta);

        ItemMeta upgradeMeta = upgradeItem.getItemMeta();
        upgradeMeta.setDisplayName(StringUtils.translate("&aUpgrade Spawner Tier"));

        List<String> upgradeLore = new ArrayList<>();

        upgradeLore.add(" ");
        upgradeLore.add(StringUtils.translate("&7Upgrade the tier"));
        upgradeLore.add(StringUtils.translate("&7of this spawner."));
        upgradeLore.add(" ");
        upgradeLore.add(StringUtils.translate("&eClick to open!"));

        upgradeMeta.setLore(upgradeLore);
        upgradeItem.setItemMeta(upgradeMeta);

        for(int i = 0; i < inv.getSize(); i++){
            inv.setItem(i, fillerItem);
        }

        inv.setItem(20, upgradeItem);
        inv.setItem(24, changeTypeItem);

        return inv;
    }
}
