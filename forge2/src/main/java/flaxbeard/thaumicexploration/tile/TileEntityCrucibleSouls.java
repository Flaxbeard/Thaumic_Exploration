package flaxbeard.thaumicexploration.tile;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApi.EntityTags;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.entities.golems.EntityGolemBase;
import thaumcraft.common.entities.monster.EntityThaumicSlime;
import flaxbeard.thaumicexploration.ThaumicExploration;
import flaxbeard.thaumicexploration.event.DamageSourceTX;

public class TileEntityCrucibleSouls extends TileEntity implements IAspectContainer, IEssentiaTransport {

	private int ticks = 0;
	public int drainTicks = 0;
	private EntityLivingBase targetMob = null;
	private int range = 5;
	private int yRange = 5;
	private float distance;
	public AspectList myAspects = new AspectList();
	public float myFlux = 0.0F;
	
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        //par1NBTTagCompound.setInteger("drainTicks",this.drainTicks);
        par1NBTTagCompound.setFloat("myFlux",this.myFlux);
        if (this.drainTicks > 0) {
        	//par1NBTTagCompound.setInteger("targetID",this.targetMob.entityId);
        }
        NBTTagCompound aspects = new NBTTagCompound();
    	
		Iterator iterator =  Aspect.aspects.keySet().iterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("Amount", this.myAspects.getAmount(Aspect.getAspect((String) next)));
			aspects.setCompoundTag((String) next, tag);
		}
		par1NBTTagCompound.setCompoundTag("Aspects", aspects);
    }
    
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        //this.drainTicks = par1NBTTagCompound.getInteger("drainTicks");
        this.myFlux = par1NBTTagCompound.getFloat("myFlux");
