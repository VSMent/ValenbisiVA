package es.uv.and.vas.valenbisi;

class Geometry {
    public String type = "";
    public double[] coordinates = new double[2];

    Geometry(String type, double[] coordinates){
        this.type = type;
        this.coordinates = coordinates;
    }
}