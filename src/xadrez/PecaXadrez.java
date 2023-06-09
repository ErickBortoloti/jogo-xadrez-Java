package xadrez;

import tabuleiro.peca;
import tabuleiro.posicao;
import tabuleiro.tabuleiror;

public abstract class PecaXadrez extends peca{
	
	private Color color;
	private int contarMovimento;

	public PecaXadrez(tabuleiror tabuleiro, Color color) {
		super(tabuleiro);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	public int getContarMovimento() {
		return contarMovimento;
	}
	
	public void acrescentarContarMovimento() {
		contarMovimento++;
	}
	
	public void reduzirContarMovimentoi() {
		contarMovimento--;
	}
	
	
	protected boolean temUmOponente(posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p != null && p.getColor() != color;
	}
	
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.paraPosicao(posicao);
	}


	
	
}
