package flaxbeard.thaumicexploration.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TXPotion extends Potion {
	public TXPotion(int par1, boolean par2, int par3) {
		super(par1, par2, par3);
	}

	public Potion setIconIndex(int par1, int par2) {
		super.setIconIndex(par1, par2);
		return this;
	}
	private static final ResourceLocation icon = new ResourceLocation("thaumicexploration:textures/tabs/binding.png");
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasStatusIcon() 
	{
	    Minecraft.getMinecraft().renderEngine.bindTexture(icon);
	    return true;
	}
	
}
