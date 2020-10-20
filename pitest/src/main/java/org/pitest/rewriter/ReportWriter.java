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
import java.util.List;
import java.util.logging.Logger;

enum Tag {
    mutation, assertions, assertion, sourceFile, mutatedClass, mutatedMethod, methodDescription, lineNumber, mutator, index, killingTest, killingTests, succeedingTests, description, block;
}

public class ReportWriter {

    private final Writer out;
    private static final Logger LOG = Log.getLogger();

    public ReportWriter(final Writer out) {
        this.out = out;
    }

    public ReportWriter(final Writer out, MutationDetails mutationDetails, List<ResultItem> assertions) {
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

    public void runBody(MutationDetails mutationDetails, List<ResultItem> assertions){
        writeReport(mutationDetails, assertions);
    }

    public void openAssertions(){
        write("<assertions>\n");
    }

    public void closeAssertions(){
        write("</assertions>\n");
    }

    private void writeReport(MutationDetails mutationDetails, List<ResultItem> assertions){
        write(makeNode(makeMutationNode(mutationDetails, assertions), makeStatusAttribute(assertions), mutation) + "\n");
    }

    private String makeMutationNode(final MutationDetails details, final List<ResultItem> assertions) {
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

        for (ResultItem resultItem : assertions){
//            LOG.info("Assertion: " + resultItem);
            if (resultItem.getAssertionDescription().equals(Serializer.EXP)){
                String nodeValue = resultItem.getExceptionName() + Serializer.SEP + Serializer.STRACE + Serializer.SEP + resultItem.getStackTrace();
                sb.append(makeNode(clean(nodeValue), makeAssertionAttribues(resultItem.getAssertionValue().toString(), resultItem.getTestUnitQualifiedName(), resultItem.getAssertionDescription()), assertion));
            }else{
                sb.append(makeNode(resultItem.getAssertionContent(), makeAssertionAttribues(resultItem.getAssertionValue().toString(),resultItem.getTestUnitQualifiedName(), resultItem.getAssertionDescription()), assertion));
                }
            }
        return sb.toString();
    }

    private String makeAssertionAttribues(String value, String testName, String assertID){
        return "testName='" + testName + "' assertValue='" + value + "' assertID='" + assertID + "'";
    }

    private String makeStatusAttribute(List<ResultItem> assertions){
        String status = assertions.stream().anyMatch(n -> n.getAssertionValue() == false) ? "KILLED" : "SURVIVED";
        return "status='" + status + "'";
    }

    private String makeAssertionAttribues(String value, String assertID){
        return "testName='" + value + "' assertID=" + assertID + "'";
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
