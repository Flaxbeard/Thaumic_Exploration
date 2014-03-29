package flaxbeard.thaumicexploration.client.render;

import java.awt.Color;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import thaumcraft.client.renderers.block.BlockRenderer;
import thaumcraft.common.blocks.BlockCandle;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import flaxbeard.thaumicexploration.ThaumicExploration;

public class BlockFloatyCandleRenderer
  extends BlockRenderer
  implements ISimpleBlockRenderingHandler
{
  public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
  {
    Color c = new Color(thaumcraft.common.lib.Utils.colors[metadata]);
    float r = c.getRed() / 255.0F;
    float g = c.getGreen() / 255.0F;
    float b = c.getBlue() / 255.0F;
    GL11.glColor3f(r, g, b);
    block.setBlockBounds(BlockRenderer.W6, 0.0F, BlockRenderer.W6, BlockRenderer.W10, 0.5F, BlockRenderer.W10);
    renderer.setRenderBoundsFromBlock(block);
    drawFaces(renderer, block, ((BlockCandle)block).icon, true);
    GL11.glColor3f(1.0F, 1.0F, 1.0F);
    
    block.setBlockBounds(0.475F, 0.5F, 0.475F, 0.525F, BlockRenderer.W10, 0.525F);
    renderer.setRenderBoundsFromBlock(block);
    drawFaces(renderer, block, ((BlockCandle)block).iconStub, true);
  }
  
  public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
  {
	return false;
    
  }
  
  public boolean shouldRender3DInInventory()
  {
    return true;
  }
  
  public int getRenderId()
  {
    return ThaumicExploration.floatCandleRenderID;
  }
}
