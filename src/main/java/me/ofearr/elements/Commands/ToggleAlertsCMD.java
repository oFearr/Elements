package me.ofearr.elements.Commands;

import me.ofearr.elements.PlayerData.PlayerDataHandler;
import me.ofearr.elements.PlayerData.PlayerDataManager;
import me.ofearr.elements.Utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ToggleAlertsCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("togglealerts")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (sender.hasPermission("elements.xrayalerts")) {

                    PlayerDataHandler playerDataHandler = new PlayerDataHandler();
                    PlayerDataManager dataManager = new PlayerDataManager();

                    dataManager.load(player);

                    if(playerDataHandler.hasXrayNotifsEnabled(player)){

                        player.sendMessage(StringUtils.translate("&b&lElements &8[&cXRAY&8] >> &aSuccessfully disabled alerts!"));
                        dataManager.getConfig().set("settings.xray-alerts", false);
                    } else {

                        player.sendMessage(StringUtils.translate("&b&lElements &8[&cXRAY&8] >> &aSuccessfully enabled alerts!"));
                        dataManager.getConfig().set("settings.xray-alerts", true);
                    }

                    dataManager.saveConfig();

                } else {
                    sender.sendMessage(StringUtils.translate("&b&lElements &8>> &cInsufficient permissions!"));
                }
            }

        }

        return false;
    }
}
