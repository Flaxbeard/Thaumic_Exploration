package flaxbeard.thaumicexploration.client.render;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import thaumcraft.client.lib.UtilsFX;
import thaumcraft.client.renderers.tile.ModelBrain;
import thaumcraft.client.renderers.tile.ModelJar;
import thaumcraft.client.renderers.tile.TileNodeRenderer;
import thaumcraft.common.blocks.BlockJar;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.tiles.TileJar;
import thaumcraft.common.tiles.TileJarFillable;
import thaumcraft.common.tiles.TileJarFillableVoid;
import thaumcraft.common.tiles.TileJarNode;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityTrashJarRenderer
  extends TileEntitySpecialRenderer
{
  private ModelJar model = new ModelJar();
  private ModelBrain brain = new ModelBrain();
  private TileNodeRenderer tnr = new TileNodeRenderer();
  private static final ResourceLocation texture = new ResourceLocation("thaumicexploration:textures/models/jar_oblivion.png");
  
  public void renderTileEntityAt(TileJar tile, double x, double y, double z, float f)
  {
    float wobble = Math.max(Math.abs(tile.wobblex), Math.abs(tile.wobblez)) / 150.0F;
    if ((tile instanceof TileJarNode))
    {
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, -0.1F + wobble, 0.0F);
      this.tnr.renderTileEntityAt(tile, x, y, z, f);
      GL11.glPopMatrix();
    }
    GL11.glPushMatrix();
    GL11.glDisable(2884);
    
    GL11.glTranslatef((float)x + 0.5F, (float)y + 0.01F + wobble, (float)z + 0.5F);
    GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
    
    GL11.glRotatef(tile.wobblex, 0.0F, 0.0F, 1.0F);
    GL11.glRotatef(tile.wobblez, 1.0F, 0.0F, 0.0F);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    if ((tile instanceof TileJarFillable))
    {
      if (((TileJarFillable)tile).amount > 0) {
        renderLiquid((TileJarFillable)tile, x, y, z, f);
      }
      if (((TileJarFillable)tile).aspectFilter != null)
      {
        GL11.glPushMatrix();
        switch (((TileJarFillable)tile).facing)
        {
        case 3: 
          GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F); break;
        case 5: 
          GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F); break;
        case 4: 
          GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
        }
        float rot = (((TileJarFillable)tile).aspectFilter.getTag().hashCode() + tile.xCoord + ((TileJarFillable)tile).facing) % 4 - 2;
        
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, -0.4F, 0.315F);
        if (Config.crooked) {
          GL11.glRotatef(rot, 0.0F, 0.0F, 1.0F);
        }
        UtilsFX.renderQuadCenteredFromTexture("textures/models/label.png", 0.5F, 1.0F, 1.0F, 1.0F, -99, 771, 1.0F);
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, -0.4F, 0.316F);
        if (Config.crooked) {
          GL11.glRotatef(rot, 0.0F, 0.0F, 1.0F);
        }
        GL11.glScaled(0.021D, 0.021D, 0.021D);
        GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
        

        UtilsFX.drawTag(-8, -8, ((TileJarFillable)tile).aspectFilter);
        GL11.glPopMatrix();
        
        GL11.glPopMatrix();
      }
    }
    this.bindTexture(texture);
    this.model.renderAll();
    GL11.glEnable(2884);
    GL11.glPopMatrix();
  }
  
  public void renderLiquid(TileJarFillable te, double x, double y, double z, float f)
  {
    if (this.field_147501_a.field_147553_e == null) {
      return;
    }
    GL11.glPushMatrix();
    GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
    
    World world = te.getWorldObj();
    RenderBlocks renderBlocks = new RenderBlocks();
    
    GL11.glDisable(2896);
    
    float level = te.amount / te.maxAmount * 0.625F;
    
    Tessellator t = Tessellator.instance;
    
    renderBlocks.setRenderBounds(0.25D, 0.0625D, 0.25D, 0.75D, 0.0625D + level, 0.75D);
    
    t.startDrawingQuads();
    if (te.aspect != null) {
      t.setColorOpaque_I(te.aspect.getColor());
    }
    int bright = 200;
    if (te.getWorldObj() != null) {
      bright = Math.max(200, ConfigBlocks.blockJar.getMixedBrightnessForBlock(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord));
    }
    t.setBrightness(bright);
    
    IIcon icon = ((BlockJar)ConfigBlocks.blockJar).iconLiquid;
    
    this.field_147501_a.field_147553_e.bindTexture(TextureMap.locationBlocksTexture);
    
    renderBlocks.renderFaceYNeg(ConfigBlocks.blockJar, -0.5D, 0.0D, -0.5D, icon);
    renderBlocks.renderFaceYPos(ConfigBlocks.blockJar, -0.5D, 0.0D, -0.5D, icon);
    renderBlocks.renderFaceZNeg(ConfigBlocks.blockJar, -0.5D, 0.0D, -0.5D, icon);
    renderBlocks.renderFaceZPos(ConfigBlocks.blockJar, -0.5D, 0.0D, -0.5D, icon);
    renderBlocks.renderFaceXNeg(ConfigBlocks.blockJar, -0.5D, 0.0D, -0.5D, icon);
    renderBlocks.renderFaceXPos(ConfigBlocks.blockJar, -0.5D, 0.0D, -0.5D, icon);
    
    t.draw();
    

    GL11.glEnable(2896);
    GL11.glPopMatrix();
    
    GL11.glColor3f(1.0F, 1.0F, 1.0F);
  }
  

  
  public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
  {
    renderTileEntityAt((TileJar)par1TileEntity, par2, par4, par6, par8);
  }
}
