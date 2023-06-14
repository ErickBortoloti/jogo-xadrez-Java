package tabuleiro;

public abstract class peca {
	
	protected posicao posicao;
	private tabuleiror tabuleiro;
	
	public peca(tabuleiror tabuleiro) {
		this.tabuleiro = tabuleiro;
		posicao = null;
	}

	protected tabuleiror getTabuleiro() {
		return tabuleiro;
	}
	
	public abstract boolean[][] movimentosPossiveis();
	
	public boolean movimentosPossiveis(posicao posicao) {
		return movimentosPossiveis()[posicao.getLinha()][posicao.getColuna()];
	}
	
	public boolean ePossivelTerMovimento() {
		boolean[][] mat = movimentosPossiveis();
		for(int i=0; i<mat.length; i++) {
			for (int j=0; j<mat.length; i++) {
				if(mat[i] [j]) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	

}
