import java.util.ArrayList;

public class Operador{
    private Character simbolo; //Hace referencia al simbolo del operador
    private int prioridad; //Hace referencia a la  prioridad, 0 es mas prioritario
    //Escala inversa, el que tiene menor numero es mas prioritario
    private Boolean asoc_LTR; //Hace referencia al sentido de asociatividad
    private static ArrayList<Operador> operadores= new ArrayList<Operador>(); //Contiene nuevos operadores

    public Operador(int p, Character c, Boolean a){
        if(p< 0)// La prioridad debe ser mayor a cero
            return;
        simbolo= c;
        prioridad= p;
        asoc_LTR= a;
        operadores.add(this);
    }

    public Operador(int p, Character c){
        this(p, c, true); // Se sume que tiene asociatividad de izquierda a derecha
    }

    public int getPrioridad(){ //Devuelve la prioridad del operador
        return prioridad;
    }

    public String toString(){ //Devuelve el simbolo del operador
        return String.valueOf(simbolo);
    }

    public Character getSimbolo(){//De vuelve el simbolo del operador pero en Character
        return simbolo;
    }

    public Boolean getAsocLTR(){//Indica si la asociativdad es de izquierda a derecha
        return asoc_LTR;
    }

    private static int indexOf(Character c){ //Busca el indice de un operador ya registrado
        for(int i= 0; i< operadores.size(); i++){
            if(operadores.get(i).getSimbolo().equals(c))
                return i;
        }
        return -1;
    }

    public static int getPrioridad(Character c){ //Devuelve la prioridad de un operador, tiene algunos ya registrados
        String t= String.valueOf(c);
        if(indexOf(c)>= 0)                      
            return operadores.get(indexOf(c)).getPrioridad();
        if(t.equals("+") || t.equals("-"))
            return 3;
        if(t.equals("*") || t.equals("/"))
            return 2;
        if(t.equals("^"))
            return 1;
        return -1; 
    }

    public static Boolean isAsoc_LTR(Character c){ //Indica la asocitividad de un operador
        String t= String.valueOf(c);
        if(indexOf(c)>= 0)
            return operadores.get(indexOf(c)).getAsocLTR();
        return ("+-*/^".indexOf(t)>= 0); //Operadores comunes
    }
}