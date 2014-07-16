package mc.Mitchellbrine.capi;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;

import java.util.ArrayList;
import java.util.List;

public class CapeCommand implements ICommand {

    private List aliases;

    public CapeCommand() {
        aliases = new ArrayList();
        aliases.add("setPlayerCape");
        aliases.add("setCapePlayer");
    }

    @Override
    public String getCommandName() {
        return "setcape";
    }

    @Override
    public String getCommandUsage(ICommandSender var1) {
        return "/setcape [player] <name/url>";
    }

    @Override
    public List getCommandAliases() {
        return this.aliases;
    }

    @Override
    public void processCommand(ICommandSender var1, String[] var2) {
            if (var2.length > 0) {
                if (var2.length == 1) {
                    if (var1 instanceof EntityPlayer) {
                        CAPI.instance.removePlayerCape(var1.getCommandSenderName());
                        CapeRendering.instance.getCaped().remove(var1);
                        CAPI.instance.addPlayerCape(var1.getCommandSenderName(), var2[0]);
                        CAPI.instance.getUpdates().add((EntityPlayer)var1);
                        CAPI.instance.logger.info("Changed " + var1.getCommandSenderName() + "'s cape location to " + CAPI.instance.getCapes().get(var1.getCommandSenderName()));
                    } else {
                        var1.addChatMessage(new ChatComponentTranslation("capi.sender.incorrect"));
                    }
                } else if (var2.length == 2) {

                    CAPI.instance.removePlayerCape(var2[0]);
                    CapeRendering.instance.getCaped().remove(var2[0]);
                    CAPI.instance.addPlayerCape(var2[0],var2[1]);
                    CAPI.instance.getUpdates().add(((EntityPlayerMP)var1).mcServer.getConfigurationManager().getPlayerForUsername(var2[0]));
                    CAPI.instance.logger.info("Changed " + var2[0] + "'s cape location to " + CAPI.instance.getCapes().get(var2[0]));
                } else {
                    var1.addChatMessage(new ChatComponentTranslation("capi.sender.argument"));
                }
            } else {
                var1.addChatMessage(new ChatComponentTranslation("capi.sender.argument"));
            }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender var1) {
        return true;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender var1, String[] var2) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] var1, int var2) {
        return false;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
