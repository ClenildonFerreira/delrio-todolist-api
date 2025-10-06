package com.clenildonferreira.delrio_todolist_api.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PagedResponseDTOTest {

    private PagedResponseDTO<String> pagedResponse;

    @BeforeEach
    void setUp() {
        List<String> content = new ArrayList<>(Arrays.asList("item1", "item2", "item3"));
        pagedResponse = new PagedResponseDTO<>(
                content,
                1,    // page
                10,   // size
                25,   // totalElements
                3,    // totalPages
                false,
                false 
        );
    }

    @Test
    void constructor_shouldInitializeAllFields() {
        // Given
        List<String> content = new ArrayList<>(Arrays.asList("test1", "test2"));
        
        // When
        PagedResponseDTO<String> newPagedResponse = new PagedResponseDTO<>(
                content,
                0,    // page
                5,    // size
                15,   // totalElements
                3,    // totalPages
                true, 
                false 
        );

        // Then
        assertEquals(content, newPagedResponse.getContent());
        assertEquals(0, newPagedResponse.getPage());
        assertEquals(5, newPagedResponse.getSize());
        assertEquals(15, newPagedResponse.getTotalElements());
        assertEquals(3, newPagedResponse.getTotalPages());
        assertTrue(newPagedResponse.isFirst());
        assertFalse(newPagedResponse.isLast());
    }

    @Test
    void defaultConstructor_shouldCreateEmptyObject() {
        // When
        PagedResponseDTO<String> emptyResponse = new PagedResponseDTO<>();

        // Then
        assertNull(emptyResponse.getContent());
        assertEquals(0, emptyResponse.getPage());
        assertEquals(0, emptyResponse.getSize());
        assertEquals(0, emptyResponse.getTotalElements());
        assertEquals(0, emptyResponse.getTotalPages());
        assertFalse(emptyResponse.isFirst());
        assertFalse(emptyResponse.isLast());
    }

    @Test
    void gettersAndSetters_shouldWorkCorrectly() {
        // Test getters
        assertEquals(3, pagedResponse.getContent().size());
        assertEquals(1, pagedResponse.getPage());
        assertEquals(10, pagedResponse.getSize());
        assertEquals(25, pagedResponse.getTotalElements());
        assertEquals(3, pagedResponse.getTotalPages());
        assertFalse(pagedResponse.isFirst());
        assertFalse(pagedResponse.isLast());

        // Test setters
        List<String> newContent = new ArrayList<>(Arrays.asList("newItem1"));
        pagedResponse.setContent(newContent);
        pagedResponse.setPage(2);
        pagedResponse.setSize(20);
        pagedResponse.setTotalElements(50);
        pagedResponse.setTotalPages(5);
        pagedResponse.setFirst(true);
        pagedResponse.setLast(true);

        assertEquals(newContent, pagedResponse.getContent());
        assertEquals(2, pagedResponse.getPage());
        assertEquals(20, pagedResponse.getSize());
        assertEquals(50, pagedResponse.getTotalElements());
        assertEquals(5, pagedResponse.getTotalPages());
        assertTrue(pagedResponse.isFirst());
        assertTrue(pagedResponse.isLast());
    }

    @Test
    void content_shouldBeMutable() {
        List<String> mutableContent = new ArrayList<>(Arrays.asList("item1", "item2", "item3"));
        PagedResponseDTO<String> mutablePagedResponse = new PagedResponseDTO<>(
                mutableContent,
                1, 10, 25, 3, false, false);
        
        // When
        List<String> content = mutablePagedResponse.getContent();
        content.add("newItem");

        // Then
        assertEquals(4, mutablePagedResponse.getContent().size());
        assertTrue(mutablePagedResponse.getContent().contains("newItem"));
    }
}