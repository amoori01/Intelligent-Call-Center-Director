import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CallCenterSimulation {
    private static List<Agent> agents = new ArrayList<>();
    private static Deque<Call> highPriorityQueue = new LinkedList<>();
    private static List<Call> generalQueue = new ArrayList<>(); // Using List for easier escalation/removal
    private static int missedCalls = 0;
    private static int currentTime = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Intelligent Call Center Simulator ---");
        System.out.println("Commands: ASSIGN_AGENTS n, CALL id type, TICK, STATUS, EXIT");

        while (true) {
            System.out.print("> ");
            if (!scanner.hasNextLine()) break;
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("EXIT")) break;

            processCommand(input);
        }
        scanner.close();
    }

    private static void processCommand(String input) {
        String[] parts = input.split(" ");
        String cmd = parts[0].toUpperCase();

        switch (cmd) {
            case "ASSIGN_AGENTS":
                int num = Integer.parseInt(parts[1]);
                agents.clear();
                for (int i = 1; i <= num; i++) agents.add(new Agent(i));
                System.out.println("Configured " + num + " agents.");
                break;

            case "CALL":
                String id = parts[1];
                String type = parts[2];
                Call newCall = new Call(id, type, currentTime);
                if (type.equalsIgnoreCase("Tech")) {
                    highPriorityQueue.addLast(newCall);
                } else {
                    generalQueue.add(newCall);
                }
                System.out.println("Call " + id + " received.");
                break;

            case "TICK":
                runTick();
                break;

            case "STATUS":
                printStatus();
                break;

            default:
                System.out.println("Unknown command.");
        }
    }

    private static void runTick() {
        currentTime++;
        System.out.println("\n[TICK: Time " + (currentTime-1) + " -> " + currentTime + "]");

        // 1. Process Busy Agents
        for (Agent a : agents) {
            if (!a.isAvailable()) {
                a.timeRemaining--;
                if (a.timeRemaining <= 0) {
                    System.out.println("- Agent " + a.id + " finished call " + a.currentCall.id);
                    a.currentCall = null;
                }
            }
        }

        // 2. Escalation and Abandonment
        Iterator<Call> it = generalQueue.iterator();
        while (it.hasNext()) {
            Call c = it.next();
            c.waitTime++;
            if (c.waitTime >= 20) {
                System.out.println("- " + c.id + " MISSED (waited 20)");
                missedCalls++;
                it.remove();
            } else if (c.waitTime >= 10) {
                System.out.println("- " + c.id + " Escalated to High Priority");
                highPriorityQueue.addLast(c);
                it.remove();
            }
        }

        // 3. Assign Calls
        for (Agent a : agents) {
            if (a.isAvailable()) {
                if (!highPriorityQueue.isEmpty()) {
                    a.currentCall = highPriorityQueue.pollFirst();
                    a.timeRemaining = 10; // Call duration
                    System.out.println("- Agent " + a.id + " takes " + a.currentCall.id + " (Tech/Escalated)");
                } else if (!generalQueue.isEmpty()) {
                    a.currentCall = generalQueue.remove(0);
                    a.timeRemaining = 10;
                    System.out.println("- Agent " + a.id + " takes " + a.currentCall.id + " (General)");
                }
            }
        }
    }

    private static void printStatus() {
        System.out.println("\n--- STATUS ---");
        
        System.out.print("High Priority Queue: [");
        for(Call c : highPriorityQueue) System.out.print(c.id + " ");
        System.out.println("]");

        System.out.print("General Queue: [");
        for(Call c : generalQueue) System.out.print(c.id + " ");
        System.out.println("]");

        System.out.print("Busy Agents: [");
        for(Agent a : agents) {
            if(!a.isAvailable()) System.out.print("Ag" + a.id + ": " + a.currentCall.id + " ");
        }
        System.out.println("]");
        System.out.println("Missed Calls: " + missedCalls);
        System.out.println("--------------\n");
    }
}