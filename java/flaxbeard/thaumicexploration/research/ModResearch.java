package flaxbeard.thaumicexploration.research;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
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
import thaumcraft.api.research.ResearchCategoryList;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigResearch;
import cpw.mods.fml.common.Loader;
import flaxbeard.thaumicexploration.ThaumicExploration;

public final class ModResearch {
	
	public static ItemStack getOriginalItem(String name, String category) {
		return ((ResearchCategoryList)ResearchCategories.researchCategories.get(category)).research.get(name).icon_item;
	}
	
	public static ResourceLocation getOriginalGraphic(String name, String category) {
		return ((ResearchCategoryList)ResearchCategories.researchCategories.get(category)).research.get(name).icon_resource;
	}


    public static void initResearch() {
    	registerResearchPages();
        ResearchItem research;
        
        research = new FauxResearchItem("TXBASICARTIFACE","TX","BASICARTIFACE","ARTIFICE",3,1,getOriginalItem("BASICARTIFACE","ARTIFICE")).registerResearchItem();
        research = new TXResearchItem("DISCOUNTRINGS", "TX", new AspectList().add(Aspect.MAGIC, 5).add(Aspect.CRYSTAL, 2), 1, 3, 1, new ItemStack(ThaumicExploration.discountRing,1,2)).setParents("BASICARTIFACE","TXBASICARTIFACE").setConcealed().setSecondary().registerResearchItem();
        ArrayList<IArcaneRecipe> scer = new ArrayList();
        for (int i = 0; i<6; i++) {
        	scer.add((IArcaneRecipe) ConfigResearch.recipes.get("DISCOUNTRINGS"+i));
        }
        research.setPages(new ResearchPage("1"), new ResearchPage((IArcaneRecipe[])scer.toArray(new IArcaneRecipe[0])));
        
        //Curing
        research = new FauxResearchItem("TXTALLOW","TX","TALLOW","ALCHEMY",-6,-11,getOriginalItem("TALLOW","ALCHEMY")).registerResearchItem();
        research = new FauxResearchItem("TXINFUSION","TX","INFUSION","ARTIFICE",2,-7,getOriginalItem("INFUSION","ARTIFICE")).registerResearchItem();
        research = new TXResearchItem("FLESHCURE", "TX", new AspectList().add(Aspect.ARMOR, 1).add(Aspect.FLESH, 3).add(Aspect.EXCHANGE, 2), -4, -10, 1, new ItemStack(Items.rotten_flesh)).setParents("TXTALLOW").setParentsHidden("TALLOW").setHidden().setSecondary().setItemTriggers(new ItemStack[] {new ItemStack(Items.rotten_flesh,1,32767)}).registerResearchItem();
        research.setPages(new ResearchPage("1"), cruciblePage("FLESHCURE"));
        
        research = new TXResearchItem("FLOATCANDLE", "TX", new AspectList().add(Aspect.MAGIC, 1).add(Aspect.FLESH, 2).add(Aspect.AIR, 3), -7, -9, 1, new ItemStack(ThaumicExploration.floatCandle)).setParents("TXTALLOW","TALLOW").setConcealed().setSecondary().registerResearchItem();
        research.setPages(new ResearchPage("1"), arcaneRecipePage("FLOATCANDLE"));
      
    	 research = new TXResearchItem("BRAINCURE", "TX", new AspectList().add(Aspect.MIND, 4).add(Aspect.ORDER, 5).add(Aspect.MAN, 3).add(Aspect.UNDEAD, 3), -2, -8, 1, new ItemStack(ThaumicExploration.pureZombieBrain)).setParents("JARBRAIN","FLESHCURE","TXINFUSION").setParentsHidden("INFUSION").setConcealed().registerResearchItem().setSecondary();
         research.setPages(new ResearchPage("1"),infusionPage("BRAINCURE") );
         if (ThaumicExploration.allowThinkTank) {
             research = new TXResearchItem("THINKTANK", "TX", new AspectList().add(Aspect.UNDEAD, 4).add(Aspect.MIND, 8).add(Aspect.SENSES, 6).add(Aspect.GREED, 2), -1, -5, 3, new ItemStack(ThaumicExploration.thinkTankJar)).setParents("BRAINCURE","RESEARCHER2").setParentsHidden("INFUSION").setConcealed().registerResearchItem();
             research.setPages(new ResearchPage("1"), infusionPage("THINKTANK"), new ResearchPage("2"), constructPage("BUILDTHINKTANK"));
         }
        
        //Misc
        if (ThaumicExploration.allowUrn) {
		        research = new TXResearchItem("URN", "TX", new AspectList().add(Aspect.MAGIC, 4).add(Aspect.VOID, 2).add(Aspect.WATER, 5), 3,-10 , 1, new ItemStack(ThaumicExploration.everfullUrn)).setParents("INFUSION","TXINFUSION").setParentsHidden("ARCANEEAR").setConcealed().registerResearchItem();
		        if (Loader.isModLoaded("Botania")) {
		        	research.setPages(new ResearchPage("1B"), infusionPage("URN") );
		        }
		        else
		        {
		        	research.setPages(new ResearchPage("1"), infusionPage("URN") );
		        }
        }
        research = new FauxResearchItem("TXHOVERHARNESS","TX","HOVERHARNESS","ARTIFICE",2,-12,getOriginalItem("HOVERHARNESS","ARTIFICE")).registerResearchItem();
        research = new TXResearchItem("STABILIZERBELT", "TX", new AspectList().add(Aspect.ORDER, 4).add(Aspect.EARTH, 4).add(Aspect.TRAVEL, 2), 1,-10 , 1, new ItemStack(ThaumicExploration.stabilizerBelt)).setParents("INFUSION","TXINFUSION","HOVERHARNESS","TXHOVERHARNESS").setConcealed().registerResearchItem();
        research.setPages(new ResearchPage("1"), infusionPage("STABILIZERBELT") );
       
        research = new FauxResearchItem("TXDISTILESSENTIA","TX","DISTILESSENTIA","ALCHEMY",7,-6,getOriginalItem("DISTILESSENTIA","ALCHEMY")).registerResearchItem();
        if (ThaumicExploration.allowReplication) {
          	  research = new TXResearchItem("REPLICATOR", "TX", new AspectList().add(Aspect.CRAFT, 10).add(Aspect.MECHANISM, 10).add(Aspect.ORDER, 6), 4,-5, 3, new ItemStack(ThaumicExploration.replicator)).setParents("DISTILESSENTIA","TXINFUSION","TXDISTILESSENTIA").setParentsHidden("INFUSION").setConcealed().registerResearchItem();
              research.setPages(new ResearchPage("1"), new ResearchPage("2"), infusionPage("REPLICATOR") );
        }

        if (ThaumicExploration.allowCrucSouls) {
	        research = new TXResearchItem("CRUCSOULS", "TX", new AspectList().add(Aspect.DEATH, 7).add(Aspect.HUNGER, 7).add(Aspect.SOUL, 8), 5,-7, 3, new ItemStack(ThaumicExploration.crucibleSouls)).setParents("DISTILESSENTIA","TXINFUSION","TXDISTILESSENTIA").setParentsHidden("BRAINCURE","INFUSION").setConcealed().registerResearchItem();
	        research.setPages(new ResearchPage("1"), new ResearchPage("2"), infusionPage("CRUCSOULS") );
        }
        if (ThaumicExploration.allowFood) {
	        research = new TXResearchItem("TALISMANFOOD", "TX", new AspectList().add(Aspect.HUNGER, 5).add(Aspect.FLESH, 4).add(Aspect.CROP, 4),-1,-11, 2, new ItemStack(ThaumicExploration.talismanFood)).setParents("TXINFUSION","FLESHCURE").setParentsHidden("INFUSION").setConcealed().registerResearchItem();
	        research.setPages(new ResearchPage("1"), infusionPage("TALISMANFOOD"));
        }
        
        if (ThaumicExploration.allowBoots) {
            research = new FauxResearchItem("TXBOOTSTRAVELLER","TX","BOOTSTRAVELLER","ARTIFICE",1,-4,getOriginalItem("BOOTSTRAVELLER","ARTIFICE")).setParents("TXINFUSION").registerResearchItem();
	        research = new TXResearchItem("METEORBOOTS", "TX", new AspectList().add(Aspect.FIRE, 5).add(Aspect.ENERGY, 5).add(Aspect.TRAVEL, 10).add(Aspect.FLIGHT, 5), 2,-1 , 2, new ItemStack(ThaumicExploration.bootsMeteor)).setParents("TXBOOTSTRAVELLER","BOOTSTRAVELLER","FOCUSFIRE").setParentsHidden("INFUSION").setConcealed().registerResearchItem();
	        research.setPages(new ResearchPage("1"), infusionPage("METEORBOOTS"));
	        
	        research = new TXResearchItem("COMETBOOTS", "TX", new AspectList().add(Aspect.WATER, 5).add(Aspect.COLD, 5).add(Aspect.TRAVEL, 10).add(Aspect.MOTION, 5), 5,-3 , 2, new ItemStack(ThaumicExploration.bootsComet)).setParents("TXBOOTSTRAVELLER","BOOTSTRAVELLER","FOCUSFROST").setParentsHidden("INFUSION").setConcealed().registerResearchItem();
	        research.setPages(new ResearchPage("1"), infusionPage("COMETBOOTS"));
	    
        }
        
//        research = new TXResearchItem("ENHANCEDRUNICARMOR", "ARTIFICE", new AspectList().add(Aspect.ARMOR, 12).add(Aspect.ENERGY, 7).add(Aspect.MAGIC, 7).add(Aspect.AIR, 7).add(Aspect.MIND, 7).add(Aspect.ORDER, 5), 7,4, 3, new ItemStack(ThaumicExploration.enhancedChestRunic)).setParents("RUNICARMOR","RUNICARMORUPGRADES").setParentsHidden("RUNICBOOTS","INFUSION","RUNICGOGGLES","RUNICBOOTSTRAVELLER").setConcealed().registerResearchItem();
//        research.setPages(new ResearchPage("1"),new ResearchPage("2"),infusionPage("EnhancedRunicArmorHelm"),infusionPage("EnhancedRunicArmorChest"),infusionPage("EnhancedRunicArmorLegs"),infusionPage("EnhancedRunicArmorBoots"));
        if (ThaumicExploration.allowSojourner || ThaumicExploration.allowMechanist) {
        	research = new FauxResearchItem("TXCAP_thaumium","TX","CAP_thaumium","THAUMATURGY",-7,-7,getOriginalItem("CAP_thaumium","THAUMATURGY")).registerResearchItem();
        }
        if (ThaumicExploration.allowSojourner) {
        	research = new TXResearchItem("CAP_SOJOURNER", "TX", new AspectList().add(Aspect.EXCHANGE, 5).add(Aspect.ENERGY, 5).add(Aspect.AURA, 8).add(Aspect.GREED, 5).add(Aspect.TOOL, 3), -5, -8, 2, new ItemStack(ThaumicExploration.sojournerCap)).setParents("TXCAP_thaumium","CAP_thaumium").setParentsHidden("WANDPED").setConcealed().registerResearchItem();
        	research.setPages(new ResearchPage("1"),arcaneRecipePage("UNCHARGEDSOJOURNER"),infusionPage("CAP_SOJOURNER"));
        }
        
        if (ThaumicExploration.allowMechanist) {
        	research = new TXResearchItem("CAP_MECHANIST", "TX", new AspectList().add(Aspect.MECHANISM, 5).add(Aspect.ENERGY, 5).add(Aspect.AURA, 8).add(Aspect.GREED, 5).add(Aspect.TOOL, 3), -9, -6, 2, new ItemStack(ThaumicExploration.mechanistCap)).setParents("TXCAP_thaumium","CAP_thaumium").setParentsHidden("NODETAPPER2").setConcealed().registerResearchItem();
        	research.setPages(new ResearchPage("1"),arcaneRecipePage("UNCHARGEDMECHANIST"),infusionPage("CAP_MECHANIST"));
        }
        
        ResourceLocation taint = new ResourceLocation("thaumicexploration:textures/tabs/taintResearch2.png");
        research = new TXResearchItem("TAINTBASICS", "TX", new AspectList(), -9, 0, 0, taint).setAutoUnlock().setStub().setRound().registerResearchItem();
        research.setPages(new ResearchPage("1"), new ResearchPage("2"));
        if (ThaumicExploration.allowTainturgy) {
	        research = new TXResearchItem("DREAMCATCHER", "TX", new AspectList().add(Aspect.MIND, 5).add(Aspect.TOOL, 5).add(Aspect.TAINT, 6), -7,2, 2, new ItemStack(ThaumicExploration.charmNoTaint)).setParents("TAINTBASICS").setHidden().setAspectTriggers(new Aspect[] { Aspect.TAINT }).registerResearchItem();
	        research.setPages(new ResearchPage("1"), arcaneRecipePage("DREAMCATCHER")); 
        }
        research = new TXResearchItem("TENTACLERING", "TX", new AspectList().add(Aspect.TAINT, 8).add(Aspect.WEAPON, 4).add(Aspect.ARMOR, 4), -11,2, 2, new ItemStack(ThaumicExploration.tentacleRing)).setParents("TAINTBASICS").setHidden().setEntityTriggers(new String[] { "Thaumcraft.Taintacle" }).registerResearchItem();
        research.setPages(new ResearchPage("1"), infusionPage("TENTACLERING")); 
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
        		research = new FauxResearchItem("TXINFUSIONENCHANTMENT","TX","INFUSIONENCHANTMENT","ARTIFICE",-5,-6,getOriginalGraphic("INFUSIONENCHANTMENT","ARTIFICE")).registerResearchItem();
    	        //research = new TXResearchItem("ENCHBINDING", "ARTIFICE", new AspectList().add(Aspect.TRAP, 6).add(Aspect.ENTROPY, 5).add(Aspect.TRAVEL, 3), -8, 9, 1, new ResourceLocation("thaumicexploration:textures/tabs/binding.png")).setParents("INFUSIONENCHANTMENT").setConcealed().setSecondary().registerResearchItem();
    	       // research.setPages(new ResearchPage("1"), infusionEnchantPage("ENCHBINDING"));
    	        research = new TXResearchItem("ENCHBINDING", "TX", new AspectList().add(Aspect.TRAP, 6).add(Aspect.ENTROPY, 5).add(Aspect.TRAVEL, 3), -7, -4, 1, new ResourceLocation("thaumicexploration:textures/tabs/binding.png")).setParents("TXINFUSIONENCHANTMENT","INFUSIONENCHANTMENT").setConcealed().setSecondary().registerResearchItem();
    	        research.setPages(new ResearchPage("1"), infusionEnchantPage("ENCHBINDING"));
        	
		        research = new TXResearchItem("ENCHNIGHTVISION", "TX", new AspectList().add(Aspect.SENSES, 6).add(Aspect.DARKNESS, 2).add(Aspect.LIGHT, 6), -5, -4, 1, new ResourceLocation("thaumicexploration:textures/tabs/nightVision.png")).setParents("TXINFUSIONENCHANTMENT","INFUSIONENCHANTMENT").setConcealed().setSecondary().registerResearchItem();
		        research.setPages(new ResearchPage("1"), infusionEnchantPage("ENCHNIGHTVISION"));
		        research = new TXResearchItem("ENCHDISARM", "TX", new AspectList().add(Aspect.SLIME, 6).add(Aspect.TRAP, 4).add(Aspect.WEAPON, 4), -3, -4, 1, new ResourceLocation("thaumicexploration:textures/tabs/disarm.png")).setParents("TXINFUSIONENCHANTMENT","INFUSIONENCHANTMENT").setConcealed().setSecondary().registerResearchItem();
		        research.setPages(new ResearchPage("1"), infusionEnchantPage("ENCHDISARM"));
        	}
        
