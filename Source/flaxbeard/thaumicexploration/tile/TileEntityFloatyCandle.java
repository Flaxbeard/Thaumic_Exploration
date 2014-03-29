package flaxbeard.thaumicexploration.tile;

import java.util.ArrayList;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import thaumcraft.common.tiles.TileInfusionMatrix;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class TileEntityFloatyCandle extends TileEntity {
	ArrayList<ChunkCoordinates> matrices = new ArrayList<ChunkCoordinates>();
	
	
//	public void updateEntity() {
//		getSurroundings();
//		for (ChunkCoordinates matrix : matrices) {
//			 TileInfusionMatrix tileMatrix = (TileInfusionMatrix) this.worldObj.getBlockTileEntity(matrix.posX, matrix.posY, matrix.posZ);
//			 if (tileMatrix != null) {
//			 if (!((ArrayList<ChunkCoordinates>)(ReflectionHelper.getPrivateValue(TileInfusionMatrix.class, tileMatrix, "pedestals"))).contains(new ChunkCoordinates(this.xCoord,this.yCoord,this.zCoord))) {
//				 ArrayList<ChunkCoordinates> pedestals = ReflectionHelper.getPrivateValue(TileInfusionMatrix.class, tileMatrix, "pedestals");
//				 pedestals.add(new ChunkCoordinates(this.xCoord,this.yCoord,this.zCoord));
//				 ReflectionHelper.setPrivateValue(TileInfusionMatrix.class, tileMatrix, pedestals,"pedestals");
//				 tileMatrix.symmetry += 10;
//				 
//			 }
//			 if (tileMatrix.crafting) {
//				 System.out.println(tileMatrix.symmetry);
//				 System.out.println(tileMatrix.instability);
//			 }
//			 }
//		}
//	}
//	
//	
//	public void getSurroundings() {
//		matrices.clear();
//		for (int xx = -12; xx <= 12; xx++) {
//			for (int zz = -12; zz <= 12; zz++)
//			{
//				for (int yy = -5; yy <= 10; yy++) {
//					if ((xx != 0) || (zz != 0))
//					{
//			            int x = this.xCoord + xx;
//			            int y = this.yCoord - yy;
//			            int z = this.zCoord + zz;
//			            TileEntity te = this.worldObj.getBlockTileEntity(x, y, z);
//			            
//			            if (te != null && te instanceof TileInfusionMatrix) {
//			            	this.matrices.add(new ChunkCoordinates(x,y,z));
//			            }
//					}
//				}
//			}
//	    }
//	}
	
	
}
