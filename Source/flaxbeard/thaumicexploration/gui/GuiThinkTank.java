package flaxbeard.thaumicexploration.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flaxbeard.thaumicexploration.tile.TileEntityThinkTank;

@SideOnly(Side.CLIENT)
public class GuiThinkTank extends GuiContainer
{
    private static final ResourceLocation furnaceGuiTextures = new ResourceLocation("thaumicexploration:textures/gui/thinktank.png");
    private TileEntityThinkTank furnaceInventory;

    public GuiThinkTank(InventoryPlayer par1InventoryPlayer, TileEntityThinkTank par2TileEntityThinkTank)
    {
        super(new ContainerThinkTank(par1InventoryPlayer, par2TileEntityThinkTank));
        this.furnaceInventory = par2TileEntityThinkTank;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        String s = this.furnaceInventory.isInvNameLocalized() ? this.furnaceInventory.getInvName() : I18n.getString(this.furnaceInventory.getInvName());
      
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(furnaceGuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        GL11.glEnable(3042);
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        
        int i1;


        i1 = this.furnaceInventory.getCookProgressScaled(28);
        this.drawTexturedModalRect(k + 91, l + 24, 198, 11, 32, i1);
        GL11.glDisable(3042);
    }
}
