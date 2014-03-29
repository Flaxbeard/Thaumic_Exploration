package flaxbeard.thaumicexploration.client.render;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import thaumcraft.common.config.ConfigBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flaxbeard.thaumicexploration.client.render.model.ModelCandle;

@SideOnly(Side.CLIENT)
public class TileEntityFloatyCandleRender extends TileEntitySpecialRenderer {
	private ModelCandle candleModel = new ModelCandle();
	private static final ResourceLocation candleTexture = new ResourceLocation("thaumicexploration:textures/models/floatyCandle.png");
	private static final ResourceLocation wickTexture = new ResourceLocation("thaumicexploration:textures/models/floatyCandleStub.png");

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1,
			double d2, float f) {
		// TODO Auto-generated method stub
        GL11.glPushMatrix();
        int ticks = Minecraft.getMinecraft().thePlayer.ticksExisted;
        int offset = (int) (tileentity.xCoord+tileentity.yCoord+tileentity.zCoord);
        //offset = 0;
        float move = 0.2F*MathHelper.sin(((offset*10)+ticks)/30.0F);
        GL11.glTranslatef((float)d0, (float)d1+move, (float)d2);
        this.bindTexture(wickTexture);
        this.candleModel.renderWick();
        this.bindTexture(candleTexture);
        Color color = new Color(ConfigBlocks.blockCandle.getRenderColor(tileentity.worldObj.getBlockMetadata(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord)));
        //System.out.println(ConfigBlocks.blockCandle.getRenderColor(tileentity.worldObj.getBlockMetadata(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord)));
        GL11.glColor3ub((byte)color.getRed(), (byte)color.getGreen(),(byte) color.getBlue());
        this.candleModel.renderAll(ticks);
        GL11.glPopMatrix();
        
	}

}
