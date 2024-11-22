package org.mrusername.ion.core.lang.unit;

import java.util.Map;

public class IonClassLoader extends ClassLoader {
    private final Map<String, byte[]> classBytes;

    public IonClassLoader(Map<String, byte[]> classBytes) {
        this.classBytes = classBytes;
    }
    public void addClass(String name, byte[] bytes) {
        classBytes.put(name, bytes);
    }
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = classBytes.get(name);
        if (bytes == null) {
            throw new ClassNotFoundException(name);
        }
        int lastDotIndex = name.lastIndexOf('.');
        if (lastDotIndex != -1) {
            String packageName = name.substring(0, lastDotIndex);
            if (getDefinedPackage(packageName) == null) {
                definePackage(packageName, null, null, null, null, null, null, null);
            }
        }
        return defineClass(name, bytes, 0, bytes.length);
    }
}