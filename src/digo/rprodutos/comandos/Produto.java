package digo.rprodutos.comandos;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import digo.rprodutos.Main;

public class Produto implements CommandExecutor {

	FileConfiguration config = Main.instance.getConfig();

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
		if (!(s.hasPermission(config.getString("config.comando-permissao")))) {
			s.sendMessage(config.getString("mensagens.sem-permissao").replace("&", "§"));
			return true;
		}

		if (args.length != 3) {
			s.sendMessage(config.getString("mensagens.comando-incorreto").replace("&", "§"));
			return true;
		}

		if (!(config.contains("produtos." + args[1]))) {
			s.sendMessage(
					config.getString("mensagens.produto-incorreto").replace("@produto", args[1]).replace("&", "§"));
			return true;
		}

		if (args[0].equalsIgnoreCase("adicionar")) {
			ArrayList<String> jogadores = new ArrayList<String>();
			if (args[2].equalsIgnoreCase("@a")) {
				for (String jogador : config.getStringList("produtos." + args[1] + ".jogadores")) {
					jogadores.add(jogador);
				}
				for (Player pl : Bukkit.getOnlinePlayers()) {
					jogadores.add(pl.getName());
				}
				s.sendMessage(config.getString("mensagens.produto-enviado-todos").replace("@produto", args[1]).replace("&", "§"));
			} else {
				for (String jogador : config.getStringList("produtos." + args[1] + ".jogadores")) {
					jogadores.add(jogador);
				}
				jogadores.add(args[2]);
				s.sendMessage(config.getString("mensagens.produto-enviado").replace("@produto", args[1])
						.replace("@jogador", args[2]).replace("&", "§"));
				if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[2]))) {
					Player p = Bukkit.getPlayer(args[2]);
					p.sendMessage(
							config.getString("mensagens.produto-recebido").replace(args[1], "@produto").replace("&", "§"));
				} else {
					s.sendMessage(config.getString("mensagens.produto-salvo").replace("@produto", args[1])
							.replace("@jogador", args[2]).replace("&", "§"));
				}
			}
			config.set("produtos." + args[1] + ".jogadores", jogadores);
			Main.instance.getConfig().options().copyDefaults(true);
			Main.instance.saveConfig();

		} else if (args[0].equalsIgnoreCase("remover")) {
			ArrayList<String> jogadores = new ArrayList<String>();
			for (String jogador : config.getStringList("produtos." + args[1] + ".jogadores")) {
				jogadores.add(jogador);
			}
			jogadores.remove(args[2]);
			if (config.getStringList("produtos." + args[1] + ".jogadores").contains(args[2])) {
				config.set("produtos." + args[1] + ".jogadores", jogadores);
				Main.instance.getConfig().options().copyDefaults(true);
				Main.instance.saveConfig();
				s.sendMessage(config.getString("mensagens.produto-removido").replace("@produto", args[1])
						.replace("@jogador", args[2]).replace("&", "§"));
			} else {
				s.sendMessage(config.getString("mensagens.produto-remover-erro").replace("@produto", args[1])
						.replace("@jogador", args[2]).replace("&", "§"));

			}
		} else {
			s.sendMessage(config.getString("mensagens.comando-incorreto").replace("&", "§"));
		}
		return false;
	}

}
