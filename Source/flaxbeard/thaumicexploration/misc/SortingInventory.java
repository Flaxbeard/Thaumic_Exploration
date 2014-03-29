package flaxbeard.thaumicexploration.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SortingInventory implements IInventory {
	
	private ItemStack[] inventory = new ItemStack[18];
	public int type = 1;

	@Override
	public int getSizeInventory() {
		
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		
		return inventory[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (this.inventory[i] != null)
        {
            ItemStack itemstack;

            if (this.inventory[i].stackSize <= j)
            {
                itemstack = this.inventory[i];
                this.inventory[i] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.inventory[i].splitStack(j);

                if (this.inventory[i].stackSize == 0)
                {
                    this.inventory[i] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {

		return inventory[i];
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inventory[i] = itemstack;
		
	}


	@Override
	public int getInventoryStackLimit() {
		
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		
		return false;
	}
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		
		return true;
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
	public void markDirty() {
		// TODO Auto-generated method stub
		
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
