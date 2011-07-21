package couk.Adamki11s.Database;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import couk.Adamki11s.Warzone.Warzone;

public class QueryPooler {
	
	public static ArrayList<String> querys = new ArrayList<String>();	
	public static int poolID, sqlClose;
	
	public void addQueryToPool(String query){
		querys.add(query);
	}
	
	public void updateGlobalStatistics(Player p){
		Initialise.pushStatistics(p);
	}
	public static void sqlCloser() {
		sqlClose = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Warzone.plugin, new Runnable() {

	            public void run() {
	                if (Initialise.core != null) {
	                	Initialise.core.close();
	                }
	            }
	        }, 300 * 20L);
	}
	
	
	public void poolQuerys(){
		poolID = Warzone.server.getScheduler().scheduleSyncRepeatingTask(Warzone.plugin, new Runnable() {	
			
			public void run() {
				if(querys.size() > 0){
					Bukkit.getServer().getScheduler().cancelTask(sqlClose);
					
					if(!Initialise.core.checkConnection()){
						Initialise.core.initialize();
					}
					
						for(String q : querys){
							System.out.println("poolList query executed");
							Initialise.core.updateQuery(q);
						}
					QueryPooler.sqlCloser();
					
				}
				querys.clear();
			}

		}, 20L, 20L);
	}

}
