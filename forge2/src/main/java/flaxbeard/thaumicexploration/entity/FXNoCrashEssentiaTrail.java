package flaxbeard.thaumicexploration.entity;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import thaumcraft.client.fx.FXEssentiaTrail;
import thaumcraft.client.lib.UtilsFX;
import cpw.mods.fml.client.FMLClientHandler;

public class FXNoCrashEssentiaTrail
  extends FXEssentiaTrail
{
  private double targetX;
  private double targetY;
  private double targetZ;
  private int count = 0;
  
  public FXNoCrashEssentiaTrail(World par1World, double par2, double par4, double par6, double tx, double ty, double tz, int count, int color, float scale)
  {
	
    super(par1World, par2, par4, par6, tx, ty, tz, count, color, scale);
  }
  
  public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5)
  {
	  try {
		  super.renderParticle(tessellator, f, f1, f2, f3, f4, f5);
	  } catch (Exception e){
	  }
  }
  
  public void onUpdate()
  {
	  try {
		  super.onUpdate();
	  } catch (Exception e){
	  }
  }
  
  public void setGravity(float value)
  {
    this.particleGravity = value;
  }
  
  protected boolean pushOutOfBlocks(double par1, double par3, double par5)
  {
	  return super.pushOutOfBlocks(par1, par3, par5);
  }
  
  public int particle = 24;
}
