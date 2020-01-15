package com.javarush.task.task37.task3707;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class AmigoSet<E> extends AbstractSet<E> implements Serializable,Cloneable, Set<E> {

    private static final Object PRESENT = new Object();
    private transient HashMap<E, Object> map;

    public AmigoSet() {
        this.map = new HashMap<>();
    }

    public AmigoSet(Collection<? extends E> collection) {
        int capacity = (int) Math.max(16, collection.size() / .75f + 1);
        map = new HashMap<>(capacity);

        this.addAll(collection);
    }

    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean add(E e) {
        if (!map.containsKey(e)) {
            map.put(e, PRESENT);
            return true;
        } else return false;
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean remove(Object o) {
        return super.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return super.containsAll(c);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            AmigoSet copy = (AmigoSet)super.clone();
            copy.map = (HashMap) map.clone();
            return copy;
        } catch (Exception e) {
            throw new InternalError(e);
        }

    }
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(HashMapReflectionHelper.callHiddenMethod(map, "capacity"));
        objectOutputStream.writeObject(HashMapReflectionHelper.callHiddenMethod(map, "loadFactor"));
        objectOutputStream.writeInt(map.keySet().size());
        for(E e : map.keySet()) {
            objectOutputStream.writeObject(e);
        }
    }
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        Object capacity = in.readObject();
        Object loadFactor = in.readObject();
        map = new HashMap((int) capacity, (float)loadFactor);
        int size = in.readInt();
        for(int i = 0; i<size; i++) {
            map.put((E)in.readObject(), PRESENT);
        }
    }
}

