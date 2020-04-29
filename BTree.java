public class BTree <T extends Comparable<T>> {
    Node<T> root;

   public BTree (T key) {
        root = new Node<>(key);
   }
   public BTree (Node <T> node) {
       root = node;
       root.sibling = null;
       root.parent = null;

   }

    public BTree <T> merge (BTree<T> tree) {
       tree.root.sibling = root.child;
       root.child = tree.root;
       tree.root.parent = root;
       root.degree ++;
       return(new BTree<T>(root));
   }

   public void show (Node<T> t) {
       if (t != null){
           System.out.println(t.key + " " + t.degree);
           //System.out.println("sib:");
           show(t.sibling);
           //System.out.println(t.key + "children:");
           show(t.child);
       }
   }

   public int getRootDegree() {
       return root.degree;
   }

}
