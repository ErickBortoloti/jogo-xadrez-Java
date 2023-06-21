package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezException;
import xadrez.XadrezMatch;

public class Principal {

	public static void main(String[] args) {
		
		
		Scanner teclado = new Scanner(System.in);
		XadrezMatch xadrezMatch = new XadrezMatch();
		List<PecaXadrez> capturadas = new ArrayList<>();
		
		while (true) {
			
			try {
			UI.limparTela();
			UI.printMatch(xadrezMatch, capturadas);
			System.out.println();
			System.out.print("Origem ");
			PosicaoXadrez jogada = UI.lerPosicaoPeca(teclado);
			
			boolean[][] movimentosPossiveis = xadrezMatch.movimentosPossiveis(jogada);
			UI.limparTela();
			UI.printTabuleiror(xadrezMatch.getPecas(), movimentosPossiveis);
			System.out.println();
			System.out.print("Destino: ");
			PosicaoXadrez destino = UI.lerPosicaoPeca(teclado);
			
			PecaXadrez captura = xadrezMatch.perfomaceMoverXadrez(jogada, destino); 
			
			if(captura != null) {
				capturadas.add(captura);
			}
			
			}
			catch (XadrezException e) {
				System.out.println(e.getMessage());
				teclado.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				teclado.nextLine();
			}
		}

		
	

	}

}
