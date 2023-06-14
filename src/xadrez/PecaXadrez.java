package xadrez;

import tabuleiro.peca;
import tabuleiro.tabuleiror;

public abstract class PecaXadrez extends peca{
	
	private Color color;

	public PecaXadrez(tabuleiror tabuleiro, Color color) {
		super(tabuleiro);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}


	
	
}
