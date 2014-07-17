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

public class RemoveCapeCommand implements ICommand {

    private List aliases;

    public RemoveCapeCommand() {
        aliases = new ArrayList();
        aliases.add("removePlayerCape");
        aliases.add("removeCapePlayer");
    }

    @Override
    public String getCommandName() {
        return "removeCape";
    }

    @Override
    public String getCommandUsage(ICommandSender var1) {
        return "/removeCape [player] <name/url>";
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
                    CapeRendering.instance.caped.remove(var1);
                    CAPI.instance.getUpdates().add(var2[0]);

                    CAPI.instance.logger.info("Removed the cape of " + var1.getCommandSenderName());
                } else {
                    var1.addChatMessage(new ChatComponentTranslation("capi.sender.incorrect"));
                }
            } else if (var2.length == 2) {

                CAPI.instance.removePlayerCape(var2[0]);
                CapeRendering.instance.getCaped().remove(var2[0]);
                CAPI.instance.getUpdates().add(var2[0]);
                CAPI.instance.logger.info("Removed the cape of " + var2[0]);
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