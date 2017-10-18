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
				parceRoot(jsonReader);
				jsonReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void parceRoot(JsonReader reader) throws IOException {
		while (reader.hasNext()) { // обходим все токены
			JsonToken jsonToken = reader.peek(); // получаем тип следующего токена
			if (jsonToken == JsonToken.BEGIN_OBJECT) { // если начало объекта
				reader.beginObject();
				System.out.println("{");
			} else if (jsonToken == JsonToken.END_OBJECT) { // если конец объекта
				reader.endObject();
				System.out.println("}");
			} else if (jsonToken == JsonToken.STRING) { // в случае если токен строковое знание - выводим на экран
				System.out.println(reader.nextString() + " "); // напечатает Hi World!
			} else if (jsonToken == JsonToken.BEGIN_ARRAY) { 
				reader.beginArray();
				System.out.println("[");
			} else if (jsonToken == JsonToken.END_ARRAY) { 
				reader.endArray();
				System.out.println("]");
			} else if (jsonToken == JsonToken.BOOLEAN) { 
				System.out.println(reader.nextBoolean() + " ");
			} else if (jsonToken == JsonToken.NUMBER) { 
				System.out.println(reader.nextInt() + " ");
			} else if (jsonToken == JsonToken.NAME) { 
				System.out.println(reader.nextName() + ":");
			} else {
				reader.skipValue(); // пропускаем все прочие токены
			}
		}
	}

}
