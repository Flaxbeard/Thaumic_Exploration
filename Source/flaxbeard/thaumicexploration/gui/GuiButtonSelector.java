package flaxbeard.thaumicexploration.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiButtonSelector extends GuiButton {
	private static final ResourceLocation gui = new ResourceLocation("thaumicexploration:textures/gui/checkBox.png");
	int myID;
	public GuiButtonSelector(int par1, int par2, int par3, boolean enable, int id) {
		super(par1, par2, par3, 11, 11, "");
		this.enabled = enable;
		this.myID = id;
	}
	
	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		par1Minecraft.renderEngine.bindTexture(gui);
		int y = 0;
		if (this.enabled) {
			y = 11;
		}
		drawTexturedModalRect(xPosition, yPosition, 0, y, 11, 11);
	}
}
