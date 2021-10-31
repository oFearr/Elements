package me.ofearr.elements.Utils;

import org.bukkit.World;

public class ServerUtils {

    public String getWorldNameString(World world){

        switch (world.getEnvironment()){
            case NORMAL:
                return StringUtils.translate("&aOverworld");
            case NETHER:
                return StringUtils.translate("&cThe Nether");
            case THE_END:
                return StringUtils.translate("&5The End");
        }

        return "";
    }

}
