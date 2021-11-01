package me.ofearr.elements.Utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public class SkullGenerator {

    public static ItemStack getSkull(String texture){
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (byte) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta) skull.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", texture));
        Field field;
        try {
            field = meta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(meta, profile);
        }catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e){
            e.printStackTrace();
        }

        skull.setItemMeta(meta);

        return skull;
    }
}
