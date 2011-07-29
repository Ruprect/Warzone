package couk.Adamki11s.Database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.util.config.Configuration;

import couk.Adamki11s.Languages.LangInterface.Langs;

public class LanguageConfiguration {
	
	public void setup(){
		File lang = new File("plugins/Warzone/Language/Lang.yml");
		if(!lang.exists()){
			try {
				lang.createNewFile();
				Configuration c = new Configuration(lang);
				c.load();
				c.setProperty("Language", "EN");
				c.save();
				writeNotes();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void writeNotes(){
		File lang = new File("plugins/Warzone/Language/Lang.yml");
		FileWriter fstream;
		try {
			fstream = new FileWriter(lang, true);
			BufferedWriter fbw = new BufferedWriter(fstream);
			fbw.write("#Supported languages: English, French, German.");
	        fbw.newLine();
	        fbw.write("#For English : EN");
	        fbw.newLine();
	        fbw.write("#For French : FR");
	        fbw.newLine();
	        fbw.write("#For German : DE");
	        fbw.newLine();
	        fbw.write("#Credits go to : @Zeerix for the German translation and @Ethneldryt for the French translation.");
	        fbw.close();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Langs loadLang(){
		File lang = new File("plugins/Warzone/Language/Lang.yml");
		String langRes = "";
		Configuration c = new Configuration(lang);
		c.load();
		langRes = c.getString("Language", "EN");
		c.save();
		if(langRes.equalsIgnoreCase("EN")){
			System.out.println("[Warzone] Language setting : ENGLISH");
			writeNotes();
			return Langs.ENGLISH;
		} else if(langRes.equalsIgnoreCase("FR")){
			System.out.println("[Warzone] Language setting : FRENCH");
			writeNotes();
			return Langs.FRENCH;
		} else if(langRes.equalsIgnoreCase("DE")){
			System.out.println("[Warzone] Language setting : GERMAN");
			writeNotes();
			return Langs.GERMAN;
		} else {
			System.out.println("[Warzone][ERROR] Language set to ENGLISH by Default.");
			System.out.println("[Warzone][ERROR] Invalid property in configuration file!");
			writeNotes();
			return Langs.ENGLISH;
		}
	}

}
