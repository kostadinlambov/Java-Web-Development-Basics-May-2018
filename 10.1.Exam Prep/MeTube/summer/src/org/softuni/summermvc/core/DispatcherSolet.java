package org.softuni.summermvc.core;

import org.softuni.broccolina.solet.*;
import org.softuni.javache.http.HttpSession;
import org.softuni.javache.http.HttpStatus;
import org.softuni.summermvc.api.Model;
import org.softuni.summermvc.api.PathVariable;
import org.softuni.summermvc.util.ControllerActionPair;
import org.softuni.summermvc.util.ControllerLoadingService;
import org.softuni.summermvc.util.TemplateEngine;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

//THERE IS A BUG IN THIS CLASS
//BUT I DON'T KNOW WHERE
@WebSolet(route = "/*")
public class DispatcherSolet extends BaseHttpSolet {
    private String applicationClassesFolderPath;

    private DependencyContainer dependencyContainer;

    private ControllerLoadingService controllerLoadingService;

    private TemplateEngine templateEngine;

    private ControllerActionInvoker controllerActionInvoker;

    private ControllerActionPair getControllerActionPairCandidate(HttpSoletRequest request) {
        Set<Object> actionParameters = new LinkedHashSet<>();

        ControllerActionPair candidateControllerActionPair = this.controllerLoadingService.getLoadedControllersAndActions()
                .get(request.getMethod())
                .entrySet()
                .stream()
                .filter(action -> {
                    Pattern routePattern = Pattern.compile("^"
                            + action.getKey()
                            + "$");
                    Matcher routeMatcher = routePattern.matcher(request.getRequestUrl());

                    if(routeMatcher.find()) {
                        List<Parameter> pathVariables = Arrays.stream(action.getValue()
                                .getAction().getParameters())
                                .filter(parameter -> parameter.isAnnotationPresent(PathVariable.class))
                                .collect(Collectors.toList());

                        for (Parameter pathVariable : pathVariables) {
                            String variableName = pathVariable.getAnnotation(PathVariable.class).name();

                            String variableValue = routeMatcher.group(variableName);

                            actionParameters.add(variableValue);
                        }

                        return false;
                    } else {
                        return false;
                    }
                })
                .map(x -> x.getValue())
                .findFirst()
                .orElse(null);

        for (Object actionParameter : actionParameters) {
            candidateControllerActionPair.addParameter(actionParameter);
        }

        return candidateControllerActionPair;
    }

    private void handleRequest(HttpSoletRequest request, HttpSoletResponse response) {
        ControllerActionPair cap = this.getControllerActionPairCandidate(request);

        if (cap == null) {
            if (request.getMethod().equals("GET")) {
                super.doGet(request, response);
            } else if (request.getMethod().equals("POST")) {
                super.doPost(request, response);
            }

            return;
        }

        try {
            String result = this.controllerActionInvoker
                    .invokeAction(cap)
                    .toString();

            response.setStatusCode(HttpStatus.OK);

            if (result.startsWith("template:")) {
                String templateName = result.split(":")[1];

                response.addHeader("Content-Type", "text/html");

                response.setContent(this.templateEngine.loadTemplate(templateName, (Model) this.dependencyContainer.getObject(Model.class.getSimpleName())).getBytes());
            } else if (result.startsWith("redirect:")) {
                String route = result.split(":")[1];

                response.setStatusCode(HttpStatus.SEE_OTHER);

                response.addHeader("Location", route);
            } else {
                response.addHeader("Content-Type", "text/plain");

                response.setContent(result.getBytes());
            }
        } catch (IllegalAccessException | InvocationTargetException | IOException | NoSuchMethodException | InstantiationException e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);

            response.addHeader("Content-Type", "text/html");

            StringBuilder content = new StringBuilder();

            content.append("<h1>" + e.getMessage() + "</h1><p>");

            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                content.append(stackTraceElement.toString() + "<br/>");
            }

            content.append("</p>");

            response.setContent(content.toString().getBytes());
        }
    }

    @Override
    public void init(SoletConfig soletConfig) {
        super.init(soletConfig);

        this.applicationClassesFolderPath = soletConfig.getAttribute("application-folder") + "classes" + File.separator;

        this.dependencyContainer = new DependencyContainer();
        this.controllerLoadingService = new ControllerLoadingService();
        this.templateEngine = new TemplateEngine(this.getSoletConfig().getAttribute("application-folder") + "resources" + File.separator + "templates" + File.separator);
        this.controllerActionInvoker = new ControllerActionInvoker(this.dependencyContainer);

        try {
            this.controllerLoadingService.loadControllerActionHandlers(this.applicationClassesFolderPath);
        } catch (NoSuchMethodException | IOException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void service(HttpSoletRequest request, HttpSoletResponse response) {
        this.dependencyContainer.addInstantiatedObject(HttpSoletRequest.class.getSimpleName(), request);
        this.dependencyContainer.addInstantiatedObject(HttpSoletResponse.class.getSimpleName(), response);

        if (request.getSession() != null) {
            this.dependencyContainer.addInstantiatedObject(HttpSession.class.getSimpleName(), request.getSession());
        }

        super.service(request, response);

        this.dependencyContainer.evictCachedStaticStates();
    }

    @Override
    protected void doGet(HttpSoletRequest request, HttpSoletResponse response) {
        this.handleRequest(request, response);
    }

    @Override
    protected void doPost(HttpSoletRequest request, HttpSoletResponse response) {
        this.handleRequest(request, response);
    }
}