package flaxbeard.thaumicexploration.client.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

import org.lwjgl.opengl.GL11;


public class ItemRenderThinkTank implements IItemRenderer {
	TileEntityThinkTankRender render;
	private TileEntity dummytile;
	  
	public ItemRenderThinkTank(TileEntitySpecialRenderer render, TileEntity dummy) {
	    this.render = (TileEntityThinkTankRender) render;
	    this.dummytile = dummy;
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		if (type == IItemRenderer.ItemRenderType.ENTITY)
			GL11.glTranslatef(-0.5F, 0.0F, -0.5F);
		this.render.renderInventoryIcon(0, 0, 0, 0);
	}
}