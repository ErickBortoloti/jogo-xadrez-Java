package xadrez;

import tabuleiro.tabuleiror;

public class XadrezMatch {
	
	private tabuleiror tabuleiro;
	
	
	public XadrezMatch() {
		tabuleiro = new tabuleiror(8, 8);
	}
	
	public PecaXadrez[][] getPecas() {
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getColunas(); i++) {
			for (int j=0; j<tabuleiro.getLinhas(); j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i,j);
			}
		}
		return mat;
		
	}

}
