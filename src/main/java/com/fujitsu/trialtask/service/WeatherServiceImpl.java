package com.fujitsu.trialtask.service;

import com.fujitsu.trialtask.enums.City;
import com.fujitsu.trialtask.enums.WeatherPhenomenon;
import com.fujitsu.trialtask.model.Weather;
import com.fujitsu.trialtask.repository.WeatherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

@Service
@AllArgsConstructor
public class WeatherServiceImpl implements WeatherService {
    private final WeatherRepository weatherRepository;

    @Override
    public void saveWeather(Weather weather) {
        weatherRepository.save(weather);
    }

    @Override
    public Weather findLatestWeatherByCity(City city) {
        return weatherRepository.findTop1WeatherByNameOrderByTimestampDesc(city);
    }

    @Override
    public void updateWeatherData() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse("https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php ");

        NodeList nodes = doc.getElementsByTagName("station");

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) continue;

            Element element = (Element) node;
            String stationName = element.getElementsByTagName("name").item(0).getTextContent();

            if (City.TALLINN.getStationName().equals(stationName)) saveNewWeather(City.TALLINN, element);
            else if (City.TARTU.getStationName().equals(stationName)) saveNewWeather(City.TARTU, element);
            else if (City.PÄRNU.getStationName().equals(stationName)) saveNewWeather(City.PÄRNU, element);
        }
    }

    private void saveNewWeather(City city, Element element) {
        Weather weather = new Weather();

        weather.setName(city);
        weather.setWindSpeed(Double.parseDouble(element.getElementsByTagName("windspeed").item(0).getTextContent()));
        weather.setAirTemperature(Double.parseDouble(element.getElementsByTagName("airtemperature").item(0).getTextContent()));

        String phenomenon = element.getElementsByTagName("phenomenon").item(0).getTextContent();
        WeatherPhenomenon weatherPhenomenon = decidePhenomenon(phenomenon);
        weather.setWeatherPhenomenon(weatherPhenomenon);

        weather.setWMOCode(Integer.parseInt(element.getElementsByTagName("wmocode").item(0).getTextContent()));
        weather.setTimestamp(Timestamp.from(Instant.now()));
        weatherRepository.save(weather);

    }

    private WeatherPhenomenon decidePhenomenon(String phenomenon) {

        for (WeatherPhenomenon w : WeatherPhenomenon.values()) {
            for (String keyword: w.getKeywordsRelatedToPhenomenon()) {
                if (phenomenon.contains(keyword)) return w;
            }
        }

        return WeatherPhenomenon.NONE;
    }
}
