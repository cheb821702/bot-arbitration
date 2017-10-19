package com.crypto.cryptocompare;

import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class JavaApiGenerator {

    private ApiJsonProvider apiProvider = new ApiJsonProvider();

    public static void main(String[] args) {
        new JavaApiGenerator().testIt();
    }

    public void testIt() {
        generateApiFromJSONSpecification(apiProvider.getSpecificationReader());
    }

    public void generateApiFromJSONSpecification(InputStreamReader textStream) {
        if (textStream != null) {
            JsonReader jsonReader = new JsonReader(textStream);
            try {
                parseRoot(jsonReader);
                jsonReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void parseRoot(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            JsonToken jsonToken = reader.peek();
            if (jsonToken == JsonToken.NAME) {
                String name = reader.nextName();
                if ("Called".equals(name)) {
                    System.out.println(name + ":" + reader.nextString());
                } else if ("Message".equals(name)) {
                    System.out.println(name + ":" + reader.nextString());
                } else if ("AvailableCalls".equals(name)) {
                    parseCalls(reader);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
    }

    private void parseCalls(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            JsonToken jsonToken = reader.peek();
            if (jsonToken == JsonToken.NAME) {
                String name = reader.nextName();
                if ("RateLimit".equals(name)) {
                    parseRateLimit(reader);
                } else {
                    parseService(reader);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
    }

    private void parseRateLimit(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            JsonToken jsonToken = reader.peek();
            if (jsonToken == JsonToken.NAME) {
                System.out.println(reader.nextName() + ":");
                parseRateLimitUnit(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
    }

    private void parseService(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            JsonToken jsonToken = reader.peek();
            if (jsonToken == JsonToken.NAME) {
                System.out.println(reader.nextName() + ":");
                parseServiceMethod(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
    }

    private void parseRateLimitUnit(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            JsonToken jsonToken = reader.peek();
            if (jsonToken == JsonToken.NAME) {
                System.out.println("\t" + reader.nextName() + "->" + reader.nextString());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
    }

    private void parseServiceMethod(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            JsonToken jsonToken = reader.peek();
            if (jsonToken == JsonToken.NAME) {
                String name = reader.nextName();
                if ("Simple".equals(name)) {
                    System.out.println("\t" + name + "->" + reader.nextString());
                } else if ("Info".equals(name)) {
                    System.out.println(name + ":");
                    reader.skipValue();
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
    }
}
