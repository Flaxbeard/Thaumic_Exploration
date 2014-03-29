package flaxbeard.thaumicexploration.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import flaxbeard.thaumicexploration.tile.TileEntityAutoCrafter;
import flaxbeard.thaumicexploration.tile.TileEntityAutoSorter;
import flaxbeard.thaumicexploration.tile.TileEntityThinkTank;

public class TXGuiHandler implements IGuiHandler {
	public TXGuiHandler() {

	}
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getBlockTileEntity(x, y, z);
		int side = 5;
		switch(id) {
		case 0:
			if(entity != null && entity instanceof TileEntityThinkTank) {
				return new ContainerThinkTank(player.inventory, (TileEntityThinkTank) entity);
			} else {
				return null;
			}
		case 1:
			if(entity != null && entity instanceof TileEntityAutoCrafter) {
				return new ContainerAutoCrafter(player.inventory, (TileEntityAutoCrafter) entity);
			} else {
				return null;
			}
		case 3:
			side--;
		case 4:
			side--;
		case 5:
			side--;
		case 6:
			side--;
		case 7:
			side--;
		case 2:
			ItemStack stack = player.getCurrentEquippedItem();
			TileEntityAutoSorter sorter = (TileEntityAutoSorter) player.worldObj.getBlockTileEntity(stack.getTagCompound().getInteger("brainx"), stack.getTagCompound().getInteger("brainy"), stack.getTagCompound().getInteger("brainz"));
			
			return new ContainerBrainMachine(player.inventory, player,sorter,new ChunkCoordinates(x,y,z),side);
		default:
			return null;
		}
	}
		
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getBlockTileEntity(x, y, z);
		int side = 5;
		switch(id) {
		case 0:
			if(entity != null && entity instanceof TileEntityThinkTank) {
				return new GuiThinkTank(player.inventory, (TileEntityThinkTank) entity);
			} else {
				return null;
			}
		case 1:
			if(entity != null && entity instanceof TileEntityAutoCrafter) {
				return new GuiAutoCrafter(player.inventory, (TileEntityAutoCrafter) entity);
			} else {
				return null;
			}
		case 3:
			side--;
		case 4:
			side--;
		case 5:
			side--;
		case 6:
			side--;
		case 7:
			side--;
		case 2:
			ItemStack stack = player.getCurrentEquippedItem();
			TileEntityAutoSorter sorter = (TileEntityAutoSorter) player.worldObj.getBlockTileEntity(stack.getTagCompound().getInteger("brainx"), stack.getTagCompound().getInteger("brainy"), stack.getTagCompound().getInteger("brainz"));
			
			return new GuiBrainMachine(player.inventory, player,sorter,new ChunkCoordinates(x,y,z),side);
		default:
			return null;
		}
	}
}