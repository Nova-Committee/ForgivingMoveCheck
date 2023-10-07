package committee.nova.forgivingmovecheck.forgiveness;

import committee.nova.forgivingmovecheck.event.ForgivingEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import org.slf4j.Logger;

public class ForgivingManager {
    public interface CheckStrategy {
        boolean check(ServerPlayer player, MovingContext ctx, Logger logger);
    }

    public enum MovingContext {
        SELF_FLYING("{} floats too long!", "msg.forgivingmovecheck.self_flying"),
        VEHICLE_FLYING("{} floats a vehicle too long!", "msg.forgivingmovecheck.vehicle_flying"),
        IDLING("{} idles too long", "msg.forgivingmovecheck.idling");

        private final String notifyStr;
        private final String broadcastComponentKey;

        MovingContext(String notifyStr, String broadcastComponentKey) {
            this.notifyStr = notifyStr;
            this.broadcastComponentKey = broadcastComponentKey;
        }

        public String getNotifyStr() {
            return notifyStr;
        }

        public Component getBroadcastComponent(String playerName) {
            return Component.translatable(broadcastComponentKey, playerName);
        }
    }

    public enum ForgivingStrategy {
        FORGIVE_SILENTLY((p, ctx, l) -> true),
        FORGIVE_BUT_BROADCAST((p, ctx, l) -> {
            final String name = p.getName().getString();
            l.warn(ctx.getNotifyStr(), name);
            p.server.getPlayerList().broadcastSystemMessage(ctx.getBroadcastComponent(name), true);
            return true;
        }),
        FORGIVE_BUT_NOTIFY((p, ctx, l) -> {
            l.warn(ctx.getNotifyStr(), p.getName().getString());
            return true;
        }),
        INQUIRE_EVENT((p, ctx, l) -> {
            final ForgivingEvent e = new ForgivingEvent(p, ctx, l);
            MinecraftForge.EVENT_BUS.post(e);
            return e.shouldSkipVanillaCheck();
        }),
        DONT_FORGIVE((p, ctx, l) -> false);

        private final CheckStrategy strategy;

        ForgivingStrategy(CheckStrategy strategy) {
            this.strategy = strategy;
        }

        public CheckStrategy getStrategy() {
            return strategy;
        }
    }

}
