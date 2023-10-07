package committee.nova.forgivingmovecheck;

import committee.nova.forgivingmovecheck.config.ForgivingConfig;
import net.fabricmc.api.ModInitializer;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class ForgivingMoveCheck implements ModInitializer {
    @Override
    public void onInitialize() {
        ModLoadingContext.registerConfig("forgivingmovecheck", ModConfig.Type.COMMON, ForgivingConfig.CFG);
    }
}
