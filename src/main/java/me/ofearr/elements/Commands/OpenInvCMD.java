package me.ofearr.elements.Commands;

import me.ofearr.elements.Utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpenInvCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("openinv")) {

            if(sender instanceof Player){
                Player player = (Player) sender;
                if (sender.hasPermission("elements.openinv")) {
                    if (args.length >= 1) {
                        String playerName = args[0].toLowerCase();

                        if(Bukkit.getPlayer(playerName) != null){
                            Player target = Bukkit.getPlayer(playerName);

                            player.openInventory(target.getInventory());

                        } else {
                            sender.sendMessage(StringUtils.translate("&b&lElements &8>> &cInvalid player name provided!"));
                        }

                    } else {
                        sender.sendMessage(StringUtils.translate("&b&lElements &8>> &cYou must specify a player name!"));
                    }
                } else {
                    sender.sendMessage(StringUtils.translate("&b&lElements &8>> &cInsufficient permissions!"));
                }
            }



        }
        return false;
    }
}
