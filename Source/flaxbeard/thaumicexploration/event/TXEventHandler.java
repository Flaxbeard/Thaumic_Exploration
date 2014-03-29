package flaxbeard.thaumicexploration.event;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAICreeperSwell;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;

import org.apache.commons.lang3.tuple.MutablePair;
import org.lwjgl.opengl.GL11;

import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.ITaintedMob;
import thaumcraft.common.entities.golems.ItemGolemBell;
import thaumcraft.common.entities.golems.ItemGolemPlacer;
import thaumcraft.common.lib.world.DamageSourceThaumcraft;
import thaumcraft.common.tiles.TileJarFillable;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flaxbeard.thaumicexploration.ThaumicExploration;
import flaxbeard.thaumicexploration.ai.EntityAICreeperDummy;
import flaxbeard.thaumicexploration.ai.EntityAINearestAttackablePureTarget;
import flaxbeard.thaumicexploration.data.TXWorldData;
import flaxbeard.thaumicexploration.tile.TileEntityAutoSorter;
import flaxbeard.thaumicexploration.tile.TileEntityBoundChest;
import flaxbeard.thaumicexploration.tile.TileEntityBoundJar;

public class TXEventHandler {
	private HashMap<String,ArrayList<EntityItem>> lastKilled = new HashMap<String,ArrayList<EntityItem>>();
	public TXEventHandler() {
		//System.out.println("TEST123");
	}
	
	@SubscribeEvent
	public void handleWorldLoad(WorldEvent.Load event) {
		TXWorldData.get(event.world);
	}
	
	@SideOnly(Side.CLIENT)
	  @SubscribeEvent
	  public void renderLast(RenderWorldLastEvent event)
	  {
	    float partialTicks = event.partialTicks;
	    Minecraft mc = Minecraft.getMinecraft();
	    if ((Minecraft.getMinecraft().renderViewEntity instanceof EntityPlayer))
	    {
	      EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().renderViewEntity;
	      long time = System.currentTimeMillis();
	      if ((player.inventory.getCurrentItem() != null) && (((player.inventory.getCurrentItem().getItem() instanceof ItemGolemPlacer)) || ((player.inventory.getCurrentItem().getItem() instanceof ItemGolemBell)))) {

	    	  renderMarkedBlocks(event, partialTicks, player, time);
	      }
	    }
	  }
	
	 @SideOnly(Side.CLIENT)
	  public void renderMarkedBlocks(RenderWorldLastEvent event, float partialTicks, EntityPlayer player, long time)
	  {
	    Minecraft mc = Minecraft.getMinecraft();
	    if ((player.inventory.getCurrentItem().hasTagCompound()) && (!player.inventory.getCurrentItem().stackTagCompound.hasKey("golemid")))
	    {
	      Entity golem = null;
	      ChunkCoordinates cc = null;
	      int face = -1;
	      if ((player.inventory.getCurrentItem().getItem() instanceof ItemGolemBell))
	      {
	    	  
	        ItemStack item = player.inventory.getCurrentItem();
	    	cc = new ChunkCoordinates(item.stackTagCompound.getInteger("brainx"),item.stackTagCompound.getInteger("brainy"),item.stackTagCompound.getInteger("brainz"));
	        
	    	//cc = ItemGolemBell.getGolemHomeCoords(player.inventory.getCurrentItem());
	        //face = ItemGolemBell.getGolemHomeFace(player.inventory.getCurrentItem());
	        
	       // int gid = ItemGolemBell.getGolemId(player.inventory.getCurrentItem());
	        //if (gid > -1) {
	        //  golem = player.worldObj.getEntityByID(gid);
	        //}
	        //if ((golem == null) || (!(golem instanceof EntityGolemBase))) {
	        //  return;
	        //}
	    	if (player.worldObj.getTileEntity(item.stackTagCompound.getInteger("brainx"),item.stackTagCompound.getInteger("brainy"),item.stackTagCompound.getInteger("brainz")) == null || !(player.worldObj.getTileEntity(item.stackTagCompound.getInteger("brainx"),item.stackTagCompound.getInteger("brainy"),item.stackTagCompound.getInteger("brainz")) instanceof TileEntityAutoSorter)) {
	    		return;
	    	}
	      }
	      GL11.glPushMatrix();
	      GL11.glAlphaFunc(516, 0.003921569F);
	      if ((cc != null) && (player.getDistanceSq(cc.posX, cc.posY, cc.posZ) < 4096.0D))
	      {

		    for (int i = 0; i<6;i++) {
		        GL11.glPushMatrix();
		        
		        drawGolemHomeOverlay(cc.posX+ForgeDirection.getOrientation(i).offsetX, cc.posY+ForgeDirection.getOrientation(i).offsetY, cc.posZ+ForgeDirection.getOrientation(i).offsetZ, i, partialTicks);
		        GL11.glPopMatrix();
	    	}
	      }
	      ItemStack item = player.inventory.getCurrentItem();
	      TileEntityAutoSorter sorter = (TileEntityAutoSorter) player.worldObj.getTileEntity(item.stackTagCompound.getInteger("brainx"),item.stackTagCompound.getInteger("brainy"),item.stackTagCompound.getInteger("brainz"));
	      for (MutablePair chest : sorter.chests)
	      {
	    	  ChunkCoordinates coord = (ChunkCoordinates) chest.left;
	    	  int s = (Integer) chest.right;
	        //NBTTagCompound nbttagcompound1 = (NBTTagCompound)tl.tagAt(q);
	        double x = coord.posX;
	        double y = coord.posY;
	        double z = coord.posZ;
	        int ox = coord.posX;
	        int oy = coord.posY;
	        int oz = coord.posZ;


		        x += ForgeDirection.getOrientation(s).offsetX;
		        y += ForgeDirection.getOrientation(s).offsetY;
		        z += ForgeDirection.getOrientation(s).offsetZ;
		        
		          if (player.getDistanceSq(x, y, z) < 4096.0D)
		          {
		            GL11.glPushMatrix();
		            drawMarkerOverlay(x, y, z, s, partialTicks, 0);
		            GL11.glPopMatrix();
		            if (player.worldObj.isAirBlock(ox, oy, oz))
		            {
		              GL11.glPushMatrix();
		              //for (int a = 0; a < 6; a++) {
		                drawAirBlockoverlay(ox + ForgeDirection.getOrientation(s).offsetX, oy + ForgeDirection.getOrientation(s).offsetY, oz + ForgeDirection.getOrientation(s).offsetZ, s, partialTicks, 0);
		              //}
		              GL11.glPopMatrix();
		            }
		            if ((Config.golemLinkQuality > 3))
		            {
		              x -= ForgeDirection.getOrientation(s).offsetX * 0.5D;
		              y -= ForgeDirection.getOrientation(s).offsetY * 0.5D;
		              z -= ForgeDirection.getOrientation(s).offsetZ * 0.5D;
		              GL11.glPushMatrix();
		              drawMarkerLine(x, y, z, s, partialTicks, 0, sorter);
		              GL11.glPopMatrix();
		            }
		          }
		        
	        }
	      }
	      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	      GL11.glAlphaFunc(516, 0.1F);
	      GL11.glPopMatrix();
	    
	  }
	 
