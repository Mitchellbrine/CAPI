package mc.Mitchellbrine.capi;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class CapeDownload {

    public static CapeDownload instance;

    public static void downloadOriginalCapes() {
        CapeDownload.startThread();
    }

    public static void startThread() {        new Thread(new DownloadThread()).start();    }



    private static class DownloadThread implements Runnable {

        @Override
        public void run() {
            try {
                downloadResource(new URL("https://raw.githubusercontent.com/Mitchellbrine/CAPI/master/capes/blue.png"),new File("capes/","blue.png"),0);
                downloadResource(new URL("https://raw.githubusercontent.com/Mitchellbrine/CAPI/master/capes/chowtime.png"),new File("capes/","chowtime.png"),0);
                downloadResource(new URL("https://raw.githubusercontent.com/Mitchellbrine/CAPI/master/capes/diseasecraft.png"),new File("capes/","diseasecraft.png"),0);
                downloadResource(new URL("https://raw.githubusercontent.com/Mitchellbrine/CAPI/master/capes/enderpearl.png"),new File("capes/","enderpearl.png"),0);
                downloadResource(new URL("https://raw.githubusercontent.com/Mitchellbrine/CAPI/master/capes/flowstone.png"),new File("capes/","flowstone.png"),0);
                downloadResource(new URL("https://raw.githubusercontent.com/Mitchellbrine/CAPI/master/capes/glowglass.png"),new File("capes/","glowglass.png"),0);
                downloadResource(new URL("https://raw.githubusercontent.com/Mitchellbrine/CAPI/master/capes/green.png"),new File("capes/","green.png"),0);
                downloadResource(new URL("https://raw.githubusercontent.com/Mitchellbrine/CAPI/master/capes/hackery.png"),new File("capes/","hackery.png"),0);
                downloadResource(new URL("https://raw.githubusercontent.com/Mitchellbrine/CAPI/master/capes/orange.png"),new File("capes/","orange.png"),0);
                downloadResource(new URL("https://raw.githubusercontent.com/Mitchellbrine/CAPI/master/capes/pink.png"),new File("capes/","pink.png"),0);
                downloadResource(new URL("https://raw.githubusercontent.com/Mitchellbrine/CAPI/master/capes/purple.png"),new File("capes/","purple.png"),0);
                downloadResource(new URL("https://raw.githubusercontent.com/Mitchellbrine/CAPI/master/capes/red.png"),new File("capes/","red.png"),0);
                downloadResource(new URL("https://raw.githubusercontent.com/Mitchellbrine/CAPI/master/capes/steamtech.png"),new File("capes/","steamtech.png"),0);
                downloadResource(new URL("https://raw.githubusercontent.com/Mitchellbrine/CAPI/master/capes/yellow.png"),new File("capes/","yellow.png"),0);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    public static boolean downloadResource(URL par1URL, File par2File, long size) throws IOException
    {
        if(par2File.exists())
        {
            if(par2File.length() == size)
            {
                return false;
            }
        }
        else if(!par2File.getParentFile().exists())
        {
            par2File.getParentFile().mkdirs();
        }

        byte[] var5 = new byte[4096];

        URLConnection con = par1URL.openConnection();
        con.setConnectTimeout(15000);
        con.setReadTimeout(15000);
        DataInputStream var6 = new DataInputStream(con.getInputStream());
        DataOutputStream var7 = new DataOutputStream(new FileOutputStream(par2File));
        boolean var8 = false;

        while(true)
        {
            int var9;

            if ((var9 = var6.read(var5)) < 0)
            {
                var6.close();
                var7.close();
                return true;
            }

            var7.write(var5, 0, var9);
        }
    }

}
