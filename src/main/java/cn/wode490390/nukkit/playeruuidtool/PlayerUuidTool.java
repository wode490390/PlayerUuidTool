package cn.wode490390.nukkit.playeruuidtool;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketSendEvent;
import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

public class PlayerUuidTool extends PluginBase implements Listener {

    private static final String CONFIG_CONSOLE_SHOW = "console-show";

    private boolean show;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        Config config = this.getConfig();
        String node = CONFIG_CONSOLE_SHOW;
        try {
            this.show = config.getBoolean(node, true);
        } catch (Exception e) {
            this.show = true;
            this.logLoadException(node);
        }
        if (this.show) {
            this.getServer().getPluginManager().registerEvents(this, this);
        }
        this.getServer().getCommandMap().register("playeruuidtool", new QueryCommand(this));
        try {
            new MetricsLite(this);
        } catch (Exception ignore) {

        }
    }

    @EventHandler
    public void onDataPacketSend(DataPacketSendEvent event) {
        if (event.getPacket().pid() == ProtocolInfo.RESOURCE_PACKS_INFO_PACKET) { //The player has been initialized on server side
            Player player = event.getPlayer();
            this.getServer().getConsoleSender().sendMessage(player.getName() + " (" + player.getUniqueId() + ")");
        }
    }

    private void logLoadException(String node) {
        this.getLogger().alert("An error occurred while reading the configuration '" + node + "'. Use the default value.");
    }
}
