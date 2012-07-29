package me.tommykins20.IRChat;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

public class IRChat extends JavaPlugin {
	
	public IRChatListener listener;
	public IRChatBot bot;
	private Logger logger = Logger.getLogger("Minecraft");
	
	public void onEnable()
	{
		listener = new IRChatListener(this);
		bot = new IRChatBot(this, getIRCUsername());
		getServer().getPluginManager().registerEvents(listener, this);
		String absfile = getDataFolder().getAbsolutePath();
		new File(absfile).mkdirs();
		if (!new File(absfile+File.separator+"config.yml").exists())
		{
			try {
				(new File(absfile+File.separator+"config.yml")).createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			saveDefaultConfig();
		}
		bot.setVerbose(false);
		try {
			bot.connect(getIRCServer(), getIRCPort());
			bot.joinChannel(getIRCChannel());
			logMessage("Successfully joined IRC server "+getIRCServer()+":"+getIRCPort()+" on channel "+getIRCChannel()+" using nickname "+getIRCUsername());
		} catch (Exception e) {
			String reason = "";
			if(e instanceof NickAlreadyInUseException)
				reason = "the selected nickname is already in use.";
			else
				if(e instanceof IrcException)
					reason = ((IrcException)e).toString();
			logWarnMessage("Error when attempting to join IRC server because "+reason);
			e.printStackTrace();
		}
	}
	
	public void onDisable()
	{
		bot.disconnect();
		logMessage("Disconnected from current IRC server.");
	}
	
	public void logMessage(String msg)
	{
		logger.info("[IRChat] "+msg);
	}
	
	public void logWarnMessage(String msg)
	{
		logger.warning("[IRChat] "+msg);
	}
	
	@Override
	public void saveDefaultConfig()
	{
		super.saveDefaultConfig();
		getConfig().set("yourservername", "default");
		getConfig().set("username", "IRChatBot");
		getConfig().set("server", "irc.esper.net");
		getConfig().set("port", 6667);
		getConfig().set("channel", "#bukkit");
		getConfig().set("chatformat", "&2[IRC] &e%name%&f: %msg%");
		saveConfig();
	}
	
	public String getServerName()
	{
		return getConfig().getString("yourservername");
	}
	
	public String getIRCUsername()
	{
		return getConfig().getString("username");
	}
	
	public String getIRCServer()
	{
		return getConfig().getString("server");
	}
	
	public int getIRCPort()
	{
		return getConfig().getInt("port");
	}
	
	public String getIRCChannel()
	{
		return getConfig().getString("channel");
	}
	
	public String getChatFormat()
	{
		return getConfig().getString("chatformat");
	}
	
	public void broadcastIRCMessage(String sender, String message)
	{
		for(Player online : getServer().getOnlinePlayers())
		{
			online.sendMessage(colorizeText(getChatFormat().replaceAll("%name%", sender).replaceAll("%msg%", message)));
		}
		logMessage("[IRC] <"+sender+"> "+message);
	}
	
	public String colorizeText(String string) {
	    string = string.replaceAll("&0", ChatColor.BLACK+"");
	    string = string.replaceAll("&1", ChatColor.DARK_BLUE+"");
	    string = string.replaceAll("&2", ChatColor.DARK_GREEN+"");
	    string = string.replaceAll("&3", ChatColor.DARK_AQUA+"");
	    string = string.replaceAll("&4", ChatColor.DARK_RED+"");
	    string = string.replaceAll("&5", ChatColor.DARK_PURPLE+"");
	    string = string.replaceAll("&6", ChatColor.GOLD+"");
	    string = string.replaceAll("&7", ChatColor.GRAY+"");
	    string = string.replaceAll("&8", ChatColor.DARK_GRAY+"");
	    string = string.replaceAll("&9", ChatColor.BLUE+"");
	    string = string.replaceAll("&a", ChatColor.GREEN+"");
	    string = string.replaceAll("&b", ChatColor.AQUA+"");
	    string = string.replaceAll("&c", ChatColor.RED+"");
	    string = string.replaceAll("&d", ChatColor.LIGHT_PURPLE+"");
	    string = string.replaceAll("&e", ChatColor.YELLOW+"");
	    string = string.replaceAll("&f", ChatColor.WHITE+"");
	    return string;
	}

}
