package structures;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Class WaveletTree
 *
 * @author Michel Llorens <mllorens@dcc.uchile.cl>
 * @version 1.0, 19-05-2016
 */
public class WaveletTree {

    private char[] dictionary;
    private HashMap<Character, Boolean> translate;
    private boolean[] bitmap;

    private WaveletTree left;
    private WaveletTree right;

    private boolean has_sons = false;

    public WaveletTree(String S, char[] sigma){
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

        this.left = new WaveletTree(sLeft.toString(), leftSigma);
        this.right = new WaveletTree(sRight.toString(), rightSigma);

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

    public int range(int x, int y, int i, int j){
        char[] rangeDictionary = Arrays.copyOfRange(this.dictionary, x - 1, y);
        return range(rangeDictionary, i - 1 , j);
    }

    private int range(char[] rangeDictionary, int i, int j){
        int counter = 0;

        for(char c: this.dictionary)
            if(arrayContains(rangeDictionary, c))
                counter++;

        if(counter == 0)
            return 0;

        else if(counter == this.dictionary.length){
            if(j < 0)
                return 0;
            if(i < 0)
                i = 0;
            return j - i;
        }
        else{
            int rightHead = 0;

            for(int k = 0; k < i; k++)
                if(this.bitmap[k])
                    rightHead++;

            int leftHead = i - rightHead;

            int rightTail = rightHead;

            for(int k = i; k < j; k++)
                if(this.bitmap[k])
                    rightTail++;

            int leftTail = j - rightTail;

            int iLeft = i - rightHead;
            int jLeft = j - rightTail;

            int iRight = i - leftHead;
            int jRight = j - leftTail;

            int left = this.left.range(rangeDictionary, iLeft, jLeft);
            int right = this.right.range(rangeDictionary, iRight, jRight);

            return left + right;
        }
    }

    private boolean arrayContains(char[] array, char value){
        int search = Arrays.binarySearch(array, value);
        return search >= 0;
    }
}
