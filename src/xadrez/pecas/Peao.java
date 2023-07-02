package xadrez.pecas;

import tabuleiro.posicao;
import tabuleiro.tabuleiror;
import xadrez.Color;
import xadrez.PecaXadrez;
import xadrez.XadrezMatch;

public class Peao extends PecaXadrez {
	
	
	private XadrezMatch xadrezMatch;

	public Peao(tabuleiror tabuleiro, Color color, XadrezMatch xadrezMatch) {
		super(tabuleiro, color);
		this.xadrezMatch = xadrezMatch;
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
			
			// movimento especial en passant branco
			
			if (posicao.getLinha() == 3) {
				posicao esquerda = new posicao(posicao.getLinha(),  posicao.getColuna() - 1);
				if (getTabuleiro().posicaoExiste(esquerda) && temUmOponente(esquerda) && getTabuleiro().peca(esquerda) == xadrezMatch.getJogadaEnPassant()) {
					mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
				}
				posicao direita = new posicao(posicao.getLinha(),  posicao.getColuna() + 1);
				if (getTabuleiro().posicaoExiste(direita) && temUmOponente(direita) && getTabuleiro().peca(direita) == xadrezMatch.getJogadaEnPassant()) {
					mat[direita.getLinha() - 1][direita.getColuna()] = true;
				}
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
				
				// movimento especial en passant preta
				
				if (posicao.getLinha() == 4) {
					posicao esquerda = new posicao(posicao.getLinha(),  posicao.getColuna() - 1);
					if (getTabuleiro().posicaoExiste(esquerda) && temUmOponente(esquerda) && getTabuleiro().peca(esquerda) == xadrezMatch.getJogadaEnPassant()) {
						mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
					}
					posicao direita = new posicao(posicao.getLinha(),  posicao.getColuna() + 1);
					if (getTabuleiro().posicaoExiste(direita) && temUmOponente(direita) && getTabuleiro().peca(direita) == xadrezMatch.getJogadaEnPassant()) {
						mat[direita.getLinha() + 1][direita.getColuna()] = true;
					}
				}
			
			}
				
			
			
		
			return mat;
	}

}
