package flaxbeard.thaumicexploration.event;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITaskEntry;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.ItemEssence;
import flaxbeard.thaumicexploration.ThaumicExploration;
import flaxbeard.thaumicexploration.data.TXWorldDataInfoWorldData;
import flaxbeard.thaumicexploration.item.ItemBlankSeal;
import flaxbeard.thaumicexploration.tile.TileEntityBoundChest;
import flaxbeard.thaumicexploration.tile.TileEntityBoundJar;

public class TXEventHandler {
	public TXEventHandler() {
		System.out.println("TEST123");
	}
	
	@ForgeSubscribe
	public void handleWorldLoad(WorldEvent.Load event) {
		TXWorldDataInfoWorldData.get(event.world);
	}
	
	@ForgeSubscribe
	public void handleItemUse(PlayerInteractEvent event) {
		if (event.entityPlayer.worldObj.blockExists(event.x, event.y, event.z)) {
			System.out.println(event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z) + " " + ThaumicExploration.boundChest.blockID);
			if (event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z) == Block.chest.blockID) {
				System.out.println("chest");
				if (event.entityPlayer.inventory.getCurrentItem() != null){ 
					if (event.entityPlayer.inventory.getCurrentItem().itemID == ThaumicExploration.chestSeal.itemID) {
						World world = event.entityPlayer.worldObj;
						
						world.setBlock(event.x, event.y, event.z, ThaumicExploration.boundChest.blockID, event.entityPlayer.worldObj.getBlockMetadata(event.x, event.y, event.z),1);
						int nextID = TXWorldDataInfoWorldData.get(world).getNextBoundChestID();
						((TileEntityBoundChest) world.getBlockTileEntity(event.x, event.y, event.z)).id = nextID;
						((TileEntityBoundChest) world.getBlockTileEntity(event.x, event.y, event.z)).setColor(15-event.entityPlayer.inventory.getCurrentItem().getItemDamage());
	//					ItemStack linkedSeal = new ItemStack(ThaumicExploration.chestSealLinked.itemID, 1, event.entityPlayer.inventory.getCurrentItem().getItemDamage());
	//					NBTTagCompound tag = new NBTTagCompound();
	//					tag.setInteger("ID", nextID);
	//					linkedSeal.setTagCompound(tag);
	//					event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, linkedSeal);
						event.entityPlayer.inventory.decrStackSize(event.entityPlayer.inventory.currentItem, 1);
						//event.setCanceled(true);
					}
					else if (event.entityPlayer.inventory.getCurrentItem().itemID == ThaumicExploration.chestSealLinked.itemID) {
						World world = event.entityPlayer.worldObj;
						//System.out.println("dafuk");
						world.setBlock(event.x, event.y, event.z, ThaumicExploration.boundChest.blockID, event.entityPlayer.worldObj.getBlockMetadata(event.x, event.y, event.z),1);
						int nextID = event.entityPlayer.inventory.getCurrentItem().stackTagCompound.getInteger("ID");
						((TileEntityBoundChest) world.getBlockTileEntity(event.x, event.y, event.z)).id = nextID;
						((TileEntityBoundChest) world.getBlockTileEntity(event.x, event.y, event.z)).setColor(15-event.entityPlayer.inventory.getCurrentItem().getItemDamage());
						
						event.entityPlayer.inventory.decrStackSize(event.entityPlayer.inventory.currentItem, 1);
						//event.setCanceled(true);
					}
				}
			}
		
			else if (event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z) == ThaumicExploration.boundChest.blockID) {
				System.out.println("bound chest");
				World world = event.entityPlayer.worldObj;
				System.out.println(event.entityPlayer.worldObj.isRemote + ItemBlankSeal.itemNames[((TileEntityBoundChest) world.getBlockTileEntity(event.x, event.y, event.z)).getSealColor()]);
				
				if (event.entityPlayer.inventory.getCurrentItem() != null){ 
					if (event.entityPlayer.inventory.getCurrentItem().itemID == ThaumicExploration.chestSeal.itemID ) {
						int color = ((TileEntityBoundChest) world.getBlockTileEntity(event.x, event.y, event.z)).getSealColor();		
						if (15-(event.entityPlayer.inventory.getCurrentItem().getItemDamage()) == color) {
							int nextID = ((TileEntityBoundChest) world.getBlockTileEntity(event.x, event.y, event.z)).id;
							ItemStack linkedSeal = new ItemStack(ThaumicExploration.chestSealLinked.itemID, 1, event.entityPlayer.inventory.getCurrentItem().getItemDamage());
							NBTTagCompound tag = new NBTTagCompound();
							tag.setInteger("ID", nextID);
							tag.setInteger("x", event.x);
							tag.setInteger("y", event.y);
							tag.setInteger("z", event.z);
							tag.setInteger("dim", event.entityPlayer.worldObj.provider.dimensionId);
							linkedSeal.setTagCompound(tag);
							event.entityPlayer.inventory.addItemStackToInventory(linkedSeal);
							event.entityPlayer.inventory.decrStackSize(event.entityPlayer.inventory.currentItem, 1);
							//event.setCanceled(true);
						}
					}
				}
			}
			else if (event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z) == ThaumicExploration.boundJar.blockID) {
				//System.out.println(((TileEntityBoundJar)event.entityPlayer.worldObj.getBlockTileEntity(event.x, event.y, event.z)).aspect.toString());
			}
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
