package couk.Adamki11s.Games;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import couk.Adamki11s.Database.Initialise;
import couk.Adamki11s.Database.Statistics;
import couk.Adamki11s.Extras.Inventory.ExtrasInventory;
import couk.Adamki11s.Lobby.Pool;
import couk.Adamki11s.Maps.Maps;
import couk.Adamki11s.Warzone.Warzone;
import couk.Adamki11s.Warzone.Warzone.GameType;
import couk.Adamki11s.Warzone.Warzone.MapName;

public class JUNGLE_GD extends Gamedata{
	
	public static int gameTime;
	
	HashMap<Player, Integer> score = new HashMap<Player, Integer>();
	HashMap<Player, Integer> shotsHit = new HashMap<Player, Integer>();
	HashMap<Player, Integer> shotsFired = new HashMap<Player, Integer>();
	HashMap<Player, Integer> shotsMissed = new HashMap<Player, Integer>();
	
	HashMap<Player, Location> previousLocation = new HashMap<Player, Location>();
	
	private HashMap<Player, Integer> virtualHealth = new HashMap<Player, Integer>();
	
	HashMap<Player, Integer> kills = new HashMap<Player, Integer>();
	HashMap<Player, Integer> deaths = new HashMap<Player, Integer>();
	
	ExtrasInventory inventmanage = new ExtrasInventory();
	
	HashMap<Player, ItemStack[]> invent = new HashMap<Player, ItemStack[]>();
	HashMap<Player, ItemStack[]> inventArmour = new HashMap<Player, ItemStack[]>();
	
	private Location sign1 = new Location(Maps.Warzone_World, -87.5, 73.62, 104.5),
	sign2 = new Location(Maps.Warzone_World, -110.5, 73.62, 140.5);
	
	Sign s1 = (Sign)Maps.Warzone_World.getBlockAt(sign1).getState();
	Sign s2 = (Sign)Maps.Warzone_World.getBlockAt(sign2).getState();
	
	private int timer = 0;
	
	private boolean playerQuit = false;
	
	private GameType gametype;
	
	ArrayList<Player> participants = new ArrayList<Player>();
	
	@Override
	public void passGameType(GameType gt){
		gametype = gt;
	}

	@Override
	public void initiateGame(ArrayList<Player> list) {
		timer = 0;
		playerQuit = false;
		for(Player p : list){
			kills.put(p, 0);
			deaths.put(p, 0);
			score.put(p, 0);
			shotsHit.put(p, 0);
			shotsFired.put(p, 0);
			shotsMissed.put(p, 0);
			virtualHealth.put(p, 3);
		}
		s1.setLine(0, ChatColor.DARK_GREEN + "YOU : 0");
		s1.setLine(1, ChatColor.RED + "THEM : 0");
		s1.setLine(2, ChatColor.DARK_PURPLE + "Time Left");
		s1.setLine(3, ChatColor.DARK_PURPLE + "" + (gameTime - timer));
		s2.setLine(0, ChatColor.DARK_GREEN + "YOU : 0");
		s2.setLine(1, ChatColor.RED + "THEM : 0");
		s2.setLine(2, ChatColor.DARK_PURPLE + "Time Left");
		s2.setLine(3, ChatColor.DARK_PURPLE + "" + (gameTime - timer));
		s1.update();
		s2.update();
	}
	
	@Override
	public void addParticipant(Player p) {
		Initialise.pushStatistics(p);
		participants.add(p);
		previousLocation.put(p, Pool.locs.get(p));
		invent.put(p, p.getInventory().getContents());
		inventArmour.put(p, p.getInventory().getArmorContents());
	}

	@Override
	public void removeParticipants(Player p) {
		participants.remove(p);
	}

	@Override
	public void shotFired(Player p) {
		shotsFired.put(p, shotsFired.get(p) + 1);
	}

	@Override
	public void shotMissed(Player p) {
		shotsMissed.put(p, shotsMissed.get(p) + 1);
	}

