package com.grafando.home.commands;

import com.grafando.home.data.LocationStorage;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeCommands implements CommandExecutor {

    private Player player;
    private LocationStorage Ls = new LocationStorage();
    private ResultSet rs;
    private Location homeLocation;
    private World World;
    String homeName;


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        player = (Player) sender;
        if(sender instanceof Player){
            if (cmd.getName().equalsIgnoreCase("sethome")){
                if (args.length > 0){
                    if(Ls.checkMultipleHomePermission(player)) {
                        homeName = args[0];
                        Ls.storeLocation(player, homeName);
                        player.sendMessage(ChatColor.translateAlternateColorCodes
                                ('&', "&6Home stored successfully..."));
                    }else{
                        player.sendMessage(ChatColor.translateAlternateColorCodes
                                ('&', "&8You cannot store more than one home!"));
                    }
                }else{
                    homeName = "home";
                    Ls.storeLocation(player, homeName);
                    player.sendMessage(ChatColor.translateAlternateColorCodes
                            ('&', "&6Home stored successfully..."));
                }
            }
            if (cmd.getName().equalsIgnoreCase("home")){
                if (args.length > 0){
                    try {
                        if (Ls.checkMultipleHomePermission(player)){
                            if (Ls.checkIfHomeSet(player)){
                                String target = args[0];
                                rs = Ls.findSpecificPlayerHome(target, player);
                                if (!rs.next()){
                                    player.sendMessage(ChatColor.translateAlternateColorCodes
                                            ('&', "&8Home not set yet!"));
                                }else{
                                    World = player.getServer().getWorld(rs.getString("World"));
                                    homeLocation = new Location(World, (double)rs.getInt("Axis_X"),
                                            (double)rs.getInt("Axis_Y"), (double)rs.getInt("Axis_Z"));
                                    player.teleport(homeLocation);
                                }
                            }else{
                                player.sendMessage(ChatColor.translateAlternateColorCodes
                                        ('&', "&8Home not set yet!"));
                            }
                        }else{
                            player.sendMessage(ChatColor.translateAlternateColorCodes
                                    ('&', "&8You cannot store more than one home!"));
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }else{
                    try {
                        rs = Ls.retrieveLocation(player);
                        if (!rs.next()){
                            player.sendMessage(ChatColor.translateAlternateColorCodes
                                    ('&', "&8Home not set yet!"));
                        }else{
                            World = player.getServer().getWorld(rs.getString("World"));
                            homeLocation = new Location(World, (double)rs.getInt("Axis_X"),
                                    (double)rs.getInt("Axis_Y"), (double)rs.getInt("Axis_Z"));
                            player.teleport(homeLocation);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }
}
