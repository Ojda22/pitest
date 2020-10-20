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

    @Override
    public int hashCode () {
        final int prime = 31;
        int result = 1;
        result = (prime * result)
                + ((this.assertionDescription == null) ? 0 : this.assertionDescription.hashCode());
        result = (prime * result)
                + ((this.testUnitQualifiedName == null) ? 0 : this.testUnitQualifiedName.hashCode());
        result = (prime * result)
                + ((this.className == null) ? 0 : this.className.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ResultItem other = (ResultItem) obj;
        if (this.assertionDescription == null) {
            if (other.assertionDescription != null) {
                return false;
            }
        } else if (!this.assertionDescription.equalsIgnoreCase(other.assertionDescription)) {
            return false;
        }
        if (this.testUnitQualifiedName == null) {
            if (other.testUnitQualifiedName != null) {
                return false;
            }
        } else if (!this.testUnitQualifiedName.equalsIgnoreCase(other.testUnitQualifiedName)) {
            return false;
        }
        if (this.className == null) {
            if (other.className != null) {
                return false;
            }
        } else if (!this.className.equalsIgnoreCase(other.className)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ResultItem:\n");
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
