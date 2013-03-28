package com.xorinc.nick.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import com.xorinc.nick.NickNightPlugin;

public class GeneralConfig {
	
	private NickNightPlugin plugin;
    
	private final String CONFIG_NAME = "generalConfig.yml";

	public boolean DEBUG = false;
	
    @SuppressWarnings("unused")
	private String configVersion = "";

	
    public GeneralConfig(NickNightPlugin plugin) {
        this.plugin = plugin;
    }
    
    public void reload() {

        loadSettings();
    }
    
    public void loadSettings(){
    	
    	if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
    	
        File f = new File(plugin.getDataFolder(), CONFIG_NAME);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Unable to create " + CONFIG_NAME + "! No config options were loaded!");
                return;
            }
        }
        
        YamlConfiguration conf = new YamlConfiguration();
        conf.options().pathSeparator('/');
        conf.options().header(new StringBuilder().append(plugin.getDescription().getName() +  " configuration").append(System.getProperty("line.separator")).toString());
        
        try {
            conf.load(f);
        } catch (Exception e) {
            plugin.getLogger().severe("==================== " + plugin.getDescription().getName() + " ====================");
            plugin.getLogger().severe("Unable to load " + CONFIG_NAME);
            plugin.getLogger().severe("Check your config for formatting issues!");
            plugin.getLogger().severe("No config options were loaded!");
            plugin.getLogger().severe("Error: " + e.getMessage());
            plugin.getLogger().severe("====================================================");
            return;
        }
        
        if (!conf.contains("version"))
        	conf.set("version", 0.1);
        configVersion = conf.getString("version");
        
        try {
            conf.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
