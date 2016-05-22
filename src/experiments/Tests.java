package experiments;

import structures.Node;

import java.util.Random;

/**
 * Class Tests
 *
 * @author Michel Llorens <mllorens@dcc.uchile.cl>
 * @version 1.0, 21-05-2016
 */
public class Tests {

    private static final int[] sigmas = {2, (int) Math.pow(2, 2), (int) Math.pow(2, 3),
            (int) Math.pow(2, 4), (int) Math.pow(2, 5), (int) Math.pow(2, 6),
            (int) Math.pow(2, 7), (int) Math.pow(2, 8)};

    private static final int symbolsLength = (int) Math.pow(2, 20);
    private static final int iterations = 10 * symbolsLength;

    public void doTest(){
        for(int sigmaSize: sigmas){
            System.out.println("Sigma with size "+sigmaSize);

            char[] sigma = makeSigma(sigmaSize);
            String S = makeSymbol(sigma);
            Node waveletTree = new Node(S, sigma);

            Random random = new Random(42);

            rankTest(waveletTree, sigma, random);
            rangeTest(waveletTree, sigma, random);
            System.out.println();
        }
    }

    private void rankTest(Node node, char[] sigma, Random random){

        System.out.println("Rank Test with sigma length "+sigma.length);

        long time = 0;

        long start, end;

        for(int k = 0; k < iterations; k++) {
            char c = sigma[random.nextInt(sigma.length)];
            int deep = random.nextInt(symbolsLength) + 1;

            start = System.nanoTime();
            node.rank(c, deep);
            end = System.nanoTime();
            time += (end - start);
        }
        long average = time / iterations;
        System.out.println("Average (nanoseconds) : "+ average);
    }

    private void rangeTest(Node node, char[] sigma, Random random){

        System.out.println("Range Test with sigma length "+sigma.length);

        int sigmaLength = sigma.length;

        long time = 0;

        long start, end;

        int x, y, i, j, tmp;

        for(int k = 0; k < iterations; k++) {
            x = random.nextInt(sigmaLength) + 1;
            y = random.nextInt(sigmaLength) + 1;
            if(x > y){
                tmp = x;
                x = y;
                y = tmp;
            }

            i = random.nextInt(symbolsLength) + 1;
            j = random.nextInt(symbolsLength) + 1;
            if(i > j){
                tmp = i;
                i = j;
                j = tmp;
            }

            start = System.nanoTime();
            node.range(x, y, i, j);
            end = System.nanoTime();
            time += (end - start);
        }
        long average = time / iterations;
        System.out.println("Average (nanoseconds) : "+ average);
    }

    private char[] makeSigma(int length){
        char[] sigma = new char[length];
        for(int i = 0; i < length; i++){
            sigma[i] = (char) (i+1) ;
        }
        return sigma;
    }

    private String makeSymbol(char[] sigma){
        StringBuilder symbol = new StringBuilder();
        for(int i = 0; i < symbolsLength; i++){
            int s = i % sigma.length;
            symbol.append(sigma[s]);
        }
        return symbol.toString();
    }
}
