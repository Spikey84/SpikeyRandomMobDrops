package me.spikey.spikeyrandommobdrops;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ToggleDropItem implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!commandSender.hasPermission("randommobs.toggle")) return true;
        if (!Main.dropRandomItem) Main.dropRandomItem = true; else Main.dropRandomItem = false;

        commandSender.sendMessage("Random mob drops is now set to %s.".formatted(Main.dropRandomItem));
        return true;
    }
}
