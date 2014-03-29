package flaxbeard.thaumicexploration.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import flaxbeard.thaumicexploration.ThaumicExploration;
import flaxbeard.thaumicexploration.block.BlockEverfullUrn;

public class BlockEverfullUrnRenderer implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
    	float f = (14.0F/16.0F);
    	float f1 = (2.0F/16.0F);

		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		float x = 0;
		float y = 0;
		float z = 0;
		IIcon icon = block.getBlockTextureFromSide(2);
        float f4 = 0;
        Tessellator tessellator = Tessellator.instance;
    	block.setBlockBounds(f1, 0.0F, f1, f, 1.0F, f);
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, (double)((float)x), (double)y, (double)z, icon);
        tessellator.draw();
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, (double)((float)x), (double)y, (double)z, icon);
        tessellator.draw();
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, (double)x, (double)y, (double)((float)z), icon);
        tessellator.draw();
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, (double)x, (double)y, (double)((float)z), icon);
        tessellator.draw();
        
        f4 = (3.0F/16.0F);
        IIcon icon1 = BlockEverfullUrn.middleSide;
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, (double)((float)x  - f4), (double)y, (double)z, icon1);
        tessellator.draw();
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, (double)((float)x  + f4), (double)y, (double)z, icon1);
        tessellator.draw();
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, (double)x, (double)y, (double)((float)z  + f4), icon1);
        tessellator.draw();
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, (double)x, (double)y, (double)((float)z - f4), icon1);
        tessellator.draw();
        
        f4 = (2.0F/16.0F);
        IIcon icon2 = BlockEverfullUrn.topSide;
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, (double)((float)x  - f4), (double)y, (double)z, icon2);
        tessellator.draw();
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, (double)((float)x  + f4), (double)y, (double)z, icon2);
        tessellator.draw();
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, (double)x, (double)y, (double)((float)z  + f4), icon2);
        tessellator.draw();
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, (double)x, (double)y, (double)((float)z - f4), icon2);
        tessellator.draw();
        
        IIcon icon3 = BlockEverfullUrn.topTop;
        IIcon icon4 = BlockEverfullUrn.bottomTop;
        f4 = (7.0F/16.0F);
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, (double)x, (double)((float)y-f4), (double)z, icon4);
        tessellator.draw();
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, (double)x, (double)((float)y ), (double)z, icon3);
        tessellator.draw();
        
        IIcon icon5 = BlockEverfullUrn.bottomBottom;
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderFaceYNeg(block, (double)x, (double)((float)y), (double)z, icon5);
        tessellator.draw();
        IIcon icon6 = BlockEverfullUrn.topBottom;
        f4 = (13.0F/16.0F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderFaceYNeg(block, (double)x, (double)((float)y+f4), (double)z, icon6);
        tessellator.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

	
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int par2, int par3, int par4,
			Block block, int modelId, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
			tessellator.setBrightness(block.getMixedBrightnessForBlock(world, par2, par3, par4));
	        
	        float f = 1.0F;
	        int l = block.colorMultiplier(world, par2, par3, par4);
	        float f1 = (float)(l >> 16 & 255) / 255.0F;
	        float f2 = (float)(l >> 8 & 255) / 255.0F;
	        float f3 = (float)(l & 255) / 255.0F;
	        float f4;
	        float xMove = (2.0F/16.0F);
	        xMove = 0;
	        float y = par3;
	        float x = par2 + xMove;
	        float z = par4 - xMove;
	
	        if (EntityRenderer.anaglyphEnable)
	        {
	            float f5 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
	            f4 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
	            float f6 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
	            f1 = f5;
	            f2 = f4;
	            f3 = f6;
	        }
	
	        tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
		
        IIcon icon = block.getBlockTextureFromSide(2);
       f4 = (2.0F/16.0F);
       f4 =0;
		block.setBlockBounds(0.0F,0.0F,0.0F,1.0F,1.0F,1.0F);
        renderer.renderFaceXPos(block, (double)((float)x  - f4), (double)y, (double)z, icon);
        renderer.renderFaceXNeg(block, (double)((float)x  + f4), (double)y, (double)z, icon);
        renderer.renderFaceZNeg(block, (double)x, (double)y, (double)((float)z  + f4), icon);
        renderer.renderFaceZPos(block, (double)x, (double)y, (double)((float)z - f4), icon);
        f4 = (3.0F/16.0F);
        IIcon icon1 = BlockEverfullUrn.middleSide;
        renderer.renderFaceXPos(block, (double)((float)x  - f4), (double)y, (double)z, icon1);
        renderer.renderFaceXNeg(block, (double)((float)x  + f4), (double)y, (double)z, icon1);
        renderer.renderFaceZNeg(block, (double)x, (double)y, (double)((float)z  + f4), icon1);
        renderer.renderFaceZPos(block, (double)x, (double)y, (double)((float)z - f4), icon1);
        f4 = (2.0F/16.0F);
        IIcon icon2 = BlockEverfullUrn.topSide;
        renderer.renderFaceXPos(block, (double)((float)x  - f4), (double)y, (double)z, icon2);
        renderer.renderFaceXNeg(block, (double)((float)x  + f4), (double)y, (double)z, icon2);
        renderer.renderFaceZNeg(block, (double)x, (double)y, (double)((float)z  + f4), icon2);
        renderer.renderFaceZPos(block, (double)x, (double)y, (double)((float)z - f4), icon2);
        IIcon icon3 = BlockEverfullUrn.topTop;
       IIcon icon4 = BlockEverfullUrn.bottomTop;
        f4 = (7.0F/16.0F);
        renderer.renderFaceYPos(block, (double)x, (double)((float)y-f4), (double)z, icon4);
        renderer.renderFaceYPos(block, (double)x, (double)((float)y ), (double)z, icon3);
        f4 = (13.0F/16.0F);
        IIcon icon5 = BlockEverfullUrn.bottomBottom;
        IIcon icon6 = BlockEverfullUrn.topBottom;
        renderer.renderFaceYNeg(block, (double)x, (double)((float)y), (double)z, icon5);
        renderer.renderFaceYNeg(block, (double)x, (double)((float)y+f4), (double)z, icon6);
        

        return true;
	}
	


	@Override
	public int getRenderId() {
		// TODO Auto-generated method stub
		
		return ThaumicExploration.everfullUrnRenderID;
	}



	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		// TODO Auto-generated method stub
		return modelId == ThaumicExploration.everfullUrnRenderID;
	}

}
