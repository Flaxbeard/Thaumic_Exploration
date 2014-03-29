package flaxbeard.thaumicexploration.research;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.apache.commons.lang3.tuple.MutablePair;

import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.api.research.ResearchPage.PageType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flaxbeard.thaumicexploration.ThaumicExploration;
import flaxbeard.thaumicexploration.api.NecromanticAltarAPI;

public class TXResearchItem extends ResearchItem {

        public TXResearchItem(String par1, String par2) {
                super(par1, par2);
        }

        public TXResearchItem(String par1, String par2, AspectList tags, int par3,
                        int par4, int par5, ItemStack icon) {
                super(par1, par2, tags, par3, par4, par5, icon);
        }

        public TXResearchItem(String par1, String par2, AspectList tags, int par3,
                        int par4, int par5, ResourceLocation icon) {
                super(par1, par2, tags, par3, par4, par5, icon);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public String getName() {
                return StatCollector.translateToLocal("te.name." + key);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public String getText() {
        	if (ThaumicExploration.prefix) {
                return StatCollector.translateToLocal("te.researchPrefix")
                                + " " + StatCollector.translateToLocal("te.tag." + key);
        	}
        	else
        	{
        		return StatCollector.translateToLocal("te.tag." + key);
        	}
        }

        @Override
        public ResearchItem setPages(ResearchPage... par) {
                for (ResearchPage page : par) {
	                	if (page.type == PageType.TEXT && page.text.split("#")[0].equals("NE")) {
	                    	//System.out.println("YAY");
	                    	String text = "";
	                    	int offset = Integer.parseInt(page.text.split("#")[1]);
	                    	int iter = 0;
	                		for (MutablePair item : NecromanticAltarAPI.necroEnergyItems.keySet()) {
	                			if (iter < (4*offset+4) && iter >= (4*offset)) {
	                    			int id = (Integer) item.left;
	                    			int meta = (Integer) item.right;
	                    			ItemStack stack = null;
	                    			if (meta == -1) {
	                    				stack = new ItemStack(id,1,0);
	                    			}
	                    			else
	                    			{
	                    				stack = new ItemStack(id,1,meta);
	                    			}
	                    			String icon = stack.getIconIndex().getIconName();
	                    			if (!icon.contains(":")) {
	                    				icon = "minecraft:"+icon;
	                    			}
	                    			text = text + stack.getDisplayName() + ": Provides " + NecromanticAltarAPI.necroEnergyItems.get(item) + " points of NE.<IMG>" + icon.split(":")[0] + ":textures/items/" + icon.split(":")[1] + ".png:0:0:255:255:0.0625</IMG>" + "<BR>";
	                			}
	                			iter++;
	                		}
	                		System.out.println("IMPORTANT: " + text);
	                		page.text = text;
	                    }
	                	else if (page.type == PageType.TEXT) {
	                    	System.out.println("TEXT IS: " + (page.text.split("#")[0].equals("NE")) + "'" + page.text.split("#")[0] + "'");
	                        page.text = "te.text." + key + "." + page.text;
	                	}

                        		
                        
                        if (page.type == PageType.INFUSION_CRAFTING) {
                                if (parentsHidden == null || parentsHidden.length == 0)
                                        parentsHidden = new String[] { "INFUSION" };
                                else {
                                        String[] newParents = new String[parentsHidden.length + 1];
                                        newParents[0] = "INFUSION";
                                        for (int i = 0; i < parentsHidden.length; i++)
                                                newParents[i + 1] = parentsHidden[i];
                                        parentsHidden = newParents;
                                }
                        }
                }

                return super.setPages(par);
        }
}