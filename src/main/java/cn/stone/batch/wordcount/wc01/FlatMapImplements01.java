package cn.stone.batch.wordcount.wc01;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * @Author: cn.stone
 * @Description:
 * @Date: Created in 20:31 2020/9/17
 * @Modified By:
 */
public class FlatMapImplements01 implements FlatMapFunction<String, Tuple2<String, Integer>> {
    public void flatMap(String input, Collector<Tuple2<String, Integer>> output) throws Exception {
        String[] splits = input.toLowerCase().split("\\W+");
        for (String split : splits) {
            if (split.length() > 0) {
                output.collect(new Tuple2<String, Integer>(split, 1));
            }
        }
    }
}
