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
	
	@EventHandler
	public void playerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		plugin.bot.sendMessage(plugin.getIRCChannel(), "["+player.getName()+"] joined "+plugin.getServerName());
	}
	
	@EventHandler
	public void playerLeave(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		plugin.bot.sendMessage(plugin.getIRCChannel(), "["+player.getName()+"] left "+plugin.getServerName());
	}
	
	@EventHandler
	public void playerKicked(PlayerKickEvent event)
	{
		Player player = event.getPlayer();
		plugin.bot.sendMessage(plugin.getIRCChannel(), "["+player.getName()+"] had been kicked off "+plugin.getServerName());
	}
	
}
