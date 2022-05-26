package com.lxy.plugins.repair;

import com.android.build.api.instrumentation.AsmClassVisitorFactory;
import com.android.build.api.instrumentation.ClassContext;
import com.android.build.api.instrumentation.ClassData;
import com.android.build.api.instrumentation.InstrumentationParameters;

import org.objectweb.asm.ClassVisitor;

public abstract class RepairClassVisitorFactory implements AsmClassVisitorFactory<InstrumentationParameters.None> {

    @Override
    public ClassVisitor createClassVisitor( ClassContext classContext, ClassVisitor classVisitor) {
        return new RepairClassVisitor(this.getInstrumentationContext().getApiVersion().get(), classVisitor);
    }

    @Override
    public boolean isInstrumentable( ClassData classData) {
        return true;
    }
}
