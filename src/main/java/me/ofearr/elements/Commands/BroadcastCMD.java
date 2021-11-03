package me.ofearr.elements.Commands;

import me.ofearr.elements.Utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BroadcastCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("broadcast")) {

            if (sender.hasPermission("elements.broadcast")) {
                if (args.length >= 1) {

                    StringBuilder sb = new StringBuilder();
                    for(String word : args){
                        sb.append(word + " ");
                    }

                    String msg = StringUtils.translate(sb.toString());
                    msg = msg.substring(0, msg.length() - 1);

                    Bukkit.broadcastMessage(StringUtils.translate("&b&lElements &8>> " + msg));

                } else {
                    sender.sendMessage(StringUtils.translate("&b&lElements &8>> &cYou must specify a message to send!"));
                }
            } else {
                sender.sendMessage(StringUtils.translate("&b&lElements &8>> &cInsufficient permissions!"));
            }

        }
        return false;
    }
}
