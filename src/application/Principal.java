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
		
		while (!xadrezMatch.getCheckMate()) {
			
			try {
			UI.limparTela();
			UI.printMatch(xadrezMatch, capturadas);
			xadrezMatch.mensagem();
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
			
			if (xadrezMatch.getEvolucao() != null) {
				System.out.println("QUal vai ser a evoluçaõ da sua peça? (B/C/Q/T)");
				System.out.println(consoleColors.RED_BACKGROUND + consoleColors.WHITE + "Lembrando: B = Bispo, C = Cavalo, Q = Rainha & T = Torre" + consoleColors.RESET);
				String type = teclado.next().toUpperCase();
				xadrezMatch.recolarPecaEvoluida(type);
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
		
		UI.limparTela();
		UI.printMatch(xadrezMatch, capturadas);

		
	

	}

}
