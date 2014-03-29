package flaxbeard.thaumicexploration.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
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

	public static IIcon middleSide;
	public static IIcon topSide;
	public static IIcon topTop;
	public static IIcon bottomTop;
	public static IIcon bottomBottom;
	public static IIcon topBottom;
	public IIcon texture;
	
	  public IIcon[] IIcon = new IIcon[16];
	  
	  @Override
	  @SideOnly(Side.CLIENT)
	  public void registerBlockIcons(IIconRegister ir)
	  {
	    this.IIcon[0] = ir.registerIcon("thaumicexploration:metalbase");
	    for (int a = 1; a <= 7; a++) {
	      this.IIcon[a] = ir.registerIcon("thaumicExploration:crucible" + a);
	    }
	    this.IIcon[9] = ir.registerIcon("thaumicExploration:blankTexture");

	  }
	  
	  public IIcon getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int side)
	  {
		TileEntityCrucibleSouls te = (TileEntityCrucibleSouls) iblockaccess.getTileEntity(i, j, k);
		
	    if (side == 1) {
	      return this.IIcon[1];
	    }
	    if (side == 0) {
	      return this.IIcon[2];
	    }
	    return this.IIcon[9];
	  }

	public BlockCrucibleSouls(int par1) {
		super(Material.iron);
	    setHardness(3.0F);
	    setResistance(17.0F);
	    setStepSound(Block.soundTypeMetal);
		// TODO Auto-generated constructor stub
	}
	
	@SideOnly(Side.CLIENT)
    public IIcon getIIcon(int par1, int par2)
    {
        return this.IIcon[3];
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

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityCrucibleSouls();
	}
    
//    @SideOnly(Side.CLIENT)
//    public void registerIIcons(IIconRegister par1IIconRegister)
//    {
//       	super.registerIIcons(par1IIconRegister);
//        this.middleSide = par1IIconRegister.registerIIcon("thaumicExploration:everfullUrnMS");
//        this.topSide = par1IIconRegister.registerIIcon("thaumicExploration:everfullUrnTS");
//        this.topTop = par1IIconRegister.registerIIcon("thaumicExploration:everfullUrnTT");
//        this.bottomTop = par1IIconRegister.registerIIcon("thaumicExploration:everfullUrnBT");
//        this.bottomBottom = par1IIconRegister.registerIIcon("thaumicExploration:everfullUrnBB");
//        this.topBottom = par1IIconRegister.registerIIcon("thaumicExploration:everfullUrnTB");
//    }
    
    

   

}
