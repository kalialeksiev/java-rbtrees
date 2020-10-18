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
            fixInsert(newR);

        }

        else {

            Node<T> newL = new Node<T>(x, Color.RED);
            newL.parent = t;
            t.left = newL;
            fixInsert(newL);

        }

    }

    public void delete(T x) throws Exception {

        Node<T> t = root;

        if (t == null) {
            return;
        }

        /**
         * search for x in tree
         */
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

        if (x.compareTo(t.value) != 0) {
            return;
        }

        /**
         * if deleting root, trivially delete
         */
        if (t.right == null && t.left == null) {
            Node<T> par = t.parent;

            if (par == null) {
                root = null;
                return;
            }

            if (par.right == t) {
                par.right = null;
            } else {
                par.left = null;
            }
            if(t.color == Color.BLACK) {
                fixDelete(null, par);
            }
        }

        else if (t.right == null) {

            Node<T> par = t.parent;

            if (par == null) {
                root = t.left;
                fixDelete(root, null);
                return;
            }

            if (par.right == t) {
                par.right = t.left;
            } else {
                par.left = t.left;
            }
            t.left.parent = par;
            if(t.color == Color.BLACK) {
                fixDelete(t.left, par);
            }
        }

        else {

            Node<T> succ = t.right;

            while(succ.left != null) {
                succ = succ.left;
            }

            t.value = succ.value;

            Node<T> par = succ.parent;

            if (par.left == succ) {
                par.left = succ.right;
            } else {
                par.right = succ.right;
            }

            if (succ.right != null) {
                succ.right.parent = par;
            }

            if (succ.color == Color.BLACK) {
                fixDelete(succ.right, par);
            }

        }

    }

    public T successor(T x) {

        Node<T> t = root;

        if (t == null) {
            return null;
        }

        /**
         * search for x in the tree
         */
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

        /**
         * if x is not present in the tree, return null
         */

        if (x.compareTo(t.value) != 0) {
            return null;
        }

        /**
         * case 1, where x has a right child
         */
        if (t.right != null) {
            Node<T> rightBranch = t.right;

            while (rightBranch.left != null) {
                rightBranch = rightBranch.left;
            }

            return rightBranch.value;
        }

        Node<T> parentX = t.parent;

        /**
         * if x happens to be the root with no right subtree, return null
         */
        if (parentX == null) {
            return null;
        }

        /**
         * case 2, where x is the left child of its parent
         */
        else if (parentX.left == t) {
            return parentX.value;
        }

        /**
         * case 3 if x is the right child of its parent go up
         * and look for the first right turn
         */
        else {
            while (parentX!= null && parentX.right == t) {
                t = parentX;
                parentX = t.parent;
            }

            /**
             * if x was the maximum, i.e. just right pointers
             * from the root, we output null, i.e. no successor
             */
            if (parentX == null) {
                return null;
            }

            else {
                return parentX.value;
            }
        }
    }

    public T predecessor(T x) {

        Node<T> t = root;

        if (t == null) {
            return null;
        }

        /**
         * search for x in the tree
         */
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

        /**
         * if x is not in the tree, return null
         */
        if (x.compareTo(t.value) != 0) {
            return null;
        }

        /**
         * case 1 where x has a left child
         */
        if (t.left != null) {
            Node<T> leftBranch = t.left;

            while(leftBranch.right != null) {
                leftBranch = leftBranch.right;
            }

            return leftBranch.value;
        }

        Node<T> parentX = t.parent;

        /**
         * if x is the root with no left subtree, return null
         */
        if (parentX == null) {
            return null;
        }

        /**
         * case 2 where x is the right child of its parent
         */
        else if (parentX.right == t) {
            return parentX.value;
        }

        /**
         * case 3 where x is the left child of its parent, so we
         * follow the parent pointers until we get the first left turn
         */
        else {

            while(parentX != null && parentX.left == t) {
                t = parentX;
                parentX = t.parent;
            }

            /**
             * x is the min element, i.e. we've reached the root
             * from x by following parent pointers with only left children,
             * so x doesn't have a predecessor
             */
            if (parentX == null) {
                return null;
            }

            else {
                return parentX.value;
            }
        }
    }

    public T max() {

        Node<T> t = root;

        if (t == null) {
            return null;
        }

        while(t.right != null) {
            t = t.right;
        }

        return t.value;
    }

    public T min() {

        Node<T> t = root;

        if (t == null) {
            return null;
        }

        while(t.left != null) {
            t = t.left;
        }

        return t.value;
    }

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
     *  they are violated after insertion
     *
     *  This method performs error checking to see if it's called
     *  with the right configuration although this is perhaps not
     *  necessary given that the method is private
     */
    private void fixInsert(Node<T> violate) throws Exception {

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

    /**
     * Restore red-black tree properties, assuming
     * they are violated after deletion
     */

    public void fixDelete(Node<T> violate, Node<T> parent) throws Exception {

        /**
         * 2 cases of whether the violating node is a null leaf or not
         */

        while( (violate != null && violate != root && violate.color == Color.BLACK)
                || (violate == null && parent != null)) {

            /**
             * this expeception means there's something wrong with the configuration of the tree
             */
            if (parent == null) {
                throw new Exception("Error: Wrong tree configuration, invariants broken.");
            }

            if (parent.left == violate) {
                Node<T> sibling = parent.right;

                /**
                 * if sibling is null, the invariants of the tree have been violated
                 */
                if (sibling == null) {
                    throw new Exception("Error: Wrong tree configuration, invariants broken.");
                }

                /**
                 * case 1 in Introduction to Algorithms book, i.e. sibling is red
                 */
                if (sibling.color == Color.RED) {
                    sibling.color = Color.BLACK;
                    parent.color = Color.RED;
                    leftRotate(parent);
                }

                /**
                 * case 2, i.e. sibling is black with both of its children black
                 */
                else if ( (sibling.right == null || sibling.right.color == Color.BLACK)
                        && (sibling.left == null || sibling.left.color == Color.BLACK) ) {
                    sibling.color = Color.RED;
                    violate = parent;
                    parent = violate.parent;
                }

                /**
                 * case 3, i.e. sibling is black, sibling's left child is black and
                 * its right child is black
                 */
                else if (sibling.right == null || sibling.right.color == Color.BLACK) {
                    sibling.color = Color.RED;
                    sibling.left.color = Color.BLACK;
                    rightRotate(sibling);
                }

                /**
                 * case 3, i.e. sibling is black and sibling's right child is red
                 */

                else {
                    sibling.color = parent.color;
                    sibling.right.color = Color.BLACK;
                    parent.color = Color.BLACK;
                    leftRotate(parent);
                    violate = root;
                    parent = null;
                }

            }

            /**
             * repeat as before with left and right interchanged
             */
            else {

                Node<T> sibling = parent.left;

                /**
                 * if sibling is null, the invariants of the tree have been violated
                 */
                if (sibling == null) {
                    throw new Exception("Error: Wrong tree configuration, invariants broken.");
                }

                /**
                 * case 1 in Introduction to Algorithms book, i.e. sibling is red
                 */
                if (sibling.color == Color.RED) {
                    sibling.color = Color.BLACK;
                    parent.color = Color.RED;
                    rightRotate(parent);
                }

                /**
                 * case 2, i.e. sibling is black with both of its children black
                 */
                else if ( (sibling.left == null || sibling.left.color == Color.BLACK)
                        && (sibling.right == null || sibling.right.color == Color.BLACK) ) {
                    sibling.color = Color.RED;
                    violate = parent;
                    parent = violate.parent;
                }

                /**
                 * case 3, i.e. sibling is black, sibling's right child is black and
                 * its left child is black
                 */
                else if (sibling.left == null || sibling.left.color == Color.BLACK) {
                    sibling.color = Color.RED;
                    sibling.right.color = Color.BLACK;
                    leftRotate(sibling);
                }

                /**
                 * case 3, i.e. sibling is black and sibling's left child is red
                 */

                else {
                    sibling.color = parent.color;
                    sibling.left.color = Color.BLACK;
                    parent.color = Color.BLACK;
                    rightRotate(parent);
                    violate = root;
                    parent = null;
                }

            }

        }

        if (violate == null) {
            return;
        }

        else {
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
