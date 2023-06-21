package application;

import java.nio.charset.CoderResult;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import xadrez.Color;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezMatch;

public class UI {
	
	public static void limparTela() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
	
	public static PosicaoXadrez lerPosicaoPeca(Scanner teclado) {
		
		try {
			String s = teclado.nextLine();
			char coluna = s.charAt(0);
			int linha = Integer.parseInt(s.substring(1));
			return new PosicaoXadrez(coluna, linha);
		}
		catch (RuntimeException e) {
			throw new InputMismatchException("Erro lendo as posições do Xadrez. Valores válidos de a1 até h8");
		}
	}
	
	public static void printMatch(XadrezMatch xadrezmatch, List<PecaXadrez> capturadas) {
		printTabuleiror(xadrezmatch.getPecas());
		System.out.println();
		printPecasCapturas(capturadas);
		System.out.println();
		System.out.println("Turno: " + xadrezmatch.getTurno());
		System.out.println("Vez do jogador: " + xadrezmatch.getJogadorAtual());
	}
	
	public static void printTabuleiror(PecaXadrez[][] pecas) {
		
		for (int i=0; i<pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for(int j=0; j<pecas.length; j++) {
				printPeca(pecas[i][j], false);
				
			}
			System.out.println();
		}
		System.out.print("  a b c d e f g h");
	}
	
	public static void printTabuleiror(PecaXadrez[][] pecas, boolean[][] movimentosPossiveis) {
		
		for (int i=0; i<pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for(int j=0; j<pecas.length; j++) {
				printPeca(pecas[i][j], movimentosPossiveis[i][j]);
				
			}
			System.out.println();
		}
		System.out.print("  a b c d e f g h");
	}

	
	private static void printPeca(PecaXadrez peca, boolean fundo) {
		if (fundo) {
		System.out.print(consoleColors.YELLOW_BACKGROUND);
		}
		
		
		if (peca == null) {
			System.out.print("-" + consoleColors.RESET);
		}
		else {
			if (peca.getColor() == Color.BRANCO) {
				System.out.print(consoleColors.WHITE + peca + consoleColors.RESET);
			}
			else {
				System.out.print(consoleColors.GREEN + peca + consoleColors.RESET);
			}
	
		}
	 System.out.print(" ");	
	}
	
	private static void printPecasCapturas(List<PecaXadrez> capturadas) { //lista criada a partir das peças capturadas do jogo todo
		List<PecaXadrez> brancas = capturadas.stream().filter(x -> x.getColor() == Color.BRANCO).collect(Collectors.toList()); //filtrar lista operacoes lambdas
		List<PecaXadrez> pretas = capturadas.stream().filter(x -> x.getColor() == Color.PRETO).collect(Collectors.toList());
		System.out.println("Peças capturadas: ");
		System.out.print(consoleColors.WHITE_BACKGROUND + consoleColors.BLACK + "Brancas - ");
		System.out.println(Arrays.toString(brancas.toArray())); // jeito padrao de imprimir arrays no java
		System.out.println(consoleColors.RESET);
		System.out.print(consoleColors.GREEN_BACKGROUND + consoleColors.BLACK + "Pretas - ");
		System.out.println(Arrays.toString(pretas.toArray())); // jeito padrao de imprimir arrays no java
		System.out.println(consoleColors.RESET);
	}


	}

