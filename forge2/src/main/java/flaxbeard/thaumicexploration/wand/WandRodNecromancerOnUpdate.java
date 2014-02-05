package flaxbeard.thaumicexploration.wand;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import thaumcraft.api.wands.IWandRodOnUpdate;
import thaumcraft.common.entities.EntityAspectOrb;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.InventoryHelper;
import thaumcraft.common.lib.world.DamageSourceThaumcraft;
import flaxbeard.thaumicexploration.ThaumicExploration;

public class WandRodNecromancerOnUpdate implements IWandRodOnUpdate {
	
	private static final int intialClock = 6000;

	@Override
	public void onUpdate(ItemStack itemstack, EntityPlayer par1EntityPlayer) {
		  if (!itemstack.hasTagCompound()) {
			  itemstack.setTagCompound(new NBTTagCompound());
		  }
		  if (!itemstack.stackTagCompound.hasKey("eatTicks")) {
			  itemstack.stackTagCompound.setInteger("eatTicks",0);
			  itemstack.stackTagCompound.setInteger("eatClock",intialClock);
		  }
		  int eatTicks = itemstack.stackTagCompound.getInteger("eatTicks");
		  int eatClock = itemstack.stackTagCompound.getInteger("eatClock");
		  if (eatTicks > eatClock) {
			  par1EntityPlayer.attackEntityFrom(DamageSourceThaumcraft.taint, 1);
			  eatTicks = 0;
			  eatClock = eatClock / 2 ;
			  if (eatClock <= 1) {
				  eatClock = 1;
			  }
		  }
		  eatTicks++;
		  AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(par1EntityPlayer.posX - 3, par1EntityPlayer.posY - 3, par1EntityPlayer.posZ - 3, par1EntityPlayer.posX + 3, par1EntityPlayer.posY + 3, par1EntityPlayer.posZ + 3);
          List<EntityAspectOrb> orbs = par1EntityPlayer.worldObj.getEntitiesWithinAABB(EntityAspectOrb.class, boundingBox);

          for(EntityAspectOrb orb : orbs) {
        	  if (!orb.isDead) {
	        	  int slot = InventoryHelper.isWandInHotbarWithRoom(orb.getAspect(), orb.getAspectValue(), par1EntityPlayer);
	    	      if ((orb.orbCooldown == 0) && (par1EntityPlayer.xpCooldown == 0) && (orb.getAspect().isPrimal()) && (slot >= 0))
	    	      {
	    	        ItemWandCasting wand = (ItemWandCasting)par1EntityPlayer.inventory.mainInventory[slot].getItem();
	    	        if (wand.getRod(par1EntityPlayer.inventory.mainInventory[slot]) == ThaumicExploration.WAND_ROD_CRYSTAL) {
	    	        	eatTicks = 0;
	    	        	eatClock = intialClock;
	    	        	wand.addVis(par1EntityPlayer.inventory.mainInventory[slot], orb.getAspect(), orb.getAspectValue()*3, true);
	    	        }
	    	        wand.addVis(par1EntityPlayer.inventory.mainInventory[slot], orb.getAspect(), orb.getAspectValue(), true);
	    	        
	    	        par1EntityPlayer.xpCooldown = 2;
	    	        orb.playSound("random.orb", 0.1F, 0.5F * ((par1EntityPlayer.worldObj.rand.nextFloat() - par1EntityPlayer.worldObj.rand.nextFloat()) * 0.7F + 1.8F));
	    	        orb.setDead();
	    	      }
        	  }
          }
		  itemstack.stackTagCompound.setInteger("eatTicks",eatTicks);
		  itemstack.stackTagCompound.setInteger("eatClock",eatClock);
	}
}
