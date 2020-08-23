package br.com.alura.roadmap.data.usecases.completeSprint;

import java.util.List;

public record CompletedSprintRequest (
        String sprintId,
        List<CompletedTask> tasks
){}