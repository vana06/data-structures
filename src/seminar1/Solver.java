package seminar1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * ( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) ) = 101
 * ( 1 + ( 5 * ( 4 * 5 ) ) ) = ( 1 + ( 5 * 20 ) ) = 101
 * ( 1 + 100 ) = 101
 *
 * Считаем, что операции деления на ноль отсутствуют
 */

public class Solver {

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
        if(values.length == 1)
            return Double.parseDouble(values[0]);

        int parenCount = 0;
        for (int i = 1; i < values.length - 1; i++) {
            String temp = values[i];
            if (temp.equals(Character.toString(LEFT_PAREN))) {
                parenCount++;
            } else if (temp.equals(Character.toString(RIGHT_PAREN))) {
                parenCount--;
            } else if (parenCount == 0 && isOperation(temp)) {
                String[] left = new String[i - 1];
                System.arraycopy(values, 1, left, 0, left.length);
                String[] right = new String[values.length - (i+2)];
                System.arraycopy(values, i + 1, right, 0, right.length);

                return calculate(parse(left), parse(right), temp);
            }
        }

        return 0; //никогда не дойдет до этой точки при условии что выражение востроено правильно
    }

    private static double calculate(double a, double b, String op){
        switch (op.charAt(0)){
        case PLUS:
            return (a+b);
        case MINUS:
            return (a-b);
        case TIMES:
            return (a*b);
        case DIVISION:
            return (a/b);
        }
        return 0; //никогда не дойдет до этой точки при условии что выражение построено правильно
    }

    private static boolean isOperation(String str){
        return str.equals(Character.toString(PLUS)) || str.equals(Character.toString(MINUS)) ||
                str.equals(Character.toString(TIMES)) || str.equals(Character.toString(DIVISION));
    }
}
