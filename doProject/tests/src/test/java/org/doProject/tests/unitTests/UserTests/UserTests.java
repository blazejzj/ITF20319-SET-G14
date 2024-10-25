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
    public User mockUser = mock(User.class);
    public Project mockProject = mock(Project.class);

   /**
    * prøvde noe som ikke funka
    * @BeforeEach
    void setUp(){
        user = new User(1, "oejakobs");
        mockProject = mock(Project.class);
    }      **/

    @Test
    @DisplayName("Add project to user")
    public void testAddProject() {
       ArrayList<Project> projects = new ArrayList<>();
       projects.add(mockProject);

       // Arrange
        when(mockUser.getProjects()).thenReturn(projects);

        // Act
        mockUser.addProject(mockProject);

        // Assert
        Assertions.assertTrue(mockUser.getProjects().contains(mockProject));
    }

    @Test
    @DisplayName("Remove a project from user")
    public void testRemoveProject() {
        ArrayList<Project> projects = new ArrayList<>();
        projects.add(mockProject);

        // Arrange
        when(mockUser.getProjects()).thenReturn(projects);

        // Act
        mockUser.removeProject(mockProject);

        // Assert
        Assertions.assertFalse(mockUser.getProjects().contains(mockProject));
    }

    @Test
    @DisplayName("Remove all projects from user")
    public void testRemoveAllProjects() {
        ArrayList<Project> projects = new ArrayList<>();
        projects.add(mockProject);

        // Arrange
        when(mockUser.getProjects()).thenReturn(projects);

        // Act
        mockUser.removeAllProjects();

        // Assert
        Assertions.assertTrue(mockUser.getProjects().isEmpty());
    }

    @Test
    @DisplayName("Remove project by ID")
    public void testRemoveProjectById() {
        ArrayList<Project> projects = new ArrayList<>();
        projects.add(mockProject);

        // Arrange
        when(mockUser.getProjects()).thenReturn(projects);
        when(mockProject.getId()).thenReturn(1);

        // Act
        mockUser.removeProjectById(1);

        // Assert
        Assertions.assertFalse(mockUser.getProjects().contains(mockProject));
    }
}
