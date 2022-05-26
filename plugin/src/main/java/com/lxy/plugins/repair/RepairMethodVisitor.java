package com.lxy.plugins.repair;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.AdviceAdapter;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Loader;

public class RepairMethodVisitor extends AdviceAdapter {
    private boolean isNeedModify = false;
    private String classPath;

    public RepairMethodVisitor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor, String classPath) {
        super(api, methodVisitor, access, name, descriptor);
        this.classPath = classPath;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if (descriptor.equals("Lcom/example/repair/Repair;")) {
            isNeedModify = true;
//            insertCode(Test.class.getName(),"test",
//                    "System.out.println(\"-678910\");",
//                    "System.out.println(\"678910\");");
//            insertCode("com.example.temp.Test", "temp", "$1=\"aaa\";", "Log.e(\"repair\", \"aaa\");");
//            insertCode("com.example.temp.activity.MainActivity", getName(), "$1=\"repair\";", null);
        }
        return super.visitAnnotation(descriptor, visible);
    }

    @Override
    //进入这个方法(在此插入方法块会插入到原方法最前面)
    protected void onMethodEnter() {
        super.onMethodEnter();

        if (isNeedModify) {
//            Label label = new Label();
//            mv.visitLabel(label);
//            mv.visitLdcInsn("repair1");
//            mv.visitVarInsn(ALOAD, 1);
//            mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false);
//            mv.visitInsn(POP);
        }
    }

    @Override
    //即将从这个方法出去(在此插入方法块会插入到方法最后，return之前)
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);

        if (isNeedModify) {
            isNeedModify = false;
        }
    }

    /**
     * 新增代码块（跨模块调用仍有问题）
     *
     * @param classPath  全类名
     * @param methodName 方法名
     * @param beforeCode 需要添加的代码(前)
     * @param afterCode  需要添加的代码（后）
     */
    private void insertCode(String classPath, String methodName, String beforeCode, String afterCode) {
        try {
            ClassPool pool = ClassPool.getDefault();
            //project.android.bootClasspath 加入android.jar，不然找不到android相关的所有类
            pool.appendClassPath("D:\\Android\\SDK\\platforms\\android-31\\android.jar");
            //将当前路径加入类池,不然找不到这个类
            pool.appendClassPath("D:\\Project\\AndroidProject\\Temp\\app\\build\\intermediates\\javac\\debug\\classes");
            pool.appendClassPath("dalvik.system.PathClassLoader");
            //引入android.os.Bundle包，因为onCreate方法参数有Bundle
            pool.appendClassPath("C:\\Users\\ASUS\\.gradle\\caches\\transforms-3\\bccd891f783672fbd18773bd939589a8\\transformed\\appcompat-1.3.0\\jars\\classes.jar");
            pool.appendClassPath("C:\\Users\\ASUS\\.gradle\\caches\\transforms-3\\7783413a51667a64f3c12aad5e982499\\transformed\\core-1.5.0\\jars\\classes.jar");
            pool.appendClassPath("C:\\Users\\ASUS\\.gradle\\caches\\transforms-3\\d39acc25592dc25ab6e8999a6014baa3\\transformed\\fragment-1.3.4\\jars\\classes.jar");
            pool.appendClassPath("C:\\Users\\ASUS\\.gradle\\caches\\transforms-3\\5ccb12b8d56f5e919552c0a91fe5fdc4\\transformed\\activity-1.2.3\\jars\\classes.jar");
            pool.appendClassPath("android.os.Bundle");

            //导入调用的方法包
            pool.importPackage("android.util.Log");

            //插入代码
            CtClass ctClass = pool.getCtClass(classPath);
            CtMethod ctMethod = ctClass.getDeclaredMethod(methodName);
            ctClass.defrost();
            if (beforeCode != null) {
                ctMethod.insertBefore(beforeCode);
            }
            if (afterCode != null) {
                ctMethod.insertAfter(afterCode);
            }
            ctClass.writeFile("D:\\Project\\AndroidProject\\Temp\\app\\build\\intermediates\\javac\\debug\\classes");
            // 调用修改过的类的方法

            Loader classLoader = new Loader(pool);
            classLoader.loadClass(classPath);
            ctClass.detach();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }

    }

}
