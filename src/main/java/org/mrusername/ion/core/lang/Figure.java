package org.mrusername.ion.core.lang;

public class Figure {
    protected String sourceCode;
    protected String name;
    public Figure(String sourceCode, String name) {
        this.sourceCode = sourceCode;
        this.name = name;
    }

    @Override
    public String toString() {
        return sourceCode;
    }

    public void addField(int modifier, boolean isStatic, boolean isFinal, String type, String name, String value) {
        String modifierStr = java.lang.reflect.Modifier.toString(modifier);
        if (isStatic) {
            modifierStr += " static";
        }
        if (isFinal) {
            modifierStr += " final";
        }

        String fieldDeclaration = String.format("%s %s %s = %s;%n", modifierStr, type, name, value);
        sourceCode = sourceCode.replaceFirst("(?<=\\{)", "\n    " + fieldDeclaration);
    }
    public void addMethod(int modifier, boolean isStatic, boolean isFinal, String returnType, String name, String params, String body) {
        String modifierStr = java.lang.reflect.Modifier.toString(modifier);
        if (isStatic) {
            modifierStr += " static";
        }
        if (isFinal) {
            modifierStr += " final";
        }
        String methodDeclaration = String.format("%s %s %s(%s) {%n%s%n    }%n", modifierStr, returnType, name, params, body);
        sourceCode = sourceCode.replaceFirst("(?=}\\s*$)", "\n\n    " + methodDeclaration);
    }

    public void addConstructor(int modifier, String params, String body) {
        String modifierStr = java.lang.reflect.Modifier.toString(modifier);
        String constructorDeclaration = String.format("%s %s(%s) {%n%s%n    }%n", modifierStr, name,params, body);
        sourceCode = sourceCode.replaceFirst("(?=}\\s*$)", "\n\n    " + constructorDeclaration);
    }
}