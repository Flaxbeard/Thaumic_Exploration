package flaxbeard.thaumicexploration.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;

import flaxbeard.thaumicexploration.ThaumicExploration;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.client.renderers.block.BlockRenderer;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigBlocks;

public class BlockSkullCandle
  extends BlockContainer
{
  public Icon icon;
  public Icon iconStub;
public Icon iconSkull;
public Icon icon2;
  
  
  public BlockSkullCandle(int i)
  {
    super(i, Material.circuits);
    setHardness(0.1F);
    setStepSound(Block.soundClothFootstep);
    setUnlocalizedName("blockCandle");
    setCreativeTab(Thaumcraft.tabTC);
    setLightValue(0.95F);
  }
  
  @SideOnly(Side.CLIENT)
  public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
  {
    for (int var4 = 0; var4 < 16; var4++) {
      par3List.add(new ItemStack(par1, 1, var4));
    }
  }
  
  @SideOnly(Side.CLIENT)
  public void registerIcons(IconRegister ir)
  {
    this.icon = ir.registerIcon("thaumicexploration:candle");
    this.iconStub = ir.registerIcon("thaumicexploration:candlestub");
    this.icon2 = ir.registerIcon("thaumicexploration:candle2");
  }
  
  @SideOnly(Side.CLIENT)
  public Icon getIcon(int side, int meta)
  {
    return this.icon;
  }
  
  public int getRenderColor(int par1)
  {
    return thaumcraft.common.lib.Utils.colors[par1];
  }
  
  public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
  {
    return par1World.doesBlockHaveSolidTopSurface(par2, par3, par4);
  }
  
  public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
  {
    int var6 = par1World.getBlockMetadata(par2, par3, par4);
    boolean var7 = canPlaceBlockAt(par1World, par2, par3 - 1, par4);
    if (!var7)
    {
      dropBlockAsItem(par1World, par2, par3, par4, var6, 0);
      par1World.setBlock(par2, par3, par4, 0, 0, 3);
    }
    super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
  }
  
  public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5)
  {
    return canPlaceBlockAt(par1World, par2, par3 - 1, par4);
  }
  
  
  public int colorMultiplier(IBlockAccess par1iBlockAccess, int par2, int par3, int par4)
  {
    int md = par1iBlockAccess.getBlockMetadata(par2, par3, par4);
    return thaumcraft.common.lib.Utils.colors[15];
  }
  
  public int damageDropped(int par1)
  {
    return par1;
  }
  
  
  public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side)
  {
    return false;
  }
  
  
  public int getRenderType()
  {
    return ThaumicExploration.candleSkullRenderID;
  }
  
  public boolean renderAsNormalBlock()
  {
    return false;
  }
  
  public boolean isOpaqueCube()
  {
    return false;
  }
  
  public void randomDisplayTick(World world, int par2, int par3, int par4, Random par5Random)
  {
    double var7 = par2 + 0.5F;
    double var9 = par3 + 0.7F + 0.5F - BlockRenderer.W3;
    double var11 = par4 + 0.5F;
    
    float xOffset=0;
    float yOffset=0;
    float zOffset=0;
    if (world.getBlockMetadata(par2,par3,par4) != 1) {
    	yOffset = BlockRenderer.W4;
    }
    if (world.getBlockMetadata(par2,par3,par4) == 2)
    	zOffset = 0.25F;
    if (world.getBlockMetadata(par2,par3,par4) == 3)
    	zOffset = -0.25F;
    if (world.getBlockMetadata(par2,par3,par4) == 4)
    	xOffset = 0.25F;
    if (world.getBlockMetadata(par2,par3,par4) == 5)
    	xOffset = -0.25F;
    
    var7 += xOffset;
    var9 += yOffset;
    var11 += zOffset;
    
    world.spawnParticle("smoke", var7, var9, var11, 0.0D, 0.0D, 0.0D);
    world.spawnParticle("flame", var7, var9, var11, 0.0D, 0.0D, 0.0D);
    //ThaumicExploration.proxy.crucibleBubble(world, (float)var7, (float)var9, (float)var11, (float)0.0D, (float)0.0D, (float)0.0D);
  }
  
  public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
  {
      int l = par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 7;

      switch (l)
      {
          case 1:
          default:
              this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
              break;
          case 2:
              this.setBlockBounds(0.25F, 0.25F, 0.5F, 0.75F, 0.75F, 1.0F);
              break;
          case 3:
              this.setBlockBounds(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.5F);
              break;
          case 4:
              this.setBlockBounds(0.5F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
              break;
          case 5:
              this.setBlockBounds(0.0F, 0.25F, 0.25F, 0.5F, 0.75F, 0.75F);
      }
  }

  /**
   * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
   * cleared to be reused)
   */
  public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
  {
      this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
      return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
  }

@Override
public TileEntity createNewTileEntity(World world) {
	// TODO Auto-generated method stub
	return new TileEntitySkull();
}
}
