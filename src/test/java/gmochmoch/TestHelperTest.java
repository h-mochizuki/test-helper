package gmochmoch;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link TestHelper} のテスト
 */
class TestHelperTest {

    @Test
    void type() throws Exception {
        String input = "TestHelperTest#type()";
        TestHelper.type(input, () -> assertEquals(input, new Scanner(System.in).next()));
    }

    @Test
    void text() throws Exception {
        String input = "TestHelperTest#text()";
        TestHelper.text(input, file -> {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                assertEquals(input, reader.readLine());
            }
        });
    }

    @Test
    void monitor() throws Exception {
        String systemOut = "TestHelperTest#monitor(): system.out";
        String systemErr = "TestHelperTest#monitor(): system.err";
        TestHelper.monitor((out, err) -> {
            System.out.print(systemOut);
            System.err.print(systemErr);
            assertEquals(systemOut, out.read());
            assertEquals(systemErr, err.read());
        });
        TestHelper.monitor((out, err) -> {
            System.out.print(systemOut);
            System.err.print(systemErr);
            assertLinesMatch(Stream.of(systemOut), out.output());
            assertLinesMatch(Stream.of(systemErr), err.output());
        });
    }

    @Test
    void monitorOut() throws Exception {
        String systemOut = "TestHelperTest#monitor(): system.out";
        TestHelper.monitorOut(out -> {
            System.out.print(systemOut);
            assertEquals(systemOut, out.read());
        });
        TestHelper.monitorOut(out -> {
            System.out.print(systemOut);
            assertLinesMatch(Stream.of(systemOut), out.output());
        });
    }

    @Test
    void monitorErr() throws Exception {
        String systemErr = "TestHelperTest#monitor(): system.err";
        TestHelper.monitorErr(err -> {
            System.err.print(systemErr);
            assertEquals(systemErr, err.read());
        });
        TestHelper.monitorErr(err -> {
            System.err.print(systemErr);
            assertLinesMatch(Stream.of(systemErr), err.output());
        });
    }
}