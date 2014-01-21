package flaxbeard.thaumicexploration.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import thaumcraft.client.renderers.block.BlockRenderer;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import flaxbeard.thaumicexploration.ThaumicExploration;
import flaxbeard.thaumicexploration.block.BlockCrucibleSouls;
import flaxbeard.thaumicexploration.block.BlockReplicator;
import flaxbeard.thaumicexploration.tile.TileEntityCrucibleSouls;

public class BlockReplicatorRenderer
  extends BlockRenderer
  implements ISimpleBlockRenderingHandler
{
  public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
  {
      block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      renderer.setRenderBoundsFromBlock(block);
      drawFaces(renderer, block, ((BlockReplicator)block).icon[0], ((BlockReplicator)block).icon[0], ((BlockReplicator)block).icon[1], ((BlockReplicator)block).icon[1], ((BlockReplicator)block).icon[1], ((BlockReplicator)block).icon[1], true);
  }
  
  public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
  {
	  
	  int metadata = world.getBlockMetadata(x, y, z);
      block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      setBrightness(world, x, y, z, block);
      renderer.setRenderBoundsFromBlock(block);
      renderer.renderStandardBlock(block, x, y, z);
      Icon top = ((BlockReplicator)block).icon[2];
     
      

      

      float f5 = 0.001F;
      renderer.renderFaceXPos(block, x + f5, y+1.0F, z, top);
      renderer.renderFaceXNeg(block, x - f5, y+1.0F, z, top);
      renderer.renderFaceZPos(block, x, y+1.0F, z + f5, top);
      renderer.renderFaceZNeg(block, x, y+1.0F, z - f5, top);
      f5 = 0.0F;
      renderer.renderFaceXPos(block, x + f5, y+1.0F, z, top);
      renderer.renderFaceXNeg(block, x - f5, y+1.0F, z, top);
      renderer.renderFaceZPos(block, x, y+1.0F, z + f5, top);
      renderer.renderFaceZNeg(block, x, y+1.0F, z - f5, top);
      f5 = -0.999F;
      renderer.renderFaceXPos(block, x + f5, y+1.0F, z, top);
      renderer.renderFaceXNeg(block, x - f5, y+1.0F, z, top);
      renderer.renderFaceZPos(block, x, y+1.0F, z + f5, top);
      renderer.renderFaceZNeg(block, x, y+1.0F, z - f5, top);
      
    
    renderer.clearOverrideBlockTexture();
    block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    renderer.setRenderBoundsFromBlock(block);
    return true;
  }
  
  public boolean shouldRender3DInInventory()
  {
    return true;
  }
  
  public int getRenderId()
  {
    return ThaumicExploration.replicatorRenderID;
  }
}
