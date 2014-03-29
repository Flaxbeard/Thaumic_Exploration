package flaxbeard.thaumicexploration.api;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.apache.commons.lang3.tuple.MutablePair;

import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.common.config.ConfigResearch;
import flaxbeard.thaumicexploration.ThaumicExploration;

public class NecromanticAltarAPI {

	public static void addNecroRecipe(String string,
			NecromanticRecipe necromanticRecipe) {
		recipes.put(string, necromanticRecipe);
	}
	
	public static void addNecroRecipeWithThaumnomiconDisplay(String string, NecromanticRecipe necromanticRecipe) {
		addNecroRecipe(string,necromanticRecipe);
		AspectList aspects = new AspectList();
		aspects.add(ThaumicExploration.fakeAspectNecro, necromanticRecipe.energyRequired);
        InfusionRecipe recipe = new InfusionRecipe(necromanticRecipe.researchRequired, necromanticRecipe.recipeOutput, getInstabilityFromRecipe(necromanticRecipe),aspects , new ItemStack(ThaumicExploration.itemAltar), necromanticRecipe.recipeInput);
        ConfigResearch.recipes.put(string, recipe);
	}
	
	private static int getInstabilityFromRecipe(
			NecromanticRecipe necromanticRecipe) {
		// TODO Auto-generated method stub
		return 10*(necromanticRecipe.energyRequired/100);
	}

	public static void addEnergyToItem(int id, int metadata, int energy) {
		necroEnergyItems.put(MutablePair.of(id,metadata), energy);
	}
	
	public static void addEnergyToItem(int id, int energy) {
		
		necroEnergyItems.put(MutablePair.of(id, -1), energy);
	}
	
	public static NecromanticRecipe findMatchingNecromanticInfusionRecipe(ArrayList<ItemStack> items, EntityPlayer player)
	{

		NecromanticRecipe var13 = null;
		NecromanticRecipe[] possibleRecipes = recipes.values().toArray(new NecromanticRecipe[0]);
		for (NecromanticRecipe var11 : possibleRecipes) {
			if ((var11.matches(items, player.worldObj, player)))
			{
		        var13 = var11;
		        break;
			}
		}
		return var13;
	}

	public static HashMap<String, NecromanticRecipe> recipes = new HashMap();
	public static HashMap<MutablePair,Integer> necroEnergyItems = new HashMap();

}
