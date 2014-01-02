package flaxbeard.thaumicexploration.tile;

import java.util.List;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.client.fx.FXEssentiaTrail;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.tiles.TileCrucible;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.Fluid;

public class TileEntityEverfullUrn extends TileEntity implements IFluidTank,IFluidHandler {

	private int ticks = 0;
	private int drainTicks = 0;
	private int dX;
	private int dY;
	private int dZ;
	private int excessTicks = 0;
	private int drainType = 0;
	private float distance = 0;
	private int range = 3;
	private int yRange = 2;
	private EntityPlayer burningPlayer;

	@Override
	public FluidStack getFluid() {
		// TODO Auto-generated method stub
		return new FluidStack(FluidRegistry.WATER, 100);
	}

	@Override
	public int getFluidAmount() {
		// TODO Auto-generated method stub
		return 9999;
	}

	@Override
	public int getCapacity() {
		// TODO Auto-generated method stub
		return 9999;
	}

	@Override
	public FluidTankInfo getInfo() {
		// TODO Auto-generated method stub
		return new FluidTankInfo(this);
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		// TODO Auto-generated method stub
        int drained = maxDrain;
        if (999999 < drained)
        {
            drained = 999999;
        }

        FluidStack stack = new FluidStack(FluidRegistry.WATER, drained);
        return stack;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		// TODO Auto-generated method stub
		return this.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
		// TODO Auto-generated method stub
		if (!resource.isFluidEqual(new FluidStack(FluidRegistry.WATER,1)) || !(from == ForgeDirection.UP)) {
			return null;
		}
		
		return this.drain(resource.amount, doDrain);
		
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		// TODO Auto-generated method stub
		if (from == ForgeDirection.UP) {
			return this.drain(maxDrain, doDrain);
		}
		else
		{
	        FluidStack stack = new FluidStack(FluidRegistry.WATER, 0);
	        return stack;
		}
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		// TODO Auto-generated method stub
		System.out.println(from == ForgeDirection.UP);
		return (from == ForgeDirection.UP);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		// TODO Auto-generated method stub
		return new FluidTankInfo[] { this.getInfo() };
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		this.ticks++;
		if (this.worldObj.getBlockMaterial(this.xCoord, this.yCoord + 1, this.zCoord) == Material.air || this.worldObj.getBlockMaterial(this.xCoord, this.yCoord + 1, this.zCoord) == Config.airyMaterial) {
			
			if (this.drainTicks > 0 && drainType == 1) {
				if (this.worldObj.getBlockId(this.dX, this.dY, this.dZ) == ConfigBlocks.blockMetalDevice.blockID) {
					if (this.worldObj.getBlockMetadata(this.dX, this.dY, this.dZ) == 0) {
						TileCrucible tile = ((TileCrucible)(this.worldObj.getBlockTileEntity(this.dX, this.dY, this.dZ)));
						if (tile.tank.getFluidAmount() < tile.tank.getCapacity()) 
						{
							this.drainTicks = (tile.tank.getCapacity() - tile.tank.getFluidAmount())/10;
							if (this.excessTicks > (20 * this.distance)) {
								tile.fill(ForgeDirection.SOUTH, new FluidStack(FluidRegistry.WATER, 10), true);
							}
							if (this.drainTicks % 5 == 0 && this.worldObj.isRemote && this.excessTicks < (40 * this.distance)) {
								FXEssentiaTrail fx = new FXEssentiaTrail(this.worldObj, this.xCoord+0.5F, this.yCoord+1.1F, this.zCoord+0.5F, this.dX+0.5F, this.dY+1.1F, this.dZ+0.5F, 5, Aspect.TOOL.getColor(), 1.0F);
				        		Minecraft.getMinecraft().effectRenderer.addEffect(fx);
							}
							this.excessTicks++;
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
				else
				{
					this.drainTicks = 0;
				}
			}
			
			if (this.drainTicks > 0 && drainType == 2) {
				EntityPlayer player = this.burningPlayer;
				List<EntityPlayer> players = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(this.xCoord-this.range, this.yCoord-this.yRange, this.zCoord-this.range, this.xCoord+this.range, this.yCoord+this.yRange, this.zCoord+this.range));
				if (players.contains(player) && player.isBurning()) {
					
					if (this.drainTicks % 3 == 0 && this.worldObj.isRemote && this.excessTicks < (40 * this.distance)) {
						FXEssentiaTrail fx = new FXEssentiaTrail(this.worldObj, this.xCoord+0.5F, this.yCoord+1.1F, this.zCoord+0.5F, player.posX, player.posY, player.posZ, 5, Aspect.TOOL.getColor(), 1.0F);
		        		Minecraft.getMinecraft().effectRenderer.addEffect(fx);
					}
					this.excessTicks++;
		        	this.drainTicks--;
		        	if (this.excessTicks > (15 * this.distance)) {
						player.extinguish();
						this.drainTicks = 0;
						worldObj.playSoundAtEntity(player, "liquid.swim", 2.0F, 1.0F);
						for (int x = -1; x<2;x++) {
							for (int z = -1; z<2;z++) {
								if (this.worldObj.getBlockId((int) player.posX +x, (int)player.posY, (int)player.posZ+z) == Block.fire.blockID) {
									this.worldObj.setBlockToAir((int) player.posX+x, (int)player.posY, (int)player.posZ+z);
								}
							}
						}

					}
				}
				else
				{
					this.drainTicks = 0;
				}
				
			}
			
			
			if (ticks%2 == 0 && this.worldObj.isRemote && (this.drainTicks <= 0 || this.excessTicks > (40 * this.distance))) {
				FXEssentiaTrail fx = new FXEssentiaTrail(this.worldObj, this.xCoord+0.5F, this.yCoord+1.1F, this.zCoord+0.5F, this.xCoord+0.5F+((Math.random())-0.5), this.yCoord+2.1F, this.zCoord+0.5F+((Math.random())-0.5), 5, Aspect.TOOL.getColor(), 1.0F);
	    		Minecraft.getMinecraft().effectRenderer.addEffect(fx);
			}
			
			if (ticks%5 == 0) {
				if (this.drainTicks == 0 || this.drainType != 2) {
					List<EntityPlayer> players = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(this.xCoord-this.range, this.yCoord-this.yRange, this.zCoord-this.range, this.xCoord+this.range, this.yCoord+this.yRange, this.zCoord+this.range));
					for (EntityPlayer player : players) {
						if (player.isBurning()) {
							distance=(float) Math.sqrt(Math.pow(this.xCoord-player.posX,2) + Math.pow(this.yCoord-player.posY,2) + Math.pow(this.zCoord-player.posZ,2));
							this.drainTicks = 100;
							this.excessTicks = 0;
							this.drainType = 2;
							this.burningPlayer = player;
							break;
						}
					}
				}
				if (this.drainTicks == 0) {
					for (int x = (-1*this.range); x<(this.range+1);x++) {
						for (int z = (-1*this.range); z<(this.range+1);z++) {
							for (int y = (-1*this.yRange); y<(this.yRange+1);y++) {
								if (this.worldObj.getBlockId(this.xCoord+x, this.yCoord+y, this.zCoord+z) == ConfigBlocks.blockMetalDevice.blockID) {
									if (this.worldObj.getBlockMetadata(this.xCoord+x, this.yCoord+y, this.zCoord+z) == 0) {

										TileCrucible tile = ((TileCrucible)(this.worldObj.getBlockTileEntity(this.xCoord+x, this.yCoord+y, this.zCoord+z)));
										if (tile.tank.getFluidAmount() < tile.tank.getCapacity()) 
										{
											distance=(float) Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2));
											this.drainTicks = (tile.tank.getCapacity() - tile.tank.getFluidAmount())/10;
											this.excessTicks = 0;
											this.drainType = 1;
											this.dX = this.xCoord+x;
											this.dY = this.yCoord+y;
											this.dZ = this.zCoord+z;
											break;
										}
										
									}
								}
							}
						}
					}
				}
			}
		}
	}

}
