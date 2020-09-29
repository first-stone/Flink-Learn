package cn.stone.api;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author: cn.stone
 * @Description:
 * @Date: Created in 10:34 2020/9/22
 * @Modified By:
 */
public class DataSource {
    /**
     * 数据源
     * 1、基于文件:flink将文件读取分为两个子任务，文件监控(非并行)和文件读取(并行)
     * -a、逐行读取，作为字符串返回(readTextFile(path))
     * -b、按指定的文件输入格式一次性读取文件(readFile(fileInputFormat,path))
     * -c、readFile(fileInputFormat-读取格式,path-文件路径,watchType-监控方式,interval-监控间隔,pathFilter-文件过滤,typeInfo)
     * 2、基于socket:从套接字读取
     * 3、基于集合
     * -a、fromCollection(collection),从集合中创建数据流，所有元素必须为同一类型
     * -b、fromCollection(Iterator,Class),从迭代器创建数据流，Class指定返回的数据类型
     * -c、fromElements(T...),从对象序列创建数据流，所有对象必须为同一类型
     * -d、fromParallelCollection(SplittableIterator,Class),并行地从迭代器创建数据流，Class指定返回的数据类型
     * -e、generateSequence(from,to),并行地生成从from-to的数字序列
     * 4、
     * 5、
     * 6、
     * ...
     * n、自定义数据源
     */
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> dataSource1 = env.readTextFile("E:\\idea-data\\DataSourceTextFile.txt");
        DataStreamSource<String> dataSource2 = env.socketTextStream("localhost", 9999);

    }
}
