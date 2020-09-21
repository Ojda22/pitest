package org.pitest.rewriter;

public class ResultItem {

    private Boolean assertion;
    private String assertionContent;

    private String className;
    private String testCaseName;
    private String testDescription;
    private String lineNumber;
    private String assertMethod;
    private String methodUnitQualifiedName;

    public ResultItem(Boolean assertion, String assertionContent, String className, String testCaseName, String testDescription, String lineNumber, String assertMethod, String methodUnitQualifiedName) {
        this.assertion = assertion;
        this.assertionContent = assertionContent;
        this.className = className;
        this.testCaseName = testCaseName;
        this.testDescription = testDescription;
        this.lineNumber = lineNumber;
        this.assertMethod = assertMethod;
        this.methodUnitQualifiedName = methodUnitQualifiedName;
    }

    public ResultItem(){
    }

    @Override
    public int hashCode () {
        return assertionContent.hashCode() + 31*(assertion ? 1 : 0) + className.hashCode() + testCaseName.hashCode() + testDescription.hashCode()
                + lineNumber.hashCode() + assertMethod.hashCode() + methodUnitQualifiedName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        ResultItem item = (ResultItem) obj;
        if ((this == item) || (this.assertion == item.assertion && this.assertionContent.equals(item.assertionContent)
        && this.className.equals(item.className) && this.testCaseName.equals(item.testCaseName) && this.testDescription.equals(item.testDescription)
        && this.lineNumber.equals(item.lineNumber) && this.assertMethod.equals(item.assertMethod) && this.methodUnitQualifiedName.equals(item.methodUnitQualifiedName))) {
            return true;
        }
        return false;
    }

    public Boolean getAssertion() {
        return assertion;
    }

    public void setAssertion(Boolean assertion) {
        this.assertion = assertion;
    }

    public String getAssertionContent() {
        return assertionContent;
    }

    public void setAssertionContent(String assertionContent) {
        this.assertionContent = assertionContent;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTestCaseName() {
        return testCaseName;
    }

    public void setTestCaseName(String testCaseName) {
        this.testCaseName = testCaseName;
    }

    public String getTestDescription() {
        return testDescription;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getAssertMethod() {
        return assertMethod;
    }

    public void setAssertMethod(String assertMethod) {
        this.assertMethod = assertMethod;
    }

    public String getMethodUnitQualifiedName() {
        return methodUnitQualifiedName;
    }

    public void setMethodUnitQualifiedName(String methodUnitQualifiedName) {
        this.methodUnitQualifiedName = methodUnitQualifiedName;
    }
}
