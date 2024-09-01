package io.wurmatron.ccspecial.common.stars;

import io.github.opencubicchunks.cubicchunks.api.util.IntRange;
import io.github.opencubicchunks.cubicchunks.api.world.ICubicWorldType;
import io.github.opencubicchunks.cubicchunks.api.worldgen.ICubeGenerator;
import io.wurmatron.ccspecial.common.skygrid.SkygridCubeGenerator;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldType;

import javax.annotation.Nullable;

public class PlanetsWorldType extends WorldType implements ICubicWorldType {

    public PlanetsWorldType(String name) {
        super(name);
    }

    @Nullable
    @Override
    public ICubeGenerator createCubeGenerator(World w) {
        return new PlanetsCubeGenerator(w);
    }

    @Override
    public IntRange calculateGenerationHeightRange(WorldServer s) {
        return new IntRange(0, 1);
    }

    @Override
    public boolean hasCubicGeneratorForWorld(World w) {
        return true;
    }
}
