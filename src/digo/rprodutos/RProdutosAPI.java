package digo.rprodutos;

import java.util.ArrayList;

public class RProdutosAPI {

	public static void adicionar(String jogador, String produto) {
		ArrayList<String> jogadores = new ArrayList<String>();
		for (String player : Main.instance.getConfig().getStringList("produtos." + produto + ".jogadores")) {
			jogadores.add(player);
		}
		jogadores.add(jogador);
		Main.instance.getConfig().set("produtos." + produto + ".jogadores", jogadores);
		Main.instance.saveConfig();
	}
	
	public static void remover(String jogador, String produto) {
		ArrayList<String> jogadores = new ArrayList<String>();
		for (String player : Main.instance.getConfig().getStringList("produtos." + produto + ".jogadores")) {
			jogadores.add(player);
		}
		jogadores.remove(jogador);
		Main.instance.getConfig().set("produtos." + produto + ".jogadores", jogadores);
		Main.instance.saveConfig();
	}
}
