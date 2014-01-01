package flaxbeard.thaumicexploration;



import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import thaumcraft.api.wands.WandRod;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import flaxbeard.thaumicexploration.block.BlockBoundChest;
import flaxbeard.thaumicexploration.block.BlockBoundJar;
import flaxbeard.thaumicexploration.block.BlockThinkTank;
import flaxbeard.thaumicexploration.common.CommonProxy;
import flaxbeard.thaumicexploration.event.TXEventHandler;
import flaxbeard.thaumicexploration.gui.TXGuiHandler;
import flaxbeard.thaumicexploration.item.ItemBlankSeal;
import flaxbeard.thaumicexploration.item.ItemBrain;
import flaxbeard.thaumicexploration.item.ItemChestSeal;
import flaxbeard.thaumicexploration.item.ItemChestSealLinked;
import flaxbeard.thaumicexploration.item.ItemCrystalArmor;
import flaxbeard.thaumicexploration.item.focus.ItemFocusNecromancy;
import flaxbeard.thaumicexploration.packet.TXPacketHandler;
import flaxbeard.thaumicexploration.research.ModRecipes;
import flaxbeard.thaumicexploration.research.ModResearch;
import flaxbeard.thaumicexploration.tile.TileCrystalAdvanced;
import flaxbeard.thaumicexploration.tile.TileEntityBoundChest;
import flaxbeard.thaumicexploration.tile.TileEntityBoundJar;
import flaxbeard.thaumicexploration.tile.TileEntityThinkTank;
import flaxbeard.thaumicexploration.wand.WandRodAmberOnUpdate;
import flaxbeard.thaumicexploration.wand.WandRodTransmutationOnUpdate;


@Mod(modid = "ThaumicExploration", name = "Thaumic Exploration", version = "0.1.1", dependencies="required-after:Thaumcraft")
@NetworkMod(clientSideRequired=true, serverSideRequired=false, channels={"tExploration"}, packetHandler = TXPacketHandler.class)
public class ThaumicExploration {
	
    @Instance("ThaumicExploration")
    public static ThaumicExploration instance;


	public static Item pureZombieBrain;
	public static int pureZombieBrainID;
	public static Item blankSeal;
	public static int blankSealID;
	public static Item chestSeal;
	public static int chestSealID;
	public static Item chestSealLinked;
	public static int chestSealLinkedID;
	public static Item jarSeal;
	public static int jarSealID;
	public static Item jarSealLinked;
	public static int jarSealLinkedID;
	public static Item transmutationCore;
	public static int transmutationCoreID;
	public static Item amberCore;
	public static int amberCoreID;
	
	public static EnumArmorMaterial armorMaterialCrystal;
	public static Item helmetCrystal;
	public static int helmetCrystalID;
	public static Item focusNecromancy;
	public static int focusNecromancyID;
	public static Item legsCrystal;
	public static int legsCrystalID;
	public static Item shoesCrystal;
	public static int shoesCrystalID;
	
	public static Block boundChest;
	public static int boundChestID;
	public static Block boundJar;
	public static int boundJarID;
	public static Block thinkTankJar;
	public static WandRod WAND_ROD_CRYSTAL;
	public static WandRod WAND_ROD_AMBER;
	
	public static CreativeTabs tab;
	
	public static boolean allowBoundInventories;
	
	
	@SidedProxy(clientSide = "flaxbeard.thaumicexploration.client.ClientProxy", serverSide = "flaxbeard.thaumicexploration.common.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		
		//Item IDs
		pureZombieBrainID = config.getItem("Cured Zombie Brain", 7000).getInt();
		blankSealID = config.getItem("Blank Tallow Seal", 7001).getInt();
		chestSealID = config.getItem("Chest Binding Seal", 7002).getInt();
		chestSealLinkedID = config.getItem("Linked Chest Binding Seal", 7003).getInt();
		jarSealID = config.getItem("Jar Binding Seal", 7006).getInt();
		jarSealLinkedID = config.getItem("Linked Jar Binding Seal", 7007).getInt();
		transmutationCoreID = config.getItem("Transmutation Filter Wand Core", 7004).getInt();
		amberCoreID = config.getItem("Amber Wand Core", 7005).getInt();
		
		//Armor Item IDs
		helmetCrystalID = config.getItem("Crystal Helmet", 7008).getInt();
		focusNecromancyID = config.getItem("Focus of Necromancy", 7009).getInt();
		legsCrystalID = config.getItem("Crystal Leggings", 7010).getInt();
		shoesCrystalID = config.getItem("Crystal Boots", 7011).getInt();
		
		//Block IDs
		boundChestID = config.getBlock("Bound Chest", 700).getInt();
		boundJarID = config.getBlock("Bound Jar", 701).getInt();
		
		allowBoundInventories = config.get("Miscellaneous", "Allow bound inventories", true).getBoolean(true);
		config.save();
	}
	
	

