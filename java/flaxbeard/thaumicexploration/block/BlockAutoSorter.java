package flaxbeard.thaumicexploration.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import flaxbeard.thaumicexploration.tile.TileEntityAutoSorter;

public class BlockAutoSorter extends BlockContainer {

	public BlockAutoSorter(int par1, Material par2Material) {
		super(par2Material);
		// TODO Auto-generated constructor stub
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		// TODO Auto-generated method stub
		return new TileEntityAutoSorter();
	}

}
