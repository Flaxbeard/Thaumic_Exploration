package flaxbeard.thaumicexploration.wand;

import java.util.ArrayList;
import java.util.List;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.wands.IWandRodOnUpdate;
import thaumcraft.common.items.wands.ItemWandCasting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class WandRodTransmutationOnUpdate implements IWandRodOnUpdate {

	private Aspect[] aspects = {Aspect.ORDER, Aspect.ENTROPY, Aspect.EARTH, Aspect.WATER, Aspect.AIR, Aspect.FIRE};

	@Override
	public void onUpdate(ItemStack itemstack, EntityPlayer player) {
		//if (player.ticksExisted % 400 == 0) {
			int numberUnder90 = 0;
			double totalExcessVis = 0;
			ArrayList<Integer> lowAspects = new ArrayList<Integer>();
			for(int i=0;i<6;i++) {
				double visCount = ((ItemWandCasting) itemstack.getItem()).getVis(itemstack, this.aspects[i]);
				double cutoffPercent = (((((ItemWandCasting) itemstack.getItem()).getMaxVis(itemstack)) / 10) * 9);
				double excessVis = (visCount - cutoffPercent);
				if (excessVis <= 0) {
					numberUnder90++;
					lowAspects.add(i);
				}
			}

			if (numberUnder90 > 0) {
				for(int i=0;i<6;i++) {
					double visCount = ((ItemWandCasting) itemstack.getItem()).getVis(itemstack, this.aspects[i]);
					double cutoffPercent = (((((ItemWandCasting) itemstack.getItem()).getMaxVis(itemstack)) / 10) * 9) + 0.1;
					double excessVis = (visCount - cutoffPercent);
					
					if (excessVis > 0) {
						((ItemWandCasting)itemstack.getItem()).consumeVis(itemstack, player, this.aspects[i], (int) excessVis,true);
						totalExcessVis += excessVis;
					}
				}
				if ((totalExcessVis/100) < 0.1) {
					totalExcessVis = 0;
				}
				double wastedVis = 0;
				int eachToAdd = (int) ((totalExcessVis) / (lowAspects.size() * 4));
				for(int z=0;z<lowAspects.size();z++) {
					double visCount = ((ItemWandCasting) itemstack.getItem()).getVis(itemstack, this.aspects[lowAspects.get(z)]);
					double myWastedVis = visCount - ((ItemWandCasting) itemstack.getItem()).getMaxVis(itemstack) + eachToAdd;
					if (myWastedVis > 0) {
						wastedVis += myWastedVis;
					}
					((ItemWandCasting)itemstack.getItem()).storeVis(itemstack, this.aspects[lowAspects.get(z)], (int) (visCount + eachToAdd));
				}
				wastedVis = wastedVis/6;
				for(int z=0;z<6;z++) {
					double visCount = ((ItemWandCasting) itemstack.getItem()).getVis(itemstack, this.aspects[z]);
					((ItemWandCasting)itemstack.getItem()).storeVis(itemstack, this.aspects[z], (int) (visCount + wastedVis));
				}
			}
		//}
		
		
		
		
		//if (player.ticksExisted % 200 == 0) {
//			for(int i=0;i<6;i++) {
//				if (((ItemWandCasting) itemstack.getItem()).getVis(itemstack, this.aspects[i]) > ((((ItemWandCasting) itemstack.getItem()).getMaxVis(itemstack)) / 10) * 9) {
//					int expendableVis = (int) (((ItemWandCasting) itemstack.getItem()).getVis(itemstack, this.aspects[i]) - 22.5);
//					for(int z=0;z<expendableVis;z++) {
//						for(int k=0;k<6;k++) {
//							if (k != i) {
//								int visCount = ((ItemWandCasting) itemstack.getItem()).getVis(itemstack, this.aspects[k]);
//								if (visCount < (((((ItemWandCasting) itemstack.getItem()).getMaxVis(itemstack)) / 10) * 9)) {
//									((ItemWandCasting)itemstack.getItem()).addVis(itemstack, this.aspects[k], 1, true);
//									((ItemWandCasting)itemstack.getItem()).addVis(itemstack, this.aspects[i], -1, true);
//								}
//							}
//						}
//					}
//				}
//			}
		//}
		
	}

}
