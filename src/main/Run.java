package main;
import entities.*;

public class Run {

    public static void main(String[] args) throws Exception{
        Tree<Integer> testTree = new Tree<Integer>();

        testTree.insert(34);
        testTree.insert(21);
        testTree.insert(55);
        testTree.insert(40);
        testTree.insert(45);
        testTree.insert(37);

        testTree.print();

        System.out.println();
        System.out.println("-----------------------");
        System.out.println();





    }
}
