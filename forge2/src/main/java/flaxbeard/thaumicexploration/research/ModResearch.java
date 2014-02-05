package flaxbeard.thaumicexploration.research;

import java.util.List;

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
import thaumcraft.common.config.ConfigResearch;
import cpw.mods.fml.common.Loader;
import flaxbeard.thaumicexploration.ThaumicExploration;
import flaxbeard.thaumicexploration.integration.TTIntegration;

public final class ModResearch {

    public static void initResearch() {
    	registerResearchPages();
        ResearchItem research;
        

        
        //Curing
        research = new TXResearchItem("FLESHCURE", "ALCHEMY", new AspectList().add(Aspect.ARMOR, 1).add(Aspect.FLESH, 2).add(Aspect.EXCHANGE, 1), -4, 0, 2, new ItemStack(Item.rottenFlesh)).setParents("TALLOW").setConcealed().registerResearchItem();
        research.setPages(new ResearchPage("1"), cruciblePage("FLESHCURE"));
        if (Loader.isModLoaded("ThaumicTinkerer")) {
        	 research = new TXResearchItem("BRAINCURE", "ARTIFICE", new AspectList().add(Aspect.MIND, 2).add(Aspect.ORDER, 1).add(Aspect.MAN, 1).add(Aspect.UNDEAD, 1), -4, 10, 4, new ItemStack(ThaumicExploration.pureZombieBrain)).setParents("JARBRAIN","FLESHCURE","INFUSION").setConcealed().registerResearchItem();
             research.setPages(new ResearchPage("1"), infusionPage("BRAINCURE"));
             if (ThaumicExploration.allowThinkTank) {
	             research = new TXResearchItem("THINKTANK", "ARTIFICE", new AspectList().add(Aspect.UNDEAD, 1).add(Aspect.MIND, 2).add(Aspect.SENSES, 1), -3, 12, 5, new ItemStack(ThaumicExploration.thinkTankJar)).setParents("BRAINCURE","RESEARCHER2").setParentsHidden("INFUSION").setConcealed().registerResearchItem();
	             research.setPages(new ResearchPage("1"), infusionPage("THINKTANK"), new ResearchPage("2"), constructPage("BUILDTHINKTANK"));
             }
        }
        else
        {
        	 research = new TXResearchItem("BRAINCURE", "ARTIFICE", new AspectList().add(Aspect.MIND, 2).add(Aspect.ORDER, 1).add(Aspect.MAN, 1).add(Aspect.UNDEAD, 1), -3, 10, 4, new ItemStack(ThaumicExploration.pureZombieBrain)).setParents("JARBRAIN","FLESHCURE").setParentsHidden("INFUSION").setConcealed().registerResearchItem();
             research.setPages(new ResearchPage("1"),infusionPage("BRAINCURE") );
             if (ThaumicExploration.allowThinkTank) {
	             research = new TXResearchItem("THINKTANK", "ARTIFICE", new AspectList().add(Aspect.UNDEAD, 1).add(Aspect.MIND, 2).add(Aspect.SENSES, 1), -2, 12, 5, new ItemStack(ThaumicExploration.thinkTankJar)).setParents("BRAINCURE","RESEARCHER2").setParentsHidden("INFUSION").setConcealed().registerResearchItem();
	             research.setPages(new ResearchPage("1"), infusionPage("THINKTANK"), new ResearchPage("2"), constructPage("BUILDTHINKTANK"));
             }
        }
        
        //Misc
        if (ThaumicExploration.allowUrn) {
	        if (Loader.isModLoaded("ThaumicTinkerer")) {
		        research = new TXResearchItem("URN", "ARTIFICE", new AspectList().add(Aspect.MAGIC, 1).add(Aspect.VOID, 1).add(Aspect.WATER, 2), -7,9 , 3, new ItemStack(ThaumicExploration.everfullUrn)).setParents("INFUSION").setParentsHidden("ARCANEEAR").setConcealed().registerResearchItem();
		        research.setPages(new ResearchPage("1"), infusionPage("URN") );
	        }
	        else
	        {
		        research = new TXResearchItem("URN", "ARTIFICE", new AspectList().add(Aspect.MAGIC, 1).add(Aspect.VOID, 1).add(Aspect.WATER, 2), -2,3 , 3, new ItemStack(ThaumicExploration.everfullUrn)).setParents("INFUSION").setParentsHidden("ARCANEEAR").setConcealed().registerResearchItem();
		        research.setPages(new ResearchPage("1"), infusionPage("URN") );
	        }
    	}
        
        if (ThaumicExploration.allowReplication) {
        	if (Loader.isModLoaded("ThaumicTinkerer")) {
            	  research = new TXResearchItem("REPLICATOR", "ALCHEMY", new AspectList().add(Aspect.CRAFT, 2).add(Aspect.MECHANISM, 1).add(Aspect.ORDER, 1), 5,4 , 5, new ItemStack(ThaumicExploration.replicator)).setParents(TTIntegration.keyRepairer()).setParentsHidden("INFUSION").setConcealed().registerResearchItem();
                  research.setPages(new ResearchPage("1"), new ResearchPage("2"), infusionPage("REPLICATOR") );
        	}
        	else
        	{
          	  research = new TXResearchItem("REPLICATOR", "ALCHEMY", new AspectList().add(Aspect.CRAFT, 2).add(Aspect.MECHANISM, 1).add(Aspect.ORDER, 1), 6,2 , 5, new ItemStack(ThaumicExploration.replicator)).setParents("TUBES").setParentsHidden("INFUSION").setConcealed().registerResearchItem();
              research.setPages(new ResearchPage("1"), new ResearchPage("2"), infusionPage("REPLICATOR") );
        	}
           
        }

        if (ThaumicExploration.allowCrucSouls) {
	        research = new TXResearchItem("CRUCSOULS", "ALCHEMY", new AspectList().add(Aspect.DEATH, 2).add(Aspect.HUNGER, 1).add(Aspect.SOUL, 1), 5,1 , 5, new ItemStack(ThaumicExploration.crucibleSouls)).setParents("DISTILESSENTIA").setParentsHidden("BRAINCURE","INFUSION").setConcealed().registerResearchItem();
	        research.setPages(new ResearchPage("1"), new ResearchPage("2"), infusionPage("CRUCSOULS") );
        }
        if (ThaumicExploration.allowFood) {
	        research = new TXResearchItem("TALISMANFOOD", "ARTIFICE", new AspectList().add(Aspect.HUNGER, 2).add(Aspect.FLESH, 1).add(Aspect.CROP, 1), -7,1 , 3, new ItemStack(ThaumicExploration.talismanFood)).setParents("INFUSION","TALLOW").setConcealed().registerResearchItem();
	        research.setPages(new ResearchPage("1"), infusionPage("TALISMANFOOD"));
        }
        
        if (ThaumicExploration.allowBoots) {
	        research = new TXResearchItem("METEORBOOTS", "ARTIFICE", new AspectList().add(Aspect.FIRE, 2).add(Aspect.ENERGY, 1).add(Aspect.TRAVEL, 1).add(Aspect.FLIGHT, 1), 1,8 , 4, new ItemStack(ThaumicExploration.bootsMeteor)).setParents("BOOTSTRAVELLER","FOCUSFIRE").setParentsHidden("INFUSION").setConcealed().registerResearchItem();
	        research.setPages(new ResearchPage("1"), infusionPage("METEORBOOTS"));
	        
	        research = new TXResearchItem("COMETBOOTS", "ARTIFICE", new AspectList().add(Aspect.WATER, 1).add(Aspect.ICE, 2).add(Aspect.TRAVEL, 1).add(Aspect.MOTION, 1), 3,7 , 4, new ItemStack(ThaumicExploration.bootsComet)).setParents("BOOTSTRAVELLER","FOCUSFROST").setParentsHidden("INFUSION").setConcealed().registerResearchItem();
	        research.setPages(new ResearchPage("1"), infusionPage("COMETBOOTS"));
        }
        
        //Taintiturgy
        ResourceLocation taint = new ResourceLocation("thaumicexploration:textures/tabs/taintResearch2.png");
        research = new TXResearchItem("TAINTBASICS", "TAINT", new AspectList(), 0, 0, 0, taint).setAutoUnlock().setStub().setRound().registerResearchItem();
        research.setPages(new ResearchPage("1"), new ResearchPage("2"));
        research = new TXResearchItem("DREAMCATCHER", "TAINT", new AspectList().add(Aspect.MIND, 1).add(Aspect.TOOL, 1).add(Aspect.TAINT, 2), 2,2 , 3, new ItemStack(ThaumicExploration.charmNoTaint)).setParents("TAINTBASICS").setConcealed().registerResearchItem();
        research.setPages(new ResearchPage("1"), arcaneRecipePage("DREAMCATCHER"));
        
        if (ThaumicExploration.taintBloom) {
	        ResearchCategories.researchCategories.get("ALCHEMY").research.remove("ETHEREALBLOOM");
	        research = new ResearchItem("ETHEREALBLOOM", "TAINT", new AspectList().add(Aspect.MAGIC, 1).add(Aspect.PLANT, 2).add(Aspect.HEAL, 1).add(Aspect.TAINT, 1), -2,2, 4, new ItemStack(Config.blockCustomPlantId, 1, 4)).setPages(new ResearchPage[] { new ResearchPage("tc.research_page.ETHEREALBLOOM.1"), new ResearchPage((CrucibleRecipe)ConfigResearch.recipes.get("EtherealBloom")), new ResearchPage("tc.research_page.ETHEREALBLOOM.2") }).setConcealed().setParents(new String[] { "CRUCIBLE", "INFUSION", "TAINTBASICS" }).registerResearchItem();
        }
        
        if (Loader.isModLoaded("ThaumicTinkerer")) {
	        research = new TXResearchItem("ENCHBINDING", TTIntegration.enchantCategory(), new AspectList().add(Aspect.TRAP, 2).add(Aspect.ENTROPY, 1).add(Aspect.TRAVEL, 1), 6, -1, 2, new ResourceLocation("thaumicexploration:textures/tabs/binding.png")).setParents(TTIntegration.keyEnchanter()).setConcealed().registerResearchItem();
	        research.setPages(new ResearchPage("1"), infusionEnchantPage("ENCHBINDING"));
	        research = new TXResearchItem("ENCHNIGHTVISION", TTIntegration.enchantCategory(), new AspectList().add(Aspect.SENSES, 2).add(Aspect.DARKNESS, 1).add(Aspect.LIGHT, 1), 5, 1, 2, new ResourceLocation("thaumicexploration:textures/tabs/nightVision.png")).setParents(TTIntegration.keyEnchanter()).setConcealed().registerResearchItem();
	        research.setPages(new ResearchPage("1"), infusionEnchantPage("ENCHNIGHTVISION"));
	        research = new TXResearchItem("ENCHDISARM", TTIntegration.enchantCategory(), new AspectList().add(Aspect.SLIME, 2).add(Aspect.TRAP, 1).add(Aspect.WEAPON, 1), 3, 2, 2, new ResourceLocation("thaumicexploration:textures/tabs/disarm.png")).setParents(TTIntegration.keyEnchanter()).setConcealed().registerResearchItem();
	        research.setPages(new ResearchPage("1"), infusionEnchantPage("ENCHDISARM"));
        }
        else
        {
        	if (Loader.isModLoaded("ForbiddenMagic")) {
    	        research = new TXResearchItem("ENCHBINDING", "ARTIFICE", new AspectList().add(Aspect.TRAP, 2).add(Aspect.ENTROPY, 1).add(Aspect.TRAVEL, 1), -8, 9, 2, new ResourceLocation("thaumicexploration:textures/tabs/binding.png")).setParents("INFUSIONENCHANTMENT").setConcealed().registerResearchItem();
    	        research.setPages(new ResearchPage("1"), infusionEnchantPage("ENCHBINDING"));
        	}
        	else
        	{
    	        research = new TXResearchItem("ENCHBINDING", "ARTIFICE", new AspectList().add(Aspect.TRAP, 2).add(Aspect.ENTROPY, 1).add(Aspect.TRAVEL, 1), -8, 13, 2, new ResourceLocation("thaumicexploration:textures/tabs/binding.png")).setParents("INFUSIONENCHANTMENT").setConcealed().registerResearchItem();
    	        research.setPages(new ResearchPage("1"), infusionEnchantPage("ENCHBINDING"));
        	}
	        research = new TXResearchItem("ENCHNIGHTVISION", "ARTIFICE", new AspectList().add(Aspect.SENSES, 2).add(Aspect.DARKNESS, 1).add(Aspect.LIGHT, 1), -6, 13, 2, new ResourceLocation("thaumicexploration:textures/tabs/nightVision.png")).setParents("INFUSIONENCHANTMENT").setConcealed().registerResearchItem();
	        research.setPages(new ResearchPage("1"), infusionEnchantPage("ENCHNIGHTVISION"));
	        research = new TXResearchItem("ENCHDISARM", "ARTIFICE", new AspectList().add(Aspect.SLIME, 2).add(Aspect.TRAP, 1).add(Aspect.WEAPON, 1), -4, 13, 2, new ResourceLocation("thaumicexploration:textures/tabs/disarm.png")).setParents("INFUSIONENCHANTMENT").setConcealed().registerResearchItem();
	        research.setPages(new ResearchPage("1"), infusionEnchantPage("ENCHDISARM"));
        }
        
        //Wandcraft
        research = new TXResearchItem("WANDAMBER", "THAUMATURGY", new AspectList().add(Aspect.TOOL, 1).add(Aspect.AURA, 2).add(Aspect.TRAP, 1).add(Aspect.MAGIC, 2), -11, 2, 4, new ItemStack(ThaumicExploration.amberCore)).setParents("ROD_obsidian","ROD_reed","ROD_blaze","ROD_ice","ROD_quartz","ROD_bone").setConcealed().registerResearchItem();
        research.setPages(new ResearchPage("1"), infusionPage("WANDAMBER"));
        
        //Sealery
        ResourceLocation chestSeal = new ResourceLocation("thaumicexploration:textures/tabs/chestSeals.png");
        ResourceLocation jarSeal = new ResourceLocation("thaumicexploration:textures/tabs/jarSeals.png");
        if (ThaumicExploration.allowBoundInventories) {
        	research = new TXResearchItem("CHESTSEAL", "ARTIFICE", new AspectList().add(Aspect.ELDRITCH, 2).add(Aspect.TRAP, 1).add(Aspect.TRAVEL, 2).add(Aspect.VOID,1), 5, 0, 4, chestSeal).setConcealed().setParentsHidden("MIRROR","TALLOW").registerResearchItem();
        	research.setPages(new ResearchPage("1"),recipePage("BLANKSEAL"),cruciblePage("CHESTSEAL"));
        	research = new TXResearchItem("JARSEAL", "ARTIFICE", new AspectList().add(Aspect.ELDRITCH, 2).add(Aspect.TRAP, 1).add(Aspect.MAGIC, 1).add(Aspect.TRAVEL,2), 7, 0, 4, jarSeal).setConcealed().setParents("CHESTSEAL").registerResearchItem();
        	research.setPages(new ResearchPage("1"),cruciblePage("JARSEAL"));
        }
        
    }
    
	private static void registerResearchPages() {
		ResourceLocation background = new ResourceLocation("thaumicexploration:textures/gui/taintBackground.png");
		ResearchCategories.registerCategory("TAINT", new ResourceLocation("thaumicexploration:textures/tabs/taintResearch.png"), background);
        
		
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