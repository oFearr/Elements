package me.ofearr.elements.Utils;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;

public class LuckPermsUtils {

    private static LuckPerms luckPerms;

    public LuckPermsUtils(LuckPerms lp){
        this.luckPerms = lp;
    }

    public static String getPlayerPrefix(Player player){
        User user = luckPerms.getPlayerAdapter(Player.class).getUser(player);
        String prefix = user.getCachedData().getMetaData().getPrefix();

        return prefix;
    }
}
