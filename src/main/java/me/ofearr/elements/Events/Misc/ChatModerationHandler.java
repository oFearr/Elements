package me.ofearr.elements.Events.Misc;

import me.ofearr.elements.Elements;
import me.ofearr.elements.Utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class ChatModerationHandler implements Listener {

    private Elements plugin;
    public ChatModerationHandler(Elements elements){
        this.plugin = elements;
    }

    private static boolean chatEnabled = true;

    public static void toggleChat(){
        if(chatEnabled){
            chatEnabled = false;
            Bukkit.broadcastMessage(StringUtils.translate("&b&lElements &8>> &c&lChat has been disabled!"));
        } else {
            chatEnabled = true;
            Bukkit.broadcastMessage(StringUtils.translate("&b&lElements &8>> &a&lChat has been enabled!"));
        }
    }

    public String getCensoredWordReplacement(String word){
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < word.length(); i++){
            sb.append("*");
        }

        return sb.toString();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreachChatFilter(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        String msg = e.getMessage();

        if(player.hasPermission("elements.admin")) return;

        List<String> blockedWords = plugin.getConfig().getStringList("chat-filter");

        for(String word : blockedWords){

            if(msg.toLowerCase().contains(word)){
                msg = msg.replaceAll("(?i)" + word, getCensoredWordReplacement(word));

            }
        }

        e.setMessage(msg);

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMessageWhileChatDisabled(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();

        if(player.hasPermission("elements.admin")) return;
        if(!chatEnabled){
            e.setCancelled(true);

            player.sendMessage(StringUtils.translate("&b&lElements &8>> &cThe chat is currently muted!"));
        }

    }
}
