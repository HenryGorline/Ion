package org.mrusername.ion;

import org.gradle.api.Project;
import org.mrusername.ion.plugin.IonPlugin;

import java.util.Set;

public class Ion extends IonPlugin {
    @Override
    public void applyIonPlugin(Project project) {
        addToJavaTask(project);
    }

}
