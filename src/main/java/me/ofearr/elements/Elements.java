package me.ofearr.elements;

import me.ofearr.elements.Commands.*;
import me.ofearr.elements.Events.Admin.XrayAlertsHandler;
import me.ofearr.elements.Events.Misc.*;
import me.ofearr.elements.PlayerData.PlayerDataHandler;
import me.ofearr.elements.Utils.HomeUtils;
import me.ofearr.elements.Utils.LuckPermsUtils;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Elements extends JavaPlugin {

    public LuckPerms luckPerms;
    public LuckPermsUtils luckPermsUtils;
    public HomeUtils homeUtils;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();

        }

        luckPermsUtils = new LuckPermsUtils(luckPerms);
        homeUtils = new HomeUtils(this);

        registerCommands();
        registerEvents();
    }

    public void registerCommands(){
        GamemodeCMD gamemodeCMD = new GamemodeCMD();
        HomeCMD homeCMD = new HomeCMD(this);

        getCommand("gms").setExecutor(gamemodeCMD);
        getCommand("gmc").setExecutor(gamemodeCMD);
        getCommand("gmsp").setExecutor(gamemodeCMD);
        getCommand("gma").setExecutor(gamemodeCMD);
        getCommand("playerlist").setExecutor(new PlayerListCMD(this));
        getCommand("sethome").setExecutor(homeCMD);
        getCommand("home").setExecutor(homeCMD);
        getCommand("homes").setExecutor(homeCMD);
        getCommand("heal").setExecutor(new HealCMD());
        getCommand("feed").setExecutor(new FeedCMD());
        getCommand("fly").setExecutor(new FlyCMD());
        getCommand("togglealerts").setExecutor(new ToggleAlertsCMD());
    }

    public void registerEvents(){
        Bukkit.getPluginManager().registerEvents(new CustomJoinMessageHandler(this), this);
        Bukkit.getPluginManager().registerEvents(new EntityBlacklistHandler(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListHandler(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDataHandler(), this);
        Bukkit.getPluginManager().registerEvents(new HomesGUIHandler(this), this);
        Bukkit.getPluginManager().registerEvents(new ChatFilterHandler(this), this);
        Bukkit.getPluginManager().registerEvents(new XrayAlertsHandler(this), this);
    }
}
