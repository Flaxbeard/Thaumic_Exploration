package flaxbeard.thaumicexploration.wand;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import thaumcraft.api.wands.IWandRodOnUpdate;

public class WandRodBreadOnUpdate implements IWandRodOnUpdate {

	@Override
	public void onUpdate(ItemStack itemstack, EntityPlayer player) {
		if (player.worldObj.rand.nextInt(1000)==0) {
			int i = player.worldObj.rand.nextInt(5);
			player.addChatMessage(new ChatComponentTranslation("<" + itemstack.getDisplayName() + "> " + StatCollector.translateToLocal("thaumicexploration.bread"+i),new Object()));
		}
	}

}
