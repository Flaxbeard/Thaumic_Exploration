package flaxbeard.thaumicexploration.wand;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
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
			  System.out.println("starting compound tag");
			  itemstack.setTagCompound(new NBTTagCompound());
			  NBTTagCompound tag = new NBTTagCompound();
			  ItemWandCasting wand = (ItemWandCasting)itemstack.getItem();
			  for (Aspect aspect : Aspect.getPrimalAspects()) {
				  tag.setInteger(aspect.getName(), wand.getVis(itemstack, aspect));
			  }
			  itemstack.stackTagCompound.setTag("lastAspects", tag);
		  }
		  ItemWandCasting thisWand = (ItemWandCasting)itemstack.getItem();
		  NBTTagCompound lastAspectTag = itemstack.stackTagCompound.getCompoundTag("lastAspects");
		  AspectList lastAspects = new AspectList();
		  for (Aspect aspect : Aspect.getPrimalAspects()) {
			  lastAspects.add(aspect,lastAspectTag.getInteger(aspect.getName()));
		  }
		  AspectList currentAspects = thisWand.getAllVis(itemstack);
		  for (Aspect aspect : Aspect.getPrimalAspects()) {
			  int diff = currentAspects.getAmount(aspect) - lastAspects.getAmount(aspect);
			  if (diff > 0) {
				  diff = (int) (diff*0.75F);
			  }
			  lastAspects.add(aspect, diff);
		  }
		  
		  AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(par1EntityPlayer.posX - 3, par1EntityPlayer.posY - 3, par1EntityPlayer.posZ - 3, par1EntityPlayer.posX + 3, par1EntityPlayer.posY + 3, par1EntityPlayer.posZ + 3);
          List<EntityAspectOrb> orbs = par1EntityPlayer.worldObj.getEntitiesWithinAABB(EntityAspectOrb.class, boundingBox);


		  
          thisWand.storeAllVis(itemstack, lastAspects);
          NBTTagCompound tag = new NBTTagCompound();
		  
		  for (Aspect aspect : Aspect.getPrimalAspects()) {
			  tag.setInteger(aspect.getName(), lastAspects.getAmount(aspect));
		  }
		  itemstack.stackTagCompound.setTag("lastAspects", tag);
		  
          for(EntityAspectOrb orb : orbs) {
        	  if (!orb.isDead) {
	        	  int slot = InventoryHelper.isWandInHotbarWithRoom(orb.getAspect(), orb.getAspectValue(), par1EntityPlayer);
	    	      if ((orb.orbCooldown == 0) && (par1EntityPlayer.xpCooldown == 0) && (orb.getAspect().isPrimal()) && (slot >= 0))
	    	      {
	    	        ItemWandCasting wand = (ItemWandCasting)par1EntityPlayer.inventory.mainInventory[slot].getItem();
	    	        if (wand.getRod(par1EntityPlayer.inventory.mainInventory[slot]) == ThaumicExploration.WAND_ROD_NECRO) {
	    	        	wand.addVis(par1EntityPlayer.inventory.mainInventory[slot], orb.getAspect(), (int) (orb.getAspectValue()*1.6666666), true);
	    	        }
	    	        wand.addVis(par1EntityPlayer.inventory.mainInventory[slot], orb.getAspect(), orb.getAspectValue(), true);
	    	        
	    	        par1EntityPlayer.xpCooldown = 2;
	    	        orb.playSound("random.orb", 0.1F, 0.5F * ((par1EntityPlayer.worldObj.rand.nextFloat() - par1EntityPlayer.worldObj.rand.nextFloat()) * 0.7F + 1.8F));
	    	        orb.setDead();
	    	      }
        	  }
          }
	}
}
