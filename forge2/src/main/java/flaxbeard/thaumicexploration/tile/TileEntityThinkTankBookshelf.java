package flaxbeard.thaumicexploration.tile;

import net.minecraft.tileentity.TileEntity;

public class TileEntityThinkTankBookshelf extends TileEntity {

	public float tickCount;
	@Override
	public void updateEntity() {
		++this.tickCount;
		if (tickCount == 100) {
			tickCount = 0;
		}
	}
}
