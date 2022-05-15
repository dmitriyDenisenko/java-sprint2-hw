package backend.api;

import backend.managers.Managers;
import backend.managers.TaskManager;
import backend.tasks.Epic;
import backend.tasks.Subtask;
import backend.tasks.Task;
import com.google.gson.*;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class HttpTaskServer {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private TaskManager manager = Managers.getDefaultFileBackedManager("");
    private HttpServer httpServer;
    private final int PORT = 8088;
    private final Gson gson = new Gson();

    public HttpTaskServer() throws IOException {
        this.httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks/task", new TaskHandler());
        httpServer.createContext("/tasks/subtask", new SubtasksHandler());
        httpServer.createContext("/tasks/epics", new EpicsHandler());
        httpServer.createContext("/tasks/history", new HistoryHandler());
        httpServer.createContext("/tasks/", new AllTasksHandler());
        httpServer.start(); // сделать функцию включения и выключения
    }

    public class TaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = null;
            String method = httpExchange.getRequestMethod();
            URI requestURI;
            String query;
            int responseCode;
            switch (method) {
                case "GET":
                    requestURI = httpExchange.getRequestURI();
                    query = requestURI.getQuery();
                    if (query != null) {
                        int numb = Integer.parseInt(query.substring(3));
                        Task task = manager.getAllTypeTaskById(numb);
                        if (task != null) {
                            response = gson.toJson(task);
                            responseCode = 200;
                        } else {
                            responseCode = 400;
                        }
                    } else {
                        response = gson.toJson(manager.getTasks());
                        responseCode = 200;
                    }
                    break;
                case "POST":
                    Headers requestHeaders = httpExchange.getRequestHeaders();
                    List<String> contentTypeValues = requestHeaders.get("Content-type");
                    if ((contentTypeValues != null) && (contentTypeValues.contains("application/json"))) {
                        try (InputStream inputStream = httpExchange.getRequestBody()) {
                            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                            try {
                                Task taskPost = gson.fromJson(body, Task.class);
                                if (manager.getTasks().containsKey(taskPost.getIndex())) {
                                    manager.updateTask(taskPost, taskPost.getIndex());
                                } else {
                                    manager.addTask(taskPost);
                                }
                                responseCode = 201;
                            } catch (JsonSyntaxException e) {
                                responseCode = 400;
                            }
                        }
                    } else {
                        responseCode = 200;
                    }
                    break;
                case "DELETE":
                    requestURI = httpExchange.getRequestURI();
                    query = requestURI.getQuery();
                    if (query != null) {
                        int numb = Integer.parseInt(query.substring(3));
                        manager.removeByIndex(numb);
                    } else {
                        manager.removeAll();
                    }
                    responseCode = 200;
                    break;
                default:
                    responseCode = 400;
            }
            httpExchange.sendResponseHeaders(responseCode, 0);
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    public class SubtasksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = null;
            String method = httpExchange.getRequestMethod();
            URI requestURI;
            String query;
            int responseCode;
            switch (method) {
                case "GET":
                    requestURI = httpExchange.getRequestURI();
                    query = requestURI.getQuery();
                    String path = requestURI.getPath();
                    String[] split = path.split("/");
                    System.out.println(split.length);
                    if(split.length == 3){
                        if (query != null) {
                            int numb = Integer.parseInt(query.substring(3));
                            Subtask subtask = manager.getAllSubtasks().get(numb);
                            if (subtask != null) {
                                response = gson.toJson(subtask);
                                responseCode = 200;
                            } else {
                                responseCode = 400;
                            }
                        } else {
                            response = gson.toJson(manager.getAllSubtasks());
                            responseCode = 200;
                        }
                        break;
                    } else {
                        if(query != null){
                            int numb = Integer.parseInt(query.substring(3));
                            Epic main = manager.getEpics().get(numb);
                            if(main != null){
                                response = gson.toJson(main.getSubtasks());
                                responseCode = 200;
                            } else{
                                responseCode = 400;
                            }
                            break;
                        } else {
                            responseCode = 400;
                        }
                        break;
                    }
                case "POST":
                    Headers requestHeaders = httpExchange.getRequestHeaders();
                    List<String> contentTypeValues = requestHeaders.get("Content-type");
                    if ((contentTypeValues != null) && (contentTypeValues.contains("application/json"))) {
                        try (InputStream inputStream = httpExchange.getRequestBody()) {
                            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                            try {
                                Subtask subtaskPost = gson.fromJson(body, Subtask.class);
                                if (manager.getAllSubtasks().containsKey(subtaskPost.getIndex())) {
                                    manager.updateSubtask(subtaskPost, subtaskPost.getMainEpic().getIndex());
                                } else {
                                    manager.addSubtask(subtaskPost, subtaskPost.getMainEpic().getIndex());
                                }
                                responseCode = 201;
                            } catch (JsonSyntaxException e) {
                                responseCode = 400;
                            }
                        }
                    } else {
                        responseCode = 501;
                    }
                    break;
                case "DELETE":
                    requestURI = httpExchange.getRequestURI();
                    query = requestURI.getQuery();
                    if (query != null) {
                        int numb = Integer.parseInt(query.substring(3));
                        manager.removeByIndex(numb);
                        responseCode = 200;
                    } else {
                        responseCode = 400;
                    }
                    break;
                default:
                    responseCode = 400;
            }
            httpExchange.sendResponseHeaders(responseCode, 0);
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    public class EpicsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = null;
            String method = httpExchange.getRequestMethod();
            URI requestURI;
            String query;
            int responseCode;
            switch (method) {
                case "GET":
                    requestURI = httpExchange.getRequestURI();
                    query = requestURI.getQuery();
                    if (query != null) {
                        int numb = Integer.parseInt(query.substring(3));
                        Epic epic = manager.getEpics().get(numb);
                        if (epic != null) {
                            response = gson.toJson(epic);
                            responseCode = 200;
                        } else {
                            responseCode = 400;
                        }
                    } else {
                        response = gson.toJson(manager.getEpics());
                        responseCode = 200;
                    }
                    break;
                case "POST":
                Headers requestHeaders = httpExchange.getRequestHeaders();
                List<String> contentTypeValues = requestHeaders.get("Content-type");
                if ((contentTypeValues != null) && (contentTypeValues.contains("application/json"))) {
                    try (InputStream inputStream = httpExchange.getRequestBody()) {
                        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                        try {
                            Epic epic = gson.fromJson(body, Epic.class);
                            if (manager.getEpics().containsKey(epic.getIndex())) {
                                manager.updateEpic(epic, epic.getIndex());
                            } else {
                                manager.addEpic(epic);
                            }
                            responseCode = 201;
                        } catch (JsonSyntaxException e) {
                            responseCode = 400;
                        }
                    }
                } else {
                    responseCode = 400;
                }
                break;
                case "DELETE":
                    requestURI = httpExchange.getRequestURI();
                    query = requestURI.getQuery();
                    if (query != null) {
                        int numb = Integer.parseInt(query.substring(3));
                        manager.removeByIndex(numb);
                        responseCode = 200;
                    } else {
                        responseCode = 400;
                    }
                    break;
                default:
                    responseCode = 400;
            }
            httpExchange.sendResponseHeaders(responseCode, 0);
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    public class HistoryHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = null;
            String method = httpExchange.getRequestMethod();
            int responseCode;
            switch (method){
                case "GET":
                    response = gson.toJson(manager.history());
                    responseCode = 200;
                    break;
                default:
                    responseCode = 400;
            }
            httpExchange.sendResponseHeaders(responseCode, 0);
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    public class AllTasksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = null;
            String method = httpExchange.getRequestMethod();
            int responseCode;
            switch (method){
                case "GET":
                    response = gson.toJson(manager.getPrioritizedTasks());
                    responseCode = 200;
                    break;
                default:
                    responseCode = 400;
            }
            httpExchange.sendResponseHeaders(responseCode, 0);
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

}
