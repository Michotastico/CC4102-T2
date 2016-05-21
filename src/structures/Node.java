package structures;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Class Node
 *
 * @author Michel Llorens <mllorens@dcc.uchile.cl>
 * @version 1.0, 19-05-2016
 */
public class Node {

    private char[] dictionary;
    private HashMap<Character, Boolean> translate;
    private boolean[] bitmap;

    private Node left;
    private Node right;

    private boolean has_sons = false;

    public Node(String S, char[] sigma){
        int sLength = S.length();
        int sigmaLength = sigma.length;

        this.dictionary = sigma;
        translate = new HashMap<>();
        bitmap = new boolean[sLength];

        int middlePoint = (sigmaLength + 1) / 2;
        for(int i = 0; i < middlePoint; i++)
            translate.put(sigma[i], false);
        for(int i = middlePoint; i < sigmaLength; i++)
            translate.put(sigma[i], true);

        for(int i = 0; i < sLength; i++){
            bitmap[i] = translate.get(S.charAt(i));
        }

        if(sigmaLength != 1)
            this.setSons(S);
    }

    private void setSons(String symbols){
        this.has_sons = true;

        StringBuilder sLeft = new StringBuilder();
        StringBuilder sRight = new StringBuilder();

        for(int counter = 0; counter < this.bitmap.length; counter++){
            if(this.bitmap[counter])
                sRight.append(symbols.charAt(counter));
            else
                sLeft.append(symbols.charAt(counter));
        }

        int middlePoint = (this.dictionary.length + 1) / 2;

        char[] leftSigma = Arrays.copyOfRange(this.dictionary, 0, middlePoint);
        char[] rightSigma = Arrays.copyOfRange(this.dictionary, middlePoint, this.dictionary.length);

        this.left = new Node(sLeft.toString(), leftSigma);
        this.right = new Node(sRight.toString(), rightSigma);

    }

    public int rank(char value, int position){
        boolean flag = this.translate.get(value);
        int counter = 0;

        for(int i = 0; i < position; i++)
            if(this.bitmap[i])
                counter++;

        if(!flag)
            counter = position - counter;

        if(!this.has_sons)
            return counter;

        if(flag)
            return this.right.rank(value, counter);
        else
            return this.left.rank(value, counter);

    }
}
