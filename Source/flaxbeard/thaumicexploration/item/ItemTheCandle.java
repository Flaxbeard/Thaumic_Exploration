package flaxbeard.thaumicexploration.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import flaxbeard.thaumicexploration.entity.EntityCandleFlame;

public class ItemTheCandle extends Item
{
    public ItemTheCandle(int par1)
    {
        super(par1);
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.tabMisc);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            --par1ItemStack.stackSize;
        }

        par2World.playSoundAtEntity(par3EntityPlayer, "random.fizz", 0.5F, 2.6F + (par2World.rand.nextFloat() - par2World.rand.nextFloat()) * 0.8F);

        if (!par2World.isRemote)
        {
            par2World.spawnEntityInWorld(new EntityCandleFlame(par2World, par3EntityPlayer));
        }

        return par1ItemStack;
    }
}