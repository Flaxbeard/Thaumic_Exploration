package flaxbeard.thaumicexploration.client;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.client.fx.FXBoreParticles;
import thaumcraft.client.fx.FXBoreSparkle;
import thaumcraft.client.fx.FXEssentiaTrail;
import thaumcraft.client.fx.FXLightningBolt;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import flaxbeard.thaumicexploration.ThaumicExploration;
import flaxbeard.thaumicexploration.client.render.BlockCrucibleSoulsRenderer;
import flaxbeard.thaumicexploration.client.render.BlockEverfullUrnRenderer;
import flaxbeard.thaumicexploration.client.render.BlockFloatyCandleRenderer;
import flaxbeard.thaumicexploration.client.render.BlockNecroPedestalRenderer;
import flaxbeard.thaumicexploration.client.render.BlockReplicatorRenderer;
import flaxbeard.thaumicexploration.client.render.BlockSkullCandleRenderer;
import flaxbeard.thaumicexploration.client.render.ItemRenderThinkTank;
import flaxbeard.thaumicexploration.client.render.RenderCandleFlame;
import flaxbeard.thaumicexploration.client.render.TileEntityBoundChestRender;
import flaxbeard.thaumicexploration.client.render.TileEntityBoundJarRender;
import flaxbeard.thaumicexploration.client.render.TileEntityFloatyCandleRender;
import flaxbeard.thaumicexploration.client.render.TileEntityNecroPedestalRenderer;
import flaxbeard.thaumicexploration.client.render.TileEntityRenderCrucibleSouls;
import flaxbeard.thaumicexploration.client.render.TileEntityReplicatorRender;
import flaxbeard.thaumicexploration.client.render.TileEntityThinkTankRender;
import flaxbeard.thaumicexploration.common.CommonProxy;
import flaxbeard.thaumicexploration.entity.EntityCandleFlame;
import flaxbeard.thaumicexploration.tile.TileEntityBoundChest;
import flaxbeard.thaumicexploration.tile.TileEntityBoundJar;
import flaxbeard.thaumicexploration.tile.TileEntityCrucibleSouls;
import flaxbeard.thaumicexploration.tile.TileEntityFloatyCandle;
import flaxbeard.thaumicexploration.tile.TileEntityNecroPedestal;
import flaxbeard.thaumicexploration.tile.TileEntityReplicator;
import flaxbeard.thaumicexploration.tile.TileEntityThinkTank;
public class ClientProxy extends CommonProxy
{
	
