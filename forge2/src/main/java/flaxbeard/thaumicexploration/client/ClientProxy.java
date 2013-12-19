package flaxbeard.thaumicexploration.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import flaxbeard.thaumicexploration.common.CommonProxy;
import flaxbeard.thaumicexploration.tile.TileEntityBoundChest;
import flaxbeard.thaumicexploration.client.render.TileEntityBoundChestRender;
public class ClientProxy extends CommonProxy
{
	
    @Override
    public void registerRenderers()
    {
    	 ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBoundChest.class, new TileEntityBoundChestRender());
    }

}