package io.wurmatron.ccspecial;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.wurmatron.ccspecial.common.skygrid.SkyGridWorldType;
import io.wurmatron.ccspecial.common.stars.PlanetsWorldType;
import io.wurmatron.ccspecial.common.utils.BlockLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = CCSpecial.MODID, name = CCSpecial.NAME, version = CCSpecial.VERSION)
public class CCSpecial {
    public static final String MODID = "ccspecial";
    public static final String NAME = "CC Special Generator";
    public static final String VERSION = "0.0.0";

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static Logger logger;
    public static File configDir;

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        logger = e.getModLog();
        new SkyGridWorldType("Skygrid");
        new PlanetsWorldType("Planets");
        configDir = new File(e.getModConfigurationDirectory() + File.separator + "CC-Special");
    }

    @EventHandler
    public void init(FMLPostInitializationEvent e) {
        BlockLoader.loadSkyBlock();
        BlockLoader.loadPlanets();
    }
}
