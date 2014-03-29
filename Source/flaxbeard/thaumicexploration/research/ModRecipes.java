package flaxbeard.thaumicexploration.research;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
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
import flaxbeard.thaumicexploration.api.NecromanticAltarAPI;
import flaxbeard.thaumicexploration.api.NecromanticRecipe;


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
    					 empty, new ItemStack(Block.bookShelf), empty, 
    					 empty, empty, empty}) }));
    	 registerResearchItemC("BUILDNECROINFUSION", Arrays.asList(new Object[] { 
    			 new AspectList(), 
    			 Integer.valueOf(7), Integer.valueOf(1), Integer.valueOf(6), 
    			 Arrays.asList(new ItemStack[] { 
    					 empty,new ItemStack(ConfigBlocks.blockCandle),empty,empty,empty,new ItemStack(ConfigBlocks.blockCandle),empty,
    					 empty,empty,empty,new ItemStack(Block.skull),empty,empty,empty,
    					 empty,empty,new ItemStack(Block.skull),new ItemStack(ThaumicExploration.itemAltar),new ItemStack(Block.skull),empty,empty,
    					 new ItemStack(ConfigBlocks.blockCandle),empty,empty,new ItemStack(Block.skull),empty,empty,new ItemStack(ConfigBlocks.blockCandle),
    					 empty,empty,empty,empty,empty,empty,empty,
    					 empty,empty,empty,new ItemStack(ConfigBlocks.blockCandle),empty,empty,empty
    					 
    			 }) }));
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
		registerResearchItemI("BRAINCURE","BRAINCUREALT5", new ItemStack(ThaumicExploration.pureZombieBrain), 3, 
				new AspectList().add(Aspect.MAN, 4).add(Aspect.MIND, 6).add(Aspect.HEAL, 2), 
				new ItemStack(ConfigItems.itemResource, 1,5), 
				new ItemStack(Item.potion,1, 8200), new ItemStack(Item.appleGold), 
				new ItemStack(Item.bucketWater));
		
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
					new AspectList().add(Aspect.MAGIC, 8).add(Aspect.SEED, 8).add(Aspect.HUNGER, 4), 
					new ItemStack(Item.bread), 
					new ItemStack(ConfigItems.itemResource,1,14));
		}
		
		registerResearchItemI("CRUCSOULS", new ItemStack(ThaumicExploration.crucibleSouls), 5, 
				new AspectList().add(Aspect.DEATH, 40).add(Aspect.UNDEAD, 10).add(Aspect.HUNGER, 20).add(Aspect.TRAP, 20).add(Aspect.WEAPON, 5).add(Aspect.SOUL, 30), 
				new ItemStack(ConfigBlocks.blockStoneDevice,1,0), 
				new ItemStack(ConfigBlocks.blockMetalDevice,1,0), new ItemStack(Item.rottenFlesh), 
				new ItemStack(Item.bone), new ItemStack(Item.ghastTear), 
				new ItemStack(Block.slowSand), new ItemStack(ConfigBlocks.blockMetalDevice,1,1));
		
//		for (int i = 0; i<16; i++) {
//			registerResearchItemI("CHESTSEAL", new ItemStack(ThaumicExploration.chestSeal, 1, i), 7, 
//					new AspectList().add(Aspect.ELDRITCH, 6).add(Aspect.EXCHANGE,4).add(Aspect.VOID, 4).add(Aspect.TRAP, 4).add(Aspect.TRAVEL, 12), 
//					new ItemStack(ThaumicExploration.blankSeal, 1, i), new ItemStack(Item.enderPearl), 
//					new ItemStack(ConfigItems.itemResource,1,14), new ItemStack(ConfigItems.itemResource,1,14),
//					new ItemStack(Item.blazePowder), new ItemStack(Block.chest),  new ItemStack(ConfigItems.itemResource,1,3));
//		}
		
		registerResearchItemI("METEORBOOTS", new ItemStack(ThaumicExploration.bootsMeteor), 4, 
				new AspectList().add(Aspect.FIRE, 25).add(Aspect.ENERGY, 25).add(Aspect.TRAVEL, 25).add(Aspect.FLIGHT,25), 
				new ItemStack(ConfigItems.itemBootsTraveller, 1, 32767), new ItemStack(ConfigBlocks.blockCrystal, 1, 1), 
				new ItemStack(Block.netherrack), new ItemStack(Block.netherrack),
				new ItemStack(Block.netherrack), new ItemStack(ConfigItems.itemFocusFire));
		
		registerResearchItemIE("ENCHBINDING","ENCHBINDING", Enchantment.enchantmentsList[ThaumicExploration.enchantmentBinding.effectId], 3, 
				new AspectList().add(Aspect.TRAP, 8).add(Aspect.ENTROPY, 4).add(Aspect.TRAVEL, 4), 
				new ItemStack(Item.swordIron), new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Block.slowSand));
		
		registerResearchItemIE("ENCHDISARM","ENCHDISARM", Enchantment.enchantmentsList[ThaumicExploration.enchantmentDisarm.effectId], 5, 
				new AspectList().add(Aspect.WEAPON, 4).add(Aspect.SLIME, 8).add(Aspect.TRAP, 4), 
				new ItemStack(Item.swordIron), new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.slimeBall));
		
		registerResearchItemIE("ENCHNIGHTVISION","ENCHNIGHTVISION", Enchantment.enchantmentsList[ThaumicExploration.enchantmentNightVision.effectId], 5, 
				new AspectList().add(Aspect.SENSES, 16).add(Aspect.DARKNESS, 8).add(Aspect.LIGHT, 16), 
				new ItemStack(Item.goldenCarrot), new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.goldenCarrot));
		
		registerResearchItemI("TALISMANFOOD", new ItemStack(ThaumicExploration.talismanFood), 5, 
				new AspectList().add(Aspect.HUNGER, 30).add(Aspect.FLESH, 25).add(Aspect.CROP, 25).add(Aspect.EXCHANGE,10), 
				new ItemStack(ConfigItems.itemResource,1,15), new ItemStack(Block.obsidian), 
				new ItemStack(Item.beefCooked), new ItemStack(Item.chickenCooked),
				new ItemStack(Item.porkCooked), new ItemStack(Item.fishCooked), new ItemStack(Item.bread));
		
		registerResearchItemI("REPLICATOR", new ItemStack(ThaumicExploration.replicator), 9, 
				new AspectList().add(Aspect.CRAFT, 50).add(Aspect.MECHANISM, 30).add(Aspect.TOOL, 30).add(Aspect.ORDER,20), 
				new ItemStack(ConfigBlocks.blockStoneDevice,1,2), new ItemStack(ConfigBlocks.blockTable,1,15), new ItemStack(Item.ingotGold), 
				new ItemStack(ConfigItems.itemResource,1,2), new ItemStack(Item.ingotGold),
				new ItemStack(ConfigItems.itemResource,1,2), new ItemStack(Item.ingotGold),
				new ItemStack(ConfigItems.itemResource,1,2));
		
		registerResearchItemI("COMETBOOTS", new ItemStack(ThaumicExploration.bootsComet), 4, 
				new AspectList().add(Aspect.WATER, 25).add(Aspect.ICE, 25).add(Aspect.TRAVEL, 25).add(Aspect.MOTION,25), 
				new ItemStack(ConfigItems.itemBootsTraveller, 1, 32767), new ItemStack(ConfigBlocks.blockCrystal, 1, 2), 
				new ItemStack(Block.blockSnow), new ItemStack(Block.blockSnow),
				new ItemStack(Block.blockSnow), new ItemStack(ConfigItems.itemFocusFrost));
		
		registerResearchItemI("RUNICBOOTS", "RUNICBOOTSCOMET", new ItemStack(ThaumicExploration.runicBootsComet), 5,
				new AspectList().add(Aspect.MAGIC, 25).add(Aspect.EXCHANGE, 25).add(Aspect.WATER, 10), 
				new ItemStack(ThaumicExploration.bootsComet, 1, 32767), new ItemStack(ConfigItems.itemBootsRunic), new ItemStack(Item.slimeBall));
		
		registerResearchItemI("RUNICBOOTS", "RUNICBOOTSMETEOR", new ItemStack(ThaumicExploration.runicBootsMeteor), 5,
				new AspectList().add(Aspect.MAGIC, 25).add(Aspect.EXCHANGE, 25).add(Aspect.FIRE, 10), 
				new ItemStack(ThaumicExploration.bootsMeteor, 1, 32767), new ItemStack(ConfigItems.itemBootsRunic), new ItemStack(Item.slimeBall));
		
		registerResearchItemI("RUNICBOOTS", "RUNICBOOTSMETEOR2", new ItemStack(ThaumicExploration.runicBootsMeteor), 5, 
				new AspectList().add(Aspect.FIRE, 35).add(Aspect.ENERGY, 25).add(Aspect.TRAVEL, 25).add(Aspect.FLIGHT,25), 
				new ItemStack(ConfigItems.itemBootsTravellerRunic, 1, 32767), new ItemStack(ConfigBlocks.blockCrystal, 1, 1), 
				new ItemStack(Block.netherrack), new ItemStack(Block.netherrack),
				new ItemStack(Block.netherrack), new ItemStack(ConfigItems.itemFocusFire));
		
		registerResearchItemI("COMETBOOTS", "RUNICBOOTSCOMET2", new ItemStack(ThaumicExploration.runicBootsComet), 5, 
				new AspectList().add(Aspect.WATER, 35).add(Aspect.ICE, 25).add(Aspect.TRAVEL, 25).add(Aspect.MOTION,25), 
				new ItemStack(ConfigItems.itemBootsTravellerRunic, 1, 32767), new ItemStack(ConfigBlocks.blockCrystal, 1, 2), 
				new ItemStack(Block.blockSnow), new ItemStack(Block.blockSnow),
				new ItemStack(Block.blockSnow), new ItemStack(ConfigItems.itemFocusFrost));
		
