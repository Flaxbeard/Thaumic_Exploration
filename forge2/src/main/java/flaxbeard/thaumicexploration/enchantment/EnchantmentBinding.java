package flaxbeard.thaumicexploration.enchantment;

import cpw.mods.fml.common.Loader;
import flaxbeard.thaumicexploration.ThaumicExploration;
import flaxbeard.thaumicexploration.integration.TTIntegration;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.enchantment.EnchantmentFireAspect;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;

public class EnchantmentBinding extends Enchantment
{
    public EnchantmentBinding(int par1, int par2)
    {
        super(par1, par2, EnumEnchantmentType.weapon);
        this.setName("binding");
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
    		if (!TTIntegration.canApplyTogether(par1Enchantment, ThaumicExploration.enchantmentBinding)) {
    			return false;
    		}
    	}
    	return !(par1Enchantment instanceof EnchantmentFireAspect);
    	
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
        return 2;
    }
}
