package es.uv.and.vas.valenbisi;

class Geometry {
    public String type = "";
    public float[] coordinates = new float[2];

    Geometry(String type, float[] coordinates){
        this.type = type;
        this.coordinates = coordinates;
    }
}