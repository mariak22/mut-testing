import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Logic to generate mutation score. The function expects the
 * content of the mutation reports to be de-serializable to
 * MutantMetadata class and errors out if the file is empty.
 */
public class MutantAnalyzer {
    public static float analyze(String test_res_file_path) {
        File file = new File(test_res_file_path);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<MutantMetadata> mutantMetadata =
                    objectMapper.readValue(file, new TypeReference<>(){});
            int killedMutants = 0;
            for (MutantMetadata meta : mutantMetadata) {
                if (meta.isKilled()) {
                    killedMutants++;
                }
            }
            int mutants = mutantMetadata.size();
            if (mutants == 0) {
                throw new IllegalArgumentException("Invalid mutation testing result file.");
            }
            float score = (killedMutants / (float) mutants) * 100;
            return score;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Invalid mutation testing result.");
    }
}
