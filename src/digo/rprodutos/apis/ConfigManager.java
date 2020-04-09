package digo.rprodutos.apis;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import digo.rprodutos.Main;

public class ConfigManager {

	public static void createConfig(String file) {
		if (!new File(Main.instance.getDataFolder(), file + ".yml").exists()) {
			Main.instance.saveResource(file + ".yml", false);
		}
	}

	public static FileConfiguration getConfig(String file) {
		File arquivo = new File(Main.instance.getDataFolder() + File.separator + file + ".yml");
		FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(arquivo);
		return config;
	}

}