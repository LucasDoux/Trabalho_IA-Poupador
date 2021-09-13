package algoritmo;
import java.util.*;
import java.util.function.*;
import graph.*;
import java.io.File;
import java.io.FileNotFoundException;
public class Poupador extends ProgramaPoupador {
	//private int[] priority = {0,4};
	private int[][] map = new int[30][30];
	private Node[][] nodeMatrix = new Node[30][30];
	private boolean flag = true;
	private List<Integer> commandList;
	private HashMap<Integer,Supplier<Integer>> priority = new HashMap<Integer,Supplier<Integer>>();
	private HashMap<Integer,Integer> sensor2command = new HashMap<Integer,Integer>();

	public Poupador(){
		Supplier<Integer> roberPriority = () -> {return 0;};
		Supplier<Integer> voidPriority = () ->  {return 1;};
		Supplier<Integer> moneyPriority = () -> {return 2;};
		this.priority.put(200,roberPriority);
		this.priority.put(0,voidPriority);
		this.priority.put(4,moneyPriority);
		this.sensor2command.put(7,1);
		this.sensor2command.put(11,4);
		this.sensor2command.put(12,3);
		this.sensor2command.put(16,2);
		this.sensor2command.put(0,0);
	}

	public HashMap<Integer,Supplier<Integer>> getPriority(){
		return this.priority;
	}

	public HashMap<Integer,Integer> getSensor2Command(){
		return this.sensor2command;
	}

	public int getPriorityValueOf(int parameter){
		if(parameter <= -4){
			return parameter;
		}else{
			return this.getPriority().get(parameter).get();
		}
	}

	public boolean isInPriority(int parameter){
		return parameter <= -4 || this.getPriority().get(parameter) != null;
	}

	public boolean isHigherPriority(int candidate, int reference){
		Integer candidatepriorityvalue = this.getPriorityValueOf(candidate);
		Integer referencepriorityvalue = this.getPriorityValueOf(reference);

		if(candidatepriorityvalue == null){
			return false;
		} else if(referencepriorityvalue == null || candidatepriorityvalue > referencepriorityvalue){
			return true;
		} else{
			return false;
		}
	}

	/*public boolean isInPriority(int parameter){
		for(int arrvalue : this.priority){
			if(arrvalue == parameter){
				return true;
			}
		}
		return false;
	}

	public boolean isHigherPriority(int candidate, int reference){
		int candidatepriorityvalue = null;
		int referencepriorityvalue = null;
		for(int i = 0; i < this.priority.length; i++){
			if(candidate == this.priority[i]){
				candidatepriorityvalue = i;
			}

			if(reference == this.prioriy[i]){
				referencepriorityvalue = i;
			}
		}

		if(candidatepriorityvalue == null){
			return false;
		} else if(referencepriorityvalue == null || candidatepriorityvalue > referencepriorityvalue){
			return true;
		}
	}*/

	public int fromSensor2Command (int parameter){
		return this.getSensor2Command().get(parameter);
	}

	public int getVisionPosition(int index){
                int vision = this.sensor.getVisaoIdentificacao()[index];
                if(vision == 0){
			int px = (int) sensor.getPosicao().getX();
			int py = (int) sensor.getPosicao().getY();
		        switch(index){
		                case 7:
		                        if(map[px][py-1] <= -4){
		                                return map[px][py-1];
					}
					break;
		                case 11:
		                        if(map[px-1][py] <= -4){
		                                return map[px-1][py];
					}
					break;
		                case 12:
		                        if(map[px+1][py] <= -4){
		                                return map[px+1][py];
					}
					break;
		                case 16:
		                        if(map[px][py+1] <= -4){
		                                return map[px][py+1];
		                        }
					break;
		        }
	        }
                return vision;
	}

	public void markCurrentPosition(){
		int px = (int) sensor.getPosicao().getX();
		int py = (int) sensor.getPosicao().getY();
		if(map[px][py] > -4){
			map[px][py] = -4;
		}else{
			map[px][py] = map[px][py] -1;
		}

	}

	public int acao() {
		if(flag){
			graphInit();
			int px = (int) sensor.getPosicao().getX();
			int py = (int) sensor.getPosicao().getY();
			System.out.println("x and y :"+px + " " + py);
			commandList = toBanckCommandList(8,8,px,py);
			flag = false;
		}
		if(!commandList.isEmpty()){
			return commandList.remove(0);
		}else{
			return 0;
		}
		/*this.markCurrentPosition();
		LinkedList<Integer> posib = new LinkedList<>();
		Integer mapnumber = null; // stores the mapnumber with the values in posib are ponting to
		int[] indexarray = {7,11,12,16};
		int rand;
		int command;
		int vision;
		for(int i : indexarray){
			vision = getVisionPosition(i);
			if(isInPriority(vision)){ // is vision[i] part of the relevant assets ?
				if(mapnumber == null || vision == mapnumber){ //is vision[i]  pointing to the same mapnumber as the posib is urrent pointin ?
					posib.add(i);
					mapnumber = vision;
				}else if(isHigherPriority(vision,mapnumber)){ //is vision[i] a mapnumber with more priority than the current?
					posib.clear();
					posib.add(i);
					mapnumber = vision;
				}
			}
		}
		//just for debyg
		System.out.print(mapnumber +"; ");
		for(int a : posib){
			System.out.print(a +" ");
		}
		System.out.println("team work");

		if(posib.size() > 0){
			rand = ((int) (Math.random() * 10)) %  posib.size();
			command = this.fromSensor2Command((posib.get(rand)));

		}else{
			command = 0;
		}
		return command;*/
	}

