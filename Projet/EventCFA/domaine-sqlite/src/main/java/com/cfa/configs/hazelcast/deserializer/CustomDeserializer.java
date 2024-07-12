package com.cfa.configs.hazelcast.deserializer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.util.SerializationUtils;

/**
 * Deserializer configuration
 * @param <T>
 */
@Slf4j
public final class CustomDeserializer<T> implements Deserializer<Object> {

//  private static final ThreadLocal<FSTConfiguration> conf = ThreadLocal.withInitial(FSTConfiguration::createUnsafeBinaryConfiguration);

  @SuppressWarnings("unchecked")
  @Override
  public Object deserialize(String s, byte[] bytes) {
    return null == bytes ? null : SerializationUtils.deserialize(bytes);
  }
}
