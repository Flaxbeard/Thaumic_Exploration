package flaxbeard.thaumicexploration.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntitySoulFX extends EntityFX
{
    /** the scale of the flame FX */
    private float flameScale;

    public EntitySoulFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        super(par1World, par2, par4, par6, par8, par10, par12);
        this.motionX = this.motionX * 0.009999999776482582D + par8;
        this.motionY = this.motionY * 0.009999999776482582D + par10;
        this.motionZ = this.motionZ * 0.009999999776482582D + par12;
        double d6 = par2 + (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
        d6 = par4 + (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
        d6 = par6 + (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
        this.flameScale = this.particleScale;
        this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
        this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4;
        this.noClip = true;
        //this.setParticleTextureIndex(48);
        this.setParticleTextureIndex(0);
        this.particleTextureIndexX = 0; //
        this.particleTextureIndexY = 0;
    }


    public int getBrightnessForRender(float par1)
    {
        float f1 = ((float)this.particleAge + par1) / (float)this.particleMaxAge;

        if (f1 < 0.0F)
        {
            f1 = 0.0F;
        }

        if (f1 > 1.0F)
        {
            f1 = 1.0F;
        }

        int i = super.getBrightnessForRender(par1);
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int)(f1 * 15.0F * 16.0F);

        if (j > 240)
        {
            j = 240;
        }

        return j | k << 16;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness(float par1)
    {
        float f1 = ((float)this.particleAge + par1) / (float)this.particleMaxAge;

        if (f1 < 0.0F)
        {
            f1 = 0.0F;
        }

        if (f1 > 1.0F)
        {
            f1 = 1.0F;
        }

        float f2 = super.getBrightness(par1);
        return f2 * f1 + (1.0F - f1);
    }
    
    @Override
    public void renderParticle(Tessellator par1Tessellator, float f, float f1, float f2, float f3, float f4, float f5)
    {
    	System.out.println("y do dis");
//        float f6 = ((float)this.particleAge + f) / (float)this.particleMaxAge;
//        this.particleScale = this.flameScale * (1.0F - f6 * f6 * 0.5F);
        par1Tessellator.setBrightness(100);//make sure you have this!!
        par1Tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        par1Tessellator.draw();
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("thaumicexploration:textures/items/replicator.png"));
        par1Tessellator.startDrawingQuads();
        par1Tessellator.setBrightness(100);//make sure you have this!!
        par1Tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        super.renderParticle(par1Tessellator, f, f1, f2,f3, f4, f5);
        par1Tessellator.draw();
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/particle/particles.png"));
        par1Tessellator.startDrawingQuads();
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
    	
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
        }

        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9599999785423279D;
        this.motionY *= 0.9599999785423279D;
        this.motionZ *= 0.9599999785423279D;

        if (this.onGround)
        {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }
    }
}
