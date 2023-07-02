package xadrez.pecas;

import tabuleiro.posicao;
import tabuleiro.tabuleiror;
import xadrez.Color;
import xadrez.PecaXadrez;
import xadrez.XadrezMatch;

public class Rei extends PecaXadrez {
	
	
	private XadrezMatch xadrezmatch;

	public Rei(tabuleiror tabuleiro, Color color, XadrezMatch xadrezmatch) {
		super(tabuleiro, color);
		this.xadrezmatch = xadrezmatch;
	}
	
	@Override
	
	public String toString(){
		return "R";
	}
	
	private boolean podeMover(posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p == null || p.getColor() != getColor();
	}
	
	private boolean torreAptaRook(posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p != null && p instanceof Torre && p.getColor() == getColor() && p.getContarMovimento() == 0;
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		posicao p = new posicao(0,0);
		
		//acima 
		
		p.setarValores(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// abaixo
		
		p.setarValores(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//esquerda
		
		p.setarValores(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//direita
		
		p.setarValores(posicao.getLinha(), posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//noroeste
		
		p.setarValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//nordeste
		
		
		p.setarValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//sudoeste
		
		p.setarValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//sudeste
		
		p.setarValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//movimento especial castling
		if (getContarMovimento() == 0 && !xadrezmatch.getCheck()) {
			//movimento especial castling, lado do rei
			posicao posicao1 = new posicao(posicao.getLinha(), posicao.getColuna() + 3);
			if (torreAptaRook(posicao1)) {
				posicao p1 = new posicao(posicao.getLinha(), posicao.getColuna() + 1);
				posicao p2 = new posicao(posicao.getLinha(), posicao.getColuna() + 2);
				if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
					mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
				}
			}
			//movimento especial castling, lado do rei
			posicao posicao2 = new posicao(posicao.getLinha(), posicao.getColuna() - 4);
			if (torreAptaRook(posicao2)) {
				posicao p1 = new posicao(posicao.getLinha(), posicao.getColuna() - 1);
				posicao p2 = new posicao(posicao.getLinha(), posicao.getColuna() - 2);
				posicao p3 = new posicao(posicao.getLinha(), posicao.getColuna() - 3);
				if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null) {
					mat[posicao.getLinha()][posicao.getColuna() -2 ] = true;
				}
			}
			
		}
		
		return mat;
	}

}
