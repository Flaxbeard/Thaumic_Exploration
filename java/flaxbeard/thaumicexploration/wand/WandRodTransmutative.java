package flaxbeard.thaumicexploration.wand;

import java.util.ArrayList;

import flaxbeard.thaumicexploration.event.TXTickHandler;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.wands.IWandRodOnUpdate;
import thaumcraft.api.wands.WandRod;

public class WandRodTransmutative extends WandRod {

	private ArrayList<ResourceLocation> textures = new ArrayList<ResourceLocation>();
	private int[] frames = {0,0,0,0,0,0,1,2,3,4,5,5,5,5,5,5,6,7,8,9,10,10,10,10,10,10,11,12,13,14,15,15,15,15,15,15,16,17,18,19,20,20,20,20,20,20,21,22,23,24,25,25,25,25,25,25,26,27,28,29};
	public WandRodTransmutative(String tag, int capacity, ItemStack item,
			int craftCost, IWandRodOnUpdate onUpdate, ResourceLocation texture) {
		super(tag, capacity, item, craftCost, onUpdate, texture);
		for (int i = 0; i<30; i++) {
			textures.add(i, new ResourceLocation("thaumicexploration:textures/models/" + i + ".png"));
		}
	}
	@Override
	public ResourceLocation getTexture()
	{
		//System.out.println(Math.floor(textureNum/2));
		int textureNum = (TXTickHandler.ticks)%118;
		return textures.get(frames[(int) (Math.floor(textureNum/2))]);
	}

}
