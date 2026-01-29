class Call {
    String id;
    String type;
    int arrivalTime;
    int waitTime = 0;
    public Call(String id, String type, int arrivalTime) {
        this.id = id;
        this.type = type;
        this.arrivalTime = arrivalTime;
    }
}