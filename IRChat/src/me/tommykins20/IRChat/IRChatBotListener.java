package me.tommykins20.IRChat;

import org.pircbotx.hooks.*;
import org.pircbotx.hooks.events.MessageEvent;

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

}
