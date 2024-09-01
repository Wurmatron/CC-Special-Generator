package io.wurmatron.ccspecial.common.stars;

import io.github.opencubicchunks.cubicchunks.api.util.Box;
import io.github.opencubicchunks.cubicchunks.api.util.Coords;
import io.github.opencubicchunks.cubicchunks.api.util.CubePos;
import io.github.opencubicchunks.cubicchunks.api.world.ICube;
import io.github.opencubicchunks.cubicchunks.api.worldgen.CubeGeneratorsRegistry;
import io.github.opencubicchunks.cubicchunks.api.worldgen.CubePrimer;
import io.github.opencubicchunks.cubicchunks.api.worldgen.ICubeGenerator;
import io.github.opencubicchunks.cubicchunks.api.worldgen.populator.CubePopulatorEvent;
import io.github.opencubicchunks.cubicchunks.api.worldgen.populator.event.PopulateCubeEvent;
import io.wurmatron.ccspecial.common.skygrid.PlanetPopulate;
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

public class PlanetsCubeGenerator implements ICubeGenerator {

    protected World world;
    private Biome[] columnBiomes;
    private PlanetPopulate planets;


    public PlanetsCubeGenerator(World world) {
        this.world = world;
        planets = new PlanetPopulate();
        world.setSpawnPoint(new BlockPos(0, 0, 0));
    }

    @Override
    public CubePrimer generateCube(int cubeX, int cubeY, int cubeZ) {
        return this.generateCube(cubeX, cubeY, cubeZ, CubePrimer.createFilled(Blocks.AIR.getDefaultState()));
    }

    @Override
    public CubePrimer generateCube(int cubeX, int cubeY, int cubeZ, CubePrimer primer) {
        // All done via populate due to large size
        return primer;
    }

    // TODO Replace with Config
    private IBlockState getRandomBlock(int radiusFromCenter) {
        int r = world.rand.nextInt(5);
        if (r > 0) {
            if (r == 0) {
                return Blocks.STONE.getDefaultState();
            } else if (r == 1) {
                return Blocks.BOOKSHELF.getDefaultState();
            } else if (r == 2) {
                return Blocks.HAY_BLOCK.getDefaultState();
            } else if (r == 3) {
                return Blocks.DIRT.getDefaultState();
            } else {
                return Blocks.BONE_BLOCK.getDefaultState();
            }
        } else {
            return Blocks.DIAMOND_BLOCK.getDefaultState();
        }
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
            planets.generate(world, rand, pos, world.getBiome(pos.getCenterBlockPos()));
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
