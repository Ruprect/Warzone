package couk.Adamki11s.Warzone;


import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

import couk.Adamki11s.Maps.Maps;

public class WarzoneBlockListener extends BlockListener {
	
	public void onBlockBreak(BlockBreakEvent evt){
		/*if(evt.getPlayer().getWorld() == Maps.Warzone_World){
		evt.getPlayer().sendMessage(ChatColor.RED + "[Warzone] The warzone world is not editable!");
		evt.setCancelled(true);
	}*/
	}
	
	public void onBlockPlace(BlockPlaceEvent evt){
		/*if(evt.getPlayer().getWorld() == Maps.Warzone_World){
			evt.getPlayer().sendMessage(ChatColor.RED + "[Warzone] The warzone world is not editable!");
			evt.setCancelled(true);
		}*/
	}

}
