package adventofcode.vo;

public class BagRule {
    private int count;
    private String child;

    public BagRule() {
    }

    public BagRule(String child, int count) {
        this.child = child;
        this.count = count;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
