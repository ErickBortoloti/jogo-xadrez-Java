package xadrez;


import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import application.consoleColors;
import tabuleiro.peca;
import tabuleiro.posicao;
import tabuleiro.tabuleiror;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class XadrezMatch {
	
	private int turno;
	private Color jogadorAtual;
	private tabuleiror tabuleiro;
	private List<peca> pecasNoTabuleiro = new ArrayList<>();
	private List<peca> pecasCapturadas = new ArrayList<>();
	private boolean check;
	private boolean checkMate;
	private PecaXadrez jogadaEnPassant;
	private PecaXadrez evolucao;
	
	
	public XadrezMatch() {
		tabuleiro = new tabuleiror(8, 8);
		turno = 1;
		jogadorAtual = Color.BRANCO;
		setupInicial();
	}
	
	public int getTurno() {
		return turno;
	}
	
	public PecaXadrez getEvolucao() {
		return evolucao;
	}
	
	public Color getJogadorAtual() {
		return jogadorAtual;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate()  {
		return checkMate;
	}
	
	public PecaXadrez getJogadaEnPassant() {
		return jogadaEnPassant;
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
		
		PecaXadrez pecaMovida = (PecaXadrez)tabuleiro.peca(destino);
		
		//movimento especial evolucao
		evolucao =  null;
		
		if (pecaMovida instanceof Peao) {
			if((pecaMovida.getColor() == Color.BRANCO && destino.getLinha() == 0) || (pecaMovida.getColor() == Color.PRETO && destino.getLinha() == 7)) {
				evolucao = (PecaXadrez)tabuleiro.peca(destino);
				evolucao = recolarPecaEvoluida("Q");
			}
		}
		
		
		
		check = (testarCheck(oponente(jogadorAtual))) ? true : false;
		
		
		if (testarCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		}
		
		else {		
		proximoTurno();
		}
		
		// movimento especial en passant
		
		if (pecaMovida instanceof Peao && (destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2)) {
			jogadaEnPassant = pecaMovida;
		}
		
		else {
			jogadaEnPassant = null;
		}
		
		
		return (PecaXadrez)pecaCapturada;
	}
	
	public PecaXadrez recolarPecaEvoluida(String type) {
		if (evolucao == null) {
			throw new IllegalStateException("Sem peça para ser evoluida");
		}
		if (!type.equals("B") && !type.equals("C") && !type.equals("T") && !type.equals("Q")) {
			throw new InvalidParameterException("Tipo inválido para a mudança");
		}
		posicao pos = evolucao.getPosicaoXadrez().toPosicao();
		peca p = tabuleiro.removerPeca(pos);
		pecasNoTabuleiro.remove(p);
		
		PecaXadrez novaPeca = novaPeca(type, evolucao.getColor());
		tabuleiro.colocarPeca(novaPeca, pos);
		pecasNoTabuleiro.add(novaPeca);
		
		return novaPeca;
	}
	
	private PecaXadrez novaPeca(String type, Color cor) {
		if (type.equals("B")) return new Bispo(tabuleiro, cor);
		if (type.equals("C")) return new Cavalo(tabuleiro, cor);
		if (type.equals("T")) return new Torre(tabuleiro, cor);
		return new Rainha(tabuleiro, cor);
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
		PecaXadrez p = (PecaXadrez)tabuleiro.removerPeca(origem);
		p.acrescentarContarMovimento();
		peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, destino);
		
		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		
		//movimento especial castling lado rei
		
		if (p instanceof Rei & destino.getColuna() == origem.getColuna() + 2) {
			posicao origemT = new posicao(origem.getLinha(), origem.getColuna() + 3);
			posicao destinoT = new posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removerPeca(origemT);
			tabuleiro.colocarPeca(torre, destinoT);
			torre.acrescentarContarMovimento();
		}
		
		//movimento especial castling queenside rook

		if (p instanceof Rei & destino.getColuna() == origem.getColuna() - 2) {
			posicao origemT = new posicao(origem.getLinha(), origem.getColuna() - 4);
			posicao destinoT = new posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removerPeca(origemT);
			tabuleiro.colocarPeca(torre, destinoT);
			torre.acrescentarContarMovimento();
		}
		
		//movimento especial en passant
		
		if(p instanceof Peao) {
			if(origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
				posicao posicaopeao;
				if (p.getColor() == Color.BRANCO) {
					posicaopeao = new posicao(destino.getLinha() + 1, destino.getColuna());
				}
				else {
					posicaopeao = new posicao(destino.getLinha() - 1, destino.getColuna());
				}
				pecaCapturada = tabuleiro.removerPeca(posicaopeao);
				pecasCapturadas.add(pecaCapturada);
				pecasNoTabuleiro.remove(pecaCapturada);
			}
		}
		
		
		
		
		return pecaCapturada;
	}
	
	private void desfazerMovimento(posicao origem, posicao destino, peca pecaCapturada) {
		PecaXadrez p = (PecaXadrez)tabuleiro.removerPeca(destino);
		p.reduzirContarMovimentoi();
		tabuleiro.colocarPeca(p, origem);
		
		if (pecaCapturada != null) {
			tabuleiro.colocarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
		
	//movimento especial castling lado rei
		
		if (p instanceof Rei & destino.getColuna() == origem.getColuna() + 2) {
			posicao origemT = new posicao(origem.getLinha(), origem.getColuna() + 3);
			posicao destinoT = new posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removerPeca(destinoT);
			tabuleiro.colocarPeca(torre, origemT);
			torre.reduzirContarMovimentoi();
		}
		
		//movimento especial castling queenside rook

		if (p instanceof Rei & destino.getColuna() == origem.getColuna() - 2) {
			posicao origemT = new posicao(origem.getLinha(), origem.getColuna() - 4);
			posicao destinoT = new posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removerPeca(destinoT);
			tabuleiro.colocarPeca(torre, origemT);
			torre.reduzirContarMovimentoi();
		}
		
		//movimento especial en passant
		
		
		if(p instanceof Peao) {
			if(origem.getColuna() != destino.getColuna() && pecaCapturada == jogadaEnPassant) {
				PecaXadrez peao = (PecaXadrez)tabuleiro.removerPeca(destino);
				posicao posicaopeao;
				if (p.getColor() == Color.BRANCO) {
					posicaopeao = new posicao(3, destino.getColuna());
				}
				else {
					posicaopeao = new posicao(4, destino.getColuna());
				}
				
				tabuleiro.colocarPeca(peao, posicaopeao);
			}
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
	

	private boolean testarCheckMate(Color cor) {
		if (!testarCheck(cor)) {
			return false;
		}
		List<peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getColor() == cor).collect(Collectors.toList());
		for (peca p : lista) {
			boolean[][] mat = p.movimentosPossiveis();
			for (int i=0; i<tabuleiro.getLinhas(); i++) {
				for (int j=0; j< tabuleiro.getColunas(); j++) {
					if(mat[i][j]) {
						posicao origem = ((PecaXadrez)p).getPosicaoXadrez().toPosicao();
						posicao destino = new posicao(i,j);
						peca pecaCapturada = makeMove(origem, destino);
						boolean testarCheck = testarCheck(cor);
						desfazerMovimento(origem, destino, pecaCapturada);
						if (!testarCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	
	
	}
	
	public boolean mensagem() {
		if(turno <= 2) {
			System.out.println();
			System.out.print(consoleColors.RED_BACKGROUND + consoleColors.YELLOW + "OBSERVAÇÃO:" + consoleColors.RESET);
			System.out.print(consoleColors.RED_BACKGROUND + consoleColors.WHITE + " Q = Rainha!" + consoleColors.RESET);
		}
		return true;
	}
	
	
	private void colocarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
		pecasNoTabuleiro.add(peca);
		
	}
	
	private void setupInicial() {
		colocarNovaPeca('a', 1, new Torre(tabuleiro, Color.BRANCO));
		colocarNovaPeca('b', 1, new Cavalo(tabuleiro, Color.BRANCO));
		colocarNovaPeca('c', 1, new Bispo(tabuleiro, Color.BRANCO));
		colocarNovaPeca('d', 1, new Rainha(tabuleiro, Color.BRANCO));
		colocarNovaPeca('e', 1, new Rei(tabuleiro, Color.BRANCO, this));
		colocarNovaPeca('f', 1, new Bispo(tabuleiro, Color.BRANCO));
		colocarNovaPeca('g', 1, new Cavalo(tabuleiro, Color.BRANCO));
		colocarNovaPeca('h', 1, new Torre(tabuleiro, Color.BRANCO));
		colocarNovaPeca('a', 2, new Peao(tabuleiro, Color.BRANCO, this));
		colocarNovaPeca('b', 2, new Peao(tabuleiro, Color.BRANCO, this));
		colocarNovaPeca('c', 2, new Peao(tabuleiro, Color.BRANCO, this));
		colocarNovaPeca('d', 2, new Peao(tabuleiro, Color.BRANCO, this));
		colocarNovaPeca('e', 2, new Peao(tabuleiro, Color.BRANCO, this));
		colocarNovaPeca('f', 2, new Peao(tabuleiro, Color.BRANCO, this));
		colocarNovaPeca('g', 2, new Peao(tabuleiro, Color.BRANCO, this));
		colocarNovaPeca('h', 2, new Peao(tabuleiro, Color.BRANCO, this));
		
		colocarNovaPeca('a', 8, new Torre(tabuleiro, Color.PRETO));
		colocarNovaPeca('b', 8, new Cavalo(tabuleiro, Color.PRETO));
		colocarNovaPeca('c', 8, new Bispo(tabuleiro, Color.PRETO));
		colocarNovaPeca('d', 8, new Rainha(tabuleiro, Color.PRETO));
		colocarNovaPeca('e', 8, new Rei(tabuleiro, Color.PRETO, this));
		colocarNovaPeca('f', 8, new Bispo(tabuleiro, Color.PRETO));
		colocarNovaPeca('g', 8, new Cavalo(tabuleiro, Color.PRETO));
		colocarNovaPeca('h', 8, new Torre(tabuleiro, Color.PRETO));
		colocarNovaPeca('a', 7, new Peao(tabuleiro, Color.PRETO, this));
		colocarNovaPeca('b', 7, new Peao(tabuleiro, Color.PRETO, this));
		colocarNovaPeca('c', 7, new Peao(tabuleiro, Color.PRETO, this));
		colocarNovaPeca('d', 7, new Peao(tabuleiro, Color.PRETO, this));
		colocarNovaPeca('e', 7, new Peao(tabuleiro, Color.PRETO, this));
		colocarNovaPeca('f', 7, new Peao(tabuleiro, Color.PRETO, this));
		colocarNovaPeca('g', 7, new Peao(tabuleiro, Color.PRETO, this));
		colocarNovaPeca('h', 7, new Peao(tabuleiro, Color.PRETO, this));
	}

}
