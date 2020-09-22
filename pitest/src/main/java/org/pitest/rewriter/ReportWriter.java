package org.pitest.rewriter;

import static org.pitest.rewriter.Tag.sourceFile;
import static org.pitest.rewriter.Tag.mutation;
import static org.pitest.rewriter.Tag.mutatedClass;
import static org.pitest.rewriter.Tag.mutatedMethod;
import static org.pitest.rewriter.Tag.methodDescription;
import static org.pitest.rewriter.Tag.lineNumber;
import static org.pitest.rewriter.Tag.mutator;
import static org.pitest.rewriter.Tag.index;
import static org.pitest.rewriter.Tag.block;
import static org.pitest.rewriter.Tag.assertion;

import org.pitest.mutationtest.engine.Location;
import org.pitest.mutationtest.engine.MethodName;
import org.pitest.mutationtest.engine.MutationDetails;
import org.pitest.util.Log;
import org.pitest.util.StringUtil;
import org.pitest.util.Unchecked;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

enum Tag {
    mutation, assertions, assertion, sourceFile, mutatedClass, mutatedMethod, methodDescription, lineNumber, mutator, index, killingTest, killingTests, succeedingTests, description, block;
}

public class ReportWriter {

    private final Writer out;
    private static final Logger LOG = Log.getLogger();

    public ReportWriter(final Writer out, MutationDetails mutationDetails, List<String> assertions) {
        this.out = out;
        runStart();
        writeReport(mutationDetails, assertions);
        runEnd();
    }

    private void write(final String value) {
        try {
            this.out.write(value);
        } catch (final IOException e) {
            throw Unchecked.translateCheckedException(e);
        }
    }

    public void runStart() {
        write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        write("<mutations>\n");
    }

    public void runEnd() {
        write("</mutations>\n");
    }

    public void openAssertions(){
        write("<assertions>\n");
    }

    public void closeAssertions(){
        write("</assertions>\n");
    }

    private void writeReport(MutationDetails mutationDetails, List<String> assertions){
        write(makeNode(makeMutationNode(mutationDetails, assertions), mutation) + "\n");
    }

    private String makeMutationNode(final MutationDetails details, final List<String> assertions) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeNode(clean(details.getFilename()), sourceFile));
        sb.append(makeNode(clean(details.getClassName().asJavaName()),mutatedClass));
        for (MethodName mn : details.getMethods()) {
            sb.append(makeNode(clean(mn.name()), mutatedMethod));
        }
        for (Location lc : details.getId().getLocations()) {
            sb.append(makeNode(clean(lc.getMethodDesc()), methodDescription));
        }
        for (Integer l : details.getLineNumbers() ) {
            sb.append(makeNode("" + l, lineNumber));
        }
        for ( String m : details.getMutators()) {
            sb.append(makeNode(clean(m), mutator));
        }
        for ( List<Integer> indexes : details.getId().getIndexesList() ) {
            sb.append(makeNode("" + indexes.get(0), index));
        }
        for (Integer b : details.getBlocks()) {
            sb.append(makeNode("" + b, block));
        }
        // (VERY) Ugly parsing --> Add all in ResultItem Class
        for (String asserT : assertions){
            if (asserT.contains(Serializer.EXP) && asserT.contains(Serializer.STRACE)){
                String [] asserTContext = asserT.split("\\[STACKTRACE\\]");
                String sTrace = "";
                if (asserTContext.length > 1) {
                    sTrace = asserTContext[1].substring(1);
                }
                String[] assertIdentification = asserTContext[0].split(" ");
                if (assertIdentification.length >= 3) {
                    String exc = assertIdentification[0];
                    String testName = assertIdentification[1];
                    String assertValue = assertIdentification[2];
                    StringBuffer exceptionValue = new StringBuffer();
                    for (int i = 3; i < assertIdentification.length; i++) {
                        exceptionValue.append(assertIdentification[i] + " ");
                    }
                    String nodeValue = exceptionValue.toString().substring(0, exceptionValue.length() - 1) + Serializer.SEP + Serializer.STRACE + Serializer.SEP + sTrace;
                    sb.append(makeNode(clean(nodeValue), makeAssertionAttribues(testName, assertValue, exc), assertion));
                }else{
                    sb.append(makeNode(clean(Arrays.asList(asserTContext).stream().collect(Collectors.joining(" "))), assertion));
                }
            }else{
                String[] asserTContext = asserT.split(Serializer.SEP);
                if (asserTContext.length == 4){
                    String assertId = asserTContext[0];
                    String testName = asserTContext[1];
                    String value = asserTContext[2];
                    String content = asserTContext[3];
                    sb.append(makeNode(content, makeAssertionAttribues(value, testName, assertId), assertion));
                }else {
                    String assertId = asserTContext[0];
                    String value = asserTContext[1];
                    String content = asserTContext[2];
                    sb.append(makeNode(content, makeAssertionAttribues(value, assertId), assertion));
                }
            }
        }
        return sb.toString();
    }

    private String makeAssertionAttribues(String value, String testName, String assertID){
        return "testName='" + testName + "' assertValue='" + value + "' assertID='" + assertID + "'";
    }

    private String makeAssertionAttribues(String value, String assertID){
        return "assertValue='" + value + "' assertID=" + assertID + "'";
    }

    public void writeAssertion(ResultItem resultItem){
        write(makeNode(resultItem.getAssertionContent(), makeAssertAttributes(resultItem), assertion) + "\n");
    }

    private String makeAssertAttributes(ResultItem resultItem){
        return "testQualifiedName=" + resultItem.getMethodUnitQualifiedName() + " testCaseName=" + resultItem.getTestCaseName() + " testCaseDescription="
                + resultItem.getTestDescription() + " assertionResult=" + resultItem.getAssertion() + "assertMethod=" + resultItem.getAssertMethod() + " lineNumber=" + resultItem.getLineNumber();
    }

    private String makeNode(final String value, final Tag tag) {
        if (value != null) {
            return "<" + tag + ">" + value + "</" + tag + ">";
        } else {
            return "<" + tag + "/>";
        }
    }

    private String makeNode(final String value, final String attributes, final Tag tag) {
        if (value != null) {
            return "<" + tag + " " + attributes + ">" + value + "</" + tag + ">";
        } else {
            return "<" + tag + attributes + "/>";
        }

    }

    private String clean(final String value) {
        return StringUtil.escapeBasicHtmlChars(value);
    }



}
