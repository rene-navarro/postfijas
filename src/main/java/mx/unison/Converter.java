package mx.unison;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

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
            System.out.printf("%s%n", inputString);
            List<String> tokens = getTokens(inputString);
            System.out.println(tokens.size());
            System.out.println(tokens);
        }
    }

    public static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") ||
                token.equals("*") || token.equals("/") || token.equals("/");

    }

    public static ArrayList<String> toPostfix(List<String> tokens) {

        Stack<String> stack = new Stack<>();
        ArrayList<String> output = new ArrayList<>();
        String t;

        for (String token : tokens) {
            if (token.equalsIgnoreCase("(")) {
                stack.push(token);
            } else if (token.equalsIgnoreCase(")")) {
                while (!(t = stack.pop()).equals("(")) {
                    output.add(t);
                }
            } else if (isOperand(token)) {
                output.add(token);
            }
            if (isOperator(token)) {
                if ( stack.isEmpty() ) {
                    stack.push(token);
                } else {
                    int r1 = Converter.getPrec(token);
                    int r2 = Converter.getPrec(stack.peek());

                    if( r1 > r2 ){
                        stack.push(token);
                    } else {
                        output.add(stack.pop());
                        stack.push(token);
                    }

                }
            }
        }
        return output;
    }

    public static boolean isOperand(String token) {
        boolean result = true;
        try {
            Double.parseDouble(token);
        } catch (NumberFormatException e) {
            result = false;
        }
        return result;
    }

    public static List<String> getTokens(String input) {
        String[] tokens = input.split("\\s+");

        ArrayList<String> tokenList = new ArrayList<>();
        for (String token : tokens) {
            tokenList.add(token.trim());
        }
        return tokenList;
    }

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
