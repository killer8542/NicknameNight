package com.xorinc.nick.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.xorinc.nick.NickNightPlugin;
import com.xorinc.nick.NicknameManager;
import com.xorinc.nick.config.NickList;
import com.xorinc.nick.exception.NoSuchNickListException;

public class NickCommand implements CommandExecutor {
	
	private NickNightPlugin plugin;
	
	public NickCommand(NickNightPlugin plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!sender.hasPermission("nicknight.use")){
			sender.sendMessage(ChatColor.RED + "You may not initiate a nickname night.");
			return true;
		}
		
		if(args.length < 1){
			sender.sendMessage("/nicknight [<list name>|reload]");
			return true;
		}
		
		/*if(args[0].equals("reload")){
			if(!sender.hasPermission("nicknight.reload"))
				sender.sendMessage("Config reloaded.");
			return true;
		}*/
		
		if(args[0].equals("stop")){
			if(plugin.isNickNight()){
				plugin.setNickNight(false);
				plugin.getManager().unNickAll();
				plugin.getServer().broadcastMessage("" + ChatColor.BLUE + ChatColor.ITALIC + "* *Nickname night is over.* *");
			}
			return true;
		}
		
		if(plugin.isNickNight()){
			sender.sendMessage("A nickname night is currently in session.");
			return true;
		}
		
		NickList list = new NickList(plugin, args[0]);
		
		try {
			list.loadSettings();
		} catch (NoSuchNickListException e) {
			sender.sendMessage(ChatColor.RED + "The nickname list " + args[0] + ".yml does not exist.");
			return true;
		}
		
		plugin.setManager(new NicknameManager(plugin, list));
		plugin.setNickNight(true);
		plugin.getServer().broadcastMessage("" + ChatColor.BLUE + ChatColor.ITALIC + "* *A nickname night has begun!* *");
		for(Player player : plugin.getServer().getOnlinePlayers())
			plugin.getManager().setNickFromList(player);
			
		return true;
	}
}