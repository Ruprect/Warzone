package couk.Adamki11s.Commands;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.text.NumberFormatter;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import couk.Adamki11s.Database.Initialise;
import couk.Adamki11s.Database.Statistics;
import couk.Adamki11s.Lobby.Pool;
import couk.Adamki11s.Maps.Maps;
import couk.Adamki11s.Warzone.Warzone;
import couk.Adamki11s.Warzone.Warzone.GameType;

public class WarzoneCommands implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if(sender instanceof Player){
			Player p = (Player)sender;
			if(label.equalsIgnoreCase("warzone")){
				if(args.length == 1 && args[0].equalsIgnoreCase("disable")){
					Warzone.server.getPluginManager().disablePlugin(Warzone.plugin);
					p.sendMessage(ChatColor.RED + "[Warzone] " + ChatColor.GRAY + " Warzone disabled successfully!");
				}
				if(args.length == 1 && args[0].equalsIgnoreCase("search-ranked")){
					new Pool().findMatch((Player)sender, GameType.RANKED);
				} else if(args.length == 1 && args[0].equalsIgnoreCase("search-social")){
					new Pool().findMatch((Player)sender, GameType.SOCIAL);
				}
				if(args.length == 1 && args[0].equalsIgnoreCase("loc")){
					p.sendMessage("Yaw : " + p.getLocation().getYaw());
					p.sendMessage("Pitch : " + p.getLocation().getPitch());
				}
				if(args.length == 1 && args[0].equalsIgnoreCase("arena")){
					p.teleport(new Location(Maps.Warzone_World, -100, 76, 200,  (float)1.7, (float)-0.14));
				}
				if(args.length == 1 && args[0].equalsIgnoreCase("kill")){
					Warzone.npc_handle.despawnNPCS();
				}
				
				if(args.length == 1 && args[0].equalsIgnoreCase("stats")){
					
					p.sendMessage(ChatColor.RED + "[Warzone]" + ChatColor.YELLOW + "[Statistics]" + ChatColor.GRAY + "[" + p.getName() + "]" + ChatColor.RED + "~~~~~~~~~~~~~~~~~~~~~~");
					
					if(Statistics.gamesPlayed.get(p.getName()) > 0){
						NumberFormat formatter = new DecimalFormat("#0.000"), kdFormat = new DecimalFormat("#0.00");
						
						double shotsHit = Statistics.totalShotsHit.get(p.getName()), shotsFired = Statistics.totalShotsFired.get(p.getName()),
						wins = Statistics.totalGamesWon.get(p.getName()), losses = Statistics.totalGamesLost.get(p.getName()),
						kills = Statistics.totalKills.get(p.getName()), deaths = Statistics.totalDeaths.get(p.getName());
						int playtime = Statistics.totalTimePlayed.get(p.getName()), gp = Statistics.gamesPlayed.get(p.getName());
						
						double accuracy = Math.abs((double)(shotsHit / shotsFired));
						accuracy *= 100;
						double KDR = Math.abs((double)(kills / deaths)), WLR = Math.abs((double)(wins / losses));
						p.sendMessage(ChatColor.DARK_AQUA + "Games Played : " + ChatColor.GREEN + Statistics.gamesPlayed.get(p.getName()));
						p.sendMessage(ChatColor.DARK_AQUA + "Accuracy : " + ChatColor.GREEN + formatter.format(accuracy) + "%");
						p.sendMessage(ChatColor.DARK_AQUA + "Kill/Death Ratio : " + ChatColor.GREEN + kdFormat.format(KDR));
						p.sendMessage(ChatColor.DARK_AQUA + "Win/Loss Ratio : " + ChatColor.GREEN + kdFormat.format(WLR));
						if(playtime < 60){
							p.sendMessage(ChatColor.DARK_AQUA + "Playtime : " + ChatColor.GREEN + playtime + " seconds");
						} else if(playtime >= 60 && playtime < 3600){
							p.sendMessage(ChatColor.DARK_AQUA + "Playtime : " + ChatColor.GREEN + (playtime / 60) + " minutes");
						} else if(playtime >= 3600 && playtime < 86400){
							p.sendMessage(ChatColor.DARK_AQUA + "Playtime : " + ChatColor.GREEN + (playtime / 3600) + " hours");
						} else {
							double days = ((double)playtime / 86400.0);
							double remaining = ((double)playtime - (days * 86400.0));
							double hours = (remaining / 3600.0);
							double minsleft = ((remaining - (hours * 3600.0))/60.0);
							p.sendMessage(ChatColor.DARK_AQUA + "Playtime : " + ChatColor.GREEN + days + " Days, " + hours + " Hours, " + minsleft + " Minutes.");
						}
					} else {
						p.sendMessage(ChatColor.RED + "Error : " + ChatColor.GRAY + "You have no statistics to display!");
					}
					
					p.sendMessage(ChatColor.RED + "[Warzone]" + ChatColor.YELLOW + "[Statistics]" + ChatColor.GRAY + "[" + p.getName() + "]" + ChatColor.RED + "~~~~~~~~~~~~~~~~~~~~~~");
				}
				
				if(args.length >= 1 && args[0].equalsIgnoreCase("sign")){
					World w = p.getWorld();
					double x = Double.parseDouble(args[1]), y = Double.parseDouble(args[2]), z = Double.parseDouble(args[3]);
					Location loc = new Location(w, x, y, z);
					Sign s = (Sign)w.getBlockAt(loc).getState();
					for(String line : s.getLines()){
						System.out.println(line);
					}
				}
			}
		}
		return false;
	}

}
