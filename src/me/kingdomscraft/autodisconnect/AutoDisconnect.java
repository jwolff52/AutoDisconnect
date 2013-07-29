package me.kingdomscraft.autodisconnect;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AutoDisconnect extends JavaPlugin{
public final Logger logger = Logger.getLogger("Minecraft");
	
	public static AutoDisconnect plugin;
	
	public AutoDisconnectListener listener=new AutoDisconnectListener(this);
	
	public static FileConfiguration config;
	public static File cfile;
	
	public static Permissions perms;

	private static String kickMessage;
	private String version;
	private boolean toggle;
	
	@Override
	public void onEnable() {
		PluginManager pm=getServer().getPluginManager();
		version="1.2";
		perms=new Permissions();
		pm.addPermission(perms.canPreformToggle);
		pm.registerEvents(listener, this);
		config=getConfig();
		config.options().copyDefaults(true);
		saveConfig();
		setKickMessage(config.getString("on_kick_message"));
		setToggle(false);
		cfile=new File(getDataFolder(), "config.yml");
		logger.info("AutoDisconnect Version: "+version+" has been enabled!");
	}

	@Override
	public void onDisable(){
		PluginManager pm=getServer().getPluginManager();
		pm.removePermission(perms.canPreformToggle);
		logger.info("AutoDisconnect has been disabled!");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player){
			Player player=(Player)sender;
			if(label.equalsIgnoreCase("ad")){
				if(args.length==1){
					if(args[0].equalsIgnoreCase("toggle")){
						String state="off";
						if(toggle){
							toggle=false;
						}else{
							toggle=true;
							state="on";
							for(Player p:Bukkit.getOnlinePlayers()){
								if(!p.isOp()){
									p.kickPlayer(kickMessage);
								}
							}
						}
						player.sendMessage(ChatColor.AQUA+"AutoDisconnect was turned "+state+ChatColor.AQUA+"!");
					}
				}
			}
		}
		return false;
	}
	public String getKickMessage() {
		return kickMessage;
	}

	public static void setKickMessage(String kickMessage) {
		AutoDisconnect.kickMessage = kickMessage;
	}

	public boolean getToggle() {
		return toggle;
	}

	public void setToggle(boolean toggle) {
		this.toggle = toggle;
	}
}
