package flaxbeard.thaumicexploration.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import flaxbeard.thaumicexploration.client.render.TileEntityBoundChestRender;
import flaxbeard.thaumicexploration.client.render.TileEntityBoundJarRender;
import flaxbeard.thaumicexploration.client.render.TileEntityThinkTankRender;
import flaxbeard.thaumicexploration.common.CommonProxy;
import flaxbeard.thaumicexploration.tile.TileEntityBoundChest;
import flaxbeard.thaumicexploration.tile.TileEntityBoundJar;
import flaxbeard.thaumicexploration.tile.TileEntityThinkTank;
public class ClientProxy extends CommonProxy
{
	
    @Override
    public void registerRenderers()
    {
    	 ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBoundJar.class, new TileEntityBoundJarRender());
    	 ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBoundChest.class, new TileEntityBoundChestRender());
    	 ClientRegistry.bindTileEntitySpecialRenderer(TileEntityThinkTank.class, new TileEntityThinkTankRender());
    	
    	 
    }

}