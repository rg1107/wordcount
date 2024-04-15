package com.cmd.wordcount.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;

@Slf4j
public final class CommandLineUtils {

    private CommandLineUtils() {}

    public static File isFilePath(final String path) {
        if (path == null || path.isEmpty()) {
            return null;
        }
        try {
            final File file = new File(path);
            if (file.isFile()) {
                log.info("The given path contains a file path={}", path);
                return file;
            } else {
                throw new IOException("Unable to read the file given in path");
            }
        } catch (final Exception ex) {
            log.info("Given String not a file location");
            return null;
        }
    }

    public static long getByteCount(final File file, final String path) {
        log.info("Fetching bytes for path");
        if (file != null) {
            return file.length();
        } else {
            //Java strings are physically stored in UTF-16BE encoding, which uses 2 bytes per code unit,
            // and String.length() measures the length in UTF-16 code units,
            return path.length()* 2L;
        }
    }

    public static long getLineCount(final File file, final String path) {
        log.info("Fetching lines for path");
        if (file != null) {
            try (final BufferedReader reader = new BufferedReader(new FileReader(file))) {
                long lines = 0;
                while (reader.readLine() != null) {
                    lines++;
                }
                return lines;
            } catch (final Exception ex) {
                log.warn("Unexpected error occurred while reading lines from the file");
                return -1;
            }
        } else {
            try (LineNumberReader lineNumberReader = new LineNumberReader(new StringReader(path))) {
                lineNumberReader.skip(Long.MAX_VALUE);
                return lineNumberReader.getLineNumber();
            } catch (final Exception ex) {
                log.warn("Unexpected error occurred while reading lines from the file");
                return -1;
            }
        }
    }

    public static long getWordCount(final File file, final String path) {
        log.info("Fetching words for path");
        if (file != null) {
            try (final BufferedReader reader = new BufferedReader(new FileReader(file))) {
                long wordCount = 0;
                String line;
                while ((line = reader.readLine()) != null) {
                    wordCount += getWordCount(line);
                }
                return wordCount;
            } catch (final Exception ex) {
                log.warn("Unexpected error occurred while reading lines from the file");
                return -1;
            }
        } else {
            return getWordCount(path);
        }
    }

    private static long getWordCount(String input) {
        final int out = 0;
        final int in = 1;
        int state = out;
        long wc = 0;  // word count
        int index = 0;

        // Scan all characters one by one
        while (index < input.length())
        {
            // If next character is a separator, set the
            // state as OUT
            if (input.charAt(index) == ' ' || input.charAt(index) == '\n'
                    || input.charAt(index) == '\t')
                state = out;


            // If next character is not a word separator
            // and state is OUT, then set the state as IN
            // and increment word count
            else if (state == out)
            {
                state = in;
                ++wc;
            }

            // Move to next character
            ++index;
        }
        return wc;
    }
}
