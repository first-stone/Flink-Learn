package cn.stone.batch.wordcount.wc02;


import cn.stone.batch.wordcount.wc01.WordCountData;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.ReduceOperator;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.core.fs.FileSystem;

/**
 * @Author: cn.stone
 * @Description:
 * @Date: Created in 20:41 2020/9/17
 * @Modified By:
 */
public class WordCount {
    public static void main(String[] args) throws Exception {
        ParameterTool params = ParameterTool.fromArgs(args);
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        env.getConfig().setGlobalJobParameters(params);
        DataSet<String> text = null;
        if (params.has("input")) {
            text = env.readTextFile(params.get("input"));
        } else {
            System.out.println("Execution WordCount example with default input data set.");
            System.out.println("Use --input to specify file input");
            text = WordCountData.getDefaultTextLineDataSet(env);
        }
        /*DataSet<Tuple2<String, Integer>> word = text.flatMap(new FlatMapImplements01())
                .groupBy("word")
                .reduce(new ReduceFunction<Tuple2<String, Integer>>() {
                    public Tuple2<String, Integer> reduce(Tuple2<String, Integer> value1, Tuple2<String, Integer> value2) throws Exception {
                        return new Tuple2<String, Integer>(value1.f0, value1.f1 + value2.f1);
                    }
                });*/
        DataSet<Word> counts = text.flatMap(new FlatMapImplements01())
                .groupBy("word")
                .reduce(new ReduceFunction<Word>() {
                    public Word reduce(Word value1, Word value2) throws Exception {
                        return new Word(value1.word, value1.frequency + value2.frequency);
                    }
                });
        if (params.has("output")) {
            counts.writeAsText(params.get("output"), FileSystem.WriteMode.OVERWRITE);
            env.execute("WordCount-Pojo Example");
        } else {
            System.out.println("Printing result to stdout. Use --output to specify output path.");
            counts.print();
        }

    }
}
