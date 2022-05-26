package com.lxy.plugins.repair;

import com.android.build.api.instrumentation.FramesComputationMode;
import com.android.build.api.instrumentation.InstrumentationScope;
import com.android.build.api.variant.AndroidComponentsExtension;
import com.android.build.api.variant.ApplicationVariant;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import kotlin.Unit;


public class RepairPlugin implements Plugin<Project> {
    public void apply(Project project) {
        AndroidComponentsExtension androidComponentsExtension = project.getExtensions().findByType(AndroidComponentsExtension.class);
        androidComponentsExtension.onVariants(androidComponentsExtension.selector().all(),  variant -> {
            ApplicationVariant appVariant=(ApplicationVariant)variant;
            appVariant.transformClassesWith(RepairClassVisitorFactory.class, InstrumentationScope.PROJECT, (params) ->  Unit.INSTANCE);
            appVariant.setAsmFramesComputationMode(FramesComputationMode.COMPUTE_FRAMES_FOR_INSTRUMENTED_CLASSES);
        });
    }

}
