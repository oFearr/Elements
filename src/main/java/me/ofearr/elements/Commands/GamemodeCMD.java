package me.ofearr.elements.Commands;

import me.ofearr.elements.Utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCMD implements CommandExecutor {

    public boolean updateGamemode(Player player, String gameModeCMD) {
        switch (gameModeCMD) {
            case "gms":
                player.setGameMode(GameMode.SURVIVAL);
                return true;
            case "gmc":
                player.setGameMode(GameMode.CREATIVE);
                return true;
            case "gmsp":
                player.setGameMode(GameMode.SPECTATOR);
                return true;
            case "gma":
                player.setGameMode(GameMode.ADVENTURE);
                return true;
        }

        return false;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("gms") || command.getName().equalsIgnoreCase("gmc") || command.getName().equalsIgnoreCase("gmsp") || command.getName().equalsIgnoreCase("gma")) {
            if (sender.hasPermission("elements.updategamemode")) {

                String commandName = command.getName().toLowerCase();

                if (args.length >= 1) {
                    String playerName = args[1];

                    if (Bukkit.getPlayer(playerName) != null) {

                        Player player = Bukkit.getPlayer(playerName);

                        if (!updateGamemode(player, commandName)) {
                            player.sendMessage(StringUtils.translate("&b&lElements &8>> &cFailed to update gamemode for " + player.getName()));
                        } else {
                            player.sendMessage(StringUtils.translate("&b&lElements &8>> &aSuccessfully updated gamemode!"));
                        }

                    } else {
                        sender.sendMessage(StringUtils.translate("&b&lElements &8>> &cThat player does not exist!"));
                    }

                } else {
                    if (sender instanceof Player) {
                        Player player = (Player) sender;

                        if (!updateGamemode(player, commandName)) {
                            player.sendMessage(StringUtils.translate("&b&lElements &8>> &cFailed to update gamemode for " + player.getName()));
                        } else {
                            player.sendMessage(StringUtils.translate("&b&lElements &8>> &aSuccessfully updated gamemode!"));
                        }

                    }

                }
            } else {
                sender.sendMessage(StringUtils.translate("&b&lElements &8>> &cInsufficient permissions!"));
            }


        }
        return false;
    }
}
