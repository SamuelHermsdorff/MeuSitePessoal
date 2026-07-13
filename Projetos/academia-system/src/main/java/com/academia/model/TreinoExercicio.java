package com.academia.model;

public class TreinoExercicio {

    private Integer id;

    private Integer treinoId;

    private Integer exercicioId;

    private Integer series;

    private Integer repeticoes;

    private Double carga;

    private String nomeExercicio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTreinoId() {
        return treinoId;
    }

    public void setTreinoId(Integer treinoId) {
        this.treinoId = treinoId;
    }

    public Integer getExercicioId() {
        return exercicioId;
    }

    public void setExercicioId(Integer exercicioId) {
        this.exercicioId = exercicioId;
    }

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public Integer getRepeticoes() {
        return repeticoes;
    }

    public void setRepeticoes(Integer repeticoes) {
        this.repeticoes = repeticoes;
    }

    public Double getCarga() {
        return carga;
    }

    public void setCarga(Double carga) {
        this.carga = carga;
    }

    public String getNomeExercicio() {
        return nomeExercicio;
    }

    public void setNomeExercicio(String nomeExercicio) {
        this.nomeExercicio = nomeExercicio;
    }
}