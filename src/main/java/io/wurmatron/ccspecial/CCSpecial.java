package io.wurmatron.ccspecial;

import io.wurmatron.ccspecial.common.skygrid.SkyGridWorldType;
import io.wurmatron.ccspecial.common.stars.PlanetsWorldType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = CCSpecial.MODID, name = CCSpecial.NAME, version = CCSpecial.VERSION)
public class CCSpecial {
    public static final String MODID = "ccspecial";
    public static final String NAME = "CC Special Generator";
    public static final String VERSION = "0.0.0";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        new SkyGridWorldType("Skygrid");
        new PlanetsWorldType("Planets");
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    }
}
