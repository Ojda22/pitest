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

    private Map<String,String> configuration = null;

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
        this.whiteList = readConfig().get("targetProject");
        LOG.info("READ CONFIG: ");
        LOG.info(readConfig().get("targetProject"));
        LOG.info(readConfig().get("assertionCache"));
        LOG.info(readConfig().get("coverageCache"));
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

    public Map<String, String> getConfiguration() {
        return configuration;
    }

}
