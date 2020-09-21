package org.pitest.rewriter;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.pitest.util.Log;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

public class AssertTransMethodAdapter extends MethodVisitor {

    private static final Logger LOG = Log.getLogger();

    int curLine = -1;
    int access;

    String classname;
    String testname;
    boolean testcase = false;

//    private static Set<String> assertDictionary = new HashSet<String>();

    public static final String columnSeperator = "\t";
    public static Map<String, Set<String>> assertionDictionary = new HashMap<String, Set<String>>();

    public static Set<Integer> returns = new HashSet<Integer>();

    static {
//        Collections.addAll(assertDictionary, "assertTrue", "assertFalse", "assertEquals", "assertNotEquals", "assertNull", "assertNotNull",
//                "assertNotSame", "assertSame", "assertArrayEquals", "fail", "failNotEquals", "failNotSame",
//                "failSame", "assertFailSame", "assertThat");

        Collections.addAll(returns, Opcodes.IRETURN, Opcodes.RETURN, Opcodes.LRETURN, Opcodes.FRETURN, Opcodes.DRETURN, Opcodes.ARETURN);

        Set<String> descriptionSet = new HashSet<String>();

        Collections.addAll(descriptionSet, "(Ljava/lang/String;Z)V", "(Z)V");
        assertionDictionary.put("assertTrue", new HashSet<String>(descriptionSet));
        descriptionSet.clear();

        Collections.addAll(descriptionSet, "(Ljava/lang/String;Z)V", "(Z)V");
        assertionDictionary.put("assertFalse", new HashSet<String>(descriptionSet));
        descriptionSet.clear();

        Collections.addAll(descriptionSet, "(Ljava/lang/String;)V", "()V");
        assertionDictionary.put("fail", new HashSet<String>(descriptionSet));
        descriptionSet.clear();

        Collections.addAll(descriptionSet, "(Ljava/lang/String;[Ljava/lang/Object;[Ljava/lang/Object;)V","([Ljava/lang/Object;[Ljava/lang/Object;)V",
                "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V"
                , "(Ljava/lang/Object;Ljava/lang/Object;)V", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V"
                , "(Ljava/lang/String;Ljava/lang/String;)V", "(Ljava/lang/String;DDD)V",
                "(DDD)V", "(Ljava/lang/String;FFF)V", "(FFF)V", "(Ljava/lang/String;JJ)V", "(JJ)V","(Ljava/lang/String;ZZ)V"
                , "(ZZ)V", "(Ljava/lang/String;BB)V", "(BB)V", "(Ljava/lang/String;CC)V", "(CC)V", "((Ljava/lang/String;SS)V"
                ,"(SS)V", "(Ljava/lang/String;II)V", "(II)V");

        assertionDictionary.put("assertEquals", new HashSet<String>(descriptionSet));
        descriptionSet.clear();

        Collections.addAll(descriptionSet, "(Ljava/lang/Object;)V", "(Ljava/lang/String;Ljava/lang/Object;)V");
        assertionDictionary.put("assertNotNull", new HashSet<String>(descriptionSet));
        descriptionSet.clear();

        Collections.addAll(descriptionSet, "(Ljava/lang/Object;)V", "(Ljava/lang/String;Ljava/lang/Object;)V");
        assertionDictionary.put("assertNull", new HashSet<String>(descriptionSet));
        descriptionSet.clear();

        Collections.addAll(descriptionSet, "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V", "(Ljava/lang/Object;Ljava/lang/Object;)V");
        assertionDictionary.put("assertSame", new HashSet<String>(descriptionSet));
        descriptionSet.clear();

        Collections.addAll(descriptionSet, "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V", "(Ljava/lang/Object;Ljava/lang/Object;)V");
        assertionDictionary.put("assertNotSame", new HashSet<String>(descriptionSet));
        descriptionSet.clear();

        Collections.addAll(descriptionSet, "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V");
        assertionDictionary.put("failNotSame", new HashSet<String>(descriptionSet));
        descriptionSet.clear();

        Collections.addAll(descriptionSet, "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V");
        assertionDictionary.put("failNotEquals", new HashSet<String>(descriptionSet));
        descriptionSet.clear();

        Collections.addAll(descriptionSet, "(Ljava/lang/String;[Ljava/lang/Object;[Ljava/lang/Object;)V"
                , "([Ljava/lang/Object;[Ljava/lang/Object;)V", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V"
                , "(Ljava/lang/String;[B[B)V", "([B[B)V","(Ljava/lang/String;[D[DD)V",
                "([D[DD)V", "(Ljava/lang/String;[F[FF)V", "([F[FF)V", "(Ljava/lang/String;[J[J)V", "([J[J)V","(Ljava/lang/String;[Z[Z)V"
                , "([Z[Z)V",   "(Ljava/lang/String;[C[C)V", "([C[C)V", "((Ljava/lang/String;[S[S)V"
                ,"([S[S)V", "(Ljava/lang/String;[I[I)V", "([I[I)V");
        assertionDictionary.put("assertArrayEquals", new HashSet<String>(descriptionSet));
        descriptionSet.clear();

        Collections.addAll(descriptionSet, "(Ljava/lang/String;)V");
        assertionDictionary.put("failSame", new HashSet<String>(descriptionSet));
        descriptionSet.clear();

        Collections.addAll(descriptionSet, "(Ljava/lang/Object;Lorg/hamcrest/Matcher;Ljava/lang/String;)V","(Ljava/lang/String;Ljava/lang/Object;Lorg/hamcrest/Matcher;Ljava/lang/String;)V");
        assertionDictionary.put("assertThat", new HashSet<String>(descriptionSet));
        descriptionSet.clear();

        LOG.info("<<<<< Assertion dictonary contains:");
        for(Entry<String, Set<String>> entrySet : assertionDictionary.entrySet()){
            LOG.info("<<<< " + entrySet.getKey());
            LOG.info("<<<<<<<< Contains descriptions: " + entrySet.getValue().size());
        }

    }

