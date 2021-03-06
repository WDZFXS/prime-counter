package com.wdzfxs.primecounter.generator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.stream.IntStream;

@Component
public class Generator {

    @Value("${maxIntValue}")
    private int maxExpectedInt;


    public void generate() throws IOException {
        System.out.println("Начал выделять память на массив");
        PrimeIntegers primeIntegers = new PrimeIntegers(maxExpectedInt);
        System.out.println("Закончил выделять память на массив");

        System.out.println("Начал заполнять массив");
        IntStream
                .range(0, 5000000)
                .parallel()
                .filter(this::isPrimeOptimized)
                .forEach(primeIntegers::add);
        System.out.println("Закончил заполнять массив");

        System.out.println("Начал сериализацию");
        FileOutputStream fileOutputStream = new FileOutputStream("Integers");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(primeIntegers);
        objectOutputStream.close();
        fileOutputStream.close();
        System.out.println("Закончил сериализацию");
    }

    private boolean isPrimeOptimized(int number) {
        if (number < 2) {
            return false;
        }
        if (number % 2 == 0) {
            return number == 2;
        }
        if (number % 3 == 0) {
            return number == 3;
        }
        for (int i = 5; i * i <= number; i += 6) {
            if (number % i == 0 || number % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }
}