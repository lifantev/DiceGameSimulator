package com.netcracker.edu.dicegame;

/**
 * Class Pair<K, V> provides keeping two values together.
 *
 * @param <K> Key
 * @param <V> Value
 */
class Pair<K,V> {

    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() { return key; }
    public V getValue() { return value; }

    public void setKey(K key) { this.key = key;}
    public void setValue(V value) { this.value = value;}

    /**
     * Creates String from Pair<KEY, VALUE></>
     *
     * @return String in format: "KEY with VALUE"
     */
    @Override
    public String toString() {
        return getKey().toString() + " with " + getValue().toString();
    }

    @Override
    public int hashCode() { return key.hashCode() ^ value.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) return false;
        Pair pairo = (Pair) o;
        return this.key.equals(pairo.getKey()) &&
                this.value.equals(pairo.getValue());
    }
}
