package xadrez.pecas;

import tabuleiro.tabuleiror;
import xadrez.Color;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez {

	public Rei(tabuleiror tabuleiro, Color color) {
		super(tabuleiro, color);
	}
	
	@Override
	
	public String toString(){
		return "R";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		return mat;
	}

}
