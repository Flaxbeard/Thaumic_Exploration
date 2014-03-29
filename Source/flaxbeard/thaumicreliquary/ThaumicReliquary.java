package flaxbeard.thaumicreliquary;



import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import flaxbeard.thaumicreliquary.research.TRRecipes;
import flaxbeard.thaumicreliquary.research.TRResearch;


@Mod(modid = "ThaumicReliquary", name = "Thaumic Reliquary", version = "1.1.0", dependencies="required-after:Thaumcraft;after:xreliquary")
@NetworkMod(clientSideRequired=true, serverSideRequired=false, channels={"tReliquary"}, packetHandler = TRPacketHandler.class)
public class ThaumicReliquary {
	
    @Instance("ThaumicReliquary")
    public static ThaumicReliquary instance;
    
    public static boolean optional;
    public static boolean disable;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		optional = config.get("Options", "Allow users to enable or disable Thaumic Reliquary at will (Servers) OR Disable Thaumic Reliquary (Singleplayer)", false).getBoolean(true);
		disable = config.get("Options", "Disable Thaumic Reliquary on servers that have it as an option.", false).getBoolean(true);
		config.save();
	}
	
	

	@EventHandler
	public void load(FMLInitializationEvent event) {
	    MinecraftForge.EVENT_BUS.register(new TREventHandler());
	}
	
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		TRRecipes.initRecipes();
		TRResearch.initResearch();
	}
}