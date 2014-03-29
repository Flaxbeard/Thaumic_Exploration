//package flaxbeard.thaumicexploration.client.render;
//
//import net.minecraft.block.Block;
//import net.minecraft.client.renderer.RenderBlocks;
//import net.minecraft.client.renderer.Tessellator;
//import net.minecraft.world.IBlockAccess;
//import thaumcraft.client.renderers.block.BlockRenderer;
//import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
//import flaxbeard.thaumicexploration.ThaumicExploration;
//import flaxbeard.thaumicexploration.block.BlockNecroPedestal;
//
//public class BlockNecroPedestalRenderer
//  extends BlockRenderer
//  implements ISimpleBlockRenderingHandler
//{
//  public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
//  {
//	    Tessellator tessellator = Tessellator.instance;
//  }
//  
//  public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
//  {
//    int metadata = world.getBlockMetadata(x, y, z);
//
//    Tessellator tessellator = Tessellator.instance;
//
//    
//      block.setBlockBounds(0.0F, 0.25F, 0.0F, 1.0F, 0.25F, 1.0F);
//      
//      
//      
//      renderer.setRenderBoundsFromBlock(block);
//      renderer.renderStandardBlock(block, x, y, z);
//      
//
//      block.setBlockBounds(0.0F-W8, 0.0F, 0.0F-W8, 1.0F+W8, 0.25F, 1.0F+W8);
//      renderer.setRenderBoundsFromBlock(block);
//     // renderer.overrideBlockTexture = ((BlockNecroPedestal)block).iconPedestal[3];
//      renderer.renderFaceYPos(block, x, y, z, ((BlockNecroPedestal)block).iconPedestal[4]);
//      renderer.renderFaceYNeg(block, x, y, z, ((BlockNecroPedestal)block).iconPedestal[4]);
//      
//      block.setBlockBounds(0.0F-W8, 0.0F, 0.0F-W8, 1.0F+W8, 2.0F, 1.0F+W8);
//      renderer.renderFaceZPos(block, x, y, z, ((BlockNecroPedestal)block).iconPedestal[2]);
//      renderer.renderFaceXPos(block, x, y, z, ((BlockNecroPedestal)block).iconPedestal[2]);
//
//      renderer.renderFaceZNeg(block, x, y, z, ((BlockNecroPedestal)block).iconPedestal[2]);
//      renderer.renderFaceXNeg(block, x, y, z, ((BlockNecroPedestal)block).iconPedestal[2]);
////      renderer.clearOverrideBlockTexture();
//////      
////      Icon bottom = ((BlockNecroPedestal)block).iconPedestal[3];
////      renderer.renderFaceYPos(block, x, y + W4, z, bottom);
//      
//      block.setBlockBounds(0.0F, 0.25F, 0.0F, 1.0F, 0.75F, 1.0F);
//      renderer.setRenderBoundsFromBlock(block);
//      renderer.renderStandardBlock(block, x, y, z);
//      
//      block.setBlockBounds(0.0F-W4, 0.25F, 0.0F-W4, 1.0F+W4, 0.75F+W1, 1.0F+W4);
//      renderer.setRenderBoundsFromBlock(block);
//      renderer.renderFaceYPos(block, x, y, z, ((BlockNecroPedestal)block).iconPedestal[3]);
//      block.setBlockBounds(0.0F-W4, 0.25F, 0.0F-W4, 1.0F+W4, 0.25F+1.5F, 1.0F+W4);
//      renderer.setRenderBoundsFromBlock(block);
//      renderer.renderFaceZPos(block, x, y, z, ((BlockNecroPedestal)block).iconPedestal[5]);
//      renderer.renderFaceXPos(block, x, y, z, ((BlockNecroPedestal)block).iconPedestal[5]);
//      renderer.renderFaceZNeg(block, x, y, z, ((BlockNecroPedestal)block).iconPedestal[5]);
//      renderer.renderFaceXNeg(block, x, y, z, ((BlockNecroPedestal)block).iconPedestal[5]);
//      
//      block.setBlockBounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
//      renderer.setRenderBoundsFromBlock(block);
//      renderer.renderStandardBlock(block, x, y, z);
//    
//   
//    renderer.clearOverrideBlockTexture();
//    block.setBlockBounds(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
//    renderer.setRenderBoundsFromBlock(block);
//    return true;
//  }
//  
//  public boolean shouldRender3DInInventory()
//  {
//    return false;
//  }
//  
//  public int getRenderId()
//  {
//    return ThaumicExploration.necroPedestalRenderID;
//  }
//}