	@EventHandler
	public void load(FMLInitializationEvent event) {
		NetworkRegistry.instance().registerGuiHandler(instance, new TXGuiHandler());
		
		//Creative Tab
		tab = new TXTab(CreativeTabs.getNextID(), "thaumicExploration");
		
		//EventHandler
		MinecraftForge.EVENT_BUS.register(new TXEventHandler());

		//Tiles
		GameRegistry.registerTileEntity(TileEntityBoundChest.class, "tileEntityBoundChest");
		GameRegistry.registerTileEntity(TileEntityBoundJar.class, "tileEntityBoundJar");
		GameRegistry.registerTileEntity(TileCrystalAdvanced.class, "tileEntityCrystalAdvanced");
		GameRegistry.registerTileEntity(TileEntityThinkTank.class, "tileEntityThinkTank");
		
		//Blocks
		thinkTankJar = new BlockThinkTank(205, false).setUnlocalizedName("thaumicexploration:thinkTankJar").setCreativeTab(tab).setTextureName("thaumicExploration:blankTexture");
	
		boundChest = new BlockBoundChest(boundChestID, 0).setHardness(2.5F).setStepSound(new StepSound("wood", 1.0F, 1.0F)).setUnlocalizedName("boundChest");
		boundJar = new BlockBoundJar(boundJarID).setUnlocalizedName("boundJar");
		
		GameRegistry.registerBlock(boundChest, "boundChest");
		GameRegistry.registerBlock(boundJar, "boundJar");
		GameRegistry.registerBlock(thinkTankJar, "thinkTankJar");
		
		//Items
		transmutationCore = (new Item(transmutationCoreID)).setUnlocalizedName("thaumicexploration:transmutationCore").setCreativeTab(tab).setTextureName("thaumicexploration:rodTransmutation");
		amberCore = (new Item(amberCoreID)).setUnlocalizedName("thaumicexploration:amberCore").setCreativeTab(tab).setTextureName("thaumicexploration:rodAmber");
		pureZombieBrain = (new ItemBrain(pureZombieBrainID)).setUnlocalizedName("thaumicexploration:pureZombieBrain").setCreativeTab(tab).setTextureName("thaumicexploration:pureZombieBrain");
		blankSeal = (new ItemBlankSeal(blankSealID).setCreativeTab(tab).setTextureName("thaumicexploration:sealBlank"));
		chestSeal = (new ItemChestSeal(chestSealID).setCreativeTab(tab).setTextureName("thaumicexploration:sealChest").setUnlocalizedName("thaumicexploration:chestSeal"));
		chestSealLinked = (new ItemChestSealLinked(chestSealLinkedID).setTextureName("thaumicexploration:sealChest").setUnlocalizedName("thaumicexploration:chestSeal"));
		jarSeal = (new ItemChestSeal(jarSealID).setCreativeTab(tab).setTextureName("thaumicexploration:sealJar").setUnlocalizedName("thaumicexploration:jarSeal"));
		jarSealLinked = (new ItemChestSealLinked(jarSealLinkedID).setTextureName("thaumicexploration:sealJar").setUnlocalizedName("thaumicexploration:jarSeal"));
		
		armorMaterialCrystal = EnumHelper.addArmorMaterial("CRYSTAL", 25, new int[] { 2, 6, 5, 2 }, 25);
		//helmetCrystal = (new ItemCrystalArmor(helmetCrystalID, armorMaterialCrystal, 2, 0)).setUnlocalizedName("thaumicexploration:helmetCrystal").setCreativeTab(CreativeTabs.tabBlock).setTextureName("thaumicexploration:focusNecromancy");
		focusNecromancy = (new ItemFocusNecromancy(focusNecromancyID)).setUnlocalizedName("thaumicexploration:necromancy").setCreativeTab(tab).setTextureName("thaumicexploration:focusNecromancy");
		
		//Wands
		WAND_ROD_AMBER = new WandRod("amber",75,new ItemStack(ThaumicExploration.amberCore),1,new WandRodAmberOnUpdate(), new ResourceLocation("thaumicexploration:textures/models/rodAmber.png"));
		WAND_ROD_CRYSTAL = new WandRod("transmutation",25,new ItemStack(ThaumicExploration.transmutationCore),1,new WandRodTransmutationOnUpdate());
		//WandRod.rods.put("transmutation1", WAND_ROD_CRYSTAL1);
		

		
		proxy.registerRenderers();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		//Researches, Thaumcraft Recipes
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
	
	private class TXTab extends CreativeTabs {

		public TXTab(int par1, String par2Str) {
			super(par1, par2Str);
			// TODO Auto-generated constructor stub
		}
		
        public ItemStack getIconItemStack() {
            return new ItemStack(ThaumicExploration.thinkTankJar, 1, 0);
        }	
		
	}
}