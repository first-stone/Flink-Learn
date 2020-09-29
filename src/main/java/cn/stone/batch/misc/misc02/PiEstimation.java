package cn.stone.batch.misc.misc02;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.ReduceOperator;

/**
 * @Author: cn.stone
 * @Description:
 * @Date: Created in 9:44 2020/9/21
 * @Modified By:
 */
@SuppressWarnings("serial")
public class PiEstimation {
    public static void main(String[] args) throws Exception {
        final long numberSamples = args.length > 0 ? Long.parseLong(args[0]) : 1000000;
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        DataSet<Long> reduceDataSet = env.generateSequence(1, numberSamples)
                .map(new MapFunction01())
                .reduce(new ReduceFunction01());
        Long aCount = reduceDataSet.collect().get(0);
        System.out.println("We estimate Pi to be:" + (aCount * 4.0 / numberSamples));

    }
}
