package flaxbeard.thaumicexploration.integration;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.tile.TileAltar;

public class BotaniaIntegration {
	public static Block getAltar() {
		return ModBlocks.altar;
	}
	public static boolean needsWater(TileEntity tile) {
		TileAltar altar = (TileAltar) tile;
		return !altar.hasWater;
	}
	public static void fillWater(TileEntity tile) {
		TileAltar altar = (TileAltar) tile;
		altar.hasWater = true;
	}
}
