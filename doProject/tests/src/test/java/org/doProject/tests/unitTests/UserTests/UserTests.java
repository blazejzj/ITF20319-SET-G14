package org.doProject.tests.unitTests.UserTests;

import org.doProject.common.domain.Project;
import org.doProject.common.domain.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.*;


public class UserTests {

    private User user;
    private Project project;

    /**
     * We are going to be setting up the User and Project objects before each test.
     * We are going to use a real User object here because we want to test actual list
     * operations and state changes within the User object instance. Mocks would not be able
     * to execute the actual methods, making it harder to validate and test various of changes.
     */
    @BeforeEach
    void setUp(){
        user = new User(1, "oejakobs");
        project = new Project(1,"Oda's Project", "Simple, yet powerful project", 1);
    }

    @Test
    @DisplayName("Add project to user")
    public void testAddProject() {
        // Act
        user.addProject(project);

        // Assert
        Assertions.assertTrue(user.getProjects().contains(project));
    }

    @Test
    @DisplayName("Remove a project from user")
    public void testRemoveProject() {
        // Arrange
        user.addProject(project);

        // Act
        user.removeProject(project);

        // Assert
        Assertions.assertFalse(user.getProjects().contains(project));
    }

    @Test
    @DisplayName("Remove all projects from user")
    public void testRemoveAllProjects() {
        // Arrange
        user.addProject(project);

        // Act
        user.removeAllProjects();

        // Assert
        Assertions.assertTrue(user.getProjects().isEmpty());
    }

    @Test
    @DisplayName("Remove project by ID")
    public void testRemoveProjectById() {
        // Arrange
        user.addProject(project);

        // Act
        user.removeProjectById(1);

        // Assert
        Assertions.assertFalse(user.getProjects().contains(project));
    }
}
