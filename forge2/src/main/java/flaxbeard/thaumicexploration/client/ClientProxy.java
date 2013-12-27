package flaxbeard.thaumicexploration.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import flaxbeard.thaumicexploration.client.render.TileEntityBoundChestRender;
import flaxbeard.thaumicexploration.client.render.TileEntityThinkTankBookshelfRender;
import flaxbeard.thaumicexploration.common.CommonProxy;
import flaxbeard.thaumicexploration.tile.TileEntityBoundChest;
import flaxbeard.thaumicexploration.tile.TileEntityThinkTankBookshelf;
public class ClientProxy extends CommonProxy
{
	
    @Override
    public void registerRenderers()
    {
    	 ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBoundChest.class, new TileEntityBoundChestRender());
    	 ClientRegistry.bindTileEntitySpecialRenderer(TileEntityThinkTankBookshelf.class, new TileEntityThinkTankBookshelfRender());
    	 
    }

}