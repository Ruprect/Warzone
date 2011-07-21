package couk.Adamki11s.Lobby;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import couk.Adamki11s.Extras.Colour.ExtrasColour;
import couk.Adamki11s.Extras.Inventory.ExtrasInventory;
import couk.Adamki11s.Extras.Random.ExtrasRandom;
import couk.Adamki11s.Games.Gamedata;
import couk.Adamki11s.Maps.Map;
import couk.Adamki11s.Maps.Maps;
import couk.Adamki11s.Warzone.Warzone;
import couk.Adamki11s.Warzone.Warzone.GameType;
import couk.Adamki11s.Warzone.Warzone.MapData;
import couk.Adamki11s.Warzone.Warzone.MapName;

public class Pool {

	private static ArrayList<Player> pool = new ArrayList<Player>();

	private static ArrayList<Player> searchers = new ArrayList<Player>();
	private static HashMap<Integer, Player> searcherPlayer = new HashMap<Integer, Player>();
	private static HashMap<Player, GameType> gameType = new HashMap<Player, GameType>();
	private static HashMap<Player, Integer> timeoutCount = new HashMap<Player, Integer>();
	private static ArrayList<Player> toRemove = new ArrayList<Player>();
	
	ExtrasInventory inventoryManage = new ExtrasInventory();
	public static HashMap<Player, ItemStack[]> savedInventory = new HashMap<Player, ItemStack[]>();

	private ExtrasRandom rand = new ExtrasRandom();
	private ExtrasColour col = new ExtrasColour();

	public void addToPool(Player p){
		pool.add(p);
	}

	public void removeFromPool(Player p){
		pool.remove(p);
	}

	public int getPoolSize(){
		return pool.size();
	}

	private int lobbyTaskID = 463965923, index, toc;
	private Player searcher;

	Player p;
	int output, gamePoolerID = 74725486;
	boolean found;
	boolean broken = false;
	
	public static HashMap<Player, Location> locs = new HashMap<Player, Location>();
	
	public static HashMap<Player, Boolean> chilledInLobby = new HashMap<Player, Boolean>();
	
	Player chiller;
	
	public void findMatch(Player play, GameType ranked){
		chiller = play;
		chilledInLobby.put(play, false);
		play.sendMessage(ChatColor.RED + "[Warzone] " + ChatColor.BLUE + "Chill in the lobby while we find a match for you.");
		locs.put(play, play.getLocation());
		
		play.teleport(new Location(Maps.Warzone_World, -100, 76, 200,  (float)1.7, (float)-0.14));
		if(!searchers.contains(play)){
			searchers.add(play);
			searcherPlayer.put(searchers.size(), play);
			gameType.put(play, ranked);
			timeoutCount.put(play, 0);
			col.sendColouredMessage(play, ("&red[Warzone] &green" + ranked.toString() + " : Searching for players..."));
		} else {
			col.sendColouredMessage(play, "&red[Warzone] You are already searching! ");
			col.sendColouredMessage(play, "&red[Warzone] Do &e/warzone stop-search &redto cancel");
		}
		if(toRemove.contains(play)){
			toRemove.remove(play);
		}
		if(!Warzone.server.getScheduler().isCurrentlyRunning(lobbyTaskID)){
			lobbyTaskID = Warzone.server.getScheduler().scheduleSyncRepeatingTask(Warzone.plugin, new Runnable() {	
				public void run() {
					String type = "";
					index = 0;
					if(searchers.size() > 0){
						for(Player p : searchers){
							type = gameType.get(p).toString();
							broken = false;
							index++;
							toc = timeoutCount.get(p) + 1;
							timeoutCount.put(p, toc);
							if(toc >= 120){
								broken = true;
								if(!toRemove.contains(p)){
									toRemove.add(p);
									col.sendColouredMessage(searcherPlayer.get(index), "&red[Warzone]&e " + type + " :&red No players found, request timed out.");
									p.teleport(locs.get(p));
								}
								if(searchers.size() == 1){
									break;
								}
							}
							if(((toc % 15) == 0) && !broken){
								searcher = searcherPlayer.get(index);
								col.sendColouredMessage(searcher, ("&red[Warzone] &green" + type + " : Searching for players..."));
							}
						}	
						
						Warzone.server.getScheduler().scheduleSyncDelayedTask(Warzone.plugin, new Runnable() {	

							public void run() {

								matchmaking(searchers);	
								chilledInLobby(chiller);

							}

						}, 15 * 20L);
						
					} else {
						
						Warzone.server.getScheduler().cancelTask(lobbyTaskID);//Closes the thread if nobody is searching.
						
					}
					
					for(Player p : toRemove){
							searchers.remove(p);
							gameType.remove(p);
							timeoutCount.remove(p);
							locs.remove(p);
						}
					
				}

			}, 20L, 20L);//First variable is delay, second is repeat period
		}
	}
	
	private void chilledInLobby(Player chill){
		chilledInLobby.put(chill, true);
	}

	public static Map map;
	public static Gamedata gd;
	ExtrasInventory inventmanage = new ExtrasInventory();
	
