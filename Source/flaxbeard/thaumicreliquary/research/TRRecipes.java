package flaxbeard.thaumicreliquary.research;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.InfusionEnchantmentRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.crafting.ShapelessArcaneRecipe;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.config.ConfigResearch;
import xreliquary.blocks.XRBlocks;
import xreliquary.items.XRItems;
import xreliquary.lib.Reference;
import cpw.mods.fml.common.registry.GameRegistry;
import flaxbeard.thaumicexploration.ThaumicExploration;
import flaxbeard.thaumicreliquary.ConditionalRecipe;


public final class TRRecipes {
	public static Field container;
	public static Field crafters;
	public static ArrayList<IInventory> craftingRecipes = new ArrayList<IInventory>();
	public static Item[] reliquaryItems = {XRItems.alkahestryTome,XRItems.condensedPotion,XRItems.destructionCatalyst,XRItems.distortionCloak,XRItems.emperorChalice,XRItems.emptyVoidTear,XRItems.fortuneCoin,XRItems.glowingBread,XRItems.glowingWater,XRItems.holyHandGrenade,XRItems.iceRod,XRItems.magicbane,XRItems.mercyCross,XRItems.midasTouchstone,XRItems.salamanderEye,XRItems.sojournerStaff,XRItems.voidTear,XRItems.witherlessRose,XRItems.wraithEye};
	public static int[] reliquaryBlocks = {XRBlocks.altarActive.blockID,XRBlocks.altarIdle.blockID,XRBlocks.lilypad.blockID,XRBlocks.wraithNode.blockID};
    public static void initRecipes() {
    	try {
			container = InventoryCrafting.class.getDeclaredField("eventHandler");
	        container.setAccessible(true);
	        crafters = Container.class.getDeclaredField("crafters");
	        crafters.setAccessible(true);
    	}
    	catch (Exception e) {
    		
    	}

    	removeReliquaryRecipes();
        initCraftingRecipes();
        initArcaneRecipes();
        initInfusionRecipes();
        initCrucibleRecipes();
    }
    
    private static void removeReliquaryRecipes() {
    	ArrayList recipes = (ArrayList) CraftingManager.getInstance().getRecipeList();
    	ArrayList<IRecipe> recipesToRemove = new ArrayList<IRecipe>();
    	for (Object rObj : recipes) {
    		IRecipe recipe = (IRecipe) rObj;
    		ItemStack result = null;
    		if (recipe instanceof ShapedRecipes)
            {
                    ShapedRecipes recipe2 = (ShapedRecipes)recipe;
                    result = recipe2.getRecipeOutput();
                    if (result.getItem() == XRItems.voidSatchel) {
                        List<IRecipe> recipeList = CraftingManager.getInstance().getRecipeList();
                        ConfigResearch.recipes.put("VOIDSATCHEL", rObj);
                    }
            }
            if (recipe instanceof ShapelessRecipes)
            {
                    ShapelessRecipes recipe2 = (ShapelessRecipes)recipe;
                    result = recipe2.getRecipeOutput();
                    if (result.getItem() == XRItems.voidSatchel) {
                        List<IRecipe> recipeList = CraftingManager.getInstance().getRecipeList();
                        ConfigResearch.recipes.put("VOIDSATCHELUPGRADE", rObj);
                    }
            }
            if (result != null) {
            	List<Item> itemsToRemove = Arrays.asList(reliquaryItems);
            	List<int[]> blocksToRemove = Arrays.asList(reliquaryBlocks);
            	if (itemsToRemove.contains(result.getItem())) {
            		recipesToRemove.add(recipe);
            	}
            	else if (result.getItem() instanceof ItemBlock) {
            		ItemBlock resultBlock = (ItemBlock) result.getItem();
            		for (int i = 0; i < reliquaryBlocks.length; i++) {
            			if (reliquaryBlocks[i] == resultBlock.itemID) {
            				recipesToRemove.add(recipe);
            			}
            		}
            	}
            }
    	}
    	for (IRecipe recipe : recipesToRemove) {
    		CraftingManager.getInstance().getRecipeList().remove(recipe);
    		GameRegistry.addRecipe(new ConditionalRecipe(recipe));
    	}
    }
    
