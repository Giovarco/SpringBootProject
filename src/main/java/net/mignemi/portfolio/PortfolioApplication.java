package net.mignemi.portfolio;

import net.mignemi.portfolio.model.Film;
import net.mignemi.portfolio.repository.FilmRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class PortfolioApplication {

    @Autowired
    FilmRepository filmRepository;

    public static void main(String[] args) {
        SpringApplication.run(PortfolioApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fillDatabaseByCsvFile() throws IOException {
        InputStream csvInputStream = getCsvFileAsInputStream();
        CSVParser parser = getCSVParser(csvInputStream);

        List<Film> filmList = new ArrayList<>();
        for (CSVRecord record : parser) {
            Film newFilm = Film.builder()
                    .title(record.get(0))
                    .locations(record.get(2)
                    ).build();
            filmList.add(newFilm);
        }

        filmRepository.saveAll(filmList);
    }

    private InputStream getCsvFileAsInputStream() throws IOException {
        Resource resource = new ClassPathResource("Film_Locations_in_San_Francisco.csv");
        return resource.getInputStream();
    }

    private CSVParser getCSVParser(InputStream input) throws IOException {
        return CSVFormat.EXCEL
                .withHeader()
                .withDelimiter(';')
                .withAllowMissingColumnNames(false)
                .parse(new InputStreamReader(input));
    }
}

