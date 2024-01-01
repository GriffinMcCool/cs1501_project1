// author Griffin McCool
package cs1501_p1;

public class BST<T extends Comparable<T>> implements BST_Inter<T>{
    private BTNode root;

    /**
    * Add a new key to the BST
    *
    * @param 	key Generic type value to be added to the BST
    */
    public void put(T key){
        root = put(root, key);
    }

    /**
    * Helper function for put(T)
    *
    * @param    node Current node being compared
    * @param    key Generic type value to look for in the BST
    *
    * @return   Current node to recursive call (root node returns to put(T)) 
    */
    private BTNode put (BTNode<T> node, T key){
        if (node == null) return new BTNode(key);
        int cmpto = node.getKey().compareTo(key);
        if (cmpto > 0) node.setLeft(put(node.getLeft(), key));
        else if (cmpto < 0) node.setRight(put(node.getRight(), key));
        return node;
    }

    /**
    * Check if the BST contains a key
    *
    * @param	key Generic type value to look for in the BST
    *
    * @return	true if key is in the tree, false otherwise
    */
    public boolean contains(T key){
        return (contains(root, key) != null);
    }

    /**
    * Helper function for contains(T)
    *
    * @param    node Current node being compared
    * @param    key Generic type value to look for in the BST
    *
    * @return   node that key is found at or null if key is not found 
    */
    private BTNode contains (BTNode<T> node, T key){
        if (node == null) return null;
        int cmpto = node.getKey().compareTo(key);
        //if key < node.key (left)
        if (cmpto > 0) return contains(node.getLeft(), key);
        //if key > node.key (right)
        else if (cmpto < 0) return contains(node.getRight(), key);
        else return node;
    }

    /**
    * Remove a key from the BST, if key is present
    * 
    * @param	key Generic type value to remove from the BST
    */
    public void delete(T key){
        if (key == null) throw new IllegalArgumentException("Cannot call delete() on a null key");
        delete(root, key);
    }

    /**
    * Helper function for delete(T)
    *
    * @param    node Current node being compared
    * @param    key Generic type value to look for in the BST
    *
    * @return   node to setLeft or setRight on parent node 
    */
    private BTNode delete(BTNode<T> node, T key){
        if (node == null) return null;
        BTNode second;
        BTNode replacement = null;
        int cmpto = node.getKey().compareTo(key);
        //if key < node.key (left)
        if (cmpto > 0) node.setLeft(delete(node.getLeft(), key));
        //if key > node.key (right)
        else if (cmpto < 0) node.setRight(delete(node.getRight(), key));
        else {
            //set node to either max left subtree node of min right subtree node (or null if both are null)
            if (node.getLeft() != null){ 
                replacement = maxN(node.getLeft());
                second = secondToMax(node.getLeft());
                //update replacement node's parent's child with replacement node's child
                if (second != null) second.setRight(replacement.getLeft());
            }
            else if (node.getRight() != null){
                replacement = minN(node.getRight());
                second = secondToMin(node.getRight());
                //update replacement node's parent's child with replacement node's child
                if (second != null) second.setLeft(replacement.getRight());
            }
            else return null;
            //replace new node's left & right pointers with removed node's left and
            //right pointers (as long as it is not being pointed at BY the removed node)
            if (replacement != node.getLeft()) replacement.setLeft(node.getLeft());
            if (replacement != node.getRight()) replacement.setRight(node.getRight());
            //update root if we changed it
            if (node == root) root = replacement;
            return replacement;
        }
        return node;
    } 

    /**
    * Finds the max key value
    *
    * @param    node Root node to start search
    *
    * @return   node with highest key value from specified root
    */
    private BTNode maxN(BTNode<T> node){
        //traverse the whole way to the right
        if (node.getRight() != null) return maxN(node.getRight());
        else return node;
    }

    /**
    * Finds the min key value
    *
    * @param    node Root node to start search
    *
    * @return   node with lowest key value from specified root
    */
    private BTNode minN(BTNode<T> node){
        //traverse the whole way to the left
        if (node.getLeft() != null) return minN(node.getLeft());
        else return node;
    }

    /**
    * Finds the second highest key value
    *
    * @param    node Root node to start search
    *
    * @return   node with second highest key value from specified root
    */
    private BTNode secondToMax(BTNode<T> node){
        //returns null if there is only one node to search (no second to max)
        if (node.getRight() == null) return null;
        //traverse until 1 away from the max
        if (node.getRight().getRight() != null) return secondToMax(node.getRight());
        else return node;
    }

    /**
    * Finds the second lowest key value
    *
    * @param    node Root node to start search
    *
    * @return   node with second lowest key value from specified root
    */
    private BTNode secondToMin(BTNode<T> node){
        //returns null if there is only one node to search (no second to min)
        if (node.getLeft() == null) return null;
        //traverse until 1 away from the min
        if (node.getLeft().getLeft() != null) return secondToMin(node.getLeft());
        else return node;
    }

    /**
    * Determine the height of the BST
    *
    * <p>
    * A single node tree has a height of 1, an empty tree has a height of 0.
    *
    * @return	int value indicating the height of the BST
    */
    public int height(){
        return height(root);
    }

    /**
    * Helper function for height()
    *
    * @param    node Root node
    *
    * @return   int value of height
    */
    private int height(BTNode<T> node){
        if (node == null) return 0;
        return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
    }


    /**
    * Determine if the BST is height-balanced
    *
    * <p>
    * A height balanced binary tree is one where the left and right subtrees
    * of all nodes differ in height by no more than 1.
    *
    * @return	true if the BST is height-balanced, false if it is not
    */
    public boolean isBalanced(){
        if (root == null) return true;
        return isBalanced(root);
    }

