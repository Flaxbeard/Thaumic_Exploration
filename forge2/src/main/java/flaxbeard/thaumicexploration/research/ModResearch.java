package flaxbeard.thaumicexploration.research;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import us.samcraft.gentlemanturgy.lib.Reference;

public final class ModResearch {

    public static void initResearch() {
    	registerResearchPages();

    	System.out.println("works");
        ResearchItem research;
        research = new TEResearchItem("Electric Magic Tools", "ARTIFICE", new AspectList(), -2, 1, 0, new ResourceLocation("thaumicexploration:textures/tabs/test.png")).setRound().setAutoUnlock().registerResearchItem();
        research.setPages(new ResearchPage("blah"));
        research = new ResearchItem("Curing", "AWESOMENESS", new AspectList().add(Aspect.LIGHT, 2), 2, 3, 3, new ItemStack(Item.appleRed)).setStub().setAutoUnlock().setRound().registerResearchItem();
        research.setPages(new ResearchPage("esijgoahg"));
		research = new ResearchItem("SMASHING", "ALCHEMY", new AspectList().add(Aspect.LIGHT, 1).add(Aspect.ARMOR, 3), -2, -1, 0, new ItemStack(Item.pickaxeIron)).setStub().setRound().registerResearchItem();
		research.setPages(new ResearchPage("0"));
		research = new ResearchItem("SMOOSHING", "ARTIFICE", new AspectList().add(Aspect.LIGHT, 1).add(Aspect.ARMOR, 3), -2, -1, 0, new ItemStack(Item.pickaxeIron)).setStub().setRound().registerResearchItem();
		research.setPages(new ResearchPage("0"));
        
    }
    
	private static void registerResearchPages() {
		ResourceLocation background = new ResourceLocation("thaumcraft", "textures/gui/gui_researchback.png");

        ResearchCategories.registerCategory("AWESOMENESS", new ResourceLocation("thaumicexploration:textures/tabs/test.png"), background);	
		
	}

}