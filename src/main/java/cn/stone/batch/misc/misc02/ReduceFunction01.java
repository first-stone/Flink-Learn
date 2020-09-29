package cn.stone.batch.misc.misc02;

import org.apache.flink.api.common.functions.ReduceFunction;

/**
 * @Author: cn.stone
 * @Description:
 * @Date: Created in 9:51 2020/9/21
 * @Modified By:
 */
public class ReduceFunction01 implements ReduceFunction<Long> {

    public Long reduce(Long value1, Long value2) throws Exception {
        return value1 + value2;
    }
}
