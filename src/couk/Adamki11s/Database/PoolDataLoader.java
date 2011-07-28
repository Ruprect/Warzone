package couk.Adamki11s.Database;

import java.io.File;
import couk.Adamki11s.Lobby.Pool;

public class PoolDataLoader {
	
	SettingsHandler poolTimes = new SettingsHandler("ConfigurationFiles/Matchmaking.config", "plugins" + 
			 File.separator +  "Warzone" + File.separator +"Configuration"  + File.separator + "MatchMaking.config");
	
	public void loadPoolingData(){
		poolTimes.load();
		Pool.timeout_Count = poolTimes.getPropertyInteger("TimeoutCount");
		Pool.sendSearchMessageEvery_XSeconds = poolTimes.getPropertyInteger("SearchMessageDialogueDisplayRate");
		Pool.showTOC = poolTimes.getPropertyBoolean("ShowTOC");
		CoreConfiguration.coalAMT = poolTimes.getPropertyInteger("CoalAmount");
		System.out.println("[Warzone] Matchmaking configuration loaded successfully!");
	}

}
