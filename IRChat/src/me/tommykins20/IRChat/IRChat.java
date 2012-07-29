package me.tommykins20.IRChat;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.pircbotx.PircBotX;

public class IRChat extends JavaPlugin {
	
	public IRChatListener listener;
	public PircBotX bot;
	private Logger logger = Logger.getLogger("Minecraft");
	
	public void onEnable()
	{
		listener = new IRChatListener(this);
		bot = new PircBotX();
		getServer().getPluginManager().registerEvents(listener, this);
		String absfile = getDataFolder().getAbsolutePath();
		new File(absfile).mkdirs();
		if (!new File(absfile+File.separator+"config.yml").exists())
		{
			try {
				(new File(absfile+File.separator+"config.yml")).createNewFile();
				logMessage("This appears to be your first time using IRChat (or you deleted your config)! You can customise your config.yml in the IRChat" +
						"folder in the plugins directory to your likings.");
			} catch (IOException e) {
				e.printStackTrace();
			}
			saveDefaultConfig();
		}
		bot.setName(getIRCUsername());
		bot.setVerbose(false);
		try {
			bot.connect(getIRCServer(), getIRCPort());
			bot.joinChannel(getIRCChannel());
			bot.getListenerManager().addListener(new IRChatBotListener(this));
			logMessage("Successfully joined IRC server "+getIRCServer()+":"+getIRCPort()+" on channel "+getIRCChannel()+" using nickname "+getIRCUsername());
		} catch (Exception e) {
			logWarnMessage("Error when attempting to join IRC server...");
			e.printStackTrace();
		}
	}
	
	public void onDisable()
	{
		bot.disconnect();
		logMessage("Disconnected from current IRC server.");
	}
	
	/**
	 * Logs a message to the server console with this format "[IRChat] <message>"
	 * @param msg - Message to log
	 */
	public void logMessage(String msg)
	{
		logger.info("[IRChat] "+msg);
	}
	
	/**
	 * Logs a warning message to the server console with this format "[IRChat] <message>"
	 * @param msg - Message to log
	 */
	public void logWarnMessage(String msg)
	{
		logger.warning("[IRChat] "+msg);
	}
	
	/**
	 * Saves the config file
	 */
	@Override
	public void saveDefaultConfig()
	{
		super.saveDefaultConfig();
		getConfig().set("yourservername", "default");
		getConfig().set("username", "IRChatBot");
		getConfig().set("server", "irc.freenode.org");
		getConfig().set("port", 6667);
		getConfig().set("channel", "#pircbotx");
		getConfig().set("chatformat", "&2[IRC] &e%name%&f: %msg%");
		saveConfig();
	}
	
	/**
	 * Gets the server name the user specified
	 * @return Server name for IRC
	 */
	public String getServerName()
	{
		return getConfig().getString("yourservername");
	}
	
	/**
	 * Gets the username used for logging into IRC servers
	 * @return Username from config.yml
	 */
	public String getIRCUsername()
	{
		return getConfig().getString("username");
	}
	
	/** 
	 * Gets the IRC server to connect to
	 * @return IRC Server ip to connect to
	 */
	public String getIRCServer()
	{
		return getConfig().getString("server");
	}
	
	/**
	 * Gets the IRC Server port to connect to
	 * @return IRC Port to connect
	 */
	public int getIRCPort()
	{
		return getConfig().getInt("port");
	}
	
	/**
	 * Gets IRC channel to connect to
	 * @return IRC channwl to connect to
	 */
	public String getIRCChannel()
	{
		return getConfig().getString("channel");
	}
	
	/**
	 * Gets the specified chat format to broadcast IRC messages to players
	 * @return Specified chat format from config.yml
	 */
	public String getChatFormat()
	{
		return getConfig().getString("chatformat");
	}
	
	/**
	 * Broadcasts IRC message to server and log to console
	 * @param sender - Message sender
	 * @param message - Actual message
	 */
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
