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
    private String symbols;
    private boolean[] bitmap;

    private Node left;
    private Node right;

    public Node(String S, char[] sigma){
        int sLength = S.length();
        int sigmaLength = sigma.length;

        this.dictionary = sigma;
        this.symbols = S;
        translate = new HashMap<>();
        bitmap = new boolean[sLength];

        int middlePoint = (sigmaLength + 1) / 2;
        for(int i = 0; i < middlePoint; i++)
            translate.put(sigma[i], false);
        for(int i = middlePoint; i < sigmaLength; i++)
            translate.put(sigma[i], true);

        for(int i = 0; i < sLength; i++){
            bitmap[i] = translate.get(this.symbols.charAt(i));
        }

        this.print();
        if(sigmaLength != 1)
            this.setSons();
    }

    private void setSons(){

        StringBuilder sLeft = new StringBuilder();
        StringBuilder sRight = new StringBuilder();

        for(int counter = 0; counter < this.bitmap.length; counter++){
            if(this.bitmap[counter])
                sRight.append(this.symbols.charAt(counter));
            else
                sLeft.append(this.symbols.charAt(counter));
        }

        int middlePoint = (this.dictionary.length + 1) / 2;

        char[] leftSigma = Arrays.copyOfRange(this.dictionary, 0, middlePoint);
        char[] rightSigma = Arrays.copyOfRange(this.dictionary, middlePoint, this.dictionary.length);

        this.left = new Node(sLeft.toString(), leftSigma);
        this.right = new Node(sRight.toString(), rightSigma);

    }

    public void print(){
        System.out.println("\nDictionary");
        for(int i = 0; i < this.dictionary.length; i++)
            System.out.print(this.dictionary[i]+",");

        System.out.println("\n\nSymbols");
        System.out.print(this.symbols);

        System.out.println("\n\nbitmap");
        for(int i = 0; i < this.bitmap.length; i++)
            System.out.print(this.bitmap[i]+",");
    }


}
