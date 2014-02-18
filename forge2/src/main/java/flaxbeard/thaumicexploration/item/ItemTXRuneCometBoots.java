package flaxbeard.thaumicexploration.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.util.Icon;

public class ItemTXRuneCometBoots extends ItemTXRuneMeteorBoots {

	public ItemTXRuneCometBoots(int par1,
			EnumArmorMaterial par2EnumArmorMaterial, int par3, int par4) {
		super(par1, par2EnumArmorMaterial, par3, par4);
		// TODO Auto-generated constructor stub
	}
	
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int par1)
    {
      return super.icons[0];
    }

}
