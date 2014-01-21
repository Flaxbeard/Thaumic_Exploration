package flaxbeard.thaumicexploration.item;

import java.util.List;

import flaxbeard.thaumicexploration.ThaumicExploration;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IVisDiscounter;
import thaumcraft.common.config.ConfigItems;

public class ItemTXArmorSpecial extends ItemArmor implements IRepairable{

        public ItemTXArmorSpecial(int par1, EnumArmorMaterial par2EnumArmorMaterial,
                        int par3, int par4) {
                super(par1, par2EnumArmorMaterial, par3, par4);
        }
        
        
        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer) {
        	if (stack.itemID == ThaumicExploration.bootsMeteor.itemID)
        		return "thaumicexploration:textures/models/armor/bootsMeteor.png";
        	return "thaumicexploration:textures/models/armor/bootsComet.png";
        }
        
        @Override
        public EnumRarity getRarity(ItemStack par1ItemStack) {
                return EnumRarity.rare;
        }

        @Override
        public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
                return false;
        }
        

}