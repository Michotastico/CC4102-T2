package main;

import structures.Node;

/**
 * Class Main
 *
 * @author Michel Llorens <mllorens@dcc.uchile.cl>
 * @version 1.0, 19-05-2016
 */
public class Main {

    public static void main(String[] args){
        String S = "alabar a la alabarda";
        char[] sigma = {' ', 'a', 'b', 'd', 'l', 'r'};
        Node node = new Node(S, sigma);
        System.out.println("Test rank");
        int rank = node.rank(' ',12);
        System.out.println(rank);
        System.out.println("Test range");
        int range = node.range(1, 2, 2, 12);
        System.out.println(range);
        System.out.println("Test range intensive");
        testLeafs(node, 1, S.length());
    }

    private static void testLeafs(Node node, int start, int end){
        for(int i = 1; i <= 6; i++){
            int range = node.range(i, i, start, end);
            System.out.println(range);
        }
    }
}