	public void matchmaking(ArrayList<Player> player_pool){
		int firstPool = 0, secondPool = 0;
		if(player_pool.size() < 2){
			return;
		} else {
			firstPool = rand.getRandomInt(player_pool.size(), 0);
			secondPool = firstPool;
			while(firstPool == secondPool){
				secondPool = rand.getRandomInt(player_pool.size(), 0);
			}
			if(gameType.get(player_pool.get(firstPool)) != gameType.get(player_pool.get(secondPool))){
				return;
			}
		}
		Player first = searcherPlayer.get(firstPool + 1),
		second = searcherPlayer.get(secondPool + 1);
		if(chilledInLobby.get(first) && chilledInLobby.get(second)){
			String type = gameType.get(first).toString();
			col.sendColouredMessage(first, "&red[Warzone] &green" + type + " match found with &9" + second.getName() + "&green!");
			col.sendColouredMessage(second, "&red[Warzone] &green" + type + " match found with &9" + first.getName() + "&green!");
			col.sendColouredMessage(first, "&red[Warzone] &aSelecting random map...");
			col.sendColouredMessage(second, "&red[Warzone] &aSelecting random map...");
	
			if(Warzone.mapList.get(Warzone.MapName.ASCENSION).isOccupied() && Warzone.mapList.get(Warzone.MapName.CASTLE).isOccupied() && Warzone.mapList.get(Warzone.MapName.DUNGEON).isOccupied() &&
					Warzone.mapList.get(Warzone.MapName.OVERFLOW).isOccupied() && Warzone.mapList.get(Warzone.MapName.PLAINES).isOccupied() && Warzone.mapList.get(Warzone.MapName.TOMB).isOccupied()
					 && Warzone.mapList.get(Warzone.MapName.JUNGLE).isOccupied() && Warzone.mapList.get(Warzone.MapName.BLIZZARD).isOccupied()){
				col.sendColouredMessage(first, "&red[Warzone] All maps are occupied! Please wait.");
				col.sendColouredMessage(second, "&red[Warzone] All maps are occupied! Please wait.");
				searchers.remove(first);
				searchers.remove(second);
				return;
			} else {
				boolean validmap = false;
				while(!validmap){
					int rand = new ExtrasRandom().getRandomInt(8, 0);
					switch(rand){
						case 0: map = Warzone.mapList.get(MapName.ASCENSION); break;
						case 1: map = Warzone.mapList.get(MapName.CASTLE); break;
						case 2: map = Warzone.mapList.get(MapName.DUNGEON); break;
						case 3: map = Warzone.mapList.get(MapName.OVERFLOW); break;
						case 4: map = Warzone.mapList.get(MapName.PLAINES); break;
						case 5: map = Warzone.mapList.get(MapName.TOMB); break;
						case 6: map = Warzone.mapList.get(MapName.JUNGLE); break;
						case 7: map = Warzone.mapList.get(MapName.BLIZZARD); break;
					}
					if(!map.isOccupied()){
						validmap = true;
					}
				}
				col.sendColouredMessage(first, "&red[Warzone] &aMap Chosen : &9" + map.getName() + ".&a Loading map...");
				col.sendColouredMessage(second, "&red[Warzone] &aMap Chosen : &9" + map.getName() + ".&a Loading map...");
				searchers.remove(first);
				searchers.remove(second);
				
				if(map.getName().toUpperCase().equals("ASCENSION")){
					gd = Warzone.mapData.get(MapData.ASCENSION);
				} else if(map.getName().toUpperCase().equals("CASTLE")){
					gd = Warzone.mapData.get(MapData.CASTLE);
				} else if(map.getName().toUpperCase().equals("DUNGEON")){
					gd = Warzone.mapData.get(MapData.DUNGEON);
				}else if(map.getName().toUpperCase().equals("OVERFLOW")){
					gd = Warzone.mapData.get(MapData.OVERFLOW);
				}else if(map.getName().toUpperCase().equals("PLAINES")){
					gd = Warzone.mapData.get(MapData.PLAINES);
				}else if(map.getName().toUpperCase().equals("TOMB")){
					gd = Warzone.mapData.get(MapData.TOMB);
				}else if(map.getName().toUpperCase().equals("JUNGLE")){
					gd = Warzone.mapData.get(MapData.JUNGLE);
				}else if(map.getName().toUpperCase().equals("BLIZZARD")){
					gd = Warzone.mapData.get(MapData.BLIZZARD);
				}
				
				ArrayList<Player> l = new ArrayList<Player>();
				l.add(first); l.add(second);
				gd.initiateGame(l);
				gd.passGameType(gameType.get(first));
				gd.addParticipant(first);
				gd.addParticipant(second);
				
				if(first.getWorld() == Maps.Warzone_World){
					first.teleport(map.getSpawnPoints().get(0));
				}
				if(second.getWorld() == Maps.Warzone_World){
					second.teleport(map.getSpawnPoints().get(1));
				}
				
				first.teleport(map.getSpawnPoints().get(0));
				second.teleport(map.getSpawnPoints().get(1));
				
				col.sendColouredMessage(first, "&red[Warzone] &aMap Loaded!&4 Let battle commence!");
				col.sendColouredMessage(second, "&red[Warzone] &aMap Loaded!&4 Let battle commence!");
				
				inventoryManage.wipeInventory(first);
				inventoryManage.wipeInventory(second);
				
				first.getInventory().addItem(new ItemStack(261, 1));
				inventmanage.addToInventory(first, 262, 64);
				inventmanage.addToInventory(first, 262, 64);
				inventmanage.addToInventory(first, 262, 64);
				inventmanage.addToInventory(first, 263, 10);
				
				second.getInventory().addItem(new ItemStack(261, 1));
				inventmanage.addToInventory(second, 262, 64);
				inventmanage.addToInventory(second, 262, 64);
				inventmanage.addToInventory(second, 262, 64);
				inventmanage.addToInventory(second, 263, 10);
				
				gd.initiateScheduler();	
				
			}
			
			
		}

	}

}
