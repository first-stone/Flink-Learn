package cn.stone.api;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.IterativeStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author: cn.stone
 * @Description:
 * @Date: Created in 13:51 2020/9/22
 * @Modified By:
 */
public class IterationStream {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<Long> basicDataStream = env.generateSequence(1, 1000);
        IterativeStream<Long> iterateDS = basicDataStream.iterate();
        DataStream<Long> minusOne = iterateDS.map(new MapFunction<Long, Long>() {
            @Override
            public Long map(Long value) throws Exception {
                System.out.println(value-1);
                return value - 1;
            }
        });
        DataStream<Long> stillGreaterThanZero = minusOne.filter(new FilterFunction<Long>() {
            @Override
            public boolean filter(Long value) throws Exception {
                return value > 0;
            }
        });
        //将stillGreaterThanZero返回给迭代头
        iterateDS.closeWith(stillGreaterThanZero);
        DataStream<Long> lessThanZero = minusOne.filter(new FilterFunction<Long>() {
            @Override
            public boolean filter(Long value) throws Exception {
                return value <= 0;
            }
        });
        env.execute("IterationStream");

    }
}
