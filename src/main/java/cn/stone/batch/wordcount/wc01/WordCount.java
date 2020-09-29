package cn.stone.batch.wordcount.wc01;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.MultipleParameterTool;
import org.apache.flink.util.Preconditions;

/**
 * @Author: cn.stone
 * @Description:
 * @Date: Created in 20:14 2020/9/17
 * @Modified By:
 */
public class WordCount {
    public static void main(String[] args) throws Exception {
        MultipleParameterTool params = MultipleParameterTool.fromArgs(args);
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        env.getConfig().setGlobalJobParameters(params);
        DataSet<String> text = null;
        if (params.has("input")) {
            for (String input : params.getMultiParameterRequired("input")) {
                if (text == null) {
                    text = env.readTextFile(input);
                } else {
                    text = text.union(env.readTextFile(input));
                }
            }
            Preconditions.checkNotNull(text, "Input DataSet should not be null.");
        } else {
            System.out.println("Execution WordCount example with default input data set.");
            System.out.println("Use --input to specify file input.");
            text = WordCountData.getDefaultTextLineDataSet(env);
        }
        DataSet<Tuple2<String, Integer>> counts = text.flatMap(new FlatMapImplements01())
                .groupBy(0)
                .sum(1);
        if (params.has("output")) {
            counts.writeAsCsv(params.get("output"), "\n", " ");
            env.execute("WordCount Example");
        } else {
            System.out.println("Print result to stdout. Use --output to specify output path.");
            counts.print();
        }
    }
}