	 public void drawMarkerOverlay(double x, double y, double z, int side, float partialTicks, int color)
	  {
	    float r = 1.0F;
	    float g = 1.0F;
	    float b = 1.0F;
	    EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().renderViewEntity;
	    double iPX = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
	    double iPY = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
	    double iPZ = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;
	    
	    float time = (float)(System.nanoTime() / 30000000L);
	    if (color == -1)
	    {
	      r = MathHelper.sin(time % 32767.0F / 12.0F + side) * 0.2F + 0.8F;
	      g = MathHelper.sin(time % 32767.0F / 14.0F + side) * 0.2F + 0.8F;
	      b = MathHelper.sin(time % 32767.0F / 16.0F + side) * 0.2F + 0.8F;
	    }
	    else
	    {
	      Color cc = new Color(UtilsFX.colors[color]);
	      r = cc.getRed() / 255.0F;
	      g = cc.getGreen() / 255.0F;
	      b = cc.getBlue() / 255.0F;
	    }
	    GL11.glPushMatrix();
	    GL11.glDepthMask(false);
	    
	    GL11.glDisable(2884);
	    GL11.glEnable(3042);
	    GL11.glBlendFunc(770, 1);
	    ForgeDirection dir = ForgeDirection.getOrientation(side);
	    
	    GL11.glTranslated(-iPX + x + 0.5D + dir.offsetX * 0.01F, -iPY + y + 0.5D + dir.offsetY * 0.01F, -iPZ + z + 0.5D + dir.offsetZ * 0.01F);
	    GL11.glRotatef(90.0F, -dir.offsetY, dir.offsetX, -dir.offsetZ);
	    
	    GL11.glPushMatrix();
	    if (dir.offsetZ < 0) {
	      GL11.glTranslated(0.0D, 0.0D, 0.5D);
	    } else {
	      GL11.glTranslated(0.0D, 0.0D, -0.5D);
	    }
	    GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
	    GL11.glScalef(0.4F, 0.4F, 0.4F);
	    UtilsFX.renderQuadCenteredFromTexture("textures/misc/mark.png", 1.0F, r, g, b, 200, 1, 1.0F);
	    GL11.glPopMatrix();
	    

	    GL11.glDisable(3042);
	    GL11.glEnable(2884);
	    
	    GL11.glDepthMask(true);
	    GL11.glPopMatrix();
	  }
	 
