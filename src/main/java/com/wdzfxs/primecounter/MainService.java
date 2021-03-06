package com.wdzfxs.primecounter;

import com.wdzfxs.primecounter.generator.Generator;
import com.wdzfxs.primecounter.generator.PrimeIntegers;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class MainService {

    private final boolean[] integers;
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
        ObjectInputStream objectInputStream = null;
        PrimeIntegers primeIntegers = null;
        try {
            fileInputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(fileInputStream);
            primeIntegers = (PrimeIntegers) Objects.requireNonNull(objectInputStream).readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.integers = Objects.requireNonNull(primeIntegers).getIntegers();
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

            if (integers[integer]) {
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