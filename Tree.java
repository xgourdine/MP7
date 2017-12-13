import java.util.ArrayList;
import java.util.List;

/**
 * A class that implements a basic binary tree storing integers.
 * <p>
 * You need to complete this class by adding methods to insert values, compute the minimum and
 * maximum, search for a value, and compute a value's depth in the tree and number of descendants.
 * You will also need to complete several constructors used during tree creation and value
 * insertion.
 * <p>
 * Our binary tree is an example of a <i>recursive data structure</i>, since each tree instance
 * refers to several other tree instances to complete the entire structure. As you might expect,
 * recursive algorithms are particularly useful on recursive data structures.
 *
 * @see <a href="https://cs125.cs.illinois.edu/MP/6/">MP6 Documentation</a>
 * @see <a href="https://en.wikipedia.org/wiki/Binary_tree">Binary Tree</a>
 */
public class Tree {

    private Tree root = getRoot(this);

    /* Current node's parent. May be null if I'm the root of the tree. */
    private Tree parent;

    /* Current node's left child, or null if none. */
    private Tree left;

    /* Current node's left child, or null if none. */
    private Tree right;

    /* Current node's value. */
    private int value;

    public Tree(int setValue) {
        value = setValue;
    }

    public Tree(int setValue, Tree setParent) {
        value = setValue;
        parent = setParent;
    }

    public static void main(String[] args) {
        Tree tree1 = new Tree(5);

        tree1.insert(3);
        tree1.insert(2);
        tree1.insert(4);
        tree1.insert(15);
        tree1.insert(6);
        tree1.insert(8);
        tree1.insert(10);
        tree1.insert(7);

        printAll(tree1);
    }

    /**
     * Recursively prints all of the values in the passed in tree
     * @param tree the passed in tree
     */
    public static void printAll(Tree tree) {
        if (tree.right != null) {
            printAll(tree.right);
        }

        System.out.println(tree.value);

        if (tree.left != null) {
            printAll(tree.left);
        }
    }

    /**
     * Sorts a tree from lowest value to highest and stores the result in
     * an integer array.
     * @param tree
     * @return
     */
    public static int[] sortTree(Tree tree) {
        List<Integer> list = new ArrayList<>();

        recurseThroughAll(tree, list);

        int length = list.size();
        int[] array = new int[length];

        for (int i = 0; i < length; i++) {
            array[i] = list.get(i);
        }

        sort(array, 0, list.size() - 1);

        return array;

    }

    /**
     * Helper function for sortTree. Recurses through all items in the tree and
     * adds them to a List object.
     * @param tree
     * @param list
     */
    public static void recurseThroughAll(Tree tree, List<Integer> list) {
        if (tree.left != null) {
            recurseThroughAll(tree.left, list);
        }

        if (tree.right != null) {
            recurseThroughAll(tree.right, list);
        }

        list.add(tree.value);
    }

    /**
     * All values that are smaller than the pivot will be set before the pivot,
     * and all the values that are bigger than pivot will be set after the pivot,
     * returning the final position of the pivot value.
     * @param arr
     * @param low
     * @param high
     * @return
     */
    static int partition(int arr[], int low, int high) {
        int pivot = arr[high];
        int i = (low-1);

        for (int j=low; j<high; j++) {
            if (arr[j] <= pivot) {
                i++;

                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i+1;
    }

    /**
     * Determines a pivot value to break the array on.
     * Recursively sorts each side of the pivot.
     * @param arr
     * @param low
     * @param high
     */
    static void sort(int arr[], int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);

            sort(arr, low, pi-1);
            sort(arr, pi+1, high);
        }
    }


    /**
     * Determines which of two trees has the larger sum.
     * Then, prints the larger tree's sum. If the two trees
     * have equal sums, prints that they are equal.
     * @param tree1
     * @param tree2
     * @return
     */
    public Tree isLarger(Tree tree1, Tree tree2) {
        int sum1 = sum(tree1);
        int sum2 = sum(tree2);

        if (sum1 > sum2) {
            System.out.println("The larger tree is the first tree and it's value is " + sum1);

            return tree1;
        }
        else if (sum1 < sum2) {
            System.out.println("The larger tree is the second tree and it's value is " + sum2);

            return tree2;
        } else {
            System.out.println("Two trees are equal");

            return null;
        }
    }

    /**
     * Recurses through the passed in tree and finds the value of
     * all nodes.
     * @param tree
     * @return
     */
    public int sum(Tree tree) {
        if (tree.left != null && tree.right != null) {
            return tree.value + sum(tree.left) + sum(tree.right);
        }

        if (tree.right != null) {
            return tree.value + sum(tree.right);
        }

        if (tree.left != null) {
            return tree.value + sum(tree.left);
        }

        return tree.value;
    }

    public boolean insert(int newValue) {
        if (root == null) {
            root = new Tree(newValue);

            return true;
        } else {
            return root.addItem(newValue);
        }
    }

    private boolean addItem(int value) {
        if (value == this.value) {
            return false;
        }
        else if (value > this.value) {
            if (right == null) {
                right = new Tree(value, this);

                return true;
            } else {
                return right.addItem(value);
            }
        }
        else if (value < this.value) {
            if (left == null) {
                left = new Tree(value, this);

                return true;
            } else {
                return left.addItem(value);
            }
        }

        return false;
    }

    public int minimum() {
        int currentValue = this.value;

        if (this.left != null) {
            if (currentValue < this.left.value) {
                return currentValue;
            } else {
                return this.left.minimum();
            }
        } else {
            return currentValue;
        }
    }

    public int maximum() {
        int currentValue = this.value;

        if (this.right != null) {
            if (currentValue > this.right.value) {
                return currentValue;
            } else {
                return this.right.maximum();
            }
        } else {
            return currentValue;
        }
    }

    public Tree search(int searchValue) {
        if (searchValue == this.value) {
            return this;
        }
        else if (searchValue < this.value && this.left != null) {
            return this.left.search(searchValue);
        } else if (searchValue > this.value && this.right != null){
            return this.right.search(searchValue);
        }

        return null;
    }

    public int depth() {
        if (this.parent != null) {
            return 1 + this.parent.depth();
        }

        return 0;
    }

    public int descendants() {
        if (this.left != null && this.right != null) {
            return 2 + this.left.descendants() + this.right.descendants();
        }
        if (this.right != null) {
            return 1 + this.right.descendants();
        }
        if (this.left != null) {
            return 1 + this.left.descendants();
        }

        return 0;
    }

    private Tree getRoot(Tree currentTree) {
        if (currentTree.parent == null) {
            return currentTree;
        } else {
            return getRoot(currentTree.parent);
        }
    }
}
