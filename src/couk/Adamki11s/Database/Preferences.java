package couk.Adamki11s.Database;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.config.Configuration;

import couk.Adamki11s.Extras.Player.ExtrasPlayer;

public class Preferences {
	
	public static HashMap<String, Armour> armourType = new HashMap<String, Armour>();
	public static HashMap<String, ItemStack> blockHead = new HashMap<String, ItemStack>();
	
	File root = new File("plugins/Warzone/Preferences");
	
	public boolean doesPlayerHavePreferenceFile(Player p){
		File pPref = new File(root + File.separator + p.getName() + ".pref");
		if(pPref.exists()){
			return true;
		} else {
			return false;
		}
	}
	
	public void createPreferenceFile(Player p){
		File pref = new File(root + File.separator + p.getName() + ".pref");
		Configuration c = new Configuration(pref);
		if(!doesPlayerHavePreferenceFile(p)){
			try {
				pref.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			c.setProperty("Preferences.ArmourType", "NONE");
			c.setProperty("Preferences.BlockOnHead", "AIR");
			c.save();
		}
	}
	
	public void loadPreferences(Player p){
		File pref = new File(root + File.separator + p.getName() + ".pref");
		Configuration c = new Configuration(pref);
		c.load();
		armourType.put(p.getName().toString(), getArmour(c.getString("Preferences.ArmourType", "NONE")));
		blockHead.put(p.getName().toString(), new ItemStack((Material.getMaterial(c.getString("Preferences.BlockOnHead", "AIR"))), 1));
	}
	
	ExtrasPlayer ep = new ExtrasPlayer();
	
	public void modifyBlockOnHead(Player p, int material){
		int level = Statistics.playerLevel.get(p.getName());
		File pref = new File(root + File.separator + p.getName() + ".pref");
		Configuration c = new Configuration(pref);
		c.load();
		Material m = Material.getMaterial(material);
		if(level >= 40){
			if(m == null || m == Material.AIR){			
				p.sendMessage(ChatColor.RED + "[Warzone] Inavlid Material Type!");
				return;
			}
			c.setProperty("Preferences.ArmourType", c.getString("Preferences.ArmourType", "NONE"));
			c.setProperty("Preferences.BlockOnHead", c.getString("Preferences.BlockOnHead", m.toString().toUpperCase()));
			blockHead.put(p.getName(), new ItemStack(m, 1));
			p.sendMessage(ChatColor.RED + "[Warzone] " + ChatColor.GREEN + "Block preference changed to " + ChatColor.DARK_AQUA + m.toString().toUpperCase() + ChatColor.GREEN + " successfully!");
			c.save();
		} else {
			p.sendMessage(ChatColor.RED + "[Warzone] You must be level 40 or above to do this!");
			return;
		}
	}
	
	public void modifyArmourPreference(Player p, Armour a){
		int level = Statistics.playerLevel.get(p.getName());
		File pref = new File(root + File.separator + p.getName() + ".pref");
		Configuration c = new Configuration(pref);
		c.load();
		if(a != null){
			boolean canusearmour = false;
			int l = 0;
			switch(a){
			case LEATHER: if(level >= 10){
				canusearmour = true;
				break;
			} else {
				l = 10;
				break;
			}
			case IRON: if(level >= 20){
				canusearmour = true;
				break;
			} else {
				l = 20;
				break;
			}
			case GOLD: if(level >= 30){
				canusearmour = true;
				break;
			} else {
				l = 30;
				break;
			}
			case DIAMOND: if(level >= 40){
				canusearmour = true;
				break;
			} else {
				l = 40;
				break;
			}
			}
			
			if(canusearmour){
				c.setProperty("Preferences.ArmourType", a.toString());
				c.setProperty("Preferences.BlockOnHead", c.getString("Preferences.BlockOnHead", "AIR"));
				c.save();
				p.sendMessage(ChatColor.RED + "[Warzone]" + ChatColor.GREEN + " Armour type changed to " + ChatColor.DARK_AQUA + a.toString() + ChatColor.GREEN + " sucessfully!");
				armourType.put(p.getName(), a);
			} else {
				p.sendMessage(ChatColor.RED + "[Warzone] You must be level " + ChatColor.DARK_AQUA + l + ChatColor.RED + " to wield this armour!");
			}
		} else {
			p.sendMessage(ChatColor.RED + "[Warzone] Error : Armour type can only be NONE, LEATHER,    IRON, GOLD or DIAMOND.");
			return;
		}
	}
	
	public Armour getArmour(String s){
		if(s.equalsIgnoreCase("NONE")){
			return Armour.NONE;
		} else if(s.equalsIgnoreCase("LEATHER")){
			return Armour.LEATHER;
		} else if(s.equalsIgnoreCase("IRON")){
			return Armour.IRON;
		} else if(s.equalsIgnoreCase("GOLD")){
			return Armour.GOLD;
		} else if(s.equalsIgnoreCase("DIAMOND")){
			return Armour.DIAMOND;
		}
		return null;
	}
	
	public enum Armour{
		
		NONE,
		LEATHER,
		IRON,
		GOLD,
		DIAMOND;
		
		@Override
		public String toString(){
			return super.toString().toUpperCase();
		}
	
	}

}
