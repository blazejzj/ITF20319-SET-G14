package org.doProject.tests.unitTests.UserTests;

import org.doProject.core.domain.Project;
import org.doProject.core.domain.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


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
        user.addProject(project);

        Assertions.assertTrue(user.getProjects().contains(project));
    }

    @Test
    @DisplayName("Remove a project from user")
    public void testRemoveProject() {
        user.addProject(project);

        user.removeProject(project);

        Assertions.assertFalse(user.getProjects().contains(project));
    }

    @Test
    @DisplayName("Remove all projects from user")
    public void testRemoveAllProjects() {
        user.addProject(project);

        user.removeAllProjects();

        Assertions.assertTrue(user.getProjects().isEmpty());
    }

    @Test
    @DisplayName("Remove project by ID")
    public void testRemoveProjectById() {
        user.addProject(project);

        user.removeProjectById(1);

        Assertions.assertFalse(user.getProjects().contains(project));
    }
}
