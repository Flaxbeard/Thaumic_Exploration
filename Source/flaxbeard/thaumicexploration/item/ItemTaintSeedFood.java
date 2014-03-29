package flaxbeard.thaumicexploration.item;

import flaxbeard.thaumicexploration.ThaumicExploration;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

public class ItemTaintSeedFood extends ItemSeedFood implements IPlantable
{
    /** Block ID of the crop this seed food should place. */
    private int cropId;

    /** Block ID of the soil this seed food should be planted on. */
    private int soilId;

    public ItemTaintSeedFood(int par1, int par2, float par3, int par4, int par5)
    {
        super(par1, par2, par3, par4, par5);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par7 != 1)
        {
            return false;
        }
        else if (par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack) && par2EntityPlayer.canPlayerEdit(par4, par5 + 1, par6, par7, par1ItemStack))
        {
            int i1 = par3World.getBlockId(par4, par5, par6);
            Block soil = Block.blocksList[i1];

            if (soil != null && ((soil.blockID == Block.grass.blockID && par3World.getBiomeGenForCoords(par4, par6) == ThaumcraftWorldGenerator.biomeTaint) || (soil.blockID == ConfigBlocks.blockTaint.blockID && par3World.getBlockMetadata(par4, par5, par6) == 1)) && par3World.isAirBlock(par4, par5 + 1, par6))
            {
                par3World.setBlock(par4, par5 + 1, par6, ThaumicExploration.taintBerryCrop.blockID);
                --par1ItemStack.stackSize;
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

}