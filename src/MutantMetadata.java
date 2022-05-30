public class MutantMetadata {
    private boolean killed;
    private MutantOperation mop;

    public MutantMetadata() {}

    public MutantMetadata(boolean killed, MutantOperation mop) {
        this.killed = killed;
        this.mop = mop;
    }

    public boolean isKilled() {
        return killed;
    }

    public MutantOperation getMop() {
        return mop;
    }

    @Override
    public String toString() {
        return "MutantMetadata{" +
                "killed=" + killed +
                ", mop=" + mop +
                '}';
    }
}
