package io.pivotal.labs.matchers;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionFactory {

    @SafeVarargs
    public static <K, V> Map<K, V> map(Map.Entry<K, V>... entries) {
        return Stream.of(entries).collect(toMap());
    }

    public static <K, V> Collector<Map.Entry<K, V>, ?, Map<K, V>> toMap() {
        return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue);
    }

    public static <K, V> Map.Entry<K, V> entry(K key, V value) {
        return new AbstractMap.SimpleImmutableEntry<K, V>(key, value);
    }

    @SafeVarargs
    static <E> List<E> list(E... elements) {
        return Arrays.asList(elements);
    }

}
