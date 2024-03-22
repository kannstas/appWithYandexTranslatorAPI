package nastya.ru;

import nastya.ru.util.YandexTranslatorResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Translator {
   private static Scanner in;
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Введите слово на русском языке");
        in = new Scanner(System.in);
        String wordToTranslate = in.nextLine();

        String response = requestAndResponse(wordToTranslate);
        System.out.println(response);
    }


    private static String requestAndResponse (String wordToTranslate) throws FileNotFoundException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://translate.api.cloud.yandex.net/translate/v2/translate";

        HashMap<String, String> tokenAndFolderIdMap = recordingTokenAndFolderIdInMap();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", "Bearer " + tokenAndFolderIdMap.get("token"));

        Map<String, String> jsonData = new HashMap<>();
        jsonData.put("folderId", tokenAndFolderIdMap.get("folderId"));
        jsonData.put("targetLanguageCode", "en");
        jsonData.put("texts", "["+wordToTranslate+"]");

        HttpEntity <Map<String, String>> request = new HttpEntity<>(jsonData, httpHeaders);

        YandexTranslatorResponse response = restTemplate.postForObject(url, request, YandexTranslatorResponse.class);
        String answerToUser = response.getTranslations().get(0).getText();

        return answerToUser;
    }

    private static HashMap<String, String> recordingTokenAndFolderIdInMap () throws FileNotFoundException {

        in = new Scanner(new FileReader("src/main/resources/resourcesFile.txt"));

        HashMap<String, String> tokenAndFolderIdMap = new HashMap<>();
        while (in.hasNextLine()) {
            String [] columns = in.nextLine().split(",");
            tokenAndFolderIdMap.put(columns[0], columns[1]);
        }

        return tokenAndFolderIdMap;
    }
}