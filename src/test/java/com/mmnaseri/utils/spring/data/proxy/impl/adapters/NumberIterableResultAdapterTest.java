package com.mmnaseri.utils.spring.data.proxy.impl.adapters;

import com.mmnaseri.utils.spring.data.domain.impl.ImmutableInvocation;
import com.mmnaseri.utils.spring.data.error.ResultAdapterFailureException;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (4/10/16)
 */
public class NumberIterableResultAdapterTest {

    private interface Sample {

        int findInt();

        long findLong();

        short findShort();

        double findDouble();

        float findFloat();

        byte findByte();

        BigDecimal findBigDecimal();

        Integer findInteger();

        Object findOther();

    }

    @Test
    public void testAccepting() throws Exception {
        final NumberIterableResultAdapter adapter = new NumberIterableResultAdapter();
        assertThat(adapter.accepts(null, null), is(false));
        assertThat(adapter.accepts(new ImmutableInvocation(Sample.class.getMethod("findOther"), new Object[]{}), new Object()), is(false));
        assertThat(adapter.accepts(new ImmutableInvocation(Sample.class.getMethod("findInt"), new Object[]{}), new Object()), is(false));
        assertThat(adapter.accepts(new ImmutableInvocation(Sample.class.getMethod("findInt"), new Object[]{}), Arrays.asList("", 1)), is(false));
        assertThat(adapter.accepts(new ImmutableInvocation(Sample.class.getMethod("findInt"), new Object[]{}), Arrays.asList(1, 2)), is(false));
        assertThat(adapter.accepts(new ImmutableInvocation(Sample.class.getMethod("findInt"), new Object[]{}), Collections.singletonList(1)), is(true));
        assertThat(adapter.accepts(new ImmutableInvocation(Sample.class.getMethod("findInteger"), new Object[]{}), Collections.singletonList(1)), is(true));
    }

    @Test
    public void testAdapting() throws Exception {
        final NumberIterableResultAdapter adapter = new NumberIterableResultAdapter();
        Long value = 100L;
        assertThat(adapter.adapt(new ImmutableInvocation(Sample.class.getMethod("findInt"), new Object[]{}), Collections.singleton(value)), Matchers.<Object>is(value.intValue()));
        assertThat(adapter.adapt(new ImmutableInvocation(Sample.class.getMethod("findLong"), new Object[]{}), Collections.singleton(value)), Matchers.<Object>is(value));
        assertThat(adapter.adapt(new ImmutableInvocation(Sample.class.getMethod("findShort"), new Object[]{}), Collections.singleton(value)), Matchers.<Object>is(value.shortValue()));
        assertThat(adapter.adapt(new ImmutableInvocation(Sample.class.getMethod("findDouble"), new Object[]{}), Collections.singleton(value)), Matchers.<Object>is(value.doubleValue()));
        assertThat(adapter.adapt(new ImmutableInvocation(Sample.class.getMethod("findFloat"), new Object[]{}), Collections.singleton(value)), Matchers.<Object>is(value.floatValue()));
        assertThat(adapter.adapt(new ImmutableInvocation(Sample.class.getMethod("findByte"), new Object[]{}), Collections.singleton(value)), Matchers.<Object>is(value.byteValue()));
    }

    @Test(expectedExceptions = ResultAdapterFailureException.class)
    public void testAdaptingUnsupportedNumberType() throws Exception {
        final NumberIterableResultAdapter adapter = new NumberIterableResultAdapter();
        adapter.adapt(new ImmutableInvocation(Sample.class.getMethod("findBigDecimal"), new Object[]{}), Collections.singletonList(1));
    }
}