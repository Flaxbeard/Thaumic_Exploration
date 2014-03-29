package flaxbeard.thaumicreliquary.research;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
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
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.config.ConfigResearch;
import xreliquary.blocks.XRBlocks;
import xreliquary.items.XRItems;
import xreliquary.lib.Reference;

public final class TRResearch {
	public static ArrayList<String> trResearch = new ArrayList<String>();

    public static void initResearch() {
    	registerResearchPages();
        ResearchItem research;
        research = new TRResearchItem("TRELIQUARY", "RELIQUARY", new AspectList(), 0, 0, 1, XRItems.potion(Reference.SPLASH_META)).setVirtual().registerResearchItem();
        trResearch.add("TRELIQUARY");
        research = new TRResearchItem("CONDPOTS", "RELIQUARY", new AspectList().add(Aspect.MAGIC, 4).add(Aspect.SLIME, 2).add(Aspect.WATER, 3), 0, -1, 1, XRItems.potion(Reference.SPLASH_META)).registerResearchItem().setParentsHidden("TRELIQUARY").setConcealed();
        research.setPages(new ResearchPage("1"), recipePage("VIAL"), arcaneRecipePage("CONDDRINK"), arcaneRecipePage("CONDSPLASH"), arcaneRecipePage("CONDSPEED"), arcaneRecipePage("CONDSLOW"), arcaneRecipePage("CONDSTRONG"), arcaneRecipePage("CONDWEAK"), 
        		arcaneRecipePage("CONDHEAL"), arcaneRecipePage("CONDACID"),arcaneRecipePage("CONDSEE"), arcaneRecipePage("CONDBLIND"), arcaneRecipePage("CONDFIRE"), arcaneRecipePage("CONDPOISON"), arcaneRecipePage("CONDINVIS"), arcaneRecipePage("CONDREGEN"));
        trResearch.add("CONDPOTS");
        research = new TRResearchItem("GLOWWATER", "RELIQUARY", new AspectList().add(Aspect.MAGIC, 4).add(Aspect.ORDER, 2).add(Aspect.LIGHT, 2), 2, -3, 1, new ItemStack(XRItems.glowingWater)).setParents("CONDPOTS").registerResearchItem().setParentsHidden("TRELIQUARY").setSecondary().setConcealed();
        research.setPages(new ResearchPage("1"), arcaneRecipePage("GLOWWATER"));
        trResearch.add("GLOWWATER");
        research = new TRResearchItem("GLOWBREAD", "RELIQUARY", new AspectList().add(Aspect.LIGHT,2).add(Aspect.HEAL, 2).add(Aspect.HUNGER, 4).add(Aspect.CROP, 3), 3,-5, 1, new ItemStack(XRItems.glowingBread)).setParents("GLOWWATER").registerResearchItem().setParentsHidden("TRELIQUARY").setSecondary().setConcealed();
        research.setPages(new ResearchPage("1"), arcaneRecipePage("GLOWBREAD"));
        trResearch.add("GLOWBREAD");
        
        research = new TRResearchItem("CONDLOVE", "RELIQUARY", new AspectList().add(Aspect.HEAL,2).add(Aspect.BEAST, 2).add(Aspect.SENSES,1).add(Aspect.CROP, 3), 1, -5, 1, XRItems.potion(Reference.APHRODITE_META)).setParents("CONDPOTS").registerResearchItem().setParentsHidden("TRELIQUARY").setSecondary().setConcealed();
        research.setPages(new ResearchPage("1"), arcaneRecipePage("CONDLOVE"));
        trResearch.add("CONDLOVE");
        
        research = new TRResearchItem("CONDCONFUSE", "RELIQUARY", new AspectList().add(Aspect.MIND,2).add(Aspect.ENTROPY, 2).add(Aspect.SENSES,1), 0, -4, 1, XRItems.potion(Reference.CONFUSION_META)).setParents("CONDPOTS").registerResearchItem().setParentsHidden("TRELIQUARY").setSecondary().setConcealed();
        research.setPages(new ResearchPage("1"), arcaneRecipePage("CONDCONFUSE"));
        trResearch.add("CONDCONFUSE");
        
        research = new TRResearchItem("CONDWITHER", "RELIQUARY", new AspectList().add(Aspect.DEATH,2).add(Aspect.ENTROPY, 2).add(Aspect.DARKNESS,1), -1, -3, 1, XRItems.potion(Reference.WITHER_META)).setParents("CONDPOTS").registerResearchItem().setParentsHidden("TRELIQUARY").setSecondary().setConcealed();
        research.setPages(new ResearchPage("1"), arcaneRecipePage("CONDWITHER"));
        trResearch.add("CONDWITHER");
        
        research = new TRResearchItem("CONDDIGGING", "RELIQUARY", new AspectList().add(Aspect.MINE,2).add(Aspect.MOTION, 2).add(Aspect.EARTH,1), -2, -2, 1, XRItems.potion(Reference.DIGGING_META)).setParents("CONDPOTS").registerResearchItem().setParentsHidden("TRELIQUARY").setSecondary().setConcealed();
        research.setPages(new ResearchPage("1"), arcaneRecipePage("CONDDIGGING"));
        trResearch.add("CONDDIGGING");
        
        research = new TRResearchItem("CONDJUMP", "RELIQUARY", new AspectList().add(Aspect.MOTION,2).add(Aspect.AIR,2).add(Aspect.TRAVEL,1), -3, -3, 1, XRItems.potion(Reference.BOUNDING_META)).setParents("CONDPOTS").registerResearchItem().setParentsHidden("TRELIQUARY").setSecondary().setConcealed();
        research.setPages(new ResearchPage("1"), arcaneRecipePage("CONDJUMP"));
        trResearch.add("CONDJUMP");
        
        research = new TRResearchItem("CONDRESIST", "RELIQUARY", new AspectList().add(Aspect.ARMOR,2).add(Aspect.METAL,2).add(Aspect.STONE,1), -2, -4, 1, XRItems.potion(Reference.RESISTANCE_META)).setParents("CONDPOTS").registerResearchItem().setParentsHidden("TRELIQUARY").setSecondary().setConcealed();
        research.setPages(new ResearchPage("1"), arcaneRecipePage("CONDRESIST"));
        trResearch.add("CONDRESIST");
        
        research = new TRResearchItem("CONDBREATHE", "RELIQUARY", new AspectList().add(Aspect.WATER,2).add(Aspect.AIR, 2).add(Aspect.BEAST,1), -1, -5, 1, XRItems.potion(Reference.BREATHING_META)).setParents("CONDPOTS").registerResearchItem().setParentsHidden("TRELIQUARY").setSecondary().setConcealed();
        research.setPages(new ResearchPage("1"), arcaneRecipePage("CONDBREATHE"));
        trResearch.add("CONDBREATHE");
        
        research = new TRResearchItem("CONDCOMP", "RELIQUARY", new AspectList().add(Aspect.MAGIC,4).add(Aspect.SLIME, 2).add(Aspect.WATER,4), -1, -7, 1, XRItems.potion(Reference.RUINATION_META)).setParents("CONDBREATHE","CONDRESIST","CONDJUMP","CONDDIGGING","CONDWITHER","CONDCONFUSE","CONDLOVE").registerResearchItem().setParentsHidden("TRELIQUARY").setConcealed();
        research.setPages(new ResearchPage("1"), arcaneRecipePage("CONDRUIN"), arcaneRecipePage("CONDPOTENCE"), arcaneRecipePage("CONDCELERY"), arcaneRecipePage("CONDPROT"), arcaneRecipePage("CONDHEALTHY"));
        trResearch.add("CONDCOMP");
        
        research = new TRResearchItem("CONDFERT", "RELIQUARY", new AspectList().add(Aspect.PLANT,2).add(Aspect.SEED, 2).add(Aspect.CROP,2), -3, 0, 1, XRItems.potion(Reference.FERTILIZER_META)).setParents("CONDPOTS").registerResearchItem().setParentsHidden("TRELIQUARY").setSecondary().setConcealed();
        research.setPages(new ResearchPage("1"), arcaneRecipePage("CONDFERT"));
        trResearch.add("CONDFERT");
        
        research = new TRResearchItem("LILY", "RELIQUARY", new AspectList().add(Aspect.PLANT,4).add(Aspect.ENTROPY, 2).add(Aspect.CROP,4).add(Aspect.MAGIC,4), -5, -2, 2, new ItemStack(XRBlocks.lilypad)).setParents("CONDFERT").registerResearchItem().setParentsHidden("TRELIQUARY").setConcealed();
        research.setPages(new ResearchPage("1"), infusionPage("LILY"));
        trResearch.add("LILY");
        
        research = new FauxResearchItem("FAUXTRANS","RELIQUARY","TRANSGOLD","ALCHEMY",3,1,new ItemStack(Item.goldNugget)).registerResearchItem();
        research = new TRResearchItem("MIDAS", "RELIQUARY", new AspectList().add(Aspect.METAL, 3).add(Aspect.CRAFT, 5).add(Aspect.EXCHANGE, 3).add(Aspect.GREED, 5), 5, 0, 1, new ItemStack(XRItems.midasTouchstone)).setParents("FAUXTRANS").setParentsHidden("TRELIQUARY").setConcealed().registerResearchItem();
        research.setPages(new ResearchPage("1"), arcaneRecipePage("MIDAS"));
        trResearch.add("FAUXTRANS");
        research = new FauxResearchItem("FAUXALU","RELIQUARY","ALUMENTUM","ALCHEMY",5,-6,new ItemStack(ConfigItems.itemResource,1,0)).registerResearchItem();
        research = new TRResearchItem("HOLYGRENADE", "RELIQUARY", new AspectList().add(Aspect.WEAPON, 3).add(Aspect.FIRE, 3).add(Aspect.ENERGY, 5).add(Aspect.GREED, 5), 6, -4, 2, new ItemStack(XRItems.holyHandGrenade)).setParents("MIDAS","FAUXALU","GLOWWATER").setParentsHidden("TRELIQUARY").setConcealed().registerResearchItem();
        research.setPages(new ResearchPage("1"), arcaneRecipePage("HOLYGRENADE"));
        trResearch.add("FAUXALU");
        //research = new FauxResearchItem("INFUSION","RELIQUARY","INFUSION","ARTIFICE",7,-2,new ItemStack(ConfigBlocks.blockStoneDevice, 1, 2)).registerResearchItem();
        research = new TRResearchItem("DESTCAT", "RELIQUARY", new AspectList().add(Aspect.ENTROPY,4).add(Aspect.MINE, 5).add(Aspect.FIRE, 6).add(Aspect.ENERGY, 6).add(Aspect.MAGIC, 5), 8, -5, 2, new ItemStack(XRItems.destructionCatalyst)).setParents("HOLYGRENADE","INFUSION").setParentsHidden("TRELIQUARY").setConcealed().registerResearchItem();
        research.setPages(new ResearchPage("1"), infusionPage("DESTCAT"));
        trResearch.add("DESTCAT");
        research = new TRResearchItem("COINFORTUNE", "RELIQUARY", new AspectList().add(Aspect.ENTROPY,4).add(Aspect.HUNGER, 6).add(Aspect.TRAVEL, 4).add(Aspect.GREED,7), 8, 1, 2, new ItemStack(XRItems.fortuneCoin)).setParents("MIDAS","INFUSION").setParentsHidden("TRELIQUARY","GLOWWATER").setConcealed().registerResearchItem();
        research.setPages(new ResearchPage("1"), infusionPage("COINFORTUNE"));
        trResearch.add("COINFORTUNE");
        research = new TRResearchItem("CROSS", "RELIQUARY", new AspectList().add(Aspect.WEAPON,6).add(Aspect.UNDEAD, 4).add(Aspect.LIGHT, 4), 4, -2, 2, new ItemStack(XRItems.mercyCross)).setParents("MIDAS","GLOWWATER").setParentsHidden("TRELIQUARY").setConcealed().registerResearchItem();
        research.setPages(new ResearchPage("1"), arcaneRecipePage("CROSS"));
        trResearch.add("CROSS");
        research = new TRResearchItem("MBANE", "RELIQUARY", new AspectList().add(Aspect.WEAPON,6).add(Aspect.MAGIC, 6).add(Aspect.LIGHT, 2).add(Aspect.ELDRITCH, 4), 9, -1, 2, new ItemStack(XRItems.magicbane)).setParents("MIDAS","INFUSION").setParentsHidden("TRELIQUARY").setConcealed().registerResearchItem();
        research.setPages(new ResearchPage("1"), infusionPage("MBANE"));
        trResearch.add("MBANE");
        research = new FauxResearchItem("FAUXLAMP","RELIQUARY","ARCANELAMP","ARTIFICE",10,-4,new ItemStack(ConfigBlocks.blockMetalDevice, 1, 7)).registerResearchItem();
        research = new TRResearchItem("SOJOURNERSTAFF", "RELIQUARY", new AspectList().add(Aspect.WEAPON,6).add(Aspect.MAGIC, 6).add(Aspect.LIGHT, 2).add(Aspect.ELDRITCH, 4), 8, -3, 2, new ItemStack(XRItems.sojournerStaff)).setParents("MIDAS","FAUXLAMP").setParentsHidden("TRELIQUARY","GLOWWATER").setConcealed().registerResearchItem();
        research.setPages(new ResearchPage("1"), arcaneRecipePage("SOJOURNERSTAFF"));
        trResearch.add("SOJOURNERSTAFF");
        research = new TRResearchItem("CHALICE", "RELIQUARY", new AspectList().add(Aspect.WATER,6).add(Aspect.HUNGER, 4).add(Aspect.GREED, 4), 6, 2, 2, new ItemStack(XRItems.emperorChalice)).setParents("MIDAS").setParentsHidden("TRELIQUARY").setConcealed().registerResearchItem();
        research.setPages(new ResearchPage("1"), arcaneRecipePage("CHALICE"));
        trResearch.add("CHALICE");
        
        research = new TRResearchItem("VOIDTEAR", "RELIQUARY", new AspectList().add(Aspect.VOID, 6).add(Aspect.ELDRITCH, 4).add(Aspect.MAGIC, 4), -1, 1, 2,  new ItemStack(XRItems.emptyVoidTear)).registerResearchItem().setParentsHidden("TRELIQUARY").setHidden().setItemTriggers(new ItemStack(Item.eyeOfEnder),new ItemStack(Item.enderPearl),new ItemStack(Block.enderChest));
        research.setPages(new ResearchPage("1"), arcaneRecipePage("VOIDTEAR"));
        trResearch.add("VOIDTEAR");
        research = new TRResearchItem("VOIDSATCHEL", "RELIQUARY", new AspectList().add(Aspect.VOID, 6).add(Aspect.ELDRITCH, 4).add(Aspect.MAGIC, 4), -4, 2, 2,  new ItemStack(XRItems.voidSatchel)).registerResearchItem().setParents("VOIDTEAR").setParentsHidden("TRELIQUARY").setConcealed().setSecondary();
        research.setPages(new ResearchPage("1"), new ResearchPage("2"),recipePage("VOIDSATCHEL"),recipePage("VOIDSATCHELUPGRADE"));
        trResearch.add("VOIDSATCHEL");
        research = new TRResearchItem("SALEYE", "RELIQUARY", new AspectList().add(Aspect.ARMOR, 4).add(Aspect.FIRE, 6).add(Aspect.MAGIC, 4), 2, 3, 2,  new ItemStack(XRItems.salamanderEye)).registerResearchItem().setParentsHidden("TRELIQUARY").setParents("INFUSION").setHidden().setEntityTriggers("Blaze","Ghast");
        research.setPages(new ResearchPage("1"), infusionPage("SALEYE"));
        trResearch.add("SALEYE");
        research = new TRResearchItem("WRAITH", "RELIQUARY", new AspectList().add(Aspect.TRAVEL, 8).add(Aspect.ELDRITCH, 8).add(Aspect.MAGIC,4).add(Aspect.MOTION, 6), 0, 3, 3,  new ItemStack(XRItems.wraithEye)).registerResearchItem().setParents("VOIDTEAR","SALEYE","MIRROR").setParentsHidden("TRELIQUARY").setConcealed();
        research.setPages(new ResearchPage("1"), arcaneRecipePage("WRAITHNODE"),infusionPage("WRAITH"));
        trResearch.add("WRAITH");
        
        research = new TRResearchItem("CLOAK", "RELIQUARY", new AspectList().add(Aspect.CLOTH, 4).add(Aspect.ELDRITCH, 8).add(Aspect.DARKNESS,2).add(Aspect.LIGHT,2), -2, 4, 3,  new ItemStack(XRItems.distortionCloak)).registerResearchItem().setParents("VOIDTEAR","INFUSION").setParentsHidden("TRELIQUARY").setConcealed();
        research.setPages(new ResearchPage("1"), infusionPage("CLOAK"));
        trResearch.add("CLOAK");
        
        research = new TRResearchItem("ROSE", "RELIQUARY", new AspectList().add(Aspect.UNDEAD, 8).add(Aspect.ARMOR, 10).add(Aspect.LIGHT,8).add(Aspect.PLANT, 4), 2, -1, 3, new ItemStack(XRItems.witherlessRose)).setParents("GLOWWATER").registerResearchItem().setParentsHidden("TRELIQUARY").setHidden().setItemTriggers(new ItemStack(Item.netherStar));
        research.setPages(new ResearchPage("1"), infusionPage("ROSE"));
        trResearch.add("ROSE");
        
        
        String[] researches = trResearch.toArray(new String[0]);
        research = new TRResearchItem("TOME", "RELIQUARY", new AspectList().add(Aspect.EXCHANGE, 16).add(Aspect.MAGIC, 16), 1, 1, 3, new ItemStack(XRItems.alkahestryTome)).registerResearchItem().setParentsHidden(researches).setHidden().setSpecial();
        research.setPages(new ResearchPage("1"), infusionPage("TOME"));
        trResearch.add("TOME");
        
        research = new TRResearchItem("ALTAR", "RELIQUARY", new AspectList().add(Aspect.MAGIC, 8).add(Aspect.MECHANISM, 10).add(Aspect.LIGHT,10).add(Aspect.EXCHANGE, 4), 4, 3, 2, new ItemStack(XRBlocks.altarActive)).setParents("TOME").registerResearchItem().setParentsHidden("TRELIQUARY").setConcealed();
        research.setPages(new ResearchPage("1"), arcaneRecipePage("ALTAR"));
        trResearch.add("ALTAR");
        
    }
    
	private static void registerResearchPages() {
		ResourceLocation background = new ResourceLocation("thaumcraft", "textures/gui/gui_researchback.png");
		ResearchCategories.registerCategory("RELIQUARY", new ResourceLocation("xreliquary:textures/items/alkahestTome.png"), background);
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