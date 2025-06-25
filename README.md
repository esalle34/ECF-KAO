# Prerequesites

- Working git installed
- Docker desktop installed
- Minikube and Kubectl installed and configured correctly

# Install

To install this project first download using :

`git clone https://github.com/esalle34/ECF-KAO.git`

# Lambda server and Kubernetes

To successfully install this part, first make sure you have docker desktop available.
Then, go to ECF-KAO/Lambda/serverless/hello-world folder, open a terminal and use the following :

`docker-compose up --build`

This should add a container in your docker desktop environment.
Afterwards, you can ensure that your lambda is working correctly by going to the exec tab then by running following command :

`npx run-func app.mjs lambdaHandler`

It should ask to install new package, proceed by typing yes.

At this point you should have a new log which shows an ok (200 status code), followed by a hello world message.

Now that the image is created we will load it to minikube first by using :

`minikube image load lambda:1.0.1`

You can replace the version after `:` by your version if updated.

Move into the hello-world project folder and start the cluster with :

`kubectl apply -f deployment.yaml`

By running :

`kubectl get deployments`

And waiting some seconds we are now able to see that the pods are ready and available.

Now more in details we can check if the previous hello world is running by first getting container names :

`kubectl get pods`

Then get the pod name you want to check with, and run (replace ${podName} with your pod name):

`kubectl exec --stdin --tty ${podName} -- /bin/bash`

Finally, execute again following:

`npx run-func app.mjs lambdaHandler`

Proceed with package installation and we should now have the previous log information showing.

# Java Hello world

To install this part you will need docker desktop.
Open a terminal then navigate to KAO > ECF > Java.

Make build.sh executable by running `chmod +x build.sh`.
Then type `./build.sh`, it will run a script that will compile Java hello world project, build docker image and then run it in a container:

If everything is fine it should display a status 200 message. You can check by navigating to http://localhost:8080.

Then go to docker desktop and stop the running container.

Next, with a terminal load the image on minikube using :

`minikube image load java-hello:1.0.1`

Then use the following line to deploy :

`kubectl apply -f deployment.yaml`

It should have 3 pods running if you do :

`kubectl get deployments`

Next, to dispatch the load balancer :

`kubectl apply -f service.yaml`

Service should be up if you type :

`kubectl get services`

Finally, use `minikube tunnel` to create a tunnel to the service with minikube.

You should be able to access the hello world by navigating to : http://localhost:8080 unless you changed the port.

# Angular Hello world

For this part you only need to execute build.sh which is in Angular folder. You might need yo use chmod.x.

You can deploy it in kubernetes using previous command lines from Java Hello world part replacing java-hello with angular-hello.

build.sh will execute ng test then ng build and finally build the docker image.

# Elastic - Kibana - Java on Kubernetes

- Elasticsearch

Start docker desktop open a terminal in the Java folder and type :

`kubectl create -f https://download.elastic.co/downloads/eck/3.0.0/crds.yaml`

`kubectl apply -f https://download.elastic.co/downloads/eck/3.0.0/operator.yaml`

This will download the required crds for Elasticsearch's and Kibana's kubernetes deployment.
Then, start elasticsearch by typing :

`kubectl apply -f elasticsearch.yaml`

Wait for the service to run by typing :

`kubectl get elasticsearch elasticsearch`

You might have a yellow health but this is because we disabled self-signed certificate.

- Connecting Hello-world :

Next, run :

`kubectl get secret elasticsearch-es-elastic-user -o=jsonpath='{.data.elastic}' | base64 --decode; echo `

This will print the superuser password, copy this one and past it in the .env folder, then run following :

`kubectl create configmap java-hello --from-env-file=.env`

(If you ever changed the namespace or the service name in elasticsearch.yaml you might need to change the DNS used :
`elasticsearch-es-default.default.svc.cluster.local:9200`, `${metadata.name}-es-default.${namespace}.svc.cluster.local:9200` accordingly.)

Check if the java-hello configmap is correctly loaded :

`kubectl describe configmap java-hello`

All data should be correctly set.
If started, delete your java-hello deployment :

`kubectl delete deploy java-hello`

Restart it with :

`kubectl apply -f deployment-elasticsearch.yaml`

Start the services :

`kubectl apply -f service.yaml`

Open a tunnel :

`minikube tunnel`

- Kibana :

Run Kibana by using :

`kubectl apply -f kibana.yaml`

Wait until service is up by running:

`kubectl get kibana kibana`

Once up use :

`kubectl port-forward services/kibana-kb-http 5601`

Open `http://localhost:5601`, switch to Discover to open search panel, create a new view (Data View > Manage this Data view) based on Index. Use this view and it will show you a list of persons. You can then use searchbar with KQL to find specitic data.

# What we should do next :

- Find a way to turn on ssl in localhost to have a most secure version of the stack.