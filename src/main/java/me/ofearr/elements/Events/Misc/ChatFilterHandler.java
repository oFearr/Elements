package me.ofearr.elements.Events.Misc;

import me.ofearr.elements.Elements;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class ChatFilterHandler implements Listener {

    private Elements plugin;
    public ChatFilterHandler(Elements elements){
        this.plugin = elements;
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
}
