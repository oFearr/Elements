package me.ofearr.elements.GUI;

import me.ofearr.elements.Elements;
import me.ofearr.elements.Events.Admin.VanishHandler;
import me.ofearr.elements.Utils.GUIPagingUtil;
import me.ofearr.elements.Utils.ServerUtils;
import me.ofearr.elements.Utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PlayerListGUI {
    private Elements plugin;

    public PlayerListGUI(Elements elements){
        this.plugin = elements;
    }

    public Inventory GUI(Player player, int page){
        Inventory inv = Bukkit.createInventory(null, 54, "Online Players");

        ItemStack fillerItem = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemStack fillerBar = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);

        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.applyPattern("#,###");

        ServerUtils serverUtils = new ServerUtils();

        List<ItemStack> playerItems = new ArrayList<>();

        for(Player p : Bukkit.getOnlinePlayers()){

            if(!(VanishHandler.isPlayerVanished(p) && !player.hasPermission("elements.vanish"))){
                ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);

                SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
                skullMeta.setOwningPlayer(p);

                skullMeta.setDisplayName(StringUtils.translate(plugin.luckPermsUtils.getPlayerPrefix(p) + " &f" + p.getName()));

                List<String> itemLore = new ArrayList<>();
                itemLore.add(" ");
                itemLore.add(StringUtils.translate("&cHealth: &d" + decimalFormat.format(p.getHealth()) + "/" + decimalFormat.format(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) + " ❤"));
                itemLore.add(StringUtils.translate("&aWorld: " + serverUtils.getWorldNameString(p.getWorld())));
                itemLore.add(" ");
                itemLore.add(StringUtils.translate("&eClick to view additional stats!"));

                skullMeta.setLore(itemLore);

                playerHead.setItemMeta(skullMeta);

                playerItems.add(playerHead);
            }

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

        if(GUIPagingUtil.isPageValid(playerItems, page -1, 45)){
            inv.setItem(45, backItem);
        }

        if(GUIPagingUtil.isPageValid(playerItems, page +1, 45)){
            inv.setItem(53, forwardItem);
        }

        for(ItemStack customItem : GUIPagingUtil.getPageItems(playerItems, page, 45)){
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
