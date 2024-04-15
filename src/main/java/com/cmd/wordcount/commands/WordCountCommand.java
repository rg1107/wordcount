package com.cmd.wordcount.commands;

import com.cmd.wordcount.util.CommandLineUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.File;

@ShellComponent
@Slf4j
public class WordCountCommand {

    private static final String HELP_MESSAGE = "This will get the word count for the text given as input. Input can be a String or a file";

    @ShellMethod(key = "gwc", value = HELP_MESSAGE)
    public String getBytes(@ShellOption(value = {"-c"}, defaultValue = ShellOption.NULL, help = "Will give out the number of bytes in the string") String bytePath,
                           @ShellOption(value = {"-l"}, defaultValue = ShellOption.NULL,  help = "Will give out the number of lines in the String") String linePath,
                           @ShellOption(value = {"-w"}, defaultValue = ShellOption.NULL, help = "Will give out the number of words in the String") String wordPath) {
        final StringBuilder output = new StringBuilder();
        final String path = getPath(bytePath, linePath, wordPath);
        final File file = CommandLineUtils.isFilePath(path);
        if (bytePath != null && !bytePath.isEmpty()) {
            output.append(" ").append(CommandLineUtils.getByteCount(file, path));
        }

        if (linePath != null && !linePath.isEmpty()) {
            output.append(" ").append(CommandLineUtils.getLineCount(file, path));
        }

        if (wordPath != null && !wordPath.isEmpty()) {
            output.append(" ").append(CommandLineUtils.getWordCount(file, path));
        }

        output.append(" ").append(path);
        return output.toString();
    }

    private String getPath(String bytePath, String linePath, String wordPath) {
        if (bytePath != null) {
            return bytePath;
        }

        if (linePath != null) {
            return linePath;
        }

        if (wordPath != null) {
            return wordPath;
        }

        return "";
    }


}
