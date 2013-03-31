package com.xorinc.nick;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import org.bukkit.entity.Player;

import com.earth2me.essentials.User;
import com.xorinc.nick.config.NickList;
import com.xorinc.nick.exception.OutOfNicksException;

public class NicknameManager {
	
	private NickList list;
	private HashMap<String, Boolean> isTaken = new HashMap<String, Boolean>();
	private HashMap<String, String> nameMap = new HashMap<String, String>();
	private NickNightPlugin plugin;
	private Random rand = new Random();
	
	public NicknameManager(NickNightPlugin plugin, NickList nicknames){
		list = nicknames;
		this.plugin = plugin;
		
		for(String nick : list.getNicks().get("vet"))
			isTaken.put(nick, false);
		for(String nick : list.getNicks().get("mod"))
			isTaken.put(nick, false);
		for(String nick : list.getNicks().get("admin"))
			isTaken.put(nick, false);
		for(String nick : list.getNicks().get("comic"))
			isTaken.put(nick, false);
	}
	
	public String getAdminNick(){
		ListIterator<String> iterator = list.getNicks().get("admin").listIterator();
		String current;
		while(iterator.hasNext()){
			current = iterator.next();
			if(isTaken.get(current))
				continue;
			return current;
		}
		return getModNick();
	}
	
	public String getModNick(){
		ListIterator<String> iterator = list.getNicks().get("mod").listIterator();
		String current;
		while(iterator.hasNext()){
			current = iterator.next();
			if(isTaken.get(current)) {
				continue;
			}
			return current;
		}
		return getVetNick();
	}
	
	public String getVetNick(){
		ListIterator<String> iterator = list.getNicks().get("vet").listIterator();
		String current;
		while(iterator.hasNext()){
			current = iterator.next();
			if(isTaken.get(current))
				continue;
			return current;
		}
		return getCommonNick();
	}
	
	public String getCommonNick(){
		List<String> commonList = list.getNicks().get("common");
		String current = commonList.get(rand.nextInt(commonList.size()-1));
		int totalNums = rand.nextInt(2) + 3;
		for(int i = 0; i < totalNums; i++){
			current += rand.nextInt(9);
		}
		return current;
	}
	
	public String getComicNick() throws OutOfNicksException{
		ListIterator<String> iterator = list.getNicks().get("comic").listIterator();
		String current;
		while(iterator.hasNext()){
			current = iterator.next();
			if(isTaken.get(current))
				continue;
			return current;
		}
		throw new OutOfNicksException("Out of comic nicks");
	}
	
	public void makeAvailable(String s){
		isTaken.put(s, false);
	}
	
	public void setNick(Player player, String nickname){
		
		if(!nameMap.containsValue(nickname)) {
			
			final User user = plugin.getEssentials().getUser(player);
		
			if (user == null || user.getNickname() == null)
				return;
		
			user.setNickname(nickname);
			nameMap.put(player.getName(), nickname);
			isTaken.put(nickname, true);
		}
	}
	
	public void unNick(Player player){
		
		if(nameMap.containsKey(player.getName())){
			final User user = plugin.getEssentials().getUser(player);
			if (user == null) return;
			user.setNickname(null);
			nameMap.remove(player.getName());
		}	
	}
	
	public void unNickAll(){
		Player[] players = plugin.getServer().getOnlinePlayers();
		for(Player player : players){
			unNick(player);
		}
	}
	
	public void setNickFromList(Player player){
		if(player.hasPermission("nicknight.type.comic")){
			try {
				setNick(player, getComicNick());
				return;
			} catch (OutOfNicksException e) {
			}
		}
		
		if(player.hasPermission("nicknight.type.admin"))
			setNick(player, getAdminNick());
		else if(player.hasPermission("nicknight.type.mod"))
			setNick(player, getModNick());
		else if(player.hasPermission("nicknight.type.vet"))
			setNick(player, getVetNick());
		else if(player.hasPermission("nicknight.type.common"))
			setNick(player, getCommonNick());	
	
	}
}
