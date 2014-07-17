package mc.Mitchellbrine.capi;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Mod(modid="cAPI",name="capeAPI (cAPI)",version="1.0",useMetadata=true)
public class CAPI {

    public static CAPI instance;
    public Logger logger;

    public CAPI() {
        instance = this;
    }

    private HashMap<String,String> capes = new HashMap<String,String>();
    private static ArrayList<String> capeURLS = new ArrayList<String>();
    private ArrayList<String> needUpdate = new ArrayList<String>();
    public String[] rainbowCapes = new String[]{"red","orange","yellow","green","blue","purple","pink"};

    @Mod.EventHandler
    public void setLogger(FMLPreInitializationEvent event) {
        File file = new File("capes/disclaimer.txt");
        file.getParentFile().mkdir();

        try {
            PrintWriter w = new PrintWriter("capes/README.txt", "UTF-8");
            w.println("                   ");
            w.println("                   ");
            w.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~");
            w.println("Make sure you put in all the capes you want or your mod links you to or else the cape will not render for you.");
            w.println("~ ~ ~");
            w.println("Enyoy!");
            w.println("-MBrine");
            w.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~");
            w.println("                   ");
            w.println("                   ");
            w.close();
        } catch (IOException e ) {
            e.printStackTrace();
        }

        logger = event.getModLog();
    }

    @Mod.EventHandler
    public void initCapes(FMLPostInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new CapeRendering());
        FMLCommonHandler.instance().bus().register(new LoggedIn());
    }

    @Mod.EventHandler
    public void startServer(FMLServerStartingEvent event) {
        event.registerServerCommand(new CapeCommand());
    }

    public HashMap<String,String> getCapes() {
        return this.capes;
    }

    public static ArrayList<String> getCapeURLS() {
        return capeURLS;
    }

    public ArrayList<String> getUpdates() { return this.needUpdate; }

    public void addPlayerCape(String name, String capeURL) {
        CAPI.instance.getCapes().put(name,capeURL);
        CAPI.getCapeURLS().add(capeURL);
        CAPI.instance.getUpdates().add(name);
    }

    public void removePlayerCape(String name) {
        CAPI.instance.getCapes().remove(name);
    }

}
