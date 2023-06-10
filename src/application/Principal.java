package application;

import tabuleiro.tabuleiror;
import xadrez.XadrezMatch;

public class Principal {

	public static void main(String[] args) {
		

		XadrezMatch partida = new XadrezMatch();
		UI.printTabuleiror(partida.getPecas());
		
	

	}

}
