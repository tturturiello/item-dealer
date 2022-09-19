package ch.supsi.webapp.web.model;

public enum ItemType {
    DEMAND( "Demand" ),
    SUPPLY( "Supply" );

    public final String value;

    ItemType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
