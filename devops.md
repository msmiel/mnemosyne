# DevOps / DevSecOps / SRE

## Links

- [Pager Duty: Article Library on Response](https://response.pagerduty.com/)
- [How to Become Professional](https://hackernoon.com/the-roadmap-to-become-a-devops-dude-from-server-to-serverless-dd97420f640e)
- [Kubernetes The Hard Way](https://github.com/kelseyhightower/kubernetes-the-hard-way)
- [How to Get into SRE](https://blog.alicegoldfuss.com/how-to-get-into-sre/)
- [Studying the Kubernetes Ingress System](https://www.joyfulbikeshedding.com/blog/2018-03-26-studying-the-kubernetes-ingress-system.html)
- [Jenkins Pipeline Syntax](https://jenkins.io/doc/book/pipeline/syntax/)
- [When Containers Become Trash Cans](https://dev.to/rionmonster/when-containers-become-trashcans-2kbb)

## Wisdom

### The 4 Golden Signals

Track these on a per RPC method level.

- Latency
- Errors
- Traffic
- Saturation

Establish alerts around the 95th or 5th percentile.
Runbooks on how to triage alerts. What does the alert mean? Where to find logs, and how many times the alert occurred previously.

### Runbooks

- "Your runbooks are useful for knowing what to do to stop the spread of the fire, but if you know exactly what _causes_ your problems, you'd have automated the solution already. On a mature SRE team, every failure is novel, so don't overinvest in runbooks." -Liz Fong-Jones (2018-09-01 on Twitter)
