package committee.nova.forgivingmovecheck;

import committee.nova.forgivingmovecheck.config.ForgivingConfig;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraftforge.fml.config.ModConfig;

public class ForgivingMoveCheck implements ModInitializer {
    @Override
    public void onInitialize() {
        ForgeConfigRegistry.INSTANCE.register("forgivingmovecheck", ModConfig.Type.COMMON, ForgivingConfig.CFG);
    }
}
