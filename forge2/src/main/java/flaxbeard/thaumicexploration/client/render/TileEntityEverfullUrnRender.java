package flaxbeard.thaumicexploration.client.render;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flaxbeard.thaumicexploration.client.render.model.ModelUrn;
import flaxbeard.thaumicexploration.tile.TileEntityEverfullUrn;

@SideOnly(Side.CLIENT)
public class TileEntityEverfullUrnRender extends TileEntitySpecialRenderer
{
    private static final ResourceLocation field_110642_c = new ResourceLocation("textures/entity/skeleton/skeleton.png");
    private static final ResourceLocation field_110640_d = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
    private static final ResourceLocation field_110641_e = new ResourceLocation("textures/entity/zombie/zombie.png");
    private static final ResourceLocation seal = new ResourceLocation("creeper.png");
    public static TileEntityEverfullUrnRender skullRenderer;
    private ModelUrn model = new ModelUrn();



    public void renderTileEntityEverfullUrnAt(TileEntityEverfullUrn par1TileEntityEverfullUrn, double x, double y, double z, float par8)
    {
    	GL11.glPushMatrix();

    	GL11.glTranslatef((float)x, (float)y+0.5F, (float)z+1);
    	GL11.glScalef(1.0F, -1.0F, -1.0F);
    	GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    	this.bindTexture(seal);
        model.renderAll();
        GL11.glPopMatrix();
    }

    public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
    {
        this.renderTileEntityEverfullUrnAt((TileEntityEverfullUrn)par1TileEntity, par2, par4, par6, par8);
    }
}
