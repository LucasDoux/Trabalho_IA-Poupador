package algoritmo;

import java.util.logging.Logger;

public class Poupador extends ProgramaPoupador {

	private static final Logger LOG = Logger.getLogger(Poupador.class.getName());
	private int matrizMemoria[][];

	public int acao() {

		// Iniciar matriz com -15
		if (matrizMemoria == null)
			instanciarMapa();

		LOG.info("Posição Atual: " + sensor.getPosicao());
		// Mapear ambiente
			mapearAmbiente();

		return (int) (Math.random() * 5);
	}

	public void instanciarMapa() {
		matrizMemoria = new int[30][30];
		for (int i = 0; i < matrizMemoria.length; i++) {
			for (int j = 0; j < matrizMemoria[i].length; j++) {
				matrizMemoria[i][j] = -12;
			}
		}
	}

	public void mapearAmbiente() {


		int[] visaoAgente = criarVisaoCompleta(sensor.getVisaoIdentificacao());
		Draw draw = new Draw(visaoAgente);
		
		int [][] matrizDesenhada = draw.getMatrizDesenhada();
		
		int linhaMatrizDesenhada = draw.getLinhas();
		int colunaMatrizDesenhada = draw.getColuna();
		
		if(sensor.getPosicao().x == 0 && sensor.getPosicao().y == 0) {
			for(int i=sensor.getPosicao().x ; i<linhaMatrizDesenhada; i++) {
				for(int j=sensor.getPosicao().y; j<colunaMatrizDesenhada; j++) {
					if(matrizMemoria[i][j] != -15)
						matrizMemoria[i][j] = matrizDesenhada[i][j];
				}
			}
		}
		
		System.out.println("----- Matriz Memória -----");
		printMatrizMemoria();
		
	}
	
	private void printMatrizMemoria() {
		for (int i = 0; i < matrizMemoria.length; i++) {
			for (int j = 0; j < matrizMemoria[i].length; j++) {
				System.out.print(matrizMemoria[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	private int[] criarVisaoCompleta(int[] visaoAgente) {

		int contador = 0;
		int indice = 0;
		int[] visaoAgenteCompleto = new int[25];

		while (contador < 25) {

			if (contador == 12)
				visaoAgenteCompleto[contador] = -15;
			
			else {
				visaoAgenteCompleto[contador] = visaoAgente[indice];
				indice += 1;
			}

			contador += 1;
		}

		return visaoAgenteCompleto;
	}
}