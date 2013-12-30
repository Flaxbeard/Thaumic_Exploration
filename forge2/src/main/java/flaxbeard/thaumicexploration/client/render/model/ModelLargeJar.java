package flaxbeard.thaumicexploration.client.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import org.lwjgl.opengl.GL11;

public class ModelLargeJar
  extends ModelBase
{
  ModelRenderer Core;
  ModelRenderer Brine;
  
  public ModelLargeJar()
  {
    this.textureWidth = 64;
    this.textureHeight = 32;
    
    this.Core = new ModelRenderer(this, 0, 0);
    this.Core.addBox(-8F, -16.0F, -8F, 16, 16, 16);
    this.Core.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Core.setTextureSize(64, 32);
    this.Core.mirror = true;
    setRotation(this.Core, 0.0F, 0.0F, 0.0F);
    
    this.Brine = new ModelRenderer(this, 0, 0);
    this.Brine.addBox(-7.0F, -15.0F, -7.0F, 14, 14, 14);
    this.Brine.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Brine.setTextureSize(64, 32);
    this.Brine.mirror = true;
    setRotation(this.Brine, 0.0F, 0.0F, 0.0F);
  }
  
  public void renderBrine()
  {
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
    this.Brine.render(0.0625F);
    GL11.glDisable(3042);
  }
  
  public void renderAll()
  {
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
    this.Core.render(0.0625F);
    GL11.glDisable(3042);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
}
