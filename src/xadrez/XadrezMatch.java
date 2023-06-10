package xadrez;

import tabuleiro.posicao;
import tabuleiro.tabuleiror;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class XadrezMatch {
	
	private tabuleiror tabuleiro;
	
	
	public XadrezMatch() {
		tabuleiro = new tabuleiror(8, 8);
		setupInicial();
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
	
	private void setupInicial() {
		tabuleiro.colocarPeca(new Torre(tabuleiro, Color.WHITE), new posicao(2,1));
		tabuleiro.colocarPeca(new Rei(tabuleiro, Color.BLACK), new posicao(0, 4));
		tabuleiro.colocarPeca(new Rei(tabuleiro, Color.WHITE), new posicao(7,4) );
	}

}
