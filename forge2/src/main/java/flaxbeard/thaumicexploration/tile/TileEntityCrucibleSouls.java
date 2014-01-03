package flaxbeard.thaumicexploration.tile;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.client.fx.FXBoreParticles;
import cpw.mods.fml.client.FMLClientHandler;

public class TileEntityCrucibleSouls extends TileEntity implements IAspectContainer {

	private int ticks = 0;
	private int drainTicks = 0;
	private EntityLivingBase targetMob;
	private int range = 5;
	private int yRange = 2;
	private float distance;
	private AspectList myAspects;

	
	@Override
	public void updateEntity() {
		super.updateEntity();
		this.ticks++;
		if (this.drainTicks > 0) {
			if (this.targetMob != null && this.targetMob.getHealth() > 0) {
				double slope = (((this.zCoord+0.5F)-this.targetMob.posZ)/((this.xCoord+0.5F)-this.targetMob.posX));
				double xChange = Math.cos(slope)/100.0D;
				double zChange = Math.sin(slope)/100.0D;
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
				float myDistance=(float) Math.sqrt(Math.pow(this.xCoord-this.targetMob.posX,2) + Math.pow(this.yCoord-this.targetMob.posY,2) + Math.pow(this.zCoord-this.targetMob.posZ,2));
				if (myDistance < this.range) {
					if (myDistance > 1.5F) {
						this.targetMob.moveEntity(xChange, 0, zChange);
					}
					
					if (this.drainTicks % 5 == 0 && this.worldObj.isRemote) {
					    FXBoreParticles fb = new FXBoreParticles(worldObj, this.targetMob.posX, this.targetMob.posY, this.targetMob.posZ, this.xCoord+0.5F, this.yCoord+0.5F, this.zCoord+0.5F, Item.porkRaw, worldObj.rand.nextInt(6), 3);
					    

					    fb.setAlphaF(0.3F);
					    fb.motionX = ((float)worldObj.rand.nextGaussian() * 0.03F);
					    fb.motionY = ((float)worldObj.rand.nextGaussian() * 0.03F);
					    fb.motionZ = ((float)worldObj.rand.nextGaussian() * 0.03F);
					    FMLClientHandler.instance().getClient().effectRenderer.addEffect(fb);
					}
	
					if (this.drainTicks % 20 == 0) {
						this.targetMob.attackEntityFrom(DamageSource.magic, 1);
					}
					this.drainTicks--;
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
			if (this.drainTicks == 0) {
				this.distance = this.range + 1.0F;
				List<EntityLivingBase> mobs = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(this.xCoord-this.range, this.yCoord-this.yRange, this.zCoord-this.range, this.xCoord+this.range, this.yCoord+this.yRange, this.zCoord+this.range));
				for (EntityLivingBase mob : mobs) {
					if (!(mob instanceof EntityPlayer)) {
						float myDistance=(float) Math.sqrt(Math.pow(this.xCoord-mob.posX,2) + Math.pow(this.yCoord-mob.posY,2) + Math.pow(this.zCoord-mob.posZ,2));
						if (myDistance < distance) {
							this.drainTicks = (int) (mob.getMaxHealth()*20);
							this.targetMob = mob;
							this.distance = myDistance;
						}
					}
				}
			}
		}
	}


	@Override
	public AspectList getAspects() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setAspects(AspectList aspects) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean doesContainerAccept(Aspect tag) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public int addToContainer(Aspect tag, int amount) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public boolean takeFromContainer(Aspect tag, int amount) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean takeFromContainer(AspectList ot) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean doesContainerContainAmount(Aspect tag, int amount) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean doesContainerContain(AspectList ot) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public int containerContains(Aspect tag) {
		// TODO Auto-generated method stub
		return 0;
	}

}
