package br.com.alura.roadmap.data.usecases.completeSprint;

public record CompletedTask(
   String id,
   Double percentCompleted,
   String description
) {}
