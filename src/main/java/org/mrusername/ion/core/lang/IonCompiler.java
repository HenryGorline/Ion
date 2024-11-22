package org.mrusername.ion.core.lang;

import org.mrusername.ion.core.lang.unit.IonClassLoader;
import org.mrusername.ion.core.lang.unit.IonJavaFileManager;
import org.mrusername.ion.core.lang.unit.IonJavaFileObject;
import org.mrusername.ion.core.util.IonFiles;

import javax.tools.*;
import java.util.ArrayList;
import java.util.List;

public class IonCompiler {
    protected final JavaCompiler compiler;
    protected final IonJavaFileManager fileManager;
    protected final IonClassLoader classLoader;
    protected final List<IonJavaFileObject> units ;
    protected final DiagnosticCollector<JavaFileObject> diagnosticCollector;

    public IonCompiler(JavaCompiler compiler, IonJavaFileManager fileManager, IonClassLoader classLoader, List<IonJavaFileObject> units,DiagnosticCollector<JavaFileObject> diagnosticCollector) {
        this.compiler = compiler;
        this.fileManager = fileManager;
        this.classLoader = classLoader;
        this.units = units;
        this.diagnosticCollector = diagnosticCollector;
    }
    public boolean compile(String packageName, String sourceCode) {
        units.add(new IonJavaFileObject(packageName, sourceCode));
        return compile();
    }

    public boolean compile() {
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, units);
        if (!task.call()) {
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                System.err.println("Error on line " + diagnostic.getLineNumber() + ": " + diagnostic.getMessage(null));
            }
            return false;
        }
        fileManager.getClassBytes().forEach(classLoader::addClass);
        return true;
    }
    public <T> Class<T> compileToClass(String packageName, String sourceCode) {
        compile(packageName, sourceCode);
        try {
            return (Class<T>) classLoader.loadClass(packageName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error while loading class: " + packageName, e);
        }
    }
    public static class Builder{
        protected final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        protected final IonJavaFileManager fileManager = new IonJavaFileManager(compiler.getStandardFileManager(new DiagnosticCollector<>(), null, null));
        protected final IonClassLoader classLoader = new IonClassLoader(fileManager.getClassBytes());
        protected final List<IonJavaFileObject> units = new ArrayList<>();
        public Builder addSource(Object source){
            units.add(new IonJavaFileObject(source.getClass().getName(), IonFiles.getSourceCode(source)));
            return this;
        }
        public IonCompiler build(){
            return new IonCompiler(compiler, fileManager, classLoader, units,new DiagnosticCollector<>());
        }
    }
}