	public Node BFS(Node start, Node goal){
   Node currentNode;
   List<Node> queue = new ArrayList<Node>();
   start.serFather(start); // set as explored means to set a father, the start node is the only to have himself as his own father.
   queue.add(start);
   while (!queue.isEmpty()){
     currentNode = queue.remove(0);
     if (currentNode == goal){
       return currentNode;
     }else{
       for(Node neighbour : currentNode.getNeighbour()){
         if(neighbour.getFather() == null){
           neighbour.serFather(currentNode);
           queue.add(neighbour);
         }
       }
     }
   }
   return null;
 }

	public List<Integer> toBanckCommandList(int banck_x, int banck_y, int start_x, int start_y){
    Node bankNode = nodeMatrix[banck_y][banck_x];
    Node startNode = nodeMatrix[start_y][start_x];
    Node node = BFS(bankNode, startNode);
    Node father;
    int x;
		int y;
    List<Integer> commandList = new ArrayList<Integer>();
    while(node != node.getFather()){
      father = node.getFather();
      x = node.getXposition() - father.getXposition();
      y = node.getYposition() - father.getYposition();
      if(x != 0){
        if(x == 1){
          commandList.add(4); //Left
        }else{
          commandList.add(3); //Right
        }
      }
      if(y != 0){
        if(y == 1){
          commandList.add(1); //Up
        }else{
          commandList.add(2); //Down
        }
      }
      node = father;
    }
    return commandList;
  }

	public void graphInit(){
		try {
   File myObj = new File("Labirinto.txt");
   Scanner reader = new Scanner(myObj);
   for(int i = 0; reader.hasNextLine(); i++){
    String lineData = reader.nextLine();
    String[] charData = lineData.split(" ");
    //runArr(charData);
    for(int j = 0; j < 30; j++){
     nodeMatrix[i][j] = new Node(charData[j] + " " + j + "," + i);
    }
    //System.out.println(data);
   }
   reader.close();
   } catch (FileNotFoundException e){
    System.out.println("An error acurred");
    e.printStackTrace();
   }

   for(int i = 0; i < 30; i++){
    for(int j = 0; j < 30; j++){
     Node node = nodeMatrix[i][j];
     List<Edge> edges = new ArrayList<Edge>();
     //System.out.println("is " + node.getLabel() + " == to 1 or 5 ?... " + (node.getLabel().equals("1") || node.getLabel().equals("5"))  );
     if(!node.getRepresentativeNumber().equals("1") && !node.getRepresentativeNumber().equals("5")){
      if(i - 1 >= 0){
       Node nodeDestiny = nodeMatrix[i-1][j];
       if(!nodeDestiny.getRepresentativeNumber().equals("1") && !nodeDestiny.getRepresentativeNumber().equals("5")){
        edges.add(new Edge(node, nodeDestiny));
       }
      }
      if(j + 1 < 30){
       Node nodeDestiny = nodeMatrix[i][j+1];
       if(!nodeDestiny.getRepresentativeNumber().equals("1") && !nodeDestiny.getRepresentativeNumber().equals("5")){
        edges.add(new Edge(node, nodeDestiny));
       }
      }
      if(i + 1 < 30){
       Node nodeDestiny = nodeMatrix[i+1][j];
       if(!nodeDestiny.getRepresentativeNumber().equals("1") && !nodeDestiny.getRepresentativeNumber().equals("5")){
        edges.add(new Edge(node, nodeDestiny));
       }
      }
      if(j - 1 >= 0){
       Node nodeDestiny = nodeMatrix[i][j-1];
       if(!nodeDestiny.getRepresentativeNumber().equals("1") && !nodeDestiny.getRepresentativeNumber().equals("5")){
        edges.add(new Edge(node, nodeDestiny));
       }
      }
     }
     node.setEdges(edges);
    }
   }

  /*for(int i = 0; i < 30; i++){
   for(int j = 0; j < 30; j++){
    System.out.print(nodeMatrix[i][j].getRepresentativeNumber() +":"+ nodeMatrix[i][j].getEdges().size() + " ");
   }
   System.out.println("");
 }*/

  //toBanckCommandArr(8,8,26,7);
	}

}
