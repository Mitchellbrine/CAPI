package mc.Mitchellbrine.capi;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CapeRendering {

    public static CapeRendering instance;

    private ArrayList<AbstractClientPlayer> caped = new ArrayList<AbstractClientPlayer>();

    public CapeRendering() {
        instance = this;
    }

    @SubscribeEvent
    public void renderCapes(RenderPlayerEvent.Specials.Pre event) {
        if (event.entityPlayer instanceof AbstractClientPlayer) {
            AbstractClientPlayer player = (AbstractClientPlayer) event.entityPlayer;

            if (CAPI.instance.getUpdates().contains(player.getCommandSenderName())) {
                String capeLocation = CAPI.instance.getCapes().get(player.getCommandSenderName());
                CAPI.instance.getUpdates().remove(player.getCommandSenderName());

                if (capeLocation == null) {
                    return;
                }

                new Thread(new CapeThread(player, capeLocation)).start();
            }

        }
    }

    private class CapeThread implements Runnable {

        AbstractClientPlayer player;
        String cloakLocation;

        public CapeThread(AbstractClientPlayer player, String cloakLocation) {
            this.player = player;
            this.cloakLocation = cloakLocation;
        }

        @Override
        public void run() {
                Image cape;
                    if (!this.cloakLocation.contains("http")) {
                        File capeFile = new File("capes/" + cloakLocation.toLowerCase() + ".png");
                        if (capeFile.exists()) {
                            cape = new ImageIcon("capes/" + cloakLocation + ".png").getImage();
                            BufferedImage capeImage = new BufferedImage(cape.getWidth(null), cape.getHeight(null), BufferedImage.TYPE_INT_ARGB);
                            capeImage.getGraphics().drawImage(cape, 0, 0, null);
                            player.getTextureCape().setBufferedImage(capeImage);
                        }
                    } else {
                        try {
                            cape = new ImageIcon(new URL(cloakLocation)).getImage();
                            BufferedImage capeImage = new BufferedImage(cape.getWidth(null), cape.getHeight(null), BufferedImage.TYPE_INT_ARGB);
                            capeImage.getGraphics().drawImage(cape, 0, 0, null);
                            player.getTextureCape().setBufferedImage(capeImage);
                        } catch (MalformedURLException ex) {
                            ex.printStackTrace();
                        }
                    }
        }


    }

    public ArrayList<AbstractClientPlayer> getCaped() {
        return this.caped;
    }

}
