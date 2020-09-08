package org.pitest.rewriter;

public class ResultItem {

    public boolean assertion;
    public Integer id;

    public ResultItem(boolean assertion, Integer id) {
        this.assertion = assertion;
        this.id = id;
    }
}
