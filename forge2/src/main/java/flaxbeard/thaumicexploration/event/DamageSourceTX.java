package flaxbeard.thaumicexploration.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

public class DamageSourceTX extends DamageSource {
	
	public static DamageSource soulCrucible = (new DamageSourceTX("soulCrucible")).setDamageBypassesArmor();

	public DamageSourceTX(String par1Str) {
		super(par1Str);
	}
	
	public static DamageSource witherPlayerDamage(EntityLivingBase par0EntityLiving)
	{
	  return new EntityDamageSource("witherMask", par0EntityLiving);
	}

}
