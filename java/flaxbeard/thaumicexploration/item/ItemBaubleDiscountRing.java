package flaxbeard.thaumicexploration.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import baubles.api.BaubleType;

public class ItemBaubleDiscountRing extends ItemBaubleDiscounter {
	private String[] textures = {"ringAer","ringTerra","ringIgnis","ringAqua","ringOrdo","ringPerdito"};
	public static int[] correspondingShards = {0,3,1,2,4,5};
	private IIcon[] icons = new IIcon[6];
	public ItemBaubleDiscountRing() {
		super(BaubleType.RING, new AspectList(), 0);
		this.setMaxDamage(0);
	}
	
	@Override
	public int getVisDiscount(ItemStack arg0, EntityPlayer arg1, Aspect arg2) {
		if (arg2 == Aspect.getPrimalAspects().get(arg0.getItemDamage())) {
			return 3;
		}
		return 0;
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemID, CreativeTabs tab,
                    List itemList) {
            for(int i = 0; i < 6; i++){
                    itemList.add(new ItemStack(itemID,1,i));
            }
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister) {
		for (int i = 0; i<6;i++) {
			icons[i] = par1IconRegister.registerIcon("thaumicexploration:" + this.textures[i]);
		}
    }
	
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1)
	{
		return this.icons[par1];
	}
	
    @Override
    public String getUnlocalizedName(ItemStack item) {
    	return this.getUnlocalizedName() + ":" + item.getItemDamage();
    }
}
