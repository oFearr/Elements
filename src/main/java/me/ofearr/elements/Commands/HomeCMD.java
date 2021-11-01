package me.ofearr.elements.Commands;

import me.ofearr.elements.Elements;
import me.ofearr.elements.GUI.HomesGUI;
import me.ofearr.elements.Utils.HomeUtils;
import me.ofearr.elements.Utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCMD implements CommandExecutor {

    private Elements plugin;

    public HomeCMD(Elements elements){
        this.plugin = elements;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("sethome")) {
            HomeUtils homeUtils = plugin.homeUtils;

            if(sender instanceof Player){
                Player player = (Player) sender;
                if (sender.hasPermission("elements.sethome") && homeUtils.getPlayerHomeCount(player) >= 1) {
                    if (args.length >= 1) {
                        String homeName = args[0].toLowerCase();

                        homeName = homeName.replace(".", "-");

                        if(homeUtils.createNewPlayerHome(player, homeName)){
                            player.sendMessage(StringUtils.translate("&b&lElements &8>> &aSuccessfully created new home " + homeName + "&a!"));
                        } else {
                            player.sendMessage(StringUtils.translate("&b&lElements &8>> &cYou already have a home named " + homeName + "&c!"));
                        }

                    } else {
                        sender.sendMessage(StringUtils.translate("&b&lElements &8>> &cYou must specify a name for your new home!"));
                    }
                } else {
                    sender.sendMessage(StringUtils.translate("&b&lElements &8>> &cYou cannot have more homes!"));
                }
            }



        } else if (command.getName().equalsIgnoreCase("home")) {
            HomeUtils homeUtils = plugin.homeUtils;

            if(sender instanceof Player){
                Player player = (Player) sender;
                if (sender.hasPermission("elements.home")) {
                    if (args.length >= 1) {
                        String homeName = args[0].toLowerCase();

                        if(homeUtils.sendPlayerToHome(player, homeName)){
                            player.sendMessage(StringUtils.translate("&b&lElements &8>> &aSuccessfully teleported to home " + homeName + "&a!"));
                        } else {
                            player.sendMessage(StringUtils.translate("&b&lElements &8>> &cYou don't have a home named " + homeName + "&c!"));
                        }

                    } else {
                        sender.sendMessage(StringUtils.translate("&b&lElements &8>> &cYou must specify the home you want to travel to!"));
                    }
                } else {
                    sender.sendMessage(StringUtils.translate("&b&lElements &8>> &cYou cannot have more homes!"));
                }
            }

        } else if (command.getName().equalsIgnoreCase("homes")) {

            if(sender instanceof Player){
                Player player = (Player) sender;
                if (sender.hasPermission("elements.homelist")) {
                    player.openInventory(new HomesGUI().GUI(player, 1));
                } else {
                    sender.sendMessage(StringUtils.translate("&b&lElements &8>> &cInsufficient permissions!"));
                }
            }

        }
        return false;
    }
}
