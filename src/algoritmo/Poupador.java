/*
 * Agente Poupador
 *
 * @author Lucas,Fabricio e Gabriel;
 * @since 20/08/2021
 */
package algoritmo;

import java.util.ArrayList;
import java.util.Random;

public class Poupador extends ProgramaPoupador {

	PossibilidadeMovimento acima, esquerda, direita, baixo, parado;
	static int teste = 0;
	static int xBanco = -1;
	static int yBanco = -1;
	public class PossibilidadeMovimento {
		boolean isViavel;
		int x;
		int y;
		Visao visao;
		int coordenadaVisao;
		int comandoMovimento;
		PossibilidadeMovimento oposto;

		public PossibilidadeMovimento(boolean isViavel, int x, int y, Visao visao, int coordenadaVisao, int comandoMovimento){
			this.isViavel = isViavel;
			this.x = x;
			this.y = y;
			this.visao = visao;
			this.coordenadaVisao = coordenadaVisao;
			this.comandoMovimento = comandoMovimento;
		}
	}

	//Mapeamento de movimentos, ainda falta uma variavel com parametro, possivelmente um boolean
	void mapearMovimentos(){
		int x = sensor.getPosicao().x;
		int y = sensor.getPosicao().y;
		acima = new PossibilidadeMovimento(movimentoIsViavel(visaoacima), x, y-1, getResultVisao(sensor.getVisaoIdentificacao()[visaoacima]), visaoacima, movercima);
		esquerda = new PossibilidadeMovimento(movimentoIsViavel(visaoesquerda), x-1, y, getResultVisao(sensor.getVisaoIdentificacao()[visaoesquerda]), visaoesquerda, moveresquerda);
		direita = new PossibilidadeMovimento(movimentoIsViavel(visaodireita), x+1, y, getResultVisao(sensor.getVisaoIdentificacao()[visaodireita]), visaodireita, moverdireita);
		baixo = new PossibilidadeMovimento(movimentoIsViavel(visaobaixo), x, y+1, getResultVisao(sensor.getVisaoIdentificacao()[visaobaixo]), visaobaixo, moverbaixo);
		parado = new PossibilidadeMovimento(true, x, y, Visao.CELULA_VAZIA, 0, 0);
		acima.oposto = baixo;
		esquerda.oposto = direita;
		direita.oposto = esquerda;
		baixo.oposto = acima;
	}

	public enum Visao{
		SEM_VISAO(-2),
		MUNDO_EXTERIOR(-1),
		CELULA_VAZIA(0),
		PAREDE(1),
		BANCO(3),
		MOEDA(4),
		PASTINHA_PODER(5),
		POUPADOR(100),
		LADRAO(200);
		Visao(int i){ valor = i;}
		private final int valor;
		public int get() {return valor;}

	}

	public Objetivo objetivo;

	public enum Objetivo{
		EXPLORAR,
		FUGIR,
		GUARDAR_MOEDAS
	}

	public Visao getResultVisao(int i){
		switch(i){
			case -2:
				return Visao.SEM_VISAO;
			case -1:
				return Visao.MUNDO_EXTERIOR;
			case 0:
				return Visao.CELULA_VAZIA;
			case 1:
				return Visao.PAREDE;
			case 3:
				return Visao.BANCO;
			case 4:
				return Visao.MOEDA;
			case 5:
				return Visao.PASTINHA_PODER;
			case 100:
				return Visao.POUPADOR;
			case 200:
				return Visao.LADRAO;
			default:
				return Visao.CELULA_VAZIA;
		}
	}

	//Movimentação do agente
	int ficarparado = 0;
	int movercima = 1;
	int moverbaixo = 2;
	int moverdireita = 3;
	int moveresquerda = 4;

	//Representação da visão do agente-poupador
	int visaoacima = 7;
	int visaoesquerda = 11;
	int visaodireita = 12;
	int visaobaixo = 16;

	int x, y, ultimox, ultimoy, penultimox, penultimoy;

	int[][] memoria = new int[31][31]; //tamanho do labirinto, sempre 31x31


	public int acao() {
		return (int) (Math.random() * 5);
	}

}