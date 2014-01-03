package flaxbeard.thaumicexploration.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flaxbeard.thaumicexploration.ThaumicExploration;
import flaxbeard.thaumicexploration.tile.TileEntityCrucibleSouls;
import flaxbeard.thaumicexploration.tile.TileEntityEverfullUrn;

public class BlockCrucibleSouls extends BlockContainer {

	public static Icon middleSide;
	public static Icon topSide;
	public static Icon topTop;
	public static Icon bottomTop;
	public static Icon bottomBottom;
	public static Icon topBottom;
	public Icon texture;
	

	public BlockCrucibleSouls(int par1) {
		super(par1, Material.iron);
		// TODO Auto-generated constructor stub
	}
	
    public TileEntity createNewTileEntity(World par1World)
    {
        return new TileEntityCrucibleSouls();
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
       	super.registerIcons(par1IconRegister);
        this.middleSide = par1IconRegister.registerIcon("thaumicExploration:everfullUrnMS");
        this.topSide = par1IconRegister.registerIcon("thaumicExploration:everfullUrnTS");
        this.topTop = par1IconRegister.registerIcon("thaumicExploration:everfullUrnTT");
        this.bottomTop = par1IconRegister.registerIcon("thaumicExploration:everfullUrnBT");
        this.bottomBottom = par1IconRegister.registerIcon("thaumicExploration:everfullUrnBB");
        this.topBottom = par1IconRegister.registerIcon("thaumicExploration:everfullUrnTB");
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
    
    
    public boolean onBlockActivated(World world, int par2, int par3, int par4, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9)
    {
        if (world.isRemote)
        {
            return true;
        }
        else
        {
		
			if (entityPlayer.inventory.getCurrentItem() != null){ 
				if (entityPlayer.inventory.getCurrentItem().getItem() instanceof IFluidContainerItem) {
					System.out.println("its happening");
					((IFluidContainerItem)entityPlayer.inventory.getCurrentItem().getItem()).fill(entityPlayer.inventory.getCurrentItem(), new FluidStack(FluidRegistry.WATER, 1000), true);
		            world.playSoundAtEntity(entityPlayer, "liquid.swim", 0.5F, 1.0F);
				}
				else if (entityPlayer.inventory.getCurrentItem().itemID == Item.bucketEmpty.itemID) {
					System.out.println("its happenin");
					entityPlayer.inventory.decrStackSize(entityPlayer.inventory.currentItem, 1);
					entityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.bucketWater, 1));
		            world.playSoundAtEntity(entityPlayer, "liquid.swim", 0.5F, 1.0F);
				}
			}
		
        	return true;
        }
    }

}
