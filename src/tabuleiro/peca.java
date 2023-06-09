package tabuleiro;

public class peca {
	
	protected posicao posicao;
	private tabuleiror tabuleiro;
	
	public peca(tabuleiror tabuleiro) {
		this.tabuleiro = tabuleiro;
		posicao = null;
	}

	protected tabuleiror getTabuleiro() {
		return tabuleiro;
	}
	
	
	
	

}
