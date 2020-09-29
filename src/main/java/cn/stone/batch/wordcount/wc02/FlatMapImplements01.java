package cn.stone.batch.wordcount.wc02;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

/**
 * @Author: cn.stone
 * @Description:
 * @Date: Created in 1:00 2020/9/19
 * @Modified By:
 */
public class FlatMapImplements01 implements FlatMapFunction<String, Word> {
    public void flatMap(String value, Collector<Word> out) throws Exception {
        String[] splits = value.toLowerCase().split("\\W+");
        for (String split : splits) {
            if (split.length() > 0) {
                out.collect(new Word(split, 1));
            }
        }
    }
}
