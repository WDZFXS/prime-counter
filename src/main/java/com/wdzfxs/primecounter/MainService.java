package com.wdzfxs.primecounter;

import com.wdzfxs.primecounter.generator.Generator;
import com.wdzfxs.primecounter.generator.PrimeIntegers;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class MainService {

    private final boolean[] primeIntegers;
    public boolean ready = false;

    public MainService(Generator generator) {
        File file = new File("Integers");
        if (!file.exists()) {
            System.out.println("File \"Integers\" not exist \nGenerator started");
            try {
                generator.generate();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (file.exists()) {
            ready = true;
        } else {
            System.out.println("Generated file not found");
            System.exit(0);
        }

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrimeIntegers primeIntegers = null;
        try {
            primeIntegers = (PrimeIntegers) Objects.requireNonNull(objectInputStream).readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        this.primeIntegers = Objects.requireNonNull(primeIntegers).getIntegers();
    }

    public HashMap<Integer, Integer> calculate(List<Integer> integerList) {
        HashMap<Integer, Integer> counter = new HashMap<>();

        for (int integer : integerList) {
            if (integer < 2) {
                continue;
            }

            if (integer == Integer.MAX_VALUE) {
                if (counter.containsKey(integer)) {
                    counter.put(integer, counter.get(integer) + 1);
                } else {
                    counter.put(integer, 1);
                }
            }

            if (primeIntegers[integer]) {
                if (counter.containsKey(integer)) {
                    counter.put(integer, counter.get(integer) + 1);
                } else {
                    counter.put(integer, 1);
                }
            }
        }

        return counter;
    }
}