	private static void initInfusionRecipes() {
		registerResearchItemI("MBANE", new ItemStack(XRItems.magicbane), 7, 
				new AspectList().add(Aspect.MAGIC,8).add(Aspect.LIGHT, 8).add(Aspect.ELDRITCH, 10), 
				new ItemStack(Item.swordGold,1,32767), 
				new ItemStack(Item.eyeOfEnder),new ItemStack(ConfigItems.itemResource,1,15), new ItemStack(Item.eyeOfEnder),new ItemStack(Item.eyeOfEnder));
		
		registerResearchItemI("CLOAK", new ItemStack(XRItems.distortionCloak), 5, 
				new AspectList().add(Aspect.DARKNESS,4).add(Aspect.LIGHT, 4).add(Aspect.ELDRITCH, 16), 
				new ItemStack(ConfigItems.itemResource,1,7), 
				new ItemStack(Item.eyeOfEnder),
				new ItemStack(Item.eyeOfEnder),
				new ItemStack(Item.eyeOfEnder),
				new ItemStack(Item.eyeOfEnder),
				new ItemStack(Item.eyeOfEnder),
				new ItemStack(Item.eyeOfEnder));
		
		registerResearchItemI("TOME", new ItemStack(XRItems.alkahestryTome), 8, 
				new AspectList().add(Aspect.EXCHANGE,16).add(Aspect.MAGIC, 32), 
				new ItemStack(Item.book), 
				new ItemStack(Item.blazeRod),
				new ItemStack(Item.ghastTear),
				new ItemStack(Item.skull,1,1),
				new ItemStack(Item.bucketLava),
				new ItemStack(Item.netherStalkSeeds),
				new ItemStack(Block.glowStone),
				new ItemStack(Item.magmaCream),
				new ItemStack(Item.ingotGold));
		
		registerResearchItemI("ROSE", new ItemStack(XRItems.witherlessRose), 8, 
				new AspectList().add(Aspect.UNDEAD,16).add(Aspect.ARMOR, 16).add(Aspect.LIGHT,64), 
				new ItemStack(Block.plantRed), 
				new ItemStack(XRItems.glowingWater),
				new ItemStack(Item.netherStar),
				new ItemStack(XRItems.glowingWater),
				new ItemStack(Item.netherStar),
				new ItemStack(XRItems.glowingWater),
				new ItemStack(Item.netherStar),
				new ItemStack(XRItems.glowingWater),
				new ItemStack(Item.netherStar));
		
		registerResearchItemI("SALEYE", new ItemStack(XRItems.salamanderEye), 5, 
				new AspectList().add(Aspect.FIRE,8).add(Aspect.ARMOR, 8).add(Aspect.MAGIC, 4), 
				new ItemStack(Item.eyeOfEnder), 
				new ItemStack(Item.ghastTear),
				new ItemStack(Item.blazeRod),
				new ItemStack(Item.magmaCream),
				new ItemStack(Item.ghastTear),
				new ItemStack(Item.blazeRod),
				new ItemStack(Item.magmaCream));
		
		registerResearchItemI("WRAITH", new ItemStack(XRItems.wraithEye), 8, 
				new AspectList().add(Aspect.TRAVEL,32).add(Aspect.ELDRITCH, 16).add(Aspect.MOTION, 8).add(Aspect.MAGIC, 8), 
				new ItemStack(XRItems.salamanderEye), 
				new ItemStack(Block.blockEmerald),
				new ItemStack(XRItems.emptyVoidTear),
				new ItemStack(XRItems.emptyVoidTear),
				new ItemStack(XRItems.emptyVoidTear),
				new ItemStack(Block.blockEmerald),
				new ItemStack(XRItems.emptyVoidTear),
				new ItemStack(XRItems.emptyVoidTear),
				new ItemStack(XRItems.emptyVoidTear));
		
		registerResearchItemI("DESTCAT", new ItemStack(XRItems.destructionCatalyst), 4, 
				new AspectList().add(Aspect.ENERGY, 16).add(Aspect.FIRE, 16).add(Aspect.MINE, 8), 
				new ItemStack(Item.flint), 
				new ItemStack(Item.blazeRod),new ItemStack(ConfigItems.itemResource,1,0), new ItemStack(Item.blazeRod),new ItemStack(XRItems.midasTouchstone,1,-1));
		
		registerResearchItemI("COINFORTUNE", new ItemStack(XRItems.fortuneCoin), 3, 
				new AspectList().add(Aspect.GREED, 8).add(Aspect.HUNGER, 8).add(Aspect.ENTROPY, 4), 
				new ItemStack(Item.ingotGold), 
				new ItemStack(XRItems.glowingWater),new ItemStack(Item.eyeOfEnder), new ItemStack(Item.eyeOfEnder),new ItemStack(Item.eyeOfEnder),new ItemStack(Item.eyeOfEnder));
		
		registerResearchItemI("LILY", new ItemStack(XRBlocks.lilypad), 3, 
				new AspectList().add(Aspect.PLANT, 8).add(Aspect.MAGIC, 8).add(Aspect.ENTROPY, 4), 
				new ItemStack(Block.waterlily), 
				XRItems.potion(Reference.FERTILIZER_META),XRItems.potion(Reference.FERTILIZER_META),XRItems.potion(Reference.FERTILIZER_META),XRItems.potion(Reference.FERTILIZER_META),XRItems.potion(Reference.FERTILIZER_META),XRItems.potion(Reference.FERTILIZER_META),XRItems.potion(Reference.FERTILIZER_META),XRItems.potion(Reference.FERTILIZER_META));
		
	}


