package flaxbeard.thaumicexploration.tile;

import net.minecraft.nbt.NBTTagCompound;
import thaumcraft.common.tiles.TileCrystal;

public class TileCrystalAdvanced extends TileCrystal {
	public int rotation1 = 0;
	public int rotation2 = 0;
	public int numCrystals = 0;
  
	public TileCrystalAdvanced()
	{
		super();   
	}
	  
	public void readCustomNBT(NBTTagCompound nbttagcompound)
	{
		super.readCustomNBT(nbttagcompound);
		this.rotation1 = nbttagcompound.getInteger("rotation1");
		this.rotation2 = nbttagcompound.getInteger("rotation2");
		this.numCrystals = nbttagcompound.getInteger("numCrystals");
	}
	  
	public void writeCustomNBT(NBTTagCompound nbttagcompound)
	{
		super.writeCustomNBT(nbttagcompound);
		nbttagcompound.setInteger("rotation1", this.rotation1);
		nbttagcompound.setInteger("rotation2", this.rotation2);
		nbttagcompound.setInteger("numCrystals", this.numCrystals);
	}
}
