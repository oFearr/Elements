package me.ofearr.elements.Commands;

import me.ofearr.elements.Events.Admin.VanishHandler;
import me.ofearr.elements.Utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VanishCMD implements CommandExecutor {

    private List<UUID> currentVanishedPlayers = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("vanish")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (sender.hasPermission("elements.vanish")) {

                    VanishHandler.toggleVanish(player);
                } else {
                    sender.sendMessage(StringUtils.translate("&b&lElements &8>> &cInsufficient permissions!"));
                }
            }

        }

        return false;
    }
}
