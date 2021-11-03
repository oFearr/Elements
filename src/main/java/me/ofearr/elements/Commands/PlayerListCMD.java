package me.ofearr.elements.Commands;

import me.ofearr.elements.Elements;
import me.ofearr.elements.GUI.PlayerListGUI;
import me.ofearr.elements.Utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerListCMD implements CommandExecutor {

    private Elements plugin;
    public PlayerListCMD(Elements elements){
        this.plugin = elements;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("playerlist") ){
            if(sender.hasPermission("elements.playerlist")){
                if(sender instanceof Player){
                    Player player = (Player) sender;

                    player.openInventory(new PlayerListGUI(plugin).GUI(player,1));
                }

            } else {
                sender.sendMessage(StringUtils.translate("&b&lElements &8>> &cInsufficient permissions!"));
            }
        }

        return false;
    }
}
