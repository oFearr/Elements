package me.ofearr.elements.GUI;

import me.ofearr.elements.PlayerData.PlayerDataManager;
import me.ofearr.elements.Utils.GUIPagingUtil;
import me.ofearr.elements.Utils.ServerUtils;
import me.ofearr.elements.Utils.SkullGenerator;
import me.ofearr.elements.Utils.StringUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class HomesGUI {

    public Inventory GUI(Player player, int page){
        Inventory inv = null;
        int slots = 45;

        ItemStack fillerItem = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemStack fillerBar = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);

        List<ItemStack> homeItems = new ArrayList<>();

        PlayerDataManager dataManager = new PlayerDataManager();
        dataManager.load(player);

        ServerUtils serverUtils = new ServerUtils();
        for(String homeName : dataManager.getConfig().getConfigurationSection("homes.").getKeys(false)){
            World world = Bukkit.getWorld(dataManager.getConfig().getString("homes." + homeName + ".world"));

            double x = dataManager.getConfig().getDouble("homes." + homeName + ".x");
            double y = dataManager.getConfig().getDouble("homes." + homeName + ".y");
            double z = dataManager.getConfig().getDouble("homes." + homeName + ".z");

            Location location = new Location(world, x, y, z);

            if(location != null){
                ItemStack homeItem = null;

                switch (world.getEnvironment()){
                    case NORMAL:
                        homeItem = SkullGenerator.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Y0MDk0MmYzNjRmNmNiY2VmZmNmMTE1MTc5NjQxMDI4NmE0OGIxYWViYTc3MjQzZTIxODAyNmMwOWNkMSJ9fX0=");
                        break;
                    case NETHER:
                        homeItem = SkullGenerator.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDgzNTcxZmY1ODlmMWE1OWJiMDJiODA4MDBmYzczNjExNmUyN2MzZGNmOWVmZWJlZGU4Y2YxZmRkZSJ9fX0=");
                        break;
                    case THE_END:
                        homeItem = SkullGenerator.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzZjYWM1OWIyYWFlNDg5YWEwNjg3YjVkODAyYjI1NTVlYjE0YTQwYmQ2MmIyMWViMTE2ZmE1NjljZGI3NTYifX19");
                        break;
                    case CUSTOM:
                        homeItem = SkullGenerator.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThkYWExZTNlZDk0ZmYzZTMzZTFkNGM2ZTQzZjAyNGM0N2Q3OGE1N2JhNGQzOGU3NWU3YzkyNjQxMDYifX19");
                }

                ItemMeta homeMeta = homeItem.getItemMeta();
                homeMeta.setDisplayName(StringUtils.translate("&a&lHome " + homeName));

                List<String> homeLore = new ArrayList<>();
                homeLore.add(" ");
                homeLore.add(StringUtils.translate("&6World: " + serverUtils.getWorldNameString(world)));
                homeLore.add(" ");
                homeLore.add(StringUtils.translate(" &bX: " + (int) x));
                homeLore.add(StringUtils.translate(" &bY: " + (int) y));
                homeLore.add(StringUtils.translate(" &bZ: " + (int) z));
                homeLore.add(" ");
                homeLore.add(StringUtils.translate("&aClick to teleport!"));
                homeLore.add(StringUtils.translate("&cShift-click to delete!"));

                homeMeta.setLore(homeLore);
                homeItem.setItemMeta(homeMeta);

                homeItems.add(homeItem);
            }

        }

        if(homeItems.size() <= 9){
            slots = 9;
        } else if(homeItems.size() <= 27){
            slots = 27;
        } else if(homeItems.size() <= 36){
            slots = 36;
        } else if(homeItems.size() <= 45){
            slots = 45;
        }

        inv = Bukkit.createInventory(null, slots, "Your Homes");

        if(homeItems.size() > 54){
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

            if(GUIPagingUtil.isPageValid(homeItems, page -1, 45)){
                inv.setItem(45, backItem);
            }

            if(GUIPagingUtil.isPageValid(homeItems, page +1, 45)){
                inv.setItem(53, forwardItem);
            }

            for(ItemStack customItem : GUIPagingUtil.getPageItems(homeItems, page, 45)){
                if(customItem.getType() != Material.AIR){
                    inv.setItem(inv.firstEmpty(), customItem);
                }

            }


            for(int i = 45; i < inv.getSize(); i++){
                inv.setItem(i, fillerBar);
            }
        } else {

            int counter = 0;
            for(ItemStack itemStack : homeItems){
                inv.setItem(counter, itemStack);
                counter++;
            }

        }

        for(int i = 0; i < inv.getSize(); i++){
            if(inv.getItem(i) == null) inv.setItem(i, fillerItem);
        }

        return inv;
    }
}
