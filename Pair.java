public class Pair<K, V> {

    private K a;
    private V b;
    
    public Pair(){
    }

    public Pair(K element0, V element1) {
        this.a = element0;
        this.b = element1;
    }

    public K getA() {
        return a;
    }

    public V getB() {
        return b;
    }
    
    public void setA(K element0) {
      this.a = element0;
    }

    public void setB(V element1) {
      this.b = element1;
    }
}
