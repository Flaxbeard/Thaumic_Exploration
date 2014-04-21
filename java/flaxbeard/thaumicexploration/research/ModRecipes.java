package flaxbeard.thaumicexploration.research;

import java.util.Arrays;
import java.util.List;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagByte;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.InfusionEnchantmentRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.crafting.ShapelessArcaneRecipe;
import thaumcraft.api.wands.WandRod;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.config.ConfigResearch;
import cpw.mods.fml.common.registry.GameRegistry;
import flaxbeard.thaumicexploration.ThaumicExploration;
import flaxbeard.thaumicexploration.item.ItemBaubleDiscountRing;


public final class ModRecipes {
	
    static ItemStack empty = new ItemStack(ConfigBlocks.blockHole, 1, 15);

    public static void initRecipes() {
        initCraftingRecipes();
        initArcaneRecipes();
        initInfusionRecipes();
        initCrucibleRecipes();
        initConstructRecipes();
        initNecromanticRecipes();
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
    					 empty, new ItemStack(Blocks.bookshelf), empty, 
    					 empty, empty, empty}) }));
    	 registerResearchItemC("BUILDNECROINFUSION", Arrays.asList(new Object[] { 
    			 new AspectList(), 
    			 Integer.valueOf(7), Integer.valueOf(1), Integer.valueOf(6), 
    			 Arrays.asList(new ItemStack[] { 
    					 empty,new ItemStack(ConfigBlocks.blockCandle),empty,empty,empty,new ItemStack(ConfigBlocks.blockCandle),empty,
    					 empty,empty,empty,new ItemStack(Blocks.skull),empty,empty,empty,
    					 empty,empty,new ItemStack(Blocks.skull),new ItemStack(ThaumicExploration.itemAltar),new ItemStack(Blocks.skull),empty,empty,
    					 new ItemStack(ConfigBlocks.blockCandle),empty,empty,new ItemStack(Blocks.skull),empty,empty,new ItemStack(ConfigBlocks.blockCandle),
    					 empty,empty,empty,empty,empty,empty,empty,
    					 empty,empty,empty,new ItemStack(ConfigBlocks.blockCandle),empty,empty,empty
    					 
    			 }) }));
    }


	private static void initInfusionRecipes() {
		registerResearchItemI("BRAINCURE", new ItemStack(ThaumicExploration.pureZombieBrain), 3, 
				new AspectList().add(Aspect.MAN, 4).add(Aspect.MIND, 6).add(Aspect.HEAL, 2), 
				new ItemStack(ConfigItems.itemResource, 1,5), 
				new ItemStack(Items.potionitem,1, 8232), new ItemStack(Items.golden_apple), 
				new ItemStack(Items.water_bucket));
		registerResearchItemI("BRAINCURE","BRAINCUREALT1", new ItemStack(ThaumicExploration.pureZombieBrain), 3, 
				new AspectList().add(Aspect.MAN, 4).add(Aspect.MIND, 6).add(Aspect.HEAL, 2), 
				new ItemStack(ConfigItems.itemResource, 1,5), 
				new ItemStack(Items.potionitem,1, 8264), new ItemStack(Items.golden_apple), 
				new ItemStack(Items.water_bucket));
		registerResearchItemI("BRAINCURE","BRAINCUREALT3", new ItemStack(ThaumicExploration.pureZombieBrain), 3, 
				new AspectList().add(Aspect.MAN, 4).add(Aspect.MIND, 6).add(Aspect.HEAL, 2), 
				new ItemStack(ConfigItems.itemResource, 1,5), 
				new ItemStack(Items.potionitem,1, 16424), new ItemStack(Items.golden_apple), 
				new ItemStack(Items.water_bucket));
		registerResearchItemI("BRAINCURE","BRAINCUREALT4", new ItemStack(ThaumicExploration.pureZombieBrain), 3, 
				new AspectList().add(Aspect.MAN, 4).add(Aspect.MIND, 6).add(Aspect.HEAL, 2), 
				new ItemStack(ConfigItems.itemResource, 1,5), 
				new ItemStack(Items.potionitem,1, 16456), new ItemStack(Items.golden_apple), 
				new ItemStack(Items.water_bucket));
		registerResearchItemI("BRAINCURE","BRAINCUREALT5", new ItemStack(ThaumicExploration.pureZombieBrain), 3, 
				new AspectList().add(Aspect.MAN, 4).add(Aspect.MIND, 6).add(Aspect.HEAL, 2), 
				new ItemStack(ConfigItems.itemResource, 1,5), 
				new ItemStack(Items.potionitem,1, 8200), new ItemStack(Items.golden_apple), 
				new ItemStack(Items.water_bucket));
		
		registerResearchItemI("ROD_AMBER", new ItemStack(ThaumicExploration.amberCore), 5, 
				new AspectList().add(Aspect.MAGIC, 14).add(Aspect.AURA, 4).add(Aspect.TRAP, 6), 
				new ItemStack(ConfigBlocks.blockCosmeticOpaque), 
				new ItemStack(ConfigItems.itemResource,1,14), new ItemStack(ConfigItems.itemResource,1,14));
		
		
		registerResearchItemI("ROD_TRANSMUTATION", new ItemStack(ThaumicExploration.transmutationCore), 6, 
				new AspectList().add(Aspect.MAGIC, 32).add(Aspect.EXCHANGE, 16).add(Aspect.AURA, 4), 
				new ItemStack(ConfigItems.itemResource,1,15), 
				new ItemStack(ConfigItems.itemShard,1,0), 
				new ItemStack(ConfigItems.itemShard,1,1), 
				new ItemStack(ConfigItems.itemShard,1,2), 
				new ItemStack(ConfigItems.itemShard,1,3), 
				new ItemStack(ConfigItems.itemShard,1,4), 
				new ItemStack(ConfigItems.itemShard,1,5), 
				new ItemStack(ConfigItems.itemResource,1,14));
		
		if (ThaumicExploration.breadWand) {
			registerResearchItemI("ROD_BREAD", new ItemStack(ThaumicExploration.breadCore), 3, 
					new AspectList().add(Aspect.MAGIC, 8).add(Aspect.CROP, 8).add(Aspect.HUNGER, 4), 
					new ItemStack(Items.bread), 
					new ItemStack(ConfigItems.itemResource,1,14));
		}
		
		registerResearchItemI("CRUCSOULS", new ItemStack(ThaumicExploration.crucibleSouls), 5, 
				new AspectList().add(Aspect.DEATH, 40).add(Aspect.UNDEAD, 10).add(Aspect.HUNGER, 20).add(Aspect.TRAP, 20).add(Aspect.WEAPON, 5).add(Aspect.SOUL, 30), 
				new ItemStack(ConfigBlocks.blockStoneDevice,1,0), 
				new ItemStack(ConfigBlocks.blockMetalDevice,1,0), new ItemStack(Items.rotten_flesh), 
				new ItemStack(Items.bone), new ItemStack(Items.ghast_tear), 
				new ItemStack(Blocks.soul_sand), new ItemStack(ConfigBlocks.blockMetalDevice,1,1));
		
		registerResearchItemI("TRASHJAR", new ItemStack(ThaumicExploration.trashJar), 7, 
				new AspectList().add(Aspect.ENTROPY, 16).add(Aspect.VOID, 12).add(Aspect.HUNGER, 12).add(Aspect.ELDRITCH, 12), 
				new ItemStack(ConfigBlocks.blockJar,1,3), 
				new ItemStack(ConfigBlocks.blockChestHungry), new ItemStack(ConfigBlocks.blockCosmeticOpaque, 6, 2), 
				new ItemStack(ConfigItems.itemShard,1,5), new ItemStack(ConfigBlocks.blockCosmeticOpaque, 6, 2), 
				new ItemStack(ConfigItems.itemShard,1,5), new ItemStack(ConfigBlocks.blockCosmeticOpaque, 6, 2),
				new ItemStack(ConfigItems.itemShard,1,5), new ItemStack(ConfigBlocks.blockCosmeticOpaque, 6, 2));
		
		registerResearchItemI("ROD_NECROMANCER_staff", new ItemStack(ThaumicExploration.necroStaffCore), 6, 
				new AspectList().add(Aspect.DEATH, 32).add(Aspect.ENTROPY, 16).add(Aspect.SOUL, 16).add(Aspect.AURA, 8), 
				new ItemStack(ConfigItems.itemWandRod, 1, 57), 
				new ItemStack(Items.skull,1,1), new ItemStack(Items.rotten_flesh), 
				new ItemStack(Items.bone), new ItemStack(ThaumicExploration.pureZombieBrain),
				new ItemStack(Items.bone), new ItemStack(Items.rotten_flesh));
		
		registerResearchItemI("TENTACLERING", new ItemStack(ThaumicExploration.tentacleRing), 4, 
				new AspectList().add(Aspect.TAINT, 32).add(Aspect.WEAPON, 8).add(Aspect.ARMOR, 16).add(Aspect.BEAST, 8), 
				new ItemStack(ConfigItems.itemBaubleBlanks, 1, 1), 
				new ItemStack(ConfigItems.itemResource,1,11), new ItemStack(ConfigItems.itemResource,1,12), 
				new ItemStack(ConfigItems.itemResource,1,11), new ItemStack(ConfigItems.itemResource,1,12));
		
		registerResearchItemI("STABILIZERBELT", new ItemStack(ThaumicExploration.stabilizerBelt), 3, 
				new AspectList().add(Aspect.ORDER, 12).add(Aspect.EARTH, 12).add(Aspect.ARMOR, 4).add(Aspect.TRAVEL, 8), 
				new ItemStack(ConfigItems.itemBaubleBlanks, 1, 2), 
				new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot), 
				new ItemStack(Items.gold_ingot), new ItemStack(Items.gold_ingot), 
				new ItemStack(ConfigItems.itemShard,1,4), new ItemStack(ConfigItems.itemShard,1,3));
		
