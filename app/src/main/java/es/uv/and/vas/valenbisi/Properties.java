package es.uv.and.vas.valenbisi;

class Properties {
    public String name;
    public int number;
    public String address;
    public String open;
    public int available;
    public int free;
    public int total;
    public String ticket;
    public String updated_at; // 08/04/2018 00:06:15;

    Properties(String name, int number, String address, String open, int available, int free, int total, String ticket, String updated_at){
        this.name = name;
        this.number = number;
        this.address = address;
        this.open = open;
        this.available = available;
        this.free = free;
        this.total = total;
        this.ticket = ticket;
        this.updated_at = updated_at;
    }
}
