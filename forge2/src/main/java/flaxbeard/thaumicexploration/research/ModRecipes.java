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
		registerResearchItemI("BRAINCURE", new ItemStack(ThaumicExploration.pureZombieBrain), 3, 
				new AspectList().add(Aspect.MAN, 4).add(Aspect.MIND, 6).add(Aspect.HEAL, 2), 
				new ItemStack(ConfigItems.itemResource, 1,5), 
				new ItemStack(Item.potion,1, 8232), new ItemStack(Item.appleGold), 
				new ItemStack(Item.bucketWater));
		registerResearchItemI("BRAINCURE","BRAINCUREALT1", new ItemStack(ThaumicExploration.pureZombieBrain), 3, 
				new AspectList().add(Aspect.MAN, 4).add(Aspect.MIND, 6).add(Aspect.HEAL, 2), 
				new ItemStack(ConfigItems.itemResource, 1,5), 
				new ItemStack(Item.potion,1, 8264), new ItemStack(Item.appleGold), 
				new ItemStack(Item.bucketWater));
		registerResearchItemI("BRAINCURE","BRAINCUREALT3", new ItemStack(ThaumicExploration.pureZombieBrain), 3, 
				new AspectList().add(Aspect.MAN, 4).add(Aspect.MIND, 6).add(Aspect.HEAL, 2), 
				new ItemStack(ConfigItems.itemResource, 1,5), 
				new ItemStack(Item.potion,1, 16424), new ItemStack(Item.appleGold), 
				new ItemStack(Item.bucketWater));
		registerResearchItemI("BRAINCURE","BRAINCUREALT4", new ItemStack(ThaumicExploration.pureZombieBrain), 3, 
				new AspectList().add(Aspect.MAN, 4).add(Aspect.MIND, 6).add(Aspect.HEAL, 2), 
				new ItemStack(ConfigItems.itemResource, 1,5), 
				new ItemStack(Item.potion,1, 16456), new ItemStack(Item.appleGold), 
				new ItemStack(Item.bucketWater));
		
		registerResearchItemI("WANDAMBER", new ItemStack(ThaumicExploration.amberCore), 5, 
				new AspectList().add(Aspect.MAGIC, 14).add(Aspect.AURA, 4).add(Aspect.TRAP, 6), 
				new ItemStack(ConfigBlocks.blockCosmeticOpaque), 
				new ItemStack(ConfigItems.itemResource,1,14), new ItemStack(ConfigItems.itemResource,1,14));
		
		registerResearchItemI("CRUCSOULS", new ItemStack(ThaumicExploration.crucibleSouls), 5, 
				new AspectList().add(Aspect.DEATH, 40).add(Aspect.UNDEAD, 10).add(Aspect.HUNGER, 20).add(Aspect.TRAP, 20).add(Aspect.WEAPON, 5).add(Aspect.SOUL, 30), 
				new ItemStack(ConfigBlocks.blockStoneDevice,1,0), 
				new ItemStack(ConfigBlocks.blockMetalDevice,1,0), new ItemStack(Item.rottenFlesh), 
				new ItemStack(Item.bone), new ItemStack(Item.ghastTear), 
				new ItemStack(Block.slowSand), new ItemStack(ConfigBlocks.blockMetalDevice,1,1));
		
		for (int i = 0; i<16; i++) {
			registerResearchItemI("CHESTSEAL", new ItemStack(ThaumicExploration.chestSeal, 1, i), 7, 
					new AspectList().add(Aspect.ELDRITCH, 6).add(Aspect.EXCHANGE,4).add(Aspect.VOID, 4).add(Aspect.TRAP, 4).add(Aspect.TRAVEL, 12), 
					new ItemStack(ThaumicExploration.blankSeal, 1, i), new ItemStack(Item.enderPearl), 
					new ItemStack(ConfigItems.itemResource,1,14), new ItemStack(ConfigItems.itemResource,1,14),
					new ItemStack(Item.blazePowder), new ItemStack(Block.chest),  new ItemStack(ConfigItems.itemResource,1,3));
		}
		
		registerResearchItemI("METEORBOOTS", new ItemStack(ThaumicExploration.bootsMeteor), 4, 
				new AspectList().add(Aspect.FIRE, 25).add(Aspect.ENERGY, 25), 
				new ItemStack(ConfigItems.itemBootsTraveller), new ItemStack(ConfigBlocks.blockCrystal, 1, 1), 
				new ItemStack(Block.netherrack), new ItemStack(Block.netherrack),
				new ItemStack(Block.netherrack), new ItemStack(ConfigItems.itemFocusFire));
		
		registerResearchItemI("COMETBOOTS", new ItemStack(ThaumicExploration.bootsComet), 4, 
				new AspectList().add(Aspect.WATER, 25).add(Aspect.ICE, 25), 
				new ItemStack(ConfigItems.itemBootsTraveller), new ItemStack(ConfigBlocks.blockCrystal, 1, 2), 
				new ItemStack(Block.blockSnow), new ItemStack(Block.blockSnow),
				new ItemStack(Block.blockSnow), new ItemStack(ConfigItems.itemFocusFrost));
		
		registerResearchItemI("CHESTSEAL", new ItemStack(ThaumicExploration.chestSeal, 1, 32767), 7, 
				new AspectList().add(Aspect.ELDRITCH, 6).add(Aspect.EXCHANGE,4).add(Aspect.VOID, 4).add(Aspect.TRAP, 4).add(Aspect.TRAVEL, 12), 
				new ItemStack(ThaumicExploration.blankSeal, 1, 32767), new ItemStack(Item.enderPearl), 
				new ItemStack(ConfigItems.itemResource,1,14), new ItemStack(ConfigItems.itemResource,1,14),
				new ItemStack(Item.blazePowder), new ItemStack(Block.chest),  new ItemStack(ConfigItems.itemResource,1,3));

		registerResearchItemI("THINKTANK", new ItemStack(ThaumicExploration.thinkTankJar), 6, 
				new AspectList().add(Aspect.MIND, 40).add(Aspect.SENSES, 20).add(Aspect.UNDEAD, 30), 
				new ItemStack(ConfigBlocks.blockJar),new ItemStack(ThaumicExploration.pureZombieBrain), new ItemStack(ConfigItems.itemInkwell,1,0), 
				new ItemStack(ThaumicExploration.pureZombieBrain), new ItemStack(ConfigItems.itemThaumonomicon), 
				new ItemStack(ThaumicExploration.pureZombieBrain), new ItemStack(ConfigItems.itemThaumometer));
		
		registerResearchItemI("URN", new ItemStack(ThaumicExploration.everfullUrn), 2, 
				new AspectList().add(Aspect.WATER, 20).add(Aspect.VOID, 8).add(Aspect.MAGIC, 4), 
				new ItemStack(Item.flowerPot),new ItemStack(Item.bucketWater), 
				new ItemStack(Item.brick),new ItemStack(Item.bucketWater), 
				new ItemStack(Item.brick),new ItemStack(Item.bucketWater), 
				new ItemStack(Item.brick) );
		
		for (int i = 0; i<16; i++) {
			registerResearchItemI("JARSEAL", new ItemStack(ThaumicExploration.jarSeal, 1, i), 7, 
					new AspectList().add(Aspect.ELDRITCH, 6).add(Aspect.EXCHANGE,4).add(Aspect.TRAP, 4).add(Aspect.TRAVEL, 12).add(Aspect.MAGIC, 2), 
					new ItemStack(ThaumicExploration.blankSeal, 1, i), new ItemStack(Item.enderPearl), 
					new ItemStack(ConfigItems.itemResource,1,14), new ItemStack(ConfigItems.itemResource,1,14),
					new ItemStack(Item.blazePowder), new ItemStack(ConfigBlocks.blockJar),  new ItemStack(ConfigItems.itemResource,1,3));
		}
		registerResearchItemI("JARSEAL", new ItemStack(ThaumicExploration.jarSeal, 1, 32767), 7, 
				new AspectList().add(Aspect.ELDRITCH, 6).add(Aspect.EXCHANGE,4).add(Aspect.TRAP, 4).add(Aspect.TRAVEL, 12).add(Aspect.MAGIC, 2), 
				new ItemStack(ThaumicExploration.blankSeal, 1, 32767), new ItemStack(Item.enderPearl), 
				new ItemStack(ConfigItems.itemResource,1,14), new ItemStack(ConfigItems.itemResource,1,14),
				new ItemStack(Item.blazePowder), new ItemStack(ConfigBlocks.blockJar),  new ItemStack(ConfigItems.itemResource,1,3));
		
		//Change Advanced golem recipe to require a pure brain
        InfusionRecipe recipe = (InfusionRecipe) ConfigResearch.recipes.get("AdvancedGolem");
        ConfigResearch.recipes.remove(ConfigResearch.recipes.get("AdvancedGolem"));
        ItemStack[] components = {new ItemStack(Item.redstone), new ItemStack(Item.glowstone), new ItemStack(Item.gunpowder), new ItemStack(ConfigBlocks.blockJar, 1, 0), new ItemStack(ThaumicExploration.pureZombieBrain) };
        recipe.components = components;
        InfusionRecipe recipe2 = ThaumcraftApi.addInfusionCraftingRecipe("ADVANCEDGOLEM", recipe.recipeOutput, recipe.instability, recipe.aspects, recipe.recipeInput, recipe.components);
		ConfigResearch.recipes.put("AdvancedGolem", recipe2);
	}

	private static void initArcaneRecipes() {
		
        registerResearchItem("DREAMCATCHER", "DREAMCATCHER", new ItemStack(ThaumicExploration.charmNoTaint), new AspectList().add(Aspect.ORDER, 15).add(Aspect.ENTROPY, 15),
                "GPG", "PSP", "FPF",
                'G', new ItemStack(ConfigItems.itemResource,1, 11),
                'P', new ItemStack(ConfigBlocks.blockWoodenDevice,1,6),
                'S', new ItemStack(ConfigItems.itemResource,1, 12),
                'F', new ItemStack(Item.feather));
	}

	private static void initCraftingRecipes() {
		for (int i=0;i<16;i++) {
			GameRegistry.addRecipe(new ItemStack(ThaumicExploration.blankSeal, 1, i), " X ", "XZX", " X ", 'X', new ItemStack(ConfigItems.itemResource,1,4), 'Z', new ItemStack(Item.dyePowder,1,i));
		}
		
		registerCraftingRecipe("BLANKSEAL", new ItemStack(ThaumicExploration.blankSeal, 1, 32767), " X ", "XZX", " X ", 'X', new ItemStack(ConfigItems.itemResource,1,4), 'Z', new ItemStack(Item.dyePowder,1,32767));
		
	}

	private static void initCrucibleRecipes() {
		registerCrucibleRecipe("FLESHCURE","FLESHCURE", new ItemStack(Item.leather,2), new ItemStack(Item.rottenFlesh), new AspectList().add(Aspect.FLESH, 2).add(Aspect.CLOTH, 1));
		

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
