package com.tatar.shoppinglist;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class PositiveNumberValidatorTest {

    PositiveNumberValidator positiveNumberValidator;

    @Before
    public void setUp() throws Exception {
        positiveNumberValidator = new PositiveNumberValidator();
    }

    @Test
    public void firstTest() {
        boolean result = positiveNumberValidator.isPositive(-1);
        Assert.assertThat(result, is(false));
    }

    @Test
    public void secondTest() {
        boolean result = positiveNumberValidator.isPositive(1);
        Assert.assertThat(result, is(true));
    }

    @Test
    public void thirdTest() {
        boolean result = positiveNumberValidator.isPositive(0);
        Assert.assertThat(result, is(false));
    }
}