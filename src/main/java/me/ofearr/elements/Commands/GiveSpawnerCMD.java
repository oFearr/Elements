package me.ofearr.elements.Commands;

import me.ofearr.elements.Utils.NBTEditor;
import me.ofearr.elements.Utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GiveSpawnerCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("givespawner")) {
            if (sender.hasPermission("elements.givespawner")) {

                Player player = null;
                int amount = 1;

                if (args.length >= 1) {

                    try {
                        String spawnerTypeName = args[0].toUpperCase();

                        if(EntityType.valueOf(spawnerTypeName) != null){
                            EntityType spawnerType = EntityType.valueOf(spawnerTypeName);

                            if(args.length >= 2){
                                if(Double.parseDouble(args[1]) % 1 == 0 && Double.parseDouble(args[1]) >= 1){
                                    if(Long.parseLong(args[1]) > Integer.MAX_VALUE || Long.parseLong(args[1]) > 64){
                                        amount = 64;
                                    } else {
                                        amount = Integer.parseInt(args[1]);
                                    }
                                }

                                if(args.length >= 3){
                                    if(Bukkit.getPlayer(args[2]) != null){
                                        player = Bukkit.getPlayer(args[2]);

                                    }
                                }
                            }

                            ItemStack spawnerItem = new ItemStack(Material.SPAWNER);
                            spawnerItem.setAmount(amount);
                            ItemMeta itemMeta = spawnerItem.getItemMeta();

                            itemMeta.setDisplayName(StringUtils.translate("&d" + spawnerType.name().toLowerCase().substring(0, 1).toUpperCase() + spawnerType.name().toLowerCase().substring(1) + " Spawner"));
                            List<String> itemLore = new ArrayList<>();

                            itemLore.add(" ");
                            itemLore.add(StringUtils.translate("&bSpawner Tier: " + 1));

                            itemMeta.setLore(itemLore);
                            spawnerItem.setItemMeta(itemMeta);

                            spawnerItem = NBTEditor.set(spawnerItem, spawnerType.toString(), "spawner_type");
                            spawnerItem = NBTEditor.set(spawnerItem, 1, "spawner_tier");

                            if(player != null){
                                if(player.getInventory().firstEmpty() != -1){
                                    player.getInventory().addItem(spawnerItem);

                                } else {
                                    player.sendMessage(StringUtils.translate("&b&lElements &8>> &cYour inventory was full so your spawner(s) fell on the ground!"));
                                }

                            } else if (sender instanceof Player){
                                player = (Player) sender;

                                if(player.getInventory().firstEmpty() != -1){
                                    player.getInventory().addItem(spawnerItem);

                                } else {
                                    player.sendMessage(StringUtils.translate("&b&lElements &8>> &cYour inventory was full so your spawner(s) fell on the ground!"));
                                }
                            }

                            if(player != null){
                                sender.sendMessage(StringUtils.translate("&b&lElements &8>> &aSuccessfully given " + amount + " " + spawnerType.name().toLowerCase().substring(0, 1).toUpperCase() + spawnerType.name().toLowerCase().substring(1) + " spawner(s) to " + player.getName() + "!"));

                            }

                        } else {
                            sender.sendMessage(StringUtils.translate("&b&lElements &8>> &cInvalid spawner type provided!"));
                        }
                    }catch (IllegalArgumentException e){
                        sender.sendMessage(StringUtils.translate("&b&lElements &8>> &cNull arguments provided!"));
                    }

                } else {
                    sender.sendMessage(StringUtils.translate("&b&lElements &8>> &cYou must specify a spawner type!"));
                }
            } else {
                sender.sendMessage(StringUtils.translate("&b&lElements &8>> &cInsufficient permissions!"));
            }

        }
        return false;
    }
}
