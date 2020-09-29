package cn.stone.stream.wordcount.wc01;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * @Author: cn.stone
 * @Description:
 * @Date: Created in 10:07 2020/9/22
 * @Modified By:
 */
public class FlatMapFunction01 implements FlatMapFunction<String, Tuple2<String, Integer>> {
    @Override
    public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
        String[] split = value.split(" ");
        for (String s : split) {
            Tuple2<String, Integer> tp2 = new Tuple2<>(s, 1);
            out.collect(tp2);
        }
    }
}
