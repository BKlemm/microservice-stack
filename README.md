# Full Modern Microservice Stack

This is a full stack development skeleton to build modern professional enterprise applications in a cloud as mircoservices. In this state all configurations are for develop mode (local).
**Don't use that configurations on production mode!**
Primly I created this skeleton to use it by my self, to create further applications.
Time to time I will update this repo with the improvements from my other developments.
You are free to use it by your self or to contribute improvements.

## Getting Started

These instructions will get you a copy of the project (clone repository) up.


**Step 1:** Install OpenJDK on your System, if not exists

**Step 2:** Install CLI Cloud Foundry

**Step 3:** Install and Build Application
```
./mvnw package
```
**Step 4:** Login to your cloud foundry instance (self hosted or use a provider)
```
cf login -a <cloud foundry instance> -u <USERNAME>
```

**Step 5:** Deploy on your cloud foundry instance 
```
cf push first-push -b go_buildpack -m 64M --random-route
```
