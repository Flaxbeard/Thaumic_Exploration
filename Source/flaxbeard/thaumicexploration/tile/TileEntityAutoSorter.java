package flaxbeard.thaumicexploration.tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

import org.apache.commons.lang3.tuple.MutablePair;

import flaxbeard.thaumicexploration.misc.SortingInventory;
import flaxbeard.thaumicexploration.misc.TempInventory;

public class TileEntityAutoSorter extends TileEntity implements IInventory{
	
	public ArrayList<MutablePair> chests = new ArrayList<MutablePair>();
	public HashMap<MutablePair,SortingInventory> chestSorts = new HashMap<MutablePair,SortingInventory>();
	
	
    public void updateEntity()
    {
		ArrayList<MutablePair> chestsToRemove = new ArrayList<MutablePair>();
    	for (MutablePair loc : chests) {
    		ChunkCoordinates chest = (ChunkCoordinates) loc.left;
    		if (this.worldObj.getBlockTileEntity(chest.posX, chest.posY, chest.posZ) == null) {
    			chestsToRemove.add(loc);
    		}
    	}
    	for (MutablePair chest : chestsToRemove) {
    		chests.remove(chest);
    	}
    	ArrayList<ItemStack> centralInventory;
    	ArrayList<MutablePair<int[],MutablePair>> accessible = new ArrayList<MutablePair<int[],MutablePair>>();
    	chestsToRemove = new ArrayList<MutablePair>();
    	for (MutablePair loc : chests) {
    		ChunkCoordinates chest = (ChunkCoordinates) loc.left;
    		TileEntity te = this.worldObj.getBlockTileEntity(chest.posX, chest.posY, chest.posZ);
    		if (te instanceof ISidedInventory) {
    			int[] accessibleSlots = ((ISidedInventory) te).getAccessibleSlotsFromSide((Integer) loc.right);

    			if (arrayContains(accessible,MutablePair.of(accessibleSlots, loc))) {
    				
    				chestsToRemove.add(loc);
    			}
    			else
    			{
    				accessible.add(MutablePair.of(accessibleSlots, loc));
    			}
    		}
    		else {
    			int[] accessibleSlots = numsUpTo(((IInventory) te).getSizeInventory());
    			
    			String test = "";
    			
    			if (arrayContains(accessible,MutablePair.of(accessibleSlots, loc))) {
    				
    				chestsToRemove.add(loc);
    			}
    			else
    			{
    				
    				accessible.add(MutablePair.of(accessibleSlots, loc));
    			}
    		}
    	}
    	for (MutablePair chest : chestsToRemove) {
    		chests.remove(chest);
    	}
    	for (MutablePair<int[],MutablePair> info : accessible) {
    		MutablePair loc = info.right;
    		System.out.println(chestSorts.keySet().contains(loc));
    		SortingInventory sort = chestSorts.get(loc);
    		if (sort != null) {
	    		if (sort.type == 1) {
	    			ChunkCoordinates chest = (ChunkCoordinates) info.right.left;
	        		TileEntity te = this.worldObj.getBlockTileEntity(chest.posX, chest.posY, chest.posZ);
	        		for (int i = 0; i<info.left.length; i++) {
	        			if (te instanceof ISidedInventory) {
	        				ISidedInventory tile = (ISidedInventory) te;
	        				tile.setInventorySlotContents(info.left[i], null);
	        			}
	        			else
	        			{
	        				IInventory tile = (IInventory) te;
	        				tile.setInventorySlotContents(info.left[i], null);
	        			}
	        		}
	    		}
	    	}
    		else
    		{
    			//System.out.println("what the fuck");
    		}
    	}
    }
    private boolean arrayContains(ArrayList<MutablePair<int[],MutablePair>> array,MutablePair<int[],MutablePair> pair) {
    	boolean contains = false;
    	for (MutablePair<int[],MutablePair> item : array) {
    		if (Arrays.equals(pair.left, item.left) && pair.right.equals(item.right)) {
    			contains = true;
    		}
    	}
    	return contains;
    }
    private int[] numsUpTo(int x) {
    	int[] test = new int[x];
    	for (int i=0;i<x;i++) {
    		test[i] = i;
    	}
    	return test;
    }
    private boolean areStacksEqual(ItemStack stack1, ItemStack stack2) {
    	ItemStack stack1c = stack1.copy();
		stack1c.stackSize = 1;
		ItemStack stack2c = stack2.copy();
		stack2c.stackSize = 1;
		return (stack1c.areItemStacksEqual(stack1c,stack2c) && stack1c.areItemStackTagsEqual(stack1c,stack2c));
    	
    }

	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		
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
		return 0;
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
		return false;
	}

}

