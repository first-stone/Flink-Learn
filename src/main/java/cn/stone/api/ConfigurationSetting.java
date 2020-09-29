package cn.stone.api;

import org.apache.flink.api.common.JobID;
import org.apache.flink.api.common.restartstrategy.RestartStrategies.RestartStrategyConfiguration;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.core.fs.CloseableRegistry;
import org.apache.flink.metrics.MetricGroup;
import org.apache.flink.runtime.execution.Environment;
import org.apache.flink.runtime.query.TaskKvStateRegistry;
import org.apache.flink.runtime.state.*;
import org.apache.flink.runtime.state.ttl.TtlTimeProvider;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Collection;

/**
 * @Author: cn.stone
 * @Description:
 * @Date: Created in 14:05 2020/9/22
 * @Modified By:
 */
public class ConfigurationSetting {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //设置缓冲区的最长等待时间
        env.setBufferTimeout(5);
        //设置作业的最大并行度
        env.setMaxParallelism(3);
        //设置单台服务器的并行度
        env.setParallelism(2);

        env.setStateBackend(new StateBackend() {
            @Override
            public CompletedCheckpointStorageLocation resolveCheckpoint(String externalPointer) throws IOException {
                return null;
            }

            @Override
            public CheckpointStorage createCheckpointStorage(JobID jobId) throws IOException {
                return null;
            }

            @Override
            public <K> AbstractKeyedStateBackend<K> createKeyedStateBackend(Environment env, JobID jobID, String operatorIdentifier, TypeSerializer<K> keySerializer, int numberOfKeyGroups, KeyGroupRange keyGroupRange, TaskKvStateRegistry kvStateRegistry, TtlTimeProvider ttlTimeProvider, MetricGroup metricGroup, @Nonnull Collection<KeyedStateHandle> stateHandles, CloseableRegistry cancelStreamRegistry) throws Exception {
                return null;
            }

            @Override
            public OperatorStateBackend createOperatorStateBackend(Environment env, String operatorIdentifier, @Nonnull Collection<OperatorStateHandle> stateHandles, CloseableRegistry cancelStreamRegistry) throws Exception {
                return null;
            }
        });


    }
}
