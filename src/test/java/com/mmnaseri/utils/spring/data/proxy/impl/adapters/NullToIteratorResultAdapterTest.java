package com.mmnaseri.utils.spring.data.proxy.impl.adapters;

import com.mmnaseri.utils.spring.data.domain.impl.ImmutableInvocation;
import org.testng.annotations.Test;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/5/15)
 */
public class NullToIteratorResultAdapterTest {

    private interface Sample {

        Iterator findIterator();

        Object findObject();

    }

    @Test
    public void testAcceptance() throws Exception {
        final NullToIteratorResultAdapter adapter = new NullToIteratorResultAdapter();
        assertThat(adapter.accepts(new ImmutableInvocation(Sample.class.getMethod("findIterator"), new Object[]{}), null), is(true));
        assertThat(adapter.accepts(new ImmutableInvocation(Sample.class.getMethod("findObject"), new Object[]{}), null), is(false));
    }

    @Test
    public void testAdaptingToIterable() throws Exception {
        final NullToIteratorResultAdapter adapter = new NullToIteratorResultAdapter();
        final Iterator value = adapter.adapt(new ImmutableInvocation(Sample.class.getMethod("findIterator"), new Object[]{}), null);
        assertThat(value, is(notNullValue()));
        assertThat(value.hasNext(), is(false));
    }

}