package couk.Adamki11s.Database;

import java.io.File;

import couk.Adamki11s.Games.ASCENSION_GD;
import couk.Adamki11s.Games.BLIZZARD_GD;
import couk.Adamki11s.Games.CASTLE_GD;
import couk.Adamki11s.Games.DUNGEON_GD;
import couk.Adamki11s.Games.JUNGLE_GD;
import couk.Adamki11s.Games.OVERFLOW_GD;
import couk.Adamki11s.Games.PLAINES_GD;
import couk.Adamki11s.Games.TOMB_GD;

public class MapDataLoader {
	
	SettingsHandler mapTimes = new SettingsHandler("ConfigurationFiles/MapData.config", "plugins" + 
			 File.separator +  "Warzone" + File.separator +"Configuration"  + File.separator + "MapData.config");
	
	public void loadMapData(){
		mapTimes.load();
		ASCENSION_GD.gameTime = mapTimes.getPropertyInteger("Ascension");
		BLIZZARD_GD.gameTime = mapTimes.getPropertyInteger("Blizzard");
		CASTLE_GD.gameTime = mapTimes.getPropertyInteger("Castle");
		DUNGEON_GD.gameTime = mapTimes.getPropertyInteger("Dungeon");
		JUNGLE_GD.gameTime = mapTimes.getPropertyInteger("Jungle");
		OVERFLOW_GD.gameTime = mapTimes.getPropertyInteger("Overflow");
		PLAINES_GD.gameTime = mapTimes.getPropertyInteger("Plaines");
		TOMB_GD.gameTime = mapTimes.getPropertyInteger("Tomb");
		System.out.println("[Warzone] Map configuration loaded successfully!");
	}

}
