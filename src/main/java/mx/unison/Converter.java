package mx.unison;

import java.util.*;

public class Converter {
    public static void main(String[] args) {


        String inputString;

        Scanner keyb = new Scanner(System.in);

        while (true) {
            System.out.println("Introduce una expresiÃ³n en notacion infija");
            System.out.print("> ");
            inputString = keyb.nextLine();
            if (inputString.equals("quit") || inputString.equals("QUIT")) {
                break;
            }
            // System.out.printf("%s%n", inputString);
            List<String> tokens = getTokens(inputString);
            System.out.println(Converter.toString(tokens));

            List<String> postfix = Converter.toPostfix(tokens);
            System.out.println(Converter.toString(postfix));

        }
    }

    /* Regresa true si el token es un operador valido */
    public static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") ||
                token.equals("*") || token.equals("/") || token.equals("^");

    }

    /* Convertir una lista de tokens a su representación como
        cadena de caracteres
     */
    public static String toString(List<String> list) {
        StringBuilder output = new StringBuilder();

        for (String token: list) {
            output.append(token);
            output.append(" ");
        }
        return output.toString();
    }
    /* Convierte los tokens de una expresion infija, a una
        lista de tokens de una expresión postfija
     */
    public static ArrayList<String> toPostfix(List<String> input) {

        Stack<String> stack = new Stack<>();
        ArrayList<String> output = new ArrayList<>();
        String t;

        for (String token : input) {
            // maneja la presencia de parentesis en le expresion infija
            if (token.equalsIgnoreCase("(")) {
                stack.push(token);
            } else if (token.equalsIgnoreCase(")")) {
                // sacar operadores de la pila hasta encontrar parentesis izquierdo
                while (!(t = stack.pop()).equals("(")) {
                    output.add(t);
                }
            } else if (isOperand(token)) {
                // Si el token es un operando, pasarlo a la lista
                // de salida
                output.add(token);
            }
            if (isOperator(token)) {
                if ( stack.isEmpty() ) {
                    // token es un operador y pila vacia, meter operador
                    // en la pila
                    stack.push(token);
                } else {
                    int r1 = Converter.getPrec(token);
                    int r2 = Converter.getPrec( stack.peek() );

                    // token es un operador y pila no vacia, meter operador
                    // de mayor prioridad a la pila
                    if( r1 > r2 ){
                        stack.push(token);
                    } else {
                        output.add(stack.pop());
                        stack.push(token);
                    }
                }
            }

        }
        String token;
        // Sacar de la pila los operadores restantes
        while ( !stack.isEmpty() ) {
            token = stack.pop();
            output.add(token);
        }
        return output;
    }

    // Verifica si el token es un operando (número)
    public static boolean isOperand(String token) {
        boolean result = true;
        try {
            Double.parseDouble(token);
        } catch (NumberFormatException e) {
            result = false;
        }
        return result;
    }

    // Obtiene la lista de tokens a partir de una cadena
    // que contiene una expresión en notación infija
    public static List<String> getTokens(String input) {

        StringTokenizer st = new StringTokenizer(input," ()+-*/^",
                                            true);

        ArrayList<String> tokenList = new ArrayList<>();
       while (st.hasMoreTokens()) {
           String token = st.nextToken();
           if(token.trim().length() == 0 ) {
               continue;
           }
           tokenList.add(token);
        }
        return tokenList;
    }

    // Obtener la prioridad de cada operador
    public static int getPrec(String token) {
        String t = token.toLowerCase();
        int rank = 0;

        switch (t) {
            case "^":
                rank = 3;
                break;
            case "*":
            case "/":
                rank = 2;
                break;
            case "+":
            case "-":
                rank = 1;
                break;
        }
        return rank;
    }
}
