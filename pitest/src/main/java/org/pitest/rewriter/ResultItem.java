package org.pitest.rewriter;

public class ResultItem {

    private Boolean assertionValue;
    private String assertionContent;
    private String assertionDescription;
    private String testUnitQualifiedName;

    // assertionDescription consists of those fields, seperated by ":"
    private String className;
    private String testCaseName;
    private String testCaseMethodDescription;
    private String assertionLineNumber;
    private String assertionMethod;

    // in case if assertionDescription is exception
    private String exceptionName;
    private String stackTrace;

    public ResultItem(){
    }

    @Override
    public int hashCode () {
        return assertionContent.hashCode() + 31*(assertionValue ? 1 : 0) + className.hashCode() + testCaseName.hashCode() + testCaseMethodDescription.hashCode()
                + assertionLineNumber.hashCode() + assertionMethod.hashCode() + testUnitQualifiedName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        ResultItem item = (ResultItem) obj;
        if ((this == item) || (this.assertionValue == item.assertionValue && this.assertionContent.equals(item.assertionContent)
        && this.testUnitQualifiedName.equals(item.testUnitQualifiedName) && this.className.equals(item.className) && this.testCaseName.equals(item.testCaseName)
        && this.testCaseMethodDescription.equals(item.testCaseMethodDescription) && this.assertionLineNumber.equals(item.assertionLineNumber) && this.assertionMethod.equals(item.assertionMethod))) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (assertionDescription.contains(Serializer.EXP)){
            stringBuilder.append("AssertionDescription: " + assertionDescription + "\n");
            stringBuilder.append("AssertionValue: " + assertionValue + "\n");
            stringBuilder.append("ExceptionName: " + exceptionName + "\n");
            stringBuilder.append("StackTrace: " + stackTrace + "\n");
            stringBuilder.append("AssertionContent: " + assertionContent + "\n");
            stringBuilder.append("TestCaseName: " + testUnitQualifiedName + "\n");
        }else {
            stringBuilder.append("AssertionDescription: " + assertionDescription + " (ClassName: " + className + ", TestClassName:" + testCaseName + ", TestCaseMethodDescription: " + testCaseMethodDescription
                    + ", AssertionLineNumber: " + assertionLineNumber + ", AssertionMethod: " + assertionMethod + ") \n");
            stringBuilder.append("AssertionContent: " + assertionContent + "\n");
            stringBuilder.append("TestUnifiedName: " + testUnitQualifiedName + "\n");
            stringBuilder.append("AssertionValue: " + assertionValue.booleanValue());
        }
        return stringBuilder.toString();
    }

    public String getTestUnitQualifiedName() {
        return testUnitQualifiedName;
    }

    public void setTestUnitQualifiedName(String testUnitQualifiedName) {
        this.testUnitQualifiedName = testUnitQualifiedName;
    }

    public String getAssertionContent() {
        return assertionContent;
    }

    public void setAssertionContent(String assertionContent) {
        this.assertionContent = assertionContent;
    }

    public Boolean getAssertionValue() {
        return assertionValue;
    }

    public void setAssertionValue(Boolean assertionValue) {
        this.assertionValue = assertionValue;
    }

    public String getAssertionDescription() {
        return assertionDescription;
    }

    public void setAssertionDescription(String assertionDescription) {
        this.assertionDescription = assertionDescription;
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

    public String getTestCaseMethodDescription() {
        return testCaseMethodDescription;
    }

    public void setTestCaseMethodDescription(String testCaseMethodDescription) {
        this.testCaseMethodDescription = testCaseMethodDescription;
    }

    public String getAssertionLineNumber() {
        return assertionLineNumber;
    }

    public void setAssertionLineNumber(String assertionLineNumber) {
        this.assertionLineNumber = assertionLineNumber;
    }

    public String getAssertionMethod() {
        return assertionMethod;
    }

    public void setAssertionMethod(String assertionMethod) {
        this.assertionMethod = assertionMethod;
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

}
