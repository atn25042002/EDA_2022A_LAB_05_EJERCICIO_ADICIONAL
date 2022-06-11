import java.util.Stack;
public class Posfijo {
    public static void main(String[] args) {
        String str= "A+(B*C+D)/E";
        System.out.println(toSufijo(str));
    }

    public static String toSufijo(String str){
        Stack<Character> operadores= new Stack<Character>();
        Stack<Character> operandos= new Stack<Character>();
        Character c;
        for(int i= 0; i< str.length(); i++){
            c= str.charAt(i); // Se extra el caracter
            if(Character.isDigit(c) || Character.isLetter(c)){ //Si es digito o letra se apila a operandos
                operandos.push(c);
            }
            else if(String.valueOf(c).equals(")")){ // Si es cierre de parentesis se ejecuta un metodo
                if(!parentesis(operadores, operandos)) //Si se pudo encontrar el parentesis de abertura
                    return "Parentesis desequilibrados"; //Si es que no, indica desequilibrio
            }
            else{
                if(operadores.isEmpty() || String.valueOf(c).equals("(")){ //Si la pila de operadores esta vacia
                    operadores.push(c);                                             //O si es ( lo apila directamente
                }                
                else if(Operador.getPrioridad(c)==-1) { //Si se ingresa un operador no registrado
                    return "No es infijo";
                }
                else{
                    acomodar(operadores, operandos, c); //Si es un operador registrado
                }
            }
        }
        pasar(operadores, operandos); //Finalmente, apila los elementos de operadores a operandos
        return toText(operandos); //Devuelve operandos en forma de texto
    }

    public static void acomodar(Stack<Character> a, Stack<Character> b, Character c){
        Character aux= a.lastElement();
        if(Operador.getPrioridad(c)< Operador.getPrioridad(aux)){ //Si es menos prioritario
            a.push(c);
        }
        else if(Operador.getPrioridad(c)> Operador.getPrioridad(aux)){ //Si es mas prioritario
            menorPrioridad(a, b, c);
        }
        else{ //Tienen igual prioridad
            if(Operador.isAsoc_LTR(c)){ //Si tiene una asociatividad de izquierda a derecha
                menorPrioridad(a, b, c); //Ejecuta otro metodo
            }
            else{
                a.push(c); // Si tiene asociatividad de derecha a izquierda
            }
        }
    }

    public static void menorPrioridad(Stack<Character> a, Stack<Character> b, Character c){
        Character aux= a.lastElement();
        while(Operador.getPrioridad(aux)<= Operador.getPrioridad(c) && !String.valueOf(aux).equals("(")){
            b.push(a.pop());//Mientras no se encuentre uno menos prioritario o una abertura de parentesis
            if(a.isEmpty()){//Si llega a vaciarse
                a.push(c); //Se apila el operador
                return;
            }
            aux= a.lastElement(); //Pasa al siguiente elemento
        }
        a.push(c); //Finalmente apila el operador
    }

    public static boolean parentesis(Stack<Character> a, Stack<Character> b){
        while(!String.valueOf(a.lastElement()).equals("(")){
            b.push(a.pop()); //Mientras no se encuentre una abertura de parentesis
            if(a.isEmpty()){ //Si llega a vaciarse es porque no estan equilibrados
                return false;
            }
        }
        a.pop(); //Finalmente retira la abertura de parentesis
        return true;
    }

    public static void pasar(Stack<Character> a, Stack<Character> b){
        while(a.size()> 0){ //Apila los elementos de 'a' a 'b'
            b.push(a.pop());
        }
    }

    public static String toText(Stack<Character> stack){
        String str= ""; //Convierte una pila a texto corrido
        for(Character c: stack){
            str+= String.valueOf(c);
        }
        return str;
    }
}
