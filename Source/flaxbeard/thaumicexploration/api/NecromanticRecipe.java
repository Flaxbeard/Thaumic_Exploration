package flaxbeard.thaumicexploration.api;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.ThaumcraftApiHelper;

public class NecromanticRecipe {
	public String researchRequired;
	public ItemStack recipeOutput;
	public ItemStack[] recipeInput;
	public int energyRequired;
	
	public NecromanticRecipe(String researchKey, ItemStack result, ItemStack[] items, int energy) {
		this.researchRequired = researchKey;
		this.recipeOutput = result;
		this.recipeInput = items;
		this.energyRequired = energy;
	}
	public NecromanticRecipe(String researchKey, ItemStack result, ItemStack items, int energy) {
		this(researchKey, result, new ItemStack[]{items}, energy);
	}
	public boolean matches(ArrayList<ItemStack> input, World world, EntityPlayer player)
	  {
	    if ((this.researchRequired.length() > 0) && (!ThaumcraftApiHelper.isResearchComplete(player.username, this.researchRequired))) {
	      return false;
	    }

	    ArrayList<ItemStack> ii = new ArrayList();
	    for (ItemStack is : input) {
	      ii.add(is.copy());
	    }
	    for (ItemStack comp : this.recipeInput)
	    {
	      boolean b = false;
	      for (int a = 0; a < ii.size(); a++)
	      {
	        ItemStack i2 = ((ItemStack)ii.get(a)).copy();
	        if (comp.getItemDamage() == 32767) {
	          i2.setItemDamage(32767);
	        }
	        if (areItemStacksEqual(i2, comp, true))
	        {
	          ii.remove(a);
	          b = true;
	          break;
	        }
	      }
	      if (!b) {
	        return false;
	      }
	    }
	    return ii.size() == 0;
	  }
	
	  private boolean areItemStacksEqual(ItemStack stack0, ItemStack stack1, boolean fuzzy)
	  {
	    if ((stack0 == null) && (stack1 != null)) {
	      return false;
	    }
	    if ((stack0 != null) && (stack1 == null)) {
	      return false;
	    }
	    if ((stack0 == null) && (stack1 == null)) {
	      return true;
	    }
	    boolean t1 = false;
	    if (fuzzy)
	    {
	      t1 = true;
	      int od = OreDictionary.getOreID(stack0);
	      if (od != -1)
	      {
	        ItemStack[] ores = (ItemStack[])OreDictionary.getOres(Integer.valueOf(od)).toArray(new ItemStack[0]);
	        if (ThaumcraftApiHelper.containsMatch(false, new ItemStack[] { stack1 }, ores)) {
	          return true;
	        }
	      }
	    }
	    else
	    {
	      t1 = ItemStack.areItemStackTagsEqual(stack0, stack1);
	    }
	    return stack0.stackSize > stack0.getMaxStackSize() ? false : stack0.getItemDamage() != stack1.getItemDamage() ? false : stack0.itemID != stack1.itemID ? false : t1;
	  }
}
