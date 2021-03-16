package ru.le;

import lombok.val;
import lombok.var;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import us.codecraft.xsoup.Xsoup;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class BusParser {
    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2) " +
            "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.38 Safari/537.36";
    private static final String URL = "https://transphoto.org";
    private static final String RUSSIAN_CITIES_URL = "/country/1/";

    public static Document getDocument(String url) {
        Document document = null;
        try {
            document = Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    public static void main(String[] args) {
        val russianCities = getDocument(URL + RUSSIAN_CITIES_URL);

        val elements = Xsoup
                .compile("//div[@class='p20w']//tbody//td[@class='d']//a")
                .evaluate(russianCities);

//        for (Element element : elements.getElements()) {
//            System.out.println(element.attr("href") + " = " + element.text());
//        }

        val cityHrefMap = elements.getElements().stream()
                .collect(Collectors.toMap(
                        Element::text,
                        entry -> entry.attr("href")
                ));

        System.out.println(cityHrefMap);

        // https://transphoto.org/list.php?cid=258&t=1
        for (var entry : cityHrefMap.entrySet()) {

        }
    }
}
