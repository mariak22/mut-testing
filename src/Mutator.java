import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.Result;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Optional;
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
    private final MutantOperation mop;
    private final String src;
    private final String tst;
    private final Optional<String> tstCase;

    public Mutator(MutantOperation mop, String src, String tst, Optional<String> tstCase) {
        this.mop = mop;
        if (new File(src).exists()) {
            this.src = src;
        } else {
            throw new IllegalArgumentException(src + " is not a valid file path");
        }
        this.tst = tst;
        this.tstCase = tstCase;
    }

    public MutantOperation getMop() {
        return mop;
    }


    public String getSrc() {
        return src;
    }

    public String getTst() {
        return tst;
    }

    // Function that mutates the source code and returns the
    // file path of the mutated source code to the caller.
    public Float mutate() {
        Path srcPath = Paths.get(this.src);
        String parentPath = srcPath.getParent().toString();
        String className = srcPath.getFileName().toString();
        className = className.substring(0, className.lastIndexOf(".java"));

        String dest = String.format("%s/%sMutated.java", parentPath, className);
        String mutatedClassName = Paths.get(dest).getFileName().toString();
        mutatedClassName = mutatedClassName.substring(0, mutatedClassName.lastIndexOf(".java"));

        try {
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
                    switch (this.mop) {
                        case ARITH:
                            StringBuilder sb = new StringBuilder(line);
                            for (int i = 0; i < line.length(); ++i) {
                                if (line.charAt(i) == '+') {
                                    sb.setCharAt(i, '-');
                                } else if (line.charAt(i) == '-') {
                                    sb.setCharAt(i, '+');
                                } else if (line.charAt(i) == '*') {
                                    sb.setCharAt(i, '/');
                                } else if (line.charAt(i) == '/') {
                                    sb.setCharAt(i, '*');
                                }
                            }
                            mutatedLine = sb.toString();
                            break;
                        case LOGICAL:
                            sb = new StringBuilder(line);
                            for (int i = 0; i < line.length(); ++i) {
                                if (line.charAt(i) == '&') {
                                    sb.setCharAt(i, '|');
                                } else if (line.charAt(i) == '|') {
                                    sb.setCharAt(i, '&');
                                }
                            }
                            mutatedLine = sb.toString();
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid mutation operation " + mop);
                    }
                }

                bw.write(mutatedLine);
                bw.newLine();
                line = br.readLine();
            }
            bw.flush();
            bw.close();

            Result result = MutationRunner.runTestOnMutatedFile(this.tstCase, this.src, dest, this.tst);
            boolean is_mutant_killed = result.getFailureCount() > 0 ? true : false;
            MutantMetadata mm = new MutantMetadata(is_mutant_killed, this.mop);
            ObjectMapper objectMapper = new ObjectMapper();
            String resultFile = "src/ResultFile.json";
            objectMapper.writeValue(new File(resultFile), Collections.singletonList(mm));

            float score = MutantAnalyzer.analyze(resultFile);
            return score;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String toString() {
        return "Mutator{" +
                "mop=" + mop +
                ", src='" + src + '\'' +
                ", tst='" + tst + '\'' +
                '}';
    }
}
