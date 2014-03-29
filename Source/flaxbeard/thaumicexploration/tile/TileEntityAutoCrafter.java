package flaxbeard.thaumicexploration.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileEntityAutoCrafter extends TileEntity implements IInventory{

	@Override
	public int getSizeInventory() {
		
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		
		return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		
		
	}


	@Override
	public int getInventoryStackLimit() {
		
		return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		
		return false;
	}

	@Override
	public String getInventoryName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openInventory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub
		
	}

}
