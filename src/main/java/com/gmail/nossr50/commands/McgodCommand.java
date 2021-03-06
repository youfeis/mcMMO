package com.gmail.nossr50.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.nossr50.datatypes.player.McMMOPlayer;
import com.gmail.nossr50.locale.LocaleLoader;
import com.gmail.nossr50.util.Permissions;
import com.gmail.nossr50.util.commands.CommandUtils;
import com.gmail.nossr50.util.player.UserManager;

public class McgodCommand implements CommandExecutor {
    private McMMOPlayer mcMMOPlayer;
    private Player player;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (args.length) {
            case 0:
                if (CommandUtils.noConsoleUsage(sender)) {
                    return true;
                }

                if (!Permissions.mcgod(sender)) {
                    sender.sendMessage(command.getPermissionMessage());
                    return true;
                }

                mcMMOPlayer = UserManager.getPlayer(sender.getName());
                player = mcMMOPlayer.getPlayer();

                toggleGodMode();
                return true;

            case 1:
                if (!Permissions.mcgodOthers(sender)) {
                    sender.sendMessage(command.getPermissionMessage());
                    return true;
                }

                mcMMOPlayer = UserManager.getPlayer(args[0]);

                if (!CommandUtils.checkPlayerExistence(sender, args[0], mcMMOPlayer)) {
                    return true;
                }

                player = mcMMOPlayer.getPlayer();

                if (CommandUtils.isOffline(sender, player)) {
                    return true;
                }

                toggleGodMode();
                sender.sendMessage("God mode has been toggled for " + player.getName()); // TODO: Localize
                return true;

            default:
                return false;
        }
    }

    private void toggleGodMode() {
        if (mcMMOPlayer.getGodMode()) {
            player.sendMessage(LocaleLoader.getString("Commands.GodMode.Disabled"));
        }
        else {
            player.sendMessage(LocaleLoader.getString("Commands.GodMode.Enabled"));
        }

        mcMMOPlayer.toggleGodMode();
    }
}
