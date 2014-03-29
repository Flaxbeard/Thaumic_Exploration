package flaxbeard.thaumicexploration.event;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.nodes.INode;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.PotionFluxTaint;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;
import thaumcraft.common.tiles.TileNode;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import flaxbeard.thaumicexploration.ThaumicExploration;
import flaxbeard.thaumicexploration.integration.TTIntegration;

public class TXBootsEventHandler
{
  
  public static final PlayerCapabilities genericPlayerCapabilities = new PlayerCapabilities();
  
  
  @SubscribeEvent
  public void livingTick(LivingEvent.LivingUpdateEvent event)
  {
	if (event.entity instanceof EntityPlayer) {
//		PlayerKnowledge rp = Thaumcraft.proxy.getPlayerKnowledge();
//		if (!rp.hasDiscoveredAspect(((EntityPlayer)event.entity).username, ThaumicExploration.fakeAspectNecro)) {
//			System.out.println(event.entity.worldObj.isRemote+" Discovering fake aspect");
//			//PacketHandler.sendAspectDiscoveryPacket(ThaumicExploration.fakeAspectNecro.getTag(), (EntityPlayerMP)event.entity);
//			
//			rp.addDiscoveredAspect(((EntityPlayer)event.entity).username, ThaumicExploration.fakeAspectNecro);
//			if (rp.hasDiscoveredAspect(((EntityPlayer)event.entity).username, ThaumicExploration.fakeAspectNecro)) {
//				System.out.println(event.entity.worldObj.isRemote+" has discovered fake aspect");
//			}
//		}
	}
    if ((event.entity instanceof EntityPlayer))
    {
    	EntityPlayer player = (EntityPlayer)event.entity;
        //Utils.setWalkSpeed(player.capabilities, Utils.getWalkSpeed(genericPlayerCapabilities));
        //updateSpeed(player);
        checkAir(player);
        
        if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemWandCasting) {
        	ItemWandCasting wand = (ItemWandCasting) player.getHeldItem().getItem();
        	if (wand.getObjectInUse(player.getHeldItem(), player.worldObj) != null && wand.getCap(player.getHeldItem()) == ThaumicExploration.WAND_CAP_MECHANIST) {
        		if (wand.getObjectInUse(player.getHeldItem(), player.worldObj) instanceof INode) {
            		if (!player.getHeldItem().hasTagCompound()) {
            			player.getHeldItem().setTagCompound(new NBTTagCompound());
            		}
            		if (!player.getHeldItem().stackTagCompound.hasKey("nodeTicks")) {
            			player.getHeldItem().stackTagCompound.setInteger("nodeTicks", 0);
            		}
            		int nodeTicks = player.getHeldItem().stackTagCompound.getInteger("nodeTicks");
            		nodeTicks++;
            		
        			TileNode node = (TileNode) wand.getObjectInUse(player.getHeldItem(), player.worldObj);
					node.onUsingWandTick(player.getHeldItem(), player, nodeTicks);
					if (nodeTicks%3==0) {
						if (player.worldObj.rand.nextBoolean()) {
							player.worldObj.playSound(node.xCoord, node.yCoord, node.zCoord, "tile.piston.in", 0.1F+(float)(0.5F*Math.random()), 0.75F, false);
						}
						else
						{
							player.worldObj.playSound(node.xCoord, node.yCoord, node.zCoord, "tile.piston.out", 0.1F+(float)(0.5F*Math.random()), 0.75F, false);
						}
					}
        			player.getHeldItem().stackTagCompound.setInteger("nodeTicks", nodeTicks);
        		}
        	}
        	if (wand.getCap(player.getHeldItem()) == ThaumicExploration.WAND_CAP_SOJOURNER && !player.worldObj.isRemote) {
        		if (!player.getHeldItem().hasTagCompound()) {
        			player.getHeldItem().setTagCompound(new NBTTagCompound());
        		}
        		if (!player.getHeldItem().stackTagCompound.hasKey("nodeTicks")) {
        			player.getHeldItem().stackTagCompound.setInteger("nodeTicks", 0);
        			player.getHeldItem().stackTagCompound.setInteger("drainX", 0);
        			player.getHeldItem().stackTagCompound.setInteger("drainY", 0);
        			player.getHeldItem().stackTagCompound.setInteger("drainZ", 0);
        		}
        		int nodeTicks = player.getHeldItem().stackTagCompound.getInteger("nodeTicks");
        		nodeTicks++;
        		if (nodeTicks >= 20) {
        			AspectList emptyAspects = new AspectList();
        			for (Aspect aspect : Aspect.getPrimalAspects()) {
        				if (wand.getVis(player.getHeldItem(), aspect) < wand.getMaxVis(player.getHeldItem())) {
        					emptyAspects.add(aspect, 1);
        				}
        			}
        			ArrayList<ChunkCoordinates> nodes = new ArrayList<ChunkCoordinates>();
        		    for (int xx = -8; xx <= 8; xx++) {
        		      for (int yy = -8; yy <= 8; yy++) {
        		        for (int zz = -8; zz <= 8; zz++)
        		        {
        		          TileEntity te = player.worldObj.getTileEntity(((int)player.posX) + xx, ((int)player.posY) + yy, ((int)player.posZ) + zz);
        		          if ((te instanceof INode) && emptyAspects.size() > 0) {
        		        	boolean canAdd = false;
        		        	for (Aspect aspect : emptyAspects.getAspects()) {
        		        		if (((INode) te).getAspects().getAmount(aspect) > 1) {
        		        			canAdd = true;
        		        		}
        		        	}
        		        	if (canAdd)
        		        		nodes.add(new ChunkCoordinates(((int)player.posX) + xx, ((int)player.posY) + yy,((int)player.posZ) + zz));
        		          }
        		        }
        		      }
        		    }
        		    ChunkCoordinates randNode;
        		    if (nodes.size() != 0) {
	        		    if (!nodes.contains(new ChunkCoordinates(player.getHeldItem().stackTagCompound.getInteger("drainX"),player.getHeldItem().stackTagCompound.getInteger("drainY"),player.getHeldItem().stackTagCompound.getInteger("drainZ")))) {
	        		    	randNode = nodes.get(player.worldObj.rand.nextInt(nodes.size()));
	            			player.getHeldItem().stackTagCompound.setInteger("drainX", randNode.posX);
	            			player.getHeldItem().stackTagCompound.setInteger("drainY", randNode.posY);
	            			player.getHeldItem().stackTagCompound.setInteger("drainZ", randNode.posZ);
	        		    }
	        		    else
	        		    {
	        		    	randNode = new ChunkCoordinates(new ChunkCoordinates(player.getHeldItem().stackTagCompound.getInteger("drainX"),player.getHeldItem().stackTagCompound.getInteger("drainY"),player.getHeldItem().stackTagCompound.getInteger("drainZ")));
	        		    }
	        		    INode node = (INode) player.worldObj.getTileEntity(randNode.posX, randNode.posY, randNode.posZ);
	        		    AspectList possibleAspects = new AspectList();
    		        	for (Aspect aspect : emptyAspects.getAspects()) {
    		        		if (node.getAspects().getAmount(aspect) > 1) {
    		        			possibleAspects.add(aspect, 1);
    		        		}
    		        	}
    		        	Aspect takeAspect = possibleAspects.getAspects()[player.worldObj.rand.nextInt(possibleAspects.getAspects().length)];
	        		    node.takeFromContainer(takeAspect, 1);
	        		    player.worldObj.markBlockForUpdate(randNode.posX, randNode.posY, randNode.posZ);
	        		    wand.addVis(player.getHeldItem(), takeAspect, 1, true);
        		    }

        		    nodeTicks = 0;
        		}
        		player.getHeldItem().stackTagCompound.setInteger("nodeTicks", nodeTicks);
        	}
        }
        
        boolean isTainted = false;
        for (int i = 0; i<10; i++) {
			if (player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).getItem() == ThaumicExploration.charmTaint) {
				isTainted = true;
				break;
			}
		}
        
