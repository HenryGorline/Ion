package org.mrusername.ion.core.lang.unit;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

public class IonJavaFileObject extends SimpleJavaFileObject {
    private String sourceCode;

    public IonJavaFileObject(String className, String sourceCode) {
        super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.sourceCode = sourceCode;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return sourceCode;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }
}