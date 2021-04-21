package com.grafando.teleportation;

import com.grafando.teleportation.commands.TeleportationCommands;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Teleportation extends JavaPlugin{

    @Override
    public void onEnable() {
        super.onEnable();
        TeleportationCommands tpCommies = new TeleportationCommands();
        this.getCommand("push").setExecutor(tpCommies);
        this.getCommand("pull").setExecutor(tpCommies);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Teleportation]: Plugin is enabled!");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Teleportation]: Plugin is disabled!");
    }
}