	private static void initArcaneRecipes() {
        registerResearchItem("WRAITHNODE", "WRAITH", new ItemStack(XRBlocks.wraithNode), new AspectList().add(Aspect.ORDER, 50).add(Aspect.ENTROPY, 50),
        		"vv", "vv",
        		'v', XRItems.emptyVoidTear);
        registerResearchItem("ALTAR", "ALTAR", new ItemStack(XRBlocks.altarIdle), new AspectList().add(Aspect.ORDER, 100).add(Aspect.ENTROPY, 100),
        		"olo", "lel", "olo",
        		'o', Block.obsidian,
        		'l', Block.redstoneLampIdle,
        		'e', Item.emerald);
        registerResearchItem("CROSS", "CROSS", new ItemStack(XRItems.mercyCross), new AspectList().add(Aspect.ORDER, 50),
        		"wgw", "glg", "wgw",
        		'w', XRItems.glowingWater,
        		'g', Item.ingotGold,
        		'l', Item.leather);
        registerResearchItem("MIDAS", "MIDAS", new ItemStack(XRItems.midasTouchstone), new AspectList().add(Aspect.ORDER, 20).add(Aspect.ENTROPY, 20),
        		"bbb", "rtr", "ggg",
        		'b', Block.blockGold,
        		'r', Item.blazeRod,
        		'g', Item.ingotGold,
        		't', Item.ghastTear);
        registerResearchItem("CHALICE", "CHALICE", new ItemStack(XRItems.emperorChalice), new AspectList().add(Aspect.WATER, 20).add(Aspect.ENTROPY, 10),
        		"gtg", "ege", "tgt",
        		'e', Item.emerald,
        		'g', Item.ingotGold,
        		't', Item.ghastTear);
        registerResearchItem("SOJOURNERSTAFF", "SOJOURNERSTAFF", new ItemStack(XRItems.sojournerStaff), new AspectList().add(Aspect.FIRE, 25).add(Aspect.AIR, 25),
        		"cgc", "gbg", "wgw",
        		'c', Item.magmaCream,
        		'g', Item.ingotGold,
        		'b', new ItemStack(ConfigBlocks.blockMetalDevice, 1, 7),
        		'w', XRItems.glowingWater);
        registerResearchItemShapeless("HOLYGRENADE", "HOLYGRENADE", new ItemStack(XRItems.holyHandGrenade), new AspectList().add(Aspect.ORDER, 5),
        		XRItems.glowingWater,
        		Item.goldNugget,
        		new ItemStack(ConfigItems.itemResource,1,0));
        registerResearchItemShapeless("CONDDRINK", "CONDPOTS", new ItemStack(XRItems.condensedPotion,1,Reference.POTION_META), new AspectList().add(Aspect.WATER, 5),
        		new ItemStack(XRItems.condensedPotion,1,Reference.WATER_META),
        		Item.redstone,
        		Item.netherStalkSeeds,
        		Item.glowstone);
        registerResearchItemShapeless("CONDSPLASH", "CONDPOTS", new ItemStack(XRItems.condensedPotion,1,Reference.SPLASH_META), new AspectList().add(Aspect.FIRE, 5),
        		new ItemStack(XRItems.condensedPotion,1,Reference.WATER_META),
        		Item.gunpowder,
        		Item.netherStalkSeeds,
        		Item.glowstone);
        
        registerResearchItemShapeless("CONDSPEED", "CONDPOTS", new ItemStack(XRItems.condensedPotion,1,Reference.SPEED_META), new AspectList().add(Aspect.AIR, 5),
        		new ItemStack(XRItems.condensedPotion,1,Reference.POTION_META),
        		Item.redstone,
        		Item.sugar,
        		Item.glowstone);
        registerResearchItemShapeless("CONDSLOW", "CONDPOTS", new ItemStack(XRItems.condensedPotion,1,Reference.SLOWING_META), new AspectList().add(Aspect.ENTROPY, 5),
        		new ItemStack(XRItems.condensedPotion,1,Reference.SPLASH_META),
        		Item.fermentedSpiderEye,
        		Item.sugar,
        		Item.glowstone);
        
        registerResearchItemShapeless("CONDSTRONG", "CONDPOTS", new ItemStack(XRItems.condensedPotion,1,Reference.STRENGTH_META), new AspectList().add(Aspect.EARTH, 5),
        		new ItemStack(XRItems.condensedPotion,1,Reference.POTION_META),
        		Item.redstone,
        		Item.blazePowder,
        		Item.glowstone);
        registerResearchItemShapeless("CONDWEAK", "CONDPOTS", new ItemStack(XRItems.condensedPotion,1,Reference.WEAKNESS_META), new AspectList().add(Aspect.AIR, 5),
        		new ItemStack(XRItems.condensedPotion,1,Reference.SPLASH_META),
        		Item.fermentedSpiderEye,
        		Item.blazePowder,
        		Item.glowstone);
        
        registerResearchItemShapeless("CONDHEAL", "CONDPOTS", new ItemStack(XRItems.condensedPotion,1,Reference.HEALING_META), new AspectList().add(Aspect.ORDER, 5),
        		new ItemStack(XRItems.condensedPotion,1,Reference.POTION_META),
        		Item.speckledMelon,
        		Item.glowstone,
        		Item.glowstone);
        registerResearchItemShapeless("CONDACID", "CONDPOTS", new ItemStack(XRItems.condensedPotion,1,Reference.ACID_META), new AspectList().add(Aspect.WATER, 5),
        		new ItemStack(XRItems.condensedPotion,1,Reference.SPLASH_META),
        		Item.speckledMelon,
        		Item.fermentedSpiderEye,
        		Item.glowstone);
        
        registerResearchItemShapeless("CONDSEE", "CONDPOTS", new ItemStack(XRItems.condensedPotion,1,Reference.INFRAVISION_META), new AspectList().add(Aspect.ORDER, 5),
        		new ItemStack(XRItems.condensedPotion,1,Reference.POTION_META),
        		Item.goldenCarrot,
        		Item.redstone,
        		Item.redstone);
        registerResearchItemShapeless("CONDBLIND", "CONDPOTS", new ItemStack(XRItems.condensedPotion,1,Reference.BLINDING_META), new AspectList().add(Aspect.ENTROPY, 5),
        		new ItemStack(XRItems.condensedPotion,1,Reference.SPLASH_META),
        		Item.goldenCarrot,
        		Item.fermentedSpiderEye,
        		Item.goldenCarrot);
        
        registerResearchItemShapeless("CONDFIRE", "CONDPOTS", new ItemStack(XRItems.condensedPotion,1,Reference.FIRE_WARDING_META), new AspectList().add(Aspect.WATER, 5),
        		new ItemStack(XRItems.condensedPotion,1,Reference.POTION_META),
        		Item.magmaCream,
        		Item.redstone,
        		Item.redstone);
        
        registerResearchItemShapeless("CONDPOISON", "CONDPOTS", new ItemStack(XRItems.condensedPotion,1,Reference.POISON_META), new AspectList().add(Aspect.WATER, 5),
        		new ItemStack(XRItems.condensedPotion,1,Reference.SPLASH_META),
        		Item.spiderEye,
        		Item.fermentedSpiderEye,
        		Item.redstone);
        
        registerResearchItemShapeless("CONDINVIS", "CONDPOTS", new ItemStack(XRItems.condensedPotion,1,Reference.INVISIBILITY_META), new AspectList().add(Aspect.ENTROPY, 5),
        		new ItemStack(XRItems.condensedPotion,1,Reference.POTION_META),
        		Item.fermentedSpiderEye,
        		Item.redstone,
        		Item.redstone);
        
        registerResearchItemShapeless("CONDREGEN", "CONDPOTS", new ItemStack(XRItems.condensedPotion,1,Reference.REGENERATION_META), new AspectList().add(Aspect.ORDER, 5),
        		new ItemStack(XRItems.condensedPotion,1,Reference.POTION_META),
        		Item.ghastTear,
        		Item.redstone,
        		Item.redstone);
        
        registerResearchItemShapeless("CONDLOVE", "CONDLOVE", new ItemStack(XRItems.condensedPotion,1,Reference.APHRODITE_META), new AspectList().add(Aspect.FIRE, 2).add(Aspect.ENTROPY, 2).add(Aspect.ORDER, 2),
        		new ItemStack(XRItems.condensedPotion,1,Reference.SPLASH_META),
        		new ItemStack(Item.dyePowder,1,3),
        		Item.redstone,
        		new ItemStack(Item.dyePowder,1,1));
        
        registerResearchItemShapeless("CONDCONFUSE", "CONDCONFUSE", new ItemStack(XRItems.condensedPotion,1,Reference.CONFUSION_META), new AspectList().add(Aspect.ORDER, 3).add(Aspect.ENTROPY, 3),
        		new ItemStack(XRItems.condensedPotion,1,Reference.SPLASH_META),
        		Item.goldenCarrot,
        		Item.fermentedSpiderEye,
        		Item.redstone);
        
        registerResearchItemShapeless("CONDWITHER", "CONDWITHER", new ItemStack(XRItems.condensedPotion,1,Reference.WITHER_META), new AspectList().add(Aspect.ENTROPY, 5),
        		new ItemStack(XRItems.condensedPotion,1,Reference.SPLASH_META),
        		new ItemStack(Item.skull,1,1),
        		Item.glowstone,
        		Item.glowstone);
        
        registerResearchItemShapeless("CONDDIGGING", "CONDDIGGING", new ItemStack(XRItems.condensedPotion,1,Reference.DIGGING_META), new AspectList().add(Aspect.EARTH,5),
        		new ItemStack(XRItems.condensedPotion,1,Reference.POTION_META),
        		Item.bone,
        		Item.glowstone,
        		Item.redstone);
        
        registerResearchItemShapeless("CONDJUMP", "CONDJUMP", new ItemStack(XRItems.condensedPotion,1,Reference.BOUNDING_META), new AspectList().add(Aspect.AIR, 5),
        		new ItemStack(XRItems.condensedPotion,1,Reference.POTION_META),
        		Item.blazePowder,
        		Item.glowstone,
        		Item.redstone);
        
        registerResearchItemShapeless("CONDRESIST", "CONDRESIST", new ItemStack(XRItems.condensedPotion,1,Reference.RESISTANCE_META), new AspectList().add(Aspect.EARTH,3).add(Aspect.ORDER,3),
        		new ItemStack(XRItems.condensedPotion,1,Reference.POTION_META),
        		Item.leather,
        		Item.redstone,
        		Item.redstone);
        
        registerResearchItemShapeless("CONDRUIN", "CONDCOMP", new ItemStack(XRItems.condensedPotion,3,Reference.RUINATION_META), new AspectList().add(Aspect.FIRE, 7).add(Aspect.ENTROPY, 7),
        		new ItemStack(XRItems.condensedPotion,1,Reference.WEAKNESS_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.SLOWING_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.POISON_META),
        		Item.glowstone);
        
        registerResearchItemShapeless("CONDPOTENCE", "CONDCOMP", new ItemStack(XRItems.condensedPotion,8,Reference.POTENCE_META), new AspectList().add(Aspect.EARTH, 20),
        		new ItemStack(XRItems.condensedPotion,1,Reference.STRENGTH_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.DIGGING_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.STRENGTH_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.DIGGING_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.STRENGTH_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.DIGGING_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.STRENGTH_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.DIGGING_META),
        		Item.glowstone);
        
        registerResearchItemShapeless("CONDCELERY", "CONDCOMP", new ItemStack(XRItems.condensedPotion,8,Reference.CELERITY_META), new AspectList().add(Aspect.AIR, 20),
        		new ItemStack(XRItems.condensedPotion,1,Reference.SPEED_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.BOUNDING_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.SPEED_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.BOUNDING_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.SPEED_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.BOUNDING_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.SPEED_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.BOUNDING_META),
        		Item.glowstone);
        
        registerResearchItemShapeless("CONDPROT", "CONDCOMP", new ItemStack(XRItems.condensedPotion,8,Reference.PROTECTION_META), new AspectList().add(Aspect.ORDER, 5).add(Aspect.EARTH, 5).add(Aspect.WATER, 10),
        		new ItemStack(XRItems.condensedPotion,1,Reference.RESISTANCE_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.FIRE_WARDING_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.RESISTANCE_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.FIRE_WARDING_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.RESISTANCE_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.FIRE_WARDING_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.RESISTANCE_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.FIRE_WARDING_META),
        		Item.glowstone);
        registerResearchItemShapeless("CONDHEALTHY", "CONDCOMP", new ItemStack(XRItems.condensedPotion,8,Reference.PANACEA_META), new AspectList().add(Aspect.ORDER, 20),
        		new ItemStack(XRItems.condensedPotion,1,Reference.HEALING_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.REGENERATION_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.HEALING_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.REGENERATION_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.HEALING_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.REGENERATION_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.HEALING_META),
        		new ItemStack(XRItems.condensedPotion,1,Reference.REGENERATION_META),
        		Item.bucketMilk);
        
        registerResearchItemShapeless("CONDBREATHE", "CONDBREATHE", new ItemStack(XRItems.condensedPotion,1,Reference.BREATHING_META), new AspectList().add(Aspect.AIR, 3).add(Aspect.WATER, 3),
        		new ItemStack(XRItems.condensedPotion,1,Reference.POTION_META),
        		new ItemStack(Item.dyePowder,1,0),
        		Item.redstone,
        		Item.redstone);
        
        registerResearchItemShapeless("CONDFERT", "CONDFERT", new ItemStack(XRItems.condensedPotion,1,Reference.FERTILIZER_META), new AspectList().add(Aspect.EARTH, 5),
        		new ItemStack(XRItems.condensedPotion,1,Reference.SPLASH_META),
        		new ItemStack(Item.dyePowder,1,15),
        		new ItemStack(Item.dyePowder,1,15),
        		new ItemStack(Item.dyePowder,1,15));
        
        registerResearchItemShapeless("GLOWWATER", "GLOWWATER", new ItemStack(XRItems.glowingWater), new AspectList().add(Aspect.AIR,3).add(Aspect.WATER,3).add(Aspect.FIRE,3).add(Aspect.EARTH,3).add(Aspect.ORDER,3).add(Aspect.ENTROPY,3),
        		new ItemStack(XRItems.condensedPotion,1,Reference.SPLASH_META),
        		Item.glowstone,
        		Item.glowstone);
        
        registerResearchItemShapeless("GLOWBREAD", "GLOWBREAD", new ItemStack(XRItems.glowingBread,3,0), new AspectList().add(Aspect.ORDER, 15),
        		Item.bread,
        		Item.bread,
        		Item.bread,
        		XRItems.glowingWater);
        
        registerResearchItemShapeless("VOIDTEAR", "VOIDTEAR", new ItemStack(XRItems.emptyVoidTear), new AspectList().add(Aspect.ENTROPY,10).add(Aspect.ORDER,10),
        		Item.eyeOfEnder,
        		Item.ghastTear,
        		Item.eyeOfEnder,
        		Item.ghastTear);
	}

