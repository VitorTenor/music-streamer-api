package com.music.musicStreamer.core.storage.impl;

import com.music.musicStreamer.entities.image.ImageRequest;
import com.music.musicStreamer.exceptions.ImageException;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("ImageFiles Test")
public class TestImageFiles {
    private static ImageFiles imageFiles;
    private static final String TEST_IMAGE_NAME = "test_image.png";
    private static final String TEST_IMAGE_NAME2 = "test_image.pngnull";
    private static final byte[] TEST_IMAGE_DATA = new byte[]{0x50, 0x4E, 0x47}; // Sample image data

    @BeforeAll
    static void setUp() {
        imageFiles = new ImageFiles();
    }

    @AfterAll
    static void tearDown() throws IOException {
        Files.delete(Paths.get("nulltest_image.pngnull"));
    }

    @Test
    @Order(1)
    @DisplayName("001 - Test saving an image file")
    public void testSaveInFiles() {
        ImageRequest imageRequest = new ImageRequest(
                TEST_IMAGE_DATA,
                1
        );

        assertDoesNotThrow(() -> imageFiles.saveInFiles(imageRequest, TEST_IMAGE_NAME));
    }

    @Test
    @Order(2)
    @DisplayName("002 - Test reading an image file")
    public void testGetBytesInFiles() {
        assertThrows(
                ImageException.class,
                () -> imageFiles.getBytesInFiles("non_existent_image.png"
        ));
    }

    @Test
    @Order(3)
    @DisplayName("003 - Test getting a file from storage")
    public void testGetFile() {
        File file = imageFiles.getFile(TEST_IMAGE_NAME);
        assertNotNull(file);
    }

    @Test
    @Order(4)
    @DisplayName("004 - Test deleting an image file")
    public void testDeleteInFiles() {
        assertThrows(
                ImageException.class,
                () -> imageFiles.deleteInFiles("non_existent_image.png"
        ));
    }

    @Test
    @Order(5)
    @DisplayName("005 - Test reading a non-existent image file")
    public void testReadNonExistentFile() {
        assertThrows(ImageException.class, () -> imageFiles.getBytesInFiles("non_existent_image.png"));
    }

    @Test
    @Order(6)
    @DisplayName("006 - Test deleting a non-existent image file")
    public void testDeleteNonExistentFile() {
        InputStream inputStream = imageFiles.getInFilesStream("non_existent_image.png");
        assertNull(inputStream);
    }

    @Test
    @Order(7)
    @DisplayName("007 - Test getAllInFiles")
    public void testGetAllInFiles() {
        List list = imageFiles.getAllInFiles();
        assertNull(list);
    }

    @Test
    @Order(8)
    @DisplayName("008 - Test saving an image file with error")
    public void testSaveInFilesWithErro() {
        ImageRequest imageRequest = new ImageRequest(
            null,
                1
        );
        assertThrows(
                Exception.class,
                () -> imageFiles.saveInFiles(imageRequest, TEST_IMAGE_NAME)
        );
    }

    @Test
    @Order(9)
    @DisplayName("009 - Test deleteInFiles with error")
    public void testDeleteInFilesWithErro() {
        assertThrows(
                Exception.class,
                () -> imageFiles.deleteInFiles(null)
        );
    }
}
