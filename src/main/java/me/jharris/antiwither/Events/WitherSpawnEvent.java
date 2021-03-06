package me.jharris.antiwither.Events;

import com.sun.org.apache.xpath.internal.operations.Bool;
import me.jharris.antiwither.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WitherSpawnEvent implements Listener {

    Main plugin;
    public static List<String> worldslist = new ArrayList<String>();

    public WitherSpawnEvent(Main plugin) {
        this.plugin = plugin;
        for (String b : plugin.getConfig().getStringList("World-Blacklist"))worldslist.add(b);
    }

    @EventHandler
    public void noWitherSpawn(CreatureSpawnEvent e){

        String message = plugin.getConfig().getString("Spawn-Attempt-Message");
        boolean bypass = plugin.getConfig().getBoolean("AllowBypass");
        String world = e.getEntity().getLocation().getWorld().getName();

        if(plugin.getConfig().getBoolean("DisableWithers")){

            if(e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.BUILD_WITHER)) {
                if (worldslist.contains(world)) return;

                if(!bypass){
                    e.setCancelled(true);

                    for (Entity entity : e.getEntity().getNearbyEntities(3, 3, 3)){
                        if (entity instanceof Player){
                            Player player = (Player) entity;
                            if(message.isEmpty()) return;
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        }
                    }
                } else{
                    for (Entity entity : e.getEntity().getNearbyEntities(3, 3, 3)){
                        if (entity instanceof Player){
                            Player player = (Player) entity;
                            if(!player.hasPermission("antiwither.bypass")) {
                                e.setCancelled(true);
                                if(message.isEmpty()) return;
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                            }
                        }
                    }
                }
            }
        }
    }
}
