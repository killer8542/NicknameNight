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
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event){
		if(plugin.isNickNight()){
			try {
				plugin.getManager().setNickFromList(event.getPlayer());
			} catch (NullPointerException e) {}
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerQuit(PlayerQuitEvent event){
		try {
			plugin.getManager().unNick(event.getPlayer());
		} catch (NullPointerException e) {}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerKick(PlayerKickEvent event){
		try {
			plugin.getManager().unNick(event.getPlayer());
		} catch (NullPointerException e) {}
	}
}
