# Prerequesites

- Working git installed
- Docker desktop installed
- Minikube and Kubectl installed and configured correctly

# Install

To install this project first download using :

`git clone https://github.com/esalle34/ECF-KAO.git`

# Lambda server and Kubernetes

To successfully install this part, first make sure you have docker desktop available.
Then, go to hello-world folder, open a terminal and use the following :

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

Then get the container name you want to check with, and run (replace ${containerName} with your container name):

`kubectl exec --stdin --tty ${containerName} -- /bin/bash`

Finally, execute again following:

`npx run-func app.mjs lambdaHandler`

Proceed with package installation and we should now have the previous log information showing.

# Java Hello world

To install this part you will need docker desktop.
Open a terminal then navigate to KAO > ECF > Java > hello-world.

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
