package mc.Mitchellbrine.capi;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

import java.util.Random;

public class LoggedIn {

    @SubscribeEvent
    public void loggedInCapes(PlayerEvent.PlayerLoggedInEvent event) {
        if (CAPI.instance.getCapes().get(event.player.getCommandSenderName()) == null || CAPI.instance.getCapes().get(event.player.getCommandSenderName()) == "") {
            Random random = new Random();
            CAPI.instance.addPlayerCape(event.player.getCommandSenderName(),CAPI.instance.rainbowCapes[random.nextInt(CAPI.instance.rainbowCapes.length)]);
            if (CAPI.instance.getCapes().get(event.player.getCommandSenderName()) == "rainbow") {
                event.player.addStat(CAPI.nyanPlayer,1);
            }
            if (CAPI.instance.getCapes().get(event.player.getCommandSenderName()) == "pink") {
                event.player.addStat(CAPI.aureyPlayer,1);
            }
        }
    }

}
