package domaca_naloga2;


import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Drevo23<Tip> implements Seznam<Tip> {

    private class Node {
        Tip key1;
        Tip key2;
        Node left, middle, right;
        Node parent;
        boolean isLeaf;

        Node(Tip key) {
            this.key1 = key;
            this.key2 = null;
            this.left = this.middle = this.right = null;
            this.isLeaf = true;
        }

        boolean is2Node() {
            return key2 == null;
        }

        boolean is3Node() {
            return key2 != null;
        }
    }

    private class SplitResult {
        Tip middleKey;
        Node left, right;

        SplitResult(Tip middleKey, Node left, Node right) {
            this.middleKey = middleKey;
            this.left = left;
            this.right = right;
        }
    }

    private Node root;
    private int size;
    private Tip lastRemoved;
    private Comparator<Tip> comparator;


    public Drevo23(Comparator<Tip> comparator) {
        root = null;
        this.comparator = comparator;
        size = 0;
    }

    @Override
    public void add(Tip e) {
        if (exists(e)) {
            throw new IllegalArgumentException("Dvojnik ni dovoljen.");
        }

        if (root == null) {
            root = new Node(e);
            size++;
        } else {
            SplitResult split = insert(root, e);
            if (split != null) {
                // Root was split -> create new root
                Node newRoot = new Node(split.middleKey);
                newRoot.isLeaf = false;
                newRoot.left = split.left;
                newRoot.middle = split.right;

                // Set parent pointers
                split.left.parent = newRoot;
                split.right.parent = newRoot;

                root = newRoot;
            }
            size++;
        }
    }


    private SplitResult insert(Node node, Tip e) {
        if (node.isLeaf) {
            return insertIntoLeaf(node, e);
        }

        SplitResult childSplit;
        if (comparator.compare(e, node.key1) < 0) {
            childSplit = insert(node.left, e);
            if (childSplit != null) return handleSplit(node, childSplit, 0);
        } else if (node.is2Node() || comparator.compare(e, node.key2) < 0) {
            childSplit = insert(node.middle, e);
            if (childSplit != null) return handleSplit(node, childSplit, 1);
        } else {
            childSplit = insert(node.right, e);
            if (childSplit != null) return handleSplit(node, childSplit, 2);
        }

        return null;
    }

    private SplitResult insertIntoLeaf(Node node, Tip e) {
        if (node.is2Node()) {
            if (comparator.compare(e, node.key1) < 0) {
                node.key2 = node.key1;
                node.key1 = e;
            } else {
                node.key2 = e;
            }
            return null;
        }

        List<Tip> keys = new ArrayList<>(List.of(node.key1, node.key2, e));
        keys.sort(comparator); // WILL IT WORK?

        Node left = new Node(keys.get(0));
        Node right = new Node(keys.get(2));

        return new SplitResult(keys.get(1), left, right);
    }


    private SplitResult handleSplit(Node node, SplitResult childSplit, int position) {
        if (node.is2Node()) {
            node.key2 = childSplit.middleKey;

            if (position == 0) {
                node.right = node.middle;
                node.left = childSplit.left;
                node.middle = childSplit.right;
            } else {
                node.middle = childSplit.left;
                node.right = childSplit.right;
            }

            node.left.parent = node;
            node.middle.parent = node;
//            if (node.right != null) node.right.parent = node;

            return null;
        }

        List<Tip> keys = new ArrayList<>(List.of(node.key1, node.key2, childSplit.middleKey));
        List<Node> children = new ArrayList<>();
        if (position == 0) {
            children.add(childSplit.left);
            children.add(childSplit.right);
            children.add(node.middle);
            children.add(node.right);
        } else if (position == 1) {
            children.add(node.left);
            children.add(childSplit.left);
            children.add(childSplit.right);
            children.add(node.right);
        } else {
            children.add(node.left);
            children.add(node.middle);
            children.add(childSplit.left);
            children.add(childSplit.right);
        }

        keys.sort(comparator); // WILL IT WORK??

        Node left = new Node(keys.get(0));
        Node right = new Node(keys.get(2));
        left.isLeaf = right.isLeaf = false;

        // Assign children and parent pointers
        left.left = children.get(0);  left.left.parent = left;
        left.middle = children.get(1); left.middle.parent = left;
        right.left = children.get(2);  right.left.parent = right;
        right.middle = children.get(3); right.middle.parent = right;

        return new SplitResult(keys.get(1), left, right);
    }


    @Override
    public Tip removeFirst() {
        return remove(root.key1);
    }

    @Override
    public Tip getFirst() {
        if (root != null) {
            return root.key1;
        }
        throw new IllegalArgumentException("Drevo je prazno.");
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int depth() {
        int dep = 0;
        Node current = root;
        while(current != null) {
            dep += 1;
            current = current.left;
        }
        return dep;
    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
    }

    @Override
    public Tip remove(Tip e) {
        if (e == null) throw new NullPointerException();
        if (root == null) throw new IllegalArgumentException("Drevo je prazno.");
        if (!exists(e)) throw new IllegalArgumentException("Element ni v drevesu.");

        lastRemoved = e;
        root = removeRecursive(root, e);
        if (root.isLeaf && root.key1 == null) {
            root = null; // Tree is empty
        }
        size--;
        return lastRemoved;
    }

    private Node removeRecursive(Node node, Tip e) {
        if (node.isLeaf) {
            // Found the leaf node containing the key
            if (node.is3Node() && comparator.compare(node.key2, e) == 0) {
                node.key2 = null;
            } else if (comparator.compare(node.key1, e) == 0) {
                if (node.is2Node()) {
                    // Mark as empty (will be handled by parent)
                    node.key1 = null;
                } else {
                    // Replace with key2 and make it 2-node
                    node.key1 = node.key2;
                    node.key2 = null;
                }
            }
            return node;
        }

        // Internal node
        if (comparator.compare(e, node.key1) < 0) {
            // Go left
            Node child = removeRecursive(node.left, e);
            Node result = fixUnderflow(node, child, 0);
            return result == node ? node : removeRecursive(result, e); // Handle root change
        } else if ((node.is2Node() && comparator.compare(e, node.key1) == 0) ||
                (node.is3Node() && comparator.compare(e, node.key1) == 0)) {
            // Find successor (left-most in middle subtree)
            Tip successor = findSuccessor(node.middle);
            lastRemoved = node.key1;
            node.key1 = successor;
            Node child = removeRecursive(node.middle, successor);
            Node result = fixUnderflow(node, child, 1);
            return result == node ? node : removeRecursive(result, e); // Handle root change
        }
        else if (node.is3Node() && comparator.compare(e, node.key2) == 0) {
            // Find successor (left-most in right subtree)
            Tip successor = findSuccessor(node.right);
            lastRemoved = node.key2;
            node.key2 = successor;
            Node child = removeRecursive(node.right, successor);
            Node result = fixUnderflow(node, child, 2);
//            return result == node ? node : removeRecursive(result, e); // Handle root change
            return node;
        }
        else if (node.is2Node() || comparator.compare(e, node.key2) < 0) {
            // Go middle
            Node child = removeRecursive(node.middle, e);
            Node result = fixUnderflow(node, child, 1);
            return result == node ? node : removeRecursive(result, e); // Handle root change
        } else {
            // Go right
            Node child = removeRecursive(node.right, e);
            Node result = fixUnderflow(node, child, 2);
//            return result == node ? node : removeRecursive(result, e); // Handle root change
            return node;
        }
    }

    private Tip findSuccessor(Node node) {
        while (!node.isLeaf) {
            node = node.left;
        }
        return node.key1;
    }

    private Node fixUnderflow(Node parent, Node child, int childPos) {
        if (child.key1 != null) {
            return parent; // No underflow
        }

        // Handle underflow (child is empty or null)
        if (childPos == 0) { // Left child
            Node sibling = parent.middle;
            if (sibling.is3Node()) {
                // Transfer from right sibling
                child.key1 = parent.key1;
                parent.key1 = sibling.key1;
                sibling.key1 = sibling.key2;
                sibling.key2 = null;

            } else {
                // Merge with sibling
                Node merged = new Node(parent.key1);
                merged.key2 = sibling.key1;
                merged.isLeaf = sibling.isLeaf;

                if (parent.is2Node()) {
                    // Parent becomes empty
                    return merged; // This becomes the new root
                } else {
                    parent.key1 = parent.key2;
                    parent.key2 = null;
                    parent.left = merged;
                    parent.middle = parent.right;
                    parent.right = null;
                }
            }
        } else if (childPos == 1) { // Middle child
            // Try left sibling first
            Node sibling = parent.left;
            if (sibling.is3Node()) {
                // Transfer from left sibling
                child.key1 = parent.key1;
                parent.key1 = sibling.key2;
                sibling.key2 = null;


            } else if (parent.is3Node()) {
                // Try right sibling
                sibling = parent.right;

                    // Merge with right sibling
                    Node merged = new Node(parent.key2);
                    merged.key2 = sibling.key1;
                    merged.isLeaf = sibling.isLeaf;

                    parent.key2 = null;
                    parent.middle = merged;
                    parent.right = null;

            } else {
                // Merge with left sibling
                Node merged = new Node(sibling.key1);
                merged.key2 = parent.key1;
                merged.isLeaf = sibling.isLeaf;


                    return merged;

            }
        } else { // Right child (childPos == 2)
            Node sibling = parent.middle;
                // Merge with sibling
                Node merged = new Node(sibling.key1);
                merged.key2 = parent.key2;
                merged.isLeaf = sibling.isLeaf;

                parent.key2 = null;
                parent.middle = merged;
                parent.right = null;
        }

        return parent;
    }

    @Override
    public boolean exists(Tip e) {
        return existsRecursive(root, e);
    }

    private boolean existsRecursive(Node node, Tip e) {
        if (node == null) {
            return false;
        }

        // Check current node's keys (not just root)
        if (node.is3Node()) {
            if (comparator.compare(e, node.key1) == 0 || comparator.compare(e, node.key2) == 0) {
                return true;
            }
        } else {
            if (comparator.compare(e, node.key1) == 0) {
                return true;
            }
        }

        // Case for leaf nodes
        if (node.isLeaf) {
            if (node.is2Node()) {
                return comparator.compare(node.key1, e) == 0;
            } else {
                return comparator.compare(node.key2, e) == 0;
            }
        }

        // Case for internal (non-leaf) nodes
        if (comparator.compare(e, node.key1) < 0) {
            return existsRecursive(node.left, e);
        } else if (node.is2Node() || comparator.compare(e, node.key2) < 0) {
            return existsRecursive(node.middle, e);
        } else {
            return existsRecursive(node.right, e);
        }
    }

    @Override
    public void print() {
        printTree(root);
    }

    private void printTree(Node node) {
        if (node == null) return;

        if (node.is2Node()) {
            System.out.println(node.key1);
        } else {
            System.out.println(node.key1 + "\n" + node.key2);
        }

        if (!node.isLeaf) {
            printTree(node.left);
            printTree(node.middle);
            if (node.right != null) {
                printTree(node.right);
            }
        }
    }

    @Override
    public void save(OutputStream outputStream) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        List<Tip> elements = new ArrayList<>();
        collectElements(root, elements);
        out.writeInt(elements.size());
        for (Tip element : elements) {
            out.writeObject(element);
        }
    }

    private void collectElements(Node node, List<Tip> elements) {
        if (node == null) return;

        if (!node.isLeaf) {
            collectElements(node.left, elements);
        }
        elements.add(node.key1);

        if (!node.isLeaf) {
            collectElements(node.middle, elements);
        }

        if (!node.is2Node()) {
            elements.add(node.key2);
            if (!node.isLeaf) {
                collectElements(node.right, elements);
            }
        }
    }

    private void reset() {
        while (root != null)
            removeFirst();
    }

    @Override
    public void restore(InputStream inputStream) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(inputStream);
        int count = in.readInt();

        reset();

        for (int i = 0; i < count; i++) {
            Tip element = (Tip) in.readObject();
            this.add(element); // Use your existing add logic
        }
    }

    @Override
    public Tip getElement(Tip e) {
        return element(root, e);
    }

    private Tip element(Node node, Tip e) {
        if (node == null) {
            return null;
        }

        // Check current node's keys (not just root)
        if (node.is3Node()) {
            if (comparator.compare(e, node.key1) == 0 || comparator.compare(e, node.key2) == 0) {
                if (comparator.compare(e, node.key1) == 0)
                    return node.key1;
                else
                    return node.key2;
            }
        } else {
            if (comparator.compare(e, node.key1) == 0) {
                return node.key1;
            }
        }

        // Case for leaf nodes
        if (node.isLeaf) {
            if (node.is2Node()) {
                return comparator.compare(node.key1, e) == 0 ? node.key1 : null;
            } else {
                return comparator.compare(node.key2, e) == 0? node.key2 : null;
            }
        }

        // Case for internal (non-leaf) nodes
        if (comparator.compare(e, node.key1) < 0) {
            return element(node.left, e);
        } else if (node.is2Node() || comparator.compare(e, node.key2) < 0) {
            return element(node.middle, e);
        } else {
            return element(node.right, e);
        }
    }

    public List<Tip> asList() {
        List<String> nodeStrings = new ArrayList<>();
        traverseTree(root, nodeStrings);
        return (List<Tip>) nodeStrings;
    }

    private void traverseTree(Node node, List<String> result) {
        if (node == null) return;

        if (node.is2Node()) {
            result.add("(" + node.key1 + ")");
        } else {
            result.add("(" + node.key1 + "," + node.key2 + ")");
        }

        if (!node.isLeaf) {
            traverseTree(node.left, result);
            traverseTree(node.middle, result);
            if (node.right != null) {
                traverseTree(node.right, result);
            }
        }
    }
}
