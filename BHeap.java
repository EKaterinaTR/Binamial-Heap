import java.util.ArrayList;
public class BHeap<T extends Comparable<T>> {
    ArrayList<BTree<T>> trees;

    private BHeap (ArrayList<BTree<T>> t ){
        trees = t;
    }
    public BHeap (){
        trees = new ArrayList<>();
    }
    public BHeap(T key){
        this();
        trees.add(new BTree<T>(key));
    }

    public T getMin () {
        T key = trees.get(0).root.key;
        for(BTree<T> t : trees) {
            if (key.compareTo(t.root.key) > 0) {
                key = t.root.key;
            }
        }
        return key;
    }
    public void merge (BHeap <T> heap) {
        int i = 0;
        int j = 0;
        BTree<T> last = null;
        ArrayList<BTree<T>> t = new ArrayList<>();
        while (i < trees.size() && j < heap.trees.size()) {
            BTree<T> t1 = trees.get(i);
            BTree<T> t2 = heap.trees.get(j);

            if (t1.getRootDegree() == t2.getRootDegree()) {
                if (t1.root.key.compareTo(t2.root.key) < 0) {
                    last = t1.merge(t2);
                }
                else {
                    last = t2.merge(t1);
                }
                i++;
                j++;
            } else {
                if (t1.getRootDegree() > t2.getRootDegree()) {
                    t1 = t2;
                    j++;
                }
                else {
                    i++;
                }

                if (last != null && last.getRootDegree() == t1.getRootDegree()) {
                    t.remove((Object)last);
                    if (t1.root.key.compareTo(last.root.key) < 0) {
                        last = t1.merge(last);
                    }
                    else {
                        last = last.merge(t1);
                    }
                }
                else {
                    last = t1;
                }
            }
            t.add(last);
        }
        while (i < trees.size()){
            BTree<T> t1 = trees.get(i);
            if (last != null && last.getRootDegree() == t1.getRootDegree()) {
                t.remove((Object)last);
                if (t1.root.key.compareTo(last.root.key) < 0) {
                    last = t1.merge(last);
                }
                else {
                    last = last.merge(t1);
                }
            }
            else {
                last = t1;
            }
            t.add(last);
            i++;
        }
        while (j < heap.trees.size()) {
            BTree<T> t1 = heap.trees.get(j);
            if (last != null && last.getRootDegree() == t1.getRootDegree()) {
                t.remove((Object)last);
                if (t1.root.key.compareTo(last.root.key) < 0) {
                    last = t1.merge(last);
                }
                else {
                    last = last.merge(t1);
                }
            }
            else {
                last = t1;
            }
            t.add(last);
            j++;
        }

        trees = t;
    }

    public void insert (T key) {
        BHeap<T> h = new BHeap<>(key);
        merge(h);

    }

    public void show () {
        for (BTree<T> t : trees) {
            t.show(t.root);
            System.out.println();
        }
    }

    public void extractMin () {
        BTree <T> key = trees.get(0);
        for(BTree<T> t : trees) {
            if (key.root.key.compareTo(t.root.key) > 0) {
                key = t;
            }
        }
        trees.remove(key);

        ArrayList<BTree<T>> newtrees  = new ArrayList<>();
        ArrayList<Node<T>>  children = new ArrayList<>();
        Node<T> leftChild = key.root.child;
        while(leftChild != null) {
            children.add(leftChild);
            leftChild = leftChild.sibling;
        }
        for(int i = children.size()-1; i >= 0; i--) {
            newtrees.add(new BTree<T>(children.get(i)));
        }
        merge(new BHeap<>(newtrees));

    }

    public void decreaseKey (Node<T> node, T key) {
        if (node.key.compareTo(key) > 0) {
            node.key = key;
            goToRightBTree(node);

        }
    }
    private void  goToRightBTree(Node<T> n) {
        while(n.parent != null && n.key.compareTo(n.parent.key) < 0) {
            T key = n.parent.key;
            n.parent.key = n.key;
            n.key = key;

            n = n.parent;
        }
    }

    public void delete(Node<T> node){
        decreaseKey(node,getMin());
        extractMin();

  }


}
