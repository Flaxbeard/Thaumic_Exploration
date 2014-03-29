package flaxbeard.thaumicexploration.misc;

import net.minecraft.network.packet.Packet41EntityEffect;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.FakePlayer;

public class FakePlayerPotion extends FakePlayer {

	public FakePlayerPotion(World world, String name) {
		super(world, name);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    protected void onNewPotionEffect(PotionEffect par1PotionEffect)
    {
        if (!this.worldObj.isRemote)
        {
            Potion.potionTypes[par1PotionEffect.getPotionID()].applyAttributesModifiersToEntity(this, this.getAttributeMap(), par1PotionEffect.getAmplifier());
        }
    }

}
