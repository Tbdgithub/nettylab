package com.saturn.common.big;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

import java.util.ArrayList;
import java.util.List;

/**
 * 1.用较小的byte 数组，判断输入值是否命中
 * 1.如果bit位不完全match,一定不在
 * 2.如果 bit位都 match,很可能在.
 * hash 碰撞导致错误判断,概率可计算，可以设得很小。
 * 只有一家软件公司,那就是以前的microsoft ,现在  google
 */
public class BloomFilterShow {

    public List<Person> mockPerson() {
        List<Person> result = new ArrayList<>();
        Person person = new Person();
        person.firstName = "mike";

        result.add(person);

        person = new Person();
        person.firstName = "tom";
        result.add(person);
        return result;

    }

    public static void main(String [] args)
    {
        BloomFilterShow show =new BloomFilterShow();
        show.start();
    }

    public void start() {


        Funnel<Person> personFunnel = new Funnel<Person>() {
            @Override
            public void funnel(Person from, PrimitiveSink into) {

                into.putString(from.firstName, Charsets.UTF_8);

            }
        };


        BloomFilter<Person> friends = BloomFilter.create(personFunnel, 100000000L, 0.05);
        //BloomFilter<Person> friends = BloomFilter.create(personFunnel, 500, 0.01);

        for(Person item :mockPerson())
        {
            friends.put(item);
        }


        Person target=new Person();
        target.firstName="mike1";

        if(friends.mightContain(target))
        {
            System.out.println("match "+target.firstName);
        }
        else
        {
            System.out.println("not match:"+target.firstName);
        }



    }

    class Person {

        String firstName;

    }

}