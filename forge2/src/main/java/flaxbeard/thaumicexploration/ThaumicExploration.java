package flaxbeard.thaumicexploration;



import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import flaxbeard.thaumicexploration.block.BlockBoundChest;
import flaxbeard.thaumicexploration.common.CommonProxy;
import flaxbeard.thaumicexploration.event.TXEventHandler;
import flaxbeard.thaumicexploration.item.ItemBrain;
import flaxbeard.thaumicexploration.packet.TXPacketHandler;
import flaxbeard.thaumicexploration.research.ModRecipes;
import flaxbeard.thaumicexploration.research.ModResearch;
import flaxbeard.thaumicexploration.tile.TileEntityBoundChest;


@Mod(modid = "ThaumicExploration", name = "Thaumic Exploration", version = "0.0.1", dependencies="required-after:Thaumcraft")
@NetworkMod(clientSideRequired=true, serverSideRequired=false, channels={"tExploration"}, packetHandler = TXPacketHandler.class)
public class ThaumicExploration {

	// The instance of your mod that Forge uses.
	public static Item pureZombieBrain;
	public static Block boundChest;
	
	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide = "flaxbeard.thaumicexploration.client.ClientProxy", serverSide = "flaxbeard.thaumicexploration.common.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		// Stub Method
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		
		MinecraftForge.EVENT_BUS.register(new TXEventHandler());
		GameRegistry.registerTileEntity(TileEntityBoundChest.class, "tileEntityBoundCHest");
		boundChest = new BlockBoundChest(202, 0).setUnlocalizedName("boundChest").setCreativeTab(CreativeTabs.tabBlock);
		GameRegistry.registerBlock(boundChest, "boundChest");
		pureZombieBrain = (new ItemBrain(7006)).setUnlocalizedName("thaumicexploration:pureZombieBrain").setCreativeTab(CreativeTabs.tabBlock).setTextureName("thaumicexploration:pureZombieBrain");
		proxy.registerRenderers();
	}
	
	 @EventHandler
     public void postInit(FMLPostInitializationEvent event) {
			ModRecipes.initRecipes();
			ModResearch.initResearch();
     }


	public void addRecipes()
	{
		
	}
	
	public void registerEntity(Class<? extends Entity> entityClass, String entityName, int bkEggColor, int fgEggColor) {
		int id = EntityRegistry.findGlobalUniqueEntityId();

		EntityRegistry.registerGlobalEntityID(entityClass, entityName, id);
		EntityList.entityEggs.put(Integer.valueOf(id), new EntityEggInfo(id, bkEggColor, fgEggColor));
	}
	
	public void addSpawn(Class<? extends EntityLiving> entityClass, int spawnProb, int min, int max, BiomeGenBase[] biomes) {
		if (spawnProb > 0) {
			EntityRegistry.addSpawn(entityClass, spawnProb, min, max, EnumCreatureType.monster, biomes);
		}
	}

	private void addAchievementName(String ach, String name)
	{
	        LanguageRegistry.instance().addStringLocalization("achievement." + ach, "en_US", name);
	}

	private void addAchievementDesc(String ach, String desc)
	{
	        LanguageRegistry.instance().addStringLocalization("achievement." + ach + ".desc", "en_US", desc);
	}


	
	@ForgeSubscribe
	public void onSound(SoundLoadEvent event) {
	// You add them the same way as you add blocks.
	//event.manager.addSound("steamcraft:wobble.ogg");
	}
}