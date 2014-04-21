package flaxbeard.thaumicexploration.item;

import java.util.UUID;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import baubles.api.BaubleType;

public class ItemStabilizerBelt extends ItemBauble {
	private static final UUID uuid = UUID.fromString("0d40c1fa-b89c-4f74-8295-74d484fa8c24");
	private static final AttributeModifier knockbackBoost = new AttributeModifier(uuid,"KBRESIST", 1.0D, 0).setSaved(true);

	public ItemStabilizerBelt() {
		super(BaubleType.BELT);
	}

	@Override
	public void onEquipped(ItemStack arg0, EntityLivingBase arg1) {
		EntityPlayer player = (EntityPlayer) arg1;
		//System.out.println(uuid.toString());
		if (player.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getModifier(uuid) == null) {
			player.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).applyModifier(knockbackBoost);
		}
	}

	@Override
	public void onUnequipped(ItemStack arg0, EntityLivingBase arg1) {
		EntityPlayer player = (EntityPlayer) arg1;
		if (player.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getModifier(uuid) != null) {
			player.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).removeModifier(knockbackBoost);
		}
	}
}
