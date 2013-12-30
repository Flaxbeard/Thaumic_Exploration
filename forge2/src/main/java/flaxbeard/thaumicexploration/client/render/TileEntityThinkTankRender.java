package flaxbeard.thaumicexploration.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import thaumcraft.client.lib.UtilsFX;
import thaumcraft.client.renderers.tile.ModelBrain;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flaxbeard.thaumicexploration.client.render.model.ModelLargeJar;
import flaxbeard.thaumicexploration.tile.TileEntityThinkTank;

@SideOnly(Side.CLIENT)
public class TileEntityThinkTankRender
  extends TileEntitySpecialRenderer
{
  private ModelLargeJar model = new ModelLargeJar();
  private ModelBrain brain = new ModelBrain();
  private ModelRenderer modelBox;
  
  public void renderTileEntityAt(TileEntityThinkTank tile, double x, double y, double z, float f)
  {
    GL11.glPushMatrix();
    GL11.glDisable(2884);
    
    GL11.glTranslatef((float)x + 0.5F, (float)y, (float)z + 0.5F);
    GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
    
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    renderBrain(tile, x, y, z, f);
    UtilsFX.bindTexture("textures/models/jar.png");
    this.model.renderAll();
    GL11.glEnable(2884);
    GL11.glPopMatrix();
  }
  

  public void renderBrain(TileEntityThinkTank te, double x, double y, double z, float f)
  {
    float bob = MathHelper.sin(Minecraft.getMinecraft().thePlayer.ticksExisted / 14.0F) * 0.03F + 0.03F;
    GL11.glPushMatrix();
    GL11.glTranslatef(0.0F, -0.8F + bob, 0.0F);
    for (float f2 = te.rota - te.rotb; f2 >= 3.141593F; f2 -= 6.283185F) {
	    while (f2 < -3.141593F) {
	      f2 += 6.283185F;
	    }
	    float f3 = te.rotb + f2 * f;
	    
	    GL11.glRotatef(f3 * 180.0F / 3.141593F, 0.0F, 1.0F, 0.0F);
    }
    GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
    
    UtilsFX.bindTexture("textures/models/brain2.png");
    GL11.glScalef(0.4F, 0.4F, 0.4F);
    this.brain.render();
    GL11.glScalef(1.0F, 1.0F, 1.0F);
    GL11.glPopMatrix();
    this.bindTexture(new ResourceLocation("thaumicexploration:textures/models/largejarbrine.png"));
    this.model.renderBrine();
  }
  
  public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
  {
    renderTileEntityAt((TileEntityThinkTank)par1TileEntity, par2, par4, par6, par8);
  }
}