    @Override
    public void registerRenderers()
    {
    	  RenderingRegistry.registerEntityRenderingHandler(EntityCandleFlame.class, new RenderCandleFlame(ThaumicExploration.theCandle));
    	 ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBoundJar.class, new TileEntityBoundJarRender());
    	 ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBoundChest.class, new TileEntityBoundChestRender());
    	 ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrucibleSouls.class, new TileEntityRenderCrucibleSouls());
    	 ClientRegistry.bindTileEntitySpecialRenderer(TileEntityReplicator.class, new TileEntityReplicatorRender());
    	 ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNecroPedestal.class, new TileEntityNecroPedestalRenderer());
    	 ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFloatyCandle.class, new TileEntityFloatyCandleRender());
    	 
    	 TileEntitySpecialRenderer renderThinkTank = new TileEntityThinkTankRender();
    	 ClientRegistry.bindTileEntitySpecialRenderer(TileEntityThinkTank.class, renderThinkTank);
    	
    	 RenderingRegistry.registerBlockHandler(ThaumicExploration.floatCandleRenderID, new BlockFloatyCandleRenderer());
    	 RenderingRegistry.registerBlockHandler(ThaumicExploration.necroPedestalRenderID, new BlockNecroPedestalRenderer());
    	 RenderingRegistry.registerBlockHandler(ThaumicExploration.candleSkullRenderID, new BlockSkullCandleRenderer());
    	 RenderingRegistry.registerBlockHandler(ThaumicExploration.everfullUrnRenderID, new BlockEverfullUrnRenderer());
    	 RenderingRegistry.registerBlockHandler(ThaumicExploration.replicatorRenderID, new BlockReplicatorRenderer());
    	 RenderingRegistry.registerBlockHandler(ThaumicExploration.crucibleSoulsRenderID, new BlockCrucibleSoulsRenderer());
    	 MinecraftForgeClient.registerItemRenderer(ThaumicExploration.thinkTankJar.blockID, new ItemRenderThinkTank(renderThinkTank, new TileEntityThinkTank()));
    	 //MinecraftForgeClient.registerItemRenderer(ThaumicExploration.everfullUrn.blockID, new ItemRenderEverfullUrn(new TileEntityEverfullUrnRender(), new TileEntityEverfullUrn()));
    }
    @Override
    public void setUnicode() {
    	Minecraft.getMinecraft().fontRenderer.setUnicodeFlag(false);
    }
    

    
    @Override
    public void spawnWaterOnPlayer(World worldObj, int xCoord, int yCoord, int zCoord, EntityPlayer player) {
    	FXEssentiaTrail fx = new FXEssentiaTrail(worldObj, xCoord+0.5F, yCoord+1.1F, zCoord+0.5F, player.posX, player.posY, player.posZ, 5, Aspect.TOOL.getColor(), 1.0F);
    	Minecraft.getMinecraft().effectRenderer.addEffect(fx);
    }
    
    @Override
    public void spawnLightningBolt(World worldObj, double xCoord, double  yCoord, double zCoord, double dX, double dY, double dZ) {
    	FXLightningBolt bolt = new FXLightningBolt(worldObj,xCoord,yCoord,zCoord,dX,dY,dZ, worldObj.rand.nextLong(), 6, 0.5F, 5);
		bolt.defaultFractal();
	    bolt.setType(4);
	    bolt.setWidth(0.05F);
	    bolt.finalizeBolt();
    }
    
    @Override
    public void spawnRandomWaterFountain(World worldObj, int xCoord, int yCoord, int zCoord) {
		FXEssentiaTrail fx = new FXEssentiaTrail(worldObj, xCoord+0.5F, yCoord+1.1F, zCoord+0.5F, xCoord+0.5F+((Math.random())-0.5), yCoord+2.1F, zCoord+0.5F+((Math.random())-0.5), 5, Aspect.TOOL.getColor(), 1.0F);
		Minecraft.getMinecraft().effectRenderer.addEffect(fx);
    }
    
    @Override
	public void spawnWaterAtLocation(World worldObj,  double xCoord, double  yCoord, double zCoord, double dX, double dY, double dZ) {
		FXEssentiaTrail fx = new FXEssentiaTrail(worldObj, xCoord, yCoord, zCoord, dX, dY, dZ, 5, Aspect.TOOL.getColor(), 1.0F);
		Minecraft.getMinecraft().effectRenderer.addEffect(fx);
    }
    
    @Override
	public void spawnEssentiaAtLocation(World worldObj,  double xCoord, double  yCoord, double zCoord, double dX, double dY, double dZ, int size, int color) {
		FXEssentiaTrail fx = new FXEssentiaTrail(worldObj, xCoord, yCoord, zCoord, dX, dY, dZ, size, color, 1.0F);
		Minecraft.getMinecraft().effectRenderer.addEffect(fx);
    }
    
    @Override
	public void spawnBoreSparkle(World worldObj, double xCoord, double yCoord, double zCoord, double x2, double y2, double z2) {
	    FXBoreSparkle fb = new FXBoreSparkle(worldObj, xCoord, yCoord, zCoord, x2, y2, z2);
	    
	    fb.setRBGColorF(0.4F + worldObj.rand.nextFloat() * 0.2F, 0.2F, 0.6F + worldObj.rand.nextFloat() * 0.3F);
	    Minecraft.getMinecraft().effectRenderer.addEffect(fb);
    }
    
    @Override
    public void spawnHarvestParticle(World worldObj, double xCoord, double yCoord, double zCoord, double x2, double y2, double z2) {
	    FXBoreParticles fb = new FXBoreParticles(worldObj, xCoord, yCoord, zCoord, x2, y2, z2, Item.coal, worldObj.rand.nextInt(6), 3);
	    
		
	    fb.setAlphaF(0.3F);
		fb.motionX = ((float)worldObj.rand.nextGaussian() * 0.03F);
		fb.motionY = ((float)worldObj.rand.nextGaussian() * 0.03F);
		fb.motionZ = ((float)worldObj.rand.nextGaussian() * 0.03F);
	    FMLClientHandler.instance().getClient().effectRenderer.addEffect(fb);
    }
    
    @Override
    public void spawnFragmentParticle(World worldObj, double xCoord, double yCoord, double zCoord, double x2, double y2, double z2, Block block, int id) {
	    FXBoreParticles fb = new FXBoreParticles(worldObj, xCoord, yCoord, zCoord, x2, y2, z2, block, worldObj.rand.nextInt(6), id);
	    
		
	    fb.setAlphaF(0.3F);
		fb.motionX = ((float)worldObj.rand.nextGaussian() * 0.03F);
		fb.motionY = ((float)worldObj.rand.nextGaussian() * 0.03F);
		fb.motionZ = ((float)worldObj.rand.nextGaussian() * 0.03F);
	    FMLClientHandler.instance().getClient().effectRenderer.addEffect(fb);
    }

}