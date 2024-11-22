package org.mrusername.ion.plugin.tojava;

import org.gradle.api.model.ObjectFactory;

import javax.inject.Inject;
import java.io.File;
import java.util.List;
import java.util.function.UnaryOperator;

public class ToJavaExtension {
    private final ObjectFactory objects;

    private File sourceDir;
    private File outputDir;
    private String fileExtension;
    private List<UnaryOperator<String>> transforms;
    @Inject
    public ToJavaExtension(ObjectFactory objects) {
        this.objects = objects;
    }

    public File getSourceDir() {
        return sourceDir;
    }

    public void setSourceDir(File sourceDir) {
        this.sourceDir = sourceDir;
    }

    public File getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(File outputDir) {
        this.outputDir = outputDir;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public List<UnaryOperator<String>> getTransforms() {
        return transforms;
    }

    public void setTransforms(List<UnaryOperator<String>> transforms) {
        this.transforms = transforms;
    }
}