//    	if (this.drainTicks >0) {
//    		//this.targetMob = (EntityLivingBase) this.worldObj.getEntityByID(par1NBTTagCompound.getInteger("targetID"));
//
//    	}
		AspectList readAspects = new AspectList();
		NBTTagCompound aspects = par1NBTTagCompound.getCompoundTag("Aspects");
		Iterator iterator =  Aspect.aspects.keySet().iterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			NBTTagCompound aspect = aspects.getCompoundTag((String) next);
			int amount = aspect.getInteger("Amount");
			if (amount > 0) {
				readAspects.add(Aspect.getAspect((String) next), amount);
			}
		}
		this.myAspects = readAspects;
    }
	
	@Override
    public Packet getDescriptionPacket()
    {
    	super.getDescriptionPacket();
        NBTTagCompound access = new NBTTagCompound();
    	access.setInteger("drainTicks",this.drainTicks);
    	access.setFloat("myFlux",this.myFlux);
        if (this.drainTicks > 0) {

        	access.setInteger("targetID",this.targetMob.entityId);
        }
    	NBTTagCompound aspects = new NBTTagCompound();
    	
		Iterator iterator =  Aspect.aspects.keySet().iterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("Amount", this.myAspects.getAmount(Aspect.getAspect((String) next)));
			aspects.setCompoundTag((String) next, tag);
		}
    	access.setCompoundTag("Aspects", aspects);
    	
        
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, access);
    }
    

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
    {
    	super.onDataPacket(net, pkt);
    	NBTTagCompound access = pkt.data;
    	this.drainTicks = access.getInteger("drainTicks");
    	this.myFlux = access.getFloat("myFlux");
    	if (this.drainTicks >0) {
    		this.targetMob = (EntityLivingBase) this.worldObj.getEntityByID(access.getInteger("targetID"));

    	}
    	else
    	{
    		this.targetMob = null;
    	}
		AspectList readAspects = new AspectList();
		NBTTagCompound aspects = access.getCompoundTag("Aspects");
		Iterator iterator =  Aspect.aspects.keySet().iterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			NBTTagCompound aspect = aspects.getCompoundTag((String) next);
			int amount = aspect.getInteger("Amount");
			if (amount > 0) {
				readAspects.add(Aspect.getAspect((String) next), amount);
			}
		}
		this.myAspects = readAspects;
    	
    	worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
    }
    
    public float getFluidHeight()
    {

      float out = 0.3F + 0.7F*(this.myFlux / 1.0F);
      if (out > 1.0F) {
        out = 1.001F;
      }
      if (out == 1.0F) {
        out = 0.9999F;
      }
      if (out == 0.3F) {
    	  out = 0.2F;
      }
      return out;
    }
    
    private void spill() {
		if (this.worldObj.isAirBlock(this.xCoord, this.yCoord + 1, this.zCoord))
		{
			if (this.worldObj.rand.nextBoolean()) {
				this.worldObj.setBlock(this.xCoord, this.yCoord + 1, this.zCoord, ConfigBlocks.blockFluxGas.blockID, 0, 3);
			} else {
				this.worldObj.setBlock(this.xCoord, this.yCoord + 1, this.zCoord, ConfigBlocks.blockFluxGoo.blockID, 0, 3);
			}
		}
		else
		{
			int bi = this.worldObj.getBlockId(this.xCoord, this.yCoord + 1, this.zCoord);
			int md = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord + 1, this.zCoord);
			if ((bi == ConfigBlocks.blockFluxGoo.blockID) && (md < 7))
			{
				this.worldObj.setBlock(this.xCoord, this.yCoord + 1, this.zCoord, ConfigBlocks.blockFluxGoo.blockID, md + 1, 3);
			}
			else if ((bi == ConfigBlocks.blockFluxGas.blockID) && (md < 7))
			{
				this.worldObj.setBlock(this.xCoord, this.yCoord + 1, this.zCoord, ConfigBlocks.blockFluxGas.blockID, md + 1, 3);
			}
			else
			{
				int x = -1 + this.worldObj.rand.nextInt(3);
				int y = -1 + this.worldObj.rand.nextInt(3);
				int z = -1 + this.worldObj.rand.nextInt(3);
				if (this.worldObj.isAirBlock(this.xCoord + x, this.yCoord + y, this.zCoord + z)) {
					if (this.worldObj.rand.nextBoolean()) {
						this.worldObj.setBlock(this.xCoord + x, this.yCoord + y, this.zCoord + z, ConfigBlocks.blockFluxGas.blockID, 0, 3);
					} else {
						this.worldObj.setBlock(this.xCoord + x, this.yCoord + y, this.zCoord + z, ConfigBlocks.blockFluxGoo.blockID, 0, 3);
					}
				}
			}
		}
    }
	
	private float tagAmount() {
		int amount = 0;
		Iterator iterator = this.myAspects.aspects.keySet().iterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			amount += this.myAspects.getAmount((Aspect) next);
		}
		return amount;
	}


	@Override
	public void updateEntity() {
		super.updateEntity();
		if (this.worldObj.isAirBlock(this.xCoord, this.yCoord+1, this.zCoord) || this.worldObj.getBlockId(this.xCoord, this.yCoord+1, this.zCoord) == ConfigBlocks.blockFluxGas.blockID || this.worldObj.getBlockId(this.xCoord, this.yCoord+1, this.zCoord) == ConfigBlocks.blockFluxGoo.blockID) {
			if (this.myFlux > 1.0F) {
				this.spill();
				this.myFlux = this.myFlux - 1.0F;
				this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			}
			this.ticks++;
			if (this.drainTicks > 0) {
				if (this.targetMob != null && this.targetMob.getHealth() > 0) {
					double slope = (((this.zCoord+0.5F)-this.targetMob.posZ)/((this.xCoord+0.5F)-this.targetMob.posX));
					float myDistance=(float) Math.sqrt(Math.pow(this.xCoord-this.targetMob.posX,2) + Math.pow(this.yCoord-this.targetMob.posY,2) + Math.pow(this.zCoord-this.targetMob.posZ,2));
					double xChange = (Math.cos(slope)/75.0D)*myDistance;
					double zChange = (Math.sin(slope)/75.0D)*myDistance;
					if (this.zCoord > this.targetMob.posZ && zChange < 0) {
						zChange = zChange * -1;
					}
					if (this.xCoord > this.targetMob.posX && xChange < 0) {
						xChange = xChange * -1;
					}
					if (this.zCoord < this.targetMob.posZ && zChange > 0) {
						zChange = zChange * -1;
					}
					if (this.xCoord < this.targetMob.posX && xChange > 0) {
						xChange = xChange * -1;
					}
					
					if (myDistance < this.range) {
						if (myDistance > 1.5F) {
							//this.targetMob.moveEntity(xChange, 0, zChange);
						}
						
						if (this.worldObj.isRemote) {
							if (this.worldObj.rand.nextInt(2) == 0) {
								ThaumicExploration.instance.proxy.spawnHarvestParticle(this.worldObj, this.targetMob.posX, this.targetMob.boundingBox.maxY-0.5F, this.targetMob.posZ, this.xCoord+0.5F, this.yCoord+0.5F, this.zCoord+0.5F);
							}
							else
							{
								ThaumicExploration.instance.proxy.spawnBoreSparkle(this.worldObj, this.targetMob.posX, this.targetMob.boundingBox.maxY-0.5F, this.targetMob.posZ, this.xCoord+0.5F, this.yCoord+0.5F, this.zCoord+0.5F);   
							}
						}
		
						if (this.drainTicks % 10 == 0) {
							if (this.targetMob.getHealth() <= 1.0F) {
								if (!this.worldObj.isRemote) {
									String name = EntityList.getEntityString(this.targetMob);
									for (EntityTags tag : ThaumcraftApi.scanEntities) {
										if (tag.entityName == name) {
											
											tag.aspects.aspects.keySet().iterator();
											
											Iterator iterator = tag.aspects.aspects.keySet().iterator();

											int i = 0;
											while (iterator.hasNext()) {
												Object next = iterator.next();
												if (next != null) {
													for (int z = 0;z<tag.aspects.getAmount((Aspect) next);z++) {
														if (this.worldObj.rand.nextInt(3) == 0) {
															this.myAspects.add((Aspect) next, 1);
														}
														else
														{
															if (this.worldObj.rand.nextBoolean()) {
																if (!((Aspect) next).isPrimal()) {
																	if (this.worldObj.rand.nextBoolean()) {
																		this.myAspects.add(((Aspect) next).getComponents()[0], 1);
																	} else {
																		this.myAspects.add(((Aspect) next).getComponents()[1], 1);
																	}
																}
																else
																{
																	this.myAspects.add((Aspect) next, 1);
																}
															}
															else
															{
																this.myFlux+=2.0F/5.0F;
															}
														}
													}
												//if (next != null && i==randomAspect) {
													//this.myAspects.add((Aspect) next, 1);
												}
												i++;
											}
		
										}
									}
									this.drainTicks = 0;
									this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
								}
								this.targetMob.attackEntityFrom(DamageSourceTX.soulCrucible, 100);
								//this.drainTicks = 1;
							}
							this.targetMob.attackEntityFrom(DamageSourceTX.soulCrucible, 1);
						}
						if (this.drainTicks > 0) {
							this.drainTicks--;
						}
					}
					else
					{
						this.drainTicks = 0;
					}
				}
				else
				{
					this.drainTicks = 0;
				}
			}
			if (ticks%5 == 0) {
				this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
				if (this.drainTicks == 0) {
					this.distance = this.range + 1.0F;
					List<EntityLivingBase> mobs = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(this.xCoord-this.range, this.yCoord-this.yRange, this.zCoord-this.range, this.xCoord+this.range, this.yCoord+this.yRange, this.zCoord+this.range));
					for (EntityLivingBase mob : mobs) {
						if (!(mob instanceof EntityPlayer) && !(mob instanceof EntityThaumicSlime)  && !(mob instanceof EntityGolemBase)) {
							float myDistance=(float) Math.sqrt(Math.pow(this.xCoord-mob.posX,2) + Math.pow(this.yCoord-mob.posY,2) + Math.pow(this.zCoord-mob.posZ,2));
							if (myDistance < distance) {
								this.drainTicks = (int) (mob.getMaxHealth()*10);
								this.targetMob = mob;
								this.distance = myDistance;
								if (!this.worldObj.isRemote) {
									this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
								}
							}
						}
					}
				}
			}
		}
		else
		{
			this.drainTicks = 0;
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
	}

	
	
	
	
	

	@Override
	public AspectList getAspects() {
		// TODO Auto-generated method stub
		return this.myAspects;
	}


	@Override
	public void setAspects(AspectList aspects) {
		this.myAspects = aspects;
	}


	@Override
	public boolean doesContainerAccept(Aspect tag) {
		return false;
	}


	@Override
	public int addToContainer(Aspect tag, int amount) {
		return amount;
	}


	@Override
	public boolean takeFromContainer(Aspect tag, int amount) {
		if (!this.worldObj.isRemote) {
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
		if (this.myAspects.getAmount(tag) >= amount) {
			this.myAspects.reduce(tag, amount);
			return true;
		}
		else
		{
			return false;
		}
	}


	@Override
	public boolean takeFromContainer(AspectList ot) {
		// TODO Auto-generated method stub
		boolean hasIt = true;
		if (!this.worldObj.isRemote) {
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
		ot.aspects.keySet().iterator();
		Iterator iterator = ot.aspects.keySet().iterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			if (this.myAspects.getAmount((Aspect) next) < ot.getAmount((Aspect) next))
				hasIt = false;
		}
		if (hasIt) {
			iterator = ot.aspects.keySet().iterator();
			while (iterator.hasNext()) {
				Object next = iterator.next();
				myAspects.reduce((Aspect) next, ot.getAmount((Aspect) next));
			}
			return true;
		}
		else
		{
			return false;
		}
	}


	@Override
	public boolean doesContainerContainAmount(Aspect tag, int amount) {
		// TODO Auto-generated method stub
		return (this.myAspects.getAmount(tag) > amount);
	}


	@Override
	public boolean doesContainerContain(AspectList ot) {
		boolean hasIt = true;
		ot.aspects.keySet().iterator();
		Iterator iterator = ot.aspects.keySet().iterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			if (this.myAspects.getAmount((Aspect) next) < ot.getAmount((Aspect) next))
				hasIt = false;
		}
		return hasIt;
	}


	@Override
	public int containerContains(Aspect tag) {
		return this.myAspects.getAmount(tag);
	}


	@Override
	public boolean isConnectable(ForgeDirection face) {
		return (face != ForgeDirection.UP);
	}
	


	@Override
	public boolean canInputFrom(ForgeDirection face) {
		return false;
	}


	@Override
	public boolean canOutputTo(ForgeDirection face) {
		return (face != ForgeDirection.UP);
	}


