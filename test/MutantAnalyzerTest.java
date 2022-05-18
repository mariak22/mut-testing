import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MutantAnalyzerTest {
    @Test(expected = IllegalArgumentException.class)
    public void malformedFileTest() throws JsonProcessingException {
        List<MutantMetadata> empty = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        MutantAnalyzer.analyze(
                "/Users/maru/Desktop/mut-testing/test/mutation_report_tests/malformed");
    }

    @Test
    public void killAllTest() throws JsonProcessingException {
        List<MutantMetadata> res = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        float score = MutantAnalyzer.analyze(
                "/Users/maru/Desktop/mut-testing/test/mutation_report_tests/mutants_all_killed");
        Assert.assertEquals(100.0, score, 1e-15);
    }
}
