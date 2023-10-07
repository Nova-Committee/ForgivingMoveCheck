package committee.nova.forgivingmovecheck;

import committee.nova.forgivingmovecheck.forgiveness.ForgivingManager.ForgivingStrategy;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(ForgivingMoveCheck.MODID)
public class ForgivingMoveCheck {
    public static final String MODID = "forgivingmovecheck";

    static final ForgeConfigSpec CFG;
    public static final ForgeConfigSpec.BooleanValue enableFloatingCheckEvent;
    public static final ForgeConfigSpec.EnumValue<ForgivingStrategy> forgiveSelfFlying;
    public static final ForgeConfigSpec.EnumValue<ForgivingStrategy> forgiveVehicleFlying;
    public static final ForgeConfigSpec.EnumValue<ForgivingStrategy> forgiveIdling;

    public ForgivingMoveCheck() {
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class,
                () -> new IExtensionPoint.DisplayTest(() -> IExtensionPoint.DisplayTest.IGNORESERVERONLY, (a, b) -> true));
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CFG);
    }

    static {
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("Forgiveness");
        forgiveSelfFlying = builder.comment("Forgive players for flying").defineEnum("forgiveSelfFlying", ForgivingStrategy.FORGIVE_BUT_NOTIFY);
        forgiveVehicleFlying = builder.comment("Forgive players for flying with vehicles").defineEnum("forgiveVehicleFlying", ForgivingStrategy.FORGIVE_BUT_NOTIFY);
        forgiveIdling = builder.comment("Forgive players for idling").defineEnum("forgiveIdling", ForgivingStrategy.FORGIVE_BUT_NOTIFY);
        builder.pop();
        builder.push("FloatingCheck");
        enableFloatingCheckEvent = builder.comment("Enable custom floating check event").define("enableFloatingCheckEvent", true);
        builder.pop();
        CFG = builder.build();
    }
}
