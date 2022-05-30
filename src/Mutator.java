import java.io.*;
import java.nio.file.Paths;
import java.util.Set;

/**
 * The mutator component takes mutation operator, path to source code
 * to modify as input and the path to the corresponding test file.
 * The mutator then applies the necessary mutations to the source and
 * generates the mutant code in a target package. The mutator then runs
 * the given unit tests against the mutated code to generate a test output
 * in the location where the mutated code exists.
 */
public class Mutator {
    private final Set<MutantOperation> mops;
    private final String functionName;
    private final String src;
    private final String dest;

    public Mutator(Set<MutantOperation> mops, String functionName, String src, String dest) {
        this.mops = mops;
        this.functionName = functionName;
        if (new File(src).exists()) {
            this.src = src;
        } else {
            throw new IllegalArgumentException(src + " is not a valid file path");
        }
        this.dest = dest;
    }

    public Set<MutantOperation> getMops() {
        return mops;
    }

    public String getFunctionName() {
        return functionName;
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
        try {
            String className = Paths.get(this.src).getFileName().toString();
            className = className.substring(0, className.lastIndexOf(".java"));
            String mutatedClassName = Paths.get(this.dest).getFileName().toString();
            mutatedClassName = mutatedClassName.substring(0, mutatedClassName.lastIndexOf(".java"));

            // Read source file to mutate
            BufferedReader br = new BufferedReader(new FileReader(src));
            String line = br.readLine();

            // Append lines to dest file and mutate
            BufferedWriter bw = new BufferedWriter(new FileWriter(dest));

            while (line != null) {
                String mutatedLine = line;
                if (line.contains(className)) {
                    mutatedLine = line.replace(className, mutatedClassName);
                } else {
                    for (MutantOperation mop : this.mops) {
                        switch (mop) {
                            case ARITH:
                                if (line.contains(" + ")) {
                                    mutatedLine = line.replace(" + ", " - ");
                                } else if (line.contains(" - ")) {
                                    mutatedLine = line.replace(" - ", " + ");
                                } else if (line.contains(" * ")) {
                                    mutatedLine = line.replace(" * ", " / ");
                                } else if (line.contains(" / ")) {
                                    mutatedLine = line.replace(" / ", " * ");
                                }
                                break;
                            case LOGICAL:
                                if (line.contains(" & ")) {
                                    mutatedLine = line.replace(" & ", " | ");
                                } else if (line.contains(" | ")) {
                                    mutatedLine = line.replace(" | ", " & ");
                                }
                                break;
                            default:
                                throw new IllegalArgumentException("Invalid mutatation operation " + mop);
                        }
                    }
                }

                bw.write(mutatedLine);
                bw.newLine();
                line = br.readLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dest;
    }

    @Override
    public String toString() {
        return "Mutator{" +
                "mops=" + mops +
                ", functionName='" + functionName + '\'' +
                ", src='" + src + '\'' +
                ", dest='" + dest + '\'' +
                '}';
    }
}
