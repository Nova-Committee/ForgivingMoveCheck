package committee.nova.forgivingmovecheck.config;

import committee.nova.forgivingmovecheck.forgiveness.ForgivingManager.ForgivingStrategy;
import net.minecraftforge.common.ForgeConfigSpec;

public class ForgivingConfig {
    public static final ForgeConfigSpec CFG;
    public static final ForgeConfigSpec.BooleanValue enableFloatingCheckEvent;
    public static final ForgeConfigSpec.EnumValue<ForgivingStrategy> forgiveSelfFlying;
    public static final ForgeConfigSpec.EnumValue<ForgivingStrategy> forgiveVehicleFlying;
    public static final ForgeConfigSpec.EnumValue<ForgivingStrategy> forgiveIdling;

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
