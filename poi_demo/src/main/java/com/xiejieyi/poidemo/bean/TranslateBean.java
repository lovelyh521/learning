package com.xiejieyi.poidemo.bean;

import java.util.List;

/**
 * 类描述：
 *
 * @author
 */
public class TranslateBean
{
    private String word;
    private String phonetic;
    private List explains;

    public String getWord()
    {
        return word;
    }

    public void setWord(String word)
    {
        this.word = word;
    }

    public String getPhonetic()
    {
        return phonetic;
    }

    public void setPhonetic(String phonetic)
    {
        this.phonetic = phonetic;
    }

    public List getExplains()
    {
        return explains;
    }

    public void setExplains(List explains)
    {
        this.explains = explains;
    }
}
