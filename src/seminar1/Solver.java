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
        LinkedList<String> list = new LinkedList<>();
        for (String str: values) {
            list.push(str);
        }


        while (true) {
            int parenIndex = 0;
            for (int i = 0; i < list.size(); i++) {
                if(parenIndex != 0){
                    if(list.get(i).equals(String.valueOf(RIGHT_PAREN))){
                        //считай
                        parenIndex--;
                        String[] str = (String[])list.getArray(parenIndex, i);
                        String result = calculate(str);
                        for(int j = parenIndex; j <= i; j++){
                            list.remove(parenIndex);
                        }
                        list.insert(result, parenIndex);

                        i -= parenIndex;
                        parenIndex = 0;
                        continue;
                    }
                }
                if(list.get(i).equals(String.valueOf(LEFT_PAREN)))
                    parenIndex = i + 1;
            }
            if(list.size() == 1)
                break;
        }
        return Double.parseDouble(list.get(0));
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

    private static String calculate(String str[]){
        String result = "";
        double a = Double.parseDouble(str[0]);
        double b = Double.parseDouble(str[2]);
        switch (str[1].charAt(0)){
        case PLUS:
            result = String.valueOf(a+b);
            break;
        case MINUS:
            result = String.valueOf(a-b);
            break;
        case TIMES:
            result = String.valueOf(a*b);
            break;
        case DIVISION:
            result = String.valueOf(a/b);
            break;
        }
        return result;
    }

}
