package mc.Mitchellbrine.capi;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class CapeDownload {

    public static CapeDownload instance;

    public CapeDownload() {
        instance = this;
    }

    public void downloadOriginalCapes() {

    }


    private class DownloadThread implements Runnable {

        @Override
        public void run() {
            try {
                downloadResource()
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    public boolean downloadResource(URL par1URL, File par2File, long size) throws IOException
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
