package flaxbeard.thaumicexploration.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentOxygen;
import net.minecraft.enchantment.EnchantmentWaterWorker;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Loader;
import flaxbeard.thaumicexploration.ThaumicExploration;
import flaxbeard.thaumicexploration.integration.TTIntegration;

public class EnchantmentNightVision extends Enchantment
{
    public EnchantmentNightVision(int par1, int par2)
    {
        super(par1, par2, EnumEnchantmentType.armor_head);
        this.setName("nightVision");
    }
    
    public int getMinEnchantability(int par1)
    {
        return 40;
    }

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return false;
	}

    @Override
    public boolean canApplyTogether(Enchantment par1Enchantment) {
    	if (Loader.isModLoaded("ThaumicTinkerer")) {
    		if (!TTIntegration.canApplyTogether(par1Enchantment, ThaumicExploration.enchantmentNightVision)) {
    			return false;
    		}
    	}
    	return (!(par1Enchantment instanceof EnchantmentOxygen) && !(par1Enchantment instanceof EnchantmentWaterWorker));
    	
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 1;
    }
}
