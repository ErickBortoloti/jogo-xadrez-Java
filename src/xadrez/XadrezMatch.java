package xadrez;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import application.consoleColors;
import tabuleiro.peca;
import tabuleiro.posicao;
import tabuleiro.tabuleiror;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class XadrezMatch {
	
	private int turno;
	private Color jogadorAtual;
	private tabuleiror tabuleiro;
	private List<peca> pecasNoTabuleiro = new ArrayList<>();
	private List<peca> pecasCapturadas = new ArrayList<>();
	private boolean check;
	
	
	public XadrezMatch() {
		tabuleiro = new tabuleiror(8, 8);
		turno = 1;
		jogadorAtual = Color.BRANCO;
		setupInicial();
	}
	
	public int getTurno() {
		return turno;
	}
	
	public Color getJogadorAtual() {
		return jogadorAtual;
	}
	
	public boolean getCheck() {
		return check;
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
	
	public boolean[][] movimentosPossiveis(PosicaoXadrez pontoDePartida) {
		posicao posicao = pontoDePartida.toPosicao();
		validarPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}
	
	public PecaXadrez perfomaceMoverXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		posicao origem = posicaoOrigem.toPosicao();
		posicao destino = posicaoDestino.toPosicao();
		validarPosicaoOrigem(origem);
		validarDestino(origem, destino);
		peca pecaCapturada = makeMove(origem, destino);
		
		if (testarCheck(jogadorAtual)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new XadrezException(consoleColors.RED_BACKGROUND + consoleColors.WHITE + "Você não pode se colocar em check!" + consoleColors.RESET);
		}
		
		check = (testarCheck(oponente(jogadorAtual))) ? true : false;
		
		proximoTurno();
		return (PecaXadrez)pecaCapturada;
	}
	
	private void validarPosicaoOrigem(posicao posicao) {
		if (!tabuleiro.issoEUmaPeca(posicao)) {
			throw new XadrezException("Não tem nenhuma peça na posição de origem");
		}
		if (jogadorAtual != ((PecaXadrez)tabuleiro.peca(posicao)).getColor()) {
			throw new XadrezException("Essa não é sua peça!");
		}
		if (!tabuleiro.peca(posicao).ePossivelTerMovimento()) {
			throw new XadrezException("Não existe movimentos possiveis para a peça escolhida");
			
		}
	}

	private void validarDestino(posicao origem, posicao destino) {
		if (!tabuleiro.peca(origem).movimentosPossiveis(destino)) {
			throw new XadrezException("A peça escolhida não pode se mover para a posição de destino");
		}
	}
	
	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Color.BRANCO) ? Color.PRETO : Color.BRANCO;
		
	}
	private peca makeMove(posicao origem, posicao destino) {
		peca p = tabuleiro.removerPeca(origem);
		peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, destino);
		
		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		return pecaCapturada;
	}
	
	private void desfazerMovimento(posicao origem, posicao destino, peca pecaCapturada) {
		peca p = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, origem);
		
		if (pecaCapturada != null) {
			tabuleiro.colocarPeca(pecaCapturada, origem);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
	}
	
	private Color oponente(Color cor) {
		return (cor == Color.BRANCO) ? Color.PRETO : Color.BRANCO;
	}
	
	private PecaXadrez rei(Color cor) {
		List<peca> lista = pecasNoTabuleiro.stream().filter(x ->((PecaXadrez)x).getColor() == cor).collect(Collectors.toList());
		for (peca p : lista) {
			if (p instanceof Rei) {
				return (PecaXadrez)p;
			}
		}
		throw new IllegalStateException("Não existe nenhum rei da cor " + cor + "no tabuleiro!");
	}
	
	private boolean testarCheck(Color cor) {
		posicao posicaoRei = rei(cor).getPosicaoXadrez().toPosicao();
		List<peca> pecasOponente = pecasNoTabuleiro.stream().filter(x ->((PecaXadrez)x).getColor() == oponente(cor)).collect(Collectors.toList());
		for (peca p : pecasOponente) {
			boolean[][] mat = p.movimentosPossiveis();
			if (mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	private void colocarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
		pecasNoTabuleiro.add(peca);
		
	}
	
	private void setupInicial() {
		colocarNovaPeca('c', 1, new Torre(tabuleiro, Color.BRANCO));
		colocarNovaPeca('c', 2, new Torre(tabuleiro, Color.BRANCO));
		colocarNovaPeca('d', 2, new Torre(tabuleiro, Color.BRANCO));
		colocarNovaPeca('e', 2, new Torre(tabuleiro, Color.BRANCO));
		colocarNovaPeca('e', 1, new Torre(tabuleiro, Color.BRANCO));
		colocarNovaPeca('d', 1, new Rei(tabuleiro, Color.BRANCO));
		
		colocarNovaPeca('c', 7, new Torre(tabuleiro, Color.PRETO));
		colocarNovaPeca('c', 8, new Torre(tabuleiro, Color.PRETO));
		colocarNovaPeca('d', 7, new Torre(tabuleiro, Color.PRETO));
		colocarNovaPeca('e', 7, new Torre(tabuleiro, Color.PRETO));
		colocarNovaPeca('e', 8, new Torre(tabuleiro, Color.PRETO));
		colocarNovaPeca('d', 8, new Rei(tabuleiro, Color.PRETO));
	}

}
