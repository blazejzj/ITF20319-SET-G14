package org.doProject.tests.unitTests.UserTests;

import org.doProject.common.domain.Project;
import org.doProject.common.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;


public class UserTests {
    private User user;

    @Mock
    private Project project1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User(1, "oejakobs");
        when(project1.getId()).thenReturn(1);
        when(project1.getTitle()).thenReturn("Mock Project");
    }

    @Test
    void testSetUserName() {
        user.setUserName("oejakobs");
        assertEquals("oejakobs", user.getUserName());
    }
}
