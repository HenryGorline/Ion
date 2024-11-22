package org.mrusername.ion.plugin.tojava;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.mrusername.ion.core.util.IonFiles;
import org.mrusername.ion.plugin.IonPlugins;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.function.UnaryOperator;

public abstract class ToJavaTask extends DefaultTask {
    @TaskAction
    public void transformFiles() {
        ToJavaExtension extension = getProject().getExtensions().findByType(ToJavaExtension.class);
        if(extension == null){
            throw new RuntimeException("ToJavaExtension not found");
        }
        File sourceDir = extension.getSourceDir();
        File outputDir = extension.getOutputDir();
        String fileExtension = extension.getFileExtension();
        List<UnaryOperator<String>> transforms = extension.getTransforms();
        if(sourceDir==null){
            throw new NullPointerException("ToJava source directory is not defined.");
        }
        if(outputDir==null){
            throw new NullPointerException("ToJava output directory is not defined.");
        }
        if(fileExtension==null){
            throw new NullPointerException("ToJava file extension is not defined.");
        }
        if(transforms==null){
            extension.setTransforms(List.of());
        }
        IonPlugins.createDir(outputDir);
        IonPlugins.forEachFile(sourceDir.toPath(), fileExtension, path -> {
            try {
                Path relativePath = sourceDir.toPath().relativize(path);
                Path targetPath = outputDir.toPath().resolve(relativePath);
                Files.createDirectories(targetPath.getParent());
                String sourceCode = IonFiles.read(path);
                for(UnaryOperator<String> transform : extension.getTransforms()){
                    sourceCode = transform.apply(sourceCode);
                }
                Files.copy(path, targetPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}