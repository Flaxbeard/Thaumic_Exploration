package flaxbeard.thaumicreliquary;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import flaxbeard.thaumicreliquary.research.TRRecipes;

public class ConditionalRecipe implements IRecipe{
	
	public IRecipe myRecipe;
	
	public ConditionalRecipe(IRecipe recipe) {
		this.myRecipe = recipe;
	}

	@Override
	public boolean matches(InventoryCrafting inventorycrafting, World world) {
		try {

	        Container c = (Container) TRRecipes.container.get(inventorycrafting);
	        List list = (List) TRRecipes.crafters.get(c);
	        if (world.isRemote && ThaumicReliquary.optional) {
	        	return myRecipe.matches(inventorycrafting, world);
	        }
	        if (!world.isRemote) {
		        for (int i = 0; i<list.size(); i++) {
		        	if (list.get(i) instanceof EntityPlayer && ThaumicReliquary.optional) {
		        		if (TRPacketHandler.enabledPlayers.contains((EntityPlayer)list.get(i))) {
		        			return myRecipe.matches(inventorycrafting, world);
		        		}
		        	}
		        }
	        }
			return false;
		}
		catch (Exception e)
		{
			
		}
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventorycrafting) {
		// TODO Auto-generated method stub
		return myRecipe.getCraftingResult(inventorycrafting);
	}

	@Override
	public int getRecipeSize() {
		// TODO Auto-generated method stub
		return myRecipe.getRecipeSize();
	}

	@Override
	public ItemStack getRecipeOutput() {
		// TODO Auto-generated method stub
		return myRecipe.getRecipeOutput();
	}

}