        //Wandcraft
        	research = new FauxResearchItem("TXROD_greatwood","TX","ROD_greatwood","THAUMATURGY",-3,-2,getOriginalItem("ROD_greatwood","THAUMATURGY")).registerResearchItem();
        research = new TXResearchItem("ROD_AMBER", "TX", new AspectList().add(Aspect.TOOL, 5).add(Aspect.AURA, 8).add(Aspect.TRAP, 5).add(Aspect.MAGIC, 8), -5, -1, 2, new ItemStack(ThaumicExploration.amberCore)).setParents("TXROD_greatwood","ROD_obsidian","ROD_reed","ROD_blaze","ROD_ice","ROD_quartz","ROD_bone").setConcealed().registerResearchItem();
        research.setPages(new ResearchPage("1"), infusionPage("ROD_AMBER"));
        research = new TXResearchItem("ROD_TRANSMUTATION", "TX", new AspectList().add(Aspect.TOOL, 8).add(Aspect.MAGIC, 8).add(Aspect.EXCHANGE, 12), -1, -1, 2, new ItemStack(ThaumicExploration.transmutationCore)).setParents("TXROD_greatwood","ROD_greatwood").setConcealed().registerResearchItem();
        research.setPages(new ResearchPage("1"), infusionPage("ROD_TRANSMUTATION"));
        research = new TXResearchItem("ROD_AMBER_staff", "TX", new AspectList().add(Aspect.TOOL, 3).add(Aspect.AURA, 4).add(Aspect.TRAP, 3).add(Aspect.MAGIC, 4), -6, 0, 2, new ItemStack(ThaumicExploration.amberStaffCore)).setParents("TXROD_greatwood_staff","ROD_AMBER").setParentsHidden("ROD_greatwood_staff").setConcealed().setSecondary().registerResearchItem();
        research.setPages(new ResearchPage("1"), arcaneRecipePage("ROD_AMBER_staff"));
        research = new TXResearchItem("ROD_TRANSMUTATION_staff", "TX", new AspectList().add(Aspect.TOOL, 4).add(Aspect.MAGIC, 4).add(Aspect.EXCHANGE, 6), 0, 0, 2, new ItemStack(ThaumicExploration.transmutationStaffCore)).setParents("TXROD_greatwood_staff","ROD_TRANSMUTATION").setParentsHidden("ROD_greatwood_staff").setConcealed().setSecondary().registerResearchItem();
        research.setPages(new ResearchPage("1"), arcaneRecipePage("ROD_TRANSMUTATION_staff"));
       
