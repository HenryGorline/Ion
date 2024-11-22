package org.mrusername.ion.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.compile.JavaCompile;
import org.mrusername.ion.plugin.tojava.ToJavaExtension;
import org.mrusername.ion.plugin.tojava.ToJavaTask;

public abstract class IonPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        applyIonPlugin(project);
    }
    public abstract void applyIonPlugin(Project project);

    public void addToJavaTask(Project project) {
        ToJavaExtension extension = project.getExtensions().create("toJava", ToJavaExtension.class);
        project.getTasks().register("toJava", ToJavaTask.class, task -> {
        });
        project.getTasks().named("compileJava", JavaCompile.class, task -> {
            task.dependsOn("toJava");
            task.setSource(extension.getOutputDir());
        });
    }

}