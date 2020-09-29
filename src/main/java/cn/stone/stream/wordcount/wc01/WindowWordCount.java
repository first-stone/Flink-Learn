package cn.stone.stream.wordcount.wc01;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * @Author: cn.stone
 * @Description:
 * @Date: Created in 10:04 2020/9/22
 * @Modified By:
 */
public class WindowWordCount {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Tuple2<String, Integer>> text = env.socketTextStream("localhost", 9999)
                .flatMap(new FlatMapFunction01())
                .keyBy(0)
                .timeWindow(Time.seconds(5))
                .sum(1);
        text.print();
        env.execute("Window WordCount");
    }
}
