package flaxbeard.thaumicexploration.client.render;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
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
  private float oldRotationDegrees;
  private ModelBook enchantmentBook = new ModelBook();
  private int[] lastDirection  = {1,1,1,1,1,1,1,1};
  private int[] direction = {1,1,1,1,1,1,1,1};
  private int[] numBooks = {6,8,8};
  private static final ResourceLocation brineTexture = new ResourceLocation("thaumicexploration:textures/models/largejarbrine.png");
  private static final ResourceLocation enchantingTableBookTextures = new ResourceLocation("textures/entity/enchanting_table_book.png");
  private static final ResourceLocation largeJarTexture = new ResourceLocation("thaumicexploration:textures/models/largejar.png");
  
  public void renderInventoryIcon(double x, double y, double z, float f)
  {
	    GL11.glPushMatrix();
	    GL11.glDisable(2884);
	    
	    GL11.glTranslatef((float)x + 0.5F, (float)y-0.1f, (float)z + 0.5F);
	    GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
	    
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    renderBrainInventory(x, y, z, f);
	    UtilsFX.bindTexture("textures/models/jar.png");
	    
	    this.model.renderLid();
	    this.bindTexture(largeJarTexture);
	    this.model.renderAll();
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    GL11.glDisable(3042);
	    GL11.glDisable(GL11.GL_CULL_FACE);
	
	    GL11.glEnable(2884);
	    GL11.glPopMatrix();
  }
  
  public void renderTileEntityAt(TileEntityThinkTank tile, double x, double y, double z, float f)
  {
    GL11.glPushMatrix();
    GL11.glDisable(2884);
    
    GL11.glTranslatef((float)x + 0.5F, (float)y, (float)z + 0.5F);
    GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
    
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    renderBrain(tile, x, y, z, f);
    UtilsFX.bindTexture("textures/models/jar.png");
    
    this.model.renderLid();
    this.bindTexture(largeJarTexture);
    this.model.renderAll();
    int space = tile.space;
    for (int i=0;i<numBooks[space];i++) {
	    GL11.glPushMatrix();
	    if (((int)(tile.rotationTicks+i))%7 == 0) {
	    	lastDirection[i] = direction[i];
	    	direction[i] = 4 - new Random().nextInt(4);
	    }
        float f4 = (((int)(tile.rotationTicks+i))%7)/7.0F;
        //System.out.println(f4);
        
	    float f1 = tile.rotationTicks + f;
	    this.bindTexture(enchantingTableBookTextures);
	    float rotationDegrees = -2*((tile.rotationTicks) % 360);
	    float bob = ((i % 2 == 0 || i == 0) ? 1 : -1) *MathHelper.sin((Minecraft.getMinecraft().thePlayer.ticksExisted)/ 28.0F) * 0.24F; 
	    GL11.glTranslatef(0.0F, -0.5F+bob, 0.0F);
	    GL11.glRotatef(rotationDegrees+((360/numBooks[space])*i), 0.0F, 1.0F, 0.0F);
	    switch (space) {
	    case 0:
		    GL11.glTranslatef(-1.4F, 0.0F, 0.0F);
		    break;
	    default:
		    GL11.glTranslatef(-2.0F, 0.0F, 0.0F);
		    break;
	    }
	    GL11.glEnable(GL11.GL_CULL_FACE);
	    //System.out.println(direction[i]);
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, tile.warmedUpNumber/40F);
	    GL11.glEnable(3042);
	    GL11.glBlendFunc(770, 771);

	    Minecraft.getMinecraft().thePlayer.worldObj.spawnParticle("enchantmenttable",(float)x + 0.5F+(2*Math.sin(rotationDegrees)),(float)y,(float)z+ 0.5F+(2*Math.cos(rotationDegrees)),0.0F,0.1F,0.0F);
	    switch (direction[i]) {
	    	case 2:
	    	    this.enchantmentBook.render((Entity)null, f1, 1.0F-f4, 0.0F, 0.75F, 0.0F, 0.0625F);
	    	    break;
	    	case 3:
	    	    this.enchantmentBook.render((Entity)null, f1, f4, 0.0F, 0.75F, 0.0F, 0.0625F);
	    	    break;
	    	default:
	    	    this.enchantmentBook.render((Entity)null, f1, 0.0F, 0.0F, 0.75F, 0.0F, 0.0625F);
	    	    break;
	    }
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    GL11.glDisable(3042);
	    GL11.glDisable(GL11.GL_CULL_FACE);
	
	    GL11.glEnable(2884);
	    GL11.glPopMatrix();
    }
    GL11.glPopMatrix();
  }
  

  public void renderBrain(TileEntityThinkTank te, double x, double y, double z, float f)
  {
	float bob = MathHelper.sin(Minecraft.getMinecraft().thePlayer.ticksExisted / 14.0F) * 0.03F + 0.03F; 
    float bob2 = MathHelper.sin((Minecraft.getMinecraft().thePlayer.ticksExisted +9.32F)/ 14.0F) * 0.03F + 0.03F;
    float bob3 = MathHelper.sin((Minecraft.getMinecraft().thePlayer.ticksExisted +4.66F)/ 14.0F) * 0.03F + 0.03F;
    GL11.glPushMatrix();
    GL11.glTranslatef(0.0F, -0.55F + bob + (te.warmedUpNumber/-160F), 0.0F);
    float rotationDegrees = 2*((te.rotationTicks) % 360);
    
    GL11.glRotatef(rotationDegrees, 0.0F, 1.0F, 0.0F);
    GL11.glTranslatef(0.0F, 0.0F, -0.266F);
    UtilsFX.bindTexture("textures/models/brain2.png");
    GL11.glScalef(0.3F, 0.3F, 0.3F);
    this.brain.render();
    GL11.glPopMatrix();
    
    GL11.glPushMatrix();
    GL11.glTranslatef(0.0F, -0.55F + bob2 + (te.warmedUpNumber/-160F), 0.0F);
    GL11.glRotatef(rotationDegrees+120F, 0.0F, 1.0F, 0.0F);
    GL11.glTranslatef(0.0F, 0.0F, -0.266F);
    UtilsFX.bindTexture("textures/models/brain2.png");
    GL11.glScalef(0.3F, 0.3F, 0.3F);
    this.brain.render();
    GL11.glPopMatrix();
    
    GL11.glPushMatrix();
    GL11.glTranslatef(0.0F, -0.55F + bob3 + (te.warmedUpNumber/-160F), 0.0F);
    GL11.glRotatef(rotationDegrees+240F, 0.0F, 1.0F, 0.0F);
    GL11.glTranslatef(0.0F, 0.0F, -0.266F);
    UtilsFX.bindTexture("textures/models/brain2.png");
    GL11.glScalef(0.3F, 0.3F, 0.3F);
    this.brain.render();
    
    
    GL11.glScalef(1.0F, 1.0F, 1.0F);
    GL11.glPopMatrix();
    this.bindTexture(brineTexture);
    this.model.renderBrine();
  }
  
  public void renderBrainInventory( double x, double y, double z, float f)
  {
	float bob = MathHelper.sin(0 / 14.0F) * 0.03F + 0.03F; 
    float bob2 = MathHelper.sin((0 +9.32F)/ 14.0F) * 0.03F + 0.03F;
    float bob3 = MathHelper.sin((0 +4.66F)/ 14.0F) * 0.03F + 0.03F;
    GL11.glPushMatrix();
    GL11.glTranslatef(0.0F, -0.55F + bob + (40/-160F), 0.0F);
    float rotationDegrees = 2*((0) % 360);
    
    GL11.glRotatef(rotationDegrees, 0.0F, 1.0F, 0.0F);
    GL11.glTranslatef(0.0F, 0.0F, -0.266F);
    UtilsFX.bindTexture("textures/models/brain2.png");
    GL11.glScalef(0.3F, 0.3F, 0.3F);
    this.brain.render();
    GL11.glPopMatrix();
    
    GL11.glPushMatrix();
    GL11.glTranslatef(0.0F, -0.55F + bob2 + (40/-160F), 0.0F);
    GL11.glRotatef(rotationDegrees+120F, 0.0F, 1.0F, 0.0F);
    GL11.glTranslatef(0.0F, 0.0F, -0.266F);
    UtilsFX.bindTexture("textures/models/brain2.png");
    GL11.glScalef(0.3F, 0.3F, 0.3F);
    this.brain.render();
    GL11.glPopMatrix();
    
    GL11.glPushMatrix();
    GL11.glTranslatef(0.0F, -0.55F + bob3 + (40/-160F), 0.0F);
    GL11.glRotatef(rotationDegrees+240F, 0.0F, 1.0F, 0.0F);
    GL11.glTranslatef(0.0F, 0.0F, -0.266F);
    UtilsFX.bindTexture("textures/models/brain2.png");
    GL11.glScalef(0.3F, 0.3F, 0.3F);
    this.brain.render();
    
    
    GL11.glScalef(1.0F, 1.0F, 1.0F);
    GL11.glPopMatrix();
    this.bindTexture(brineTexture);
    this.model.renderBrine();
  }
  
  public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
  {
    renderTileEntityAt((TileEntityThinkTank)par1TileEntity, par2, par4, par6, par8);
  }
}

