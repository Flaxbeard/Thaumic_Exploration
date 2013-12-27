package flaxbeard.thaumicexploration.client.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import flaxbeard.thaumicexploration.tile.TileEntityThinkTankBookshelf;

@SideOnly(Side.CLIENT)
public class TileEntityThinkTankBookshelfRender extends TileEntitySpecialRenderer
{
    private static final ResourceLocation enchantingTableBookTextures = new ResourceLocation("textures/entity/enchanting_table_book.png");
    private ModelBook enchantmentBook = new ModelBook();

    public void renderTileEntityThinkTankBookshelfAt(TileEntityThinkTankBookshelf tileEntityThinkTankBookshelf, double par2, double par4, double par6, float par8)
    {
    	System.out.println("got dat render man");
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2 + 0.5F, (float)par4 + 2.0F, (float)par6 + 0.5F);
        float f1 = (float)tileEntityThinkTankBookshelf.tickCount + par8;
        //GL11.glTranslatef(0.0F, 0.1F + MathHelper.sin(f1 * 0.1F) * 0.01F, 0.0F);
        
        GL11.glEnable(GL11.GL_CULL_FACE);
        this.enchantmentBook.render((Entity)null, 0, 0, 0, 0, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }

    public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
    {
        this.renderTileEntityThinkTankBookshelfAt((TileEntityThinkTankBookshelf)par1TileEntity, par2, par4, par6, par8);
    }
}
