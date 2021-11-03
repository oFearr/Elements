package me.ofearr.elements.Events.Misc;

import me.ofearr.elements.Elements;
import me.ofearr.elements.GUI.PlayerListGUI;
import me.ofearr.elements.GUI.PlayerStatsGUI;
import me.ofearr.elements.Utils.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlayerListHandler implements Listener {

    private Elements plugin;

    public PlayerListHandler(Elements elements){
        this.plugin = elements;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteractWithPlayerList(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();

        ItemStack item = e.getCurrentItem();

        if(item == null) return;
        if(e.getSlotType() == InventoryType.SlotType.OUTSIDE) return;

        if(e.getView().getTitle().equalsIgnoreCase(StringUtils.translate("Online Players"))){
            if(item.hasItemMeta() && item.getItemMeta().hasLore()){
                List<String> itemLore = item.getItemMeta().getLore();
                String itemName = item.getItemMeta().getDisplayName();

                if(itemName.equalsIgnoreCase(StringUtils.translate("&aClick to go back a page."))
                        || itemName.equalsIgnoreCase(StringUtils.translate("&aClick to go forward a page."))){

                    int page = Integer.parseInt(itemLore.get(1).replace(StringUtils.translate("&bGo to page "), ""));

                    player.openInventory(new PlayerListGUI(plugin).GUI(player, page));

                } else if(itemLore.contains(StringUtils.translate("&eClick to view additional stats!"))){
                    player.openInventory(new PlayerStatsGUI().GUI(player));
                }

            }

            e.setCancelled(true);
        } else if(e.getView().getTitle().contains("'s Stats")) e.setCancelled(true);
    }

}
