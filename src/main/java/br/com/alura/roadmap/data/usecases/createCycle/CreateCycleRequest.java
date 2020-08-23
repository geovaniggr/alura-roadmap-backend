package br.com.alura.roadmap.data.usecases.createCycle;

import br.com.alura.roadmap.domain.entities.Cycle;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public record CreateCycleRequest(
        @NotNull @NotEmpty String name,

        @JsonFormat(pattern = "dd/MM/yyyy")
        @JsonProperty("startDate")
        LocalDate startDate,

        @JsonFormat(pattern = "dd/MM/yyyy")
        @JsonProperty("endDate")
        LocalDate endDate
        ) {
        public Cycle toEntity(){
                return new Cycle(
                        name,
                        startDate,
                        endDate
                );
        }
}
