package flaxbeard.thaumicexploration.tile;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import flaxbeard.thaumicexploration.ThaumicExploration;

public class TileEntityNecroFire extends TileEntity implements IAspectContainer {
	
	public void burnItem(EntityItem entity) {
		
	}

	@Override
	public AspectList getAspects() {
		// TODO Auto-generated method stub
    	if (worldObj.getBlockTileEntity(xCoord,yCoord-1,zCoord) != null &&  worldObj.getBlockTileEntity(xCoord,yCoord-1,zCoord) instanceof TileEntityNecroPedestal) {
    		TileEntityNecroPedestal pedesetal = (TileEntityNecroPedestal)worldObj.getBlockTileEntity(xCoord,yCoord-1,zCoord);
    		return pedesetal.getAspects();
    	}
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
		return amount;
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
