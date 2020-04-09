package digo.rprodutos;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import digo.rprodutos.comandos.Produto;
import digo.rprodutos.comandos.Produtos;
import digo.rprodutos.eventos.AoClicar;
import digo.rprodutos.eventos.AoEntrar;

public class Main extends JavaPlugin{

	public static Main instance;
	
	@Override
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		getCommand("produto").setExecutor(new Produto());
		getCommand("produtos").setExecutor(new Produtos());
		Bukkit.getPluginManager().registerEvents(new AoClicar(), this);
		Bukkit.getPluginManager().registerEvents(new AoEntrar(), this);
	}
}
