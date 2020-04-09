package digo.rprodutos.eventos;

import org.bukkit.configuration.file.FileConfiguration;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import digo.rprodutos.Main;

public class AoEntrar implements Listener {
	
	FileConfiguration config = Main.instance.getConfig();

	@EventHandler
	public void aoEntrar(PlayerJoinEvent e) {
		for (String key : config.getConfigurationSection("produtos").getKeys(false)) {
			if (config.getStringList("produtos." + key + ".jogadores").contains(e.getPlayer().getName())) {
				e.getPlayer().sendMessage(config.getString("mensagens.jogador-entrou").replace("&", "§"));
				return;
			}
		}
	}

}
