package com.milev.ivan.os;

import com.sun.javafx.binding.Logging;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

public class Main {

    public static void showDifferentClassloaders() {
        System.out.println("System(app) class loader:"
                + Main.class.getClassLoader());

        System.out.println("Extension class loader:"
                + Logging.class.getClassLoader());

        System.out.println("Boostrap class loader:"
                + ArrayList.class.getClassLoader());
    }

    public static void loadClass(String name) throws ClassNotFoundException {
        Class.forName(name);
    }

    public static void contextClassLoader() throws InterruptedException {
        Thread t = new Thread(() -> {
            Thread it = new Thread(() -> {
                System.out.println("");
            });
            it.setContextClassLoader(Logging.class.getClassLoader());
            System.out.print("Thread with ExtClassLoader set as context class loader: ");
            System.out.println(it.getContextClassLoader());
        });
        System.out.print("Thread without explicit set for context class loader: ");
        System.out.println(t.getContextClassLoader());
        t.start();
        t.join();
    }

    public static void loadJarRuntime() throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        File jar = new File("HelloWorld.jar");
        URL[] jarURL = {jar.toURL()};
        URLClassLoader classLoader = new URLClassLoader(jarURL, ClassLoader.getSystemClassLoader());
        Class<?> helloWorldClass = Class.forName("HelloWorld", true, classLoader);
        Object instance = helloWorldClass.newInstance();
        System.out.println(instance);
    }

    public static void main(String[] args) throws InterruptedException, ClassNotFoundException, MalformedURLException, InstantiationException, IllegalAccessException {
        showDifferentClassloaders();

//        try {
//            loadClass("NonExistingClass");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }

        contextClassLoader();
        loadJarRuntime();
    }
}
