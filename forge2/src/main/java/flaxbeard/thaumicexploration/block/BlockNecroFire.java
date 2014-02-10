package flaxbeard.thaumicexploration.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import flaxbeard.thaumicexploration.ThaumicExploration;
import flaxbeard.thaumicexploration.entity.EntityInfusionItem;
import flaxbeard.thaumicexploration.tile.TileEntityNecroFire;
import flaxbeard.thaumicexploration.tile.TileEntityNecroPedestal;

public class BlockNecroFire extends BlockFire implements ITileEntityProvider{

	public BlockNecroFire(int par1) {
		super(par1);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
    
    }
	@Override
    public boolean isCollidable()
    {
        return true;
    }
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
    }
	
	@Override
    public boolean func_82506_l()
    {
        return true;
    }

	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity entity) {
	    if (entity instanceof EntityItem && !(entity instanceof EntityInfusionItem)) {
	    	if (par1World.getBlockTileEntity(par2, par3-1, par4) != null && par1World.getBlockTileEntity(par2, par3-1, par4) instanceof TileEntityNecroPedestal) {
	    		TileEntityNecroPedestal pedesetal = (TileEntityNecroPedestal)par1World.getBlockTileEntity(par2, par3-1, par4);
	    		pedesetal.burnItem((EntityItem) entity);
	    	}
	    }
	    if (!entity.isImmuneToFire() && !(entity instanceof EntityInfusionItem))
	    {
	        entity.attackEntityFrom(DamageSource.onFire, 4.0F);
	        entity.setFire(8);
	        System.out.println("burning item " + entity.toString());
	    }
	}
	
	@Override
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        if (par1World.provider.dimensionId > 0 || par1World.getBlockId(par2, par3 - 1, par4) != Block.obsidian.blockID || !Block.portal.tryToCreatePortal(par1World, par2, par3, par4))
        {
            if (par1World.getBlockId(par2, par3-1, par4) != ThaumicExploration.necroPedestal.blockID)
            {
                par1World.setBlockToAir(par2, par3, par4);
            }
            else
            {
                par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World) + par1World.rand.nextInt(10));
            }
        }
    }
	
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        if ((par1World.getBlockId(par2, par3-1, par4) != ThaumicExploration.necroPedestal.blockID))
        {
            par1World.setBlockToAir(par2, par3, par4);
        }
    }

	@Override
	public TileEntity createNewTileEntity(World world) {
		// TODO Auto-generated method stub
		return new TileEntityNecroFire();
	}
	
	@Override
  public void setBlockBoundsBasedOnState(IBlockAccess world, int i, int j, int k)
  {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
  }

}
