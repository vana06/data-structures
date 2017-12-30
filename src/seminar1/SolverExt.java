package seminar1;

import seminar1.collections.LinkedStack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

/**
 * ( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) ) = 101
 * ( 1 + ( 5 * ( 4 * 5 ) ) )
 * ( 1 + ( 5 * 20 ) ) = 101
 * ( 1 + 100 ) = 101
 *
 * 1 + ( 2 + 3 ) * 4 * 5 = 101
 * 1 + 5 * 4 * 5 = 101
 * 1 + 5 * 20 = 101
 * 1 + 100 = 101
 * 20 / 4 = 5
 * ( 101 - 1 ) / 5 = 20
 *
 * Считаем, что операции деления на ноль отсутствуют
 */
public class SolverExt {

    private static final String QUIT = "q";

    private static final char LEFT_PAREN   = '(';
    private static final char RIGHT_PAREN  = ')';
    private static final char PLUS         = '+';
    private static final char MINUS        = '-';
    private static final char TIMES        = '*';
    private static final char DIVISION     = '/';

    private static double evaluate(String[] values) {
        return parse(values);
    }

    public static void main(String[] args) {
        try (BufferedReader lineReader = new BufferedReader(new InputStreamReader(System.in))) {
            String sequence;
            while (!QUIT.equals(sequence = lineReader.readLine())) {
                System.out.println(evaluate(sequence.split(" ")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static double parse(String[] values){
        LinkedStack<Double> numbers = new LinkedStack<>();
        LinkedStack<String> operations = new LinkedStack<>();

        if(values.length == 1)
            return Double.parseDouble(values[0]);

        int parenCount = 0, expBegin = 0, expEnd = values.length;
        boolean insideParen = false;
        for (int i = 0; i < values.length; i++) {
            String temp = values[i];
            if (temp.equals(Character.toString(LEFT_PAREN))) {
                parenCount++;
                if(!insideParen) {
                    insideParen = true;
                    expBegin = i;
                }
            } else if (temp.equals(Character.toString(RIGHT_PAREN))) {
                parenCount--;
                expEnd = i;
            } else if(!insideParen){
                if(isOperation(temp)){
                    operations.push(temp);
                } else{
                    numbers.push(Double.parseDouble(temp));
                }
            }

            if (insideParen && parenCount == 0) {
                String[] expression = new String[expEnd - (expBegin +1)];
                System.arraycopy(values, expBegin+1, expression, 0, expression.length);
                numbers.push(parse(expression));
                insideParen = false;
            }
        }

        return calculate(numbers, operations);
    }

    private static double calculate(LinkedStack<Double> numbers, LinkedStack<String> operations){
        LinkedStack<Double> tempNumbers = new LinkedStack<>();
        LinkedStack<String> tempOperations = new LinkedStack<>();
        Iterator<String> opIt = operations.iterator();
        while(opIt.hasNext()){
            String op = opIt.next();
            if(op.equals(Character.toString(PLUS)) || op.equals(Character.toString(MINUS))){
                tempNumbers.push(numbers.pop());
                tempOperations.push(op);
            } else {
                if(op.equals(Character.toString(TIMES))){
                    double a = numbers.pop();
                    double b = numbers.pop();
                    double res = a*b;
                    numbers.push(res);
                } else { //DIVIDE
                    double a = numbers.pop();
                    double b = numbers.pop();
                    double res = b/a;
                    numbers.push(res);
                }
            }
        }
        if(numbers.size()!=0){
            tempNumbers.push(numbers.pop());
        }


        if(tempNumbers.size() == 1) {
            return tempNumbers.pop();
        }
        opIt = tempOperations.iterator();
        double result = 0;
        while(opIt.hasNext()){
            String op = opIt.next();
            if(op.equals(Character.toString(PLUS))){
                double a= tempNumbers.pop();
                double b = tempNumbers.pop();
                result = a+b;
                tempNumbers.push(result);
            } else { //MINUS
                double a= tempNumbers.pop();
                double b = tempNumbers.pop();
                result = a-b;
                tempNumbers.push(result);
            }
        }
        return result;
    }

    private static boolean isOperation(String str){
        return str.equals(Character.toString(PLUS)) || str.equals(Character.toString(MINUS)) ||
                str.equals(Character.toString(TIMES)) || str.equals(Character.toString(DIVISION));
    }
}