package application;

import xadrez.Color;
import xadrez.PecaXadrez;

public class UI {
	
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

