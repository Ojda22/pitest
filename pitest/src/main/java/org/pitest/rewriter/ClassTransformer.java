package org.pitest.rewriter;

import org.pitest.util.Log;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

public class ClassTransformer implements ClassFileTransformer {

    private static final Logger LOG = Log.getLogger();

    //make white list automatic
    private String whiteList = Properties.PROJECT_PREFIX;

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        LOG.info("ClassName: " + className);
        try {
            if (className == null){
                return classfileBuffer;
            }
            if (loader != ClassLoader.getSystemClassLoader()){
                return classfileBuffer;
            }

            if (this.whiteList == null || !className.startsWith(this.whiteList)){
             return classfileBuffer;
            }

            LOG.info("Property: " + whiteList);
            LOG.info("Transformer for class: " + className);

            byte[] result = classfileBuffer;
            ClassReader reader = new ClassReader(classfileBuffer);
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            ClassVisitor cv = writer;

            cv = new NegatorClassAdapter(cv);
            reader.accept(cv, ClassReader.SKIP_FRAMES);
            result = writer.toByteArray();
            return result;
        }catch (Throwable throwable){
            throwable.printStackTrace();
            String message = "Exception thrown during instrumentation";
            LOG.log(Level.SEVERE, message);
            System.exit(1);
        }
        throw new RuntimeException("Should not come here");
    }

    public ClassTransformer(){
//        BufferedReader br = null;
//
//        try {
//            String sCurrentLine;
//            br = new BufferedReader(new FileReader("prefix.conf"));
//
//            while ((sCurrentLine = br.readLine()) != null) {
//                whiteList = sCurrentLine;
//                LOG.info("=======================conf read:" + whiteList);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (br != null){
//                    br.close();
//                }
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
    }
}
