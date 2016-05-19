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
    }
}
