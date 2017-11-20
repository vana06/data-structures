package seminar1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
                        String[] str = list.getArray(parenIndex, i);
                        String result = calculate(str);
                        for(int j = parenIndex; j <= i; j++){
                            list.remove(parenIndex);
                        }
                        list.insert(result, parenIndex);

                        i = -1;
                        parenIndex = 0;
                        continue;
                    }
                }
                if(list.get(i).equals(String.valueOf(LEFT_PAREN)))
                    parenIndex = i + 1;
            }
            if(list.size() == 1)
                return Double.parseDouble(list.get(0));
            if(parenIndex == 0)
                break;
        }
        return Double.parseDouble(calculateComplex(list.getArray(-1, list.size())));
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

    private static String calculate(String[] str){
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
    private static String calculateComplex(String[] str){
        LinkedList<String> list = new LinkedList<>();
        for (String value: str) {
            list.push(value);
        }
        for(int i = 0; i < list.size(); i++){
            String operation = list.get(i);
            if(operation.charAt(0) == TIMES){
                Double a = Double.parseDouble(list.get(i-1));
                Double b = Double.parseDouble(list.get(i+1));
                Double num = a * b;
                list.remove(i+1);
                list.remove(i);
                list.remove(i - 1);
                list.insert(String.valueOf(num), i-1);
                i = -1;
            }
            if(operation.charAt(0) == DIVISION){
                Double a = Double.parseDouble(list.get(i-1));
                Double b = Double.parseDouble(list.get(i+1));
                Double num = a / b;
                list.remove(i+1);
                list.remove(i);
                list.remove(i - 1);
                list.insert(String.valueOf(num), i-1);
                i = -1;
            }
        }
        while (list.size() != 1) {
            for (int i = 0; i < list.size(); i++) {
                String operation = list.get(i);
                if (operation.charAt(0) == PLUS) {
                    Double a = Double.parseDouble(list.get(i - 1));
                    Double b = Double.parseDouble(list.get(i + 1));
                    Double num = a + b;
                    list.remove(i + 1);
                    list.remove(i);
                    list.remove(i - 1);
                    list.insert(String.valueOf(num), i - 1);
                }
                if (operation.charAt(0) == MINUS) {
                    Double a = Double.parseDouble(list.get(i - 1));
                    Double b = Double.parseDouble(list.get(i + 1));
                    Double num = a - b;
                    list.remove(i + 1);
                    list.remove(i);
                    list.remove(i - 1);
                    list.insert(String.valueOf(num), i - 1);
                }
            }
        }
        return list.get(0);
    }
}