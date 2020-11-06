package org.pitest.rewriter;

import org.pitest.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

public class ClassTransformer implements ClassFileTransformer {

    private static final Logger LOG = Log.getLogger();

    //make white list automatic
    private String whiteList = null;

    private static Map<String,String> configuration;

    static {
        configuration = readConfig();
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

//        LOG.info("ClassName: " + className);
        try {
            if (className == null){
                return classfileBuffer;
            }

//            if (className.contains("ESTest")) {
//                LOG.info("EVOSUITE CLASS CLASSLOADER: " + loader + " - " +  ClassLoader.getSystemClassLoader());
//            }

            if (loader != ClassLoader.getSystemClassLoader()){
                return classfileBuffer;
            }

            if (this.whiteList == null || !className.startsWith(this.whiteList)){
                return classfileBuffer;
            }

//            if (className.contains("ESTest")){
//                LOG.info("EVOSUITE CLASS PASSING WHITE LIST CHECK");
//            }

            byte[] result = classfileBuffer;
            ClassReader reader = new ClassReader(classfileBuffer);
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
//            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            ClassVisitor cv = writer;

            cv = new NegatorClassAdapter(cv);
//            reader.accept(cv, ClassReader.SKIP_FRAMES);
            reader.accept(cv, ClassReader.EXPAND_FRAMES);
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
        this.whiteList = readConfig().get("targetProject");
        configuration = readConfig();
    }

    public static Map<String,String> readConfig() {
        BufferedReader br = null;

        Map<String, String> confMap = new HashMap<>();
        try {
            List<String> lines;
            br = new BufferedReader(new FileReader("prefix.conf"));
            lines = br.lines().collect(Collectors.toList());
            for (String line : lines) {
                if (line.contains("=")) {
                    String key = line.split("=")[0];
                    String value = line.split("=")[1];
                    confMap.put(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return confMap;
    }

    public static Map<String, String> getConfiguration() {
        return configuration;
    }

}
