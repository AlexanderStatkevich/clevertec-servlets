package com.statkevich.receipttask.reader;

import com.statkevich.receipttask.dto.InputPositionDto;
import com.statkevich.receipttask.dto.InputValuesDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FileReaderTest {

    private final List<String> orderList = List.of("file-test_parameter.txt");
    private FileReader fileReader;

    @BeforeEach
    void setUp() {
        ConsoleReader consoleReader = new ConsoleReader();
        fileReader = new FileReader(consoleReader);
    }

    @Test
    void test() {
        InputValuesDto expected = new InputValuesDto(List.of(
                new InputPositionDto(1L, 2),
                new InputPositionDto(2L, 3),
                new InputPositionDto(3L, 1)),
                "1234");
        InputValuesDto actual = fileReader.read(orderList);

        assertThat(actual).isEqualTo(expected);
    }
}
