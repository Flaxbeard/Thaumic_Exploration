package flaxbeard.thaumicexploration.event;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import thaumcraft.client.fx.FXLightningBolt;
import thaumcraft.common.config.Config;
import thaumcraft.common.lib.Utils;
import flaxbeard.thaumicexploration.ThaumicExploration;

public class TXBootsEventHandler
{
  
  public static final PlayerCapabilities genericPlayerCapabilities = new PlayerCapabilities();
  
  @ForgeSubscribe
  public void livingTick(LivingEvent.LivingUpdateEvent event)
  {
    if ((event.entity instanceof EntityPlayer))
    {
    	EntityPlayer player = (EntityPlayer)event.entity;
        Utils.setWalkSpeed(player.capabilities, Utils.getWalkSpeed(genericPlayerCapabilities));
        updateSpeed(player);
        checkAir(player);
        if (player.getCurrentItemOrArmor(4) != null) {
	        if (player.getCurrentItemOrArmor(4).itemID == ThaumicExploration.maskEvil.itemID && player.username.equalsIgnoreCase("Succubism")) {
	        	player.worldObj.spawnParticle("heart", (double)(player.posX + Math.random()-0.5F), (double)(player.boundingBox.maxY + Math.random()/2), (double)(player.posZ + Math.random()-0.5F), 0.0D, 0.0D, 0.0D);        	
	        }

        }
    }
    
    

    if ((event.entity.worldObj.isRemote) && (this.prevStep.containsKey(Integer.valueOf(event.entity.entityId))) && ((((EntityPlayer)event.entity).inventory.armorItemInSlot(0) == null) || (((EntityPlayer)event.entity).inventory.armorItemInSlot(0).getItem().itemID != ThaumicExploration.bootsMeteor.itemID && ((EntityPlayer)event.entity).inventory.armorItemInSlot(0).getItem().itemID != ThaumicExploration.bootsComet.itemID)))
    {
      event.entity.stepHeight = ((Float)this.prevStep.get(Integer.valueOf(event.entity.entityId))).floatValue();
      this.prevStep.remove(Integer.valueOf(event.entity.entityId));
    
    }
   
    
  }
  
  private void updateSpeed(EntityPlayer player)
  {
    if ((!player.capabilities.isFlying) && (player.inventory.armorItemInSlot(0) != null))
    {
      int haste = EnchantmentHelper.getEnchantmentLevel(Config.enchHaste.effectId, player.inventory.armorItemInSlot(0));
      if (player.inventory.armorItemInSlot(0).getItem().itemID == ThaumicExploration.bootsMeteor.itemID)
      {
        if (player.worldObj.isRemote)
        {
          if (!this.prevStep.containsKey(Integer.valueOf(player.entityId))) {
            this.prevStep.put(Integer.valueOf(player.entityId), Float.valueOf(player.stepHeight));
          }
          player.stepHeight = 1.0F;
        }
        float bonus = 0.0F;
        if (haste > 0) {
          bonus = haste * 0.007F;
        }
        Utils.setWalkSpeed(player.capabilities, Utils.getWalkSpeed(player.capabilities) + 0.055F + bonus);
        

        player.jumpMovementFactor = (0.033F + bonus * 0.66F);
        if (player.isInWater())
        {
          player.motionX *= 1.133000016212463D;
          player.motionZ *= 1.133000016212463D;
        }
        if (player.fallDistance > 0.0F) {
          player.fallDistance = 0.0F;
        }
      }
      else if (player.inventory.armorItemInSlot(0).getItem().itemID == ThaumicExploration.bootsComet.itemID)
      {
          if (player.worldObj.isRemote)
          {
            if (!this.prevStep.containsKey(Integer.valueOf(player.entityId))) {
              this.prevStep.put(Integer.valueOf(player.entityId), Float.valueOf(player.stepHeight));
            }
            player.stepHeight = 1.0F;
          }
          if (!player.inventory.armorItemInSlot(0).hasTagCompound()) {
    			NBTTagCompound par1NBTTagCompound = new NBTTagCompound();
    			player.inventory.armorItemInSlot(0).setTagCompound(par1NBTTagCompound );
    			player.inventory.armorItemInSlot(0).stackTagCompound.setInteger("runTicks",0);
          }
          int ticks = player.inventory.armorItemInSlot(0).stackTagCompound.getInteger("runTicks");
          float bonus = 0.0F;
          if (haste > 0) {
            bonus = haste * 0.007F;
          }
          bonus = bonus + ((ticks/5) * 0.003F);
          Utils.setWalkSpeed(player.capabilities, Utils.getWalkSpeed(player.capabilities) + 0.110F + bonus);
          

          player.jumpMovementFactor = (0.033F + bonus * 0.66F);
          if (player.isInWater())
          {
            player.motionX *= 1.133000016212463D;
            player.motionZ *= 1.133000016212463D;
          }
          if (player.fallDistance > 0.0F) {
            player.fallDistance = 0.0F;
          }
        }
      
    }
  }
  
