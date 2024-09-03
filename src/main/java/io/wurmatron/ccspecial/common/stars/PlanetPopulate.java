package io.wurmatron.ccspecial.common.stars;

import io.github.opencubicchunks.cubicchunks.api.util.CubePos;
import io.github.opencubicchunks.cubicchunks.api.worldgen.populator.ICubicPopulator;
import io.wurmatron.ccspecial.api.PlanetEntry;
import io.wurmatron.ccspecial.common.utils.BlockLoader;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Random;

public class PlanetPopulate implements ICubicPopulator {

    public static int planetRarity = 24;

    @Override
    public void generate(World world, Random rand, CubePos cubePos, Biome biome) {
        if (rand.nextInt(planetRarity) == 0) {
            generatePlanet(world, rand, cubePos, BlockLoader.planets.get(world.rand.nextInt(BlockLoader.planets.size())));
        }
    }

    public static void generatePlanet(World world, Random rand, CubePos pos, PlanetEntry entry) {
        int offsetX = rand.nextInt(4) + rand.nextInt(4);
        if (rand.nextBoolean()) {
            offsetX = -offsetX;
        }
        int offsetY = rand.nextInt(4) + rand.nextInt(4);
        if (rand.nextBoolean()) {
            offsetY = -offsetY;
        }
        int offsetZ = rand.nextInt(4) + rand.nextInt(4);
        if (rand.nextBoolean()) {
            offsetZ = -offsetZ;
        }
        int radius = rand.nextInt(10) + 5;
        if (rand.nextInt(30) == 0) {
            radius = radius + rand.nextInt(10) + 5;
        }
        BlockPos core = pos.getCenterBlockPos().add(offsetX, offsetY, offsetZ);
        makeSphere(world, core, entry, radius, radius, radius);
    }


    // Modified from WorldEdit
    public static void makeSphere(World world, BlockPos pos, PlanetEntry entry, double radiusX, double radiusY, double radiusZ) {
        IBlockState[] blocks = BlockLoader.convert(entry);
        radiusX += 0.5;
        radiusY += 0.5;
        radiusZ += 0.5;

        double invRadiusX = 1 / radiusX;
        double invRadiusY = 1 / radiusY;
        double invRadiusZ = 1 / radiusZ;

        int ceilRadiusX = (int) Math.ceil(radiusX);
        int ceilRadiusY = (int) Math.ceil(radiusY);
        int ceilRadiusZ = (int) Math.ceil(radiusZ);

        double nextXn = 0;
        forX:
        for (int x = 0; x <= ceilRadiusX; ++x) {
            final double xn = nextXn;
            nextXn = (x + 1) * invRadiusX;
            double nextYn = 0;
            forY:
            for (int y = 0; y <= ceilRadiusY; ++y) {
                final double yn = nextYn;
                nextYn = (y + 1) * invRadiusY;
                double nextZn = 0;
                forZ:
                for (int z = 0; z <= ceilRadiusZ; ++z) {
                    final double zn = nextZn;
                    nextZn = (z + 1) * invRadiusZ;

                    double distanceSq = lengthSq(xn, yn, zn);
                    if (distanceSq > 1) {
                        if (z == 0) {
                            if (y == 0) {
                                break forX;
                            }
                            break forY;
                        }
                        break forZ;
                    }
                    boolean infil = false;
                    if (lengthSq(nextXn, yn, zn) <= 1 && lengthSq(xn, nextYn, zn) <= 1 && lengthSq(xn, yn, nextZn) <= 1) {
                        infil = true;
                    }
                    if (infil) {
                        setBlockState(world, blocks[0], blocks[1], pos.add(x, y, z));
                        setBlockState(world, blocks[0], blocks[1], pos.add(-x, y, z));
                        setBlockState(world, blocks[0], blocks[1], pos.add(x, -y, z));
                        setBlockState(world, blocks[0], blocks[1], pos.add(x, y, -z));
                        setBlockState(world, blocks[0], blocks[1], pos.add(-x, -y, z));
                        setBlockState(world, blocks[0], blocks[1], pos.add(x, -y, -z));
                        setBlockState(world, blocks[0], blocks[1], pos.add(-x, y, -z));
                        setBlockState(world, blocks[0], blocks[1], pos.add(-x, -y, -z));
                    } else {
                        setBlockState(world, blocks[1], blocks[2], pos.add(x, y, z));
                        setBlockState(world, blocks[1], blocks[2], pos.add(-x, y, z));
                        setBlockState(world, blocks[1], blocks[2], pos.add(x, -y, z));
                        setBlockState(world, blocks[1], blocks[2], pos.add(x, y, -z));
                        setBlockState(world, blocks[1], blocks[2], pos.add(-x, -y, z));
                        setBlockState(world, blocks[1], blocks[2], pos.add(x, -y, -z));
                        setBlockState(world, blocks[1], blocks[2], pos.add(-x, y, -z));
                        setBlockState(world, blocks[1], blocks[2], pos.add(-x, -y, -z));
                    }
                }
            }
        }
        setupCore(world, pos, blocks);
    }

    private static void setBlockState(World world, IBlockState a, IBlockState b, BlockPos pos) {
        if (world.rand.nextInt(3) == 2) {
            world.setBlockState(pos, b);
        } else {
            world.setBlockState(pos, a);
        }
    }

    private static void setupCore(World world, BlockPos pos, IBlockState[] blocks) {
        // Core
        world.setBlockState(pos, blocks[3]);
        if (world.rand.nextInt(3) == 0) {
            world.setBlockState(pos.add(0, 0, 0), blocks[3]);
        }
        if (world.rand.nextInt(3) == 0) {
            world.setBlockState(pos.add(-0, 0, 0), blocks[3]);
        }
        if (world.rand.nextInt(3) == 0) {
            world.setBlockState(pos.add(0, 0, 0), blocks[3]);
        }
        if (world.rand.nextInt(3) == 0) {
            world.setBlockState(pos.add(0, -0, 0), blocks[3]);
        }
        if (world.rand.nextInt(3) == 0) {
            world.setBlockState(pos.add(0, 0, 1), blocks[3]);
        }
        if (world.rand.nextInt(3) == 0) {
            world.setBlockState(pos.add(0, 0, -1), blocks[3]);
        }
    }


    private static double lengthSq(double x, double y, double z) {
        return (x * x) + (y * y) + (z * z);
    }
}
