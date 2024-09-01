package io.wurmatron.ccspecial.common.skygrid;

import io.github.opencubicchunks.cubicchunks.api.util.CubePos;
import io.github.opencubicchunks.cubicchunks.api.worldgen.populator.ICubicPopulator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Random;

public class PlanetPopulate implements ICubicPopulator {

    @Override
    public void generate(World world, Random rand, CubePos cubePos, Biome biome) {
        if (rand.nextInt(24) == 1) {
            generatePlanet(world, rand, cubePos);
        }
    }

    public static void generatePlanet(World world, Random rand, CubePos pos) {
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
        makeSphere(world, core, Blocks.BONE_BLOCK.getDefaultState(), radius, radius, radius, true);
    }

    // Modified from WorldEdit
    public static void makeSphere(World world, BlockPos pos, IBlockState block, double radiusX, double radiusY, double radiusZ, boolean filled) {
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
                    world.setBlockState(pos.add(x, y, z), block);
                    world.setBlockState(pos.add(-x, y, z), block);
                    world.setBlockState(pos.add(x, -y, z), block);
                    world.setBlockState(pos.add(x, y, -z), block);
                    world.setBlockState(pos.add(-x, -y, z), block);
                    world.setBlockState(pos.add(x, -y, -z), block);
                    world.setBlockState(pos.add(-x, y, -z), block);
                    world.setBlockState(pos.add(-x, -y, -z), block);
                }
            }
        }
    }

    private static double lengthSq(double x, double y, double z) {
        return (x * x) + (y * y) + (z * z);
    }
}
