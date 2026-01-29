class Agent {
    int id;
    Call currentCall = null;
    int timeRemaining = 0;
    public Agent(int id) { this.id = id; }
    public boolean isAvailable() { return currentCall == null; }
}