// 		registerResearchItemI("CHESTSEAL", new ItemStack(ThaumicExploration.chestSeal, 1, 32767), 7, 
//				new AspectList().add(Aspect.ELDRITCH, 6).add(Aspect.EXCHANGE,4).add(Aspect.VOID, 4).add(Aspect.TRAP, 4).add(Aspect.TRAVEL, 12), 
//				new ItemStack(ThaumicExploration.blankSeal, 1, 32767), new ItemStack(Item.enderPearl), 
//				new ItemStack(ConfigItems.itemResource,1,14), new ItemStack(ConfigItems.itemResource,1,14),
//				new ItemStack(Item.blazePowder),new ItemStack(Block.chest),  new ItemStack(ConfigItems.itemResource,1,3));

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
		
		registerResearchItemI("NECROINFUSION", new ItemStack(ThaumicExploration.itemAltar), 7, 
				new AspectList().add(Aspect.MAGIC, 32).add(Aspect.DEATH, 16).add(Aspect.UNDEAD, 16).add(Aspect.CRAFT, 16), 
				new ItemStack(ConfigBlocks.blockStoneDevice,1,1),new ItemStack(Block.netherrack), 
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
//					new ItemStack(ThaumicExploration.blankSeal, 1, i), new ItemStack(Item.enderPearl), 
//					new ItemStack(ConfigItems.itemResource,1,14), new ItemStack(ConfigItems.itemResource,1,14),
//					new ItemStack(Item.blazePowder), new ItemStack(ConfigBlocks.blockJar),  new ItemStack(ConfigItems.itemResource,1,3));
//		}
//		registerResearchItemI("JARSEAL", new ItemStack(ThaumicExploration.jarSeal, 1, 32767), 7, 
//				new AspectList().add(Aspect.ELDRITCH, 6).add(Aspect.EXCHANGE,4).add(Aspect.TRAP, 4).add(Aspect.TRAVEL, 12).add(Aspect.MAGIC, 2), 
//				new ItemStack(ThaumicExploration.blankSeal, 1, 32767), new ItemStack(Item.enderPearl), 
//				new ItemStack(ConfigItems.itemResource,1,14), new ItemStack(ConfigItems.itemResource,1,14),
//				new ItemStack(Item.blazePowder), new ItemStack(ConfigBlocks.blockJar),  new ItemStack(ConfigItems.itemResource,1,3));
//		
		//Change Advanced golem recipe to require a pure brain
		if (ThaumicExploration.brainsGolem) {
	        InfusionRecipe recipe = (InfusionRecipe) ConfigResearch.recipes.get("AdvancedGolem");
	        ConfigResearch.recipes.remove(ConfigResearch.recipes.get("AdvancedGolem"));
	        ItemStack[] components = {new ItemStack(Item.redstone), new ItemStack(Item.glowstone), new ItemStack(Item.gunpowder), new ItemStack(ConfigBlocks.blockJar, 1, 0), new ItemStack(ThaumicExploration.pureZombieBrain) };
	        recipe.components = components;
	        InfusionRecipe recipe2 = ThaumcraftApi.addInfusionCraftingRecipe("ADVANCEDGOLEM", recipe.recipeOutput, recipe.instability, recipe.aspects, recipe.recipeInput, recipe.components);
			ConfigResearch.recipes.put("AdvancedGolem", recipe2);
		}
		






//		registerResearchItemI("ENHANCEDRUNICARMOR","EnhancedRunicArmorHelm", new ItemStack(ThaumicExploration.enhancedHelmetRunic), 3, new AspectList().add(Aspect.ARMOR, 5).add(Aspect.MAGIC, 20).add(Aspect.ENERGY, 20), new ItemStack(ConfigItems.itemHelmetRunic, 1, 32767), new ItemStack[] { new ItemStack(Item.helmetDiamond, 1, 32767), new ItemStack(ConfigItems.itemResource, 1, 15), new ItemStack(ConfigItems.itemResource, 1, 7), new ItemStack(ConfigItems.itemResource, 1, 15)});
//	    registerResearchItemI("ENHANCEDRUNICARMOR","EnhancedRunicArmorBoots", new ItemStack(ThaumicExploration.enhancedBootsRunic), 3, new AspectList().add(Aspect.ARMOR, 5).add(Aspect.MAGIC, 20).add(Aspect.ENERGY, 20), new ItemStack(ConfigItems.itemBootsRunic, 1, 32767), new ItemStack[] { new ItemStack(Item.bootsDiamond, 1, 32767), new ItemStack(ConfigItems.itemResource, 1, 15), new ItemStack(ConfigItems.itemBootsRobe, 1, 32767), new ItemStack(ConfigItems.itemResource, 1, 15)});
//	    registerResearchItemI("ENHANCEDRUNICARMOR","EnhancedRunicArmorChest", new ItemStack(ThaumicExploration.enhancedChestRunic), 4, new AspectList().add(Aspect.ARMOR, 15).add(Aspect.MAGIC, 30).add(Aspect.ENERGY, 30), new ItemStack(ConfigItems.itemChestRunic, 1, 32767), new ItemStack[] { new ItemStack(Item.plateDiamond, 1, 32767), new ItemStack(ConfigItems.itemResource, 1, 15), new ItemStack(ConfigItems.itemChestRobe, 1, 32767), new ItemStack(ConfigItems.itemResource, 1, 15)});
//	    registerResearchItemI("ENHANCEDRUNICARMOR","EnhancedRunicArmorLegs", new ItemStack(ThaumicExploration.enhancedLegsRunic), 4, new AspectList().add(Aspect.ARMOR, 10).add(Aspect.MAGIC, 25).add(Aspect.ENERGY, 25), new ItemStack(ConfigItems.itemLegsRunic, 1, 32767), new ItemStack[] { new ItemStack(Item.legsDiamond, 1, 32767), new ItemStack(ConfigItems.itemResource, 1, 15), new ItemStack(ConfigItems.itemLegsRobe, 1, 32767), new ItemStack(ConfigItems.itemResource, 1, 15)});
//		
	    registerResearchItemIU("RBCU1", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)1), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.ENERGY, 32), new ItemStack(ThaumicExploration.runicBootsComet), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.redstone), new ItemStack(ConfigItems.itemShard.itemID, 1, 1), new ItemStack(ConfigItems.itemShard.itemID, 1, 1), new ItemStack(ConfigItems.itemShard.itemID, 1, 1) });
		registerResearchItemIU("RBCU2", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)2), 3, new AspectList().add(Aspect.MAGIC, 32).add(Aspect.ARMOR, 16), new ItemStack(ThaumicExploration.runicBootsComet), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.flint), new ItemStack(ConfigItems.itemShard.itemID, 1, 4), new ItemStack(ConfigItems.itemShard.itemID, 1, 4), new ItemStack(ConfigItems.itemShard.itemID, 1, 4) });
		registerResearchItemIU("RBCU3", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)3), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.AIR, 16).add(Aspect.MOTION, 16), new ItemStack(ThaumicExploration.runicBootsComet), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.gunpowder), new ItemStack(ConfigItems.itemShard.itemID, 1, 5), new ItemStack(ConfigItems.itemShard.itemID, 1, 5), new ItemStack(ConfigItems.itemShard.itemID, 1, 5) });
		registerResearchItemIU("RBCU4", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)4), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.LIFE, 16).add(Aspect.HEAL, 16), new ItemStack(ThaumicExploration.runicBootsComet), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.appleGold), new ItemStack(ConfigItems.itemShard.itemID, 1, 2), new ItemStack(ConfigItems.itemShard.itemID, 1, 2), new ItemStack(ConfigItems.itemShard.itemID, 1, 2) });
		registerResearchItemIU("RBCU5", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)5), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.STONE, 16).add(Aspect.METAL, 16), new ItemStack(ThaumicExploration.runicBootsComet), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.ingotIron), new ItemStack(ConfigItems.itemShard.itemID, 1, 3), new ItemStack(ConfigItems.itemShard.itemID, 1, 3), new ItemStack(ConfigItems.itemShard.itemID, 1, 3) });
		registerResearchItemIU("RBCU6", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)6), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.ENERGY, 16).add(Aspect.CRYSTAL, 16), new ItemStack(ThaumicExploration.runicBootsComet), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Block.glass), new ItemStack(ConfigItems.itemShard.itemID, 1, 0), new ItemStack(ConfigItems.itemShard.itemID, 1, 0), new ItemStack(ConfigItems.itemShard.itemID, 1, 0) });
		
		registerResearchItemIU("RBMU1", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)1), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.ENERGY, 32), new ItemStack(ThaumicExploration.runicBootsMeteor), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.redstone), new ItemStack(ConfigItems.itemShard.itemID, 1, 1), new ItemStack(ConfigItems.itemShard.itemID, 1, 1), new ItemStack(ConfigItems.itemShard.itemID, 1, 1) });
		registerResearchItemIU("RBMU2", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)2), 3, new AspectList().add(Aspect.MAGIC, 32).add(Aspect.ARMOR, 16), new ItemStack(ThaumicExploration.runicBootsMeteor), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.flint), new ItemStack(ConfigItems.itemShard.itemID, 1, 4), new ItemStack(ConfigItems.itemShard.itemID, 1, 4), new ItemStack(ConfigItems.itemShard.itemID, 1, 4) });
		registerResearchItemIU("RBMU3", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)3), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.AIR, 16).add(Aspect.MOTION, 16), new ItemStack(ThaumicExploration.runicBootsMeteor), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.gunpowder), new ItemStack(ConfigItems.itemShard.itemID, 1, 5), new ItemStack(ConfigItems.itemShard.itemID, 1, 5), new ItemStack(ConfigItems.itemShard.itemID, 1, 5) });
		registerResearchItemIU("RBMU4", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)4), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.LIFE, 16).add(Aspect.HEAL, 16), new ItemStack(ThaumicExploration.runicBootsMeteor), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.appleGold), new ItemStack(ConfigItems.itemShard.itemID, 1, 2), new ItemStack(ConfigItems.itemShard.itemID, 1, 2), new ItemStack(ConfigItems.itemShard.itemID, 1, 2) });
		registerResearchItemIU("RBMU5", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)5), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.STONE, 16).add(Aspect.METAL, 16), new ItemStack(ThaumicExploration.runicBootsMeteor), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.ingotIron), new ItemStack(ConfigItems.itemShard.itemID, 1, 3), new ItemStack(ConfigItems.itemShard.itemID, 1, 3), new ItemStack(ConfigItems.itemShard.itemID, 1, 3) });
		registerResearchItemIU("RBMU6", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)6), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.ENERGY, 16).add(Aspect.CRYSTAL, 16), new ItemStack(ThaumicExploration.runicBootsMeteor), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Block.glass), new ItemStack(ConfigItems.itemShard.itemID, 1, 0), new ItemStack(ConfigItems.itemShard.itemID, 1, 0), new ItemStack(ConfigItems.itemShard.itemID, 1, 0) });
		
