//package flaxbeard.thaumicexploration.client.render;
//
//import java.util.Random;
//
//import net.minecraft.block.Block;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.Tessellator;
//import net.minecraft.client.renderer.entity.RenderManager;
//import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
//import net.minecraft.entity.item.EntityItem;
//import net.minecraft.item.ItemBlock;
//import net.minecraft.item.ItemStack;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.MathHelper;
//import net.minecraft.util.ResourceLocation;
//
//import org.lwjgl.opengl.GL11;
//
//import flaxbeard.thaumicexploration.tile.TileEntityNecroPedestal;
//
//public class TileEntityNecroPedestalRenderer extends TileEntitySpecialRenderer {
//	
//	
//	private static final ResourceLocation largeJarTexture = new ResourceLocation("thaumicexploration:textures/blocks/replicatorRunes.png");
//	@Override
//	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1,
//			double d2, float f) {
//
//	         TileEntityNecroPedestal replicator = ((TileEntityNecroPedestal) tileentity);
//     	int highestFilledSlot = -1;
//		for (int i : replicator.slots) {
//			if (replicator.getStackInSlot(i) != null) {
//				highestFilledSlot = i+1;
//			}
//		}
//		//ystem.out.println(highestFilledSlot);
//         for (int i = 0; i<6; i++) {
//	         if (replicator.getStackInSlot(i) != null) {
//		         EntityItem entityitem = null;
//		         float ticks = Minecraft.getMinecraft().renderViewEntity.ticksExisted + f;
//		         float tickOffset = ticks/360;
//		         GL11.glPushMatrix();
//		         float h = MathHelper.sin(ticks % 32767.0F / 16.0F) * 0.05F;
//		         GL11.glTranslatef((float)d0 + 0.5F, (float)d1 + 2.15F + h, (float)d2 + 0.5F);
//
//		         GL11.glRotatef((ticks % 360.0F) * 10.0F  + ((360.0F/highestFilledSlot)*i), 0.0F, 1.0F, 0.0F);
//		         //System.out.println((ticks % 360.0F + ((360.0F/highestFilledSlot)*i)));
//		         GL11.glTranslatef(1.0F, MathHelper.sin((float) (Math.toRadians(ticks*10.0F+tickOffset*1000.0F+(360.0F/highestFilledSlot)*i)))/5.0F, 0.0F);
//		         GL11.glRotatef((ticks % 360.0F) * 4.5F, 0.0F, -1.0F, 0.0F);
//		         if (((replicator.getStackInSlot(i).getItem() instanceof ItemBlock))) {
//		           GL11.glScalef(2.0F, 2.0F, 2.0F);
//		         } else {
//		           GL11.glScalef(1.0F, 1.0F, 1.0F);
//		         }
//		         ItemStack is = replicator.getStackInSlot(i).copy();
//		         is.stackSize = 1;
//		         entityitem = new EntityItem(replicator.getWorldObj(), 0.0D, 0.0D, 0.0D, is);
//		         entityitem.hoverStart = 0.0F;
//		        
//		         RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
//		         if (!Minecraft.isFancyGraphicsEnabled())
//		         {
//		           GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
//		           RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
//		         }
//		         GL11.glDisable(3042);
//		         GL11.glPopMatrix();
//	         }
//         }
//    
//	}
//	
//
//
//}