//	@Override
//	public void setSuction(AspectList suction) {
//		// TODO Auto-generated method stub
//		
//	}


	@Override
	public void setSuction(Aspect aspect, int amount) {
		// TODO Auto-generated method stub
		
	}


//	@Override
//	public AspectList getSuction(ForgeDirection face) {
//		// TODO Auto-generated method stub
//		AspectList AL = new AspectList();
//		Iterator iterator =  Aspect.aspects.keySet().iterator();
//		while (iterator.hasNext()) {
//			AL.add(Aspect.getAspect((String) iterator.next()), 0);
//		}
//		return AL;
//	}


	@Override
	public int takeVis(Aspect aspect, int amount) {
		// TODO Auto-generated method stub
		if (!this.worldObj.isRemote) {
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
		if (amount > this.myAspects.getAmount(aspect)) {
			int total  = this.myAspects.getAmount(aspect);
			this.myAspects.reduce(aspect, total);
			return total;
		}
		else
		{
			this.myAspects.reduce(aspect, amount);
			return amount;
		}
		
	}


//	@Override
//	public AspectList getEssentia(ForgeDirection face) {
//		// TODO Auto-generated method stub
//		return null;
//	}


	@Override
	public int getMinimumSuction() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public boolean renderExtendedTube() {
		// TODO Auto-generated method stub
		return false;
		//NEW AFTER THIS LINE
	}

	@Override
	public Aspect getSuctionType(ForgeDirection face) {
		return null;
	}

	@Override
	public int getSuctionAmount(ForgeDirection face) {
		return 0;
	}

	@Override
	public int addVis(Aspect aspect, int amount) {
		return 0;
	}

	@Override
	public Aspect getEssentiaType(ForgeDirection face) {
		return this.myAspects.size() > 0 ? this.myAspects.getAspects()[this.worldObj.rand.nextInt(this.myAspects.getAspects().length)] : null;
	}

	@Override
	public int getEssentiaAmount(ForgeDirection face) {
		return this.myAspects.visSize();
	}

}