        if (!player.getEntityData().hasKey("tainted")) {
        	player.getEntityData().setBoolean("tainted", isTainted);
        }
        boolean wasTainted = player.getEntityData().getBoolean("tainted");
        if (wasTainted && !isTainted && !player.capabilities.disableDamage) {
        	player.attackEntityFrom(DamageSourceTX.noTaint, 999);
        }
        if (!wasTainted && isTainted) {
        	player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "thaumcraft:zap", 1.0F, 1.0F);
//        	player.addPotionEffect(new PotionEffect(Potion.blindness.id,60));
//        	player.addPotionEffect(new PotionEffect(Potion.confusion.id,160));
        }
    	player.getEntityData().setBoolean("tainted", isTainted);
        
        if (!(player.worldObj.getBiomeGenForCoords((int)player.posX, (int)player.posZ) == ThaumcraftWorldGenerator.biomeTaint)) {
        	if (isTainted) {
        		if (!player.getEntityData().hasKey("taintGracePeriod")) {
		        	player.getEntityData().setInteger("taintGracePeriod", 0);
		        }
		        int taintGP = player.getEntityData().getInteger("taintGracePeriod");
		        taintGP++;
		        player.getEntityData().setInteger("taintGracePeriod", taintGP);
    			if (player.getActivePotionEffect(ThaumicExploration.potionTaintWithdrawl) == null && taintGP > 100) {
    				for (int i = 0; i<10; i++) {
    					if (player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).getItem() == ConfigItems.itemResource && (player.inventory.getStackInSlot(i).getItemDamage() == 11 || player.inventory.getStackInSlot(i).getItemDamage() == 12)) {
    						player.inventory.decrStackSize(i, 1);
    	    				taintGP = 0;
    	    				player.getEntityData().setInteger("taintGracePeriod",0);
    						break;
    					}
    				}

    				if (taintGP > 100)
    					player.addPotionEffect(new PotionEffect(ThaumicExploration.potionTaintWithdrawl.id,100,1));
    				
    			}
    		}
	        
        }
        else
        {
        	if (!player.worldObj.isRemote){
	        	if (isTainted) {
	        		player.getEntityData().setInteger("taintGracePeriod", 0);
    				if (player.getFoodStats().getFoodLevel() < 4) {
	    		        if (!player.getEntityData().hasKey("taintFoodBuff")) {
	    		        	player.getEntityData().setInteger("taintFoodBuff", 0);
	    		        }
	    		        int taint = player.getEntityData().getInteger("taintFoodBuff");
	    		        taint++;
	    		        if (taint > 80) {
	    		        	player.getFoodStats().addStats(1, 0.0F);
	    		        	taint = 0;
	    		        	if (player.worldObj.isRemote) {
	    		        		Thaumcraft.proxy.swarmParticleFX(player.worldObj, player, 0.1F, 10.0F, 0.0F);
	    		        	}
	    		        }

	    		        player.getEntityData().setInteger("taintFoodBuff", taint);
    				}
    				else
    				{
    					if (!player.getEntityData().hasKey("taintFoodBuff")) {
 	    		        	player.getEntityData().setInteger("taintFoodBuff", 0);
 	    		        }
 	    		        player.getEntityData().setInteger("taintFoodBuff", 0);
    				}
	        	}
        	}
        }
        if (player.getActivePotionEffect(PotionFluxTaint.fluxTaint) != null) {
        	for (int i = 0; i<10; i++) {
    			if (player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).getItem() == ThaumicExploration.charmTaint) {
        		player.removePotionEffect(PotionFluxTaint.fluxTaint.id);	
				break;
			}
        	}
        }
    }
    
    

    if ((event.entity.worldObj.isRemote) && (this.prevStep.containsKey(Integer.valueOf(event.entity.getEntityId()))) && ((((EntityPlayer)event.entity).inventory.armorItemInSlot(0) == null) || (((EntityPlayer)event.entity).inventory.armorItemInSlot(0).getItem() != ThaumicExploration.bootsMeteor && ((EntityPlayer)event.entity).inventory.armorItemInSlot(0).getItem() != ThaumicExploration.runicBootsMeteor && ((EntityPlayer)event.entity).inventory.armorItemInSlot(0).getItem() != ThaumicExploration.runicBootsComet &&((EntityPlayer)event.entity).inventory.armorItemInSlot(0).getItem() != ThaumicExploration.bootsComet)))
    {
      event.entity.stepHeight = ((Float)this.prevStep.get(Integer.valueOf(event.entity.getEntityId()))).floatValue();
      this.prevStep.remove(Integer.valueOf(event.entity.getEntityId()));
    
    }
   
    
  }
  

  
  @SubscribeEvent
  public void joinWorld(EntityJoinWorldEvent event)
  {
    if ((event.entity instanceof EntityPlayer))
    {
      EntityPlayer player = (EntityPlayer)event.entity;
      //updateSpeed(player);
    }
  }
  
  HashMap<Integer, Float> prevStep = new HashMap();
  
  @SubscribeEvent
  public void playerJumps(LivingEvent.LivingJumpEvent event)
  {
	  
    if (((event.entity instanceof EntityPlayer)) && (((EntityPlayer)event.entity).inventory.armorItemInSlot(0) != null) && (((EntityPlayer)event.entity).inventory.armorItemInSlot(0).getItem() == ThaumicExploration.bootsMeteor || ((EntityPlayer)event.entity).inventory.armorItemInSlot(0).getItem()== ThaumicExploration.runicBootsMeteor)) {
      if (((EntityPlayer)event.entity).isSneaking()) {
    	  Vec3 vector = event.entityLiving.getLook(0.5F);
	      double total = Math.abs(vector.zCoord + vector.xCoord);
	      EntityPlayer player = (EntityPlayer)event.entity;
	      double jump = 0;
		  if (Loader.isModLoaded("ThaumicTinkerer")) {
	    	  jump = TTIntegration.getAscentLevel((EntityPlayer)event.entity);
	      }
		  if(jump >= 1) {
			  jump = (jump + 2D)/4D;
		  }
		  
	      if (vector.yCoord < total)
	    	  vector.yCoord = total;
	      
	      event.entityLiving.motionY += ((jump+1)*vector.yCoord)/1.5F;
	      event.entityLiving.motionZ += (jump+1)*vector.zCoord*4;
	      event.entityLiving.motionX += (jump+1)*vector.xCoord*4;

	      
      }
      else
      {
    	  event.entityLiving.motionY += 0.2750000059604645D;
      }
    }
    else if (((event.entity instanceof EntityPlayer)) && (((EntityPlayer)event.entity).inventory.armorItemInSlot(0) != null) && (((EntityPlayer)event.entity).inventory.armorItemInSlot(0).getItem() == ThaumicExploration.bootsComet || ((EntityPlayer)event.entity).inventory.armorItemInSlot(0).getItem() == ThaumicExploration.runicBootsComet)) {
    	event.entityLiving.motionY += 0.2750000059604645D;
      }
    
  }
  
  public void checkAir(EntityPlayer player)
  {  
    if ((player.inventory.armorItemInSlot(0) != null) && (player.inventory.armorItemInSlot(0).getItem()== ThaumicExploration.bootsMeteor || player.inventory.armorItemInSlot(0).getItem() == ThaumicExploration.runicBootsMeteor)) {
      
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
    else if ((player.inventory.armorItemInSlot(0) != null) && (player.inventory.armorItemInSlot(0).getItem()== ThaumicExploration.bootsComet|| player.inventory.armorItemInSlot(0).getItem() == ThaumicExploration.runicBootsComet)) {
        
        Vec3 vector = player.getLook(1.0F);
        ItemStack item = player.inventory.armorItemInSlot(0);
        if (!item.hasTagCompound()) {
  			NBTTagCompound par1NBTTagCompound = new NBTTagCompound();
  			item.setTagCompound(par1NBTTagCompound );
  			item.stackTagCompound.setInteger("runTicks",0);
        }
        for (int x = -5; x < 6; x++) {
      	  for (int z = -5; z < 6; z++) {
  		      if ((player.worldObj.getBlock((int) (player.posX + x), (int) (player.posY-1), (int) (player.posZ + z)) == Blocks.water || player.worldObj.getBlock((int) (player.posX + x), (int) (player.posY-1), (int) (player.posZ + z)) == Blocks.water) && player.worldObj.getBlock((int) (player.posX + x), (int) player.posY-1, (int) (player.posZ + z)).getMaterial() == Material.water && player.worldObj.getBlockMetadata((int) (player.posX + x), (int) player.posY-1, (int) (player.posZ + z)) == 0 && !player.isInWater() && (Math.abs(x)+Math.abs(z) < 8)) {
  		    	  player.worldObj.setBlock((int) (player.posX + x), (int) player.posY-1, (int) (player.posZ + z), ThaumicExploration.meltyIce);
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