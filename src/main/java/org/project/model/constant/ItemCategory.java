package org.project.model.constant;

public enum ItemCategory {
    A_LA_Carte("a la Carte"),
    APPERTIZERS("appertizers"),
    SIDES("sides"),
    EXTRAS("extras"),
    BEVERAGES("beverages");

    private String name;

    ItemCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
