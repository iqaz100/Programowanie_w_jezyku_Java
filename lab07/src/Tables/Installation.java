package Tables;

public class Installation {
    private int id;
    private String address;
    private int idClient;
    private int routerNumber;
    private int serviceType;

    public Installation(){

    }

    public Installation(int id, String address, int idClient, int routerNumber, int serviceType){
        this.id = id;
        this.address = address;
        this.idClient = idClient;
        this.routerNumber = routerNumber;
        this.serviceType = serviceType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getRouterNumber() {
        return routerNumber;
    }

    public void setRouterNumber(int routerNumber) {
        this.routerNumber = routerNumber;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public String toString(){
        return "" + id;
    }
}
