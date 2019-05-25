package cn.wode490390.nukkit.playeruuidtool;

import cn.nukkit.IPlayer;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginIdentifiableCommand;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.plugin.Plugin;
import java.util.UUID;

public class QueryCommand extends Command implements PluginIdentifiableCommand {

    private final Plugin plugin;

    public QueryCommand(Plugin plugin) {
        super("playeruuidtool", "Querys player's UUID", "/playeruuidtool <player|UUID>", new String[]{"put"});
        this.setPermission("playeruuidtool.command");
        this.getCommandParameters().clear();
        this.addCommandParameters("byName", new CommandParameter[]{
                new CommandParameter("player", CommandParamType.TARGET, false)
        });
        this.addCommandParameters("byUUID", new CommandParameter[]{
                new CommandParameter("UUID", CommandParamType.STRING, false)
        });
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!this.plugin.isEnabled() || !this.testPermission(sender)) {
            return false;
        }
        if (args.length > 0) {
            IPlayer player;
            try {
                UUID uuid = UUID.fromString(args[0]);
                player = this.plugin.getServer().getOfflinePlayer(uuid);
                sender.sendMessage(player.getName() + " (" + uuid + ")");
            } catch (Exception ignore) {
                player = this.plugin.getServer().getOfflinePlayer(args[0]);
                sender.sendMessage(args[0] + " (" + player.getUniqueId() + ")");
            }
        } else {
            sender.sendMessage(new TranslationContainer("commands.generic.usage", this.getUsage()));
        }
        return true;
    }

    @Override
    public Plugin getPlugin() {
        return this.plugin;
    }
}