//		ItemStack result;
//		
//		result = new ItemStack(ThaumicExploration.enhancedHelmetRunic2);
//		result.setTagInfo("upgrade2", new NBTTagByte("upgrade2", (byte)1));
//		registerResearchItemIUI("RAUE1_0", "RUNICARMORUPGRADES", result, 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.ENERGY, 32), new ItemStack(ThaumicExploration.enhancedHelmetRunic), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.redstone), new ItemStack(ConfigItems.itemShard.itemID, 1, 1), new ItemStack(ConfigItems.itemShard.itemID, 1, 1), new ItemStack(ConfigItems.itemShard.itemID, 1, 1) });
//		result = new ItemStack(ThaumicExploration.enhancedChestRunic2);
//		result.setTagInfo("upgrade2", new NBTTagByte("upgrade2", (byte)1));
//		registerResearchItemIUI("RAUE1_1", "RUNICARMORUPGRADES", result, 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.ENERGY, 32), new ItemStack(ThaumicExploration.enhancedChestRunic), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.redstone), new ItemStack(ConfigItems.itemShard.itemID, 1, 1), new ItemStack(ConfigItems.itemShard.itemID, 1, 1), new ItemStack(ConfigItems.itemShard.itemID, 1, 1) });
//		result = new ItemStack(ThaumicExploration.enhancedLegsRunic2);
//		result.setTagInfo("upgrade2", new NBTTagByte("upgrade2", (byte)1));
//		registerResearchItemIUI("RAUE1_2", "RUNICARMORUPGRADES", result, 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.ENERGY, 32), new ItemStack(ThaumicExploration.enhancedLegsRunic), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.redstone), new ItemStack(ConfigItems.itemShard.itemID, 1, 1), new ItemStack(ConfigItems.itemShard.itemID, 1, 1), new ItemStack(ConfigItems.itemShard.itemID, 1, 1) });
//		result = new ItemStack(ThaumicExploration.enhancedBootsRunic2);
//		result.setTagInfo("upgrade2", new NBTTagByte("upgrade2", (byte)1));
//		registerResearchItemIUI("RAUE1_3", "RUNICARMORUPGRADES", result, 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.ENERGY, 32), new ItemStack(ThaumicExploration.enhancedBootsRunic), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.redstone), new ItemStack(ConfigItems.itemShard.itemID, 1, 1), new ItemStack(ConfigItems.itemShard.itemID, 1, 1), new ItemStack(ConfigItems.itemShard.itemID, 1, 1) });
//		
//		result = new ItemStack(ThaumicExploration.enhancedHelmetRunic2);
//		result.setTagInfo("upgrade2", new NBTTagByte("upgrade2", (byte)2));
//		registerResearchItemIUI("RAUE2_0", "RUNICARMORUPGRADES", result, 3, new AspectList().add(Aspect.MAGIC, 32).add(Aspect.ARMOR, 16), new ItemStack(ThaumicExploration.enhancedHelmetRunic), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.flint), new ItemStack(ConfigItems.itemShard.itemID, 1, 4), new ItemStack(ConfigItems.itemShard.itemID, 1, 4), new ItemStack(ConfigItems.itemShard.itemID, 1, 4) });
//		result = new ItemStack(ThaumicExploration.enhancedHelmetRunic2);
//		result.setTagInfo("upgrade2", new NBTTagByte("upgrade2", (byte)2));
//		registerResearchItemIUI("RAUE2_1", "RUNICARMORUPGRADES", result, 3, new AspectList().add(Aspect.MAGIC, 32).add(Aspect.ARMOR, 16), new ItemStack(ThaumicExploration.enhancedChestRunic), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.flint), new ItemStack(ConfigItems.itemShard.itemID, 1, 4), new ItemStack(ConfigItems.itemShard.itemID, 1, 4), new ItemStack(ConfigItems.itemShard.itemID, 1, 4) });
//		result = new ItemStack(ThaumicExploration.enhancedHelmetRunic2);
//		result.setTagInfo("upgrade2", new NBTTagByte("upgrade2", (byte)2));
//		registerResearchItemIUI("RAUE2_2", "RUNICARMORUPGRADES", result, 3, new AspectList().add(Aspect.MAGIC, 32).add(Aspect.ARMOR, 16), new ItemStack(ThaumicExploration.enhancedLegsRunic), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.flint), new ItemStack(ConfigItems.itemShard.itemID, 1, 4), new ItemStack(ConfigItems.itemShard.itemID, 1, 4), new ItemStack(ConfigItems.itemShard.itemID, 1, 4) });
//		result = new ItemStack(ThaumicExploration.enhancedHelmetRunic2);
//		result.setTagInfo("upgrade2", new NBTTagByte("upgrade2", (byte)2));
//		registerResearchItemIUI("RAUE2_3", "RUNICARMORUPGRADES", result, 3, new AspectList().add(Aspect.MAGIC, 32).add(Aspect.ARMOR, 16), new ItemStack(ThaumicExploration.enhancedBootsRunic), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.flint), new ItemStack(ConfigItems.itemShard.itemID, 1, 4), new ItemStack(ConfigItems.itemShard.itemID, 1, 4), new ItemStack(ConfigItems.itemShard.itemID, 1, 4) });
//		
//		result = new ItemStack(ThaumicExploration.enhancedHelmetRunic2);
//		result.setTagInfo("upgrade2", new NBTTagByte("upgrade2", (byte)3));
//		registerResearchItemIUI("RAUE3_0", "RUNICARMORUPGRADES", result, 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.AIR, 16).add(Aspect.MOTION, 16), new ItemStack(ThaumicExploration.enhancedHelmetRunic), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.gunpowder), new ItemStack(ConfigItems.itemShard.itemID, 1, 5), new ItemStack(ConfigItems.itemShard.itemID, 1, 5), new ItemStack(ConfigItems.itemShard.itemID, 1, 5) });
//		result = new ItemStack(ThaumicExploration.enhancedHelmetRunic2);
//		result.setTagInfo("upgrade2", new NBTTagByte("upgrade2", (byte)3));
//		registerResearchItemIUI("RAUE3_1", "RUNICARMORUPGRADES", result, 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.AIR, 16).add(Aspect.MOTION, 16), new ItemStack(ThaumicExploration.enhancedChestRunic), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.gunpowder), new ItemStack(ConfigItems.itemShard.itemID, 1, 5), new ItemStack(ConfigItems.itemShard.itemID, 1, 5), new ItemStack(ConfigItems.itemShard.itemID, 1, 5) });
//		result = new ItemStack(ThaumicExploration.enhancedHelmetRunic2);
//		result.setTagInfo("upgrade2", new NBTTagByte("upgrade2", (byte)3));
//		registerResearchItemIUI("RAUE3_2", "RUNICARMORUPGRADES", result, 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.AIR, 16).add(Aspect.MOTION, 16), new ItemStack(ThaumicExploration.enhancedLegsRunic), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.gunpowder), new ItemStack(ConfigItems.itemShard.itemID, 1, 5), new ItemStack(ConfigItems.itemShard.itemID, 1, 5), new ItemStack(ConfigItems.itemShard.itemID, 1, 5) });
//		result = new ItemStack(ThaumicExploration.enhancedHelmetRunic2);
//		result.setTagInfo("upgrade2", new NBTTagByte("upgrade2", (byte)3));
//		registerResearchItemIUI("RAUE3_3", "RUNICARMORUPGRADES", result, 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.AIR, 16).add(Aspect.MOTION, 16), new ItemStack(ThaumicExploration.enhancedBootsRunic), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.gunpowder), new ItemStack(ConfigItems.itemShard.itemID, 1, 5), new ItemStack(ConfigItems.itemShard.itemID, 1, 5), new ItemStack(ConfigItems.itemShard.itemID, 1, 5) });
//		
//		result = new ItemStack(ThaumicExploration.enhancedHelmetRunic2);
//		result.setTagInfo("upgrade2", new NBTTagByte("upgrade2", (byte)4));
//		registerResearchItemIUI("RAUE4_0", "RUNICARMORUPGRADES", result, 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.LIFE, 16).add(Aspect.HEAL, 16), new ItemStack(ThaumicExploration.enhancedHelmetRunic), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.appleGold), new ItemStack(ConfigItems.itemShard.itemID, 1, 2), new ItemStack(ConfigItems.itemShard.itemID, 1, 2), new ItemStack(ConfigItems.itemShard.itemID, 1, 2) });
//		result = new ItemStack(ThaumicExploration.enhancedHelmetRunic2);
//		result.setTagInfo("upgrade2", new NBTTagByte("upgrade2", (byte)4));
//		registerResearchItemIUI("RAUE4_1", "RUNICARMORUPGRADES", result, 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.LIFE, 16).add(Aspect.HEAL, 16), new ItemStack(ThaumicExploration.enhancedChestRunic), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.appleGold), new ItemStack(ConfigItems.itemShard.itemID, 1, 2), new ItemStack(ConfigItems.itemShard.itemID, 1, 2), new ItemStack(ConfigItems.itemShard.itemID, 1, 2) });
//		result = new ItemStack(ThaumicExploration.enhancedHelmetRunic2);
//		result.setTagInfo("upgrade2", new NBTTagByte("upgrade2", (byte)4));
//		registerResearchItemIUI("RAUE4_2", "RUNICARMORUPGRADES", result, 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.LIFE, 16).add(Aspect.HEAL, 16), new ItemStack(ThaumicExploration.enhancedLegsRunic), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.appleGold), new ItemStack(ConfigItems.itemShard.itemID, 1, 2), new ItemStack(ConfigItems.itemShard.itemID, 1, 2), new ItemStack(ConfigItems.itemShard.itemID, 1, 2) });
//		result = new ItemStack(ThaumicExploration.enhancedHelmetRunic2);
//		result.setTagInfo("upgrade2", new NBTTagByte("upgrade2", (byte)4));
//		registerResearchItemIUI("RAUE4_3", "RUNICARMORUPGRADES", result, 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.LIFE, 16).add(Aspect.HEAL, 16), new ItemStack(ThaumicExploration.enhancedBootsRunic), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.appleGold), new ItemStack(ConfigItems.itemShard.itemID, 1, 2), new ItemStack(ConfigItems.itemShard.itemID, 1, 2), new ItemStack(ConfigItems.itemShard.itemID, 1, 2) });
//		
//		result = new ItemStack(ThaumicExploration.enhancedHelmetRunic2);
//		result.setTagInfo("upgrade2", new NBTTagByte("upgrade2", (byte)5));
//		registerResearchItemIUI("RAUE5_0", "RUNICARMORUPGRADES", result, 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.STONE, 16).add(Aspect.METAL, 16), new ItemStack(ThaumicExploration.enhancedHelmetRunic), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.ingotIron), new ItemStack(ConfigItems.itemShard.itemID, 1, 3), new ItemStack(ConfigItems.itemShard.itemID, 1, 3), new ItemStack(ConfigItems.itemShard.itemID, 1, 3) });
//		result = new ItemStack(ThaumicExploration.enhancedHelmetRunic2);
//		result.setTagInfo("upgrade2", new NBTTagByte("upgrade2", (byte)5));
//		registerResearchItemIUI("RAUE5_1", "RUNICARMORUPGRADES", result, 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.STONE, 16).add(Aspect.METAL, 16), new ItemStack(ThaumicExploration.enhancedChestRunic), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.ingotIron), new ItemStack(ConfigItems.itemShard.itemID, 1, 3), new ItemStack(ConfigItems.itemShard.itemID, 1, 3), new ItemStack(ConfigItems.itemShard.itemID, 1, 3) });
//		result = new ItemStack(ThaumicExploration.enhancedHelmetRunic2);
//		result.setTagInfo("upgrade2", new NBTTagByte("upgrade2", (byte)5));
//		registerResearchItemIUI("RAUE5_2", "RUNICARMORUPGRADES", result, 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.STONE, 16).add(Aspect.METAL, 16), new ItemStack(ThaumicExploration.enhancedLegsRunic), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.ingotIron), new ItemStack(ConfigItems.itemShard.itemID, 1, 3), new ItemStack(ConfigItems.itemShard.itemID, 1, 3), new ItemStack(ConfigItems.itemShard.itemID, 1, 3) });
//		result = new ItemStack(ThaumicExploration.enhancedHelmetRunic2);
//		result.setTagInfo("upgrade2", new NBTTagByte("upgrade2", (byte)5));
//		registerResearchItemIUI("RAUE5_3", "RUNICARMORUPGRADES", result, 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.STONE, 16).add(Aspect.METAL, 16), new ItemStack(ThaumicExploration.enhancedBootsRunic), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.ingotIron), new ItemStack(ConfigItems.itemShard.itemID, 1, 3), new ItemStack(ConfigItems.itemShard.itemID, 1, 3), new ItemStack(ConfigItems.itemShard.itemID, 1, 3) });
////			
//		result = new ItemStack(ThaumicExploration.enhancedHelmetRunic2);
//		result.setTagInfo("upgrade2", new NBTTagByte("upgrade2", (byte)6));
//		registerResearchItemIUI("RAUE6_0", "RUNICARMORUPGRADES", result, 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.ENERGY, 16).add(Aspect.CRYSTAL, 16), new ItemStack(ThaumicExploration.enhancedHelmetRunic), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Block.glass), new ItemStack(ConfigItems.itemShard.itemID, 1, 0), new ItemStack(ConfigItems.itemShard.itemID, 1, 0), new ItemStack(ConfigItems.itemShard.itemID, 1, 0) });
//		result = new ItemStack(ThaumicExploration.enhancedHelmetRunic2);
//		result.setTagInfo("upgrade2", new NBTTagByte("upgrade2", (byte)6));
//		registerResearchItemIUI("RAUE6_1", "RUNICARMORUPGRADES", result, 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.ENERGY, 16).add(Aspect.CRYSTAL, 16), new ItemStack(ThaumicExploration.enhancedChestRunic), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Block.glass), new ItemStack(ConfigItems.itemShard.itemID, 1, 0), new ItemStack(ConfigItems.itemShard.itemID, 1, 0), new ItemStack(ConfigItems.itemShard.itemID, 1, 0) });
//		result = new ItemStack(ThaumicExploration.enhancedHelmetRunic2);
//		result.setTagInfo("upgrade2", new NBTTagByte("upgrade2", (byte)6));
//		registerResearchItemIUI("RAUE6_2", "RUNICARMORUPGRADES", result, 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.ENERGY, 16).add(Aspect.CRYSTAL, 16), new ItemStack(ThaumicExploration.enhancedLegsRunic), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Block.glass), new ItemStack(ConfigItems.itemShard.itemID, 1, 0), new ItemStack(ConfigItems.itemShard.itemID, 1, 0), new ItemStack(ConfigItems.itemShard.itemID, 1, 0) });
//		result = new ItemStack(ThaumicExploration.enhancedHelmetRunic2);
//		result.setTagInfo("upgrade2", new NBTTagByte("upgrade2", (byte)6));
//		registerResearchItemIUI("RAUE6_3", "RUNICARMORUPGRADES", result, 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.ENERGY, 16).add(Aspect.CRYSTAL, 16), new ItemStack(ThaumicExploration.enhancedBootsRunic), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Block.glass), new ItemStack(ConfigItems.itemShard.itemID, 1, 0), new ItemStack(ConfigItems.itemShard.itemID, 1, 0), new ItemStack(ConfigItems.itemShard.itemID, 1, 0) });
//		
//		
//		registerResearchItemIU("RAUE21_0", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)1), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.ENERGY, 32), new ItemStack(ThaumicExploration.enhancedHelmetRunic2), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.redstone), new ItemStack(ConfigItems.itemShard.itemID, 1, 1), new ItemStack(ConfigItems.itemShard.itemID, 1, 1), new ItemStack(ConfigItems.itemShard.itemID, 1, 1) });
//		registerResearchItemIU("RAUE21_1", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)1), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.ENERGY, 32), new ItemStack(ThaumicExploration.enhancedChestRunic2), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.redstone), new ItemStack(ConfigItems.itemShard.itemID, 1, 1), new ItemStack(ConfigItems.itemShard.itemID, 1, 1), new ItemStack(ConfigItems.itemShard.itemID, 1, 1) });
//		registerResearchItemIU("RAUE21_2", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)1), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.ENERGY, 32), new ItemStack(ThaumicExploration.enhancedLegsRunic2), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.redstone), new ItemStack(ConfigItems.itemShard.itemID, 1, 1), new ItemStack(ConfigItems.itemShard.itemID, 1, 1), new ItemStack(ConfigItems.itemShard.itemID, 1, 1) });
//		registerResearchItemIU("RAUE21_3", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)1), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.ENERGY, 32), new ItemStack(ThaumicExploration.enhancedBootsRunic2), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.redstone), new ItemStack(ConfigItems.itemShard.itemID, 1, 1), new ItemStack(ConfigItems.itemShard.itemID, 1, 1), new ItemStack(ConfigItems.itemShard.itemID, 1, 1) });
//		registerResearchItemIU("RAUE22_0", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)2), 3, new AspectList().add(Aspect.MAGIC, 32).add(Aspect.ARMOR, 16), new ItemStack(ThaumicExploration.enhancedHelmetRunic2), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.flint), new ItemStack(ConfigItems.itemShard.itemID, 1, 4), new ItemStack(ConfigItems.itemShard.itemID, 1, 4), new ItemStack(ConfigItems.itemShard.itemID, 1, 4) });
//		registerResearchItemIU("RAUE22_1", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)2), 3, new AspectList().add(Aspect.MAGIC, 32).add(Aspect.ARMOR, 16), new ItemStack(ThaumicExploration.enhancedChestRunic2), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.flint), new ItemStack(ConfigItems.itemShard.itemID, 1, 4), new ItemStack(ConfigItems.itemShard.itemID, 1, 4), new ItemStack(ConfigItems.itemShard.itemID, 1, 4) });
//		registerResearchItemIU("RAUE22_2", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)2), 3, new AspectList().add(Aspect.MAGIC, 32).add(Aspect.ARMOR, 16), new ItemStack(ThaumicExploration.enhancedLegsRunic2), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.flint), new ItemStack(ConfigItems.itemShard.itemID, 1, 4), new ItemStack(ConfigItems.itemShard.itemID, 1, 4), new ItemStack(ConfigItems.itemShard.itemID, 1, 4) });
//		registerResearchItemIU("RAUE22_3", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)2), 3, new AspectList().add(Aspect.MAGIC, 32).add(Aspect.ARMOR, 16), new ItemStack(ThaumicExploration.enhancedBootsRunic2), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.flint), new ItemStack(ConfigItems.itemShard.itemID, 1, 4), new ItemStack(ConfigItems.itemShard.itemID, 1, 4), new ItemStack(ConfigItems.itemShard.itemID, 1, 4) });
//		registerResearchItemIU("RAUE23_0", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)3), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.AIR, 16).add(Aspect.MOTION, 16), new ItemStack(ThaumicExploration.enhancedHelmetRunic2), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.gunpowder), new ItemStack(ConfigItems.itemShard.itemID, 1, 5), new ItemStack(ConfigItems.itemShard.itemID, 1, 5), new ItemStack(ConfigItems.itemShard.itemID, 1, 5) });
//		registerResearchItemIU("RAUE23_1", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)3), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.AIR, 16).add(Aspect.MOTION, 16), new ItemStack(ThaumicExploration.enhancedChestRunic2), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.gunpowder), new ItemStack(ConfigItems.itemShard.itemID, 1, 5), new ItemStack(ConfigItems.itemShard.itemID, 1, 5), new ItemStack(ConfigItems.itemShard.itemID, 1, 5) });
//		registerResearchItemIU("RAUE23_2", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)3), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.AIR, 16).add(Aspect.MOTION, 16), new ItemStack(ThaumicExploration.enhancedLegsRunic2), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.gunpowder), new ItemStack(ConfigItems.itemShard.itemID, 1, 5), new ItemStack(ConfigItems.itemShard.itemID, 1, 5), new ItemStack(ConfigItems.itemShard.itemID, 1, 5) });
//		registerResearchItemIU("RAUE23_3", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)3), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.AIR, 16).add(Aspect.MOTION, 16), new ItemStack(ThaumicExploration.enhancedBootsRunic2), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.gunpowder), new ItemStack(ConfigItems.itemShard.itemID, 1, 5), new ItemStack(ConfigItems.itemShard.itemID, 1, 5), new ItemStack(ConfigItems.itemShard.itemID, 1, 5) });
//		registerResearchItemIU("RAUE24_0", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)4), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.LIFE, 16).add(Aspect.HEAL, 16), new ItemStack(ThaumicExploration.enhancedHelmetRunic2), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.appleGold), new ItemStack(ConfigItems.itemShard.itemID, 1, 2), new ItemStack(ConfigItems.itemShard.itemID, 1, 2), new ItemStack(ConfigItems.itemShard.itemID, 1, 2) });
//		registerResearchItemIU("RAUE24_1", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)4), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.LIFE, 16).add(Aspect.HEAL, 16), new ItemStack(ThaumicExploration.enhancedChestRunic2), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.appleGold), new ItemStack(ConfigItems.itemShard.itemID, 1, 2), new ItemStack(ConfigItems.itemShard.itemID, 1, 2), new ItemStack(ConfigItems.itemShard.itemID, 1, 2) });
//		registerResearchItemIU("RAUE24_2", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)4), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.LIFE, 16).add(Aspect.HEAL, 16), new ItemStack(ThaumicExploration.enhancedLegsRunic2), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.appleGold), new ItemStack(ConfigItems.itemShard.itemID, 1, 2), new ItemStack(ConfigItems.itemShard.itemID, 1, 2), new ItemStack(ConfigItems.itemShard.itemID, 1, 2) });
//		registerResearchItemIU("RAUE24_3", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)4), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.LIFE, 16).add(Aspect.HEAL, 16), new ItemStack(ThaumicExploration.enhancedBootsRunic2), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.appleGold), new ItemStack(ConfigItems.itemShard.itemID, 1, 2), new ItemStack(ConfigItems.itemShard.itemID, 1, 2), new ItemStack(ConfigItems.itemShard.itemID, 1, 2) });
//		registerResearchItemIU("RAUE25_0", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)5), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.STONE, 16).add(Aspect.METAL, 16), new ItemStack(ThaumicExploration.enhancedHelmetRunic2), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.ingotIron), new ItemStack(ConfigItems.itemShard.itemID, 1, 3), new ItemStack(ConfigItems.itemShard.itemID, 1, 3), new ItemStack(ConfigItems.itemShard.itemID, 1, 3) });
//		registerResearchItemIU("RAUE25_1", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)5), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.STONE, 16).add(Aspect.METAL, 16), new ItemStack(ThaumicExploration.enhancedChestRunic2), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.ingotIron), new ItemStack(ConfigItems.itemShard.itemID, 1, 3), new ItemStack(ConfigItems.itemShard.itemID, 1, 3), new ItemStack(ConfigItems.itemShard.itemID, 1, 3) });
//		registerResearchItemIU("RAUE25_2", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)5), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.STONE, 16).add(Aspect.METAL, 16), new ItemStack(ThaumicExploration.enhancedLegsRunic2), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.ingotIron), new ItemStack(ConfigItems.itemShard.itemID, 1, 3), new ItemStack(ConfigItems.itemShard.itemID, 1, 3), new ItemStack(ConfigItems.itemShard.itemID, 1, 3) });
//		registerResearchItemIU("RAUE25_3", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)5), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.STONE, 16).add(Aspect.METAL, 16), new ItemStack(ThaumicExploration.enhancedBootsRunic2), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Item.ingotIron), new ItemStack(ConfigItems.itemShard.itemID, 1, 3), new ItemStack(ConfigItems.itemShard.itemID, 1, 3), new ItemStack(ConfigItems.itemShard.itemID, 1, 3) });
//		registerResearchItemIU("RAUE26_0", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)6), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.ENERGY, 16).add(Aspect.CRYSTAL, 16), new ItemStack(ThaumicExploration.enhancedHelmetRunic2), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Block.glass), new ItemStack(ConfigItems.itemShard.itemID, 1, 0), new ItemStack(ConfigItems.itemShard.itemID, 1, 0), new ItemStack(ConfigItems.itemShard.itemID, 1, 0) });
//		registerResearchItemIU("RAUE26_1", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)6), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.ENERGY, 16).add(Aspect.CRYSTAL, 16), new ItemStack(ThaumicExploration.enhancedChestRunic2), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Block.glass), new ItemStack(ConfigItems.itemShard.itemID, 1, 0), new ItemStack(ConfigItems.itemShard.itemID, 1, 0), new ItemStack(ConfigItems.itemShard.itemID, 1, 0) });
//		registerResearchItemIU("RAUE26_2", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)6), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.ENERGY, 16).add(Aspect.CRYSTAL, 16), new ItemStack(ThaumicExploration.enhancedLegsRunic2), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Block.glass), new ItemStack(ConfigItems.itemShard.itemID, 1, 0), new ItemStack(ConfigItems.itemShard.itemID, 1, 0), new ItemStack(ConfigItems.itemShard.itemID, 1, 0) });
//		registerResearchItemIU("RAUE26_3", "RUNICARMORUPGRADES", new NBTTagByte("upgrade", (byte)6), 3, new AspectList().add(Aspect.MAGIC, 16).add(Aspect.ENERGY, 16).add(Aspect.CRYSTAL, 16), new ItemStack(ThaumicExploration.enhancedBootsRunic2), new ItemStack[] { new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Block.glass), new ItemStack(ConfigItems.itemShard.itemID, 1, 0), new ItemStack(ConfigItems.itemShard.itemID, 1, 0), new ItemStack(ConfigItems.itemShard.itemID, 1, 0) });
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
        
        registerResearchItem("DREAMCATCHER", "DREAMCATCHER", new ItemStack(ThaumicExploration.charmNoTaint), new AspectList().add(Aspect.ORDER, 15).add(Aspect.ENTROPY, 15),
                "GPG", "PSP", "FPF",
                'G', new ItemStack(ConfigItems.itemResource,1, 11),
                'P', new ItemStack(ConfigBlocks.blockWoodenDevice,1,6),
                'S', new ItemStack(ConfigItems.itemResource,1, 12),
                'F', new ItemStack(Item.feather));
        
        registerResearchItem("DREAMCATCHER2", "DREAMCATCHER", new ItemStack(ThaumicExploration.charmNoTaint), new AspectList().add(Aspect.ORDER, 15).add(Aspect.ENTROPY, 15),
                "GPG", "PSP", "FPF",
                'G', "gooTaint",
                'P', new ItemStack(ConfigBlocks.blockWoodenDevice,1,6),
                'S', new ItemStack(ConfigItems.itemResource,1, 12),
                'F', "tendrilTaint");
        
        registerResearchItem("UNCHARGEDSOJOURNER", "CAP_SOJOURNER", new ItemStack(ThaumicExploration.sojournerCapUncharged), new AspectList().add(Aspect.AIR, 6).add(Aspect.ENTROPY, 6).add(Aspect.ORDER, 6),
                "ABA", "A A",
                'A', new ItemStack(Item.dyePowder,1,4),
                'B', new ItemStack(Item.diamond));
        
        registerResearchItem("UNCHARGEDMECHANIST", "CAP_MECHANIST", new ItemStack(ThaumicExploration.mechanistCapUncharged), new AspectList().add(Aspect.FIRE, 6).add(Aspect.ENTROPY, 6).add(Aspect.ORDER, 6),
                "AAA", "ABA", " C ",
                'A', new ItemStack(Item.redstone),
                'B', new ItemStack(Block.pistonBase),
                'C', new ItemStack(Item.redstoneRepeater));
        
        registerResearchItem("ROD_AMBER_staff","ROD_AMBER_staff", new ItemStack(ThaumicExploration.amberStaffCore), new AspectList().add(Aspect.ORDER, ((WandRod)WandRod.rods.get("AMBER_staff")).getCraftCost()),
                "  S", " G ", "G  ",
                'S', new ItemStack(ConfigItems.itemResource, 1, 15),
                'G', new ItemStack(ThaumicExploration.amberCore));
	}

	private static void initCraftingRecipes() {
		for (int i=0;i<16;i++) {
			GameRegistry.addRecipe(new ItemStack(ThaumicExploration.blankSeal, 1, i), " X ", "XZX", " X ", 'X', new ItemStack(ConfigItems.itemResource,1,4), 'Z', new ItemStack(Item.dyePowder,1,i));
		}
		
		registerCraftingRecipe("BLANKSEAL", new ItemStack(ThaumicExploration.blankSeal, 1, 32767), " X ", "XZX", " X ", 'X', new ItemStack(ConfigItems.itemResource,1,4), 'Z', new ItemStack(Item.dyePowder,1,32767));
		
	}

	private static void initCrucibleRecipes() {
		registerCrucibleRecipe("FLESHCURE","FLESHCURE", new ItemStack(Item.leather,2), new ItemStack(Item.rottenFlesh), new AspectList().add(Aspect.FLESH, 2).add(Aspect.CLOTH, 1));
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
		  NecromanticAltarAPI.addNecroRecipeWithThaumnomiconDisplay("ROD_NECROMANCER", new NecromanticRecipe("NECROWAND", new ItemStack(ThaumicExploration.necroCore), new ItemStack[]{new ItemStack(Item.skull,1,1),new ItemStack(Item.bone),new ItemStack(Item.bone),new ItemStack(ConfigItems.itemResource,1,14),new ItemStack(ConfigItems.itemResource,1,14)}, 25));
		  NecromanticAltarAPI.addEnergyToItem(Item.rottenFlesh.itemID,1);
		  NecromanticAltarAPI.addEnergyToItem(Item.bone.itemID,2);
		  NecromanticAltarAPI.addEnergyToItem(ConfigItems.itemResource.itemID,5,4);
		  NecromanticAltarAPI.addEnergyToItem(Item.skull.itemID,1,5);
		  NecromanticAltarAPI.addEnergyToItem(Item.appleRed.itemID,1);
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
	
	private static void registerResearchItemIU(String research, String name,NBTTagByte nbtTagByte, int instability, AspectList aspects, ItemStack input, ItemStack... stuff) {
		InfusionRecipe recipe = ThaumcraftApi.addInfusionCraftingRecipe(name, nbtTagByte, instability, aspects, input, stuff);	
        ConfigResearch.recipes.put(research, recipe);
	}

	private static void registerResearchItemC(String string, List<Object> asList) {
		ConfigResearch.recipes.put(string, asList);
		
	}
}
