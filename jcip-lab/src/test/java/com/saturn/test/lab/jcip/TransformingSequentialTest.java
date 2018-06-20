package com.saturn.test.lab.jcip;

import com.saturn.lab.jcip.ApplyingThreadPools.TransformingSequential;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransformingSequentialTest {

    @Test
    public void test1() {

        TransformingSequential transformingSequential = new TransformingSequential() {
            @Override
            public void process(TransformingSequential.Element e) {
                   System.out.println("processed :"+e.toString());
            }
        };

        List<TransformingSequential.Node<String>> nodes = new ArrayList<>();

        TransformingSequential.Node<String> item1 = new TransformingSequential.Node<String>() {
            @Override
            public String compute() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "computed item1";
            }

            @Override
            public List<TransformingSequential.Node<String>> getChildren() {
                return new ArrayList<>();
            }
        };

        TransformingSequential.Node<String> item = new TransformingSequential.Node<String>() {
            @Override
            public String compute() {

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "computed item";
            }

            @Override
            public List<TransformingSequential.Node<String>> getChildren() {
                List<TransformingSequential.Node<String>> items = new ArrayList<>();
                items.add(item1);
                return items;
            }
        };

        nodes.add(item);

        try {
            Collection<String> result = transformingSequential.getParallelResults(nodes);
            System.out.println(result);

//
//            TransformingSequential.Element elem0=new TransformingSequential.Element() {
//                @Override
//                public int hashCode() {
//                    return super.hashCode();
//                }
//            };
//            ExecutorService exec = Executors.newCachedThreadPool();
//            List<TransformingSequential.Element> elements=new ArrayList<>();
//            elements.add(elem0);
//            transformingSequential.processInParallel(exec,elements);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