        research = new FauxResearchItem("TXROD_greatwood_staff","TX","ROD_greatwood_staff","THAUMATURGY",-3,3,getOriginalItem("ROD_greatwood_staff","THAUMATURGY")).registerResearchItem();
        research = new TXResearchItem("ROD_NECROMANCER_staff", "TX", new AspectList().add(Aspect.TOOL, 5).add(Aspect.ENTROPY, 5).add(Aspect.DEATH, 8).add(Aspect.AURA, 4).add(Aspect.SOUL, 5), -3, 1, 3, new ItemStack(ThaumicExploration.necroStaffCore)).setParents("ROD_greatwood_staff","TXROD_greatwood_staff").setConcealed().registerResearchItem();
        research.setPages(new ResearchPage("1"), infusionPage("ROD_NECROMANCER_staff"));

        if (ThaumicExploration.breadWand) {
//            research = new TXResearchItem("ROD_BREAD", "THAUMATURGY", new AspectList().add(Aspect.MAGIC, 5).add(Aspect.CROP, 3).add(Aspect.HUNGER, 4).add(Aspect.HARVEST, 3), -11, 0, 1, new ItemStack(ThaumicExploration.breadCore)).setParents("ROD_AMBER").setConcealed().registerResearchItem().setSecondary();
//            research.setPages(new ResearchPage("1"), infusionPage("ROD_BREAD"));
        }
        
