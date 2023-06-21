package xadrez.pecas;

import tabuleiro.posicao;
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



	@Override
	public boolean[][] movimentosPossiveis() {
			boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
			posicao p = new posicao(0, 0);
			
			
			// acima
			p.setarValores(posicao.getLinha() - 1, posicao.getColuna());
			while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().issoEUmaPeca(p)) {
				mat[p.getLinha()] [p.getColuna()] = true;
				p.setLinha(p.getLinha() - 1);
				
			}
			if (getTabuleiro().posicaoExiste(p) && temUmOponente(p)) {
				mat[p.getLinha()] [p.getColuna()] = true;
			}
			
			// esquerda
			p.setarValores(posicao.getLinha(), posicao.getColuna() - 1);
			while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().issoEUmaPeca(p)) {
				mat[p.getLinha()] [p.getColuna()] = true;
				p.setColuna(p.getColuna() - 1);
				
			}
			if (getTabuleiro().posicaoExiste(p) && temUmOponente(p)) {
				mat[p.getLinha()] [p.getColuna()] = true;
			}
			
			// direita
			p.setarValores(posicao.getLinha(), posicao.getColuna() + 1);
			while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().issoEUmaPeca(p)) {
				mat[p.getLinha()] [p.getColuna()] = true;
				p.setColuna(p.getColuna() + 1);
				
			}
			if (getTabuleiro().posicaoExiste(p) && temUmOponente(p)) {
				mat[p.getLinha()] [p.getColuna()] = true;
			}
			
			//baixo
			p.setarValores(posicao.getLinha() + 1, posicao.getColuna());
			while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().issoEUmaPeca(p)) {
				mat[p.getLinha()] [p.getColuna()] = true;
				p.setLinha(p.getLinha() + 1);
				
			}
			if (getTabuleiro().posicaoExiste(p) && temUmOponente(p)) {
				mat[p.getLinha()] [p.getColuna()] = true;
			}
			
			
			
			return mat;
	}

}