//		for (int i = 0; i<16; i++) {
//			registerResearchItemI("CHESTSEAL", new ItemStack(ThaumicExploration.chestSeal, 1, i), 7, 
//					new AspectList().add(Aspect.ELDRITCH, 6).add(Aspect.EXCHANGE,4).add(Aspect.VOID, 4).add(Aspect.TRAP, 4).add(Aspect.TRAVEL, 12), 
//					new ItemStack(ThaumicExploration.blankSeal, 1, i), new ItemStack(Items.enderPearl), 
//					new ItemStack(ConfigItems.itemResource,1,14), new ItemStack(ConfigItems.itemResource,1,14),
//					new ItemStack(Items.blazePowder), new ItemStack(Blocks.chest),  new ItemStack(ConfigItems.itemResource,1,3));
//		}
		
		registerResearchItemI("METEORBOOTS", new ItemStack(ThaumicExploration.bootsMeteor), 4, 
				new AspectList().add(Aspect.FIRE, 25).add(Aspect.ENERGY, 25).add(Aspect.TRAVEL, 25).add(Aspect.FLIGHT,25), 
				new ItemStack(ConfigItems.itemBootsTraveller, 1, 32767), new ItemStack(ConfigBlocks.blockCrystal, 1, 1), 
				new ItemStack(Blocks.netherrack), new ItemStack(Blocks.netherrack),
				new ItemStack(Blocks.netherrack), new ItemStack(ConfigItems.itemFocusFire));
		
		registerResearchItemIE("ENCHBINDING","ENCHBINDING", Enchantment.enchantmentsList[ThaumicExploration.enchantmentBinding.effectId], 3, 
				new AspectList().add(Aspect.TRAP, 8).add(Aspect.ENTROPY, 4).add(Aspect.TRAVEL, 4), 
				new ItemStack(Items.iron_sword), new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Blocks.soul_sand));
		
		registerResearchItemIE("ENCHDISARM","ENCHDISARM", Enchantment.enchantmentsList[ThaumicExploration.enchantmentDisarm.effectId], 5, 
				new AspectList().add(Aspect.WEAPON, 4).add(Aspect.SLIME, 8).add(Aspect.TRAP, 4), 
				new ItemStack(Items.iron_sword), new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Items.slime_ball));
		
		registerResearchItemIE("ENCHNIGHTVISION","ENCHNIGHTVISION", Enchantment.enchantmentsList[ThaumicExploration.enchantmentNightVision.effectId], 5, 
				new AspectList().add(Aspect.SENSES, 16).add(Aspect.DARKNESS, 8).add(Aspect.LIGHT, 16), 
				new ItemStack(Items.golden_carrot), new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Items.golden_carrot));
		
		registerResearchItemI("TALISMANFOOD", new ItemStack(ThaumicExploration.talismanFood), 5, 
				new AspectList().add(Aspect.HUNGER, 30).add(Aspect.FLESH, 25).add(Aspect.CROP, 25).add(Aspect.EXCHANGE,10), 
				new ItemStack(ConfigItems.itemResource,1,15), new ItemStack(Blocks.obsidian), 
				new ItemStack(Items.cooked_beef), new ItemStack(Items.cooked_chicken),
				new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_fished), new ItemStack(Items.bread));
		
		registerResearchItemI("REPLICATOR", new ItemStack(ThaumicExploration.replicator), 9, 
				new AspectList().add(Aspect.CRAFT, 50).add(Aspect.MECHANISM, 30).add(Aspect.TOOL, 30).add(Aspect.ORDER,20), 
				new ItemStack(ConfigBlocks.blockStoneDevice,1,2), new ItemStack(ConfigBlocks.blockTable,1,15), new ItemStack(Items.gold_ingot), 
				new ItemStack(ConfigItems.itemResource,1,2), new ItemStack(Items.gold_ingot),
				new ItemStack(ConfigItems.itemResource,1,2), new ItemStack(Items.gold_ingot),
				new ItemStack(ConfigItems.itemResource,1,2));
		
		registerResearchItemI("COMETBOOTS", new ItemStack(ThaumicExploration.bootsComet), 4, 
				new AspectList().add(Aspect.WATER, 25).add(Aspect.COLD, 25).add(Aspect.TRAVEL, 25).add(Aspect.MOTION,25), 
				new ItemStack(ConfigItems.itemBootsTraveller, 1, 32767), new ItemStack(ConfigBlocks.blockCrystal, 1, 2), 
				new ItemStack(Blocks.snow), new ItemStack(Blocks.snow),
				new ItemStack(Blocks.snow), new ItemStack(ConfigItems.itemFocusFrost));
		
		
