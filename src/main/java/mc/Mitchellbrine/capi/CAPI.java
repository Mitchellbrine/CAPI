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
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
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
    private ArrayList<String> needUpdate = new ArrayList<String>();
    public String[] rainbowCapes = new String[]{"red","orange","yellow","green","blue","purple","pink","rainbow"};
    public static AchievementPage capiPage;
    public static Achievement nyanPlayer;
    public static Achievement aureyPlayer;

    @Mod.EventHandler
    public void setLogger(FMLPreInitializationEvent event) {
        File file = new File("capes/disclaimer.txt");
        file.getParentFile().mkdir();

        try {
            PrintWriter w = new PrintWriter("capes/README.txt", "UTF-8");
            w.println("                   ");
            w.println("                   ");
            w.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~");
            w.println("Make sure all the capes exist that the mod calls!");
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

        CapeDownload.downloadOriginalCapes();

    }

    @Mod.EventHandler
    public void setAchievement(FMLPreInitializationEvent event) {

        aureyPlayer = new Achievement("aurey","aurey",0,2,new ItemStack(Items.dye,1,9),null).initIndependentStat().registerStat();
        nyanPlayer = new Achievement("nyan","nyan",0,0, Items.nether_star,null).initIndependentStat().registerStat();

        capiPage = new AchievementPage("capeAPI (cAPI)",new Achievement[]{aureyPlayer,nyanPlayer});
        AchievementPage.registerAchievementPage(capiPage);
    }

    @Mod.EventHandler
    public void initCapes(FMLPostInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new CapeRendering());
        FMLCommonHandler.instance().bus().register(new LoggedIn());
    }

/*    @Mod.EventHandler
    public void startServer(FMLServerStartingEvent event) {
        event.registerServerCommand(new CapeCommand());
    } */

    public HashMap<String,String> getCapes() {
        return this.capes;
    }

    public ArrayList<String> getUpdates() { return this.needUpdate; }

    public void addCape(String capeURL, String capeName) {
        new Thread(new CapeThread(capeURL,capeName)).start();
    }

    public void addPlayerCape(String name, String capeName) {
        CAPI.instance.getCapes().put(name,capeName);
        CAPI.instance.getUpdates().add(name);
    }

    public void removePlayerCape(String name) {
        CAPI.instance.getCapes().remove(name);
    }

    private class CapeThread implements Runnable {

        String capeURL;
        String capeName;

        public CapeThread(String capeURL,String capeName) {
            this.capeURL = capeURL;
            this.capeName = capeName;
        }

        @Override
        public void run() {
            try {
                CapeDownload.downloadResource(new URL(capeURL),new File("capes/",capeName+".png"),0);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

}
