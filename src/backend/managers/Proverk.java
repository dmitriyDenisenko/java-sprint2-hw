package backend.managers;

import backend.api.HttpTaskServer;
import backend.api.KVTaskClient;

import java.io.IOException;

public class Proverk {
    public static void main(String[] args) throws IOException, InterruptedException {
        KVTaskClient client = new KVTaskClient("http://localhost:8088/");
        String json = "{\n" +
                "\t\"name\": \"hey1\",\n" +
                "\t\"description\": \"wayaya1\",\n" +
                "\t\"status\": \"NEW\",\n" +
                "\t\"index\": 2,\n" +
                "\t\"type\": \"TASK\"\n" +
                "}";
        client.put("1",json);
        System.out.println(client.load("1"));
    }
}
