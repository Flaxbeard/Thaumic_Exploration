package flaxbeard.thaumicexploration.research;

import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.InfusionEnchantmentRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigResearch;
import cpw.mods.fml.common.Loader;
import flaxbeard.thaumicexploration.ThaumicExploration;

public final class ModResearch {

    public static void initResearch() {
    	registerResearchPages();
        ResearchItem research;
        

        
        //Curing
        research = new TXResearchItem("FLESHCURE", "ALCHEMY", new AspectList().add(Aspect.ARMOR, 1).add(Aspect.FLESH, 3).add(Aspect.EXCHANGE, 2), -4, 0, 1, new ItemStack(Items.rotten_flesh)).setParents("TALLOW").setHidden().setSecondary().setItemTriggers(new ItemStack[] {new ItemStack(Items.rotten_flesh,1,32767)}).registerResearchItem();
        research.setPages(new ResearchPage("1"), cruciblePage("FLESHCURE"));
        
        research = new TXResearchItem("FLOATCANDLE", "ALCHEMY", new AspectList().add(Aspect.MAGIC, 1).add(Aspect.FLESH, 2).add(Aspect.AIR, 3), -4, -2, 1, new ItemStack(ThaumicExploration.floatCandle)).setParents("TALLOW").setConcealed().setSecondary().registerResearchItem();
        research.setPages(new ResearchPage("1"), arcaneRecipePage("FLOATCANDLE"));
      
    	 research = new TXResearchItem("BRAINCURE", "ARTIFICE", new AspectList().add(Aspect.MIND, 4).add(Aspect.ORDER, 5).add(Aspect.MAN, 3).add(Aspect.UNDEAD, 3), -3, 10, 1, new ItemStack(ThaumicExploration.pureZombieBrain)).setParents("JARBRAIN","FLESHCURE").setParentsHidden("INFUSION").setConcealed().registerResearchItem().setSecondary();
         research.setPages(new ResearchPage("1"),infusionPage("BRAINCURE") );
         if (ThaumicExploration.allowThinkTank) {
             research = new TXResearchItem("THINKTANK", "ARTIFICE", new AspectList().add(Aspect.UNDEAD, 4).add(Aspect.MIND, 8).add(Aspect.SENSES, 6).add(Aspect.GREED, 2), -2, 12, 3, new ItemStack(ThaumicExploration.thinkTankJar)).setParents("BRAINCURE","RESEARCHER2").setParentsHidden("INFUSION").setConcealed().registerResearchItem();
             research.setPages(new ResearchPage("1"), infusionPage("THINKTANK"), new ResearchPage("2"), constructPage("BUILDTHINKTANK"));
         }
        
        //Misc
        if (ThaumicExploration.allowUrn) {
		        research = new TXResearchItem("URN", "ALCHEMY", new AspectList().add(Aspect.MAGIC, 4).add(Aspect.VOID, 2).add(Aspect.WATER, 5), -3,2 , 1, new ItemStack(ThaumicExploration.everfullUrn)).setParents("INFUSION","CRUCIBLE").setParentsHidden("ARCANEEAR").setConcealed().registerResearchItem();
		        if (Loader.isModLoaded("Botania")) {
		        	research.setPages(new ResearchPage("1B"), infusionPage("URN") );
		        }
		        else
		        {
		        	research.setPages(new ResearchPage("1"), infusionPage("URN") );
		        }
        }
        
        if (ThaumicExploration.allowReplication) {
          	  research = new TXResearchItem("REPLICATOR", "ALCHEMY", new AspectList().add(Aspect.CRAFT, 10).add(Aspect.MECHANISM, 10).add(Aspect.ORDER, 6), 6,2, 3, new ItemStack(ThaumicExploration.replicator)).setParents("TUBES").setParentsHidden("INFUSION").setConcealed().registerResearchItem();
              research.setPages(new ResearchPage("1"), new ResearchPage("2"), infusionPage("REPLICATOR") );
        }

        if (ThaumicExploration.allowCrucSouls) {
	        research = new TXResearchItem("CRUCSOULS", "ALCHEMY", new AspectList().add(Aspect.DEATH, 7).add(Aspect.HUNGER, 7).add(Aspect.SOUL, 8), 5,1, 3, new ItemStack(ThaumicExploration.crucibleSouls)).setParents("DISTILESSENTIA").setParentsHidden("BRAINCURE","INFUSION").setConcealed().registerResearchItem();
	        research.setPages(new ResearchPage("1"), new ResearchPage("2"), infusionPage("CRUCSOULS") );
        }
        if (ThaumicExploration.allowFood) {
	        research = new TXResearchItem("TALISMANFOOD", "ARTIFICE", new AspectList().add(Aspect.HUNGER, 5).add(Aspect.FLESH, 4).add(Aspect.CROP, 4),-2,-1, 2, new ItemStack(ThaumicExploration.talismanFood)).setParents("BASICARTIFACE").setParentsHidden("INFUSION","TALLOW").setConcealed().registerResearchItem();
	        research.setPages(new ResearchPage("1"), infusionPage("TALISMANFOOD"));
        }
        
        if (ThaumicExploration.allowBoots) {
	        research = new TXResearchItem("METEORBOOTS", "ARTIFICE", new AspectList().add(Aspect.FIRE, 5).add(Aspect.ENERGY, 5).add(Aspect.TRAVEL, 10).add(Aspect.FLIGHT, 5), 5,7 , 2, new ItemStack(ThaumicExploration.bootsMeteor)).setParents("BOOTSTRAVELLER","FOCUSFIRE").setParentsHidden("INFUSION").setConcealed().registerResearchItem();
	        research.setPages(new ResearchPage("1"), infusionPage("METEORBOOTS"));
	        
	        research = new TXResearchItem("COMETBOOTS", "ARTIFICE", new AspectList().add(Aspect.WATER, 5).add(Aspect.COLD, 5).add(Aspect.TRAVEL, 10).add(Aspect.MOTION, 5), 3,8 , 2, new ItemStack(ThaumicExploration.bootsComet)).setParents("BOOTSTRAVELLER","FOCUSFROST").setParentsHidden("INFUSION").setConcealed().registerResearchItem();
	        research.setPages(new ResearchPage("1"), infusionPage("COMETBOOTS"));
	        ResourceLocation runicBoots = new ResourceLocation("thaumicexploration:textures/tabs/runicBoots.png");
	        research = new TXResearchItem("RUNICBOOTS", "ARTIFICE", new AspectList().add(Aspect.ARMOR, 5).add(Aspect.WATER, 8).add(Aspect.FIRE, 8).add(Aspect.TRAVEL, 10), 3,6, 1, runicBoots).setParents("METEORBOOTS","COMETBOOTS","RUNICBOOTSTRAVELLER").setParentsHidden("INFUSION").setConcealed().setSecondary().registerResearchItem();
	        research.setPages(new ResearchPage("1"), infusionPage("RUNICBOOTSMETEOR"),infusionPage("RUNICBOOTSMETEOR2"), infusionPage("RUNICBOOTSCOMET"),infusionPage("RUNICBOOTSCOMET2"));
        }
        
//        research = new TXResearchItem("ENHANCEDRUNICARMOR", "ARTIFICE", new AspectList().add(Aspect.ARMOR, 12).add(Aspect.ENERGY, 7).add(Aspect.MAGIC, 7).add(Aspect.AIR, 7).add(Aspect.MIND, 7).add(Aspect.ORDER, 5), 7,4, 3, new ItemStack(ThaumicExploration.enhancedChestRunic)).setParents("RUNICARMOR","RUNICARMORUPGRADES").setParentsHidden("RUNICBOOTS","INFUSION","RUNICGOGGLES","RUNICBOOTSTRAVELLER").setConcealed().registerResearchItem();
//        research.setPages(new ResearchPage("1"),new ResearchPage("2"),infusionPage("EnhancedRunicArmorHelm"),infusionPage("EnhancedRunicArmorChest"),infusionPage("EnhancedRunicArmorLegs"),infusionPage("EnhancedRunicArmorBoots"));
        
        if (ThaumicExploration.allowSojourner) {
        	research = new TXResearchItem("CAP_SOJOURNER", "THAUMATURGY", new AspectList().add(Aspect.EXCHANGE, 5).add(Aspect.ENERGY, 5).add(Aspect.AURA, 8).add(Aspect.GREED, 5).add(Aspect.TOOL, 3), 6, 6, 2, new ItemStack(ThaumicExploration.sojournerCap)).setParents("CAP_thaumium").setParentsHidden("WANDPED").setConcealed().registerResearchItem();
        	research.setPages(new ResearchPage("1"),arcaneRecipePage("UNCHARGEDSOJOURNER"),infusionPage("CAP_SOJOURNER"));
        }
        
        if (ThaumicExploration.allowMechanist) {
        	research = new TXResearchItem("CAP_MECHANIST", "THAUMATURGY", new AspectList().add(Aspect.MECHANISM, 5).add(Aspect.ENERGY, 5).add(Aspect.AURA, 8).add(Aspect.GREED, 5).add(Aspect.TOOL, 3), 8, 5, 2, new ItemStack(ThaumicExploration.mechanistCap)).setParents("CAP_thaumium").setParentsHidden("NODETAPPER2").setConcealed().registerResearchItem();
        	research.setPages(new ResearchPage("1"),arcaneRecipePage("UNCHARGEDMECHANIST"),infusionPage("CAP_MECHANIST"));
        }
        
        //Taintiturgy
        if (ThaumicExploration.allowTainturgy) {
	        ResourceLocation taint = new ResourceLocation("thaumicexploration:textures/tabs/taintResearch2.png");
	        research = new TXResearchItem("TAINTBASICS", "TAINT", new AspectList(), 0, 0, 0, taint).setAutoUnlock().setStub().setRound().registerResearchItem();
	        research.setPages(new ResearchPage("1"), new ResearchPage("2"));
	        research = new TXResearchItem("DREAMCATCHER", "TAINT", new AspectList().add(Aspect.MIND, 5).add(Aspect.TOOL, 5).add(Aspect.TAINT, 6), 2,2, 2, new ItemStack(ThaumicExploration.charmNoTaint)).setParents("TAINTBASICS").setHidden().setAspectTriggers(new Aspect[] { Aspect.TAINT }).registerResearchItem();
	        research.setPages(new ResearchPage("1"), arcaneRecipePage("DREAMCATCHER"));
	        
	        if (ThaumicExploration.taintBloom) {
		        ResearchCategories.researchCategories.get("ALCHEMY").research.remove("ETHEREALBLOOM");
		        research = new ResearchItem("ETHEREALBLOOM", "ALCHEMY", new AspectList().add(Aspect.MAGIC, 1).add(Aspect.PLANT, 8).add(Aspect.HEAL, 5).add(Aspect.TAINT, 8), -2, -3, 2, new ItemStack(ConfigBlocks.blockCustomPlant, 1, 4)).setPages(new ResearchPage[] { new ResearchPage("tc.research_page.ETHEREALBLOOM.1"), cruciblePage("EtherealBloom"), new ResearchPage("tc.research_page.ETHEREALBLOOM.2") }).setHidden().setAspectTriggers(new Aspect[] { Aspect.TAINT }).setConcealed().setParents(new String[] { "CRUCIBLE", "INFUSION" }).registerResearchItem();
	        }
        }
        
        //Necromancy
//        taint = new ResourceLocation("thaumicexploration:textures/tabs/necromancyResearch.png");
//        research = new TXResearchItem("NECROINFUSION", "NECROMANCY", new AspectList(), -5, 0, 0, taint).registerResearchItem();
//        research.setPages(new ResearchPage("1"), new ResearchPage("2"), new ResearchPage("3"), infusionPage("NECROINFUSION"));
//        research = new TXResearchItem("ROD_NECROMANCER", "NECROMANCY", new AspectList(), -2, -2, 0, new ItemStack(ThaumicExploration.necroCore)).setConcealed().setParents("NECROINFUSION").registerResearchItem();
//        research.setPages(new ResearchPage("1"), infusionPage("ROD_NECROMANCER"));
//        research = new TXResearchItem("NECROENERGY", "NECROMANCY", new AspectList(), -7, 0, 0, new ResourceLocation("thaumicexploration", "textures/tabs/necroAspectResearch.png")).setConcealed().setParents("NECROINFUSION").registerResearchItem().setRound().setAutoUnlock();
//        int numItems = (int) Math.ceil(NecromanticAltarAPI.necroEnergyItems.size() / 4.0F);
//        ArrayList<ResearchPage> test = new ArrayList<ResearchPage>();
//        test.add(new ResearchPage("1"));
//        for (int i = 0; i<numItems;i++) {
//        	test.add(new ResearchPage("NE#"+i));
//        }
//        research.setPages(test.toArray(new ResearchPage[0]));
//        
//        {
        	if (ThaumicExploration.allowEnchants) {
    	        //research = new TXResearchItem("ENCHBINDING", "ARTIFICE", new AspectList().add(Aspect.TRAP, 6).add(Aspect.ENTROPY, 5).add(Aspect.TRAVEL, 3), -8, 9, 1, new ResourceLocation("thaumicexploration:textures/tabs/binding.png")).setParents("INFUSIONENCHANTMENT").setConcealed().setSecondary().registerResearchItem();
    	       // research.setPages(new ResearchPage("1"), infusionEnchantPage("ENCHBINDING"));
    	        research = new TXResearchItem("ENCHBINDING", "ARTIFICE", new AspectList().add(Aspect.TRAP, 6).add(Aspect.ENTROPY, 5).add(Aspect.TRAVEL, 3), -8, 13, 1, new ResourceLocation("thaumicexploration:textures/tabs/binding.png")).setParents("INFUSIONENCHANTMENT").setConcealed().setSecondary().registerResearchItem();
    	        research.setPages(new ResearchPage("1"), infusionEnchantPage("ENCHBINDING"));
        	
		        research = new TXResearchItem("ENCHNIGHTVISION", "ARTIFICE", new AspectList().add(Aspect.SENSES, 6).add(Aspect.DARKNESS, 2).add(Aspect.LIGHT, 6), -6, 13, 1, new ResourceLocation("thaumicexploration:textures/tabs/nightVision.png")).setParents("INFUSIONENCHANTMENT").setConcealed().setSecondary().registerResearchItem();
		        research.setPages(new ResearchPage("1"), infusionEnchantPage("ENCHNIGHTVISION"));
		        research = new TXResearchItem("ENCHDISARM", "ARTIFICE", new AspectList().add(Aspect.SLIME, 6).add(Aspect.TRAP, 4).add(Aspect.WEAPON, 4), -4, 13, 1, new ResourceLocation("thaumicexploration:textures/tabs/disarm.png")).setParents("INFUSIONENCHANTMENT").setConcealed().setSecondary().registerResearchItem();
		        research.setPages(new ResearchPage("1"), infusionEnchantPage("ENCHDISARM"));
        	}
        
        //Wandcraft
        research = new TXResearchItem("ROD_AMBER", "THAUMATURGY", new AspectList().add(Aspect.TOOL, 5).add(Aspect.AURA, 8).add(Aspect.TRAP, 5).add(Aspect.MAGIC, 8), -11, 2, 2, new ItemStack(ThaumicExploration.amberCore)).setParents("ROD_obsidian","ROD_reed","ROD_blaze","ROD_ice","ROD_quartz","ROD_bone").setConcealed().registerResearchItem();
        research.setPages(new ResearchPage("1"), infusionPage("ROD_AMBER"));
        research = new TXResearchItem("ROD_TRANSMUTATION", "THAUMATURGY", new AspectList().add(Aspect.TOOL, 8).add(Aspect.MAGIC, 8).add(Aspect.EXCHANGE, 12), -1, 3, 2, new ItemStack(ThaumicExploration.transmutationCore)).setParents("ROD_greatwood").setConcealed().registerResearchItem();
        research.setPages(new ResearchPage("1"), infusionPage("ROD_TRANSMUTATION"));
        research = new TXResearchItem("ROD_AMBER_staff", "THAUMATURGY", new AspectList().add(Aspect.TOOL, 5).add(Aspect.AURA, 8).add(Aspect.TRAP, 5).add(Aspect.MAGIC, 8), -12, 2, 2, new ItemStack(ThaumicExploration.amberStaffCore)).setParents("ROD_AMBER").setParentsHidden("ROD_greatwood_staff").setConcealed().setSecondary().registerResearchItem();
        research.setPages(new ResearchPage("1"), arcaneRecipePage("ROD_AMBER_staff"));
        if (ThaumicExploration.breadWand) {
            research = new TXResearchItem("ROD_BREAD", "THAUMATURGY", new AspectList().add(Aspect.MAGIC, 5).add(Aspect.CROP, 3).add(Aspect.HUNGER, 4).add(Aspect.HARVEST, 3), -11, 0, 1, new ItemStack(ThaumicExploration.breadCore)).setParents("ROD_AMBER").setConcealed().registerResearchItem().setSecondary();
            research.setPages(new ResearchPage("1"), infusionPage("ROD_BREAD"));
        }
        
        //Sealery
        ResourceLocation chestSeal = new ResourceLocation("thaumicexploration:textures/tabs/chestSeals.png");
        ResourceLocation jarSeal = new ResourceLocation("thaumicexploration:textures/tabs/jarSeals.png");
        if (ThaumicExploration.allowBoundInventories) {
        	research = new TXResearchItem("CHESTSEAL", "ARTIFICE", new AspectList().add(Aspect.ELDRITCH, 5).add(Aspect.TRAP, 8).add(Aspect.TRAVEL, 5).add(Aspect.VOID,3), 6, 2, 2, chestSeal).setConcealed().setParentsHidden("MIRROR","TALLOW").registerResearchItem();
        	research.setPages(new ResearchPage("1"),recipePage("BLANKSEAL"),cruciblePage("CHESTSEAL"));
        	research = new TXResearchItem("JARSEAL", "ARTIFICE", new AspectList().add(Aspect.ELDRITCH, 5).add(Aspect.TRAP, 8).add(Aspect.MAGIC, 5).add(Aspect.TRAVEL,5), 8, 2, 1, jarSeal).setConcealed().setParents("CHESTSEAL").registerResearchItem().setSecondary();
        	research.setPages(new ResearchPage("1"),cruciblePage("JARSEAL"));
        }
        
    }
    
