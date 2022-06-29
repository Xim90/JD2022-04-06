package by.it.avramchuk.jd02_07.util;

import by.it._classwork_.jd01_14.TaskA;

import java.io.File;

public class PathFinder {
    public static String getPath(Class<?> aClass, String filename) {
        String root = System.getProperty(TaskA.USER_DIR);
        String relationPath = aClass.getName()
                .replace(aClass.getSimpleName(), "")
                .replace(".", File.separator);
        return root + File.separator + "src" + File.separator + relationPath + filename;
    }
}
