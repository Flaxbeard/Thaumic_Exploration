package flaxbeard.thaumicexploration.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ChunkCoordinates;

import org.apache.commons.lang3.tuple.MutablePair;

import thaumcraft.common.container.ContainerGhostSlots;
import flaxbeard.thaumicexploration.misc.SortingInventory;
import flaxbeard.thaumicexploration.tile.TileEntityAutoSorter;

public class ContainerBrainMachine extends ContainerGhostSlots {
	
	public TileEntityAutoSorter te;
	public ChunkCoordinates cc;
	public int side;
	
	public SortingInventory inventory = new SortingInventory();

	public ContainerBrainMachine(IInventory pinventory, EntityPlayer player, TileEntityAutoSorter sorter, ChunkCoordinates chunkCoordinates, int s) {
		cc = chunkCoordinates;
		te = sorter;
		side = s;
		System.out.println("X: " + chunkCoordinates.posX + " Y: " + chunkCoordinates.posY + " Z: " + chunkCoordinates.posZ);
		if (!sorter.chestSorts.containsKey(MutablePair.of(chunkCoordinates,side))) {
			sorter.chestSorts.put(MutablePair.of(chunkCoordinates, side), inventory);
		}
		else
		{
			inventory = sorter.chestSorts.get(MutablePair.of(chunkCoordinates, side));	
		}

        int i;
        int j;
        //this.addSlotToContainer(new Slot(inventory, 0, 62 + 1 * 18, 17 + 1 * 18));

        for (i = 0; i < 2; ++i)
        {
            for (j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new SlotGhostSingular(inventory, j + i * 9, 8 + j * 18, 36 + i * 18));
            }
        }
        for (i = 0; i < 3; ++i)
        {
            for (j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(pinventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(pinventory, i, 8 + i * 18, 142));
        }
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public int getNumber() {
		
		return inventory.type;
	}

}
