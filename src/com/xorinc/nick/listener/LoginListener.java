package com.xorinc.nick.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.xorinc.nick.NickNightPlugin;

public class LoginListener implements Listener {
	
	private NickNightPlugin plugin;
	
	public LoginListener(NickNightPlugin plugin){
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event){
		if(plugin.isNickNight()){
			plugin.getManager().setNickFromList(event.getPlayer());
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent event){
		plugin.getManager().unNick(event.getPlayer());
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerKick(PlayerKickEvent event){
		plugin.getManager().unNick(event.getPlayer());
	}
}
