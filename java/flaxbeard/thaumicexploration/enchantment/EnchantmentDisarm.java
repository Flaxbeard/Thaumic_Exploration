package flaxbeard.thaumicexploration.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLootBonus;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Loader;
import flaxbeard.thaumicexploration.ThaumicExploration;
import flaxbeard.thaumicexploration.integration.TTIntegration;

public class EnchantmentDisarm extends Enchantment
{
    public EnchantmentDisarm(int par1, int par2)
    {
        super(par1, par2, EnumEnchantmentType.weapon);
        this.setName("disarm");
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinEnchantability(int par1)
    {
        return 10 + 20 * (par1 - 1);
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int par1)
    {
        return super.getMinEnchantability(par1) + 50;
    }
    
    @Override
    public boolean canApplyTogether(Enchantment par1Enchantment) {
    	if (Loader.isModLoaded("ThaumicTinkerer")) {
    		if (!TTIntegration.canApplyTogether(par1Enchantment, ThaumicExploration.enchantmentDisarm)) {
    			return false;
    		}
    	}
    	return !(par1Enchantment instanceof EnchantmentLootBonus);
    	
    }
    
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return false;
	}

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 3;
    }
}
