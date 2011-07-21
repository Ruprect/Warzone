package couk.Adamki11s.Warzone;

import java.util.Map.Entry;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import couk.Adamki11s.Database.Initialise;
import couk.Adamki11s.Database.Statistics;
import couk.Adamki11s.Extras.Inventory.ExtrasInventory;
import couk.Adamki11s.Extras.Player.ExtrasPlayer;
import couk.Adamki11s.Games.Gamedata;
import couk.Adamki11s.Maps.Maps;
import couk.Adamki11s.Warzone.Warzone.MapData;

public class WarzonePlayerListener extends PlayerListener {

	public void onPlayerJoin(PlayerJoinEvent evt){
		if(!Statistics.databaseHoldings.contains(evt.getPlayer().getName())){
			Initialise.core.insertQuery("INSERT INTO statistics (player, wins, draws, losses, shotsfired, shotshit, shotsmissed)" +
					" values ('" + evt.getPlayer().getName() + "', '0', '0', '0', '0', '0', '0');");
			Statistics.databaseHoldings.add(evt.getPlayer().getName());
			Initialise.pushStatistics(evt.getPlayer());
		}
		if(Warzone.quitterHandle.doesExist(evt.getPlayer())){
			evt.getPlayer().teleport(Warzone.quitterHandle.getQuitterLocation(evt.getPlayer()));
			evt.getPlayer().sendMessage(ChatColor.RED + "[Warzone] You quit during a game! Returned to initial location.");
			Warzone.quitterHandle.removeQuitter(evt.getPlayer());
		}
	}
	
	ExtrasInventory invManage = new ExtrasInventory();
	ExtrasPlayer exP = new ExtrasPlayer();

	public void onPlayerInteract(PlayerInteractEvent evt){
		Player player = evt.getPlayer();
		if (((evt.getAction() == Action.RIGHT_CLICK_AIR)) && 
				(player.getItemInHand().getTypeId() == 261) && (player.getInventory().contains(262))){
			for(Entry<MapData, Gamedata> maps : Warzone.mapData.entrySet()){
				if(maps.getValue().getParticipants().contains(player)){
					Gamedata gd = maps.getValue();
					gd.shotFired(player);
				}
			}
		} else if((player.getLocation().getWorld() == Maps.Warzone_World) && ((evt.getAction() == Action.LEFT_CLICK_AIR) || evt.getAction() == Action.LEFT_CLICK_BLOCK) &&
		(player.getItemInHand().getTypeId() == 261) && player.getInventory().contains(263)){		
			for(Entry<MapData, Gamedata> maps : Warzone.mapData.entrySet()){
				if(maps.getValue().getParticipants().contains(player)){
					Location loc = player.getLocation();
					if(invManage.getAmountOf(player, 263) > 1){
						invManage.removeFromInventory(player, 263, 1);
					} else {
						invManage.removeAllFromInventory(player, 263);
					}
					World w = loc.getWorld();
					loc = exP.getLocationLooked(player);
					int repeat = 0;
					while(repeat < 1){
					for(double x = (loc.getX() - 3); x <= (loc.getX() + 3); x++){
						for(double y = (loc.getY() - 3); y <= (loc.getY() + 3); y++){
							for(double z = (loc.getZ() - 3); z <= (loc.getZ() + 3); z++){
								w.playEffect(new Location(w, x, y, z), Effect.SMOKE, 10);
							}
						}
					}
					repeat++;
					}
				}
			}
		}
	}
	
	public void onPlayerToggleSneak(PlayerToggleSneakEvent evt) {
		Player player = evt.getPlayer();
		for(Entry<MapData, Gamedata> maps : Warzone.mapData.entrySet()){
			if(maps.getValue().getParticipants().contains(player)){
				if(evt.isSneaking()){
					evt.getPlayer().setSneaking(true);
		        	evt.setCancelled(true);
				}
			}
		}
     }
	
	public void onPlayerDropItem(PlayerDropItemEvent evt){
		Player player = evt.getPlayer();
		for(Entry<MapData, Gamedata> maps : Warzone.mapData.entrySet()){
			if(maps.getValue().getParticipants().contains(player)){
				evt.setCancelled(true);				
		}
		}
	}
	
	public void onPlayerPortal(PlayerPortalEvent evt){
		if(evt.getPlayer().getWorld() == Maps.Warzone_World){
			evt.setCancelled(true);
		}
	}

}
