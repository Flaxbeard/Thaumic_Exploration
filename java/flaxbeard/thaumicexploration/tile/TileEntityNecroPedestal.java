//package flaxbeard.thaumicexploration.tile;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import net.minecraft.block.Block;
//import net.minecraft.client.Minecraft;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.item.EntityItem;
//import net.minecraft.entity.monster.EntityPigZombie;
//import net.minecraft.entity.monster.EntitySkeleton;
//import net.minecraft.entity.monster.EntityZombie;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.inventory.ISidedInventory;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.nbt.NBTTagList;
//import net.minecraft.network.INetworkManager;
//import net.minecraft.network.packet.Packet;
//import net.minecraft.network.packet.Packet132TileEntityData;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.tileentity.TileEntitySkull;
//import net.minecraft.util.AxisAlignedBB;
//import net.minecraft.util.ChunkCoordinates;
//import net.minecraft.util.DamageSource;
//import net.minecraft.util.MathHelper;
//import net.minecraft.world.World;
//
//import org.apache.commons.lang3.tuple.MutablePair;
//
//import thaumcraft.api.aspects.Aspect;
//import thaumcraft.api.aspects.AspectList;
//import thaumcraft.api.aspects.IAspectContainer;
//import thaumcraft.api.wands.IWandable;
//import thaumcraft.common.Thaumcraft;
//import thaumcraft.common.config.ConfigBlocks;
//import thaumcraft.common.lib.InventoryHelper;
//import flaxbeard.thaumicexploration.ThaumicExploration;
//import flaxbeard.thaumicexploration.api.NecromanticAltarAPI;
//import flaxbeard.thaumicexploration.api.NecromanticRecipe;
//import flaxbeard.thaumicexploration.entity.EntityInfusionItem;
//import flaxbeard.thaumicexploration.packet.TXPacketHandler;
//
//public class TileEntityNecroPedestal extends TileEntity implements ISidedInventory,IAspectContainer,IWandable {
//	public AspectList infusionRequirements = new AspectList();
//	  public static final int[] slots = {0,1,2,3,4,5};
//	public boolean active;
//	public boolean crafting;
//	private ItemStack[] inventory = new ItemStack[6];
//	private int recipeEnergy = 0;
//	private ArrayList<ItemStack> recipeInput = null;
//	private ItemStack recipeOutput = null;
//	private int craftTicks;
//	private int recipeInstability = 0;
//	private int soundTicks=0;
//	private int soundOn=0;
//	private ChunkCoordinates[] myCandleLocations = new ChunkCoordinates[5];
//	
//	public static String getLetterFromNumber(int n) {
//		String[] letters = {"a","b","c","d","e","f","g","h"};
//		return letters[n];
//	}
//	
//	@Override
//	public void updateEntity() {
//
//		if (this.active) {
//			for (ChunkCoordinates pos : myCandleLocations) {
//				if (pos != null && this.worldObj.getBlockId(pos.posX, pos.posY, pos.posZ) != ConfigBlocks.blockCandle.blockID) {
//					active = false;
//					break;
//				}
//			}
//			if (!checkForSkulls()) {
//				active = false;
//			}
//			if (this.worldObj.getBlockId(xCoord,yCoord+1,zCoord) == Block.fire.blockID) {
//				this.worldObj.setBlock(xCoord, yCoord+1, zCoord, ThaumicExploration.necroFire.blockID);
//			}
//		}
//		else
//		{
//			this.crafting = false;
//			if (this.worldObj.getBlockId(xCoord,yCoord+1,zCoord) == ThaumicExploration.necroFire.blockID) {
//				this.worldObj.setBlockToAir(xCoord, yCoord+1, zCoord);
//			}
//			
//		}
//		if (this.crafting && this.recipeInput == null) {
//			this.crafting = false;
//		}
//		if (this.crafting) {
//			if (!this.worldObj.isRemote && this.recipeInstability > 0 && this.worldObj.rand.nextInt(500) <= (this.recipeInstability/10)) {
//				int rand = this.worldObj.rand.nextInt(5);
//
//				switch (rand) {
//				case 1:
//					int ir = worldObj.rand.nextInt(5);
//					
//					EntityZombie zombie = new EntityZombie(worldObj);
//					zombie.setPosition(this.myCandleLocations[ir].posX+0.5F, this.myCandleLocations[ir].posY+0.5F, this.myCandleLocations[ir].posZ+0.5F);
//					worldObj.spawnEntityInWorld(zombie);
//					zombie.setPosition(this.myCandleLocations[ir].posX+0.5F, this.myCandleLocations[ir].posY+0.5F, this.myCandleLocations[ir].posZ+0.5F);
//					break;
//				case 2:
//				case 3:
//					inEvZap(false); break;
//				}
//			}
//		}
//		if (this.crafting && this.recipeInput.size() > 0 && this.recipeEnergy == 0) {
//			this.craftTicks++;
//			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
//			
//			if (craftTicks > 100) {
//				int highestFilledSlot = -1;
//				for (int i : slots) {
//					if (getStackInSlot(i) != null) {
//						highestFilledSlot = i;
//					}
//				}
//				if (highestFilledSlot != -1) {
//					for (int a = 0; a < this.recipeInput.size(); a++) {
//						if ((InventoryHelper.areItemStacksEqual(getStackInSlot(highestFilledSlot), (ItemStack)this.recipeInput.get(a), true, false))) {
//							this.recipeInput.remove(a);
//							
//							this.setInventorySlotContents(highestFilledSlot, null);
//						}
//					}
//				}
//				else
//				{
//					this.crafting = false;
//					this.recipeEnergy = 0;
//					this.recipeOutput = null;
//					this.recipeInput = null;
//				}
//				craftTicks = 0;
//			}
//		}
//		if (!this.crafting) {
//			this.recipeEnergy = 0;
//			this.recipeOutput = null;
//			this.recipeInput = null;
//			this.recipeInstability = 0;
//		}
//		else if (this.crafting && this.recipeEnergy == 0 && this.recipeInput.size() == 0) {
//			this.crafting = false;
//			this.setInventorySlotContents(0, this.recipeOutput);
//			this.recipeOutput = null;
//			this.recipeInput = null;
//		}
//		for (int i = 0; i<6;i++) {
//			if (this.getStackInSlot(i) != null && !worldObj.isRemote &&!this.active) {
//				float f = worldObj.rand.nextFloat() * 0.8F + 0.1F;
//	            float f1 = worldObj.rand.nextFloat() * 0.8F + 0.1F;
//	            float f2 = worldObj.rand.nextFloat();
//	            EntityItem entityitem;
//		    	int k1 = worldObj.rand.nextInt(21) + 10;
//	
//	            k1 = this.getStackInSlot(i).stackSize;
//	
//	            entityitem = new EntityInfusionItem(worldObj, (double)((float)xCoord + f), (double)((float)yCoord + f1), (double)((float)zCoord + f2), new ItemStack(this.getStackInSlot(i).itemID, k1, this.getStackInSlot(i).getItemDamage()));
//	            float f3 = 0.05F;
//	            entityitem.motionX = (double)((float)worldObj.rand.nextGaussian() * f3);
//	            entityitem.motionY = (double)((float)worldObj.rand.nextGaussian() * f3 + 0.2F);
//	            entityitem.motionZ = (double)((float)worldObj.rand.nextGaussian() * f3);
//	
//	            if (this.getStackInSlot(i).hasTagCompound())
//	            {
//	                entityitem.getEntityItem().setTagCompound((NBTTagCompound)this.getStackInSlot(i).getTagCompound().copy());
//	            }
//	            ItemStack template = getStackInSlot(i).copy();
//		    	if (template.stackSize > 1) {
//			    	template.stackSize = template.stackSize -=1;
//	
//			    	setInventorySlotContents(i, template);
//		    	}
//		    	else
//		    	{
//		    		this.setInventorySlotContents(i, null);
//		    	}
//	            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
//	            worldObj.spawnEntityInWorld(entityitem);
//			}
//		}
//		
//		if (this.active && this.worldObj.isRemote) {
//			for (int f = 0; f<4; f++) {
//				double ticks = (Minecraft.getMinecraft().thePlayer.ticksExisted + (12.5*f)) % 50;
//				for (int i = 0; i<5; i++) {
//					if (myCandleLocations[i] != null) {
//					int i1 = (i+1)%5;
//					
//					double xDiff = (myCandleLocations[i].posX-myCandleLocations[i1].posX);
//					if (myCandleLocations[i].posX > myCandleLocations[i1].posX && xDiff < 0) {
//						xDiff *= -1;
//					}
//					if (myCandleLocations[i].posX < myCandleLocations[i1].posX && xDiff > 0) {
//						xDiff *= -1;
//					}
//					double zDiff = (myCandleLocations[i].posZ-myCandleLocations[i1].posZ);
//					if (myCandleLocations[i].posZ > myCandleLocations[i1].posZ && zDiff < 0) {
//						zDiff *= -1;
//					}
//					if (myCandleLocations[i].posZ < myCandleLocations[i1].posZ && zDiff > 0) {
//						zDiff *= -1;
//					}
//					float xPos = myCandleLocations[i].posX - (float)(ticks*xDiff/50);
//					float zPos = myCandleLocations[i].posZ - (float)(ticks*zDiff/50);
////					if (i==3 && !this.worldObj.isRemote)
////						System.out.println(myCandleLocations[i].posX + " " + myCandleLocations[i1].posX + " SS " + zDiff + " " + (myCandleLocations[i].posZ-myCandleLocations[i1].posZ));
//					Thaumcraft.proxy.sparkle(xPos+0.5F, yCoord+0.75F, zPos+0.5F, 1.0F, 0x7F0000, 0.0F);
//				}
//				}
//			}
//		
//		
//			for (int f = 0; f<4; f++) {
//				double ticks = (Minecraft.getMinecraft().thePlayer.ticksExisted + (12.5*f)) % 50;
//				for (int i = 0; i<5; i++) {
//					if (myCandleLocations[i] != null) {
//					int i1 = (i+2)%5;
//					
//					double xDiff = (myCandleLocations[i].posX-myCandleLocations[i1].posX);
//					if (myCandleLocations[i].posX > myCandleLocations[i1].posX && xDiff < 0) {
//						xDiff *= -1;
//					}
//					if (myCandleLocations[i].posX < myCandleLocations[i1].posX && xDiff > 0) {
//						xDiff *= -1;
//					}
//					double zDiff = (myCandleLocations[i].posZ-myCandleLocations[i1].posZ);
//					if (myCandleLocations[i].posZ > myCandleLocations[i1].posZ && zDiff < 0) {
//						zDiff *= -1;
//					}
//					if (myCandleLocations[i].posZ < myCandleLocations[i1].posZ && zDiff > 0) {
//						zDiff *= -1;
//					}
//					float xPos = myCandleLocations[i].posX - (float)(ticks*xDiff/50);
//					float zPos = myCandleLocations[i].posZ - (float)(ticks*zDiff/50);
////					if (i==3 && !this.worldObj.isRemote)
////						System.out.println(myCandleLocations[i].posX + " " + myCandleLocations[i1].posX + " SS " + zDiff + " " + (myCandleLocations[i].posZ-myCandleLocations[i1].posZ));
//					Thaumcraft.proxy.sparkle(xPos+0.5F, yCoord+0.75F, zPos+0.5F, 1.0F, 0x7F0000, 0.0F);
//				}
//				}
//			}
//			int highestFilledSlot = -1;
//			for (int i : slots) {
//				if (getStackInSlot(i) != null) {
//					highestFilledSlot = i+1;
//				}
//			}
//			if (this.worldObj.isRemote) {
//				if(crafting) {
//					if (soundTicks == 0) {
//						soundTicks = 100;
//						worldObj.playSound(xCoord + 0.5F, yCoord + 0.5F, zCoord + 0.5F, "thaumicexploration:necroInfusion"+this.getLetterFromNumber(soundOn), 1.0F, 1.0F, false);
//						soundOn = (soundOn+1)%8;
//						//Minecraft.getMinecraft().gets
//					}
//					this.soundTicks--;
//					
//				}
//				for (int i=0;i<6;i++) {
//					if (this.getStackInSlot(i) != null) {
//				         float ticks = Minecraft.getMinecraft().renderViewEntity.ticksExisted;
//				         float tickOffset = ticks/360;
//				         float xChange = -MathHelper.cos((float) (Math.toRadians(ticks*10.0F+(360.0F/highestFilledSlot)*i)+Math.PI));
//				         float zChange = MathHelper.sin((float) (Math.toRadians(ticks*10.0F+(360.0F/highestFilledSlot)*i)+Math.PI));
//				         float yChange = MathHelper.sin((float) (Math.toRadians(ticks*10.0F+tickOffset*1000.0F+(360.0F/highestFilledSlot)*i)))/5.0F;
//				         Thaumcraft.proxy.sparkle((float)xCoord+0.5F+xChange, (float)yCoord+2.15F+yChange, (float)zCoord+0.5F+zChange, 0x7F0000);
//				         if (crafting && this.recipeInput.size() > 0 && this.recipeEnergy == 0 && (highestFilledSlot-1==i)) {
//				        	 int randomCandle = worldObj.rand.nextInt(5);
//				        	 if(worldObj.rand.nextInt(3) == 0) {
//				        		 ThaumicExploration.proxy.spawnLightningBolt(worldObj, myCandleLocations[randomCandle].posX+0.5F, myCandleLocations[randomCandle].posY+0.75F, myCandleLocations[randomCandle].posZ+0.5F, (float)xCoord+0.5F+xChange, (float)yCoord+2.15F+yChange, (float)zCoord+0.5F+zChange);
//				        		 worldObj.playSound((float)xCoord+0.5F+xChange, (float)yCoord+2.15F+yChange, (float)zCoord+0.5F+zChange, "thaumcraft:shock", 0.1F, 1.0F, false);
//					             //worldObj.playSound((float)xCoord+0.5F+xChange, (float)yCoord+2.15F+yChange, (float)zCoord+0.5F+zChange, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F,false);
//				        	 }
//					         if (this.craftTicks == 100) {
//					        	 Thaumcraft.proxy.burst(worldObj, (float)xCoord+0.5F+xChange, (float)yCoord+2.15F+yChange, (float)zCoord+0.5F+zChange, 0.5F);
//					             worldObj.playSound((float)xCoord+0.5F+xChange, (float)yCoord+2.15F+yChange, (float)zCoord+0.5F+zChange, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F,false);
//					        	 //ThaumicExploration.proxy.spawnLightningBolt(worldObj, (float)xCoord+0.5F+xChange, (float)yCoord+2.15F+yChange, (float)zCoord+0.5F+zChange, (float)xCoord+xChange+Math.random(), (float)yCoord+1.65F+yChange+Math.random(), (float)zCoord+Math.random()+zChange);
//					         }
//				         }
//					}
//				}
//
//			}
//		}
//
//	}
//	
//	  private void inEvZap(boolean all)
//	  {
//	    List<Entity> targets = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getAABBPool().getAABB(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1).expand(10.0D, 10.0D, 10.0D));
//	    if ((targets != null) && (targets.size() > 0)) {
//	      for (Entity target : targets)
//	      {
//	    	if (!(target instanceof EntityZombie) && !(target instanceof EntitySkeleton) && !(target instanceof EntityPigZombie)) {
//	        TXPacketHandler.sendNecroZapPacket(this.xCoord + 0.5F, this.yCoord + 0.5F, this.zCoord + 0.5F, (float)target.posX, (float)target.posY + target.height / 2.0F, (float)target.posZ, this);
//	        
//	        target.attackEntityFrom(DamageSource.magic, 4 + this.worldObj.rand.nextInt(4));
//	        
//	        if (!all) {
//	          break;
//	        }
//	    	}
//	      }
//	    }
//	  }
//	
//	public void burnItem(EntityItem entity) {
//		if (this.recipeEnergy > 0) {
//			int multiplier = entity.getEntityItem().stackSize;
//			int itemEnergy = 0;
//			if (NecromanticAltarAPI.necroEnergyItems.get(MutablePair.of(entity.getEntityItem().itemID, -1)) != null) {
//				itemEnergy = NecromanticAltarAPI.necroEnergyItems.get(MutablePair.of(entity.getEntityItem().itemID, -1));
//			}
//			if (NecromanticAltarAPI.necroEnergyItems.get(MutablePair.of(entity.getEntityItem().itemID, entity.getEntityItem().getItemDamage())) != null) {
//				itemEnergy = NecromanticAltarAPI.necroEnergyItems.get(MutablePair.of(entity.getEntityItem().itemID, entity.getEntityItem().getItemDamage()));
//			}
//			this.recipeEnergy = Math.max(this.recipeEnergy - (itemEnergy*multiplier), 0);
//			entity.attackEntityFrom(DamageSource.lava, 100);
//		}
//	}
//
//	@Override
//	public AspectList getAspects() {
//		// TODO Auto-generated method stub
//		AspectList aspects = new AspectList();
//		if (this.recipeEnergy > 0) {
//			aspects.add(ThaumicExploration.fakeAspectNecro, this.recipeEnergy);
//		}
//		return aspects;
//	}
//
//	@Override
//	public void setAspects(AspectList aspects) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public boolean doesContainerAccept(Aspect tag) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public int addToContainer(Aspect tag, int amount) {
//		// TODO Auto-generated method stub
//		return amount;
//	}
//
//	@Override
//	public boolean takeFromContainer(Aspect tag, int amount) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean takeFromContainer(AspectList ot) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean doesContainerContainAmount(Aspect tag, int amount) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean doesContainerContain(AspectList ot) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public int containerContains(Aspect tag) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//	
//	public boolean checkForSkulls() {
//		boolean hasSkulls = true;
//		if (!(worldObj.getBlockId(xCoord+1, yCoord, zCoord) == Block.skull.blockID && ((TileEntitySkull)worldObj.getBlockTileEntity(xCoord+1, yCoord, zCoord)).getSkullType() < 4)) {
//			hasSkulls = false;
//			System.out.println(worldObj.getBlockId(xCoord+1, yCoord, zCoord) == Block.skull.blockID);
//			System.out.println( worldObj.getBlockMetadata(xCoord+1, yCoord, zCoord) < 4);
//		}
//		if (!(worldObj.getBlockId(xCoord-1, yCoord, zCoord) == Block.skull.blockID  && ((TileEntitySkull)worldObj.getBlockTileEntity(xCoord-1, yCoord, zCoord)).getSkullType() < 4)) {
//			hasSkulls = false;
//		}
//		if (!(worldObj.getBlockId(xCoord, yCoord, zCoord+1) == Block.skull.blockID  && ((TileEntitySkull)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord+1)).getSkullType() < 4)) {
//			hasSkulls = false;
//		}
//		if (!(worldObj.getBlockId(xCoord, yCoord, zCoord-1) == Block.skull.blockID  && ((TileEntitySkull)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord-1)).getSkullType() < 4)) {
//			hasSkulls = false;
//		}
//
//		return hasSkulls;
//		
//	}
//
//	@Override
//	public int onWandRightClick(World world, ItemStack wandstack,
//			EntityPlayer player, int x, int y, int z, int side, int md) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//	private ChunkCoordinates[] candleLocations = {new ChunkCoordinates(-2,0,-2),new ChunkCoordinates(2,0,-2),new ChunkCoordinates(3,0,1),new ChunkCoordinates(0,0,3),new ChunkCoordinates(-3,0,1)};
//	@Override
//	public ItemStack onWandRightClick(World world, ItemStack wandstack,
//			EntityPlayer player) {
//		if (!active)
//		{
//			ArrayList<ChunkCoordinates> possibleCandleLocations = new ArrayList<ChunkCoordinates>();
//			outerLoop:
//			for (int j = 0; j < 4; j ++) {
//				possibleCandleLocations.clear();
//				int x = xCoord;
//				int y = yCoord;
//				int z = zCoord;
//				for (int k = 0; k < 5; k++) {
//					int xOffset = candleLocations[k].posX;
//					int zOffset = candleLocations[k].posZ;
//					if (j > 1) {
//						zOffset = -candleLocations[k].posZ;
//					}
//					if (j % 2 == 0) {
//						xOffset = candleLocations[k].posZ;
//						zOffset = candleLocations[k].posX;
//						if (j > 1) {
//							xOffset = -candleLocations[k].posZ;
//						}
//					}
//	
//					if (world.getBlockId(x + xOffset, y, z + zOffset) == ConfigBlocks.blockCandle.blockID) {
//						possibleCandleLocations.add(new ChunkCoordinates(x + xOffset, y, z + zOffset));
//						
//						if (k == 4 && checkForSkulls()) {
//							
//							active = true;
//							this.myCandleLocations = (ChunkCoordinates[]) possibleCandleLocations.toArray(new ChunkCoordinates[0]);
//							break outerLoop;
//						}
//					}
//					else
//					{
//						break;
//					}
//				}
//			}
//		}
//		else
//		{
//			ArrayList<ItemStack> items = new ArrayList<ItemStack>();
//			for (int i = 0; i<this.slots.length; i++) {
//				if (getStackInSlot(i) != null) {
//					items.add(getStackInSlot(i));
//				}
//			}
//			System.out.println(items.size());
//			if (NecromanticAltarAPI.findMatchingNecromanticInfusionRecipe(items, player) != null) {
//				NecromanticRecipe recipe = NecromanticAltarAPI.findMatchingNecromanticInfusionRecipe(items, player);
//				this.crafting = true;
//				
//					this.worldObj.playSound(this.xCoord, this.yCoord, this.zCoord, "thaumcraft:infuserstart", 0.5F, 1.0F, false);
//				
//				this.recipeEnergy = recipe.energyRequired;
//				this.recipeOutput = recipe.recipeOutput;
//				this.recipeInstability = recipe.energyRequired;
//				this.craftTicks = 0;
//				this.soundTicks = 0;
//				this.soundOn=0;
//				this.recipeInput =new ArrayList<ItemStack>(Arrays.<ItemStack>asList(recipe.recipeInput));
//			}
//		}
//		return wandstack;
//	}
//
//	@Override
//	public void onUsingWandTick(ItemStack wandstack, EntityPlayer player,
//			int count) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void onWandStoppedUsing(ItemStack wandstack, World world,
//			EntityPlayer player, int count) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	
//
//	@Override
//	public String getInvName() {
//		// TODO Auto-generated method stub
//		return "necromantic altar";
//	}
//
//	@Override
//	public boolean isInvNameLocalized() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public int getInventoryStackLimit() {
//		// TODO Auto-generated method stub
//		return 1;
//	}
//
//	@Override
//	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
//		// TODO Auto-generated method stub
//		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this;
//	}
//
//	@Override
//	public void openChest() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void closeChest() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
//		// TODO Auto-generated method stub
//		return  true;
//	}
//
//	@Override
//	public int[] getAccessibleSlotsFromSide(int var1) {
//		// TODO Auto-generated method stub
//		for (int i : slots) {
//			if (getStackInSlot(i) == null) {
//				int[] j = { i };
//				return j;
//			}
//		}
//		return null;
//	}
//
//	@Override
//	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//	
//	@Override
//	public ItemStack decrStackSize(int i, int j) {
//		if (this.inventory[i] != null)
//		{
//			ItemStack template = this.inventory[i].copy();
//			template.stackSize = 0;
//			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
//	      	if (this.inventory[i].stackSize <= j)
//	      	{
//	      		
//	      		ItemStack itemstack = this.inventory[i];
//	      		
//	      		this.inventory[i] = template;
//	        	return itemstack;
//	      	}
//	      	ItemStack itemstack = this.inventory[i].splitStack(j);
//	      	if (this.inventory[i] == null) {
//	      		this.inventory[i] = template;
//	      	}
////	      	if (this.inventory[i ].stackSize == 0) {
////	    	  	this.inventory[i] = null;
////	      	}
//	      	return itemstack;
//	    }
//		return null;
//	}
//	@Override
//	public ItemStack getStackInSlotOnClosing(int i) {
//		if (this.inventory[i] != null)
//		{
//			ItemStack itemstack = this.inventory[i];
//			this.inventory[i] = null;
//			return itemstack;
//		}
//		return null;
//	}
//	@Override
//	public void setInventorySlotContents(int i, ItemStack itemstack) {
//		//if (itemstack != null) {
//			this.inventory[i] = itemstack;
//			if ((itemstack != null) && (itemstack.stackSize > getInventoryStackLimit())) {
//				itemstack.stackSize = getInventoryStackLimit();
//			}
//			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
//		//}
//	}
//	@Override
//	public int getSizeInventory() {
//		return 6;
//	}
//	@Override
//	public ItemStack getStackInSlot(int i) {
//		if (!(this.inventory.length - 1 < i)) {
//			return this.inventory[i];
//		}
//		return null;
//	}
//	public void readInventoryNBT(NBTTagCompound nbttagcompound)
//	{
//		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
//		this.inventory = new ItemStack[getSizeInventory()];
//		this.active = nbttagcompound.getBoolean("active");
//		this.crafting = nbttagcompound.getBoolean("crafting");
//		if (this.active) {
//			for (int i = 0; i < nbttaglist.tagCount(); i++)
//			{
//				NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
//				byte b0 = nbttagcompound1.getByte("Slot");
//				if ((b0 >= 0) && (b0 < this.inventory.length)) {
//					this.inventory[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
//				}
//			}
//		}
//		NBTTagList nbttaglist2 = nbttagcompound.getTagList("Candles");
//		for (int i = 0; i < nbttaglist2.tagCount(); i++)
//		{
//			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist2.tagAt(i);
//			//System.out.println(i + " and " + nbttagcompound1.getInteger("x")+nbttagcompound1.getInteger("y")+nbttagcompound1.getInteger("z"));
//			this.myCandleLocations[i] = new ChunkCoordinates(nbttagcompound1.getInteger("x"),nbttagcompound1.getInteger("y"),nbttagcompound1.getInteger("z"));
//		}
//		if (crafting) {
//			NBTTagList nbttaglistIn = nbttagcompound.getTagList("recipein");
//
//			this.recipeInput = new ArrayList();
//		    for (int i = 0; i < nbttaglistIn.tagCount(); i++)
//		    {
//		      NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglistIn.tagAt(i);
//		      byte b0 = nbttagcompound1.getByte("item");
//		      this.recipeInput.add(ItemStack.loadItemStackFromNBT(nbttagcompound1));
//		    }
//			this.recipeOutput = ItemStack.loadItemStackFromNBT(nbttagcompound.getCompoundTag("Recipe Output"));
//			this.recipeEnergy =  nbttagcompound.getInteger("Recipe Energy");
//			this.recipeInstability =  nbttagcompound.getInteger("Recipe Instability");
//			this.craftTicks =  nbttagcompound.getInteger("Recipe Tick");
//		}
//	}
//	  
//	public void writeInventoryNBT(NBTTagCompound nbttagcompound)
//	{
//		nbttagcompound.setBoolean("crafting", crafting);
//		nbttagcompound.setBoolean("active", active);
//		if (crafting) {
//			if ((this.recipeInput != null) && (this.recipeInput.size() > 0))
//		    {
//		      NBTTagList nbttaglist = new NBTTagList();
//		      int count = 0;
//		      for (ItemStack stack : this.recipeInput) {
//		        if (stack != null)
//		        {
//		          NBTTagCompound nbttagcompound1 = new NBTTagCompound();
//		          nbttagcompound1.setByte("item", (byte)count);
//		          stack.writeToNBT(nbttagcompound1);
//		          nbttaglist.appendTag(nbttagcompound1);
//		          count++;
//		        }
//		      }
//		      nbttagcompound.setTag("recipein", nbttaglist);
//		    }
//		
//		
//			NBTTagCompound recipeOutputs = new NBTTagCompound();
//		
//			recipeOutput.writeToNBT(recipeOutputs);
//			nbttagcompound.setTag("Recipe Output", recipeOutputs);
//	
//		}
//		nbttagcompound.setInteger("Recipe Energy", this.recipeEnergy);
//		nbttagcompound.setInteger("Recipe Instability", this.recipeInstability);
//		nbttagcompound.setInteger("Recipe Tick", this.craftTicks);
//		
//		NBTTagList nbttaglist2 = new NBTTagList();
//		nbttagcompound.setTag("Candles", nbttaglist2);
//		if (active) {
//			for (ChunkCoordinates candle : this.myCandleLocations) {
//					NBTTagCompound nbttagcompound1 = new NBTTagCompound();
//			        nbttagcompound1.setInteger("x", candle.posX);
//			        nbttagcompound1.setInteger("y", candle.posY);
//			        nbttagcompound1.setInteger("z", candle.posZ);
//			        nbttaglist2.appendTag(nbttagcompound1);
//			}
//			nbttagcompound.setTag("Candles", nbttaglist2);
//		}
//		NBTTagList nbttaglist = new NBTTagList();
//		for (int i = 0; i < this.inventory.length; i++) {
//			if (this.inventory[i] != null)
//			{
//				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
//		        nbttagcompound1.setByte("Slot", (byte)i);
//		        this.inventory[i].writeToNBT(nbttagcompound1);
//		        nbttaglist.appendTag(nbttagcompound1);
//			}
//		}
//		nbttagcompound.setTag("Items", nbttaglist);
//	}
//	
//	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
//		super.onDataPacket(net, pkt);
//		this.readInventoryNBT(pkt.data);
//	}
//	
//    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
//    {
//    	super.writeToNBT(par1NBTTagCompound);
//		
//    	this.writeInventoryNBT(par1NBTTagCompound);
//    }
//    
//	
//    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
//    {
//		super.readFromNBT(par1NBTTagCompound);
//		this.readInventoryNBT(par1NBTTagCompound);
//    }
//
//	
//	public Packet getDescriptionPacket() {
//		super.getDescriptionPacket();
//		NBTTagCompound access = new NBTTagCompound();
//		this.writeInventoryNBT(access);
//        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, access);
//	}
//	
//
//}
