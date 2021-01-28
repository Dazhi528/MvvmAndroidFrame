package com.dazhi.libroot.util;

import java.util.HashMap;

/**
 * 功能：链表哈希数据结构
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2021/1/25 17:28
 */
@SuppressWarnings({"unused", "RedundantSuppression"})
public class LinkedMap<T extends LinkedMap.Ext<T>> {
    private final HashMap<String, T> map = new HashMap<>();
    private T head;
    private T tail;

    public int size() {
        return map.size();
    }
    
    public void put(T t) {
        if (t == null) {
            return;
        }
        synchronized (LinkedMap.class) {
            // 存在校验
            String key = t.getKey();
            if (map.get(key) != null) {
                return;
            }
            // map赋值
            map.put(key, t);
            // 链表赋值
            // 头空
            if (head == null) {
                tail = null;
                head = t;
                head.setPrev(null);
                head.setNext(null);
            }
            // 尾空
            else if (tail == null) {
                tail = t;
                head.setNext(tail);
                tail.setPrev(head);
                tail.setNext(null);
            }
            // 都不空
            else {
                T temp = tail;
                tail = t;
                tail.setPrev(temp);
                temp.setNext(tail);
            }
        }
    }

    // 获得队首，判断是否需要重发
    public T first() {
        return head;
    }

    // 键值对精确查找
    public T get(String key) {
        return map.get(key);
    }

    public T remove(String key) {
        synchronized (LinkedMap.class) {
            T temp = map.remove(key);
            if (temp != null) {
                T prev = temp.getPrev();
                temp.setPrev(null);
                T next = temp.getNext();
                temp.setNext(null);
                if (prev != null) {
                    prev.setNext(next);
                }
                if (next != null) {
                    next.setPrev(prev);
                }
                if(head==temp) {
                    head = next;
                }
                if(tail==temp) {
                    tail = prev;
                }
            }
            return temp;
        }
    }

    public void cancel() {
        synchronized (LinkedMap.class) {
            map.clear();
            T temp;
            while (head!=null) {
                temp = head.getNext();
                head.setPrev(null);
                head.setNext(null);
                head = temp;
            }
            if(tail!=null) {
                tail.setPrev(null);
                tail.setNext(null);
                tail = null;
            }
        }
    }

    /**
     * 作者：WangZezhi  (2021/1/27  18:14)
     * 功能：扩展成链表结构
     * 描述：即此链表哈希结构的数据bean类需实现此接口
     */
    public interface Ext<E> {
        // 子类中需定义类似如下变量，并实现如下的get/set方法
        // private E prev;
        // private E next;

        // 上一个
        E getPrev();
        void setPrev(E prev);

        // 下一个
        E getNext();
        void setNext(E next);

        // 索引key
        String getKey();
    }
    
}
