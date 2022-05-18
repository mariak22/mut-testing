import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MutantAnalyzer {
    public static float analyze(String test_res_file_path) {
        File file = new File(test_res_file_path);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<MutantMetadata> mutantMetadata =
                    objectMapper.readValue(file, new TypeReference<List<MutantMetadata>>(){});
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
