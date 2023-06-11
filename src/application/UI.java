package application;

import xadrez.Color;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

import java.util.InputMismatchException;
import java.util.Scanner;

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
	
	public static void printTabuleiror(PecaXadrez[][] pecas) {
		
		for (int i=0; i<pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for(int j=0; j<pecas.length; j++) {
				printPeca(pecas[i][j]);
				
			}
			System.out.println();
		}
		System.out.print("  a b c d e f g h");
	}

	
	private static void printPeca(PecaXadrez peca) {
		if (peca == null) {
			System.out.print("-");
		}
		else {
			if (peca.getColor() == Color.WHITE) {
				System.out.print(consoleColors.WHITE + peca + consoleColors.RESET);
			}
			else {
				System.out.print(consoleColors.GREEN + peca + consoleColors.RESET);
			}
	
		}
	 System.out.print(" ");	
	}


	}

