package xadrez.pecas;

import tabuleiro.tabuleiror;
import xadrez.Color;
import xadrez.PecaXadrez;

public class Torre extends PecaXadrez {

	public Torre(tabuleiror tabuleiro, Color color) {
		super(tabuleiro, color);
	}
	
	
	
	@Override
	public String toString() {
		return "T";
	}

}
