package flaxbeard.thaumicexploration.event;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import thaumcraft.common.config.ConfigBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITaskEntry;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flaxbeard.thaumicexploration.ThaumicExploration;
import flaxbeard.thaumicexploration.data.TXWorldDataInfoWorldData;
import flaxbeard.thaumicexploration.item.ItemBlankSeal;
import flaxbeard.thaumicexploration.tile.TileEntityBoundChest;
import flaxbeard.thaumicexploration.tile.TileEntityBoundJar;

public class TXEventHandler {
	public TXEventHandler() {
		//System.out.println("TEST123");
	}
	
	@ForgeSubscribe
	public void handleWorldLoad(WorldEvent.Load event) {
		TXWorldDataInfoWorldData.get(event.world);
	}
	
	@ForgeSubscribe
	
	public void handleItemUse(PlayerInteractEvent event) {
		byte type = 0;
		
		if (event.entityPlayer.worldObj.blockExists(event.x, event.y, event.z)) {
			//System.out.println(event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z) + " " + ThaumicExploration.boundChest.blockID);
			if (event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z) == Block.chest.blockID) {
				//System.out.println("chest");
				if (event.entityPlayer.inventory.getCurrentItem() != null){ 
					if (event.entityPlayer.inventory.getCurrentItem().itemID == ThaumicExploration.chestSeal.itemID) {
						type = 1;
					}
					else if (event.entityPlayer.inventory.getCurrentItem().itemID == ThaumicExploration.chestSealLinked.itemID) {
						type = 2;
					}
				}
			}
			else if (event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z) == ThaumicExploration.boundChest.blockID) {
				//System.out.println("bound chest");
				World world = event.entityPlayer.worldObj;
				//System.out.println(event.entityPlayer.worldObj.isRemote + ItemBlankSeal.itemNames[((TileEntityBoundChest) world.getBlockTileEntity(event.x, event.y, event.z)).getSealColor()]);
				if (event.entityPlayer.inventory.getCurrentItem() != null){ 
					if (event.entityPlayer.inventory.getCurrentItem().itemID == ThaumicExploration.chestSeal.itemID ) {
						int color = ((TileEntityBoundChest) world.getBlockTileEntity(event.x, event.y, event.z)).getSealColor();		
						type = 3;
						event.setCanceled(true);
					}
				}
			}
		}
		


		if (event.entityPlayer.worldObj.blockExists(event.x, event.y, event.z)) {
			//System.out.println(event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z) + " " + ThaumicExploration.boundJar.blockID);
			if (event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z) == ConfigBlocks.blockJar.blockID && event.entityPlayer.worldObj.getBlockMetadata(event.x, event.y, event.z) == 0) {
				//System.out.println("itsa jar mario");
				if (event.entityPlayer.inventory.getCurrentItem() != null){ 
					if (event.entityPlayer.inventory.getCurrentItem().itemID == ThaumicExploration.jarSeal.itemID) {
						type = 4;
					}
					else if (event.entityPlayer.inventory.getCurrentItem().itemID == ThaumicExploration.jarSealLinked.itemID) {
						type = 5;
					}
				}
			}
			else if (event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z) == ThaumicExploration.boundJar.blockID) {
				//System.out.println("bound jar");
				World world = event.entityPlayer.worldObj;
				//System.out.println(event.entityPlayer.worldObj.isRemote + ItemBlankSeal.itemNames[((TileEntityBoundJar) world.getBlockTileEntity(event.x, event.y, event.z)).getSealColor()]);
				if (event.entityPlayer.inventory.getCurrentItem() != null){ 
					if (event.entityPlayer.inventory.getCurrentItem().itemID == ThaumicExploration.jarSeal.itemID ) {
						int color = ((TileEntityBoundJar) world.getBlockTileEntity(event.x, event.y, event.z)).getSealColor();		
						type = 6;
						event.setCanceled(true);
					}
				}
			}
		}
		
		
		
		if (event.entityPlayer.worldObj.isRemote && type > 0) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
	        DataOutputStream outputStream = new DataOutputStream(bos);
	
	        try
	        {
	            outputStream.writeByte(1);
	            outputStream.writeInt(event.entityPlayer.worldObj.provider.dimensionId);
	            outputStream.writeInt(event.x);
	            outputStream.writeInt(event.y);
	            outputStream.writeInt(event.z);
	            outputStream.writeByte(type);
	            outputStream.writeInt( event.entityPlayer.entityId);
	           
	        }
	        catch (Exception ex)
	        {
	            ex.printStackTrace();
	        }
	
	        Packet250CustomPayload packet = new Packet250CustomPayload();
	        packet.channel = "tExploration";
	        packet.data = bos.toByteArray();
	        packet.length = bos.size();
	        PacketDispatcher.sendPacketToAllPlayers(packet);
	        //System.out.println("sent");
		}

		
	}
	
//	@ForgeSubscribe
//	public void handler(EntityJoinWorldEvent event) {
//		if( event.entity instanceof EntityZombie )
//        {
//                EntityZombie zombie = (EntityZombie)event.entity;
//                //if (hasTask(new EntityAINearestAttackableTarget(zombie, EntityItemBrain.class, 0, false), zombie)) {
//                	//zombie.targetTasks.addTask(1, new EntityAINearestAttackableTarget(zombie, EntityItem.class, 0, false));
//                	//zombie.tasks.addTask(3, new EntityAIAttackOnCollide(zombie, EntityItem.class, 1.0D, true));
//                	zombie.tasks.addTask(0, new EntityAINearestEntity(zombie, EntityItem.class, 0, true));
//                	//}
//                	
//        }
//	}
	
	boolean hasTask(EntityAIBase task, EntityZombie zombie)
	{
		 for(Object taskEntryObj : zombie.targetTasks.taskEntries) { 
			 EntityAITaskEntry taskEntry = (EntityAITaskEntry)taskEntryObj; 
			 /* Blame the deobfuscation not supporting generics for this one */ 
//			 if( taskEntry.action instanceof task) { 
//				 return true; 
//			 } 
		} return false;
		
	}

}
