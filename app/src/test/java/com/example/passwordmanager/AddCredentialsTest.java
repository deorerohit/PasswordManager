package com.example.passwordmanager;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class AddCredentialsTest {

    @Test
    public void isTitleValid() {
        //Should be String only
        boolean resultTitle = AddCredentials.validateInput("Rohit");
        assertThat(resultTitle).isEqualTo(true);
    }

    @Test
    public void isUsernameValid() {
        //Should not contain special symbol
        boolean resultUsername = AddCredentials.validateUsername("Rohit123");
        assertThat(resultUsername).isEqualTo(true);
    }

    @Test
    public void isPasswordValid() {
        //Should be 8 characters long and contains char, num, and symbol
        boolean resultUsername = AddCredentials.validatePasssword("Rohivt@1");
        assertThat(resultUsername).isEqualTo(true);
    }
}