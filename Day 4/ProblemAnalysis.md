# Problem Analysis

## Problem 1

### Given
1) List of log entries that are not ordered.
2) each log entry consists of date and message. Message is one of three forms
    a) Guard #{id} begins shift -> Denotes that a new guard has started their shift
    b) wakes up -> Denotes guard has woken up. This corresponds to the guard which had most recently started their shift
    c) falls asleep -> Denotes the guard has fallen asleep. This correcsponds to the guard which had most recently started their shift

### Want to know
1) The id of the guard which has the most minutes asleep
2) The minute for which the aforementioned guard is asleep the most

### Algorithm
1) Read the log entries 