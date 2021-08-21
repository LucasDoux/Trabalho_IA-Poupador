/*
 * Agente Poupador
 *
 * @author Lucas,Fabricio e Gabriel;
 * @since 20/08/2021
 */
package algoritmo;

import java.util.ArrayList;
import java.awt.Point;
import java.util.List;

public class Poupador extends ProgramaPoupador {

	private final int Sem_Utilidade = 0;

	private final int Ficar_Parado = 0;
	private final int Mover_Cima = 1;
	private final int Mover_Baixo = 2;
	private final int Mover_Direita= 3;
	private final int Mover_Esquerda = 4;

	private final int Sem_Visao=-2;
	private final int MundoExterior_Visao = -1;
	private final int CelulaVazia_Visao = 1;
	private final int Banco_Visao = 3;
	private final int Moeda_Visao = 4;
	private final int Pastilha_Poder_Visao = 5;
	private final int Poupador_Visao = 100;
	private final int Ladrao_Visao = 200;

	private final int Sem_Olfato = 0;
	private final int Um_Atras_Olfato = 1;
	private final int Dois_Atras_Olfato = 2 ;
	private final int Tres_Atras_Olfato = 3;
	private final int Quatro_Atras_Olfato = 4;
	private final int Cinco_Atras_Olfato = 5 ;

	private List <Point> areaVerificada = new ArrayList<Point>();

	public int acao() {
		return (int) (Math.random() * 5);

	}

}