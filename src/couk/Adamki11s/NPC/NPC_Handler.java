package couk.Adamki11s.NPC;

import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.martin.bukkit.npclib.NPCManager;
import couk.Adamki11s.Extras.Random.ExtrasRandom;
import couk.Adamki11s.Maps.Maps;
import couk.Adamki11s.Warzone.Warzone;

public class NPC_Handler {

	public static NPCManager npcManage;
	public static NPC_Control npc;
	public static int ai_ID, loader_ID, Q_1, Q_2, Q_3, Q_4, Q_5, R_A, R_M, R_D_M;

	private ArrayList<AINPC> npcRef = new ArrayList<AINPC>();

	Location b_c_1 = new Location(Maps.Warzone_World, -94.8, 76, 214.5),
	b_c_2 = new Location(Maps.Warzone_World, -106.5, 76, 230.1);
	
	ExtrasRandom exr = new ExtrasRandom();

	public NPC_Handler(NPCManager npcManage2, NPC_Control npc2) {
		npcManage = npcManage2;
		npc = npc2;
	}
	
	public void enableNPCAIController(){
		loader_ID = Warzone.server.getScheduler().scheduleSyncDelayedTask(Warzone.plugin, new Runnable() {	

			public void run() {

				if(npcRef.isEmpty()){
					initialiseNPCMap();
				}
					spawnNPCS();			

			}

		}, 1L);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initiate_Q_1();
		initiate_Q_2();
		initiate_Q_3();
		initiate_Q_4();
		initiate_Q_5();
		initiate_R_A();
	}

	public void initiate_Q_1(){
		Q_1 = Warzone.server.getScheduler().scheduleSyncRepeatingTask(Warzone.plugin, new Runnable() {	

			public void run() {

				Q_1();

			}

		},41L, 42L);
	}
	
	public void initiate_Q_2(){
		Q_2 = Warzone.server.getScheduler().scheduleSyncRepeatingTask(Warzone.plugin, new Runnable() {	

			public void run() {

				Q_2();

			}

		}, 41L, 50L);
	}
	
	public void initiate_Q_3(){
		Q_3 = Warzone.server.getScheduler().scheduleSyncRepeatingTask(Warzone.plugin, new Runnable() {	

			public void run() {

				Q_3();

			}

		}, 41L, 35L);
	}
	
	public void initiate_Q_4(){
		Q_4 = Warzone.server.getScheduler().scheduleSyncRepeatingTask(Warzone.plugin, new Runnable() {	

			public void run() {

				Q_4();

			}

		}, 41L, 80L);
	}
	
	public void initiate_Q_5(){
		Q_5 = Warzone.server.getScheduler().scheduleSyncRepeatingTask(Warzone.plugin, new Runnable() {	

			public void run() {

				Q_5();

			}

		}, 41L, 60L);
	}
	
	public void initiate_R_A(){
		R_A = Warzone.server.getScheduler().scheduleSyncRepeatingTask(Warzone.plugin, new Runnable() {	

			public void run() {

				R_A();

			}

		}, 41L, 20L);
	}

	private void initialiseNPCMap(){
		npcRef.add(AINPC.Adamki11s);
		npcRef.add(AINPC.iPhysX);
		npcRef.add(AINPC.alta189);
		npcRef.add(AINPC.cronikkk);
		npcRef.add(AINPC.Xephos);
		npcRef.add(AINPC.jamescosten);
		npcRef.add(AINPC.Notch);
		npcRef.add(AINPC.EvilSeph);
		npcRef.add(AINPC.Dinnerbone);
		npcRef.add(AINPC.Israphel);
		npcRef.add(AINPC.SeaNanners);
		npcRef.add(AINPC.RightLegRed);
		npcRef.add(AINPC.Afforess);
		npcRef.add(AINPC.Coestar);
		npcRef.add(AINPC.Honeydew);
	}
	
	private void spawnNPCS(){
		for(AINPC ref : npcRef){
			String r = ref.toString();
			Location l = getRandomSpawn();
			spawnNPC(r, l);
		}
		npc.setItemInHand(AINPC.Adamki11s.toString(), Material.GLOWSTONE);
		npc.setItemInHand(AINPC.Afforess.toString(), Material.BOOK);
		npc.setItemInHand(AINPC.alta189.toString(), Material.BED);
		npc.setItemInHand(AINPC.cronikkk.toString(), Material.EGG);
		npc.setItemInHand(AINPC.Dinnerbone.toString(), Material.BEDROCK);
		npc.setItemInHand(AINPC.EvilSeph.toString(), Material.GOLD_BLOCK);
		npc.setItemInHand(AINPC.Coestar.toString(), Material.BOAT);
		npc.setItemInHand(AINPC.jamescosten.toString(), Material.CACTUS);
		npc.setItemInHand(AINPC.iPhysX.toString(), Material.BURNING_FURNACE);
		npc.setItemInHand(AINPC.Honeydew.toString(), Material.GRILLED_PORK);
		npc.setItemInHand(AINPC.Israphel.toString(), Material.GREEN_RECORD);
		npc.setItemInHand(AINPC.Xephos.toString(), Material.LAPIS_BLOCK);
		npc.setItemInHand(AINPC.Notch.toString(), Material.GOLDEN_APPLE);
		npc.setItemInHand(AINPC.SeaNanners.toString(), Material.SOUL_SAND);
		npc.setItemInHand(AINPC.RightLegRed.toString(), Material.PUMPKIN);
		
	}
	
