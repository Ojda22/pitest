package org.pitest.rewriter;

public class ResultItem {

    public Boolean assertion;
    public String id;

    public ResultItem(boolean assertion, String id) {
        this.assertion = assertion;
        this.id = id;
    }

    @Override
    public int hashCode () {
        return id.hashCode() + 31*(assertion ? 1 : 0);
    }

    @Override
    public boolean equals(Object obj) {
        ResultItem item = (ResultItem)obj;
        if ((this == item) || (this.assertion == item.assertion && this.id.equals(item.id))
        )			return true;
        return false;
    }
}
