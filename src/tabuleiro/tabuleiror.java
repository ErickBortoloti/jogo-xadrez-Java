package tabuleiro;

public class tabuleiror {
	
	private int linhas;
	private int colunas;
	private peca[][] pecas;
	
	
	public tabuleiror(int linhas, int colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new TabuleiroException("Erro ao criar tabuleiro: é necessário que haja ao menos uma linha ou coluna");
			
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new peca[linhas][colunas];
	}


	public int getLinhas() {
		return linhas;
	}


	public int getColunas() {
		return colunas;
	}

	
	public peca peca(int linha, int coluna) {
		if (!posicaoExiste(linha, coluna)) {
			throw new TabuleiroException("Posição não existe no tabuleiro");
		}
		return pecas[linha][coluna];
	}
	
	public peca peca(posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new TabuleiroException("Posição não existe no tabuleiro");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	public void colocarPeca(peca peca, posicao posicao) {
		if (issoEUmaPeca(posicao)) {
			throw new TabuleiroException("Já tem uma peça na posição: " + posicao);
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
	
	public peca removerPeca(posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new TabuleiroException("Posição não existe no tabuleiro");
		}
		if (peca(posicao) == null) {
			return null;
		}
		else {
			peca aux = peca(posicao);
			aux.posicao = null;
			pecas[posicao.getLinha()][posicao.getColuna()] = null;
			return aux;
		}
		
	}
	
	private boolean posicaoExiste(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}
	
	
	public boolean posicaoExiste(posicao posicao) {
		return posicaoExiste(posicao.getLinha(), posicao.getColuna());
	}
	
	
	public boolean issoEUmaPeca(posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new TabuleiroException("Posição não existe no tabuleiro");
		}
		return peca(posicao) != null;
	}

}
