package digo.rprodutos.comandos;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import digo.rprodutos.Main;
import digo.rprodutos.apis.ConfigManager;

public class Produtos implements CommandExecutor {

	FileConfiguration config = Main.instance.getConfig();

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
		if (!(s instanceof Player)) {
			s.sendMessage(ConfigManager.getConfig("mensagens").getString("mensagens.console-erro").replace("&", "§"));
			return true;
		}
		Player p = (Player) s;
		Inventory produtos = Bukkit.createInventory(null, 6 * 9, Main.instance.getConfig().getString("config.menu-nome").replace("&", "§"));
		if (config.getConfigurationSection("produtos") == null) {
			p.openInventory(produtos);
			return true;
		}

		for (String key : config.getConfigurationSection("produtos").getKeys(false)) {
			if (config.getStringList("produtos." + key + ".jogadores").contains(p.getName())) {
				String nome = config.getString("produtos." + key + ".nome").replace("&", "§");
				List<String> lore = config.getStringList("produtos." + key + ".lore");
				Integer id = config.getInt("produtos." + key + ".id");
				Integer cdata = config.getInt("produtos." + key + ".data");
				Short data = cdata.shortValue();
				Boolean glow = config.getBoolean("produtos." + key + ".glow");

				@SuppressWarnings("deprecation")
				ItemStack produto = new ItemStack(Material.getMaterial(id), 1, (short) data);
				ItemMeta produtometa = produto.getItemMeta();
				produtometa.setDisplayName(nome);
				produtometa.setLore(coloredLore(lore));
				if (glow == true) {
					produtometa.addEnchant(Enchantment.SILK_TOUCH, 1, true);
					produtometa.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				}
				produto.setItemMeta(produtometa);
				for (String jogador : config.getStringList("produtos." + key + ".jogadores")) {
					if (jogador.equals(p.getName())) {
						produtos.addItem(produto);
					}
				}
			}
		}

		p.openInventory(produtos);
		return false;
	}

	private static List<String> coloredLore(List<String> oldLore) {
		List<String> coloredLore = new ArrayList<>();
		for (String s : oldLore) {
			coloredLore.add(s.replace('&', '§'));
		}
		return coloredLore;
	}
}