  @ForgeSubscribe
  public void joinWorld(EntityJoinWorldEvent event)
  {
    if ((event.entity instanceof EntityPlayer))
    {
      EntityPlayer player = (EntityPlayer)event.entity;
      updateSpeed(player);
    }
  }
  
  HashMap<Integer, Float> prevStep = new HashMap();
  
  @ForgeSubscribe
  public void playerJumps(LivingEvent.LivingJumpEvent event)
  {
	  
    if (((event.entity instanceof EntityPlayer)) && (((EntityPlayer)event.entity).inventory.armorItemInSlot(0) != null) && (((EntityPlayer)event.entity).inventory.armorItemInSlot(0).getItem().itemID == ThaumicExploration.bootsMeteor.itemID)) {
      if (((EntityPlayer)event.entity).isSneaking()) {
    	  Vec3 vector = event.entityLiving.getLook(0.5F);
	      double total = vector.zCoord + vector.xCoord;
	      EntityPlayer player = (EntityPlayer)event.entity;
	
	      if (vector.yCoord < total)
	    	  vector.yCoord = total;
	      if (vector.yCoord > 0)
	      event.entityLiving.motionY += vector.yCoord/1.5F;
	      event.entityLiving.motionZ += vector.zCoord*4;
	      event.entityLiving.motionX += vector.xCoord*4;
      }
      else
      {
    	  event.entityLiving.motionY += 0.2750000059604645D;
      }
    }
    else if (((event.entity instanceof EntityPlayer)) && (((EntityPlayer)event.entity).inventory.armorItemInSlot(0) != null) && (((EntityPlayer)event.entity).inventory.armorItemInSlot(0).getItem().itemID == ThaumicExploration.bootsComet.itemID)) {
    	event.entityLiving.motionY += 0.2750000059604645D;
      }
    
  }
  
