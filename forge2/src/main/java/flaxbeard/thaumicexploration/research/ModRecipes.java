package flaxbeard.thaumicexploration.research;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.config.ConfigResearch;
import cpw.mods.fml.common.registry.GameRegistry;
import flaxbeard.thaumicexploration.ThaumicExploration;


public final class ModRecipes {
	
    static ItemStack empty = new ItemStack(ConfigBlocks.blockHole, 1, 15);

    public static void initRecipes() {
        initCraftingRecipes();
        initArcaneRecipes();
        initInfusionRecipes();
        initCrucibleRecipes();
        initConstructRecipes();
    }
    
    private static void initConstructRecipes() {
    	
    	 registerResearchItemC("BUILDTHINKTANK", Arrays.asList(new Object[] { 
    			 new AspectList(), 
    			 Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(3), 
    			 Arrays.asList(new ItemStack[] { 
    					 empty, empty, empty, 
    					 empty, empty, empty, 
    					 empty, empty, empty, 
    					 empty, empty, empty, 
    					 empty, new ItemStack(ThaumicExploration.thinkTankJar), empty, 
    					 empty, empty, empty, 
    					 empty, empty, empty, 
    					 empty, new ItemStack(Block.bookShelf), empty, 
    					 empty, empty, empty}) }));
    }


	private static void initInfusionRecipes() {
		registerResearchItemI("CHESTSEAL", new ItemStack(ThaumicExploration.chestSeal, 1, 32767), 3, 
				new AspectList().add(Aspect.AIR, 15).add(Aspect.MOTION, 20).add(Aspect.TRAVEL, 10), 
				new ItemStack(ThaumicExploration.blankSeal, 1, 32767), new ItemStack(Item.appleGold), 
				new ItemStack(Item.potion,1, 16392), new ItemStack(Item.appleGold), 
				new ItemStack(Item.speckledMelon));
		registerResearchItemI("THINKTANK", new ItemStack(ThaumicExploration.thinkTankJar), 3, 
				new AspectList().add(Aspect.AIR, 15).add(Aspect.MOTION, 20).add(Aspect.TRAVEL, 10), 
				new ItemStack(ThaumicExploration.thinkTankJar),new ItemStack(ThaumicExploration.pureZombieBrain), new ItemStack(ConfigItems.itemInkwell,1,0), 
				new ItemStack(ThaumicExploration.pureZombieBrain), new ItemStack(ConfigItems.itemThaumonomicon), 
				new ItemStack(ThaumicExploration.pureZombieBrain), new ItemStack(ConfigItems.itemThaumometer));
		registerResearchItemI("JARSEAL", new ItemStack(ThaumicExploration.jarSeal, 1, 32767), 3, 
				new AspectList().add(Aspect.AIR, 15).add(Aspect.MOTION, 20).add(Aspect.TRAVEL, 10), 
				new ItemStack(ThaumicExploration.blankSeal, 1, 32767), new ItemStack(Item.appleGold), 
				new ItemStack(Item.speckledMelon), new ItemStack(Item.appleGold), 
				new ItemStack(Item.speckledMelon));
		
		//Change Advanced golem recipe to require a pure brain
        InfusionRecipe recipe = (InfusionRecipe) ConfigResearch.recipes.get("AdvancedGolem");
        ConfigResearch.recipes.remove(ConfigResearch.recipes.get("AdvancedGolem"));
        ItemStack[] components = {new ItemStack(Item.redstone), new ItemStack(Item.glowstone), new ItemStack(Item.gunpowder), new ItemStack(ConfigBlocks.blockJar, 1, 0), new ItemStack(ThaumicExploration.pureZombieBrain) };
        recipe.components = components;
        InfusionRecipe recipe2 = ThaumcraftApi.addInfusionCraftingRecipe("ADVANCEDGOLEM", recipe.recipeOutput, recipe.instability, recipe.aspects, recipe.recipeInput, recipe.components);
		ConfigResearch.recipes.put("AdvancedGolem", recipe2);
	}

	private static void initArcaneRecipes() {
		// TODO Auto-generated method stub
		
	}

