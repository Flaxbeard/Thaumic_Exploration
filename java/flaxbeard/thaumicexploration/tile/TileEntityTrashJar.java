package flaxbeard.thaumicexploration.tile;

import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.tiles.TileJarFillable;
import flaxbeard.thaumicexploration.ThaumicExploration;

public class TileEntityTrashJar extends TileJarFillable {
	int ticks = 0;
	
    public int getMinimumSuction()
    {
      return 8;
    }
    
    public Aspect getSuctionType(ForgeDirection loc)
    {
      return this.aspect;
    }
    
    public int getSuctionAmount(ForgeDirection loc)
    {
      if (this.amount < this.maxAmount)
      {
        return 8;
      }
      return 0;
    }
    
    public Aspect getEssentiaType(ForgeDirection loc)
    {
      return this.aspect;
    }
    
    public int getEssentiaAmount(ForgeDirection loc)
    {
      return this.amount;
    }
    

	
	@Override
	public boolean takeFromContainer(Aspect tt, int am)
	{
	    return false;
	}
	
    public void updateEntity()
    {
    	if (this.worldObj.isRemote) {
	        this.spazattack = 10;
	        for (int i=0;i<3;i++) {
	        	double[] pos1 = {xCoord+Math.random(),yCoord+Math.random(),zCoord+Math.random()};
	        	double[] pos2 = {pos1[0]+(Math.random()/2)-0.25F,pos1[1]+(Math.random()/2)-0.25F,pos1[2]+(Math.random()/2)-0.25F};
	        	ThaumicExploration.instance.proxy.spawnLightningBolt(worldObj,pos1[0],pos1[1],pos1[2], xCoord+0.5F, yCoord+0.4F,zCoord+0.5F);
	        	ThaumicExploration.instance.proxy.spawnLightningBolt(worldObj,pos2[0],pos2[1],pos2[2], pos1[0],pos1[1],pos1[2]);
	        }
    	}
    	if (this.amount > 0) {
    		ticks++;
    		if (ticks > 5) {
    			this.amount--;
    			ticks = 0;
    		}
    	}
    	if (this.amount == 0) {
    		this.aspect = null;
    		ticks = 0;
    	}
    	super.updateEntity();
    }
	
	
}
