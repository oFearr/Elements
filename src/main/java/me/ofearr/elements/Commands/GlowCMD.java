package me.ofearr.elements.Commands;

import me.ofearr.elements.PlayerData.PlayerDataManager;
import me.ofearr.elements.Utils.StringUtils;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GlowCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("glow")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (sender.hasPermission("elements.glow")) {
                    PlayerDataManager dataManager = new PlayerDataManager();
                    dataManager.load(player);

                    if(dataManager.getConfig().getBoolean("settings.glow")){
                        player.setGlowing(false);

                        sender.sendMessage(StringUtils.translate("&b&lElements &8>> &aYou're no longer glowing!"));
                        dataManager.getConfig().set("settings.glow", false);

                    } else {
                        player.setGlowing(true);

                        sender.sendMessage(StringUtils.translate("&b&lElements &8>> &aYou're now glowing!"));
                        dataManager.getConfig().set("settings.glow", true);
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
