package me.tommykins20.IRChat;

import org.pircbotx.hooks.*;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.QuitEvent;

@SuppressWarnings("rawtypes")
public class IRChatBotListener extends ListenerAdapter implements Listener {
	
	private IRChat plugin;
	
	public IRChatBotListener(IRChat irchat)
	{
		plugin = irchat;
	}
	
	@Override
	public void onMessage(MessageEvent event) throws Exception
	{
		String message = event.getMessage();
		String senderName = event.getUser().getNick();
		plugin.broadcastIRCMessage(senderName, message);
	}
	
	@Override
	public void onEvent(Event event) throws Exception
	{
		if(event instanceof JoinEvent)
		{
			JoinEvent je = (JoinEvent) event;
			String playername = je.getUser().getNick();
			plugin.broadcastDifferentIRCMessage(playername+" has joined the IRC channel.");
		}else
			if(event instanceof QuitEvent)
			{
				QuitEvent qe = (QuitEvent) event;
				String playername = qe.getUser().getNick();
				plugin.broadcastDifferentIRCMessage(playername+" has left the IRC channel.");
			}
	}

}
