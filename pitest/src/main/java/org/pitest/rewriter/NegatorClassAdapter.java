package org.pitest.rewriter;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.pitest.util.Log;

import java.util.logging.Logger;

public class NegatorClassAdapter extends ClassVisitor {

    private static final Logger LOG = Log.getLogger();
    String name;

    public NegatorClassAdapter(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor);
    }

    @Override
    public void visit(final int version,final int access,final String name,final String signature,final String superName,final String[] interfaces) {
        this.name = name;
        cv.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
//        if (this.name.contains("ESTest")){
//            LOG.info("###### ESTest CLASS: " + this.name + ":" + name + ":" + descriptor);
//        }
        mv = new AssertTransMethodAdapter(mv, access, this.name + ":" + name + ":" + descriptor);

        return mv;
    }
}
