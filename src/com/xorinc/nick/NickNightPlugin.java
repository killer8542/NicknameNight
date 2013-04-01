package com.xorinc.nick;

import java.io.File;


import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.Essentials;
import com.xorinc.nick.command.NickCommand;
import com.xorinc.nick.config.GeneralConfig;
import com.xorinc.nick.listener.LoginListener;


public class NickNightPlugin extends JavaPlugin {
	
	private GeneralConfig config;
	private boolean isNickNight = false;
	private NicknameManager manager;
	private Essentials ess;
	
	@Override
	public void onEnable() {
		if(!hookEssentials()){
			setEnabled(false);
			return;
		}
		
		//config = new GeneralConfig(this);
		//config.loadSettings();
		
		getCommand("nicknight").setExecutor(new NickCommand(this));
		getServer().getPluginManager().registerEvents(new LoginListener(this), this);
		
		if (!(new File(getDataFolder().getPath() + "/nicklists")).exists()) {
			new File(getDataFolder().getPath() + "/nicklists").mkdirs();
		}
		
	}
	
	public void reload() {
	

	}

	@Override
	public void onDisable() {
		isNickNight = false;
		manager.unNickAll();
		
	}
	
	public boolean hookEssentials(){
		Plugin test = 	getServer().getPluginManager().getPlugin("Essentials");
        if (test == null) {
        	getLogger().severe("Could not find Essentials!");
            return false;       
        }
        
        ess = (Essentials) test;
        getLogger().info("Successfully linked with Essentials!");
        return test.isEnabled();
	}
	
	public boolean isNickNight(){
		return isNickNight;
	}
	
	public Essentials getEssentials() {
		return ess;
	}
	
	public void setManager(NicknameManager manager){
		if(!isNickNight)
			this.manager = manager;
	}

	public GeneralConfig getPluginConfig() {
		return config;
	}


	public NicknameManager getManager() {
		return manager;
	}

	public void setNickNight(boolean isNickNight) {
		this.isNickNight = isNickNight;
	}
	
	

	
}
