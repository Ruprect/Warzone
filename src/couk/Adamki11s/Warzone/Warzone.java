package couk.Adamki11s.Warzone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.martin.bukkit.npclib.NPCManager;

import couk.Adamki11s.Commands.WarzoneCommands;
import couk.Adamki11s.Database.Initialise;
import couk.Adamki11s.Database.QueryPooler;
import couk.Adamki11s.Database.QuitterHandler;
import couk.Adamki11s.Games.ASCENSION_GD;
import couk.Adamki11s.Games.BLIZZARD_GD;
import couk.Adamki11s.Games.CASTLE_GD;
import couk.Adamki11s.Games.DUNGEON_GD;
import couk.Adamki11s.Games.Gamedata;
import couk.Adamki11s.Games.JUNGLE_GD;
import couk.Adamki11s.Games.OVERFLOW_GD;
import couk.Adamki11s.Games.PLAINES_GD;
import couk.Adamki11s.Games.TOMB_GD;
import couk.Adamki11s.Maps.ASCENSION;
import couk.Adamki11s.Maps.BLIZZARD;
import couk.Adamki11s.Maps.CASTLE;
import couk.Adamki11s.Maps.DUNGEON;
import couk.Adamki11s.Maps.JUNGLE;
import couk.Adamki11s.Maps.Map;
import couk.Adamki11s.Maps.Maps;
import couk.Adamki11s.Maps.OVERFLOW;
import couk.Adamki11s.Maps.PLAINES;
import couk.Adamki11s.Maps.TOMB;
import couk.Adamki11s.NPC.NPC_Control;
import couk.Adamki11s.NPC.NPC_Handler;

public class Warzone extends JavaPlugin {
	
	public static QueryPooler queryPooler = new QueryPooler();
	public static QuitterHandler quitterHandle = new QuitterHandler();
	public static NPC_Handler npc_handle;
	
	public static Server server;
	public static Plugin plugin;
	public static JavaPlugin jp;
	public static String prefix = "[Warzone]",
	                     version;
	public static Logger log = Logger.getLogger("Minecraft");
	
	public static HashMap<MapName, Map> mapList = new HashMap<MapName, Map>();
	public static HashMap<MapData, Gamedata> mapData = new HashMap<MapData, Gamedata>();
	
	public static NPCManager npcManage;
	public static NPC_Control npc;

	@Override
	public void onDisable() {
		server.getScheduler().cancelTasks(plugin);
		npc_handle.despawnNPCS();
		log.info(prefix + " Warzone, version " + version + " disabled successfully!");  
	}
	
	Maps mapsClass = new Maps();

	@Override
	public void onEnable() {
		version = this.getDescription().getVersion();
		plugin = this;
		jp = this;
		npcManage = new NPCManager(this);
		server = getServer();
		queryPooler.poolQuerys();
		PluginManager pm = server.getPluginManager();
		pm.registerEvent(Type.CREATURE_SPAWN, new WarzoneCreatureSpawnEvent(), Event.Priority.Highest, this);
		pm.registerEvent(Type.ENTITY_DAMAGE, new WarzoneEntityListener(), Priority.Normal, this);
		pm.registerEvent(Type.PLAYER_JOIN, new WarzonePlayerListener(), Priority.Normal, this);
		pm.registerEvent(Type.PLAYER_INTERACT, new WarzonePlayerListener(), Priority.Normal, this);
		pm.registerEvent(Type.PLAYER_QUIT, new WarzonePlayerListener(), Priority.Normal, this);
		pm.registerEvent(Type.PLAYER_DROP_ITEM, new WarzonePlayerListener(), Priority.Normal, this);
		pm.registerEvent(Type.PLAYER_TOGGLE_SNEAK, new WarzonePlayerListener(), Priority.Highest, this);
		pm.registerEvent(Type.PLAYER_PORTAL, new WarzonePlayerListener(), Priority.Highest, this);
		pm.registerEvent(Type.BLOCK_BREAK, new WarzoneBlockListener(), Priority.Highest, this);
		pm.registerEvent(Type.BLOCK_PLACE, new WarzoneBlockListener(), Priority.Highest, this);
		new Initialise().init();
		mapsClass.initi();
		loadMaps();
		loadData();
		getCommand("warzone").setExecutor(new WarzoneCommands());
		if(Maps.worldFound){
			log.info(prefix + " Warzone, version " + version + " enabled successfully!");
		}
		npc = new NPC_Control(this, npcManage);
		npc_handle = new NPC_Handler(npcManage, npc);
		npc_handle.enableNPCAIController();
	}
	
	public void loadMaps() {
		mapList.put(MapName.ASCENSION, new ASCENSION());
		mapList.put(MapName.CASTLE, new CASTLE());
		mapList.put(MapName.DUNGEON, new DUNGEON());
		mapList.put(MapName.OVERFLOW, new OVERFLOW());
		mapList.put(MapName.PLAINES, new PLAINES());
		mapList.put(MapName.TOMB, new TOMB());
		mapList.put(MapName.BLIZZARD, new BLIZZARD());
		mapList.put(MapName.JUNGLE, new JUNGLE());
	}
	
	public void loadData(){
		mapData.put(MapData.ASCENSION, new ASCENSION_GD());
		mapData.put(MapData.CASTLE, new CASTLE_GD());
		mapData.put(MapData.DUNGEON, new DUNGEON_GD());
		mapData.put(MapData.OVERFLOW, new OVERFLOW_GD());
		mapData.put(MapData.PLAINES, new PLAINES_GD());
		mapData.put(MapData.TOMB, new TOMB_GD());
		mapData.put(MapData.BLIZZARD, new BLIZZARD_GD());
		mapData.put(MapData.JUNGLE, new JUNGLE_GD());
	}
	
	public static enum GameType{
		RANKED,
		SOCIAL;
		
		@Override
		public String toString(){
			char first = super.toString().charAt(0);
			String rest = super.toString().substring(1, super.toString().length()).toLowerCase();
			return first + rest;
		}
	}
	
	public static enum MapData {
		ASCENSION,
		CASTLE,
		DUNGEON,
		OVERFLOW,
		PLAINES,
		TOMB,
		JUNGLE,
		BLIZZARD;
		
		@Override
		public String toString() {
			String result = super.toString();
			return result.toUpperCase();
		}
	}
	
	public static enum MapName {
		ASCENSION,
		CASTLE,
		DUNGEON,
		OVERFLOW,
		PLAINES,
		TOMB,
		JUNGLE,
		BLIZZARD;
		
		@Override
		public String toString() {
			String result = super.toString();
			return result.toUpperCase();
		}
	}
	
}
