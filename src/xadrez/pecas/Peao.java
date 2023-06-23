package xadrez.pecas;

import tabuleiro.posicao;
import tabuleiro.tabuleiror;
import xadrez.Color;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

	public Peao(tabuleiror tabuleiro, Color color) {
		super(tabuleiro, color);
		}

	@Override
	
	public String toString(){
		return "P";
	}
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		posicao p = new posicao(0, 0);
		
		if(getColor() == Color.BRANCO) {

		
			p.setarValores(posicao.getLinha() - 2, posicao.getColuna());
			posicao p2 = new posicao(posicao.getLinha() - 1, posicao.getColuna());
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().issoEUmaPeca(p) && getContarMovimento() == 0 && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().issoEUmaPeca(p2)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setarValores(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().issoEUmaPeca(p)) {
			    mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setarValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExiste(p) && temUmOponente(p)) {
			    mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setarValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExiste(p) && temUmOponente(p)) {
			    mat[p.getLinha()][p.getColuna()] = true;
			}
		}
			else {
				p.setarValores(posicao.getLinha() + 1, posicao.getColuna());
				if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().issoEUmaPeca(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
				p.setarValores(posicao.getLinha() + 2, posicao.getColuna());
				posicao p3 = new posicao(posicao.getLinha() + 1, posicao.getColuna());
				if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().issoEUmaPeca(p) && getContarMovimento() == 0 && getTabuleiro().posicaoExiste(p3) && !getTabuleiro().issoEUmaPeca(p3)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
				
				p.setarValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
				if(getTabuleiro().posicaoExiste(p) && temUmOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
				p.setarValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
				if(getTabuleiro().posicaoExiste(p) && temUmOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
			
			}
				
			
			
		
			return mat;
	}

}
