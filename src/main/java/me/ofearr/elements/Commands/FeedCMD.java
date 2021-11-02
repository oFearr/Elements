package me.ofearr.elements.Commands;

import me.ofearr.elements.Utils.StringUtils;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("feed")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (sender.hasPermission("elements.feed")) {

                    player.setFoodLevel(20);

                    player.sendMessage(StringUtils.translate("&b&lElements &8>> &aYou're now fully saturated!"));
                } else {
                    sender.sendMessage(StringUtils.translate("&b&lElements &8>> &cInsufficient permissions!"));
                }
            }

        }

        return false;
    }
}
