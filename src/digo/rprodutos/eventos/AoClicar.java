package digo.rprodutos.eventos;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import digo.rprodutos.Main;

public class AoClicar implements Listener {

	FileConfiguration config = Main.instance.getConfig();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void aoClicar(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player))
			return;
		if (e.getCurrentItem() == null)
			return;
		if (e.getCurrentItem().getType() == Material.AIR)
			return;
		if (!(e.getInventory().getTitle()
				.equals(Main.instance.getConfig().getString("config.menu-nome").replace("&", "§"))))
			return;
		if (!(e.getCurrentItem().hasItemMeta()))
			return;
		if (!(e.getCurrentItem().getItemMeta().hasDisplayName()))
			return;
		if (e.getCurrentItem().getAmount() != 1)
			return;
		e.setCancelled(true);
		Player p = (Player) e.getWhoClicked();
		for (String key : config.getConfigurationSection("produtos").getKeys(false)) {
			String nome = config.getString("produtos." + key + ".nome").replace("&", "§");
			Integer id = config.getInt("produtos." + key + ".id");
			String titulo = config.getString("produtos." + key + ".titulo").replace("&", "§").replace("@jogador",
					p.getName());
			;
			String subtitulo = config.getString("produtos." + key + ".subtitulo").replace("&", "§").replace("@jogador",
					p.getName());
			;
			if (e.getCurrentItem().getItemMeta().getDisplayName().equals(nome)) {
				if (e.getCurrentItem().getTypeId() == id) {
					p.closeInventory();
					ArrayList<String> jogadores = new ArrayList<String>();
					for (String jogador : config.getStringList("produtos." + key + ".jogadores")) {
						jogadores.add(jogador);
					}
					jogadores.remove(p.getName());
					if (config.getStringList("produtos." + key + ".jogadores").contains(p.getName())) {
						config.set("produtos." + key + ".jogadores", jogadores);
						Main.instance.getConfig().options().copyDefaults(true);
						Main.instance.saveConfig();
					} else {
						p.sendMessage("§cOcorreu um erro ao ativar este produto, por favor contate um staffer.");
						return;
					}

					for (String comando : config.getStringList("produtos." + key + ".comandos")) {
						comando = comando.replace("@jogador", p.getName());
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), comando);
					}
					for (Player pl : Bukkit.getOnlinePlayers()) {
						pl.sendTitle(titulo, subtitulo);
					}
				}
			}
		}
	}
}