	private static void initCraftingRecipes() {
		registerCraftingRecipe("VIAL", new ItemStack(XRItems.condensedPotion, 5, Reference.EMPTY_VIAL_META), "x x", "x x", " x ", 'x', new ItemStack(Block.thinGlass));
		
	}

	private static void initCrucibleRecipes() {
		//registerCrucibleRecipe("CONDPOTS","COODSPLASH", new ItemStack(XRItems.condensedPotion,1,Reference.POTION_META), new ItemStack(XRItems.condensedPotion,1,Reference.WATER_META), new AspectList().add(Aspect.ENERGY, 2).add(Aspect.MECHANISM, 1));
	}
	
	private static void registerResearchItem(String name, String research, ItemStack output, AspectList aspects, Object... stuff) {
	        ShapedArcaneRecipe recipe = ThaumcraftApi.addArcaneCraftingRecipe(research, output, aspects, stuff);
	        ConfigResearch.recipes.put(name, recipe);
	}
	
	private static void registerResearchItemShapeless(String name, String research, ItemStack output, AspectList aspects, Object... stuff) {
        ShapelessArcaneRecipe recipe = ThaumcraftApi.addShapelessArcaneCraftingRecipe(research, output, aspects, stuff);
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
	
	private static void registerResearchItemIE(String name, String research, Enchantment output, int instability, AspectList aspects, ItemStack... stuff) {
		InfusionEnchantmentRecipe recipe = ThaumcraftApi.addInfusionEnchantmentRecipe(name, output, instability, aspects, stuff);
        ConfigResearch.recipes.put(research, recipe);
	}

	private static void registerResearchItemC(String string, List<Object> asList) {
		ConfigResearch.recipes.put(string, asList);
		
	}
}