	public void despawnNPCS(){
		for(AINPC ref : npcRef){
			npc.despawn(ref.toString());
		}
		System.out.println("[Warzone] NPC'S despawned successfully.");
	}
	
	private void Q_1(){
		AINPC xq1 = AINPC.Adamki11s, xq2 = AINPC.Afforess, xq3 = AINPC.alta189;
		npc.move(xq1.toString(), getRandomSpawn());
		npc.move(xq2.toString(), getRandomSpawn());
		npc.move(xq3.toString(), getRandomSpawn());
	}
	
	private void Q_2(){
		AINPC xq1 = AINPC.cronikkk, xq2 = AINPC.Dinnerbone, xq3 = AINPC.EvilSeph;
		npc.move(xq1.toString(), getRandomSpawn());
		npc.move(xq2.toString(), getRandomSpawn());
		npc.move(xq3.toString(), getRandomSpawn());
	}
	
	private void Q_3(){
		AINPC xq1 = AINPC.Coestar, xq2 = AINPC.iPhysX, xq3 = AINPC.jamescosten;
		npc.move(xq1.toString(), getRandomSpawn());
		npc.move(xq2.toString(), getRandomSpawn());
		npc.move(xq3.toString(), getRandomSpawn());
	}
	
	private void Q_4(){
		AINPC xq1 = AINPC.Honeydew, xq2 = AINPC.Israphel, xq3 = AINPC.Xephos;
		npc.move(xq1.toString(), getRandomSpawn());
		npc.move(xq2.toString(), getRandomSpawn());
		npc.move(xq3.toString(), getRandomSpawn());
	}
	
	private void Q_5(){
		AINPC xq1 = AINPC.Notch, xq2 = AINPC.SeaNanners, xq3 = AINPC.RightLegRed;
		npc.move(xq1.toString(), getRandomSpawn());
		npc.move(xq2.toString(), getRandomSpawn());
		npc.move(xq3.toString(), getRandomSpawn());
	}
	
	private void R_A(){
		AINPC ra1, ra2;
		int ri1 = exr.getRandomInt(15, 0), ri2 = exr.getRandomInt(15, 0);
		ra1 = getAINPC(ri1);
		ra2 = getAINPC(ri2);
		npc.animateArmSwing(ra1.toString());
		npc.animateArmSwing(ra2.toString());
	}
	
	private AINPC getAINPC(int r){
		AINPC ra1;
		switch(r){
		case 0 : ra1 = AINPC.Adamki11s; break;
		case 1 : ra1 = AINPC.Afforess; break;
		case 2 : ra1 = AINPC.alta189; break;
		case 3 : ra1 = AINPC.cronikkk; break;
		case 4 : ra1 = AINPC.Dinnerbone; break;
		case 5 : ra1 = AINPC.EvilSeph; break;
		case 6 : ra1 = AINPC.Coestar; break;
		case 7 : ra1 = AINPC.iPhysX; break;
		case 8 : ra1 = AINPC.jamescosten; break;
		case 9 : ra1 = AINPC.Honeydew; break;
		case 10 : ra1 = AINPC.Israphel; break;
		case 11 : ra1 = AINPC.Xephos; break;
		case 12 : ra1 = AINPC.Notch; break;
		case 13 : ra1 = AINPC.SeaNanners; break;
		case 14 : ra1 = AINPC.RightLegRed; break;
		default: ra1 = AINPC.Adamki11s;
		}
		return ra1;
	}
	
	
	
	private Location getRandomSpawn(){
		World w = Maps.Warzone_World;//92 - 108, 212 - 232
		double x = 92 + exr.getRandomInt(16, 0),
		y = 74,
		z = 212 + exr.getRandomInt(20, 0);
		x *= -1;
		float _y = 0 + exr.getRandomInt(350, 5),
		_p = -12 + exr.getRandomInt(42, 0);
		
		return new Location(w, x, y, z, _y, _p);	
	}

	private void spawnNPC(String name, Location loc){
		npc.spawn(name, loc);
	}

}