// 		registerResearchItemI("CHESTSEAL", new ItemStack(ThaumicExploration.chestSeal, 1, 32767), 7, 
//				new AspectList().add(Aspect.ELDRITCH, 6).add(Aspect.EXCHANGE,4).add(Aspect.VOID, 4).add(Aspect.TRAP, 4).add(Aspect.TRAVEL, 12), 
//				new ItemStack(ThaumicExploration.blankSeal, 1, 32767), new ItemStack(Items.enderPearl), 
//				new ItemStack(ConfigItems.itemResource,1,14), new ItemStack(ConfigItems.itemResource,1,14),
//				new ItemStack(Items.blazePowder),new ItemStack(Blocks.chest),  new ItemStack(ConfigItems.itemResource,1,3));

		registerResearchItemI("THINKTANK", new ItemStack(ThaumicExploration.thinkTankJar), 6, 
				new AspectList().add(Aspect.MIND, 40).add(Aspect.SENSES, 20).add(Aspect.UNDEAD, 30), 
				new ItemStack(ConfigBlocks.blockJar),new ItemStack(ThaumicExploration.pureZombieBrain), new ItemStack(ConfigItems.itemInkwell,1,0), 
				new ItemStack(ThaumicExploration.pureZombieBrain), new ItemStack(ConfigItems.itemThaumonomicon), 
				new ItemStack(ThaumicExploration.pureZombieBrain), new ItemStack(ConfigItems.itemThaumometer));
		
		registerResearchItemI("URN", new ItemStack(ThaumicExploration.everfullUrn), 2, 
				new AspectList().add(Aspect.WATER, 20).add(Aspect.VOID, 8).add(Aspect.MAGIC, 4), 
				new ItemStack(Items.flower_pot),new ItemStack(Items.water_bucket), 
				new ItemStack(Items.brick),new ItemStack(Items.water_bucket), 
				new ItemStack(Items.brick),new ItemStack(Items.water_bucket), 
				new ItemStack(Items.brick) );
		
		registerResearchItemI("NECROINFUSION", new ItemStack(ThaumicExploration.itemAltar), 7, 
				new AspectList().add(Aspect.MAGIC, 32).add(Aspect.DEATH, 16).add(Aspect.UNDEAD, 16).add(Aspect.CRAFT, 16), 
				new ItemStack(ConfigBlocks.blockStoneDevice,1,1),new ItemStack(Blocks.netherrack), 
				new ItemStack(ConfigBlocks.blockCosmeticSolid,1,6),new ItemStack(ConfigBlocks.blockStoneDevice,1,2),
				new ItemStack(ConfigBlocks.blockCosmeticSolid,1,6));
		
		registerResearchItemI("CAP_SOJOURNER", new ItemStack(ThaumicExploration.sojournerCap), 5, 
				new AspectList().add(Aspect.AURA, 6).add(Aspect.MAGIC, 12).add(Aspect.EXCHANGE, 16).add(Aspect.ENERGY, 12), 
				new ItemStack(ThaumicExploration.sojournerCapUncharged),new ItemStack(ConfigItems.itemResource, 1, 14), 
				new ItemStack(ConfigItems.itemResource, 1, 14));
		
		registerResearchItemI("CAP_MECHANIST", new ItemStack(ThaumicExploration.mechanistCap), 5, 
				new AspectList().add(Aspect.MECHANISM, 16).add(Aspect.MAGIC, 12).add(Aspect.AURA, 6).add(Aspect.ENERGY, 12), 
				new ItemStack(ThaumicExploration.mechanistCapUncharged),new ItemStack(ConfigItems.itemResource, 1, 14), 
				new ItemStack(ConfigItems.itemResource, 1, 14));
		