    /**
    * Helper function for isBalanced()
    *
    * @param    node current node
    *
    * @return   true if tree is balanced, false if not
    */
    private boolean isBalanced(BTNode<T> node){
        //if |(height of left subtree)-(height of right subtree)| > 1, tree is not balanced
        if (Math.abs((height(node.getLeft()) - height(node.getRight()))) > 1) return false;
        //if there is a left or right subtree, check the height of that subtree
        if (node.getLeft() != null){
            //if recursive callee returns false, return false to caller
            if(!isBalanced(node.getLeft())) return false;
        }
        if (node.getRight() != null){
            //if recursive callee returns false, return false to caller
            if(!isBalanced(node.getRight())) return false;
        }
        return true;
    }

    /**
    * Produce a ':' separated String of all keys in ascending order
    *
    * <p>
    * Perform an in-order traversal of the tree and produce a String
    * containing the keys in ascending order, separated by ':'s.
    * 
    * @return	String containing the keys in ascending order, ':' separated
    */
    public String inOrderTraversal(){
        if (root == null) return "";
        String inOrder = inOrderTraversal(root, "");
        //removes last ":"
        return inOrder.substring(0, inOrder.length() - 1);
    }

    /**
    * Helper function for inOrderTraversal()
    *
    * @param    node Current node
    * @param    inOrder String that is modified to represent BST
    *
    * @return   String representation of BST
    */
    private String inOrderTraversal(BTNode<T> node, String inOrder){
        //move to left element if it exists, otherwise append current node to inOrder string
        if (node.getLeft() != null){
            inOrder = inOrderTraversal(node.getLeft(), inOrder);
        }
        //append current element to String
        inOrder += node.getKey() + ":";
        //move to right element if it exists
        if (node.getRight() != null){
            inOrder = inOrderTraversal(node.getRight(), inOrder);
        }
        return inOrder;
    }

    /**
    * Produce String representation of the BST
    * 
    * <p>
    * Perform a pre-order traversal of the BST in order to produce a String
    * representation of the BST. The reprsentation should be a comma separated
    * list where each entry represents a single node. Each entry should take
    * the form: *type*(*key*). You should track 4 node types:
    *     `R`: The root of the tree
    *     `I`: An interior node of the tree (e.g., not the root, not a leaf)
    *     `L`: A leaf of the tree
    *     `X`: A stand-in for a null reference
    * For each node, you should list its left child first, then its right
    * child. You do not need to list children of leaves. The `X` type is only
    * for nodes that have one valid child.
    * 
    * @return	String representation of the BST
    */
    public String serialize(){
        if (root == null) return "";
        String s = serialize(root, "R(" + root.getKey() + "),");
        //removes last ","
        return s.substring(0, s.length() - 1);
    }

    /**
    * Helper function for serialize()
    *
    * @param    node current node
    * @param    s string to be appended to and returned
    *
    * @return   String representation of the BST
    */
    private String serialize(BTNode<T> node, String s){
        //if there is a node to left, append current node as an interior node, then move to left node
        if (node.getLeft() != null){
            if (node != root) s += ("I(" + node.getKey() + "),");
            s = serialize(node.getLeft(), s);
            //if there is both a left and right node, move to right without appending current node again
            if (node.getRight()!= null){
                return serialize(node.getRight(), s);
            } else s += ("X(NULL),");
        }
        //if there is no left or right, append current node as leaf
        else if (node.getRight() == null){
            s += ("L(" + node.getKey() + "),");
        }
        //if there is a right node, append current node as an interior node, then move to right
        if (node.getRight() != null){
            if (node != root) s += ("I(" + node.getKey() + "),");
            //if there is no left node, append a NULL node  
            if (node.getLeft() == null) s += ("X(NULL),");
            s = serialize(node.getRight(), s);
        }
        return s;
    }

    /**
    * Produce a deep copy of the BST that is reversed (i.e., left children
    * hold keys greater than the current key, right children hold keys less
    * than the current key).
    *
    * @return	Deep copy of the BST reversed
    */
    public BST_Inter<T> reverse(){
        BST newBST = new BST();
        if (root == null) return newBST;
        newBST.root = reverse(newBST.root, root);
        return newBST;
    }

    /**
    * Helper function for reverse()
    *
    * @param    newRoot Root of new reversed BST
    * @param    oldNode Node from original BST to get key from
    *
    * @return   Root of new BST
    */
    private BTNode reverse(BTNode<T> newRoot, BTNode<T> oldNode){
        //get key from original BST
        newRoot = reversePut(newRoot, oldNode.getKey());

        //if there is a key to left recurse to next key
        if (oldNode.getLeft() != null){
            reverse(newRoot, oldNode.getLeft());
        }
        //if there is a key to right recurse to next key
        if (oldNode.getRight() != null){
            reverse(newRoot, oldNode.getRight());
        }
        return newRoot;
    }

    /**
    * Helper function for reverse()
    *
    * @param    node New node to be put into reverseBST
    * @param    key Generic type value to look for in the BST
    *
    * @return   Current node to recursive call (root node returns to reverse(BTNode, BTNode))
    */
    private BTNode reversePut(BTNode<T> node, T key){
        if (node == null) return new BTNode(key);
        int cmpto = node.getKey().compareTo(key);
        if (cmpto < 0) node.setLeft(reversePut(node.getLeft(), key));
        else if (cmpto > 0) node.setRight(reversePut(node.getRight(), key));
        return node;
    }
}