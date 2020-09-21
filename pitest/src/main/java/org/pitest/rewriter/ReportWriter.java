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
import org.pitest.util.StringUtil;
import org.pitest.util.Unchecked;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

enum Tag {
    mutation, assertions, assertion, sourceFile, mutatedClass, mutatedMethod, methodDescription, lineNumber, mutator, index, killingTest, killingTests, succeedingTests, description, block;
}

public class ReportWriter {

    private final Writer out;

    public ReportWriter(final Writer out, MutationDetails mutationDetails) {
        this.out = out;
        runStart();
        writeReport(mutationDetails);
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
        try {
            write("</mutations>\n");
            this.out.close();
            this.out.flush();
        } catch (final IOException e) {
            throw Unchecked.translateCheckedException(e);
        }
    }

    public void openAssertions(){
        write("<assertions>\n");
    }

    public void closeAssertions(){
        write("</assertions>\n");
    }

    private void writeReport(MutationDetails mutationDetails){
        write(makeNode(makeMutationNode(mutationDetails), mutation) + "\n");
    }

    private String makeMutationNode(final MutationDetails details) {
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
        return sb.toString();
    }

    public void writeAssertion(ResultItem resultItem){
        write(makeNode(resultItem.getAssertionContent(), makeAssertAttributes(resultItem), assertion) + "\n");
    }

    private String makeAssertAttributes(ResultItem resultItem){
        return "testQualifiedName='" + resultItem.getMethodUnitQualifiedName() + "' testCaseName='" + resultItem.getTestCaseName() + "' testCaseDescription='"
                + resultItem.getTestDescription() + "' assertionResult=" + resultItem.getAssertion() + "assertMethod='" + resultItem.getAssertMethod() + "' lineNumber=" + resultItem.getLineNumber();
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
