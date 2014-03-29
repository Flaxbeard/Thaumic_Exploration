package flaxbeard.thaumicexploration.client.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelUrn extends ModelBase
{
    public ModelRenderer urn = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);


    public ModelUrn()
    {
        this.urn.addBox(-6F, -16.0F, -6F, 12, 9, 12);
        this.urn.addBox(-3F, -7.0F, -3F, 6, 4, 6);
        this.urn.addBox(-4F, -3.0F, -4F,8,3, 8);
        this.urn.mirror = true;
    }

    /**
     * This method renders out all parts of the chest model.
     */
    public void renderAll()
    {
      
        this.urn.render(0.0625F);
    }
}
