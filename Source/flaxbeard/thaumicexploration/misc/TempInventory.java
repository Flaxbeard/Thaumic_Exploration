package flaxbeard.thaumicexploration.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class TempInventory implements IInventory {
	
	private ItemStack[] inventory;
	public int type = 1;
	
	public TempInventory(int i) {
		inventory = new ItemStack[i];
	}
	
	public boolean equals(Object obj) {
		///System.out.println("ttt");
		if (!(obj instanceof TempInventory)) {
			return false;
		}
		if (inventory.length != ((TempInventory)obj).inventory.length) {
			return false;
		}
		for (int z = 0; z<inventory.length; z++) {
			if (inventory[z] == null && ((TempInventory)obj).inventory[z] == null) {
				
			}
			else
			{
				if (inventory[z] == null && ((TempInventory)obj).inventory[z] != null) {
					return false;
				}
				if (inventory[z] != null && ((TempInventory)obj).inventory[z] == null) {
					return false;
				}
				if (!inventory[z].equals(((TempInventory)obj).inventory[z])) {
					return false;
				}
			}
		}
		return true;
		
	}

	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return inventory[i];
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inventory[i] = itemstack;
		
	}

	@Override
	public String getInvName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 64;
	}

	@Override
	public void onInventoryChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openChest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeChest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return true;
	}
	
}
