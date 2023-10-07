package committee.nova.forgivingmovecheck.event;

import committee.nova.forgivingmovecheck.forgiveness.ForgivingManager;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;

public interface ForgivingCallback {
    Event<ForgivingCallback> EVENT = EventFactory.createArrayBacked(ForgivingCallback.class, listeners -> (player, ctx, logger, skipVanillaCheck) -> {
        for (final ForgivingCallback l : listeners) {
            final TriState state = l.execute(player, ctx, logger, skipVanillaCheck);
            if (state.equals(TriState.DEFAULT)) continue;
            return state;
        }
        return TriState.of(skipVanillaCheck);
    });

    TriState execute(ServerPlayer player, ForgivingManager.MovingContext context, Logger logger, boolean skipVanillaCheck);
}
