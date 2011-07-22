package couk.Adamki11s.Database;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

public class Statistics {
	
	public static ArrayList<String> databaseHoldings = new ArrayList<String>();
	public static HashMap<String, Integer> totalShotsFired = new HashMap<String, Integer>();
	public static HashMap<String, Integer> totalShotsHit = new HashMap<String, Integer>();
	public static HashMap<String, Integer> totalShotsMissed = new HashMap<String, Integer>();
	public static HashMap<String, Integer> totalGamesWon = new HashMap<String, Integer>();
	public static HashMap<String, Integer> totalGamesDrawn = new HashMap<String, Integer>();
	public static HashMap<String, Integer> totalGamesLost = new HashMap<String, Integer>();
	public static HashMap<String, Integer> totalTimePlayed = new HashMap<String, Integer>();
	public static HashMap<Player, Long> totalPoints = new HashMap<Player, Long>();
	public static HashMap<String, Integer> totalKills = new HashMap<String, Integer>();
	public static HashMap<String, Integer> totalDeaths = new HashMap<String, Integer>();
	public static HashMap<String, Integer> gamesPlayed = new HashMap<String, Integer>();

}
