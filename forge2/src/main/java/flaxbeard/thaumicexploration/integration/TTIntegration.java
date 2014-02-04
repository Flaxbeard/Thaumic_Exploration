package flaxbeard.thaumicexploration.integration;

import java.lang.reflect.Method;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import vazkii.tinkerer.common.enchantment.EnchantmentVampirism;
import vazkii.tinkerer.common.enchantment.core.EnchantmentManager;
import vazkii.tinkerer.common.lib.LibEnchantIDs;
import vazkii.tinkerer.common.lib.LibMisc;
import vazkii.tinkerer.common.lib.LibResearch;
import cpw.mods.fml.relauncher.ReflectionHelper;
import flaxbeard.thaumicexploration.ThaumicExploration;

public class TTIntegration {

	public static void registerEnchants() {
		if (okVersion()) {
			try {
			EnchantmentManager.registerExponentialCostData(ThaumicExploration.enchantmentBinding, "thaumicexploration:textures/tabs/binding.png", false, new AspectList().add(Aspect.ENTROPY, 15).add(Aspect.ORDER, 15), "ENCHBINDING");
			EnchantmentManager.registerExponentialCostData(ThaumicExploration.enchantmentNightVision, "thaumicexploration:textures/tabs/nightvision.png", false, new AspectList().add(Aspect.ENTROPY, 20).add(Aspect.FIRE, 10).add(Aspect.ORDER, 20), "ENCHNIGHTVISION");
			EnchantmentManager.registerExponentialCostData(ThaumicExploration.enchantmentDisarm, "thaumicexploration:textures/tabs/disarm.png", false, new AspectList().add(Aspect.AIR, 12).add(Aspect.ORDER, 7).add(Aspect.ENTROPY, 7), "ENCHDISARM");
			}
			catch (Exception e) {
				Method m = ReflectionHelper.findMethod(EnchantmentManager.class, null, new String[] { "registerExponentialCostData" }, Enchantment.class, String.class, boolean.class, AspectList.class, String.class);
				try {
					m.invoke(null, ThaumicExploration.enchantmentBinding, "thaumicexploration:textures/tabs/binding.png", false, new AspectList().add(Aspect.ENTROPY, 15).add(Aspect.ORDER, 15), "ENCHBINDING");
					m.invoke(null, ThaumicExploration.enchantmentNightVision, "thaumicexploration:textures/tabs/nightvision.png", false, new AspectList().add(Aspect.ENTROPY, 20).add(Aspect.FIRE, 10).add(Aspect.ORDER, 20), "ENCHNIGHTVISION");
					m.invoke(null, ThaumicExploration.enchantmentDisarm, "thaumicexploration:textures/tabs/disarm.png", false, new AspectList().add(Aspect.AIR, 12).add(Aspect.ORDER, 7).add(Aspect.ENTROPY, 7), "ENCHDISARM");
					
				} catch (Exception x) {
					x.printStackTrace();
				}
			}

		}
	}
	public static boolean canApplyTogether(Enchantment par1Enchantment, Enchantment par2Enchantment) {
		if (par2Enchantment == ThaumicExploration.enchantmentBinding)
			return !(par1Enchantment instanceof EnchantmentVampirism);
		return true;
	}
	
	public static boolean okVersion() {
		String ver = LibMisc.VERSION.substring(LibMisc.VERSION.lastIndexOf("-") + 1);
		int version = Integer.parseInt(ver);
		System.out.println("!!!!!!!THAUMIC TINKERER VERSION: " + version + "!!!!!!!");
		return (version > 71);
	}
	
	public static String keyRepairer() {
		return LibResearch.KEY_REPAIRER;
	}
	
	public static int getAscentLevel(EntityPlayer player) {
		int boost = EnchantmentHelper.getMaxEnchantmentLevel(LibEnchantIDs.idAscentBoost, player.inventory.armorInventory);
		return boost;
	}
	
	public static String enchantCategory() {
		return LibResearch.CATEGORY_ENCHANTING;
	}
	
	public static String keyEnchanter() {
		return LibResearch.KEY_ENCHANTER;
	}
	
}
