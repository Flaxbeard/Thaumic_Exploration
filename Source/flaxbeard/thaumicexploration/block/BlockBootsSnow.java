package flaxbeard.thaumicexploration.block;

import java.util.Random;

import net.minecraft.block.BlockSnow;
import net.minecraft.world.World;

public class BlockBootsSnow extends BlockSnow{

	protected BlockBootsSnow(int par1) {
		super(par1);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
            par1World.setBlockToAir(par2, par3, par4);
    }

}
