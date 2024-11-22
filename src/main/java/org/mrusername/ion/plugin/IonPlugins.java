package org.mrusername.ion.plugin;

import org.gradle.api.Project;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class IonPlugins {
    public static void createDir(File file){
        if (file.exists()) {
            file.delete();
        }
        file.mkdirs();
    }
    public static File getSourceDir(Project project) {
        return project.file("src/main/java");
    }
    public static File getBuildSubDir(Project project,String path) {
        return project.file("build/" + path);
    }
    public static void forEachFile(Path sourceDir, String fileExtension, Consumer<Path> fileProcessor) {
        try (Stream<Path> paths = Files.walk(sourceDir)) {
            paths.filter(path -> path.toString().endsWith(fileExtension)).forEach(fileProcessor);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