	private static void initCraftingRecipes() {
		for (int i=0;i<15;i++) {
			GameRegistry.addRecipe(new ItemStack(ThaumicExploration.blankSeal, 1, i), " X ", "XZX", " X ", 'X', new ItemStack(ConfigItems.itemResource,1,4), 'Z', new ItemStack(Item.dyePowder,1,i));
		}
		
		registerCraftingRecipe("BLANKSEAL", new ItemStack(ThaumicExploration.blankSeal, 1, 32767), " X ", "XZX", " X ", 'X', new ItemStack(ConfigItems.itemResource,1,4), 'Z', new ItemStack(Item.dyePowder,1,32767));
		
	}

	private static void initCrucibleRecipes() {
		registerCrucibleRecipe("FLESHCURE","FLESHCURE", new ItemStack(Item.leather), new ItemStack(Item.rottenFlesh), new AspectList().add(Aspect.MAGIC, 2).add(Aspect.FLESH, 2));
//        registerResearchItem(LibResearch.KEY_GASEOUS_LIGHT, new ItemStack(ModItems.gaseousLight), new ItemStack(ConfigItems.itemEssence, 1, 0), new AspectList().add(Aspect.LIGHT, 16).add(Aspect.AIR, 10).add(Aspect.MOTION, 8));
//        registerResearchItem(LibResearch.KEY_GASEOUS_SHADOW, new ItemStack(ModItems.gaseousShadow), new ItemStack(ConfigItems.itemEssence, 1, 0), new AspectList().add(Aspect.DARKNESS, 16).add(Aspect.AIR, 10).add(Aspect.MOTION, 8));
//        registerResearchItem(LibResearch.KEY_SPELL_CLOTH, new ItemStack(ModItems.spellCloth), new ItemStack(ConfigItems.itemResource, 0, 7), new AspectList().add(Aspect.MAGIC, 10).add(Aspect.ENTROPY, 6).add(Aspect.EXCHANGE, 4));
//        registerResearchItem(LibResearch.KEY_BRIGHT_NITOR, new ItemStack(ModItems.brightNitor), new ItemStack(ConfigItems.itemResource, 1, 1), new AspectList().add(Aspect.ENERGY, 25).add(Aspect.LIGHT, 25).add(Aspect.AIR, 10).add(Aspect.FIRE, 10));
//        registerResearchItem(LibResearch.KEY_MAGNETS, new ItemStack(ModItems.soulMould), new ItemStack(Item.enderPearl), new AspectList().add(Aspect.BEAST, 4).add(Aspect.MIND, 8).add(Aspect.SENSES, 8));
	}
	
	private static void registerResearchItem(String name, String research, ItemStack output, AspectList aspects, Object... stuff) {
	        ShapedArcaneRecipe recipe = ThaumcraftApi.addArcaneCraftingRecipe(research, output, aspects, stuff);
	        ConfigResearch.recipes.put(name, recipe);
	}
	
	private static void registerCraftingRecipe(String name, ItemStack output, Object... stuff) {
	        GameRegistry.addRecipe(output, stuff);
	        List<IRecipe> recipeList = CraftingManager.getInstance().getRecipeList();
	        if(name != null && name.length() != 0)
	                ConfigResearch.recipes.put(name, recipeList.get(recipeList.size() - 1));
	}
	

	
	private static void registerCrucibleRecipe(String name, String research, ItemStack output, ItemStack input, AspectList aspects) {
	        CrucibleRecipe recipe = ThaumcraftApi.addCrucibleRecipe(name, output, input, aspects);
	        ConfigResearch.recipes.put(research, recipe);
	}
	
	private static void registerResearchItemI(String name, Object output, int instability, AspectList aspects, ItemStack input, ItemStack... stuff) {
         InfusionRecipe recipe = ThaumcraftApi.addInfusionCraftingRecipe(name, output, instability, aspects, input, stuff);
         ConfigResearch.recipes.put(name, recipe);
	}
	
	private static void registerResearchItemI(String name, String research, Object output, int instability, AspectList aspects, ItemStack input, ItemStack... stuff) {
        InfusionRecipe recipe = ThaumcraftApi.addInfusionCraftingRecipe(name, output, instability, aspects, input, stuff);
        ConfigResearch.recipes.put(research, recipe);
	}
	

	private static void registerResearchItemC(String string, List<Object> asList) {
		ConfigResearch.recipes.put(string, asList);
		
	}
}
