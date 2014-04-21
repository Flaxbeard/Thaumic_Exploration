package flaxbeard.thaumicexploration.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flaxbeard.thaumicexploration.ThaumicExploration;
import flaxbeard.thaumicexploration.tile.TileEntityEverfullUrn;

public class BlockEverfullUrn extends BlockContainer {

	public static IIcon middleSide;
	public static IIcon topSide;
	public static IIcon topTop;
	public static IIcon bottomTop;
	public static IIcon bottomBottom;
	public static IIcon topBottom;
	public IIcon texture;
	

	public BlockEverfullUrn(int par1) {
		super(Material.rock);
		// TODO Auto-generated constructor stub
	}
	
    public TileEntity createNewTileEntity(World par1World)
    {
        return new TileEntityEverfullUrn();
    }
	  
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IIconRegister)
    {
       	super.registerBlockIcons(par1IIconRegister);
        this.middleSide = par1IIconRegister.registerIcon("thaumicExploration:everfullUrnMS");
        this.topSide = par1IIconRegister.registerIcon("thaumicExploration:everfullUrnTS");
        this.topTop = par1IIconRegister.registerIcon("thaumicExploration:everfullUrnTT");
        this.bottomTop = par1IIconRegister.registerIcon("thaumicExploration:everfullUrnBT");
        this.bottomBottom = par1IIconRegister.registerIcon("thaumicExploration:everfullUrnBB");
        this.topBottom = par1IIconRegister.registerIcon("thaumicExploration:everfullUrnTB");
    }
    
    public void setBlockBoundsBasedOnState(IBlockAccess world, int i, int j, int k)
    {
    	float f = (14.0F/16.0F);
    	float f1 = (2.0F/16.0F);
    	float f2 = (9.0F/16.0F);
    	float f3 = (6.0F/16.0F);
    	float f4 = (5.0F/16.0F);
    	setBlockBounds(f1, 0.0F, f1, f, 1.0F, f);
       
    }
    public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, List arraylist, Entity par7Entity)
    {
    	float f = (14.0F/16.0F);
    	float f1 = (2.0F/16.0F);
    	float f2 = (9.0F/16.0F);
    	float f3 = (6.0F/16.0F);
    	float f4 = (5.0F/16.0F);
    	setBlockBounds(f1, 0.0F, f1, f, 1.0F, f);
        super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, par7Entity);
    }
    
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        int l = par1World.getBlockMetadata(par2, par3, par4);
        float f = (float)par2 + 0.5F;
        float f1 = (float)par3 + 1.0F;
        float f2 = (float)par4 + 0.5F;
        float f3 = 0.52F;
        float f4 = par5Random.nextFloat() * 0.6F - 0.3F;
//        par1World.spawnParticle("splash", f, f1, f2, 0.0D, 1.0D, 0.0D);
//        par1World.spawnParticle("splash", f, f1, f2, 0.0D, 1.0D, 0.0D);
//        par1World.spawnParticle("splash", f, f1, f2, 0.0D, 1.0D, 0.0D);
//        par1World.spawnParticle("splash", f, f1, f2, 0.0D, 1.0D, 0.0D);
//        par1World.spawnParticle("splash", f, f1, f2, 0.0D, 1.0D, 0.0D);
//        par1World.spawnPamorticle("splash", f, f1, f2, 0.0D, 1.0D, 0.0D);
        if (Math.random() < 0.1) {    

        }

        //par1World.spawnParticle("smoke", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
        //par1World.spawnParticle("flame", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
        
        
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    public int getRenderType()
    {
        return ThaumicExploration.everfullUrnRenderID;
    }
    
    public boolean onBlockActivated(World world, int par2, int par3, int par4, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9)
    {

		
			if (entityPlayer.inventory.getCurrentItem() != null){ 
				if (entityPlayer.inventory.getCurrentItem().getItem() == Items.bucket) {
					entityPlayer.inventory.decrStackSize(entityPlayer.inventory.currentItem, 1);
					if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.water_bucket, 1))) {
						entityPlayer.dropItem(Items.water_bucket, 1);
					}
		            world.playSoundAtEntity(entityPlayer, "liquid.swim", 0.5F, 1.0F);
				}
				else if (FluidContainerRegistry.isEmptyContainer(entityPlayer.inventory.getCurrentItem())) {
					ItemStack newStack = entityPlayer.inventory.getCurrentItem();
					entityPlayer.inventory.decrStackSize(entityPlayer.inventory.currentItem, 1);
					if (!entityPlayer.inventory.addItemStackToInventory(FluidContainerRegistry.fillFluidContainer(new FluidStack(FluidRegistry.WATER, 1000), newStack))) {
						entityPlayer.dropItem(FluidContainerRegistry.fillFluidContainer(new FluidStack(FluidRegistry.WATER, 1000), newStack).getItem(),1);
					}
					world.playSoundAtEntity(entityPlayer, "liquid.swim", 0.5F, 1.0F);
				}
				else if (entityPlayer.inventory.getCurrentItem().getItem() instanceof IFluidContainerItem) {
					ItemStack newStack = new ItemStack(entityPlayer.inventory.getCurrentItem().getItem(),1);
					entityPlayer.inventory.decrStackSize(entityPlayer.inventory.currentItem, 1);
					((IFluidContainerItem)newStack.getItem()).fill(newStack, new FluidStack(FluidRegistry.WATER, 1000), true);
					if (!entityPlayer.inventory.addItemStackToInventory(newStack)) {
						entityPlayer.dropItem(newStack.getItem(),newStack.stackSize);
					}
					world.playSoundAtEntity(entityPlayer, "liquid.swim", 0.5F, 1.0F);
				}
			}
		
        	return true;
        
    }

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		// TODO Auto-generated method stub
		return new TileEntityEverfullUrn();
	}

}