	 public void drawMarkerLine(double x, double y, double z, int side, float partialTicks, int color, TileEntityAutoSorter sorter)
	  {
	    EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().renderViewEntity;
	    double iPX = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
	    double iPY = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
	    double iPZ = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;
	    
	    double ePX = sorter.xCoord * partialTicks;
	    double ePY = sorter.yCoord * partialTicks;
	    double ePZ = sorter.zCoord * partialTicks;
	    
	    GL11.glTranslated(-iPX + ePX, -iPY + ePY + 0.5F, -iPZ + ePZ);
	    
	    float r = 1.0F;
	    float g = 1.0F;
	    float b = 1.0F;
	    float time = (float)(System.nanoTime() / 30000000L);
	    if (color > -1)
	    {
	      Color co = new Color(UtilsFX.colors[color]);
	      r = co.getRed() / 255.0F;
	      g = co.getGreen() / 255.0F;
	      b = co.getBlue() / 255.0F;
	    }
	    GL11.glDepthMask(false);
	    GL11.glEnable(3042);
	    GL11.glBlendFunc(770, 1);
	    
	    Tessellator tessellator = Tessellator.instance;
	    
	    double ds1x = ePX;
	    double ds1y = ePY + 0.5F;
	    double ds1z = ePZ;
	    double dd1x = x + 0.5D + ForgeDirection.getOrientation(side).offsetX * 0.5D;
	    double dd1y = y + 0.5D + ForgeDirection.getOrientation(side).offsetY * 0.5D;
	    double dd1z = z + 0.5D + ForgeDirection.getOrientation(side).offsetZ * 0.5D;
	    double dc1x = (float)(dd1x - ds1x);
	    double dc1y = (float)(dd1y - ds1y);
	    double dc1z = (float)(dd1z - ds1z);
	    
	    double ds2x = x + 0.5D;
	    double ds2y = y + 0.5D;
	    double ds2z = z + 0.5D;
	    

	    double dc22x = (float)(ds2x - ds1x);
	    double dc22y = (float)(ds2y - ds1y);
	    double dc22z = (float)(ds2z - ds1z);
	    
	    UtilsFX.bindTexture("textures/misc/script.png");
	    GL11.glDisable(2884);
	    tessellator.startDrawing(5);
	    



	    float f4 = 0.0F;
	    




	    double dx2 = 0.0D;
	    double dy2 = 0.0D;
	    double dz2 = 0.0D;
	    double d3 = x - ePX;
	    double d4 = y - ePY;
	    double d5 = z - ePZ;
	    
	    float dist = MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
	    float blocks = Math.round(dist);
	    float length = blocks * Config.golemLinkQuality;
	    
	    float f9 = 0.0F;
	    float f10 = 1.0F;
	    
	    int count = 0;
	    for (int i = 0; i <= length; i++)
	    {
	      float f2 = i / length;
	      float f2a = i * 1.5F / length;
	      f2a = Math.min(0.75F, f2a);
	      float f3 = 1.0F - Math.abs(i - length / 2.0F) / (length / 2.0F);
	      f4 = 0.0F;
	      if (color == -1)
	      {
	        r = MathHelper.sin(time % 32767.0F / 12.0F + side + i) * 0.2F + 0.8F;
	        g = MathHelper.sin(time % 32767.0F / 14.0F + side + i) * 0.2F + 0.8F;
	        b = MathHelper.sin(time % 32767.0F / 16.0F + side + i) * 0.2F + 0.8F;
	      }
	      double dx = dc1x + MathHelper.sin((float)((side * 20 + z % 16.0D + dist * (1.0F - f2) * Config.golemLinkQuality - time % 32767.0F / 5.0F) / 4.0D)) * 0.5F * f3;
	      double dy = dc1y + MathHelper.sin((float)((side * 20 + x % 16.0D + dist * (1.0F - f2) * Config.golemLinkQuality - time % 32767.0F / 5.0F) / 3.0D)) * 0.5F * f3;
	      double dz = dc1z + MathHelper.sin((float)((side * 20 + y % 16.0D + dist * (1.0F - f2) * Config.golemLinkQuality - time % 32767.0F / 5.0F) / 2.0D)) * 0.5F * f3;
	      if (i > length - Config.golemLinkQuality / 2)
	      {
	        dx2 = dc22x + MathHelper.sin((float)((side * 20 + z % 16.0D + dist * (1.0F - f2) * Config.golemLinkQuality - time % 32767.0F / 5.0F) / 4.0D)) * 0.5F * f3;
	        dy2 = dc22y + MathHelper.sin((float)((side * 20 + x % 16.0D + dist * (1.0F - f2) * Config.golemLinkQuality - time % 32767.0F / 5.0F) / 3.0D)) * 0.5F * f3;
	        dz2 = dc22z + MathHelper.sin((float)((side * 20 + y % 16.0D + dist * (1.0F - f2) * Config.golemLinkQuality - time % 32767.0F / 5.0F) / 2.0D)) * 0.5F * f3;
	        
	        f3 = (length - i) / (Config.golemLinkQuality / 2.0F);
	        f4 = 1.0F - f3;
	        dx = dx * f3 + dx2 * f4;
	        dy = dy * f3 + dy2 * f4;
	        dz = dz * f3 + dz2 * f4;
	      }
	      tessellator.setColorRGBA_F(r, g, b, f2a * (1.0F - f4));
	      

	      float f13 = (1.0F - f2) * dist - time * 0.005F;
	      
	      tessellator.addVertexWithUV(dx * f2, dy * f2 - 0.05D, dz * f2, f13, f10);
	      tessellator.addVertexWithUV(dx * f2, dy * f2 + 0.05D, dz * f2, f13, f9);
	    }
	    tessellator.draw();
	    
	    GL11.glEnable(2884);
	    GL11.glDisable(3042);
	    GL11.glDepthMask(true);
	  }
	 
