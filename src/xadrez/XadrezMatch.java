package xadrez;


import tabuleiro.peca;
import tabuleiro.posicao;
import tabuleiro.tabuleiror;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class XadrezMatch {
	
	private int turno;
	private Color jogadorAtual;
	private tabuleiror tabuleiro;
	
	
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
		return pecaCapturada;
	}
	
	
	private void colocarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
		
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
