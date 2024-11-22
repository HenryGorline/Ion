package org.mrusername.ion.core.lang.unit;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class IonJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {
    private final Map<String, ByteArrayOutputStream> classBytes = new HashMap<>();

    public IonJavaFileManager(JavaFileManager fileManager) {
        super(fileManager);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        classBytes.put(className, baos);
        return new SimpleJavaFileObject(URI.create("byte:///" + className.replace('.', '/') + kind.extension), kind) {
            @Override
            public OutputStream openOutputStream() {
                return baos;
            }
        };
    }

    public Map<String, byte[]> getClassBytes() {
        Map<String, byte[]> result = new HashMap<>();
        classBytes.forEach((key, value) -> result.put(key, value.toByteArray()));
        return result;
    }
}