//		for (int i = 0; i<16; i++) {
//			registerResearchItemI("JARSEAL", new ItemStack(ThaumicExploration.jarSeal, 1, i), 7, 
//					new AspectList().add(Aspect.ELDRITCH, 6).add(Aspect.EXCHANGE,4).add(Aspect.TRAP, 4).add(Aspect.TRAVEL, 12).add(Aspect.MAGIC, 2), 
//					new ItemStack(ThaumicExploration.blankSeal, 1, i), new ItemStack(Items.enderPearl), 
//					new ItemStack(ConfigItems.itemResource,1,14), new ItemStack(ConfigItems.itemResource,1,14),
//					new ItemStack(Items.blazePowder), new ItemStack(ConfigBlocks.blockJar),  new ItemStack(ConfigItems.itemResource,1,3));
//		}
//		registerResearchItemI("JARSEAL", new ItemStack(ThaumicExploration.jarSeal, 1, 32767), 7, 
//				new AspectList().add(Aspect.ELDRITCH, 6).add(Aspect.EXCHANGE,4).add(Aspect.TRAP, 4).add(Aspect.TRAVEL, 12).add(Aspect.MAGIC, 2), 
//				new ItemStack(ThaumicExploration.blankSeal, 1, 32767), new ItemStack(Items.enderPearl), 
//				new ItemStack(ConfigItems.itemResource,1,14), new ItemStack(ConfigItems.itemResource,1,14),
//				new ItemStack(Items.blazePowder), new ItemStack(ConfigBlocks.blockJar),  new ItemStack(ConfigItems.itemResource,1,3));
//		
		//Change Advanced golem recipe to require a pure brain
