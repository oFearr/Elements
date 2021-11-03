package me.ofearr.elements.Commands;

import me.ofearr.elements.Events.Misc.ChatModerationHandler;
import me.ofearr.elements.PlayerData.PlayerDataHandler;
import me.ofearr.elements.PlayerData.PlayerDataManager;
import me.ofearr.elements.Utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleChatCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("togglechat")) {
            if (sender.hasPermission("elements.togglechat")) {

                ChatModerationHandler.toggleChat();
            } else {
                sender.sendMessage(StringUtils.translate("&b&lElements &8>> &cInsufficient permissions!"));
            }


        }

        return false;
    }
}
