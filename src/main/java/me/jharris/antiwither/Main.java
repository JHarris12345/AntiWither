package me.jharris.antiwither;

import me.jharris.antiwither.Events.WitherSpawnEvent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("[AntiWither] The plugin has been enabled!");

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new WitherSpawnEvent(this), this);

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equals("awreload")) {
            if (args.length > 0) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if(player.hasPermission("antiwither.reload")) {
                        player.sendMessage(ChatColor.RED + "Did you mean /awreload?");
                    } else {
                        player.sendMessage(ChatColor.RED + "You don't have permission to do this!");
                    }
                } else {
                    System.out.println(ChatColor.WHITE + "[AntiWither] " + ChatColor.RED + "Did you mean `awreload`?");
                }

            } else {
                if (sender instanceof Player) {

                    Player player = (Player) sender;
                    if(player.hasPermission("antiwither.reload")) {
                        reloadConfig();
                        player.sendMessage(ChatColor.GREEN + "The config has been reloaded!");
                        System.out.println(ChatColor.WHITE + "[AntiWither] " + ChatColor.GREEN + "The config has been reloaded!");
                    } else {
                        player.sendMessage(ChatColor.RED + "You don't have permission to do this!");
                    }

                } else {
                    reloadConfig();
                    System.out.println(ChatColor.WHITE + "[AntiWither] " + ChatColor.GREEN + "The config has been reloaded!");
                }
            }
        }
        return true;
    }

        @Override
        public void onDisable () {
            // Plugin shutdown logic
            System.out.println("[AntiWither] The plugin has been disabled!");
        }
}
