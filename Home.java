package com.grafando.home;

import com.grafando.home.commands.HomeCommands;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Home extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();
        HomeCommands homeCommies = new HomeCommands();
        this.getCommand("home").setExecutor(homeCommies);
        this.getCommand("sethome").setExecutor(homeCommies);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Home]: Plugin is enabled!");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Home]: Plugin is disabled!");
    }
}
