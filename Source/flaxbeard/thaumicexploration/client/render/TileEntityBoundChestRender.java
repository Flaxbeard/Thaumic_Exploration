package flaxbeard.thaumicexploration.client.render;

import java.util.Calendar;

import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flaxbeard.thaumicexploration.client.render.model.ModelChestOverlay;
import flaxbeard.thaumicexploration.tile.TileEntityBoundChest;

@SideOnly(Side.CLIENT)
public class TileEntityBoundChestRender extends TileEntitySpecialRenderer
{
    private static final ResourceLocation field_110637_a = new ResourceLocation("textures/entity/chest/normal.png");
    private static final ResourceLocation christmasTexture = new ResourceLocation("textures/entity/chest/christmas.png");
    private static final ResourceLocation overlayn = new ResourceLocation("thaumicexploration:textures/blocks/boundchestoverlaynone.png");
    private static final ResourceLocation overlay0 = new ResourceLocation("thaumicexploration:textures/blocks/boundchestoverlay0.png");
    private static final ResourceLocation overlay1 = new ResourceLocation("thaumicexploration:textures/blocks/boundchestoverlay1.png");
    private static final ResourceLocation overlay2 = new ResourceLocation("thaumicexploration:textures/blocks/boundchestoverlay2.png");
    private static final ResourceLocation overlay3 = new ResourceLocation("thaumicexploration:textures/blocks/boundchestoverlay3.png");
    private static final ResourceLocation overlay4 = new ResourceLocation("thaumicexploration:textures/blocks/boundchestoverlay4.png");
    private static final ResourceLocation[] overlays = {overlayn,overlay0,overlay1,overlay2,overlay3,overlay4};
    private static final ResourceLocation seal = new ResourceLocation("thaumicexploration:textures/blocks/boundchestoverlayseal.png");
    private static final ResourceLocation christmasSeal = new ResourceLocation("thaumicexploration:textures/blocks/boundchestoverlaysealchristmas.png");
    /** The Ender Chest Chest's model. */
    private ModelChest theEnderChestModel = new ModelChest();
    private ModelChestOverlay theOverlayModel = new ModelChestOverlay();

