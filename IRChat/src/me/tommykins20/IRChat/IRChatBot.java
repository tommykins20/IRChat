package me.tommykins20.IRChat;

import org.jibble.pircbot.PircBot;

public class IRChatBot extends PircBot {
	
	private IRChat plugin;
	
	public IRChatBot(IRChat irchat, String username)
	{
		super.setName(username);
		plugin = irchat;
	}
	
	public void onMessage(String channel, String sender, String login, String hostname, String message)
	{
		plugin.broadcastIRCMessage(login, message);
	}

}
