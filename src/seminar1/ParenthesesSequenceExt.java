package seminar1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 1. пустая строка — правильная скобочная последовательность;
 * 2. правильная скобочная последовательность,
 *      взятая в скобки одного типа — правильная скобочная последовательность;
 * 3. правильная скобочная последовательность,
 *      к которой приписана слева или справа правильная скобочная последовательность
 *      — тоже правильная скобочная последовательность.
 */
public class ParenthesesSequenceExt {

    private static final String QUIT = "q";

    private static final char LEFT_PAREN     = '(';
    private static final char RIGHT_PAREN    = ')';
    private static final char LEFT_BRACE     = '{';
    private static final char RIGHT_BRACE    = '}';
    private static final char LEFT_BRACKET   = '[';
    private static final char RIGHT_BRACKET  = ']';

    // sequence = "()()" | "(({}[]))[[[" | "{}" | ...
    private static boolean isBalanced(String sequence) {
        int paren = 0, brace = 0, bracket = 0;
        char lastOpen = ' ';
        for(int i = 0; i < sequence.length(); i++){
            switch (sequence.charAt(i)){
            case LEFT_PAREN:{
                paren++;
                lastOpen = LEFT_PAREN;
                break;
            }
            case RIGHT_PAREN:{
                if(lastOpen != LEFT_PAREN)
                    return false;
                paren--;
                if(paren < 0)
                    return false;
                break;
            }
            case LEFT_BRACE:{
                brace++;
                lastOpen = LEFT_BRACE;
                break;
            }
            case RIGHT_BRACE:{
                if(lastOpen != LEFT_BRACE)
                    return false;
                brace--;
                if(brace < 0)
                    return false;
                break;
            }
            case LEFT_BRACKET:{
                bracket++;
                lastOpen = LEFT_BRACKET;
                break;
            }
            case RIGHT_BRACKET:{
                if(lastOpen != LEFT_BRACKET)
                    return false;
                bracket--;
                if(bracket < 0)
                    return false;
                break;
            }
            }
        }
        if(paren == 0 && brace == 0 && bracket == 0)
            return true;
        return false;
    }

    public static void main(String[] args) {
        try (BufferedReader lineReader = new BufferedReader(new InputStreamReader(System.in))) {
            String sequence;
            while (!QUIT.equals(sequence = lineReader.readLine())) {
                System.out.println(isBalanced(sequence) ? "YES" : "NO");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}