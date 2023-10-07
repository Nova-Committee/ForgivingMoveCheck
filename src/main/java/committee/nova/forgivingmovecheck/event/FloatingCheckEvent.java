package committee.nova.forgivingmovecheck.event;

import committee.nova.forgivingmovecheck.forgiveness.ForgivingManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;


@Cancelable
public class FloatingCheckEvent extends Event {
    private final ServerPlayer player;
    private final ForgivingManager.MovingContext context;
    private boolean floating;

    public FloatingCheckEvent(ServerPlayer player, ForgivingManager.MovingContext context, boolean floating) {
        this.player = player;
        this.context = context;
        this.floating = floating;
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
     * @return Whether the player/vehicle is determined as floating
     */
    public boolean isFloating() {
        return floating;
    }

    /**
     * @param floating Whether you want the player/vehicle to be determined as floating
     */
    public void setFloating(boolean floating) {
        this.floating = floating;
    }
}
