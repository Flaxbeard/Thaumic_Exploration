package flaxbeard.thaumicexploration.item;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IVisDiscounter;
import thaumcraft.common.config.ConfigItems;
import flaxbeard.thaumicexploration.ThaumicExploration;

public class ItemTXArmorSpecialDiscount extends ItemArmor implements IRepairable, IVisDiscounter{

        public ItemTXArmorSpecialDiscount(int par1, ItemArmor.ArmorMaterial par2EnumArmorMaterial,
                        int par3, int par4) {
                super(par2EnumArmorMaterial, par3, par4);
        }
        
        
        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
        	if (stack.getItem() == ThaumicExploration.maskEvil)
        		return "thaumicexploration:textures/models/armor/mask.png";
        	return "";
        }
        
        @Override
        public EnumRarity getRarity(ItemStack par1ItemStack) {
                return EnumRarity.rare;
        }

        @Override
        public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
                return par2ItemStack.isItemEqual(new ItemStack(ConfigItems.itemResource, 1, 2)) ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
        }

        @Override
        public int getVisDiscount() {
        	
                return 5;
        }
        
        @Override
        public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
        	if (stack.getItem() == ThaumicExploration.maskEvil)
        		list.add(StatCollector.translateToLocal("tc.visdiscount") + ": " + getVisDiscount() + "%");
        }

}