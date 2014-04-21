//package flaxbeard.thaumicexploration.client.render;
//
//import java.awt.Color;
//import java.util.Random;
//
//import net.minecraft.block.Block;
//import net.minecraft.client.renderer.RenderBlocks;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.world.IBlockAccess;
//
//import org.lwjgl.opengl.GL11;
//
//import thaumcraft.client.renderers.block.BlockRenderer;
//import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
//import flaxbeard.thaumicexploration.ThaumicExploration;
//import flaxbeard.thaumicexploration.block.BlockSkullCandle;
//
//public class BlockSkullCandleRenderer
//  extends BlockRenderer
//  implements ISimpleBlockRenderingHandler
//{
//   
//  public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
//  {
//    Color c = new Color(thaumcraft.common.lib.Utils.colors[metadata]);
//    float r = c.getRed() / 255.0F;
//    float g = c.getGreen() / 255.0F;
//    float b = c.getBlue() / 255.0F;
//    GL11.glColor3f(r, g, b);
//    block.setBlockBounds(BlockRenderer.W6, 0.0F, BlockRenderer.W6, BlockRenderer.W10, 0.5F, BlockRenderer.W10);
//    renderer.setRenderBoundsFromBlock(block);
//    drawFaces(renderer, block, ((BlockSkullCandle)block).icon, true);
//    GL11.glColor3f(1.0F, 1.0F, 1.0F);
//    
//    block.setBlockBounds(0.475F, 0.5F, 0.475F, 0.525F, BlockRenderer.W10, 0.525F);
//    renderer.setRenderBoundsFromBlock(block);
//    drawFaces(renderer, block, ((BlockSkullCandle)block).iconStub, true);
//  }
//  
//  
//  
//  public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
//  {
//    int type = 0;
//    float xOffset=0;
//    float yOffset=0;
//    float zOffset=0;
//    if (world.getBlockMetadata(x, y, z) != 1) {
//    	yOffset = BlockRenderer.W4;
//        renderer.overrideBlockTexture = ((BlockSkullCandle)block).icon2;
//    }
//    if (world.getBlockMetadata(x, y, z) == 2)
//    	zOffset = 0.25F;
//    if (world.getBlockMetadata(x, y, z) == 3)
//    	zOffset = -0.25F;
//    if (world.getBlockMetadata(x, y, z) == 4)
//    	xOffset = 0.25F;
//    if (world.getBlockMetadata(x, y, z) == 5)
//    	xOffset = -0.25F;
//    block.setBlockBounds(BlockRenderer.W6+xOffset, 0.0F+BlockRenderer.W8+yOffset, BlockRenderer.W6 + zOffset, BlockRenderer.W10+xOffset, 0.5F+BlockRenderer.W5+yOffset, BlockRenderer.W10 + zOffset);
//    renderer.setRenderBoundsFromBlock(block);
//    renderer.renderStandardBlock(block, x, y, z);
//
////    renderer.overrideBlockTexture = ((BlockSkullCandle)block).iconSkull;
////    block.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
////    renderer.setRenderBoundsFromBlock(block);
////    renderer.renderStandardBlock(block, x, y, z);
////    renderer.clearOverrideBlockTexture();
//    Random rr = new Random(x + y * z);
//    int q = 4 + rr.nextInt(3);
//    for (int a = 0; a < q; a++)
//    {
//      boolean side = rr.nextBoolean();
//      int loc = 1 + rr.nextInt(4);
//      int n = 1;
//      if (loc > 1 && loc < 4)
//    	  n = 2;
//      if (a % 2 == 0)
//      {
//        block.setBlockBounds(BlockRenderer.W5 + BlockRenderer.W1 * loc + xOffset, 0.0F+BlockRenderer.W8+yOffset, side ? BlockRenderer.W5  + zOffset : BlockRenderer.W10 + zOffset, BlockRenderer.W6 + BlockRenderer.W1 * loc + xOffset, BlockRenderer.W1 * (1 + rr.nextInt(n))+BlockRenderer.W8+yOffset, side ? BlockRenderer.W6 + zOffset: BlockRenderer.W11 + zOffset);
//        
//
//        renderer.setRenderBoundsFromBlock(block);
//        renderer.renderStandardBlock(block, x, y, z);
//      }
//      else
//      {
//        block.setBlockBounds(side ? BlockRenderer.W5 + xOffset: BlockRenderer.W10 + xOffset, 0.0F+BlockRenderer.W8+yOffset, BlockRenderer.W5 + BlockRenderer.W1 * loc + zOffset, side ? BlockRenderer.W6 + xOffset: BlockRenderer.W11 + xOffset, BlockRenderer.W1 * (1 + rr.nextInt(n))+BlockRenderer.W8+yOffset, BlockRenderer.W6 + BlockRenderer.W1 * loc + zOffset);
//        
//
//        renderer.setRenderBoundsFromBlock(block);
//        renderer.renderStandardBlock(block, x, y, z);
//      }
//    }
//    renderer.overrideBlockTexture = ((BlockSkullCandle)block).iconStub;
//    block.setBlockBounds(0.475F+xOffset, 0.5F+BlockRenderer.W5+yOffset, 0.475F + zOffset, 0.525F+xOffset, BlockRenderer.W10+BlockRenderer.W5+yOffset, 0.525F + zOffset);
//    renderer.setRenderBoundsFromBlock(block);
//    renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, 1.0F, 1.0F, 1.0F);
//    
//    renderer.clearOverrideBlockTexture();
//    block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
//    renderer.setRenderBoundsFromBlock(block);
//    return true;
//  }
//  
//  public boolean shouldRender3DInInventory()
//  {
//    return true;
//  }
//  
//  public int getRenderId()
//  {
//    return ThaumicExploration.candleSkullRenderID;
//  }
//}
