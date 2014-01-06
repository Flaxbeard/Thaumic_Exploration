package flaxbeard.thaumicexploration.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;

import org.lwjgl.opengl.GL11;

import thaumcraft.client.lib.UtilsFX;
import flaxbeard.thaumicexploration.tile.TileEntityCrucibleSouls;

public class TileEntityRenderCrucibleSouls
  extends TileEntitySpecialRenderer
{
  public void renderEntityAt(TileEntityCrucibleSouls cr, double x, double y, double z, float fq)
  {
    if (cr.getFluidHeight() > 0) {
      renderFluid(cr, x, y, z);
    }
  }
  
  public void renderFluid(TileEntityCrucibleSouls cr, double x, double y, double z)
  {
    Icon icon = Block.waterStill.getIcon(0, 0);
    
    GL11.glPushMatrix();
    GL11.glTranslated(x, y + cr.getFluidHeight(), z + 1.0D);
    GL11.glRotatef(90.0F, -1.0F, 0.0F, 0.0F);
    if (cr.getFluidHeight() > 0)
    {
      float recolor = 1.0F;
      if (recolor > 0.0F) {
        recolor = 0.5F + recolor / 2.0F;
      }
      UtilsFX.renderQuadFromIcon(true, icon, 1.0F, 1.0F - recolor / 3.0F, 1.0F - recolor, 1.0F - recolor / 2.0F, Block.blocksList[thaumcraft.common.config.Config.blockMetalDeviceId].getMixedBrightnessForBlock(cr.worldObj, cr.xCoord, cr.yCoord, cr.zCoord), 771, 1.0F);
    }
    GL11.glPopMatrix();
  }
  
  public void renderTileEntityAt(TileEntity te, double d, double d1, double d2, float f)
  {
    renderEntityAt((TileEntityCrucibleSouls)te, d, d1, d2, f);
  }
}