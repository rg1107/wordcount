package com.cmd.wordcount.commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class HelloCommand {
    @ShellMethod(key = "hello", value = "I will say hello to you")
    public String hello(@ShellOption(defaultValue = "World") String arg) {
        return "Hello " + arg + "!";
    }

    @ShellMethod(key = "goodbye", value = "I will wave goodbye to you")
    public String goodBye() {
        return "GoodBye!";
    }
}
