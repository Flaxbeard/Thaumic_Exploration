package flaxbeard.thaumicexploration.event;

import net.minecraft.util.DamageSource;

public class DamageSourceTX extends DamageSource {
	
	public static DamageSource soulCrucible = (new DamageSourceTX("soulCrucible")).setDamageBypassesArmor();

	public DamageSourceTX(String par1Str) {
		super(par1Str);
	}

}
