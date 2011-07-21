package couk.Adamki11s.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
