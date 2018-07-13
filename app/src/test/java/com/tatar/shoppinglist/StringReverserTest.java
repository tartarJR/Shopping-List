package com.tatar.shoppinglist;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StringReverserTest {

    StringReverser stringReverser;

    @Before
    public void setUp() throws Exception {
        stringReverser = new StringReverser();
    }

    @Test
    public void reverse_emptyString_emptyStringReturned() {
        String result = stringReverser.reverse("");
        assertThat(result, is(""));
    }

    @Test
    public void reverse_singleCharacter_sameStringReturned() {
        String result = stringReverser.reverse("x");
        assertThat(result, is("x"));
    }

    @Test
    public void reverse_sitring_reversedString() {
        String result = stringReverser.reverse("abcd");
        assertThat(result, is("dcba"));
    }
}