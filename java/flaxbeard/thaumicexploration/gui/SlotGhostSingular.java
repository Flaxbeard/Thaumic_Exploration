package flaxbeard.thaumicexploration.gui;

import net.minecraft.inventory.IInventory;
import thaumcraft.common.container.SlotGhost;

public class SlotGhostSingular extends SlotGhost {

	public SlotGhostSingular(IInventory par1iInventory, int par2, int par3,
			int par4) {
		super(par1iInventory, par2, par3, par4);
		// TODO Auto-generated constructor stub
	}
	
	public int getSlotStackLimit()
	{
		return 1;
	}

}
