package couk.Adamki11s.Database;

import java.io.File;

import couk.Adamki11s.Games.ABYSS_GD;
import couk.Adamki11s.Games.AFTERMATH_GD;
import couk.Adamki11s.Games.ASCENSION_GD;
import couk.Adamki11s.Games.AURORA_GD;
import couk.Adamki11s.Games.BLIND_GD;
import couk.Adamki11s.Games.BLIZZARD_GD;
import couk.Adamki11s.Games.BURROW_GD;
import couk.Adamki11s.Games.CASTLE_GD;
import couk.Adamki11s.Games.CONTAINMENT_GD;
import couk.Adamki11s.Games.CRYPT_GD;
import couk.Adamki11s.Games.DOME_GD;
import couk.Adamki11s.Games.DUNGEON_GD;
import couk.Adamki11s.Games.HOMETREE_GD;
import couk.Adamki11s.Games.JUNGLE_GD;
import couk.Adamki11s.Games.LAPUTA_GD;
import couk.Adamki11s.Games.NUKETOWN_GD;
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
		BLIND_GD.gameTime = mapTimes.getPropertyInteger("Blind");
		CONTAINMENT_GD.gameTime = mapTimes.getPropertyInteger("Containment");
		AFTERMATH_GD.gameTime = mapTimes.getPropertyInteger("Aftermath");
		CRYPT_GD.gameTime = mapTimes.getPropertyInteger("Crypt");
		HOMETREE_GD.gameTime = mapTimes.getPropertyInteger("Hometree");
		AURORA_GD.gameTime = mapTimes.getPropertyInteger("Aurora");
		ABYSS_GD.gameTime = mapTimes.getPropertyInteger("Abyss");
		BURROW_GD.gameTime = mapTimes.getPropertyInteger("Burrow");
		LAPUTA_GD.gameTime = mapTimes.getPropertyInteger("Laputa");
		DOME_GD.gameTime = mapTimes.getPropertyInteger("Dome");
		NUKETOWN_GD.gameTime = mapTimes.getPropertyInteger("Nuketown");
		System.out.println("[Warzone] Map configuration loaded successfully!");
	}

}
