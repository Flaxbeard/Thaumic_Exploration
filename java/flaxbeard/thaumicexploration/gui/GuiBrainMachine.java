package flaxbeard.thaumicexploration.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.ResourceLocation;

import org.apache.commons.lang3.tuple.MutablePair;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flaxbeard.thaumicexploration.misc.SortingInventory;
import flaxbeard.thaumicexploration.packet.TXClientPacketHandler;
import flaxbeard.thaumicexploration.tile.TileEntityAutoSorter;

@SideOnly(Side.CLIENT)
public class GuiBrainMachine extends GuiContainer {
	  private static final ResourceLocation furnaceGuiTextures = new ResourceLocation("thaumicexploration:textures/gui/brains.png");
	  private static final ResourceLocation slotOverlay = new ResourceLocation("thaumicexploration:textures/gui/brainsOverlayy.png");


	List<GuiButton> buttonListP = new ArrayList();

	public GuiBrainMachine(InventoryPlayer par1InventoryPlayer, EntityPlayer player, TileEntityAutoSorter sorter, ChunkCoordinates chunkCoordinates,int side)
    {
		
        super(new ContainerBrainMachine(par1InventoryPlayer, player, sorter, chunkCoordinates,side));

    }
	
    

	    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	    {
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        this.mc.getTextureManager().bindTexture(furnaceGuiTextures);
	        int k = (this.width - this.xSize) / 2;
	        int l = (this.height - this.ySize) / 2;
	        GL11.glEnable(3042);
	        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
//	        
//	        int i1;
//
//
//	        i1 = this.furnaceInventory.getCookProgressScaled(28);
//	        this.drawTexturedModalRect(k + 91, l + 24, 198, 11, 32, i1);
	        GL11.glDisable(3042);
	        
	        
	    }
	    
		@Override
		public void initGui() {
			super.initGui();
	        int k = (this.width - this.xSize) / 2;
	        int l = (this.height - this.ySize) / 2;
			buttonListP.clear();
			ContainerBrainMachine container = (ContainerBrainMachine) this.inventorySlots;
			buttonListP.add(new GuiButtonSelector(1, k + 53, l + 14,container.getNumber()!=0,0));
			buttonListP.add(new GuiButtonSelector(1, k + 89, l + 14,container.getNumber()!=1,1));
			buttonListP.add(new GuiButtonSelector(1, k + 125, l + 14,container.getNumber()!=2,2));
			buttonList = buttonListP;
		}
		
		@Override
		protected void actionPerformed(GuiButton par1GuiButton) {
			if (par1GuiButton instanceof GuiButtonSelector) {
				GuiButtonSelector button = (GuiButtonSelector) par1GuiButton;
				ContainerBrainMachine container = (ContainerBrainMachine) this.inventorySlots;
				TXClientPacketHandler.sendTypeChangePacket(container.te, container.cc, button.myID, container.side);
				TileEntityAutoSorter switcher = container.te;
			    SortingInventory inv = switcher.chestSorts.get(MutablePair.of(container.cc,container.side));
			    inv.type = button.myID;
			    switcher.chestSorts.put(MutablePair.of(container.cc,container.side), inv);
			    for (Object item : buttonListP) {
			    	((GuiButtonSelector) item).enabled = (((GuiButtonSelector) item).myID != button.myID);
			    }
			}
		}

}