	@Override
	public void shotHit(Player p, Player target) {
		shotsHit.put(p, shotsHit.get(p) + 1);
		virtualHealth.put(target, virtualHealth.get(target) - 1);
		
		if(virtualHealth.get(target) <= 0){
			target.damage(1);
			kills.put(p, kills.get(p) + 1);
			deaths.put(target, deaths.get(target) + 1);
			Warzone.ec.sendColouredMessage(p, "&red[Warzone] &aYou shot &9" + target.getName() + "&a and gained a point!");
			Warzone.ec.sendColouredMessage(target, "&red[Warzone] &aYou got shot by &9" + p.getName() + "!");
			virtualHealth.put(target, 3);
			virtualHealth.put(p, 3);
			incrementPlayerScore(p);
			respawn();
		}
	}

	@Override
	public void incrementPlayerScore(Player p) {
		score.put(p, score.get(p) + 1);
	}

	@Override
	public Player getWinner() {
		int index = 0, score = 0, count = 0;
		for(Player p : participants){
			count++;
			if(this.score.get(p) > score){
				score = this.score.get(p);
				index = (count - 1);
			}
		}
		return participants.get(index);
	}
	
	@Override
	public Player getLooser() {
		int index = 0, score = 1584635, count = 0;
		for(Player p : participants){
			count++;
			if(this.score.get(p) < score){
				score = this.score.get(p);
				index = (count - 1);
			}
		}
		return participants.get(index);
	}

	@Override
	public String getName() {
		return "Jungle";
	}

