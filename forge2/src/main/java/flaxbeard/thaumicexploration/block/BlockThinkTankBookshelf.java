package flaxbeard.thaumicexploration.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import flaxbeard.thaumicexploration.tile.TileEntityThinkTankBookshelf;

public class BlockThinkTankBookshelf extends Block{

	public BlockThinkTankBookshelf(int par1, Material par2Material) {
		super(par1, par2Material);
		// TODO Auto-generated constructor stub
	}
	
    public TileEntity createNewTileEntity(World par1World)
    {
        TileEntityThinkTankBookshelf tileentitychest = new TileEntityThinkTankBookshelf();
        return tileentitychest;
    }


}
