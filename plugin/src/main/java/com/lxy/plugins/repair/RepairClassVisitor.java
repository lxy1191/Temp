package com.lxy.plugins.repair;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class RepairClassVisitor extends ClassVisitor {
    private String className;

    public RepairClassVisitor(int asmApiVersion, ClassVisitor classVisitor) {
        super(asmApiVersion, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        System.out.println(name);
        this.className=name;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        return new RepairMethodVisitor(api, methodVisitor, access, name, descriptor,className);
    }


}
