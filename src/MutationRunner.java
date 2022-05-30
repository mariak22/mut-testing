import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class MutationRunner {
    public static Result runTestOnMutatedFile(Optional<String> functionName, String srcFile,
                                              String mutatedFile, String tstFile) {
        JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(System.out));

        String srcClassName = Paths.get(srcFile).getFileName().toString();
        srcClassName = srcClassName.substring(0, srcClassName.lastIndexOf(".java"));

        String mutatedSrcClassName = Paths.get(mutatedFile).getFileName().toString();
        mutatedSrcClassName = mutatedSrcClassName.substring(0, mutatedSrcClassName.lastIndexOf(".java"));

        Path srcPath = Paths.get(tstFile);
        String parentPath = srcPath.getParent().toString();
        String testClassName = srcPath.getFileName().toString();
        testClassName = testClassName.substring(0, testClassName.lastIndexOf(".java"));
        String mutatedTestClassName = String.format("%s/%sMutated.java", parentPath, testClassName);

        try {
            // Read source file to mutate
            BufferedReader br = new BufferedReader(new FileReader(tstFile));
            String line = br.readLine();

            // Append lines to dest file and mutate
            BufferedWriter bw = new BufferedWriter(new FileWriter(mutatedTestClassName));

            while (line != null) {
                String mutatedLine = line;
                if (line.contains(testClassName)) {
                    mutatedLine = line.replace(testClassName, testClassName+"Mutated");
                } else if (line.contains(srcClassName)) {
                    mutatedLine = line.replace(srcClassName, mutatedSrcClassName);
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

        try {
            String mutatedTestFileName = Paths.get(mutatedTestClassName).getFileName().toString();
            mutatedTestFileName = mutatedTestFileName.substring(0, mutatedTestFileName.lastIndexOf(".java"));
            Request request;
            if (functionName.isPresent()) {
                request = Request.method(Class.forName(mutatedTestFileName), functionName.get());
            } else {
                request = Request.aClass(Class.forName(mutatedTestFileName));
            }
            Result result = junit.run(request);
            return result;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
