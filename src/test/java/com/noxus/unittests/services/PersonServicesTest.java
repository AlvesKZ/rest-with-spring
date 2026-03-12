package com.noxus.unittests.services;

import com.noxus.data.dto.PersonDTO;
import com.noxus.exception.RequiredObjectIsNullException;
import com.noxus.model.Person;
import com.noxus.repository.PersonRepository;
import com.noxus.services.PersonServices;
import com.noxus.unittests.mapper.mocks.MockPerson;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

    MockPerson input;

    @InjectMocks
    private PersonServices service;

    @Mock
    PersonRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockPerson();
    }

    @Test
    void findById() {

        Person person = input.mockEntity(1);
        person.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(person));

        var result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertLinkExists(result, "self", "GET", "/api/person/v1/1");
        assertLinkExists(result, "findAll", "GET", "/api/person/v1");
        assertLinkExists(result, "create", "POST", "/api/person/v1");
        assertLinkExists(result, "update", "PUT", "/api/person/v1");
        assertLinkExists(result, "delete", "DELETE", "/api/person/v1/1");

        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void create() {

        Person person = input.mockEntity(1);
        Person persisted = input.mockEntity(1);
        persisted.setId(1L);

        PersonDTO dto = input.mockDTO(1);

        when(repository.save(any(Person.class))).thenReturn(persisted);

        var result = service.create(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertLinkExists(result, "self", "GET", "/api/person/v1/1");
        assertLinkExists(result, "findAll", "GET", "/api/person/v1");
        assertLinkExists(result, "create", "POST", "/api/person/v1");
        assertLinkExists(result, "update", "PUT", "/api/person/v1");
        assertLinkExists(result, "delete", "DELETE", "/api/person/v1/1");

        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testCreateWithNullPerson() {

        Exception exception = assertThrows(
                RequiredObjectIsNullException.class,
                () -> service.create(null)
        );

        String expectedMessage = "It is not allowed to persist a null object!";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void update() {

        Person person = input.mockEntity(1);
        person.setId(1L);

        Person persisted = input.mockEntity(1);
        persisted.setId(1L);

        PersonDTO dto = input.mockDTO(1);

        when(repository.findById(1L)).thenReturn(Optional.of(person));
        when(repository.save(any(Person.class))).thenReturn(persisted);

        var result = service.update(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertLinkExists(result, "self", "GET", "/api/person/v1/1");
        assertLinkExists(result, "findAll", "GET", "/api/person/v1");
        assertLinkExists(result, "create", "POST", "/api/person/v1");
        assertLinkExists(result, "update", "PUT", "/api/person/v1");
        assertLinkExists(result, "delete", "DELETE", "/api/person/v1/1");

        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testUpdateWithNullPerson() {

        Exception exception = assertThrows(
                RequiredObjectIsNullException.class,
                () -> service.update(null)
        );

        String expectedMessage = "It is not allowed to persist a null object!";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void delete() {

        Person person = input.mockEntity(1);
        person.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(person));

        service.delete(1L);

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Person.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findAll() {

        List<Person> list = input.mockEntityList();
        when(repository.findAll()).thenReturn(list);

        List<PersonDTO> people = service.findAll();

        assertNotNull(people);
        assertEquals(14, people.size());

        var personOne = people.get(1);

        assertNotNull(personOne);
        assertNotNull(personOne.getId());
        assertNotNull(personOne.getLinks());

        assertLinkExists(personOne, "self", "GET", "/api/person/v1/1");
        assertLinkExists(personOne, "findAll", "GET", "/api/person/v1");
        assertLinkExists(personOne, "create", "POST", "/api/person/v1");
        assertLinkExists(personOne, "update", "PUT", "/api/person/v1");
        assertLinkExists(personOne, "delete", "DELETE", "/api/person/v1/1");

        assertEquals("Address Test1", personOne.getAddress());
        assertEquals("First Name Test1", personOne.getFirstName());
        assertEquals("Last Name Test1", personOne.getLastName());
        assertEquals("Female", personOne.getGender());
    }

    private void assertLinkExists(PersonDTO dto, String rel, String method, String path) {
        assertTrue(dto.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals(rel)
                        && link.getHref().endsWith(path)
                        && link.getType().equals(method)
                ));
    }
}