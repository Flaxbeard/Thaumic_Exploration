package flaxbeard.thaumicexploration.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import flaxbeard.thaumicexploration.ThaumicExploration;
import flaxbeard.thaumicexploration.tile.TileEntityThinkTank;

public class TXGuiHandler implements IGuiHandler {
	public TXGuiHandler() {

	}
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getBlockTileEntity(x, y, z);
		
		switch(id) {
		case 0:
			if(entity != null && entity instanceof TileEntityThinkTank) {
				return new ContainerThinkTank(player.inventory, (TileEntityThinkTank) entity);
			} else {
				return null;
			}
		default:
			return null;
		}
	}
		
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getBlockTileEntity(x, y, z);
		
		switch(id) {
		case 0:
			if(entity != null && entity instanceof TileEntityThinkTank) {
				return new GuiThinkTank(player.inventory, (TileEntityThinkTank) entity);
			} else {
				return null;
			}
		default:
			return null;
		}
	}
}