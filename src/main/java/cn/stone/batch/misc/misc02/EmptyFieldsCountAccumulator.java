package cn.stone.batch.misc.misc02;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.accumulators.Accumulator;
import org.apache.flink.api.common.functions.RichFilterFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author: cn.stone
 * @Description:
 * @Date: Created in 14:18 2020/9/21
 * @Modified By:
 */
public class EmptyFieldsCountAccumulator {
    private static final String EMPTY_FIELD_ACCUMULATOR = "empty-fields";

    public static void main(String[] args) throws Exception {
        final ParameterTool params = ParameterTool.fromArgs(args);
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        env.getConfig().setGlobalJobParameters(params);
        final DataSet<StringTriple> file = getDataSet(env, params);
        final DataSet<StringTriple> filteredLines = file.filter(new EmptyFieldFilter());
        JobExecutionResult result = null;
        if (params.has("output")) {
            filteredLines.writeAsCsv(params.get("output"));
            env.execute("Accumulator example");
        } else {
            System.out.println("Printing result to stdout. Use --output to specify output path.");
            filteredLines.print();
            result = env.getLastJobExecutionResult();
        }
        final List<Integer> emptyFields = result.getAccumulatorResult(EMPTY_FIELD_ACCUMULATOR);
        System.out.format("Number of detected empty fields per column: %s\n", emptyFields);
    }

    private static DataSet<StringTriple> getDataSet(ExecutionEnvironment env, ParameterTool params) {
        if (params.has("input")) {
            return env.readCsvFile(params.get("input"))
                    .fieldDelimiter(";")
                    .pojoType(StringTriple.class);
        } else {
            System.out.println("Executing EmptyFieldsCountAccumulator example with default input data set.");
            System.out.println("Use --input to specify file input.");
            return env.fromCollection(getExampleInputTuple());
        }
    }

    private static Collection<StringTriple> getExampleInputTuple() {
        Collection<StringTriple> inputTuples = new ArrayList<>();
        inputTuples.add(new StringTriple("John", "Doe", "Foo Str."));
        inputTuples.add(new StringTriple("Joe", "Johnson", ""));
        inputTuples.add(new StringTriple(null, "Kate Morn", "Bar Blvd."));
        inputTuples.add(new StringTriple("Tim", "Rinny", ""));
        inputTuples.add(new StringTriple("Alicia", "Jackson", "  "));
        return inputTuples;
    }

    public static class EmptyFieldFilter extends RichFilterFunction<StringTriple> {
        private final VectorAccumulator emptyFieldCounter = new VectorAccumulator();

        @Override
        public void open(Configuration parameters) throws Exception {
            super.open(parameters);
            getRuntimeContext().addAccumulator(EMPTY_FIELD_ACCUMULATOR, this.emptyFieldCounter);
        }

        @Override
        public boolean filter(StringTriple value) throws Exception {
            boolean containsEmptyFields = false;
            for (int pos = 0; pos < value.getArity(); pos++) {
                final String field = value.getField(pos);
                if (field == null || field.trim().isEmpty()) {
                    containsEmptyFields = true;
                    this.emptyFieldCounter.add(pos);
                }
            }
            return !containsEmptyFields;
        }
    }

    public static class VectorAccumulator implements Accumulator<Integer, ArrayList<Integer>> {

        private final ArrayList<Integer> resultVector;

        private VectorAccumulator() {
            this(new ArrayList<Integer>());
        }

        public VectorAccumulator(ArrayList<Integer> resultVector) {
            this.resultVector = resultVector;
        }

        @Override
        public void add(Integer value) {
            updateResultVector(value, 1);
        }

        private void updateResultVector(int position, int delta) {
            while (this.resultVector.size() <= position) {
                this.resultVector.add(0);
            }
            final int component = this.resultVector.get(position);
            this.resultVector.set(position, component + delta);
        }

        @Override
        public ArrayList<Integer> getLocalValue() {
            return this.resultVector;
        }

        @Override
        public void resetLocal() {
            this.resultVector.clear();
        }

        @Override
        public void merge(Accumulator<Integer, ArrayList<Integer>> other) {
            ArrayList<Integer> otherVector = other.getLocalValue();
            for (int index = 0; index < otherVector.size(); index++) {
                updateResultVector(index, otherVector.get(index));
            }
        }

        @Override
        public Accumulator<Integer, ArrayList<Integer>> clone() {
            return new VectorAccumulator(new ArrayList<Integer>(resultVector));
        }

        @Override
        public String toString() {
            return StringUtils.join(resultVector, ',');
        }
    }

    public static class StringTriple extends Tuple3<String, String, String> {
        public StringTriple() {

        }

        public StringTriple(String f0, String f1, String f2) {
            super(f0, f1, f2);
        }
    }
}
