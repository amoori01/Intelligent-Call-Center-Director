🧠 Intelligent Call Center Director

This problem asks you to simulate a busy call center system that distributes calls to agents intelligently based on:

Call priority

Waiting time

Agent availability

🎯 System Objective

To build a program that simulates how a real call center operates over time.

Everything happens step by step using a command called:

👉 TICK = the passage of one unit of time

📞 Types of Calls

There are only two types of calls:

Type	Description	Priority
General	General inquiry	Normal
Tech	Technical support	High
🧾 Information for Each Call

Each call contains:

timestamp → time of arrival

customer_id → customer number

type → General or Tech

Waiting time (calculated automatically)

🧩 Required Data Structures
1️⃣ Circular Queue

Used for:

🔹 General calls

Reason:

Strict FIFO order (First In, First Out)

Limited capacity

Realistic representation of call centers

2️⃣ Linked List / Deque

Used for:

🔹 High Priority Queue (Tech + Escalated calls)

Reason:

Insertion from multiple ends

Flexible when escalation happens

3️⃣ List of Agent Objects

Represents the employees.

Each agent has:

ID

Status (Busy / Available)

The call being handled

Remaining time to finish the call

⏱️ System Rules
🔺 1. Priority

Agents always take High Priority calls first

Then General calls

🔁 2. Escalation

If:

🟡 a General call
⏱️ waits more than 10 time units

➡️ it becomes High Priority
➡️ it is moved to the end of the high-priority queue

❌ 3. Call Abandonment

If any call:

⏱️ waits more than 20 time units

📴 the customer hangs up
❌ the call is removed from the queue
📊 it is recorded as a MISSED call

⏳ Time (TICK)

Every time the command is executed:

TICK


The following happens:

Time increases by 1

Remaining time for agents’ calls decreases

If a call finishes → the agent becomes available

New calls are assigned

Escalation is checked

Call abandonment is checked

🧑‍💼 Call Assignment

When an agent becomes available:

1️⃣ Take from the High Priority Queue
2️⃣ If empty → take from the General Queue

🧪 Simple Example
Commands:
ASSIGN_AGENTS 2
CALL C1 General 0
CALL C2 Tech 0

At TICK:

Agent 1 takes C2 (Tech)

Agent 2 takes C1 (General)

Because:
👉 Technical support has higher priority.

After 10 time units:

C3 was a General call

It waited more than 10 units

➡️ It is escalated to High Priority

After 20 time units:

Any call not yet served

❌ is removed
📌 counted as a missed call (MISSED)

📊 STATUS Command Displays:

High Priority Queue

General Queue

Busy agents

Number of missed calls

Example:

High Priority Queue: [C3]
General Queue: [C4]
Busy Agents: [Ag1: C2, Ag2: C1]
Missed Calls: 1
