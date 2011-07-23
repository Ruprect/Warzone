package couk.Adamki11s.Database;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import org.bukkit.entity.Player;
import com.alta189.sqlLibrary.SQLite.sqlCore;

import couk.Adamki11s.Warzone.Warzone;

public class Initialise {

	public static sqlCore core;

	private File mainDir = new File("plugins/Warzone"),
	databaseRoot = new File(mainDir + File.separator + "Database"),
	quitterRoot = new File(mainDir + File.separator + "Quitters"),
	configRoot = new File(mainDir + File.separator + "Configuration"),
	inventoryRoot = new File(mainDir + File.separator + "Inventory"),
	preferencesRoot = new File(mainDir + File.separator + "Preferences");

	public void init(){
		directoryCheck();
		SQLite_Startup();
	}

	private void directoryCheck(){
		if(!mainDir.exists()){
			System.out.println(Warzone.prefix + " Creating Main Directory...");
			mainDir.mkdir();
		}
		if(!databaseRoot.exists()){
			System.out.println(Warzone.prefix + " Creating Database Directory...");
			databaseRoot.mkdir();
		}
		if(!quitterRoot.exists()){
			System.out.println(Warzone.prefix + " Creating Quitters Directory...");
			quitterRoot.mkdir();
		}
		if(!configRoot.exists()){
			System.out.println(Warzone.prefix + " Creating Configuration Directory...");
			configRoot.mkdir();
		}
		if(!inventoryRoot.exists()){
			System.out.println(Warzone.prefix + " Creating Inventory Directory...");
			inventoryRoot.mkdir();
		}
		if(!preferencesRoot.exists()){
			System.out.println(Warzone.prefix + " Creating Preferences Directory...");
			preferencesRoot.mkdir();
		}
	}
	
	int l = 999999999;

	private void SQLite_Startup(){
		System.out.println(Warzone.prefix + " Initialising SQL Tables...");
		core = new sqlCore(Logger.getLogger("Minecraft"), Warzone.prefix, "Warzone_Database", databaseRoot.getAbsolutePath());
		core.initialize();
		if(!core.checkTable("statistics")){
			String createRanked = "CREATE TABLE statistics('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'player' VARCHAR(100), 'wins' INTEGER, 'draws' INTEGER" +
					", 'losses' INTEGER, 'shotsfired' INTEGER, 'shotshit' INTEGER, 'shotsmissed' INTEGER, 'playtime' INTEGER, 'kills' INTEGER, 'deaths' INTEGER, 'gp' INTEGER);";
			/**
			 * TODO: Add
			 * Kill Death
			 * Points
			 * Maybe, buy features...
			 */
			core.createTable(createRanked);
		}
		System.out.println(Warzone.prefix + " SQL Tables initialised successfully!");
		System.out.println(Warzone.prefix + " Loading player data...");
		pushStatistics();
	}
	
	public static void pushStatistics(Player p){
		try{
			ResultSet res = core.sqlQuery("SELECT * FROM statistics WHERE player='" + p.getName() + "';");
			while(res.next()){
				String playername = res.getString("player");
				int wins = res.getInt("wins"),
				draws = res.getInt("draws"),
				losses = res.getInt("losses"),
				shotsfired = res.getInt("shotsfired"),
				shotshit = res.getInt("shotshit"),
				shotsmissed = res.getInt("shotsmissed"),
				timeplayed = res.getInt("playtime"),
				kills = res.getInt("kills"),
				deaths = res.getInt("deaths"),
				gamesplayed = res.getInt(("gp"));
				Statistics.databaseHoldings.add(playername);
				Statistics.totalGamesDrawn.put(playername, draws);
				Statistics.totalGamesLost.put(playername, losses);
				Statistics.totalGamesWon.put(playername, wins);
				Statistics.totalShotsFired.put(playername, shotsfired);
				Statistics.totalShotsHit.put(playername, shotshit);
				Statistics.totalShotsMissed.put(playername, shotsmissed);
				Statistics.totalTimePlayed.put(playername, timeplayed);
				Statistics.totalKills.put(playername, kills);
				Statistics.totalDeaths.put(playername, deaths);
				Statistics.gamesPlayed.put(playername, gamesplayed);
				float score = getPlayerScore(wins, draws, losses, shotsmissed, kills, deaths);
				Statistics.playerScore.put(playername, score);
				int level = getLevel(score);
				Statistics.playerLevel.put(playername, level);
			}
		} catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	
	private void pushStatistics(){
		try{
			ResultSet res = core.sqlQuery("SELECT * FROM statistics;");
			while(res.next()){
				String playername = res.getString("player");
				int wins = res.getInt("wins"),
				draws = res.getInt("draws"),
				losses = res.getInt("losses"),
				shotsfired = res.getInt("shotsfired"),
				shotshit = res.getInt("shotshit"),
				shotsmissed = res.getInt("shotsmissed"),
				timeplayed = res.getInt("playtime"),
				kills = res.getInt("kills"),
				deaths = res.getInt("deaths"),
				gamesplayed = res.getInt(("gp"));
				Statistics.databaseHoldings.add(playername);
				Statistics.totalGamesDrawn.put(playername, draws);
				Statistics.totalGamesLost.put(playername, losses);
				Statistics.totalGamesWon.put(playername, wins);
				Statistics.totalShotsFired.put(playername, shotsfired);
				Statistics.totalShotsHit.put(playername, shotshit);
				Statistics.totalShotsMissed.put(playername, shotsmissed);
				Statistics.totalTimePlayed.put(playername, timeplayed);
				Statistics.totalKills.put(playername, kills);
				Statistics.totalDeaths.put(playername, deaths);
				Statistics.gamesPlayed.put(playername, gamesplayed);
				float score = getPlayerScore(wins, draws, losses, shotsmissed, kills, deaths);
				Statistics.playerScore.put(playername, score);
				int level = getLevel(score);
				Statistics.playerLevel.put(playername, level);
			}
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		System.out.println(Warzone.prefix + " Player data loaded successfully!");
	}
	
	public static int levels[] = {0, 1000, 2500, 5000, 8000, 12000, 17000, 23000, 30000, 38000, 47000,
			57000, 68000, 80000, 93000, 107000, 122000, 138000, 155000, 173000, 192000,
			212000, 233000, 255000, 278000, 302000, 327000, 353000, 380000, 408000, 437000,
			467000, 498000, 530000, 563000, 597000, 632000, 668000, 705000, 743000,
			782000, 822000, 863000, 905000, 958000, 1002000, 1057000, 1103000, 1150000,
			1198000, 1277000, 1500000};
	
	private static int getLevel(float s){
		int level = 0;
		for(int index = 1; index < levels.length; index++){
			if(s >= levels[index - 1] && s < levels[index]){
				level = index;
				break;
			}
		}
		return level;
	}
	
	public static Float getPlayerScore(int wins, int draws, int losses, int shotshit, int kills, int deaths) {
		/*
		 * Win = +500
		 * Draw = +200
		 * Loss = +50
		 * ShotHit = +5
		 * Kill = +30
		 * Death = +5
		 */
		Float score = (float) 0;
		score += (wins * 500);
		score += (draws * 200);
		score += (losses * 50);
		score += (shotshit * 5);
		score += (kills * 30);
		score += (deaths * 5);
		return score;
	}


}
