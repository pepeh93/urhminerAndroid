package com.urh.model;

public class Content {
    private int id;
    private String name;
    private String algorithm;
    private String country;
    private int year;
    private int block_amount;
    private String unit;
    private Double maximum_value;
    private String description;
    private String video_id;
    private String image;
    private int content_type_id;
    private String descripcion;
    private Boolean favorito;
    private String extra_button_name;

    public String getExtraButtonLink() {
        return extra_button_link;
    }

    public String getExtraButtonName() {
        return extra_button_name;
    }

    private String extra_button_link;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getCountry() {
        return country;
    }

    public int getYear() {
        return year;
    }

    public int getBlock_amount() {
        return block_amount;
    }

    public String getUnit() {
        return unit;
    }

    public Double getMaximum_value() {
        return maximum_value;
    }

    public String getDescription() {
        return description;
    }

    public String getVideo_id() {
        return video_id;
    }

    public String getImage() {
        return image;
    }

    public int getContent_type_id() {
        return content_type_id;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }

    public int getTipoContenido() {
        return content_type_id;
    }

    public Content(String name,
                  String image) {
        this.name = name;
        this.image = image;
    }

    public Boolean getFavorito() {
        return favorito;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
}
