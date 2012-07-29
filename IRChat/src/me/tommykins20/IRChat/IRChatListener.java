package me.tommykins20.IRChat;

import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

public class IRChatListener implements Listener {
	
	private IRChat plugin;
	
	public IRChatListener(IRChat irc)
	{
		plugin = irc;
	}
	
	@EventHandler
	public void playerChatEvent(PlayerChatEvent event)
	{
		Player player = event.getPlayer();
		plugin.bot.sendMessage(plugin.getIRCChannel(), "["+player.getName()+"] "+event.getMessage());
	}
	
}
