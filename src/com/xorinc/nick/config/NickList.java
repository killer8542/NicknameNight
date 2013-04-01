package com.xorinc.nick.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import com.xorinc.nick.NickNightPlugin;
import com.xorinc.nick.exception.NoSuchNickListException;

public class NickList {
	
	private NickNightPlugin plugin;
    
	private String configName;

	public boolean DEBUG = false;
	
	private HashMap<String, List<String>> nicknames = new HashMap<String, List<String>>();
	private final List<String> DEFAULT_NAME = new ArrayList<String>();
	
    public NickList(NickNightPlugin plugin, String configName){
        this.plugin = plugin;
        this.configName = configName + ".yml";
        DEFAULT_NAME.add("I_AM_ERROR");
    }
    
    public void loadSettings() throws NoSuchNickListException{
    	
		if (!plugin.getDataFolder().exists()) {
		    plugin.getDataFolder().mkdirs();
		}
		
		if (!(new File(plugin.getDataFolder().getPath() + "/nicklists")).exists()) {
			new File(plugin.getDataFolder().getPath() + "/nicklists").mkdirs();
		}

    	File f = new File(plugin.getDataFolder().getPath() + "/nicklists/" + configName);
        if (!f.exists()) {
            throw new NoSuchNickListException("Nickname list " + configName + " does not exist.");
        }

        YamlConfiguration conf = new YamlConfiguration();
        conf.options().pathSeparator('/');
        conf.options().header(new StringBuilder().append(plugin.getDescription().getName() +  " configuration").append(System.getProperty("line.separator")).toString());
        
        try {
            conf.load(f);
        } catch (Exception e) {
            plugin.getLogger().severe("==================== " + plugin.getDescription().getName() + " ====================");
            plugin.getLogger().severe("Unable to load " + configName);
            plugin.getLogger().severe("Check your config for formatting issues!");
            plugin.getLogger().severe("No config options were loaded!");
            plugin.getLogger().severe("Error: " + e.getMessage());
            plugin.getLogger().severe("====================================================");
            return;
        }
        
        if (!conf.contains("Common"))
        	conf.set("Common", DEFAULT_NAME);
        nicknames.put("common", conf.getStringList("Common"));
        
        if (!conf.contains("Vet"))
        	conf.set("Vet", DEFAULT_NAME);
        nicknames.put("vet", conf.getStringList("Vet"));
        
        if (!conf.contains("Mod"))
        	conf.set("Mod", DEFAULT_NAME);
        nicknames.put("mod", conf.getStringList("Mod"));       
        
        if (!conf.contains("Admin"))
        	conf.set("Admin", DEFAULT_NAME);
        nicknames.put("admin", conf.getStringList("Admin"));
        
        if (!conf.contains("Comic"))
        	conf.set("Comic", DEFAULT_NAME);
        nicknames.put("comic", conf.getStringList("Comic"));
        
        try {
            conf.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	public HashMap<String, List<String>> getNicks(){
    	return new HashMap<String, List<String>>(nicknames);
    }
}


