package flaxbeard.thaumicexploration.misc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.MapGenScatteredFeature;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.nodes.NodeModifier;
import thaumcraft.api.nodes.NodeType;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.lib.Utils;
import thaumcraft.common.lib.world.WorldGenCustomFlowers;
import thaumcraft.common.lib.world.WorldGenGreatwoodTrees;
import thaumcraft.common.lib.world.WorldGenHilltopStones;
import thaumcraft.common.lib.world.WorldGenMound;
import thaumcraft.common.lib.world.WorldGenSilverwoodTrees;
import thaumcraft.common.lib.world.biomes.BiomeGenEerie;
import thaumcraft.common.lib.world.biomes.BiomeGenMagicalForest;
import thaumcraft.common.lib.world.biomes.BiomeGenTaint;
import thaumcraft.common.lib.world.biomes.BiomeHandler;
import thaumcraft.common.tiles.TileNode;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;

public class WorldGenTX
  implements IWorldGenerator
{
  public static BiomeGenBase biomeTaint;
  public static BiomeGenBase biomeEerie;
  public static BiomeGenBase biomeMagicalForest;
  static Collection<Aspect> c = Aspect.aspects.values();
  static ArrayList<Aspect> basicAspects = new ArrayList();
  static ArrayList<Aspect> complexAspects = new ArrayList();
  
  public void initialize()
  {

  }
  
  public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
  {
    worldGeneration(random, chunkX, chunkZ, world, true);
  }
  
  public void worldGeneration(Random random, int chunkX, int chunkZ, World world, boolean newGen)
  {
    switch (world.provider.dimensionId)
    {
    case -1: 
      break;
    case 1: 
      break;
    default: 
      generateSurface(world, random, chunkX, chunkZ, newGen);
    }
    if (!newGen) {
      world.getChunkFromChunkCoords(chunkX, chunkZ).setChunkModified();
    }
  }
  

  
  HashMap<Integer, Boolean> structureNode = new HashMap();
  
  private void generateSurface(World world, Random random, int chunkX, int chunkZ, boolean newGen)
  {
    
  }
  
 
}
