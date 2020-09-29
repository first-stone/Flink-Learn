package cn.stone.api;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.ProcessJoinFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

/**
 * @Author: cn.stone
 * @Description:
 * @Date: Created in 17:00 2020/9/22
 * @Modified By:
 */
public class Transformation {
    public static void main(String[] args) throws Exception {
        /**转换算子
         * map(),输入一个元素，输出一个元素(DataStream->DataStream)
         * flatMap(),输入一个元素，输出0个,1个或多个元素(DataStream->DataStream)
         * filter(),输入一个元素，输出0个或多个元素(DataStream->DataStream)
         * keyBy(),根据相同key的记录对数据进行划分(DataStream->KeyedStream)
         * 以下情况的元素不能作为key使用：
         * 1、POJO类型，但没有重写hashCode()，而是依赖Object.hashCode()。
         * 2、该元素是数组类型。
         * reduce(),将key相同的数据进行滚动合并,(keyedStream->DataStream)
         * aggregations(),滚动聚合具有相同key的流元素,包括sum(key)-将相同key的值进行求和、min(key)-返回指定字段的最小值、minBy(key)-返回指定字段最小值所在的元素、max(key)-同min、maxBy(key)-同minBy,(keyedStream->DataStream)
         * window(),对已经分区的keyedStream定义窗口,通过指定规则对key进行分组,(KeyedStream->WindowedStream)
         * windowAll(),对常规的DataStream定义窗口,数据会收集到一个task(DataStream->AllWindowedStream)
         * WindowApply(),对整个窗口使用函数,(WindowedStream->DataStream,AllWindowedStream->DataStream)
         * Union(),联合两个或多个DataStream,如果联合自身则每条记录出现两次(DataStream...->DataStream)
         * Window Join(),在指定key的公共窗口上连接两个数据流,(DataStream,DataStream->DataStream)
         * IntervalJoin(),对指定的时间间隔内使用公共key连接两个KeyedStream(KeyedStream,KeyedStream->DataStream)
         * Window CoGroup()
         * Connect(),连接两个DataStream,可以共享状态,(DataStream,DataStream->ConnectedDataStreams)
         * CoFlatMap()和CoMap(),对ConnectedDataStreams执行类似map或者flatMap的操作,(ConnectedDataStreams->DataStream)
         * Split(),将一个DataStream根据某些规则切分为两个或多个流,(DataStream->splitStream)
         * select(),对切分后的splitStream进行划分,(splitStream->DataStream)
         * assignTimestamps(),注册时间,(DataStream->DataStream)
         * Iterate(),用以获取IterativeStream,(DataStream->IterativeStream)
         * project(),对元组类型的DataStream提取子元组(DataStream,DataStream)
         * partitionCustom(),通过自定义的分区函数对key进行分区,只支持单分区(DataStream->DataStream)
         * shuffle(),将数据随机分区
         * rebalance(),以轮询的方式为每个分区均衡地分配数据,(DataStream->DataStream)
         * broadCast(),将DataStream广播向每个分区,(DataStream->DataStream)
         * rescale(),根据上下游task进行分区,(DataStream->DataStream)
         *
         */
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Long> basicDataStream = env.generateSequence(1, 1000);
        DataStream<Long> mapDataStream = basicDataStream.map(new MapFunction<Long, Long>() {
            @Override
            public Long map(Long value) throws Exception {
                return value + 1;
            }
        });
        DataStream<Long> filterDataStream = mapDataStream.filter(new FilterFunction<Long>() {
            @Override
            public boolean filter(Long value) throws Exception {
                return value % 2 == 0 ? true : false;
            }
        });
        KeyedStream<Long, Long> keyDataStream = filterDataStream.keyBy(new KeySelector<Long, Long>() {
            @Override
            public Long getKey(Long value) throws Exception {
                return value % 3 == 0 ? 1L : 2L;
            }
        });
        /*DataStream<Long> reduceDataStream = keyDataStream.reduce(new ReduceFunction<Long>() {
            @Override
            public Long reduce(Long value1, Long value2) throws Exception {
                return value1 + value2;
            }
        });
        reduceDataStream.print();*/
        /*DataStream<Long> minDataStream = keyDataStream.min(0);
        minDataStream.print();*/
        /*DataStream<Long> minByDataStream = keyDataStream.minBy(0);
        minByDataStream.print();*/
        //WindowedStream<Long, Long, TimeWindow> windowDataStream = keyDataStream.window(TumblingProcessingTimeWindows.of(Time.seconds(2)));


        env.execute("TransformationAPI");


    }
}
