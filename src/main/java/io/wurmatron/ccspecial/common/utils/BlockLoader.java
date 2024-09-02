package io.wurmatron.ccspecial.common.utils;

import io.wurmatron.ccspecial.CCSpecial;
import io.wurmatron.ccspecial.api.PlanentEntry;
import io.wurmatron.ccspecial.api.SkyblockEntry;
import joptsimple.internal.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
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
                    Block block = getBlock(entry.block);
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
            generateDefaultSkyblock();
            loadSkyBlock();
        }
    }

    private static Block getBlock(String name) {
        if (name.contains(":")) {
            String[] parts = name.split(":");
            return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(parts[0], parts[1]));
        }
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name));
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

    public static String[] blacklist = new String[]{"dragon_egg", "door", "fence", "bed", "stairs", "brewing_stand",
            "command_block", "rail", "slab", "portal", "furnace", "cocoa", "air", "shulker", "carpet", "wall", "deadbush",
            "plant", "dispenser", "dropper", "gateway", "flower_pot", "pane", "hopper", "lit_", "stem", "_extension",
            "powered_", "structure_", "pressure_plate", "button", "tallgrass", "sign", "snow_layer", "piston_head",
            "leaves", "fire", "double_plant", "daylight_detector", "placed_", "molten", "debug", "bloom", "bellows",
            "bloomery", "forge", "redstone_torch", };

    private static void generateDefaultSkyblock() {
        m:
        for (Block block : ForgeRegistries.BLOCKS) {
            if (block.getRegistryName() == null)
                continue m;
            for (String no : blacklist) {
                if (block.getRegistryName().getResourcePath().replaceFirst(no, "").length() != block.getRegistryName().getResourcePath().length()) {
                    continue m;
                }
            }
            SkyblockEntry entry;
            try {
                block.getRegistryName().getResourceDomain();
                entry = new SkyblockEntry(block.getRegistryName().getResourceDomain() + ":" + block.getRegistryName().getResourcePath(), 0, 1);
            } catch (Exception e) {
                entry = new SkyblockEntry(block.getRegistryName().getResourcePath(), 0, 1);
            }
            save(entry);
        }
    }

    private static void save(SkyblockEntry entry) {
        if (!SKYBLOCK_FILES.exists()) {
            SKYBLOCK_FILES.mkdirs();
            CCSpecial.logger.info("Creating SkyBlock Folder");
        }
        try {
            Files.write(new File(SKYBLOCK_FILES + File.separator + entry.block.toUpperCase().replaceAll(":", "-") + ".json").toPath(), CCSpecial.GSON.toJson(entry).getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE_NEW);
            CCSpecial.logger.info("Creating Default SkyBlock file for " + entry.block);
        } catch (Exception e) {
            CCSpecial.logger.warn("Failed to save '" + entry.block + "'");
            e.printStackTrace();
        }
    }

    private static boolean verifyBlock(String name) {
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name));
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
