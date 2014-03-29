package flaxbeard.thaumicexploration.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import flaxbeard.thaumicexploration.tile.TileEntityReplicator;

public class TileEntityReplicatorRender extends TileEntitySpecialRenderer {
	
	
	private static final ResourceLocation largeJarTexture = new ResourceLocation("thaumicexploration:textures/blocks/replicatorRunes.png");
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1,
			double d2, float f) {

         TileEntityReplicator replicator = ((TileEntityReplicator) tileentity);
         if (replicator.getStackInSlot(0) != null && replicator.validLocation() && !replicator.crafting) {
	         EntityItem entityitem = null;
	         float ticks = Minecraft.getMinecraft().renderViewEntity.ticksExisted + f;
	         GL11.glPushMatrix();
	         float h = MathHelper.sin(ticks % 32767.0F / 16.0F) * 0.05F;
	         GL11.glTranslatef((float)d0 + 0.5F, (float)d1 + 1.15F + h, (float)d2 + 0.5F);
	         GL11.glRotatef(ticks % 360.0F, 0.0F, 1.0F, 0.0F);
	         if (((replicator.getStackInSlot(0).getItem() instanceof ItemBlock))) {
	           GL11.glScalef(2.0F, 2.0F, 2.0F);
	         } else {
	           GL11.glScalef(1.0F, 1.0F, 1.0F);
	         }
	         ItemStack is = replicator.getStackInSlot(0).copy();
	         is.stackSize = 1;
	         entityitem = new EntityItem(replicator.getWorldObj(), 0.0D, 0.0D, 0.0D, is);
	         entityitem.hoverStart = 0.0F;
	         if (replicator.getStackInSlot(0).stackSize == 0) {
	             GL11.glEnable(3042);
	             GL11.glBlendFunc(770, 1);
	             GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.85F);
	         }
	         RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
	         if (!Minecraft.isFancyGraphicsEnabled())
	         {
	           GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
	           RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
	         }
	         GL11.glDisable(3042);
	         GL11.glPopMatrix();
         }
         if (replicator.getStackInSlot(0) != null && replicator.validLocation()  && replicator.crafting) {
	         EntityItem entityitem = null;
	         float ticks = Minecraft.getMinecraft().renderViewEntity.ticksExisted + f;
	         GL11.glPushMatrix();
	         float h = MathHelper.sin(ticks % 32767.0F / 16.0F) * 0.05F;
	         GL11.glTranslatef((float)d0 + 0.5F, (float)d1 + 1.15F + h, (float)d2 + 0.5F);
	         GL11.glRotatef(ticks % 360.0F, 0.0F, 1.0F, 0.0F);
	         float size = (100F-replicator.ticksLeft)/100F;

	         if (((replicator.getStackInSlot(0).getItem() instanceof ItemBlock))) {
	           GL11.glScalef(2.0F * size, 2.0F * size, 2.0F * size);
	         } else {
	           GL11.glScalef(1.0F * size, 1.0F * size, 1.0F * size);
	         }
	         ItemStack is = replicator.getStackInSlot(0).copy();
	         is.stackSize = 1;
	         entityitem = new EntityItem(replicator.getWorldObj(), 0.0D, 0.0D, 0.0D, is);
	         entityitem.hoverStart = 0.0F;

	         RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
	         if (!Minecraft.isFancyGraphicsEnabled())
	         {
	           GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
	           RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
	         }
	         GL11.glDisable(3042);
	         GL11.glPopMatrix();
         }

         
		 Tessellator tessellator = Tessellator.instance;
		 
    	 GL11.glPushMatrix();
    	 GL11.glTranslated(d0+0.5F, d1, d2+0.5F);
         this.bindTexture(largeJarTexture);

	     if (replicator.crafting && replicator.getStackInSlot(0) != null) {
				ItemStack example = replicator.getStackInSlot(0).copy();
				example.stackSize = 1;
				AspectList ot = ThaumcraftCraftingManager.getObjectTags(example);
		        ot = ThaumcraftCraftingManager.getBonusTags(example, ot);
		        ot = ot.copy();
         for (int i = 0; i<4; i++) {
        	 Aspect aspect;
        	 if (ot.getAspects().length == 1) {
        		 aspect = ot.getAspects()[0];
        	 }
        	 else if (ot.getAspects().length == 2) {
        		 aspect = ot.getAspects()[i % 2];
        	 }
        	 else if (ot.getAspects().length == 4) {
        		 aspect = ot.getAspects()[i];
        	 }
        	 else if (ot.getAspects().length == 3)
        	 {
        		 Aspect largestAspect = null;
        		 int amount = 0;
        		 for (Aspect a : ot.getAspects()) {
        			 if (ot.getAmount(a) > amount) {
        				 largestAspect = a;
        				 amount = ot.getAmount(a);
        			 }
        		 }
        		 aspect = null;
        		 if (i == 1) {
        			 aspect = ot.getAspects()[0];
        			 if (aspect == largestAspect) {
        				 aspect = ot.getAspects()[1];
        			 }
        		 }
        		 if (i == 3) {
        			 if (ot.getAspects()[0] == largestAspect) {
        				 aspect = ot.getAspects()[2];
        			 }
        			 else
        			 {
	        			 aspect = ot.getAspects()[1];
	        			 if (aspect == largestAspect) {
	        				 aspect = ot.getAspects()[2];
	        			 }
        			 }
        		 }
        		 if (i % 2 == 0)
        			 aspect = largestAspect;
        	 }
        	 else
        	 {
        		 aspect = ot.getAspects()[i];
        	 }
	         tessellator.startDrawingQuads();
	         tessellator.setBrightness(255);
	         d0 = 0.0F;
	         d1 = 0.0F;
	         d2 = 0.0F;
	         tessellator.setColorOpaque_I(aspect.getColor());
	         float offset = (ot.getAmount(aspect) - replicator.recipeEssentia.getAmount(aspect));
	         offset = offset /ot.getAmount(aspect);
	         tessellator.addVertexWithUV(d0 + 1 - 0.5F, d1 + offset, d2 - 0.001 - 0.5F, 0, 1.0 - offset);
	         tessellator.addVertexWithUV(d0 + 1 - 0.5F, d1 +0, d2 - 0.001 - 0.5F, 0,1);
	         tessellator.addVertexWithUV(d0 + 0 - 0.5F, d1 + 0, d2 - 0.001 - 0.5F, 1, 1);
	         tessellator.addVertexWithUV(d0 + 0 - 0.5F, d1 + offset,d2 - 0.001 - 0.5F, 1, 1.0 - offset);
	         tessellator.draw();
	         GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
	         
         }
	     }
         GL11.glPopMatrix();
		
	}

}