  public void checkAir(EntityPlayer player)
  {  
    if ((player.inventory.armorItemInSlot(0) != null) && (player.inventory.armorItemInSlot(0).getItem().itemID == ThaumicExploration.bootsMeteor.itemID)) {
      
      Vec3 vector = player.getLook(1.0F);
      ItemStack item = player.inventory.armorItemInSlot(0);
      if (!item.hasTagCompound()) {
			NBTTagCompound par1NBTTagCompound = new NBTTagCompound();
			item.setTagCompound(par1NBTTagCompound );
			item.stackTagCompound.setBoolean("IsSmashing",false);
			item.stackTagCompound.setInteger("smashTicks",0);
			item.stackTagCompound.setInteger("airTicks",0);
      }
      boolean smashing = item.stackTagCompound.getBoolean("IsSmashing");
      int ticks = item.stackTagCompound.getInteger("smashTicks");
      int ticksAir = item.stackTagCompound.getInteger("airTicks");

      if (player.onGround) {
    	  int size = 0;
    	  if (ticks > 5)
    		  size = 1;
    	  if (ticks > 10)
    		  size = 2;
    	  if (ticks > 15)
    		  size = 3;
    	  smashing = false;
    	  ticks = 0;
    	  ticksAir = 0;
    	  if (size > 0) {

    		  player.worldObj.createExplosion(player, player.posX, player.posY, player.posZ, size, false);
      	  }
      }
      if (player.capabilities.isFlying) {
    	  smashing = false;
    	  ticks = 0;
    	  ticksAir = 0;
      }
      if (smashing) {
    	  
          player.worldObj.spawnParticle("flame", (double)(player.posX + Math.random()-0.5F), (double)(player.posY + Math.random()-0.5F), (double)(player.posZ + Math.random()-0.5F), 0.0D, 0.0D, 0.0D);
          player.worldObj.spawnParticle("smoke", (double)(player.posX + Math.random()-0.5F), (double)(player.posY + Math.random()-0.5F), (double)(player.posZ + Math.random()-0.5F), 0.0D, 0.0D, 0.0D);
          player.worldObj.spawnParticle("flame", (double)(player.posX + Math.random()-0.5F), (double)(player.posY + Math.random()-0.5F), (double)(player.posZ + Math.random()-0.5F), 0.0D, 0.0D, 0.0D);
    	  player.motionY-= 0.1F;
    	  ticks++;
      }
      else
      {
    	  double motion = Math.abs(player.motionX) + Math.abs(player.motionZ) + Math.abs(0.5*player.motionY);
    	  if (!player.isWet() && motion > 0.1F) {

    	  player.worldObj.spawnParticle("flame", (double)(player.posX + Math.random()-0.5F), (double)(player.boundingBox.minY + 0.25F + ((Math.random()-0.5)*0.25F)), (double)(player.posZ + Math.random()-0.5F), 0.0D, 0.025D, 0.0D);
    	  player.worldObj.spawnParticle("flame", (double)(player.posX + Math.random()-0.5F), (double)(player.boundingBox.minY + 0.25F + ((Math.random()-0.5)*0.25F)), (double)(player.posZ + Math.random()-0.5F), 0.0D, 0.025D, 0.0D);
    	  }
      }
      item.stackTagCompound.setBoolean("IsSmashing", smashing);
      item.stackTagCompound.setInteger("smashTicks", ticks);
      item.stackTagCompound.setInteger("airTicks", ticksAir);
    }
    else if ((player.inventory.armorItemInSlot(0) != null) && (player.inventory.armorItemInSlot(0).getItem().itemID == ThaumicExploration.bootsComet.itemID)) {
        
        Vec3 vector = player.getLook(1.0F);
        ItemStack item = player.inventory.armorItemInSlot(0);
        if (!item.hasTagCompound()) {
  			NBTTagCompound par1NBTTagCompound = new NBTTagCompound();
  			item.setTagCompound(par1NBTTagCompound );
  			item.stackTagCompound.setInteger("runTicks",0);
        }
        for (int x = -5; x < 6; x++) {
      	  for (int z = -5; z < 6; z++) {
  		      if (player.worldObj.getBlockMaterial((int) (player.posX + x), (int) player.posY-1, (int) (player.posZ + z)) == Material.water && player.worldObj.getBlockMetadata((int) (player.posX + x), (int) player.posY-1, (int) (player.posZ + z)) == 0 && !player.isInWater() && (Math.abs(x)+Math.abs(z) < 8)) {
  		    	  player.worldObj.setBlock((int) (player.posX + x), (int) player.posY-1, (int) (player.posZ + z), ThaumicExploration.meltyIce.blockID);
  		    	player.worldObj.spawnParticle("snowballpoof", (int) (player.posX + x), (int) player.posY, (int) (player.posZ + z), 0.0D, 0.025D, 0.0D);
  		      }
      	  }
        }
        int ticks = item.stackTagCompound.getInteger("runTicks");

       

		double motion = Math.abs(player.motionX) + Math.abs(player.motionZ) + Math.abs(player.motionY);
		if (motion > 0.1F || !player.onGround) {
			if (ticks < 100)
				ticks++;
		}
		else
		{
			ticks = 0;
		}
		
		if (!player.isWet() && motion > 0.1F  ) {
			 player.worldObj.spawnParticle("snowballpoof", (double)(player.posX + Math.random()-0.5F), (double)(player.boundingBox.minY + 0.25F + ((Math.random()-0.5)*0.25F)), (double)(player.posZ + Math.random()-0.5F), 0.0D, 0.025D, 0.0D);
	    	  player.worldObj.spawnParticle("snowballpoof", (double)(player.posX + Math.random()-0.5F), (double)(player.boundingBox.minY + 0.25F + ((Math.random()-0.5)*0.25F)), (double)(player.posZ + Math.random()-0.5F), 0.0D, 0.025D, 0.0D);
		}
        
        item.stackTagCompound.setInteger("runTicks", ticks);
      }
    
  }
}