package committee.nova.forgivingmovecheck.event;

import committee.nova.forgivingmovecheck.forgiveness.ForgivingManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import org.slf4j.Logger;

@Cancelable
public class ForgivingEvent extends Event {
    private final ServerPlayer player;
    private final ForgivingManager.MovingContext context;
    private final Logger logger;
    private boolean shouldSkipVanillaCheck;

    public ForgivingEvent(ServerPlayer player, ForgivingManager.MovingContext context, Logger logger) {
        this.player = player;
        this.context = context;
        this.logger = logger;
    }

    /**
     * @return The player to be checked
     */
    public ServerPlayer getPlayer() {
        return player;
    }

    /**
     * @return The moving context
     */
    public ForgivingManager.MovingContext getContext() {
        return context;
    }

    /**
     * @return The logger in {@link net.minecraft.server.network.ServerGamePacketListenerImpl}, for notification
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * @return Whether the vanilla disconnection and log should be skipped
     */
    public boolean shouldSkipVanillaCheck() {
        return shouldSkipVanillaCheck;
    }

    /**
     * @param shouldSkipVanillaCheck Whether you want the vanilla disconnection and log to be skipped
     */
    public void setShouldSkipVanillaCheck(boolean shouldSkipVanillaCheck) {
        this.shouldSkipVanillaCheck = shouldSkipVanillaCheck;
    }
}
