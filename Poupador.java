package algoritmo;
import java.util.*;
public class Poupador extends ProgramaPoupador {
	//private int[] priority = {0,4};
	private int[][] map = new int[30][30];
	private HashMap<Integer,Integer> priority = new HashMap<Integer,Integer>();
	private HashMap<Integer,Integer> sensor2command = new HashMap<Integer,Integer>();
	
	public Poupador(){
		this.priority.put(0,1);
		this.priority.put(4,2);
		this.sensor2command.put(7,1);
		this.sensor2command.put(11,4);
		this.sensor2command.put(12,3);
		this.sensor2command.put(16,2);
		this.sensor2command.put(0,0);
	}
	
	public HashMap<Integer,Integer> getPriority(){
		return this.priority;
	}
	
	public HashMap<Integer,Integer> getSensor2Command(){
		return this.sensor2command;
	}
	
	public int getPriorityValueOf(int parameter){
		if(parameter > -4){
			return this.getPriority().get(parameter);
		}else{
			return parameter;
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
                int px = (int) sensor.getPosicao().getX();
                int py = (int) sensor.getPosicao().getY();
                if(vision == 0){
		        switch(index){
		                case 7:
		                        if(map[px][py-1] <= -4){
		                                return map[px][py-1];
		                        }else{
		                                return vision;
		                        }
		                case 11:
		                        if(map[px-1][py] <= -4){
		                                return map[px-1][py];
		                        }else{
		                                return vision;
		                        }
		                case 12:
		                        if(map[px+1][py] <= -4){
		                                return map[px+1][py];
		                        }else{
		                                return vision;
		                        }
		                case 16:
		                        if(map[px][py+1] <= -4){
		                                return map[px][py+1];
		                        }else{
		                                return vision;
		                        }
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
		this.markCurrentPosition();
		LinkedList<Integer> posib = new LinkedList<>();
		Integer mapnumber = null; // stores the mapnumber with the values in posib are ponting to
		int[] indexarray = {7,11,12,16};
		int rand;
		int command;
		int vision;
		for(int i : indexarray){
			vision = getVisionPosition(i);
			if(isInPriority(vision)){ // is vision[i] part of the relevant assets ?
				if(mapnumber == null || vision == mapnumber){ //is vision[i] the pointing to the same mapnumber as the posib is urrent pointin ?
					posib.add(i);
					mapnumber = vision;
				}else if(isHigherPriority(vision,mapnumber)){ //is vision[i] a mapnumber with more priority than the current?
					posib.clear();
					posib.add(i);
					mapnumber = vision;
				}
			}
		}
		System.out.print(mapnumber +"; ");
		for(int a : posib){
			System.out.print(a +" ");
		}
		System.out.println(" ");
		if(posib.size() > 0){
			rand = ((int) (Math.random() * 10)) %  posib.size();
			//System.out.println(posib.size() + " " + rand);
			command = this.fromSensor2Command((posib.get(rand)));
		
		}else{
			command = 0;
		}		
		return command;
	}

}
