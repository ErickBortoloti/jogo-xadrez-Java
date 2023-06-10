package tabuleiro;

public class tabuleiror {
	
	private int linhas;
	private int colunas;
	private peca[][] pecas;
	
	
	public tabuleiror(int linhas, int colunas) {
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new peca[linhas][colunas];
	}


	public int getLinhas() {
		return linhas;
	}


	public void setLinhas(int linhas) {
		this.linhas = linhas;
	}


	public int getColunas() {
		return colunas;
	}


	public void setColunas(int colunas) {
		this.colunas = colunas;
	}
	
	public peca peca(int linha, int coluna) {
		return pecas[linha][coluna];
	}
	
	public peca peca(posicao posicao) {
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	

}
