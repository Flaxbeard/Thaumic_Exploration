package flaxbeard.thaumicexploration.client.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import org.lwjgl.opengl.GL11;

public class ModelJarOverlay
  extends ModelBase
{
  ModelRenderer Core;
  ModelRenderer Brine;
  ModelRenderer Lid;
  
  public ModelJarOverlay()
  {
    this.textureWidth = 64;
    this.textureHeight = 32;
    
    this.Core = new ModelRenderer(this, 0, 0);
    this.Core.addBox(-6.0F, -12.0F, -6.0F, 12, 12, 12);
    this.Core.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Core.setTextureSize(64, 32);
    this.Core.mirror = true;
    
    this.Lid = new ModelRenderer(this, 0, 24);
    this.Lid.addBox(-3.0F, -0.25F, -3.0F, 6, 2, 6);
    this.Lid.setRotationPoint(0.0F, -14.0F, 0.0F);
    this.Lid.setTextureSize(64, 32);
    this.Lid.mirror = true;
  }
  
  
  public void renderAll()
  {
    this.Lid.render(0.0625F);
    this.Core.render(0.0625F);
  }
}
