package flaxbeard.thaumicexploration.tile;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.tiles.TileJarFillable;
import flaxbeard.thaumicexploration.data.BoundJarWorldData;

public class TileEntityBoundJar extends TileJarFillable {
	private BoundJarWorldData myJarData;
	
	@Override
	public int addToContainer(Aspect tt, int am) {
		this.updateEntity();
		if (myJarData == null) {
			myJarData = BoundJarWorldData.get(this.worldObj, "swagn", 0);
		}
	    if (am == 0) {
	        return am;
	    }
		if (((this.amount < this.maxAmount) && (tt == this.aspect)) || (this.amount == 0))
	    {
	      this.aspect = tt;
	      int added = Math.min(am, this.maxAmount - this.amount);
	      this.amount += added;
	      am -= added;
	      myJarData.updateJarContents(tt, this.amount);
	    }
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		return am;
	}
	
	@Override
	public boolean takeFromContainer(Aspect tt, int am)
	{
		this.updateEntity();
		if ((this.amount >= am) && (tt == this.aspect))
		{
			this.amount -= am;
			if (this.amount <= 0)
			{
				this.aspect = null;
				this.amount = 0;
			}
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			myJarData.updateJarContents(tt, this.amount);
			return true;
	    }
	    return false;
	}
	
    public void updateEntity()
    {
    	if (!this.worldObj.isRemote) {
	    	if (myJarData == null) {
				myJarData = BoundJarWorldData.get(this.worldObj, "swagn", 0);
			}
	    	
	    	//System.out.println(this.amount);
			if (this.amount != myJarData.getJarAmount()) {
				this.amount = myJarData.getJarAmount();
			}
			if (this.aspect != myJarData.getJarAspect()) {
				this.aspect = myJarData.getJarAspect();
			}
	
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    	}
    	super.updateEntity();
    }
	
	
}
