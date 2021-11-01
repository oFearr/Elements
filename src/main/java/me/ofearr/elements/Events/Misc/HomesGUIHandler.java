package me.ofearr.elements.Events.Misc;

import me.ofearr.elements.Elements;
import me.ofearr.elements.GUI.HomesGUI;
import me.ofearr.elements.Utils.HomeUtils;
import me.ofearr.elements.Utils.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class HomesGUIHandler implements Listener {

    private Elements plugin;

    public HomesGUIHandler(Elements elements){
        this.plugin = elements;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteractWithHomesGUI(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();

        ItemStack item = e.getCurrentItem();

        if(item == null) return;
        if(e.getSlotType() == InventoryType.SlotType.OUTSIDE) return;

        if(e.getView().getTitle().equalsIgnoreCase(StringUtils.translate("Your Homes"))){
            if(item.hasItemMeta() && item.getItemMeta().hasLore()){
                List<String> itemLore = item.getItemMeta().getLore();
                String itemName = item.getItemMeta().getDisplayName();

                if(itemName.equalsIgnoreCase(StringUtils.translate("&aClick to go back a page."))
                        || itemName.equalsIgnoreCase(StringUtils.translate("&aClick to go forward a page."))){

                    int page = Integer.parseInt(itemLore.get(1).replace(StringUtils.translate("&bGo to page "), ""));

                    player.openInventory(new HomesGUI().GUI(player, page));

                } else if(itemLore.contains(StringUtils.translate("&aClick to teleport!"))){
                    String homeName = ChatColor.stripColor(itemName).replace("Home ", "");
                    HomeUtils homeUtils = plugin.homeUtils;

                    if(e.isShiftClick()){
                        if(homeUtils.deletePlayerHome(player, homeName)){
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    player.closeInventory();
                                    player.sendMessage(StringUtils.translate("&b&lElements &8>> &aSuccessfully deleted home " + homeName + "!"));
                                }
                            }.runTaskLater(plugin, 1L);

                        } else {
                            player.sendMessage(StringUtils.translate("&b&lElements &8>> &cFailed to delete home " + homeName + "!"));
                        }

                    } else {

                        if(homeUtils.sendPlayerToHome(player, homeName)){
                            player.sendMessage(StringUtils.translate("&b&lElements &8>> &aSuccessfully teleported to home " + homeName + "&a!"));
                        } else {
                            player.sendMessage(StringUtils.translate("&b&lElements &8>> &cYou don't have a home named " + homeName + "&c!"));
                        }

                    }
                }

            }

            e.setCancelled(true);
        }
    }

}
