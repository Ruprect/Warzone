package couk.Adamki11s.Database;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import com.alta189.sqlLibrary.SQLite.sqlCore;

import couk.Adamki11s.Warzone.Warzone;

public class Initialise {

	public static sqlCore core;

	private File mainDir = new File("plugins/Warzone"),
	databaseRoot = new File(mainDir + File.separator + "Database"),
	quitterRoot = new File(mainDir + File.separator + "Quitters");

	public void init(){
		directoryCheck();
		SQLite_Startup();
	}

	private void directoryCheck(){
		if(!mainDir.exists()){
			System.out.println(Warzone.prefix + " Creating Main Directory...");
			mainDir.mkdir();
			if(!databaseRoot.exists()){
				System.out.println(Warzone.prefix + " Creating Database Directory...");
				databaseRoot.mkdir();
			}
			if(!quitterRoot.exists()){
				System.out.println(Warzone.prefix + " Creating Quitters Directory...");
				quitterRoot.mkdir();
			}
		}
	}
	
	int l = 999999999;

	private void SQLite_Startup(){
		System.out.println(Warzone.prefix + " Initialising SQL Tables...");
		core = new sqlCore(Logger.getLogger("Minecraft"), Warzone.prefix, "Warzone_Database", databaseRoot.getAbsolutePath());
		core.initialize();
		if(!core.checkTable("statistics")){
			String createRanked = "CREATE TABLE statistics('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'player' VARCHAR(100), 'wins' INTEGER, 'draws' INTEGER" +
					", 'losses' INTEGER, 'shotsfired' INTEGER, 'shotshit' INTEGER, 'shotsmissed' INTEGER, 'playtime' INT);";
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
			Bukkit.getServer().getScheduler().cancelTask(QueryPooler.sqlClose);
			if(!Initialise.core.checkConnection()){
				Initialise.core.initialize();
			}
			ResultSet res = core.sqlQuery("SELECT * FROM statistics WHERE player='" + p.getName() + "';");
			while(res.next()){
				String playername = res.getString("player");
				int wins = res.getInt("wins"),
				draws = res.getInt("draws"),
				losses = res.getInt("losses"),
				shotsfired = res.getInt("shotsfired"),
				shotshit = res.getInt("shotshit"),
				shotsmissed = res.getInt("shotsmissed"),
				timeplayed = res.getInt("playtime");
				Statistics.databaseHoldings.add(playername);
				Statistics.totalGamesDrawn.put(playername, draws);
				Statistics.totalGamesLost.put(playername, losses);
				Statistics.totalGamesWon.put(playername, wins);
				Statistics.totalShotsFired.put(playername, shotsfired);
				Statistics.totalShotsHit.put(playername, shotshit);
				Statistics.totalShotsMissed.put(playername, shotsmissed);
				Statistics.totalTimePlayed.put(playername, timeplayed);
			}
			QueryPooler.sqlCloser();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	
	private void pushStatistics(){
		try{
			Bukkit.getServer().getScheduler().cancelTask(QueryPooler.sqlClose);
			if(!Initialise.core.checkConnection()){
				Initialise.core.initialize();
			}
			ResultSet res = core.sqlQuery("SELECT * FROM statistics;");
			while(res.next()){
				String playername = res.getString("player");
				int wins = res.getInt("wins"),
				draws = res.getInt("draws"),
				losses = res.getInt("losses"),
				shotsfired = res.getInt("shotsfired"),
				shotshit = res.getInt("shotshit"),
				shotsmissed = res.getInt("shotsmissed"),
				timeplayed = res.getInt("playtime");
				Statistics.databaseHoldings.add(playername);
				Statistics.totalGamesDrawn.put(playername, draws);
				Statistics.totalGamesLost.put(playername, losses);
				Statistics.totalGamesWon.put(playername, wins);
				Statistics.totalShotsFired.put(playername, shotsfired);
				Statistics.totalShotsHit.put(playername, shotshit);
				Statistics.totalShotsMissed.put(playername, shotsmissed);
				Statistics.totalTimePlayed.put(playername, timeplayed);
			}
			QueryPooler.sqlCloser();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		System.out.println(Warzone.prefix + " Player data loaded successfully!");
	}


}