    /**
     * Helps to render Ender Chest.
     */
    public void renderBoundChest(TileEntityBoundChest par1TileEntityEnderChest, double par2, double par4, double par6, float par8)
    {
        int i = 0;

        if (par1TileEntityEnderChest.hasWorldObj())
        {
            i = par1TileEntityEnderChest.getBlockMetadata();
        }

        this.bindTexture(field_110637_a);
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26)
        {
        	this.bindTexture(christmasTexture);
        }
        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float)par2, (float)par4 + 1.0F, (float)par6 + 1.0F);
        GL11.glScalef(1.0F, -1.0F, -1.0F);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        short short1 = 0;

        if (i == 2)
        {
            short1 = 180;
        }

        if (i == 3)
        {
            short1 = 0;
        }

        if (i == 4)
        {
            short1 = 90;
        }

        if (i == 5)
        {
            short1 = -90;
        }

        GL11.glRotatef((float)short1, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        float f1 = par1TileEntityEnderChest.prevLidAngle + (par1TileEntityEnderChest.lidAngle - par1TileEntityEnderChest.prevLidAngle) * par8;
        f1 = 1.0F - f1;
        f1 = 1.0F - f1 * f1 * f1;
        this.theEnderChestModel.chestLid.rotateAngleX = -(f1 * (float)Math.PI / 2.0F);
        this.theEnderChestModel.renderAll();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
    
    public void renderBoundChestFrame(TileEntityBoundChest par1TileEntityEnderChest, double par2, double par4, double par6, float par8)
    {
    	
        int i = 0;

        if (par1TileEntityEnderChest.hasWorldObj())
        {
            i = par1TileEntityEnderChest.getBlockMetadata();
        }
        int ticks = par1TileEntityEnderChest.getAccessTicks();
        if (ticks > 0) {
        	double divisor = (80/6) + 0.0001;
        	double frame = ((par1TileEntityEnderChest.getAccessTicks()-1) / divisor)-1;
        	int trueFrame = (int) Math.ceil(frame+0.5);
        	if (trueFrame > 5) {
        		trueFrame = 5;
        	}
        	if (trueFrame < 0) {
        		trueFrame = 0;
        	}
        	this.bindTexture(overlays[trueFrame]);
        }
        else
        {
        	this.bindTexture(overlays[0]);
        }
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        float colormod = 1.0F;
        
        int j = par1TileEntityEnderChest.clientColor;
        GL11.glColor4f(colormod * EntitySheep.fleeceColorTable[j][0], colormod * EntitySheep.fleeceColorTable[j][1], colormod * EntitySheep.fleeceColorTable[j][2], 0.9F);
        GL11.glTranslatef((float)par2, (float)par4 + 1.0F, (float)par6 + 1.0F);
        //float size = 0*(16.0F/14.0F);
        float size = 1.0F;
        GL11.glScalef(size, -size, -size);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        short short1 = 0;

        if (i == 2)
        {
            short1 = 180;
        }

        if (i == 3)
        {
            short1 = 0;
        }

        if (i == 4)
        {
            short1 = 90;
        }

        if (i == 5)
        {
            short1 = -90;
        }

        GL11.glRotatef((float)short1, 0.0F, 1.0F, 0.0F);
        float offset = 0*(1F/14F);
        GL11.glTranslatef(-0.5F-offset, -0.5F-(2*offset), -0.5F+offset);
        float f1 = par1TileEntityEnderChest.prevLidAngle + (par1TileEntityEnderChest.lidAngle - par1TileEntityEnderChest.prevLidAngle) * par8;
        f1 = 1.0F - f1;
        f1 = 1.0F - f1 * f1 * f1;
        this.theOverlayModel.chestLid.rotateAngleX = -(f1 * (float)Math.PI / 2.0F);
        this.theOverlayModel.renderAll();

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
    
    public void renderBoundChestSeal(TileEntityBoundChest par1TileEntityEnderChest, double par2, double par4, double par6, float par8)
    {
    	
        int i = 0;

        if (par1TileEntityEnderChest.hasWorldObj())
        {
            i = par1TileEntityEnderChest.getBlockMetadata();
        }
        int ticks = par1TileEntityEnderChest.getAccessTicks();
        
        this.bindTexture(seal);
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26)
        {
        	this.bindTexture(christmasSeal);
        }
        GL11.glPushMatrix();
        
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        float colormod = 1.0F;
        
        int j = par1TileEntityEnderChest.clientColor;
        GL11.glColor4f(colormod * EntitySheep.fleeceColorTable[j][0], colormod * EntitySheep.fleeceColorTable[j][1], colormod * EntitySheep.fleeceColorTable[j][2], 1.0F);
        GL11.glTranslatef((float)par2, (float)par4 + 1.0F, (float)par6 + 1.0F);
        //float size = 0*(16.0F/14.0F);
        float size = 1.0F;
        GL11.glScalef(size, -size, -size);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        short short1 = 0;

        if (i == 2)
        {
            short1 = 180;
        }

        if (i == 3)
        {
            short1 = 0;
        }

        if (i == 4)
        {
            short1 = 90;
        }

        if (i == 5)
        {
            short1 = -90;
        }

        GL11.glRotatef((float)short1, 0.0F, 1.0F, 0.0F);
        float offset = 0*(1F/14F);
        GL11.glTranslatef(-0.5F-offset, -0.5F-(2*offset), -0.5F+offset);
        float f1 = par1TileEntityEnderChest.prevLidAngle + (par1TileEntityEnderChest.lidAngle - par1TileEntityEnderChest.prevLidAngle) * par8;
        f1 = 1.0F - f1;
        f1 = 1.0F - f1 * f1 * f1;
        this.theOverlayModel.chestLid.rotateAngleX = -(f1 * (float)Math.PI / 2.0F);
        this.theOverlayModel.renderAll();

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }



    public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
    {
    	
        this.renderBoundChest((TileEntityBoundChest)par1TileEntity, par2, par4, par6, par8);
        this.renderBoundChestFrame((TileEntityBoundChest)par1TileEntity, par2, par4, par6, par8);
        this.renderBoundChestSeal((TileEntityBoundChest)par1TileEntity, par2, par4, par6, par8);
    }
}
