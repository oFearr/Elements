package me.ofearr.elements;

import me.ofearr.elements.Commands.GamemodeCMD;
import me.ofearr.elements.Commands.PlayerListCMD;
import me.ofearr.elements.Events.Misc.CustomJoinMessageHandler;
import me.ofearr.elements.Events.Misc.EntityBlacklistHandler;
import me.ofearr.elements.Events.Misc.PlayerListHandler;
import me.ofearr.elements.PlayerData.PlayerDataHandler;
import me.ofearr.elements.Utils.LuckPermsUtils;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Elements extends JavaPlugin {

    public LuckPerms luckPerms;
    public LuckPermsUtils luckPermsUtils;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();

        }

        luckPermsUtils = new LuckPermsUtils(luckPerms);

        registerCommands();
        registerEvents();
    }

    public void registerCommands(){
        GamemodeCMD gamemodeCMD = new GamemodeCMD();

        getCommand("gms").setExecutor(gamemodeCMD);
        getCommand("gmc").setExecutor(gamemodeCMD);
        getCommand("gmsp").setExecutor(gamemodeCMD);
        getCommand("gma").setExecutor(gamemodeCMD);
        getCommand("playerlist").setExecutor(new PlayerListCMD(this));
    }

    public void registerEvents(){
        Bukkit.getPluginManager().registerEvents(new CustomJoinMessageHandler(this), this);
        Bukkit.getPluginManager().registerEvents(new EntityBlacklistHandler(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListHandler(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDataHandler(), this);
    }
}
