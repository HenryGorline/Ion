package org.mrusername.ion.core.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IonFiles {
    public static String getSourceCode(Class<?> clazz){
        String className = clazz.getSimpleName();
        String packageName = clazz.getPackage().getName();
        String packagePath = packageName.replace('.', '/');
        String sourceFilePath = "src/main/java/" + packagePath + "/" + className + ".java";
        Path path = Paths.get(sourceFilePath);
        try {
            return java.nio.file.Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getSourceCode(Object object){
        return getSourceCode(object.getClass());
    }
    public static String read(Path path){
        try{
            return Files.readString(path);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