	private static void registerResearchPages() {
		ResourceLocation background = new ResourceLocation("thaumicexploration:textures/gui/taintBackground.png");
		ResearchCategories.registerCategory("TAINT", new ResourceLocation("thaumicexploration:textures/tabs/taintResearch.png"), background);
//		ResourceLocation background2 = new ResourceLocation("thaumcraft", "textures/gui/gui_researchback.png");
//		ResearchCategories.registerCategory("NECROMANCY", new ResourceLocation("thaumicexploration:textures/tabs/necromancyResearch.png"), background2);
		
	}
	
    private static ResearchPage recipePage(String name) {
        return new ResearchPage((IRecipe) ConfigResearch.recipes.get(name));
    }
	
	private static ResearchPage cruciblePage(String name) {
        return new ResearchPage((CrucibleRecipe) ConfigResearch.recipes.get(name));
	}
	
	private static ResearchPage infusionPage(String name) {
        return new ResearchPage((InfusionRecipe) ConfigResearch.recipes.get(name));
	}
	
	private static ResearchPage infusionEnchantPage(String name) {
        return new ResearchPage((InfusionEnchantmentRecipe) ConfigResearch.recipes.get(name));
	}
	
	private static ResearchPage constructPage(String name) {
        return new ResearchPage((List)ConfigResearch.recipes.get(name));
	}

    private static ResearchPage arcaneRecipePage(String name) {
        return new ResearchPage((IArcaneRecipe) ConfigResearch.recipes.get(name));
    }
}