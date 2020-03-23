
package lenguaje_intermedio;
import java.util.Stack;
import java.util.Scanner;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Arbol {
    
    static int memoria=0;
    static int index=1;
    public Arbol(String valorexpresion) {

    		  if(String.valueOf(valorexpresion.charAt(valorexpresion.length()-1)).contains("+") || 
    		     String.valueOf(valorexpresion.charAt(valorexpresion.length()-1)).contains("-") ||
    		     String.valueOf(valorexpresion.charAt(valorexpresion.length()-1)).contains("*") ||
    		     String.valueOf(valorexpresion.charAt(valorexpresion.length()-1)).contains("/")){
    			  throw new NullPointerException("Error");
    		  }else {

    	      
    	      
    	      //Evaluar expresion
    	      ScriptEngineManager manager = new ScriptEngineManager();
    	        ScriptEngine engine = manager.getEngineByName("js");
    	        
    	        try {;
    	            Object result = engine.eval(valorexpresion);
    	            System.out.println(valorexpresion+" = "+result);
    	        } catch(ScriptException se) {
    	            se.printStackTrace();
    	        }
    		  }
	}
    
    public static void main(String[] args) {
            System.out.println("Ingrese la operacion: ");
            Scanner myObj = new Scanner(System.in);
            String inputString = myObj.nextLine(); 
        //String inputString="5*3 + 2*2";
        inputString=inputString.replaceAll(" ","");// quitar los empacios en blanco
        //System.out.println("Expresion ingresada: "+inputString);
        Shunting parentNode=shunt(inputString);
    
    
    }
    
    
    private static Shunting shunt(String inputString) {


        Pila myStack=new Pila(); //pila para variables
        Operador opr=new Operador(); //pila para operadores


        Stack<Character> operatorStack= new Stack(); //variables 
        Stack<Shunting> expressionStack=new Stack();//operador

        Character c;
        for (int i=0;i<inputString.length();i++){

            c=inputString.charAt(i); // devuelve un caracter dentro del String por medio del parametro = i

            if (c=='('){
                operatorStack.push(c);
            }

            else if (Character.isDigit(c) || Character.isLetter(c)){
                expressionStack.push(new Shunting(c));
            }

            else if (isOperator(c)){

                    while (opr.getOperatorPrecedence(myStack.getTopOfOperator(operatorStack)) >= opr.getOperatorPrecedence(c)) {
                        Character operator = operatorStack.pop();
                        Shunting e2 = expressionStack.pop();
                        Shunting e1 = expressionStack.pop();
                        
                        expressionStack.push(new Shunting(operator,e1,e2,"T"+index++));
                    }

                operatorStack.push(c);
            }

            else if (c==')'){

                    while (myStack.getTopOfOperator(operatorStack) != '(') {

                        Character operator = operatorStack.pop();
                        Shunting e2 = expressionStack.pop();
                        Shunting e1 = expressionStack.pop();

                        expressionStack.push(new Shunting(operator,e1,e2,"T"+index++));
                    }

                operatorStack.pop();
            }

            else{
                System.out.println("error error");
            }
        }

        while(!operatorStack.empty()){
            Character operator=operatorStack.pop();
            Shunting e2=expressionStack.pop();
            Shunting e1=expressionStack.pop();
            expressionStack.push(new Shunting(operator,e1,e2,"T"+index++));
        }


        return expressionStack.pop();
    }
    
    
    public static boolean isOperator(Character c){
        if (c=='+' || c=='-' || c=='/' || c=='*'|| c=='%'){
            return true;
        }
        else{
            return false;
        }
    }
    
    
}
