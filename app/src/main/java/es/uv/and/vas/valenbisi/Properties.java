package es.uv.and.vas.valenbisi;

import java.util.Date;

public class Properties {
    public String name = "";
    public int number = 0;
    public String address = "";
    public String open = "";
    public int available = 0;
    public int free = 0;
    public int total = 0;
    public String ticket = "";
    public String updated_at = null; // 08/04/2018 00:06:15;

//        public Date updated_at = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH).parse("01/01/2018 00:00:01",0 ); // 01/01/2018 00:00:01;
//        public LocalDate updated_at = LocalDate.parse("01/01/2018 00:00:01", DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH)); // 01/01/2018 00:00:01;


//        String string = "January 2, 2010"; 
//        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH); 
//        Date date = format.parse(string); 
//        System.out.println(date); // Sat Jan 02 00:00:00 GMT 2010

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
