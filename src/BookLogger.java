import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class BookLogger extends JavaPlugin implements Listener {

    private final List<String> books = new ArrayList<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("BookLogger enabled.");
    }

    @EventHandler
    public void onBookSign(PlayerEditBookEvent event) {

        if (!event.isSigning()) {
            return;
        }

        BookMeta meta = event.getNewBookMeta();

if (meta == null) {
    return;
}

        StringBuilder data = new StringBuilder();

        data.append("Player: ").append(event.getPlayer().getName()).append("\n");
        data.append("Title: ").append(meta.getTitle()).append("\n");
        data.append("Author: ").append(meta.getAuthor()).append("\n");

        int page = 1;
        for (String text : meta.getPages()) {
            data.append("Page ").append(page++).append(": ").append(text).append("\n");
        }

        books.add(data.toString());

        getLogger().info("Saved book from " + event.getPlayer().getName());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!command.getName().equalsIgnoreCase("viewbooks")) {
            return false;
        }

        if (books.isEmpty()) {
            sender.sendMessage("No books have been saved.");
            return true;
        }

        sender.sendMessage("§6Saved Books:");

        int count = 1;
        for (String book : books) {
            sender.sendMessage("§e----- Book " + count++ + " -----");
            sender.sendMessage(book);
        }

        return true;
    }
}
