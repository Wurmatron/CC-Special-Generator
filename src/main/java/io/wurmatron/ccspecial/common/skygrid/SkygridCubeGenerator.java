package io.wurmatron.ccspecial.common.skygrid;

import io.github.opencubicchunks.cubicchunks.api.util.Box;
import io.github.opencubicchunks.cubicchunks.api.util.Coords;
import io.github.opencubicchunks.cubicchunks.api.util.CubePos;
import io.github.opencubicchunks.cubicchunks.api.world.ICube;
import io.github.opencubicchunks.cubicchunks.api.worldgen.CubeGeneratorsRegistry;
import io.github.opencubicchunks.cubicchunks.api.worldgen.CubePrimer;
import io.github.opencubicchunks.cubicchunks.api.worldgen.ICubeGenerator;
import io.github.opencubicchunks.cubicchunks.api.worldgen.populator.CubePopulatorEvent;
import io.github.opencubicchunks.cubicchunks.api.worldgen.populator.event.PopulateCubeEvent;
import io.wurmatron.ccspecial.common.utils.BlockLoader;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class SkygridCubeGenerator implements ICubeGenerator {

    protected World world;
    private Biome[] columnBiomes;


    public SkygridCubeGenerator(World world) {
        this.world = world;
        world.setSpawnPoint(new BlockPos(0, 0, 0));
    }

    @Override
    public CubePrimer generateCube(int cubeX, int cubeY, int cubeZ) {
        return this.generateCube(cubeX, cubeY, cubeZ, CubePrimer.createFilled(Blocks.AIR.getDefaultState()));
    }

    @Override
    public CubePrimer generateCube(int cubeX, int cubeY, int cubeZ, CubePrimer primer) {
        // Y = 0;
        setBlockState(0, 0, 0, primer);
        setBlockState(0, 0, 4, primer);
        setBlockState(0, 0, 8, primer);
        setBlockState(0, 0, 12, primer);
        setBlockState(4, 0, 0, primer);
        setBlockState(4, 0, 4, primer);
        setBlockState(4, 0, 8, primer);
        setBlockState(4, 0, 12, primer);
        setBlockState(8, 0, 0, primer);
        setBlockState(8, 0, 4, primer);
        setBlockState(8, 0, 8, primer);
        setBlockState(8, 0, 12, primer);
        setBlockState(12, 0, 0, primer);
        setBlockState(12, 0, 4, primer);
        setBlockState(12, 0, 8, primer);
        setBlockState(12, 0, 12, primer);
        // Y = 4
        setBlockState(0, 4, 0, primer);
        setBlockState(0, 4, 4, primer);
        setBlockState(0, 4, 8, primer);
        setBlockState(0, 4, 12, primer);
        setBlockState(4, 4, 0, primer);
        setBlockState(4, 4, 4, primer);
        setBlockState(4, 4, 8, primer);
        setBlockState(4, 4, 12, primer);
        setBlockState(8, 4, 0, primer);
        setBlockState(8, 4, 4, primer);
        setBlockState(8, 4, 8, primer);
        setBlockState(8, 4, 12, primer);
        setBlockState(12, 4, 0, primer);
        setBlockState(12, 4, 4, primer);
        setBlockState(12, 4, 8, primer);
        setBlockState(12, 4, 12, primer);
        // Y = 8
        setBlockState(0, 8, 0, primer);
        setBlockState(0, 8, 4, primer);
        setBlockState(0, 8, 8, primer);
        setBlockState(0, 8, 12, primer);
        setBlockState(4, 8, 0, primer);
        setBlockState(4, 8, 4, primer);
        setBlockState(4, 8, 8, primer);
        setBlockState(4, 8, 12, primer);
        setBlockState(8, 8, 0, primer);
        setBlockState(8, 8, 4, primer);
        setBlockState(8, 8, 8, primer);
        setBlockState(8, 8, 12, primer);
        setBlockState(12, 8, 0, primer);
        setBlockState(12, 8, 4, primer);
        setBlockState(12, 8, 8, primer);
        setBlockState(12, 8, 12, primer);
        // Y = 12
        setBlockState(0, 12, 0, primer);
        setBlockState(0, 12, 4, primer);
        setBlockState(0, 12, 8, primer);
        setBlockState(0, 12, 12, primer);
        setBlockState(4, 12, 0, primer);
        setBlockState(4, 12, 4, primer);
        setBlockState(4, 12, 8, primer);
        setBlockState(4, 12, 12, primer);
        setBlockState(8, 12, 0, primer);
        setBlockState(8, 12, 4, primer);
        setBlockState(8, 12, 8, primer);
        setBlockState(8, 12, 12, primer);
        setBlockState(12, 12, 0, primer);
        setBlockState(12, 12, 4, primer);
        setBlockState(12, 12, 8, primer);
        setBlockState(12, 12, 12, primer);
        return primer;
    }

    private IBlockState getRandomBlock() {
        if (BlockLoader.skyblock != null && !BlockLoader.skyblock.isEmpty())
            return BlockLoader.skyblock.get(world.rand.nextInt(BlockLoader.skyblock.size()));
        else {
            BlockLoader.loadSkyBlock();
            return getRandomBlock();
        }
    }


    private void setBlockState(int x, int y, int z, CubePrimer primer) {
        IBlockState state = getRandomBlock();
        if (state.getBlock() instanceof BlockBush) {
            if (state.getBlock() == Blocks.WHEAT || state.getBlock() == Blocks.CARROTS || state.getBlock() == Blocks.POTATOES) {
                primer.setBlockState(x, y + 1, z, state);
                primer.setBlockState(x, y, z, Blocks.FARMLAND.getDefaultState());
                return;
            }
            if (state.getBlock() == Blocks.NETHER_WART) {
                primer.setBlockState(x, y + 1, z, state);
                primer.setBlockState(x, y, z, Blocks.SOUL_SAND.getDefaultState());
                return;
            }
        }
        if (state.getBlock().equals(Blocks.REEDS)) {
            primer.setBlockState(x, y, z + 1, Blocks.WATER.getDefaultState());
        }
        if (state.getBlock().equals(Blocks.WATERLILY)) {
            primer.setBlockState(x, y, z, Blocks.WATER.getDefaultState());
            primer.setBlockState(x, y + 1, z, state);
            return;
        }
        primer.setBlockState(x, y, z, state);
    }

    public void generateColumn(Chunk column) {
        this.columnBiomes = this.world.getBiomeProvider().getBiomes(this.columnBiomes, Coords.cubeToMinBlock(column.x), Coords.cubeToMinBlock(column.z), 16, 16);
        byte[] columnBiomeArray = column.getBiomeArray();
        for (int i = 0; i < columnBiomeArray.length; ++i) {
            columnBiomeArray[i] = (byte) Biome.getIdForBiome(this.columnBiomes[i]);
        }
    }

    @Override
    public void populate(ICube cube) {
        if (!MinecraftForge.EVENT_BUS.post(new CubePopulatorEvent(world, cube))) {
            CubePos pos = cube.getCoords();
            Random rand = Coords.coordsSeedRandom(cube.getWorld().getSeed(), cube.getX(), cube.getY(), cube.getZ());
            MinecraftForge.EVENT_BUS.post(new PopulateCubeEvent.Pre(world, rand, pos.getX(), pos.getY(), pos.getZ(), false));
            MinecraftForge.EVENT_BUS.post(new PopulateCubeEvent.Post(world, rand, pos.getX(), pos.getY(), pos.getZ(), false));
            CubeGeneratorsRegistry.generateWorld(world, rand, pos, world.getBiome(cube.localAddressToBlockPos(0)));
        }
    }

    @Override
    public Box getFullPopulationRequirements(ICube cube) {
        return RECOMMENDED_FULL_POPULATOR_REQUIREMENT;
    }

    @Override
    public Box getPopulationPregenerationRequirements(ICube cube) {
        return RECOMMENDED_GENERATE_POPULATOR_REQUIREMENT;
    }

    @Override
    public void recreateStructures(ICube cube) {

    }

    @Override
    public void recreateStructures(Chunk chunk) {

    }

    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType type, BlockPos pos) {
        return this.world.getBiome(pos).getSpawnableList(type);
    }

    @Nullable
    @Override
    public BlockPos getClosestStructure(String s, BlockPos blockPos, boolean b) {
        return null;
    }
}
