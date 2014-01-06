package flaxbeard.thaumicexploration.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
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
	
	  public Icon[] icon = new Icon[16];
	  
	  
	  @SideOnly(Side.CLIENT)
	  public void registerIcons(IconRegister ir)
	  {
	    this.icon[0] = ir.registerIcon("thaumicexploration:metalbase");
	    for (int a = 1; a <= 7; a++) {
	      this.icon[a] = ir.registerIcon("thaumicExploration:crucible" + a);
	    }
	    this.icon[9] = ir.registerIcon("thaumicExploration:blankTexture");

	  }
	  
	  public Icon getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int side)
	  {
		TileEntityCrucibleSouls te = (TileEntityCrucibleSouls) iblockaccess.getBlockTileEntity(i, j, k);
		
	    if (side == 1) {
	      return this.icon[1];
	    }
	    if (side == 0) {
	      return this.icon[2];
	    }
	    return this.icon[9];
	  }

	public BlockCrucibleSouls(int par1) {
		super(par1, Material.iron);
	    setHardness(3.0F);
	    setResistance(17.0F);
	    setStepSound(Block.soundMetalFootstep);
		// TODO Auto-generated constructor stub
	}
	
	@SideOnly(Side.CLIENT)
    public Icon getIcon(int par1, int par2)
    {
        return this.icon[3];
    }
	
    public TileEntity createNewTileEntity(World par1World)
    {
        return new TileEntityCrucibleSouls();
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    public int getRenderType()
    {
        return ThaumicExploration.crucibleSoulsRenderID;
    }
    
//    @SideOnly(Side.CLIENT)
//    public void registerIcons(IconRegister par1IconRegister)
//    {
//       	super.registerIcons(par1IconRegister);
//        this.middleSide = par1IconRegister.registerIcon("thaumicExploration:everfullUrnMS");
//        this.topSide = par1IconRegister.registerIcon("thaumicExploration:everfullUrnTS");
//        this.topTop = par1IconRegister.registerIcon("thaumicExploration:everfullUrnTT");
//        this.bottomTop = par1IconRegister.registerIcon("thaumicExploration:everfullUrnBT");
//        this.bottomBottom = par1IconRegister.registerIcon("thaumicExploration:everfullUrnBB");
//        this.topBottom = par1IconRegister.registerIcon("thaumicExploration:everfullUrnTB");
//    }
    
    
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
    
    
   

}
