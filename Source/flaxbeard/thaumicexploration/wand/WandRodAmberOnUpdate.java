package flaxbeard.thaumicexploration.wand;

import java.util.ArrayList;
import java.util.List;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.IWandRodOnUpdate;
import thaumcraft.common.items.wands.ItemWandCasting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class WandRodAmberOnUpdate implements IWandRodOnUpdate {

	
	private Aspect[] aspects = {Aspect.ORDER, Aspect.ENTROPY, Aspect.EARTH, Aspect.WATER, Aspect.AIR, Aspect.FIRE};

	@Override
	public void onUpdate(ItemStack itemstack, EntityPlayer player) {
		if (player.ticksExisted % 100 == 0) {
			List lowAspects = new ArrayList();
			for (int i=0; i<6; i++) {
				double visCount = ((ItemWandCasting) itemstack.getItem()).getVis(itemstack, this.aspects[i]);
				double cutoffPercent = (((((ItemWandCasting) itemstack.getItem()).getMaxVis(itemstack))));
				if (visCount < cutoffPercent) {
					lowAspects.add(aspects[i]);
				}
			}
			if (lowAspects.size() > 0) {
				for (int i=0; i<lowAspects.size(); i++) {
					((ItemWandCasting) itemstack.getItem()).addVis(itemstack, (Aspect) lowAspects.get(i), 1, true);
				}
			}	
		
		}

	}
}