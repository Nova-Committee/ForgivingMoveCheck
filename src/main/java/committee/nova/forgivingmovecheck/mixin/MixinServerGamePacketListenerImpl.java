package committee.nova.forgivingmovecheck.mixin;

import committee.nova.forgivingmovecheck.ForgivingMoveCheck;
import committee.nova.forgivingmovecheck.event.FloatingCheckEvent;
import committee.nova.forgivingmovecheck.forgiveness.ForgivingManager;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundMoveVehiclePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraftforge.common.MinecraftForge;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class MixinServerGamePacketListenerImpl {
    @Shadow
    public ServerPlayer player;

    @Shadow
    @Final
    static Logger LOGGER;

    @Shadow
    private boolean clientIsFloating;

    @Shadow
    private boolean clientVehicleIsFloating;

    @Shadow
    private int aboveGroundTickCount;

    @Shadow
    private int aboveGroundVehicleTickCount;

    @Inject(method = "tick", at = @At(
            value = "INVOKE",
            target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;)V",
            ordinal = 0,
            remap = false
    ), cancellable = true)
    private void inject$tick$selfFlying(CallbackInfo ci) {
        final boolean skip = ForgivingMoveCheck.forgiveSelfFlying.get().getStrategy().check(player, ForgivingManager.MovingContext.SELF_FLYING, LOGGER);
        if (skip) {
            ci.cancel();
            aboveGroundTickCount = 0;
        }
    }

    @Inject(method = "tick", at = @At(
            value = "INVOKE",
            target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;)V",
            ordinal = 1,
            remap = false
    ), cancellable = true)
    private void inject$tick$vehicleFlying(CallbackInfo ci) {
        final boolean skip = ForgivingMoveCheck.forgiveVehicleFlying.get().getStrategy().check(player, ForgivingManager.MovingContext.VEHICLE_FLYING, LOGGER);
        if (skip) {
            ci.cancel();
            aboveGroundVehicleTickCount = 0;
        }
    }

    @Inject(method = "tick", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;disconnect(Lnet/minecraft/network/chat/Component;)V",
            ordinal = 2
    ), cancellable = true)
    private void inject$tick$idling(CallbackInfo ci) {
        final boolean skip = ForgivingMoveCheck.forgiveIdling.get().getStrategy().check(player, ForgivingManager.MovingContext.IDLING, LOGGER);
        if (skip) {
            ci.cancel();
            player.resetLastActionTime();
        }
    }

    @Inject(method = "handleMovePlayer", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerChunkCache;move(Lnet/minecraft/server/level/ServerPlayer;)V",
            ordinal = 1
    ))
    private void inject$handleMovePlayer(ServerboundMovePlayerPacket packet, CallbackInfo ci) {
        final FloatingCheckEvent event = new FloatingCheckEvent(player, ForgivingManager.MovingContext.SELF_FLYING, clientIsFloating);
        MinecraftForge.EVENT_BUS.post(event);
        clientIsFloating = event.isFloating();
    }

    @Inject(method = "handleMoveVehicle", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;getX()D",
            ordinal = 2
    ))
    private void inject$handleMoveVehicle(ServerboundMoveVehiclePacket packet, CallbackInfo ci) {
        final FloatingCheckEvent event = new FloatingCheckEvent(player, ForgivingManager.MovingContext.VEHICLE_FLYING, clientVehicleIsFloating);
        MinecraftForge.EVENT_BUS.post(event);
        clientVehicleIsFloating = event.isFloating();
    }
}
