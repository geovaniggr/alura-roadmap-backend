package br.com.alura.roadmap.domain.entities;

import java.time.LocalDate;
import java.util.List;

import lombok.NoArgsConstructor;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter @Setter
@NoArgsConstructor
@Document
public class Cycle {

    private String id;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private List<String> users;

    public Cycle(String name, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}