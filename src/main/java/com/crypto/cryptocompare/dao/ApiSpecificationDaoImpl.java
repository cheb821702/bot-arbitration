package com.crypto.cryptocompare.dao;

import com.crypto.cryptocompare.model.ApiRequest;
import com.crypto.cryptocompare.model.ApiResponse;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.io.InputStreamReader;

import static com.crypto.cryptocompare.ClientProperties.*;

public class ApiSpecificationDaoImpl extends BaseDaoImpl<ApiRequest,ApiResponse> implements ApiSpecificationDao{

    @Override
    protected String getBaseUrl() {
        return API_SPECIFICATION_URL;
    }

    @Override
    protected String convertToHttpParameters(ApiRequest request) {
        return null;
    }

    @Override
    protected ApiResponse parseResponse(InputStreamReader reader) {
        if (reader != null) {
            JsonReader jsonReader = new JsonReader(reader);
            try {
                parseRoot(jsonReader);
                jsonReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
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
