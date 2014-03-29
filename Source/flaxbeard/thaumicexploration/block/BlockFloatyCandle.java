package flaxbeard.thaumicexploration.block;

import java.util.Random;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.common.blocks.BlockCandle;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flaxbeard.thaumicexploration.ThaumicExploration;
import flaxbeard.thaumicexploration.tile.TileEntityFloatyCandle;

public class BlockFloatyCandle extends BlockCandle implements ITileEntityProvider {

	public BlockFloatyCandle(int i) {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
 	{
		return true;
 	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1iBlockAccess, int par2, int par3, int par4)
	{
		setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
		//super.setBlockBoundsBasedOnState(par1iBlockAccess, par2, par3, par4);
	}
	
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(IconRegister ir)
//	{
//		this.icon = ir.registerIcon("thaumicexploration:blankTexture");
//		this.iconStub = ir.registerIcon("thaumicexploration:blankTexture");
//	}
//	  



	public int getRenderType()
	{
		return ThaumicExploration.floatCandleRenderID;
	}
	  
	  
	 @Override
	 @SideOnly(Side.CLIENT)
	 public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	 {
		 double var7 = par2 + 0.5F;
		 double var9 = par3 + 0.7F+0.25F;
		 double var11 = par4 + 0.5F;
		 int ticks = Minecraft.getMinecraft().thePlayer.ticksExisted;
		 int offset = (int) (par2+par3+par4);
		 //offset = 0;
		 float move = 0.2F*MathHelper.sin( (((offset*10)+ticks)/30.0F));
		 par1World.spawnParticle("smoke", var7, var9+move, var11, 0.0D, 0.0D, 0.0D);
		 par1World.spawnParticle("flame", var7, var9+move, var11, 0.0D, 0.0D, 0.0D);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		// TODO Auto-generated method stub
		return new TileEntityFloatyCandle();
	}
}
