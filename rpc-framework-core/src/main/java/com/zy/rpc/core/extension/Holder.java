package com.zy.rpc.core.extension;

/**
 * Holder 类，作用是为不可变的对象引用提供一个可变的包装
 *
 * @author zy
 * @version 1.0
 */
public class Holder<T> {

    private volatile T value;

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

}
