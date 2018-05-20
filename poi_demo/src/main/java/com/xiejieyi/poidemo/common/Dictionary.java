package com.xiejieyi.poidemo.common;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 类描述：
 *
 * @author
 */
public class Dictionary
{
    private HashMap<String, Integer> dictionary ;
    private int wordsCount;

    /**
     * 字典这个类的构造函数
     */
    public Dictionary()    {
        dictionary =  new HashMap<>();
        wordsCount = 0;
    }

    /**
     * 向字典里插入一个单词
     *
     * @param word
     */
    public void insert(String word)
    {
        if (dictionary.containsKey(word))
        {
            int currentCount = dictionary.get(word);
            dictionary.put(word, currentCount + 1);
        } else
        {
            dictionary.put(word, 1);
        }
        wordsCount++;
    }

    /**
     * 展示字典中存放的所有单词及其出现次数
     */
    public List getDictionary()
    {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(dictionary.entrySet());

        //升序排序
        Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        return list.stream().filter(item->item.getValue()>5).collect(Collectors.toList());
        // return list.stream().filter(item->item.getValue()>5 && item.getValue()<30).collect(Collectors.toList());
    }

}