	@Override
	public void tickerTask(int schedulerTask) {
		Player quitter = null;
		if(Warzone.server.getPlayer(participants.get(0).getName()) == null){
			timer = gameTime;
			playerQuit = true;
			score.put(participants.get(0), 0);
			score.put(participants.get(1), score.get(participants.get(1)) + 1);
			if(Warzone.server.getPlayer(participants.get(1).getName()) != null){
				quitter = participants.get(0);
				participants.get(1).sendMessage(ChatColor.RED + "[Warzone] " + ChatColor.YELLOW + participants.get(0).getName()
						+ ChatColor.RED + " Quit the game!");
			}
			endGame(schedulerTask);
		} else if(Warzone.server.getPlayer(participants.get(1).getName()) == null){
			timer = gameTime;
			playerQuit = true;
			score.put(participants.get(1), 0);
			score.put(participants.get(0), score.get(participants.get(0)) + 1);
			if(Warzone.server.getPlayer(participants.get(0).getName()) != null){
				quitter = participants.get(1);
				participants.get(0).sendMessage(ChatColor.RED + "[Warzone] " + ChatColor.YELLOW + participants.get(1).getName()
						+ ChatColor.RED + " Quit the game!");
			}
			endGame(schedulerTask);
		} else {
			timer++;
			if((timer % 10) == 0){
				for(Player pp : participants){
					if(virtualHealth.get(pp) < 3){
						virtualHealth.put(pp, virtualHealth.get(pp) + 1);
					}
				}
			}
			if((((timer - 1) % 60) == 0) && (timer < gameTime - 50)){
				for(Player p : participants){
					p.sendMessage(ChatColor.RED + "[Warzone]" + ChatColor.YELLOW + "[Timer]" + ChatColor.GREEN + " " + ((Math.round(gameTime - timer) / 60) + 1) + " minute(s) remaining.");
				}
			} else if(((gameTime - timer) < 10) && ((timer >= (gameTime - 10)) && (timer <= gameTime) )){
				for(Player p : participants){
					p.sendMessage(ChatColor.RED + "[Warzone]" + ChatColor.YELLOW + "[Timer]" + ChatColor.GREEN + " " + (gameTime - timer) + " second(s) remaining.");
				}		
			}
		}
		if(timer > gameTime){
			endGame(schedulerTask);
		}
		
		s1.setLine(2, ChatColor.DARK_PURPLE + "Time Left");
		s2.setLine(2, ChatColor.DARK_PURPLE + "Time Left");
		s1.setLine(3, ChatColor.DARK_PURPLE + "" + (gameTime - timer));
		s2.setLine(3, ChatColor.DARK_PURPLE + "" + (gameTime - timer));
		s1.update();
		s2.update();
		
		if(playerQuit && quitter != null){
			Warzone.inventData.checkFile(quitter);
			Warzone.inventData.saveInventory(quitter, invent.get(quitter));
			int playTime = Statistics.totalTimePlayed.get(quitter.getName()) + gameTime;
			int loss = Statistics.totalGamesLost.get(quitter.getName()) + 1;
			if(gametype == GameType.RANKED){
				String updateStats = "UPDATE statistics SET playtime='" + playTime + "'" +
				", losses='" + loss + "' WHERE player='" + quitter.getName() + "';";
				Initialise.core.updateQuery(updateStats);
			}
			
			if(!Warzone.quitterHandle.doesExist(quitter)){
				Warzone.quitterHandle.addQuitter(quitter, previousLocation.get(quitter));
			} else {
				try {
					throw new Exception("[Warzone] Quitter file already exists when it shouldn't!");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override public void endGame(int taskid){
Player winner = getWinner();
		
		if(score.get(participants.get(0)) == score.get(participants.get(1))){
			participants.get(0).sendMessage(ChatColor.RED + "[Warzone]" + ChatColor.GREEN + " The match was a draw!");
			participants.get(1).sendMessage(ChatColor.RED + "[Warzone]" + ChatColor.GREEN + " The match was a draw!");
			wasDraw = true;
			for(Player part : participants){
				part.teleport(previousLocation.get(part));
				
				inventmanage.wipeInventory(part);
				part.getInventory().setContents(invent.get(part));
				part.getInventory().setArmorContents(inventArmour.get(part));	
				inventmanage.sortInventory(part);
			}
		} else {
			for(Player part : participants){
				part.sendMessage(ChatColor.RED + "[Warzone] Game over!");
				part.sendMessage(ChatColor.RED + "[Warzone] " + ChatColor.GOLD + winner.getName() + ChatColor.GREEN + " was victorious!");
				part.sendMessage(ChatColor.RED + "[Warzone] " + ChatColor.GOLD + winner.getName() + ChatColor.GREEN + " won with " + ChatColor.BLUE + score.get(winner) +
						ChatColor.GREEN + " kills to " + ChatColor.BLUE + score.get(getLooser()) + ChatColor.GREEN + " kills.");
				part.teleport(previousLocation.get(part));
				
				inventmanage.wipeInventory(part);
				part.getInventory().setContents(invent.get(part));
				part.getInventory().setArmorContents(inventArmour.get(part));	
				inventmanage.sortInventory(part);
			}
		}
		if(gametype == GameType.RANKED){
			saveData(participants);
		}
		participants.clear();
		shotsHit.clear();
		shotsFired.clear();
		shotsMissed.clear();
		timer = 0;
		Warzone.mapList.get(MapName.JUNGLE).setOccupiedState(false);
		Warzone.server.getScheduler().cancelTask(taskid);
	}
	
	public static boolean wasDraw = false;
	
	@Override public void saveData(ArrayList<Player> partic){
		for(Player p : partic){
			int fired = Statistics.totalShotsFired.get(p.getName()) + shotsFired.get(p);
			int hit = Statistics.totalShotsHit.get(p.getName()) + shotsHit.get(p);
			int missed = Statistics.totalShotsMissed.get(p.getName()) + (fired - hit);
			int playTime = Statistics.totalTimePlayed.get(p.getName()) + gameTime;
			int won = Statistics.totalGamesWon.get(p.getName()) + 1;
			int loss = Statistics.totalGamesLost.get(p.getName()) + 1;
			int drew = Statistics.totalGamesDrawn.get(p.getName()) + 1;
			int gp = Statistics.gamesPlayed.get(p.getName()) + 1;
			int kill = Statistics.totalKills.get(p.getName()) + kills.get(p);
			int death = Statistics.totalDeaths.get(p.getName()) + deaths.get(p);
			String updateStats = "";
			if(!playerQuit){
				if(wasDraw){
					updateStats = "UPDATE statistics SET shotsfired='" + fired + "', shotshit='" + hit + "', shotsmissed='" + missed + "', playtime='" + playTime + "'" +
							", draws='" + drew + "', kills='" + kill + "', deaths='" + death + "', gp='" + gp + "' WHERE player='" + p.getName() + "';";
				} else if(getWinner() == p){
					updateStats = "UPDATE statistics SET shotsfired='" + fired + "', shotshit='" + hit + "', shotsmissed='" + missed + "', playtime='" + playTime + "'" +
					", wins='" + won + "', kills='" + kill + "', deaths='" + death + "', gp='" + gp + "' WHERE player='" + p.getName() + "';";
				} else {
					updateStats = "UPDATE statistics SET shotsfired='" + fired + "', shotshit='" + hit + "', shotsmissed='" + missed + "', playtime='" + playTime + "'" +
					", losses='" + loss + "', kills='" + kill + "', deaths='" + death + "', gp='" + gp + "' WHERE player='" + p.getName() + "';";
				}
				Initialise.core.updateQuery(updateStats);
			} else {
				if(Warzone.server.getPlayer(p.getName()) == null){
				
				} else {
					updateStats = "UPDATE statistics SET shotsfired='" + fired + "', shotshit='" + hit + "', shotsmissed='" + missed + "', playtime='" + playTime + "'" +
					", wins='" + won + "', kills='" + kill + "', deaths='" + death + "', gp='" + gp + "' WHERE player='" + p.getName() + "';";
				}
				Initialise.core.updateQuery(updateStats);
			}
			
			Initialise.pushStatistics(p);
		}
	}

	@Override
	public ArrayList<Player> getParticipants() {
		return participants;
	}

	@Override
	public void respawn() {
		participants.get(0).teleport(Warzone.mapList.get(MapName.JUNGLE).getSpawnPoints().get(0));
		participants.get(1).teleport(Warzone.mapList.get(MapName.JUNGLE).getSpawnPoints().get(1));
		
		participants.get(0).setHealth(20);
		participants.get(1).setHealth(20);

		inventmanage.wipeInventory(participants.get(0));
		inventmanage.wipeInventory(participants.get(1));
		
		inventmanage.addToInventory(participants.get(0), 261, 1);
		inventmanage.addToInventory(participants.get(1), 261, 1);
		
		inventmanage.addToInventory(participants.get(0), 262, 64);
		inventmanage.addToInventory(participants.get(0), 263, 5);
		
		inventmanage.addToInventory(participants.get(1), 262, 64);
		inventmanage.addToInventory(participants.get(1), 263, 5);

		updateSigns();
		

		updateSigns();
		
	}
	
	@Override
	public void updateSigns(){
		Player p1 = participants.get(0), p2 = participants.get(1);
		s1.setLine(0, ChatColor.DARK_GREEN + "YOU : " + score.get(p1));
		s1.setLine(1, ChatColor.RED + "THEM : " + score.get(p2));
		s2.setLine(0, ChatColor.DARK_GREEN + "YOU : " + score.get(p2));
		s2.setLine(1, ChatColor.RED + "THEM : " + score.get(p1));
		s1.update();
		s2.update();
	}
	
	private int gamePoolerID;

	@Override
	public void initiateScheduler() {
		// TODO Auto-generated method stub
		gamePoolerID = Warzone.server.getScheduler().scheduleSyncRepeatingTask(Warzone.plugin, new Runnable() {	
			public void run() {
				tickerTask(gamePoolerID);
			}

		}, 20L, 20L);
	}

}
