package org.pitest.rewriter;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.pitest.util.Log;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class AssertTransMethodAdapter extends MethodVisitor {

    private static final Logger LOG = Log.getLogger();
    String name;
    int curLine = -1;
    int access;

    private static Set<String> assertDictionary = new HashSet<String>();

    public AssertTransMethodAdapter(MethodVisitor methodVisitor, int access, String classMethodDescription){
        super(Opcodes.ASM5, methodVisitor);
//        this.name = classMethodDescription;
        this.name = classMethodDescription.split(":")[0];
        this.access = access;
    }

    static {
        Collections.addAll(assertDictionary, "assertTrue", "assertFalse", "assertEquals", "assertNotEquals", "assertNull", "assertNotNull",
                "assertNotSame", "assertSame", "assertArrayEquals", "fail", "failNotEquals", "failNotSame",
                "failSame", "assertFailSame", "assertThat");
    }

    @Override
    public void visitLineNumber(int line, Label start) {
        mv.visitLineNumber(line, start);
        curLine = line;
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        mv.visitMaxs(maxStack + 4, maxLocals);
    }

    @Override
    public void visitMethodInsn(final int opcode,final String owner,final String name,final String descriptor, boolean isInterface) {
        if (shouldTransform(owner, name)){
            mv.visitLdcInsn(this.name.replace("/",".") + ":" + curLine);
            LOG.info("#### shouldtransfrommethod:" + this.name.replace("/", ".") + ":" + curLine  +":" + name);
            mv.visitMethodInsn(opcode, Properties.REWRITER_CLASS_NAME, name, getNewDesc(descriptor), isInterface);
        }else {
            mv.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }
    }

    public String getNewDesc(String description){
        return description.substring(0, description.length() - 2) + "Ljava/lang/String;)V";
    }

    public boolean shouldTransform(String className, String methodName){
        if (assertDictionary.contains(methodName)){
            return true;
        }
        return false;
    }

}
