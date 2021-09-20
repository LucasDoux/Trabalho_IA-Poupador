package algoritmo;

import java.util.logging.Logger;

public class Draw {

	private int[][] matrizVisao;
	private int[][] matrizDesenhada;
	private int totalUnidades;
	private int linhas;
	private int colunas;

	public Draw(int[] visaoAgente) {
		transformarVetorVisaoParaMatriz(visaoAgente);
		desenharMatrizMenor();

		System.out.println("----- Matriz Visao -----");
		printMatrizPrincipal();
		
		System.out.println(" ");
		
		System.out.println("----- Matriz Desenhada -----");
		printMatrizDesenhada();

	}

	public int getTotalUnidades() {
		return this.totalUnidades;
	}

	public int getLinhas() {
		return this.linhas;
	}

	public int getColuna() {
		return this.colunas;
	}

	public int[][] getMatrizDesenhada() {
		return this.matrizDesenhada;
	}

	private void transformarVetorVisaoParaMatriz(int[] visaoAgente) {
		matrizVisao = new int[5][5];
		int contador = -1;

		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				contador += 1;
				if (contador == 24)
					break;

				matrizVisao[y][x] = visaoAgente[contador];
			}
		}
	}

	private void printMatrizPrincipal() {
		for (int i = 0; i < matrizVisao.length; i++) {
			for (int j = 0; j < matrizVisao[i].length; j++) {
				System.out.print(matrizVisao[i][j] + " ");
			}
			System.out.println();
		}

	}

	private void printMatrizDesenhada() {

		for (int i = 0; i < matrizDesenhada.length; i++) {
			for (int j = 0; j < matrizDesenhada[i].length; j++) {
				System.out.print(matrizDesenhada[i][j] + " ");
			}
			System.out.println();
		}
	}

	private void desenharMatrizMenor() {

		linhas = contagemLinhas(matrizVisao);
		totalUnidades = contagemTotalUnidades(matrizVisao);
		colunas = contagemColunas(totalUnidades, linhas);
		
		System.out.println();
		System.out.println("X: " + linhas);
		System.out.println("Y: " + colunas);

		matrizDesenhada = new int[colunas][linhas];
		int[] array = new int[totalUnidades];
		int contador = 0;

		for (int x = 0; x < matrizVisao.length; x++) {
			for (int y = 0; y < matrizVisao[x].length; y++) {
				if (matrizVisao[x][y] != -1) {
					array[contador] = matrizVisao[x][y];
					contador += 1;
				}
			}
		}

		contador = -1;

		for (int y = 0; y < colunas; y++) {
			for (int x = 0; x < linhas; x++) {
				contador += 1;
				if (contador == totalUnidades)
					break;

				matrizDesenhada[y][x] = array[contador];
			}
		}

	}

	private int contagemColunas(int totalUnidades, int linhas) {
		return totalUnidades / linhas;
	}

	private int contagemLinhas(int[][] matriz) {
		int xx = 0;
		boolean statusFlag = false;

		for (int y = 0; y < matriz.length; y++) {
			for (int x = 0; x < matriz[y].length; x++) {
				if (matriz[y][x] != -1) {
					xx += 1;
					statusFlag = true;
				}
			}
			if (statusFlag == true)
				break;
		}

		return xx;
	}

	private int contagemTotalUnidades(int[][] matriz) {
		int yy = 0;
		for (int y = 0; y < matriz.length; y++) {
			for (int x = 0; x < matriz[y].length; x++) {
				if (matriz[y][x] != -1) {
					yy += 1;
				}
			}
		}
		return yy;
	}
}
