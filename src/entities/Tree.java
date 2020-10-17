package entities;
import java.util.*;

public class Tree<T extends Comparable<T>> {

    public Node<T> root = null;

    public Tree(Node<T> root) {
        this.root = root;
    }

    public Tree() {}

    public boolean search(T x) {
        Node<T> y = root;

        while(y != null && y.value.compareTo(x) != 0) {
            if (y.value.compareTo(x) > 0) {
                y = y.left;
            } else {
                y = y.right;
            }
        }

        return y != null;
    }

    public void insert(T x) throws Exception{

        Node<T> t = root;

        if (t == null) {
            root = new Node<T>(x, Color.BLACK);
            return;
        }

        while(x.compareTo(t.value) != 0) {
            if (x.compareTo(t.value) > 0) {

                if (t.right != null) {
                    t = t.right;
                } else {
                    break;
                }

            } else {

                if (t.left != null) {
                    t = t.left;
                } else {
                    break;
                }

            }
        }

        if (x.compareTo(t.value) == 0) {
            return;
        }

        else if (x.compareTo(t.value) > 0) {

            Node<T> newR = new Node<T>(x, Color.RED);
            newR.parent = t;
            t.right = newR;
            safeFix(newR);

        }

        else {

            Node<T> newL = new Node<T>(x, Color.RED);
            newL.parent = t;
            t.left = newL;
            safeFix(newL);

        }

    }

    public void delete(T x) {

    }

//    public T successor(T x) {
//
//    }
//
//    public T predecessor(T x) {
//
//    }
//
//    public T max() {
//
//    }
//
//    public T min() {
//
//    }

    public void print() {
       Queue<Node<T>> vertexQueue = new LinkedList<Node<T>>();

       vertexQueue.add(root);

       while (!vertexQueue.isEmpty()) {
           Node<T> current = vertexQueue.remove();

           if (current != null) {
               System.out.println(current.value + " , " + current.color);
               vertexQueue.add(current.left);
               vertexQueue.add(current.right);
           } else {
               System.out.println("NIL" + " , BLACK");
           }
        }
    }


    /** Restore red-black tree properties, assuming
     *  they are violated because violate and violate.parent
     *  are both red
     *
     *  This method performs error checking to see if it's called
     *  with the right configuration although this is perhaps not
     *  necessary given that the method is private
     */
    private void safeFix(Node<T> violate) throws Exception {

        if (violate == null) {
            return;
        }

        while(violate.parent != null && violate.color == Color.RED && violate.parent.color == Color.RED) {
            Node<T> parent = violate.parent;

            Node<T> grandparent = parent.parent;

            // cannot have root painted red by consistency assumption
            if (grandparent == null) {
                throw new Exception("Error: Root painted red at some point");
            }

            Node<T> uncle;

            if (grandparent.left == parent) {
                uncle = grandparent.right;
            } else {
                uncle = grandparent.left;
            }

            /**
             * The if is case 1 from the Lecture Notes, i.e. uncle red
             */
             if (uncle != null && uncle.color == Color.RED) {

                 uncle.color = Color.BLACK;
                 parent.color = Color.BLACK;
                 grandparent.color = Color.RED;
                 violate = grandparent;

             } else {

                 /**
                  * Case 2 from the Lecture Notes, i.e. violating, parent,
                  * and grandparent are not aligned - we turn it into case 3
                  */

                 if (grandparent.left == parent && parent.right == violate) {

                     leftRotate(parent);
                     violate = parent;

                 }

                 else if (grandparent.right == parent && parent.left == violate) {

                     System.out.println("We're in this symmetric case");

                     rightRotate(parent);
                     violate = parent;

                 }

                 /**
                  * Case 3 from Lecture Notes, i.e. violating, parent,
                  * and grandparent are aligned - we fix it in O(1)
                  */

                 else if (grandparent.left == parent && parent.left == violate) {

                     rightRotate(grandparent);
                     grandparent.color = Color.RED;
                     parent.color = Color.BLACK;

                 }

                 // (grandparent.right == parent && parent.right == violate)
                 else {

                     System.out.println("We're in this symmetric case");

                     leftRotate(grandparent);
                     grandparent.color = Color.RED;
                     parent.color = Color.BLACK;

                 }

             }

        } // end of while

        if (violate == root) {
            violate.color = Color.BLACK;
        }
    }

    private void leftRotate(Node<T> x) {

        if (x == null) {
            return;
        }

        Node<T> y = x.right;
        if (y == null) {
            return;
        }

        if (y.left != null) {
            y.left.parent = x;
        }
        x.right = y.left;

        Node<T> par = x.parent;
        y.parent = par;

        if (par == null) {
            root = y;
        }

        if (par != null && par.left == x) {
            par.left = y;
        }

        else if (par != null && par.right == x) {
            par.right = y;
        }

        y.left = x;
        x.parent = y;
    }

    private void rightRotate(Node<T> y) {

        if (y == null) {
            return;
        }

        Node<T> x = y.left;
        if (x == null) {
            return;
        }

        if (x.right != null) {
            x.right.parent = y;
        }
        y.left = x.right;

        Node<T> par = y.parent;
        x.parent = par;

        if (par == null) {
            root = x;
        }

        if (par != null && par.left == y) {
            par.left = x;
        }

        else if (par != null && par.right == y) {
            par.right = x;
        }

        x.right = y;
        y.parent = x;
    }

}
