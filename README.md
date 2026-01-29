# 🧠 Intelligent Call Center Director

Simulate a busy call center that distributes calls to agents intelligently based on:
- Call priority
- Waiting time
- Agent availability

This repository models a step-by-step simulation where every step is a single unit of time called a `TICK`.

---

## 🎯 Objective

Build a simulation that mimics how a real call center operates over time, assigning calls to agents and handling escalation, abandonment, and prioritization.

---

## Table of Contents

- [Call Types](#call-types)
- [Call Data](#call-data)
- [Data Structures](#data-structures)
- [System Rules](#system-rules)
- [Time and TICK](#time-and-tick)
- [Call Assignment](#call-assignment)
- [Commands & Example](#commands--example)
- [STATUS Output Example](#status-output-example)
  
---

## 📞 Call Types

There are two call types:

- **General** — General inquiry (Priority: Normal)
- **Tech** — Technical support (Priority: High)

---

## 🧾 Call Data

Each call includes:
- `timestamp` — time of arrival
- `customer_id` — customer identifier
- `type` — `General` or `Tech`
- `waiting_time` — computed automatically in the simulation

---

## 🧩 Required Data Structures

1. **Circular Queue**  
   - Used for General calls  
   - Enforces strict FIFO order and models limited capacity

2. **Linked List / Deque**  
   - Used for the High Priority queue (Tech + Escalated calls)  
   - Allows insertion from both ends and flexible reordering for escalations

3. **List of Agent Objects**  
   Each agent has:
   - `ID`
   - `Status` (Busy / Available)
   - `current_call` (call being handled)
   - `remaining_time` (time left to finish the call)

---

## ⏱️ System Rules

1. **Priority**  
   - Agents always serve High Priority calls first, then General calls.

2. **Escalation**  
   - If a General call waits more than **10** time units it is escalated to High Priority and moved to the *end* of the high-priority queue.

3. **Call Abandonment**  
   - If any call waits more than **20** time units, the caller hangs up. The call is removed and counted as a **MISSED** call.

---

## ⏳ Time (TICK)

Each `TICK` (one time unit) triggers:
- Global time increment by 1
- Decrement of `remaining_time` for busy agents
- Agents finishing calls become available
- New calls are assigned (see assignment rules)
- Escalation checks for long-waiting General calls
- Abandonment checks for calls waiting > 20 units

---

## 🧑‍💼 Call Assignment

When an agent becomes available:
1. Take the next call from the **High Priority Queue**
2. If it is empty, take the next call from the **General Queue**

---

## 🧪 Simple Example

Commands:
```
ASSIGN_AGENTS 2
CALL C1 General 0
CALL C2 Tech 0
TICK
```

At the first `TICK`:
- Agent 1 → C2 (Tech, High Priority)
- Agent 2 → C1 (General)

Escalation example:
- A General call C3 waits > 10 ticks → moved to High Priority queue (end).

Abandonment example:
- Any call waiting > 20 ticks → removed and counted as MISSED.

---

## 📊 STATUS Command Displays

The `STATUS` command should show:
- High Priority Queue (order)
- General Queue (order)
- Busy agents and their assigned calls
- Number of missed calls

Example:
```
High Priority Queue: [C3]
General Queue: [C4]
Busy Agents: [Ag1: C2, Ag2: C1]
Missed Calls: 1
```

---

## ✅ Implementation Notes & Suggestions

- Use the Circular Queue with a fixed capacity to model real-world queue limits.
- Implement the High Priority queue as a deque to allow escalated calls to be appended.
- Maintain timestamps on all calls to compute waiting time and trigger escalation/abandonment.
- Ensure agent scheduling picks high-priority calls first each `TICK` before considering general calls.

---


