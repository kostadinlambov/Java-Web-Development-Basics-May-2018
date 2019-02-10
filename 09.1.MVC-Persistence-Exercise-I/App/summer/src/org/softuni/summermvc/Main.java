package org.softuni.summermvc;

import org.softuni.summermvc.api.Model;
import org.softuni.summermvc.util.PathFormatter;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws NoSuchMethodException {
        String route = "/idex/comments";
        String potentialRoute = "/idex/comments";

        Pattern finalizedPattern = Pattern.compile(PathFormatter.formatPath(route));

        Matcher finalizedMatcher = finalizedPattern.matcher(potentialRoute);

        if(finalizedMatcher.find()) {
            System.out.println("vlezoh");
        }
    }
}



// @Controller
// @GetMapping
// @PostMapping

//