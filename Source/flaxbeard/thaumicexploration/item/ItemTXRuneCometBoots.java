package flaxbeard.thaumicexploration.item;

import net.minecraft.item.ItemArmor;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTXRuneCometBoots extends ItemTXRuneMeteorBoots {

	public ItemTXRuneCometBoots(int par1,
			ItemArmor.ArmorMaterial par2EnumArmorMaterial, int par3, int par4) {
		super(par4, par2EnumArmorMaterial, par3, par4);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1)
    {
      return super.icons[0];
    }

}
