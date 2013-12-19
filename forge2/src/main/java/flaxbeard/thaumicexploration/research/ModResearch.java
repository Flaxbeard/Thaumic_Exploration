package flaxbeard.thaumicexploration.research;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigResearch;
import flaxbeard.thaumicexploration.ThaumicExploration;

public final class ModResearch {

    public static void initResearch() {
    	registerResearchPages();

    	System.out.println("works");
        ResearchItem research;
        research = new TXResearchItem("FLESHCURE", "ALCHEMY", new AspectList().add(Aspect.LIGHT, 2), -4, 0, 3, new ItemStack(ThaumicExploration.pureZombieBrain)).setParents("TALLOW","INFUSION").setConcealed().registerResearchItem();
        //research.setPages(new ResearchPage("1"), infusionPage("Cured Zombie Brain"), cruciblePage("Leather"));
      
    }
    
	private static void registerResearchPages() {
		ResourceLocation background = new ResourceLocation("thaumcraft", "textures/gui/gui_researchback.png");

        
		
	}
	
	private static ResearchPage cruciblePage(String name) {
        return new ResearchPage((CrucibleRecipe) ConfigResearch.recipes.get(name));
	}
	
	private static ResearchPage infusionPage(String name) {
        return new ResearchPage((InfusionRecipe) ConfigResearch.recipes.get(name));
	}

}