	  public void drawAirBlockoverlay(double x, double y, double z, int side, float partialTicks, int color)
	  {
	    float r = 1.0F;
	    float g = 1.0F;
	    float b = 1.0F;
	    EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().renderViewEntity;
	    double iPX = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
	    double iPY = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
	    double iPZ = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;
	    
	    float time = (float)(System.nanoTime() / 30000000L);
	    if (color == -1)
	    {
	      r = MathHelper.sin(time % 32767.0F / 12.0F + side) * 0.2F + 0.8F;
	      g = MathHelper.sin(time % 32767.0F / 14.0F + side) * 0.2F + 0.8F;
	      b = MathHelper.sin(time % 32767.0F / 16.0F + side) * 0.2F + 0.8F;
	    }
	    else
	    {
	      Color cc = new Color(UtilsFX.colors[color]);
	      r = cc.getRed() / 255.0F;
	      g = cc.getGreen() / 255.0F;
	      b = cc.getBlue() / 255.0F;
	    }
	    GL11.glPushMatrix();
	    GL11.glDepthMask(false);
	    
	    GL11.glDisable(2884);
	    GL11.glEnable(3042);
	    GL11.glBlendFunc(770, 1);
	    ForgeDirection dir = ForgeDirection.getOrientation(side);
	    
	    GL11.glTranslated(-iPX + x + 0.5D - dir.offsetX * 0.01F, -iPY + y + 0.5D - dir.offsetY * 0.01F, -iPZ + z + 0.5D - dir.offsetZ * 0.01F);
	    GL11.glRotatef(90.0F, -dir.offsetY, dir.offsetX, -dir.offsetZ);
	    
	    GL11.glPushMatrix();
	    if (dir.offsetZ < 0) {
	      GL11.glTranslated(0.0D, 0.0D, 0.5D);
	    } else {
	      GL11.glTranslated(0.0D, 0.0D, -0.5D);
	    }
	    GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
	    GL11.glScalef(0.98F, 0.98F, 0.98F);
	    UtilsFX.renderQuadCenteredFromTexture("textures/blocks/empty.png", 1.0F, r, g, b, 200, 1, 1.0F);
	    GL11.glPopMatrix();
	    

	    GL11.glDisable(3042);
	    GL11.glEnable(2884);
	    
	    GL11.glDepthMask(true);
	    GL11.glPopMatrix();
	  }
	 
	  public void drawGolemHomeOverlay(double x, double y, double z, int side, float partialTicks)
	  {
	    float r = 1.0F;
	    float g = 1.0F;
	    float b = 1.0F;
	    EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().renderViewEntity;
	    double iPX = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
	    double iPY = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
	    double iPZ = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;
	    
	    float time = (float)(System.nanoTime() / 30000000L);
	    
	    r = MathHelper.sin(time % 32767.0F / 12.0F + side) * 0.2F + 0.8F;
	    g = MathHelper.sin(time % 32767.0F / 14.0F + side) * 0.2F + 0.8F;
	    b = MathHelper.sin(time % 32767.0F / 16.0F + side) * 0.2F + 0.8F;
	    GL11.glPushMatrix();
	    GL11.glDepthMask(false);
	    
	    GL11.glDisable(2884);
	    GL11.glEnable(3042);
	    GL11.glBlendFunc(770, 1);
	    ForgeDirection dir = ForgeDirection.getOrientation(side);
	    
	    GL11.glTranslated(-iPX + x + 0.5D + dir.offsetX * 0.01F, -iPY + y + 0.5D + dir.offsetY * 0.01F, -iPZ + z + 0.5D + dir.offsetZ * 0.01F);
	    GL11.glRotatef(90.0F, -dir.offsetY, dir.offsetX, -dir.offsetZ);
	    
	    GL11.glPushMatrix();
	    if (dir.offsetZ < 0) {
	      GL11.glTranslated(0.0D, 0.0D, 0.5D);
	    } else {
	      GL11.glTranslated(0.0D, 0.0D, -0.5D);
	    }
	    GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
	    GL11.glScalef(0.65F, 0.65F, 0.65F);
	    UtilsFX.renderQuadCenteredFromTexture("textures/misc/home.png", 1.0F, r, g, b, 200, 1, 1.0F);
	    GL11.glPopMatrix();
	    

	    GL11.glDisable(3042);
	    GL11.glEnable(2884);
	    
	    GL11.glDepthMask(true);
	    GL11.glPopMatrix();
	  }
	

//	@SideOnly(Side.CLIENT)
//	@SubscribeEvent
//	public void onSound(SoundLoadEvent event) {
//		event.manager.addSound("thaumicexploration:necroInfusion.ogg");
//		for (int i=0;i<8;i++) {
//			event.manager.addSound("thaumicexploration:necroInfusion"+TileEntityNecroPedestal.getLetterFromNumber(i)+".ogg");
//		}
//	}
	

