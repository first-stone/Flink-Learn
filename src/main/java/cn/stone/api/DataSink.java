package cn.stone.api;

import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author: cn.stone
 * @Description:
 * @Date: Created in 11:15 2020/9/22
 * @Modified By:
 */
public class DataSink {
    /**
     * 数据下沉flink-connector-filesystem
     * 1、输出到文件
     * -a、writeAsText(path,writeMode),输出为Text文件,指定输出模式
     * -b、writeAsCsv(path,writeMode,rowDelimiter,fieldDelimiter),输出为CSV文件,指定输出模式,行分隔符,字段分隔符
     * -c、writeUsingOutputFormat(outputFormat),自定义文件输出
     * 2、输出到控制台
     * -a、print(),标准输出到控制台
     * -b、printToErr(),错误输出到控制台
     * 3、输出到套接字
     * -a、writeToSocket(host,port,schema)
     *
     *
     *
     *
     * ...
     * n、addSink(),自定义输出
     */
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> basicDataSource = env.socketTextStream("localhost", 9999);
        basicDataSource.writeAsText("path", FileSystem.WriteMode.OVERWRITE);
        basicDataSource.writeAsCsv("path", FileSystem.WriteMode.OVERWRITE, " ", "\n");

    }
}
