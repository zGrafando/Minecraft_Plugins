package com.grafando.teleportation.commands;

import com.grafando.teleportation.Teleportation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportationCommands implements CommandExecutor {

    private Player target;
    private Player player;
    TeleportationLogs tpLog = new TeleportationLogs();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        player = (Player) sender;
        if (sender instanceof Player){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Teleporting..."));
            if (cmd.getName().equalsIgnoreCase("push")){
                if (args.length == 1){
                    try{
                        target = Bukkit.getPlayer(args[0]);
                        tpLog.writeLogRecord(player, target);
                        player.teleport(target.getLocation());
                    }catch(IllegalArgumentException e){
                        player.sendMessage(ChatColor.translateAlternateColorCodes
                                ('&', "&8Invalid player name!"));
                        e.printStackTrace();
                    }
                }else{
                    player.sendMessage(ChatColor.translateAlternateColorCodes
                            ('&', "&8Please run /<command> <playername>"));
                }
            }else if(cmd.getName().equalsIgnoreCase("pull")){
                if (args.length == 1){
                    try{
                        target = Bukkit.getPlayer(args[0]);
                        tpLog.writeLogRecord(target, player);
                        target.teleport(player.getLocation());
                    }catch(IllegalArgumentException e){
                        player.sendMessage(ChatColor.translateAlternateColorCodes
                                ('&', "&8Invalid player name!"));
                        e.printStackTrace();
                    }
                }
                else{
                    player.sendMessage(ChatColor.translateAlternateColorCodes
                            ('&', "&8Please run /<command> <playername>"));
                }
            }
        }else{
            sender.sendMessage("teleporting...");
        }
        return true;
    }
}
