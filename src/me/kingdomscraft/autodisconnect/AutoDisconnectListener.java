package me.kingdomscraft.autodisconnect;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class AutoDisconnectListener implements Listener{
	public static AutoDisconnect plugin;
	
	public AutoDisconnectListener(AutoDisconnect instance){
		plugin=instance;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		if(!e.getPlayer().isOp()&&plugin.getToggle()){
			e.getPlayer().kickPlayer(plugin.getKickMessage());
		}
	}
}
