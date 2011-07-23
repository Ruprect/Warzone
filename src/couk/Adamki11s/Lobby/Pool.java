package couk.Adamki11s.Lobby;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import couk.Adamki11s.Database.Statistics;
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

	public static ArrayList<Player> searchers = new ArrayList<Player>();
	private static HashMap<Integer, Player> searcherPlayer = new HashMap<Integer, Player>();
	public static HashMap<Player, GameType> gameType = new HashMap<Player, GameType>();
	public static HashMap<Player, Integer> timeoutCount = new HashMap<Player, Integer>();
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
	
	public static int timeout_Count = 120, sendSearchMessageEvery_XSeconds = 15;
	public static boolean showTOC = false;
	
	Player chiller;
	
	public void findMatch(Player play, GameType ranked){		
		play.teleport(new Location(Maps.Warzone_World, -100, 76, 200,  (float)1.7, (float)-0.14));
		if(!searchers.contains(play)){
			searchers.add(play);
			searcherPlayer.put(searchers.size(), play);
			gameType.put(play, ranked);
			timeoutCount.put(play, 0);
			chiller = play;
			chilledInLobby.put(play, false);
			play.sendMessage(ChatColor.RED + "[Warzone] " + ChatColor.BLUE + "Chill in the lobby while we find a match for you.");
			locs.put(play, play.getLocation());
			if(showTOC){
				col.sendColouredMessage(play, ("&red[Warzone] &green" + ranked.toString() + " : Searching for players..."));
			} else {
				col.sendColouredMessage(play, ("&red[Warzone] &green" + ranked.toString() + " : Searching for players..."));
			}
		} else {
			col.sendColouredMessage(play, "&red[Warzone] You are already searching! ");
			col.sendColouredMessage(play, "&red[Warzone] Do &e/warzone stop search &redto cancel");
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
							if(toc >= timeout_Count){
								broken = true;
								if(!toRemove.contains(p)){
									toRemove.add(p);
									col.sendColouredMessage(p, "&red[Warzone]&e " + type + " :&red No players found, request timed out.");
									p.teleport(locs.get(p));
									break;
								}
							}
							if(((toc % sendSearchMessageEvery_XSeconds) == 0) && !broken){
								searcher = searcherPlayer.get(index);
								if(showTOC){
									col.sendColouredMessage(searcher, ("&red[Warzone] &green" + type + " : Searching for players... &redTimeout in : &e" + (timeout_Count - timeoutCount.get(searcher))));
								} else {
									col.sendColouredMessage(searcher, ("&red[Warzone] &green" + type + " : Searching for players..."));
								}
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
			col.sendColouredMessage(first, "&red[Warzone] &green" + type + " match found with &9" + second.getName() + " - " + Statistics.levelTitles.get(Statistics.playerLevel.get(second.getName())) + " (" +
					Statistics.playerLevel.get(second.getName()) + ")");
			col.sendColouredMessage(second, "&red[Warzone] &green" + type + " match found with &9" + first.getName() + " - " + Statistics.levelTitles.get(Statistics.playerLevel.get(first.getName())) + " (" +
					Statistics.playerLevel.get(first.getName()) + ")");
			col.sendColouredMessage(first, "&red[Warzone] &aSelecting random map...");
			col.sendColouredMessage(second, "&red[Warzone] &aSelecting random map...");
	
			if(Warzone.mapList.get(Warzone.MapName.ASCENSION).isOccupied() && Warzone.mapList.get(Warzone.MapName.CASTLE).isOccupied() && Warzone.mapList.get(Warzone.MapName.DUNGEON).isOccupied() &&
					Warzone.mapList.get(Warzone.MapName.OVERFLOW).isOccupied() && Warzone.mapList.get(Warzone.MapName.PLAINES).isOccupied() && Warzone.mapList.get(Warzone.MapName.TOMB).isOccupied()
					 && Warzone.mapList.get(Warzone.MapName.JUNGLE).isOccupied() && Warzone.mapList.get(Warzone.MapName.BLIZZARD).isOccupied()  && Warzone.mapList.get(Warzone.MapName.BLIND).isOccupied()
					 && Warzone.mapList.get(Warzone.MapName.CONTAINMENT).isOccupied() && Warzone.mapList.get(Warzone.MapName.AFTERMATH).isOccupied() && Warzone.mapList.get(Warzone.MapName.CRYPT).isOccupied()
					 && Warzone.mapList.get(Warzone.MapName.HOMETREE).isOccupied() && Warzone.mapList.get(Warzone.MapName.AURORA).isOccupied() && Warzone.mapList.get(Warzone.MapName.ABYSS).isOccupied()
					 && Warzone.mapList.get(Warzone.MapName.BURROW).isOccupied() && Warzone.mapList.get(Warzone.MapName.LAPUTA).isOccupied() && Warzone.mapList.get(Warzone.MapName.DOME).isOccupied()
					 && Warzone.mapList.get(Warzone.MapName.NUKETOWN).isOccupied()){
				col.sendColouredMessage(first, "&red[Warzone] All maps are occupied! Please wait.");
				col.sendColouredMessage(second, "&red[Warzone] All maps are occupied! Please wait.");
				searchers.remove(first);
				searchers.remove(second);
				return;
			} else {
				boolean validmap = false;
				while(!validmap){
					int rand = new ExtrasRandom().getRandomInt(20, 0);
					switch(rand){
						case 0: map = Warzone.mapList.get(MapName.ASCENSION); break;
						case 1: map = Warzone.mapList.get(MapName.CASTLE); break;
						case 2: map = Warzone.mapList.get(MapName.DUNGEON); break;
						case 3: map = Warzone.mapList.get(MapName.OVERFLOW); break;
						case 4: map = Warzone.mapList.get(MapName.PLAINES); break;
						case 5: map = Warzone.mapList.get(MapName.TOMB); break;
						case 6: map = Warzone.mapList.get(MapName.JUNGLE); break;
						case 8: map = Warzone.mapList.get(MapName.BLIZZARD); break;
						case 9: map = Warzone.mapList.get(MapName.BLIND); break;
						case 10: map = Warzone.mapList.get(MapName.CONTAINMENT); break;
						case 11: map = Warzone.mapList.get(MapName.AFTERMATH); break;
						case 12: map = Warzone.mapList.get(MapName.CRYPT); break;
						case 13: map = Warzone.mapList.get(MapName.HOMETREE); break;
						case 14: map = Warzone.mapList.get(MapName.AURORA); break;
						case 15: map = Warzone.mapList.get(MapName.ABYSS); break;
						case 16: map = Warzone.mapList.get(MapName.BURROW); break;
						case 17: map = Warzone.mapList.get(MapName.LAPUTA); break;
						case 18: map = Warzone.mapList.get(MapName.DOME); break;
						case 19: map = Warzone.mapList.get(MapName.NUKETOWN); break;
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
				}else if(map.getName().toUpperCase().equals("BLIND")){
					gd = Warzone.mapData.get(MapData.BLIND);
				}else if(map.getName().toUpperCase().equals("CONTAINMENT")){
					gd = Warzone.mapData.get(MapData.CONTAINMENT);
				}else if(map.getName().toUpperCase().equals("AFTERMATH")){
					gd = Warzone.mapData.get(MapData.AFTERMATH);
				}else if(map.getName().toUpperCase().equals("CRYPT")){
					gd = Warzone.mapData.get(MapData.CRYPT);
				}else if(map.getName().toUpperCase().equals("HOMETREE")){
					gd = Warzone.mapData.get(MapData.HOMETREE);
				}else if(map.getName().toUpperCase().equals("AURORA")){
					gd = Warzone.mapData.get(MapData.AURORA);
				}else if(map.getName().toUpperCase().equals("ABYSS")){
					gd = Warzone.mapData.get(MapData.ABYSS);
				}else if(map.getName().toUpperCase().equals("BURROW")){
					gd = Warzone.mapData.get(MapData.BURROW);
				}else if(map.getName().toUpperCase().equals("LAPUTA")){
					gd = Warzone.mapData.get(MapData.LAPUTA);
				}else if(map.getName().toUpperCase().equals("DOME")){
					gd = Warzone.mapData.get(MapData.DOME);
				}else if(map.getName().toUpperCase().equals("NUKETOWN")){
					gd = Warzone.mapData.get(MapData.NUKETOWN);
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
				inventmanage.addToInventory(first, 263, 5);
				
				second.getInventory().addItem(new ItemStack(261, 1));
				inventmanage.addToInventory(second, 262, 64);
				inventmanage.addToInventory(second, 263, 5);
				
				gd.initiateScheduler();	
				
				/*switch(Preferences.armourType.get(first.getName())){
				case NONE: break;
				case LEATHER:
				first.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET, 1));
				first.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
				first.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS, 1));
				first.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS, 1));
				break;
				case IRON:
				first.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET, 1));
				first.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
				first.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
				first.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS, 1));
				break;
				case GOLD:
				first.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET, 1));
				first.getInventory().setChestplate(new ItemStack(Material.GOLD_CHESTPLATE, 1));
				first.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS, 1));
				first.getInventory().setBoots(new ItemStack(Material.GOLD_BOOTS, 1));
				break;
				case DIAMOND:
				first.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET, 1));
				first.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
				first.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS, 1));
				first.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1));
				break;
				}
				
				switch(Preferences.armourType.get(second.getName())){
				case NONE: break;
				case LEATHER:
				second.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET, 1));
				second.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
				second.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS, 1));
				second.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS, 1));
				break;
				case IRON:
				second.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET, 1));
				second.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
				second.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
				second.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS, 1));
				break;
				case GOLD:
				second.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET, 1));
				second.getInventory().setChestplate(new ItemStack(Material.GOLD_CHESTPLATE, 1));
				second.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS, 1));
				second.getInventory().setBoots(new ItemStack(Material.GOLD_BOOTS, 1));
				break;
				case DIAMOND:
				second.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET, 1));
				second.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
				second.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS, 1));
				second.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1));
				break;
				}
				
				if(Preferences.blockHead.get(first.getName()).getType() != Material.AIR){
					first.getInventory().setHelmet(null);
					new ExtrasPlayer().setBlockOnPlayerHead(first, Preferences.blockHead.get(first.getName()).getType());
				}

				if(Preferences.blockHead.get(second.getName()).getType() != Material.AIR){
					second.getInventory().setHelmet(null);
					new ExtrasPlayer().setBlockOnPlayerHead(second, Preferences.blockHead.get(second.getName()).getType());
				}*/
				
			}
			
			
		}

	}

}