	@SubscribeEvent
	public void handleTaintSpawns(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityPlayer) {
	
			//PacketHandler.sendAspectDiscoveryPacket(ThaumicExploration.fakeAspectNecro.getTag(), (EntityPlayerMP)event.entity);
//			PlayerKnowledge rp = Thaumcraft.proxy.getPlayerKnowledge();
//			rp.addDiscoveredAspect(((EntityPlayer)event.entity).username, ThaumicExploration.fakeAspectNecro);
//			if (rp.hasDiscoveredAspect(((EntityPlayer)event.entity).username, ThaumicExploration.fakeAspectNecro)) {
//				System.out.println(event.entity.worldObj.isRemote+" has discovered fake aspect");
//			}
		}
		if (event.entity instanceof ITaintedMob) {
			EntityLiving mob = (EntityLiving) event.entity;
			List<EntityAITaskEntry> tasksToRemove = new ArrayList<EntityAITaskEntry>();
			for ( Object entry : mob.targetTasks.taskEntries)
			{
				EntityAITaskEntry entry2 = (EntityAITaskEntry)entry;
				if (entry2.action instanceof EntityAINearestAttackableTarget)
				{
					tasksToRemove.add((EntityAITaskEntry) entry);
				}
			}
			for (EntityAITaskEntry entry : tasksToRemove)
			{
				mob.targetTasks.removeTask(entry.action);
			}
			//System.out.println("brainwashed1");
			mob.targetTasks.addTask(1, new EntityAINearestAttackablePureTarget((EntityCreature) mob, EntityPlayer.class, 0, true)); 
		}
	}

	@SubscribeEvent
	public void handleMobDrop(LivingDropsEvent event) {
		if (event.source == DamageSourceTX.soulCrucible) {
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void handleTeleport(EnderTeleportEvent event) {
		if (event.entityLiving instanceof EntityEnderman || event.entityLiving instanceof EntityPlayer) {
			if (event.entityLiving.isPotionActive(ThaumicExploration.potionBinding)) {
				event.setCanceled(true);
			}
		}
	}
	
	
	@SubscribeEvent
	public void stopCreeperExplosions(LivingUpdateEvent event) {
		if (event.entityLiving.getEquipmentInSlot(4) != null) {
			ItemStack heldItem = event.entityLiving.getEquipmentInSlot(4);
			int nightVision = EnchantmentHelper.getEnchantmentLevel(ThaumicExploration.enchantmentNightVision.effectId, heldItem);
			if(nightVision > 0 && (!event.entityLiving.isPotionActive(Potion.nightVision.id) || event.entityLiving.getActivePotionEffect(Potion.nightVision).getDuration() < 202)) {
				event.entityLiving.addPotionEffect(new PotionEffect(Potion.nightVision.id, 202, 1));
			}
		}
		if (event.entityLiving instanceof EntityCreeper && event.entityLiving.isPotionActive(ThaumicExploration.potionBinding)) {
            EntityCreeper creeper = (EntityCreeper) event.entityLiving;
            int size = creeper.tasks.taskEntries.size();
			for(int i = 0; i<size; i++) {
				EntityAITaskEntry entry = (EntityAITaskEntry) creeper.tasks.taskEntries.get(i);
            	if(entry.action instanceof EntityAICreeperSwell) {
            		entry.action = new EntityAICreeperDummy(creeper);
            		creeper.tasks.taskEntries.set(i, entry);
            	}
            }
			//ReflectionHelper.setPrivateValue(EntityCreeper.class, (EntityCreeper) event.entityLiving, 2, LibObfuscation.TIME_SINCE_IGNITED);
		}
		else if (event.entityLiving instanceof EntityCreeper) {
			EntityCreeper creeper = (EntityCreeper) event.entityLiving;
            int size = creeper.tasks.taskEntries.size();
			for(int i = 0; i<size; i++) {
				EntityAITaskEntry entry = (EntityAITaskEntry) creeper.tasks.taskEntries.get(i);
            	if(entry.action instanceof EntityAICreeperDummy) {
            		entry.action = new EntityAICreeperSwell(creeper);
            		creeper.tasks.taskEntries.set(i, entry);
            		creeper.setCreeperState(1);
            	}
            	
            }
		}
	}
	

//	@SubscribeEvent
//	public void handleTaintSeeds(BlockEvent.HarvestDropsEvent event) {
//		if (event.drops.size() > 0 && !event.drops.contains(Item.itemsList[Block.tallGrass]) && event.block == Block.tallGrass && event.world.getBiomeGenForCoords(event.x, event.z) == ThaumcraftWorldGenerator.biomeTaint) {
//			event.drops.clear();
//			event.drops.add(new ItemStack(Item.arrow));
//		}
//	}


	
	
	@SubscribeEvent
	public void handleEnchantmentAttack(LivingAttackEvent event) {
		if ((event.entityLiving instanceof EntityEnderman || event.entityLiving instanceof EntityCreeper || event.entityLiving instanceof EntityPlayer)&& event.source.getSourceOfDamage() instanceof EntityLivingBase) {
			EntityLivingBase attacker = (EntityLivingBase) event.source.getSourceOfDamage();
			ItemStack heldItem = attacker.getHeldItem();
			if(heldItem == null)
				return;

			int binding = EnchantmentHelper.getEnchantmentLevel(ThaumicExploration.enchantmentBinding.effectId, heldItem);
			if(binding > 1) {
				event.entityLiving.addPotionEffect(new PotionEffect(ThaumicExploration.potionBinding.id, 50, 1));
			}
		}
		if (event.source.getSourceOfDamage() instanceof EntityLivingBase) {
			EntityLivingBase attacker = (EntityLivingBase) event.source.getSourceOfDamage();
			ItemStack heldItem = attacker.getHeldItem();
			if(heldItem == null)
				return;

			int binding = EnchantmentHelper.getEnchantmentLevel(ThaumicExploration.enchantmentBinding.effectId, heldItem);
			if (binding > 0) {
				event.entityLiving.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 50, 1));
			}
		}
		if (event.source.getSourceOfDamage() instanceof EntityLivingBase) {
			EntityLivingBase attacker = (EntityLivingBase) event.source.getSourceOfDamage();
			ItemStack heldItem = attacker.getHeldItem();
			if(heldItem == null)
				return;

			int disarm = EnchantmentHelper.getEnchantmentLevel(ThaumicExploration.enchantmentDisarm.effectId, heldItem);
			if (disarm > 0 && !(event.entityLiving instanceof EntityPlayer)) {
				if (event.entityLiving.getHeldItem() != null && !event.entityLiving.worldObj.isRemote && event.entityLiving.worldObj.rand.nextInt(10-(2*disarm)) == 0) {
					ItemStack itemstack = event.entityLiving.getHeldItem();
					event.entityLiving.setCurrentItemOrArmor(0, null);
					World world = event.entityLiving.worldObj;
					double x = event.entityLiving.posX;
					double y = event.entityLiving.posY;
					double z = event.entityLiving.posZ;
					float f = world.rand.nextFloat() * 0.8F + 0.1F;
	                float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
	                float f2 = world.rand.nextFloat();
	                EntityItem entityitem;
			    	int k1 = world.rand.nextInt(21) + 10;
		
		            k1 = itemstack.stackSize;
		
		            entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));
		            float f3 = 0.05F;
		            entityitem.motionX = (double)((float)world.rand.nextGaussian() * f3);
		            entityitem.motionY = (double)((float)world.rand.nextGaussian() * f3 + 0.2F);
		            entityitem.motionZ = (double)((float)world.rand.nextGaussian() * f3);
		
		            if (itemstack.hasTagCompound())
		            {
		                entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
		            }
		            world.spawnEntityInWorld(entityitem);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void handleTaint(LivingHurtEvent event) {
		if (event.entityLiving.worldObj.rand.nextInt(4) < 3) {
		if (event.source.damageType == "mob") {
			if (event.source.getSourceOfDamage() instanceof ITaintedMob) {
				if (event.entityLiving instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) event.entityLiving;
					
//					List<String> completed = Thaumcraft.proxy.getResearchManager().getResearchForPlayerSafe(player.username);
//				    if ((completed != null) && (completed.contains("CRUCIBLE")) && (completed.contains("NITOR")))
//				    {
//				    	
//				      completed.remove("CRUCIBLE");
//				      completed.remove("NITOR");
//				      completed = new ArrayList();
//				      completed.add("TAINTURGE");
//				      completed.add("TAINTURGE2");
//				      Thaumcraft.proxy.getCompletedResearch().put(player.username, completed);
//				    }
//				    //Thaumcraft.proxy.getResearchManager().updateResearchNBT(player);
//				    
//				    ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
//			        DataOutputStream outputStream = new DataOutputStream(bos);
//			        try
//			        {
//			            outputStream.writeByte(6);
//			            outputStream.writeInt(player.worldObj.provider.dimensionId);
//			            outputStream.writeInt(player.entityId);
//			           
//			        }
//			        catch (Exception ex)
//			        {
//			            ex.printStackTrace();
//			        }
//			
//			        Packet250CustomPayload packet = new Packet250CustomPayload();
//			        packet.channel = "tExploration";
//			        packet.data = bos.toByteArray();
//			        packet.length = bos.size();
//			        PacketDispatcher.sendPacketToPlayer(packet, (Player) player);
					
					for (int i = 0; i<10; i++) {
						//if (player.inventory.getStackInSlot(i) != null)
							
						if (player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).getItem() == ThaumicExploration.charmNoTaint) {
							event.setCanceled(true);
							break;
						}
					}
				}
			}
		}
		if (event.source == DamageSourceThaumcraft.taint  || event.source == DamageSourceThaumcraft.tentacle  || event.source == DamageSourceThaumcraft.swarm ) {
			if (event.entityLiving instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.entityLiving;
				for (int i = 0; i<10; i++) {
					if (player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).getItem() == ThaumicExploration.charmNoTaint) {
						event.setCanceled(true);
						break;
					}
				}
					
			}
		}
	}
	}
	
	@SubscribeEvent
	public void handleItemUse(PlayerInteractEvent event) {
		byte type = 0;
		
		if (event.entityPlayer.worldObj.blockExists(event.x, event.y, event.z)) {
			if (event.entityPlayer.getCurrentEquippedItem() != null) {
				if (event.entityPlayer.getCurrentEquippedItem().getItem() == ConfigItems.itemGolemBell) {
					ItemStack stack  =event.entityPlayer.getCurrentEquippedItem();
					if (event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z) == ThaumicExploration.autoSorter) {
						
						if (stack.hasTagCompound())
					      {
					        stack.getTagCompound().removeTag("golemid");
					        stack.getTagCompound().removeTag("markers");
					        stack.getTagCompound().removeTag("golemhomex");
					        stack.getTagCompound().removeTag("golemhomey");
					        stack.getTagCompound().removeTag("golemhomez");
					        stack.getTagCompound().removeTag("golemhomeface");
					        stack.getTagCompound().setInteger("brainx", event.x);
					        stack.getTagCompound().setInteger("brainy", event.y);
					        stack.getTagCompound().setInteger("brainz", event.z);
					        stack.getTagCompound().setInteger("brain", event.z);
					      }
					      if (event.entityPlayer.worldObj.isRemote)
					      {
					        if (event.entityPlayer != null) {
					        	event.entityPlayer.swingItem();
					        }
					      }
					}
					if (!stack.hasTagCompound()) {
						stack.setTagCompound(new NBTTagCompound());
					}
					if (event.entityPlayer.worldObj.getTileEntity(event.x, event.y, event.z) != null && (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("golemid"))) {
						if (stack.getTagCompound().hasKey("brainx")) {
					        ChunkCoordinates cc = new ChunkCoordinates(event.x,event.y,event.z);
							if (event.entityPlayer.worldObj.getTileEntity(event.x, event.y, event.z) instanceof IInventory) {
								TileEntityAutoSorter sorter = (TileEntityAutoSorter) event.entityPlayer.worldObj.getTileEntity(stack.getTagCompound().getInteger("brainx"), stack.getTagCompound().getInteger("brainy"), stack.getTagCompound().getInteger("brainz"));
								if (sorter.chests.contains(MutablePair.of(cc,event.face))) {
									sorter.chests.remove(MutablePair.of(cc,event.face));
								}
								else
								{
							        if (!event.entityPlayer.worldObj.isRemote)
							        {
							            FMLNetworkHandler.openGui(event.entityPlayer, ThaumicExploration.instance, 2+event.face, event.entityPlayer.worldObj, event.x,event.y,event.z);
							            

							        }
									sorter.chests.add(MutablePair.of(cc,event.face));
								}

							}
						}

					}
						
				}
			}
			//System.out.println(event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z) + " " + ThaumicExploration.boundChest);
			if (event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z) == Blocks.chest) {

				if (event.entityPlayer.inventory.getCurrentItem() != null){ 
					if (event.entityPlayer.inventory.getCurrentItem().getItem() == ThaumicExploration.chestSeal) {
						type = 1;
					}
					else if (event.entityPlayer.inventory.getCurrentItem().getItem() == ThaumicExploration.chestSealLinked) {
						type = 2;
					}
				}
			}
			else if (event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z) == ThaumicExploration.boundChest) {
				World world = event.entityPlayer.worldObj;
				//System.out.println(event.entityPlayer.worldObj.isRemote + ItemBlankSeal.itemNames[((TileEntityBoundChest) world.getBlockTileEntity(event.x, event.y, event.z)).getSealColor()]);
				if (event.entityPlayer.inventory.getCurrentItem() != null){ 
					if (event.entityPlayer.inventory.getCurrentItem().getItem() == ThaumicExploration.chestSeal) {
						int color = ((TileEntityBoundChest) world.getTileEntity(event.x, event.y, event.z)).clientColor;		
						type = 3;
						if (15-(event.entityPlayer.inventory.getCurrentItem().getItemDamage()) == color) {
							int nextID = ((TileEntityBoundChest) world.getTileEntity(event.x, event.y, event.z)).id;
							ItemStack linkedSeal = new ItemStack(ThaumicExploration.chestSealLinked, 1, event.entityPlayer.inventory.getCurrentItem().getItemDamage());
							NBTTagCompound tag = new NBTTagCompound();
							tag.setInteger("ID", nextID);
							tag.setInteger("x", event.x);
							tag.setInteger("y", event.y);
							tag.setInteger("z", event.z);
							tag.setInteger("dim", world.provider.dimensionId);
							linkedSeal.setTagCompound(tag);

							event.entityPlayer.inventory.addItemStackToInventory(linkedSeal);
							if (!event.entityPlayer.capabilities.isCreativeMode)
								event.entityPlayer.inventory.decrStackSize(event.entityPlayer.inventory.currentItem, 1);
						}
						event.setCanceled(true);
					}
				}
			}
		
		}


		if (event.entityPlayer.worldObj.blockExists(event.x, event.y, event.z)) {
			if (event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z) == ConfigBlocks.blockJar && event.entityPlayer.worldObj.getBlockMetadata(event.x, event.y, event.z) == 0) {
				if (event.entityPlayer.inventory.getCurrentItem() != null && ((TileJarFillable)event.entityPlayer.worldObj.getTileEntity(event.x, event.y, event.z)).aspectFilter == null && ((TileJarFillable)event.entityPlayer.worldObj.getTileEntity(event.x, event.y, event.z)).amount == 0){ 
					if (event.entityPlayer.inventory.getCurrentItem().getItem() == ThaumicExploration.jarSeal) {
						type = 4;
					}
					else if (event.entityPlayer.inventory.getCurrentItem().getItem() == ThaumicExploration.jarSealLinked) {
						type = 5;
					}
				}
			}
			else if (event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z) == ThaumicExploration.boundJar) {
				World world = event.entityPlayer.worldObj;
				if (event.entityPlayer.inventory.getCurrentItem() != null){ 
					if (event.entityPlayer.inventory.getCurrentItem().getItem() == ThaumicExploration.jarSeal ) {
						int color = ((TileEntityBoundJar) world.getTileEntity(event.x, event.y, event.z)).getSealColor();		
						type = 6;
						if (15-(event.entityPlayer.inventory.getCurrentItem().getItemDamage()) == color) {
							int nextID = ((TileEntityBoundJar) world.getTileEntity(event.x, event.y, event.z)).id;
							ItemStack linkedSeal = new ItemStack(ThaumicExploration.jarSealLinked, 1, event.entityPlayer.inventory.getCurrentItem().getItemDamage());
							NBTTagCompound tag = new NBTTagCompound();
							tag.setInteger("ID", nextID);
							tag.setInteger("x", event.x);
							tag.setInteger("y", event.y);
							tag.setInteger("z", event.z);
							tag.setInteger("dim", world.provider.dimensionId);
							linkedSeal.setTagCompound(tag);

							event.entityPlayer.inventory.addItemStackToInventory(linkedSeal);
							if (!event.entityPlayer.capabilities.isCreativeMode)
								event.entityPlayer.inventory.decrStackSize(event.entityPlayer.inventory.currentItem, 1);
						}
						event.setCanceled(true);
					}
				}
			}
		}
		
		
		
		if (event.entityPlayer.worldObj.isRemote && type > 0) {
			ByteBuf buf = Unpooled.buffer();
    		ByteBufOutputStream out = new ByteBufOutputStream(buf);
	
	        try
	        {
	        	out.writeByte(1);
	        	out.writeInt(event.entityPlayer.worldObj.provider.dimensionId);
	        	out.writeInt(event.x);
	        	out.writeInt(event.y);
	        	out.writeInt(event.z);
	        	out.writeByte(type);
	            out.writeInt( event.entityPlayer.getEntityId());
	            FMLProxyPacket packet = new FMLProxyPacket(buf,"tExploration");
		        ThaumicExploration.channel.sendToServer(packet);
		        out.close();
	        }
	        catch (Exception ex)
	        {
	            ex.printStackTrace();
	        }
	
	        
		}

		
	}
	
}
