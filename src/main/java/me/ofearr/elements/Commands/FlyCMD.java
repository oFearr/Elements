package me.ofearr.elements.Commands;

import me.ofearr.elements.Utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FlyCMD implements CommandExecutor {

    private List<UUID> currentFlyingPlayers = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("fly")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (sender.hasPermission("elements.fly")) {

                    if(currentFlyingPlayers.contains(player.getUniqueId())){
                        player.setFlying(false);
                        player.sendMessage(StringUtils.translate("&b&lElements &8>> &aYou're no longer flying!"));
                    } else {
                        player.setFlying(true);
                        player.sendMessage(StringUtils.translate("&b&lElements &8>> &aYou're now flying!"));
                    }

                } else {
                    sender.sendMessage(StringUtils.translate("&b&lElements &8>> &cInsufficient permissions!"));
                }
            }

        }

        return false;
    }
}
