
# OSINT Scan Application

This README provides comprehensive documentation detailing steps for building, running, and utilizing the application.

## Running with Docker

To run the application with Docker, you'll need to follow these steps:

1. **Frontend Docker Image**: [davidaczo/frontend](https://hub.docker.com/repository/docker/davidaczo/frontend)

2. **Backend Docker Image**: [davidaczo/back-end](https://hub.docker.com/repository/docker/davidaczo/back-end)

### Initiating Docker Images Locally

To initiate the Docker images locally, follow these steps:

- Run the command `docker compose up`. This command will pull the necessary Docker images from Docker Hub and start the containers.

**Note:** If you are running the application on a Windows machine, you need to change the Docker host in `MyDockerClient` to `tcp://localhost:2375`. Here's how:

Change:

```plaintext
"/var/run/docker.sock:/var/run/docker.sock"
```

To:

```plaintext
"localhost:2375:/var/run/docker.sock"
```

## Frontend

- **Docker Image**: [davidaczo/frontend](https://hub.docker.com/repository/docker/davidaczo/frontend)

- A simple frontend for initiating OSINT scans with two tools: TheHarvester and Amass.

- For an Amass scan, only a domain name is required. For TheHarvester, you need to select the data source for scanning.

- When a scan is started, it will appear in the History section. On the card, only important information is visible, such as the start time, domain name, tool name, etc. To view the scan results, click on the "Details" link in the bottom left corner.

### Usage

1. Download necessary dependencies with `npm install`.
2. Start the application by running `npm start`.
3. Open your web browser and navigate to [http://localhost:3000](http://localhost:3000) to view the application.
4. Use the application according to its features.

## Backend

- **Docker Image**: [davidaczo/back-end](https://hub.docker.com/repository/docker/davidaczo/back-end)

- A simple backend to serve the frontend's features.

### Important Note for Windows Users

If you are running the application on Windows, you need to change the Docker host in `MyDockerClient` to `"tcp://localhost:2375"`.

### Usage

1. Open the application in IntelliJ IDEA.
2. Download the dependencies.
3. Build with Maven.
4. Start the application.


or


1. Run `mvn clean package`.
2. Execute `java -jar target/backend-app.jar`.

---

This corrected version addresses the formatting issues, clarifies the instructions, and fixes any potential mistakes.