//		if (ThaumicExploration.brainsGolem) {
//	        InfusionRecipe recipe = (InfusionRecipe) ConfigResearch.recipes.get("AdvancedGolem");
//	        ConfigResearch.recipes.remove(ConfigResearch.recipes.get("AdvancedGolem"));
//	        ItemStack[] components = {new ItemStack(Items.redstone), new ItemStack(Items.glowstone_dust), new ItemStack(Items.gunpowder), new ItemStack(ConfigBlocks.blockJar, 1, 0), new ItemStack(ThaumicExploration.pureZombieBrain) };
//	        recipe. = components;
//	        InfusionRecipe recipe2 = ThaumcraftApi.addInfusionCraftingRecipe("ADVANCEDGOLEM", recipe.recipeOutput, recipe.instability, recipe.aspects, recipe.recipeInput, recipe.components);
//			ConfigResearch.recipes.put("AdvancedGolem", recipe2);
//		}
		
	}


	private static void initArcaneRecipes() {
		for (int i = 0; i<15; i++) {
	        registerResearchItemShapeless("FLOATCANDLE"+i, "FLOATCANDLE", new ItemStack(ThaumicExploration.floatCandle,3,i), new AspectList().add(Aspect.AIR, 5),
	                new ItemStack(ConfigBlocks.blockCandle,1,i),
	                new ItemStack(ConfigBlocks.blockCandle,1,i),
	                new ItemStack(ConfigBlocks.blockCandle,1,i),
	                new ItemStack(ConfigItems.itemShard,1,0));
		}
        registerResearchItemShapeless("FLOATCANDLE", "FLOATCANDLE", new ItemStack(ThaumicExploration.floatCandle,3,32767), new AspectList().add(Aspect.AIR, 5),
                new ItemStack(ConfigBlocks.blockCandle,1,32767),
                new ItemStack(ConfigBlocks.blockCandle,1,32767),
                new ItemStack(ConfigBlocks.blockCandle,1,32767),
                new ItemStack(ConfigItems.itemShard,1,0));
        for (int i = 0; i<6; i++) {
	        registerResearchItem("DISCOUNTRINGS"+i, "DISCOUNTRINGS", new ItemStack(ThaumicExploration.discountRing,1,i), new AspectList().add(Aspect.getPrimalAspects().get(i), 15),
	                " F ", "F F", " F ",
	                'F', new ItemStack(ConfigItems.itemShard,1,ItemBaubleDiscountRing.correspondingShards[i]));
        }
        
        registerResearchItem("DREAMCATCHER", "DREAMCATCHER", new ItemStack(ThaumicExploration.charmNoTaint), new AspectList().add(Aspect.ORDER, 15).add(Aspect.ENTROPY, 15),
                "GPG", "PSP", "FPF",
                'G', new ItemStack(ConfigItems.itemResource,1, 11),
                'P', new ItemStack(ConfigBlocks.blockWoodenDevice,1,6),
                'S', new ItemStack(ConfigItems.itemResource,1, 12),
                'F', new ItemStack(Items.feather));
        
        registerResearchItem("DREAMCATCHER2", "DREAMCATCHER", new ItemStack(ThaumicExploration.charmNoTaint), new AspectList().add(Aspect.ORDER, 15).add(Aspect.ENTROPY, 15),
                "GPG", "PSP", "FPF",
                'G', "gooTaint",
                'P', new ItemStack(ConfigBlocks.blockWoodenDevice,1,6),
                'S', new ItemStack(ConfigItems.itemResource,1, 12),
                'F', "tendrilTaint");
        
        registerResearchItem("UNCHARGEDSOJOURNER", "CAP_SOJOURNER", new ItemStack(ThaumicExploration.sojournerCapUncharged), new AspectList().add(Aspect.AIR, 6).add(Aspect.ENTROPY, 6).add(Aspect.ORDER, 6),
                "ABA", "A A",
                'A', new ItemStack(Items.dye,1,4),
                'B', new ItemStack(Items.diamond));
        
        registerResearchItem("UNCHARGEDMECHANIST", "CAP_MECHANIST", new ItemStack(ThaumicExploration.mechanistCapUncharged), new AspectList().add(Aspect.FIRE, 6).add(Aspect.ENTROPY, 6).add(Aspect.ORDER, 6),
                "AAA", "ABA", " C ",
                'A', new ItemStack(Items.redstone),
                'B', new ItemStack(Blocks.piston),
                'C', new ItemStack(Items.repeater));
        
        registerResearchItem("ROD_AMBER_staff","ROD_AMBER_staff", new ItemStack(ThaumicExploration.amberStaffCore), new AspectList().add(Aspect.ORDER, ((WandRod)WandRod.rods.get("AMBER_staff")).getCraftCost()),
                "  S", " G ", "G  ",
                'S', new ItemStack(ConfigItems.itemResource, 1, 15),
                'G', new ItemStack(ThaumicExploration.amberCore));
        
        registerResearchItem("ROD_TRANSMUTATION_staff","ROD_TRANSMUTATION_staff", new ItemStack(ThaumicExploration.transmutationStaffCore), new AspectList().add(Aspect.ORDER, ((WandRod)WandRod.rods.get("AMBER_staff")).getCraftCost()),
                "  S", " G ", "G  ",
                'S', new ItemStack(ConfigItems.itemResource, 1, 15),
                'G', new ItemStack(ThaumicExploration.transmutationCore));
	}

	private static void initCraftingRecipes() {
		for (int i=0;i<16;i++) {
			GameRegistry.addRecipe(new ItemStack(ThaumicExploration.blankSeal, 1, i), " X ", "XZX", " X ", 'X', new ItemStack(ConfigItems.itemResource,1,4), 'Z', new ItemStack(Items.dye,1,i));
		}
		
		registerCraftingRecipe("BLANKSEAL", new ItemStack(ThaumicExploration.blankSeal, 1, 32767), " X ", "XZX", " X ", 'X', new ItemStack(ConfigItems.itemResource,1,4), 'Z', new ItemStack(Items.dye,1,32767));
		
	}

	private static void initCrucibleRecipes() {
		registerCrucibleRecipe("FLESHCURE","FLESHCURE", new ItemStack(Items.leather,2), new ItemStack(Items.rotten_flesh), new AspectList().add(Aspect.FLESH, 2).add(Aspect.CLOTH, 1));
		for (int i = 0; i<16; i++) {
			registerCrucibleRecipe("CHESTSEAL","CHESTSEAL", new ItemStack(ThaumicExploration.chestSeal,1,i), new ItemStack(ThaumicExploration.blankSeal,1,i), 
					new AspectList().add(Aspect.ELDRITCH, 6).add(Aspect.EXCHANGE,4).add(Aspect.VOID, 4).add(Aspect.TRAP, 4).add(Aspect.TRAVEL, 6));
		}
		
		for (int i = 0; i<16; i++) {
			registerCrucibleRecipe("JARSEAL","JARSEAL", new ItemStack(ThaumicExploration.jarSeal,1,i), new ItemStack(ThaumicExploration.blankSeal,1,i), 
					new AspectList().add(Aspect.ELDRITCH, 6).add(Aspect.EXCHANGE,4).add(Aspect.CRYSTAL, 4).add(Aspect.TRAP, 4).add(Aspect.TRAVEL, 6));
		}
		//registerCrucibleRecipe("CHESTSEAL","CHESTSEAL", new ItemStack(ThaumicExploration.chestSeal,1,1), new ItemStack(ThaumicExploration.blankSeal,1,32767), 
				//new AspectList().add(Aspect.ELDRITCH, 6).add(Aspect.EXCHANGE,4).add(Aspect.VOID, 4).add(Aspect.TRAP, 4).add(Aspect.TRAVEL, 12));
	}
	
	public static void initNecromanticRecipes()
	{
//		  NecromanticAltarAPI.addNecroRecipeWithThaumnomiconDisplay("ROD_NECROMANCER", new NecromanticRecipe("NECROWAND", new ItemStack(ThaumicExploration.necroCore), new ItemStack[]{new ItemStack(Items.skull,1,1),new ItemStack(Items.bone),new ItemStack(Items.bone),new ItemStack(ConfigItems.itemResource,1,14),new ItemStack(ConfigItems.itemResource,1,14)}, 25));
//		  NecromanticAltarAPI.addEnergyToItem(Items.rottenFlesh,1);
//		  NecromanticAltarAPI.addEnergyToItem(Items.bone,2);
//		  NecromanticAltarAPI.addEnergyToItem(ConfigItems.itemResource,5,4);
//		  NecromanticAltarAPI.addEnergyToItem(Items.skull,1,5);
//		  NecromanticAltarAPI.addEnergyToItem(Items.appleRed,1);
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
	
	private static void registerResearchItemShapeless(String name, String research, ItemStack output, AspectList aspects, Object... stuff) {
        ShapelessArcaneRecipe recipe = ThaumcraftApi.addShapelessArcaneCraftingRecipe(research, output, aspects, stuff);
        ConfigResearch.recipes.put(name, recipe);
	}
	
	private static void registerResearchItemI(String name, String research, Object output, int instability, AspectList aspects, ItemStack input, ItemStack... stuff) {
        InfusionRecipe recipe = ThaumcraftApi.addInfusionCraftingRecipe(name, output, instability, aspects, input, stuff);
        ConfigResearch.recipes.put(research, recipe);
	}
	
	private static void registerResearchItemIUI(String research, String name, Object output, int instability, AspectList aspects, ItemStack input, ItemStack... stuff) {
        InfusionRecipe recipe = ThaumcraftApi.addInfusionCraftingRecipe(name, output, instability, aspects, input, stuff);
        ConfigResearch.recipes.put(research, recipe);
	}
	
	private static void registerResearchItemIE(String name, String research, Enchantment output, int instability, AspectList aspects, ItemStack... stuff) {
		InfusionEnchantmentRecipe recipe = ThaumcraftApi.addInfusionEnchantmentRecipe(name, output, instability, aspects, stuff);
        ConfigResearch.recipes.put(research, recipe);
	}
	
	private static void registerResearchItemIU(String research, String name,Object[] objects, int instability, AspectList aspects, ItemStack input, ItemStack... stuff) {
		InfusionRecipe recipe = ThaumcraftApi.addInfusionCraftingRecipe(name, objects, instability, aspects, input, stuff);	
        ConfigResearch.recipes.put(research, recipe);
	}

	private static void registerResearchItemC(String string, List<Object> asList) {
		ConfigResearch.recipes.put(string, asList);
		
	}
}
