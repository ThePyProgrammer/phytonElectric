package application.model.util;

import java.util.*;

public class FixedArrayList<T> extends ArrayList<T> {
    int capacity;
    public FixedArrayList(int capacity) {
        super(capacity);
        this.capacity = capacity;
    }
    @Override
    public boolean add(T e) {
        if (this.size() < capacity) {
            return super.add(e);
        }
        return false;
    }
}
