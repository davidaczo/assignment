This README provides comprehensive documentation detailing steps for building, running, and utilizing the application.

Frontend:
	-A simple frontend for starting OSINT scans with 2 tools: TheHarvester and Amass.
	-For Amass scan only a domain name is requestet, for TheHarvester we need to select the datasource for scanning.
	-If a scan is started it will show up in the History section.On the card only important informations are visible like begin time, domain name, tool name, etc... But if you want to see the scan's result you have to click on Details link on the bottom left corner.
	
	Usage:
		-Download necessary dependencies with: npm install.
		-Start the application by running: npm start.
		-Open your web browser and navigate to http://localhost:3000 to view the application.
		-Use the application according to its features.
		

Backend:
	-A simple backend to serve the frontend's features.
	
	Usage:
		-Open the application in intelliJ.
		-Download the dependencies.
		-Build with maven.
		-Start the application.
		or
		-mvn clean package
		-java -jar target/backend-app.jar
		

