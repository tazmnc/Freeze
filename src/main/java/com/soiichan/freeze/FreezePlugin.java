package com.soiichan.freeze;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * FreezePlugin - plugin don gian dong bang nguoi choi qua lenh /freeze.
 *
 * Cach dung: /freeze <ten_nguoi_choi>  (go lenh lan nua de go dong bang)
 * Quyen: freeze.use (mac dinh: op)
 */
public class FreezePlugin extends JavaPlugin implements Listener, CommandExecutor {

    /** Danh sach UUID cua nhung nguoi choi dang bi dong bang. */
    private final Set<UUID> frozenPlayers = new HashSet<>();

    @Override
    public void onEnable() {
        getCommand("freeze").setExecutor(this);
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("FreezePlugin da duoc bat!");
    }

    @Override
    public void onDisable() {
        frozenPlayers.clear();
        getLogger().info("FreezePlugin da tat!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("freeze")) {
            return false;
        }

        if (!sender.hasPermission("freeze.use")) {
            sender.sendMessage(ChatColor.RED + "Ban khong co quyen su dung lenh nay!");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(ChatColor.YELLOW + "Cach dung: /" + label + " <ten_nguoi_choi>");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Khong tim thay nguoi choi dang online: " + args[0]);
            return true;
        }

        UUID uuid = target.getUniqueId();
        if (frozenPlayers.contains(uuid)) {
            frozenPlayers.remove(uuid);
            sender.sendMessage(ChatColor.GREEN + "Da go dong bang cho " + target.getName() + ".");
            target.sendMessage(ChatColor.GREEN + "Ban da duoc go dong bang, co the di chuyen lai!");
        } else {
            frozenPlayers.add(uuid);
            sender.sendMessage(ChatColor.AQUA + "Da dong bang " + target.getName() + ".");
            target.sendMessage(ChatColor.AQUA + "Ban da bi dong bang! Hay dung yen.");
        }
        return true;
    }

    /**
     * Chan nguoi choi bi dong bang di chuyen.
     * Van cho phep xoay dau (nhin xung quanh) de do gay kho chiu.
     */
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!frozenPlayers.contains(event.getPlayer().getUniqueId())) {
            return;
        }
        // Chi huy neu nguoi choi doi o block (di chuyen that su)
        if (event.getFrom().getBlockX() != event.getTo().getBlockX()
                || event.getFrom().getBlockY() != event.getTo().getBlockY()
                || event.getFrom().getBlockZ() != event.getTo().getBlockZ()) {
            event.setCancelled(true);
        }
    }

    /** Tien ich: kiem tra mot nguoi choi co dang bi dong bang khong. */
    public boolean isFrozen(Player player) {
        return frozenPlayers.contains(player.getUniqueId());
    }
}
