package couk.Adamki11s.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import couk.Adamki11s.Database.LobbyPlaceHolder;
import couk.Adamki11s.Database.PermissionsCore;
import couk.Adamki11s.Database.PlayerReturnHandler;
import couk.Adamki11s.Lobby.Pool;
import couk.Adamki11s.Warzone.Warzone;
import couk.Adamki11s.Warzone.Warzone.GameType;

public class SearchCommands {
	
	PermissionsCore pc = new PermissionsCore();
	LobbyPlaceHolder lph = new LobbyPlaceHolder();	
	PlayerReturnHandler prh = new PlayerReturnHandler();
	
	public void check(Player p, String[] args){
		
		if(args.length == 2 && args[0].equalsIgnoreCase("search")){
			if(args[1].equalsIgnoreCase("ranked")){
				if(pc.doesHaveNode(p, "warzone.play.ranked")){
					new Pool().findMatch(p, GameType.RANKED);
					return;
				} else {
					pc.sendInsufficientPermsMsg(p);
					return;
				}
			}
			if(args[1].equalsIgnoreCase("social")){
				if(pc.doesHaveNode(p, "warzone.play.social")){
					new Pool().findMatch(p, GameType.SOCIAL);
					return;
				} else {
					p.sendMessage(ChatColor.RED + "[Warzone] " + Warzone.li.getObj("You do not have permission to disable Warzone!"));
					return;
				}
			}
		}
		
		if(args.length >= 1 && (args[0].equalsIgnoreCase("stop") || args[0].equalsIgnoreCase("cancel"))){
			if(Pool.searchers.contains(p)){
				Pool.searchers.remove(p);
				Pool.gameType.remove(p);
				Pool.timeoutCount.remove(p);
				p.teleport(Pool.locs.get(p));
				Pool.chilledInLobby.put(p, false);
				Pool.locs.remove(p);
				if(lph.checkLobby(p)){
					lph.removeLobbyDumpFile(p);
				}
				if(prh.doesExist(p)){
					prh.removeReturn(p);
				}
				p.sendMessage(ChatColor.RED + "[Warzone] " + Warzone.li.getObj("Search cancelled successfully."));
				return;
			} else {
				p.sendMessage(ChatColor.RED + "[Warzone] " + Warzone.li.getObj("You are not searching for a game!"));
				return;
			}
		}
		
	}

}
