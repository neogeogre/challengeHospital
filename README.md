### challenge

Answer to an interview challenge.

- with kotlin best practices
- use `assertj`
- build fat jar

build with:
```shell
./gradlew clean build
```

run with:
```shell
cd build/libs
java -jar challengeHospital-2.0.0.jar "F,D,T,T" "P,An"
```



## Coding challenge

Problem description

You were asked by a doctor friend to prepare for her a “Hospital simulator”, which can
simulate the future patients’ state, based on their current state and a list of drugs they take.
Patients can have one of these states:
- F: Fever
- H: Healthy
- D: Diabetes
- T: Tuberculosis
- X: Dead

In the “Hospital simulator” drugs are provided to all patients. It is not possible to target a
specific patient. This is the list of available drugs:
- As: Aspirin
- An: Antibiotic
- I: Insulin
- P: Paracetamol

Drugs can change patients’ states. They can cure, cause side effects or even kill a patient if
not properly prescribed.
Drugs effects are described by the following rules:
- Aspirin cures Fever;
- Antibiotic cures Tuberculosis;
- A sick patient not receiving the right medicines remains sick, if not explicitly
mentioned otherwise;
- Insulin prevents diabetic subject from dying, does not cure Diabetes;
- If insulin is mixed with antibiotic, healthy people catch Fever;
- Paracetamol cures Fever;
- Paracetamol kills subject if mixed with aspirin;
- With a probability of 1/1,000,000 the Flying Spaghetti Monster shows his noodly power and resurrects a dead patient (Dead becomes Healthy).

While applying the rules, please keep in mind that:
- During one simulation a patient can change his state only once (only one rule can be
applied).
- Rules causing Death take precedence over others.

Input
Parameter 1
List of patients' health status codes, separated by a comma. e.g. “D,F,F” means we have 3
patients, one with diabetes and two with fever.
Parameter 2
List of drugs codes, separated by a comma, e.g. “As,I” means patients will be treated with
Aspirin and Insulin.
Output
The result should be sent to stdout.
It should be a comma separated string with number of patients with a given state, following
the format:
F:NP,H:NP,D:NP,T:NP,X:NP
Where:
- F, H, D, T, X are patients’ health status codes;
- NP is a number of patients for a given state;
E.g. “F:0,H:2,D:0,T:0,X:1” means there are two healthy patients and one that is dead.

Examples

1. Input: `"D,D" ""`
Output: `"F:0,H:0,D:0,T:0,X:2"` (diabetic patients die without insulin)
```shell
$ java -cp hospital-simulator.jar com.edgelab.hospital.Application D,D
F:0,H:0,D:0,T:0,X:2
```

2. Input: `"F" "P"`
Output: `"F:0,H:1,D:0,T:0,X:0"` (paracetamol cures fever)
```shell
$ java -cp hospital-simulator.jar com.edgelab.hospital.Application F P
F:0,H:1,D:0,T:0,X:0
```
