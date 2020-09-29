package cn.stone.batch.misc.misc02;

import org.apache.flink.api.common.functions.MapFunction;

/**
 * @Author: cn.stone
 * @Description:
 * @Date: Created in 9:48 2020/9/21
 * @Modified By:
 */
public class MapFunction01 implements MapFunction<Long, Long> {

    public Long map(Long value) throws Exception {
        double x = Math.random();
        double y = Math.random();
        return (x * x + y * y) < 1 ? 1L : 0L;
    }
}
