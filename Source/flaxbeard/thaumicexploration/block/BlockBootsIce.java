package flaxbeard.thaumicexploration.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class BlockBootsIce extends BlockIce {

	public BlockBootsIce(int par1) {
		super();
		// TODO Auto-generated constructor stub
	}

	
	@Override
    public Item getItemDropped(int par1, Random par2Random, int par3)
    {
        return Item.getItemFromBlock(Blocks.ice);
    }
	
	@Override
	public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
    {
       
        par2EntityPlayer.addExhaustion(0.025F);
            if (par1World.provider.isHellWorld)
            {
                par1World.setBlockToAir(par3, par4, par5);
                return;
            }

            int i1 = EnchantmentHelper.getFortuneModifier(par2EntityPlayer);
            this.dropBlockAsItem(par1World, par3, par4, par5, par6, i1);
            Material material = par1World.getBlock(par3, par4 - 1, par5).getMaterial();

            if (material.blocksMovement() || material.isLiquid())
            {
                par1World.setBlock(par3, par4, par5, Blocks.water);
            }
        
    }
	
	@Override
	  public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	    {
	        if (true)
	        {
	            if (par1World.provider.isHellWorld)
	            {
	                par1World.setBlockToAir(par2, par3, par4);
	                return;
	            }

	            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
	            par1World.setBlock(par2, par3, par4, Blocks.water);
	        }
	    }

}
