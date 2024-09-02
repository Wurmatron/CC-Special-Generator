package io.wurmatron.ccspecial.common.utils;

import io.wurmatron.ccspecial.CCSpecial;
import io.wurmatron.ccspecial.api.PlanentEntry;
import io.wurmatron.ccspecial.api.SkyblockEntry;
import joptsimple.internal.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

public class BlockLoader {


    public static File SKYBLOCK_FILES = new File(CCSpecial.configDir + File.separator + "skyblock");
    public static File PLANET_FILES = new File(CCSpecial.configDir + File.separator + "planets");

    public static ArrayList<IBlockState> skyblock = new ArrayList<>();
    public static ArrayList<PlanentEntry> planets = new ArrayList<>();

    public static void loadSkyBlock() {
        if (SKYBLOCK_FILES.exists()) {
            for (File file : SKYBLOCK_FILES.listFiles()) {
                try {
                    SkyblockEntry entry = CCSpecial.GSON.fromJson(Strings.join(Files.readAllLines(file.toPath()), "\n"), SkyblockEntry.class);
                    Block block = Block.getBlockFromName(entry.block);
                    if (block != null && block != Blocks.AIR) {
                        IBlockState state = block.getStateFromMeta(entry.meta);
                        for (int count = 0; count < entry.rarityEntry; count++) {
                            skyblock.add(state);
                        }
                    } else {
                        CCSpecial.logger.warn("Failed to load '" + file.getName() + "' with invalid block '" + entry.block + "'");
                    }
                } catch (Exception e) {
                    CCSpecial.logger.warn("Failed to load '" + file.getName() + "'");
                    e.printStackTrace();
                }
            }
        } else {
            // TODO Create default's
        }
    }

    public static void loadPlanets() {
        if (PLANET_FILES.exists()) {
            for (File file : PLANET_FILES.listFiles()) {
                try {
                    PlanentEntry entry = CCSpecial.GSON.fromJson(Strings.join(Files.readAllLines(file.toPath()), "\n"), PlanentEntry.class);
                    if (validate(entry)) {
                        for (int count = 0; count < entry.rarityEntry; count++) {
                            planets.add(entry);
                        }
                    } else {
                        // Error is printed from the verify method
                    }
                } catch (Exception e) {
                    CCSpecial.logger.warn("Failed to load '" + file.getName() + "'");
                    e.printStackTrace();
                }
            }
        } else {
            // TODO Create default's
        }
    }

    public static IBlockState getSkyblock() {
        return Blocks.STONE.getDefaultState();
    }

    public static IBlockState getPlanet() {
        return Blocks.STONE.getDefaultState();
    }

    private static boolean verifyBlock(String name) {
        Block block = Block.getBlockFromName(name);
        return block != null && block != Blocks.AIR;
    }

    private static boolean validate(PlanentEntry entry) {
        if (!verifyBlock(entry.coreBlock)) {
            CCSpecial.logger.warn("Invalid Core Block '" + entry.coreBlock + "'");
            return false;
        }
        if (!verifyBlock(entry.fillerBlock)) {
            CCSpecial.logger.warn("Invalid Core Block '" + entry.fillerBlock + "'");
            return false;
        }
        if (!verifyBlock(entry.fillerRareBlock)) {
            CCSpecial.logger.warn("Invalid Core Block '" + entry.fillerRareBlock + "'");
            return false;
        }
        if (!verifyBlock(entry.outerBlock)) {
            CCSpecial.logger.warn("Invalid Core Block '" + entry.outerBlock + "'");
            return false;
        }
        return true;
    }
}
