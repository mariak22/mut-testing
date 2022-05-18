public class MutantMetadata {
    private boolean killed;
    private MutantOperation mop;
    private int line_failed;

    public MutantMetadata() {}

    public MutantMetadata(boolean killed, MutantOperation mop, int line_failed) {
        this.killed = killed;
        this.mop = mop;
        this.line_failed = line_failed;
    }

    public boolean isKilled() {
        return killed;
    }

    public MutantOperation getMop() {
        return mop;
    }

    public int getLine_failed() {
        return line_failed;
    }

    @Override
    public String toString() {
        return "MutantMetadata{" +
                "killed=" + killed +
                ", mop=" + mop +
                ", line_failed=" + line_failed +
                '}';
    }
}
