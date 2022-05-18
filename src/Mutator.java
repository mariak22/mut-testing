/**
 * The mutator component takes mutation operator, path to source code
 * to modify as input and the path to the corresponding test file.
 * The mutator then applies the necessary mutations to the source and
 * generates the mutant code in a target package. The mutator then runs
 * the given unit tests against the mutated code to generate a test output
 * in the location where the mutated code exists.
 */
public class Mutator {
    private final MutantOperation mop;
    private final String src;
    private final String dest;

    public Mutator(MutantOperation mop, String src, String dest) {
        this.mop = mop;
        this.src = src;
        this.dest = dest;
    }

    public MutantOperation getMop() {
        return mop;
    }

    public String getSrc() {
        return src;
    }

    public String getDest() {
        return dest;
    }

    // Function that mutates the source code and returns the
    // path of the mutation test report file to the caller.
    public String mutate() {
        throw new IllegalStateException("Unimplemented function");
    }

    @Override
    public String toString() {
        return "Mutator{" +
                "mop=" + mop +
                ", src='" + src + '\'' +
                ", dest='" + dest + '\'' +
                '}';
    }
}
