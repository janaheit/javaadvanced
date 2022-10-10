package be.abis.exercise.test;

import java.util.function.BiFunction;

public class ReduceWithLambdasTest {

    public static void main(String[] args) {
        BiFunction<Double, Double, Double> reduce = (price, percent) -> price*(1-(percent/100));
        System.out.println(reduce.apply(100.0, 25.0));
    }
}
