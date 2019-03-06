package com.intellinum.rib.test;

import com.intellinum.rib.utils.BeanToUnderscore;

/**
 * Created by Otniel on 5/6/2015.
 */
public class TestBeanToUnderscore {
    public static void main(String[] args){
        String var = "testBeanVariable";
        BeanToUnderscore convert = new BeanToUnderscore(var);
        System.out.println(convert.getUnderScoredVariable());
    }
}
