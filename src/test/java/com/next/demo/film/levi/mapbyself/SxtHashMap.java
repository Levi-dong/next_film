package com.next.demo.film.levi.mapbyself;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 自定义map
 * 实现put方法增加得键值对，并解决键值重复的时候覆盖相应得节点
 * @author Levi
 */
public class SxtHashMap {

    Node[] table; //位桶数组，bucket arry
    int size;    //存放的键值对个数

    public SxtHashMap(){
        table = new Node[16]; //长度一般定义为2的整数幂
    }

    public void put(Object key,Object value){
        Node newNode = new Node();
        newNode.hash = myHash(key.hashCode(),table.length);
        newNode.key = key;
        newNode.value = value;
        newNode.next = null;

        Node temp = table[newNode.hash];
        Node lastNode = null;//正在遍历得最后一个元素
        boolean keyRepeat = false;
        if(!Optional.ofNullable(temp).isPresent()){
            //此数组元素为空，则直接将新节点放进去
            table[newNode.hash] = newNode;
            size++;
        }else {
            //此数组元素不为空，则遍历对应链表
            while (Optional.ofNullable(temp).isPresent()){
                //判断key如果重复，则覆盖
                if (temp.key.equals(key)) {
                    keyRepeat = true;
                    temp.value = value; //只是覆盖value即可，其他的值保持不表
                    break;
                }else {
                    //key不重复
                    lastNode = temp;
                    temp = temp.next;
                }
            }
            if (!keyRepeat){//没有发生key重复的情况，则添加到链表最后
                lastNode.next = newNode;
                size++;
            }
        }
    }

    public static int myHash(int v,int length){
        //System.out.println(v&(length-1)); //效率高
        //System.out.println(v%length);   //效率低
        return v&(length-1);
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder("{");
        //遍历bucket数组
        for (int i = 0; i < table.length; i++) {
            Node temp = table[i];
            //遍历链表
            while (temp!=null){
                stringBuilder.append(temp.key+"="+temp.value+",");
                temp = temp.next;
            }
        }
        if (stringBuilder.length()>0)
            stringBuilder.setCharAt(stringBuilder.length()-1, '}');
        return stringBuilder.toString();
    }

    public Object get(Object key){
        int hash = myHash(key.hashCode(),table.length);
        Object value = null;

        if(table[hash]!=null){
            Node temp = table[hash];
            while (temp!=null){
                if(temp.key.equals(key)){//如果相等，则说明找到了键值对，返回value
                    value = temp.value;
                    break;
                }else {
                    temp = temp.next;
                }
            }
        }

        return value;
    }
}
