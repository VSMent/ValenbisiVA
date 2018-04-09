package es.uv.and.vas.valenbisi;

class Station {
    public Properties properties = null;
    public Geometry geometry = null;

    Station(Properties properties, Geometry geometry){
        this.properties = properties;
        this.geometry = geometry;
    }
}