    public AssertTransMethodAdapter(MethodVisitor methodVisitor, int access, String classMethodDescription){
        super(Opcodes.ASM5, methodVisitor);
        this.classname = classMethodDescription;
//        this.name = classMethodDescription.split(":")[0];
        this.access = access;
    }


    @Override
    public void visitLineNumber(int line, Label start) {
        mv.visitLineNumber(line, start);
        curLine = line;
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        mv.visitMaxs(maxStack, maxLocals);
    }

    @Override
    public void visitMethodInsn(final int opcode, final String owner, final String name, final String descriptor, boolean isInterface) {
        if (shouldTransform(owner, descriptor, name, opcode)){
            mv.visitLdcInsn(this.classname.replace("/",".") + ":" + curLine + ":" + name + "\n");
//            LOG.info("<<<<< Transforming assertID:" + this.classname.replace("/",".") + ":" + curLine + ":" + name + "\n");
            mv.visitMethodInsn(opcode, Properties.REWRITER_CLASS_NAME, name, getNewDesc(descriptor), isInterface);
        }else {
            mv.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }
    }

    public String getNewDesc(String description){
        return description.substring(0, description.length() - 2) + "Ljava/lang/String;)V";
    }

    public boolean shouldTransform(String owner, String description, String methodName, int opcode){
        if (assertionDictionary.containsKey(methodName)){
            // junit/framework/Assert is depricated but however, it will not hurt if we consider it
            // org.junit.jupiter.api.Assertions is for junit5, method descriptions need to be added
            if ("org/junit/Assert".equals(owner) || ("junit/framework/Assert".equals(owner)) || opcode == Opcodes.INVOKESTATIC) {
                if (assertionDictionary.get(methodName).contains(description)) {
                    return true;
                }
                if ("assertThat".equals(methodName)) {
                    return true;
                }
            }
        }
        return false;
    }

}