        //Sealery
        ResourceLocation chestSeal = new ResourceLocation("thaumicexploration:textures/tabs/chestSeals.png");
        ResourceLocation jarSeal = new ResourceLocation("thaumicexploration:textures/tabs/jarSeals.png");
        if (ThaumicExploration.allowBoundInventories) {
        	research = new TXResearchItem("CHESTSEAL", "TX", new AspectList().add(Aspect.ELDRITCH, 5).add(Aspect.TRAP, 8).add(Aspect.TRAVEL, 5).add(Aspect.VOID,3), -9, -3, 2, chestSeal).setConcealed().setParentsHidden("MIRROR","TALLOW").registerResearchItem();
        	research.setPages(new ResearchPage("1"),recipePage("BLANKSEAL"),cruciblePage("CHESTSEAL"));
        	research = new TXResearchItem("JARSEAL", "TX", new AspectList().add(Aspect.ELDRITCH, 5).add(Aspect.TRAP, 8).add(Aspect.MAGIC, 5).add(Aspect.TRAVEL,5), -7, -2, 1, jarSeal).setConcealed().setParents("CHESTSEAL").registerResearchItem().setSecondary();
        	research.setPages(new ResearchPage("1"),cruciblePage("JARSEAL"));
        }
    	research = new FauxResearchItem("TXJARVOID","TX","JARVOID","ALCHEMY",7,-10,getOriginalItem("JARVOID","ALCHEMY")).registerResearchItem();
    	research = new TXResearchItem("TRASHJAR", "TX", new AspectList().add(Aspect.ENTROPY, 8).add(Aspect.VOID, 8).add(Aspect.HUNGER, 5).add(Aspect.ELDRITCH,3), 5, -9, 2, new ItemStack(ThaumicExploration.trashJar)).setConcealed().setParents("TXJARVOID","JARVOID","INFUSION","TXINFUSION").registerResearchItem();
    	
    	research.setPages(new ResearchPage("1"),new ResearchPage("2"),infusionPage("TRASHJAR"));
    }
    
	private static void registerResearchPages() {
		//ResourceLocation background = new ResourceLocation("thaumicexploration:textures/gui/taintBackground.png");
		//ResearchCategories.registerCategory("TAINT", new ResourceLocation("thaumicexploration:textures/tabs/taintResearch.png"), background);
		ResourceLocation background2 = new ResourceLocation("thaumcraft", "textures/gui/gui_researchback.png");
		ResearchCategories.registerCategory("TX", new ResourceLocation("thaumicexploration:textures/tabs/txTab.png"), background2);
		
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