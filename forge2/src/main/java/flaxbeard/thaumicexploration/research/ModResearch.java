package flaxbeard.thaumicexploration.research;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigResearch;
import flaxbeard.thaumicexploration.ThaumicExploration;

public final class ModResearch {

    public static void initResearch() {
    	registerResearchPages();
        ResearchItem research;
        
        //Curing
        research = new TXResearchItem("FLESHCURE", "ALCHEMY", new AspectList().add(Aspect.ARMOR, 1).add(Aspect.ORDER, 1).add(Aspect.EXCHANGE, 1), -4, 0, 3, new ItemStack(Item.rottenFlesh)).setParents("TALLOW").setConcealed().registerResearchItem();
        research.setPages(new ResearchPage("1"), cruciblePage("FLESHCURE"));
        research = new TXResearchItem("BRAINCURE", "ARTIFICE", new AspectList().add(Aspect.ARMOR, 1).add(Aspect.ORDER, 1).add(Aspect.EXCHANGE, 1), -3, 10, 7, new ItemStack(ThaumicExploration.pureZombieBrain)).setParents("JARBRAIN","FLESHCURE").setParentsHidden("INFUSION").setConcealed().registerResearchItem();
        research.setPages(new ResearchPage("1") );
        
        //Wandcraft
        research = new TXResearchItem("WANDAMBER", "THAUMATURGY", new AspectList().add(Aspect.ARMOR, 1).add(Aspect.ORDER, 1).add(Aspect.EXCHANGE, 1), -11, 2, 7, new ItemStack(ThaumicExploration.amberCore)).setParents("ROD_obsidian","ROD_reed","ROD_blaze","ROD_ice","ROD_quartz","ROD_bone").setConcealed().registerResearchItem();
        research.setPages(new ResearchPage("1") );
        

        
       

        
        ResourceLocation chestSeal = new ResourceLocation("thaumicexploration:textures/tabs/chestSeals.png");
        ResourceLocation jarSeal = new ResourceLocation("thaumicexploration:textures/tabs/jarSeals.png");
        if (ThaumicExploration.allowBoundInventories) {
        	research = new TXResearchItem("CHESTSEAL", "ARTIFICE", new AspectList().add(Aspect.MAGIC, 2).add(Aspect.TRAVEL, 2).add(Aspect.ELDRITCH, 1).add(Aspect.VOID,2), 5, 0, 3, chestSeal).setConcealed().setParentsHidden("MIRROR","TALLOW").registerResearchItem();
        	research.setPages(new ResearchPage("1"),recipePage("BLANKSEAL"),infusionPage("CHESTSEAL"));
        	research = new TXResearchItem("JARSEAL", "ARTIFICE", new AspectList().add(Aspect.MAGIC, 2).add(Aspect.TRAVEL, 2).add(Aspect.ELDRITCH, 1).add(Aspect.VOID,2), 7, 0, 3, jarSeal).setConcealed().setParentsHidden("CHESTSEAL").registerResearchItem();
        	research.setPages(new ResearchPage("1"),infusionPage("JARSEAL"));
        }
    }
    
	private static void registerResearchPages() {
		ResourceLocation background = new ResourceLocation("thaumcraft", "textures/gui/gui_researchback.png");

        
		
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

}