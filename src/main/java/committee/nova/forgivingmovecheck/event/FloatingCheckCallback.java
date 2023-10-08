package committee.nova.forgivingmovecheck.event;

import committee.nova.forgivingmovecheck.forgiveness.ForgivingManager;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.server.level.ServerPlayer;

public interface FloatingCheckCallback {
    Event<FloatingCheckCallback> EVENT = EventFactory.createArrayBacked(FloatingCheckCallback.class, listeners -> (player, ctx, floating) -> {
        for (final FloatingCheckCallback l : listeners) {
            final TriState state = l.check(player, ctx, floating);
            if (state.equals(TriState.DEFAULT)) continue;
            return state;
        }
        return TriState.of(floating);
    });

    TriState check(ServerPlayer player, ForgivingManager.MovingContext context, boolean floating);
}
