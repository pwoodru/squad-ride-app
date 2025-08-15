# Squad Ride Setup Guide

This guide will help you set up and run the Squad Ride application on your local machine.

## Prerequisites

Before you begin, ensure you have the following installed:

### Java Install Instructions
- **Java 17 or higher** - [Download here](https://adoptium.net/)
- The link above takes you to a page for Adoptium's Eclipse Temurin JDK.
- Temurin is used by Microsoft Azure, AWS, and Google Cloud. It is free and legit.
- Click Download on the front page and save it somewhere you'll remember, preferably somewhere close to your squad ride app folder
- Run the installer you downloaded
- **IMPORTANT** When you reach the installer section allowing you to click icons and select options to install, make sure to change the icon next to
"Set or override JAVA_HOME variable" from an 'X' to "Will be installed on local hard drive"
- Finish the installation

### Node Install Instructions
- **Node.js 16 or higher** - [Download here](https://nodejs.org/)
- Go to the link above and click "Get Node.js"
- Select "Windows Installer (.msi)"
- Save this file somewhere you will remember, preferably close to your squad ride app folder
- Run the installer and follow the instructions to complete the Node.js install

### Maven Install Instructions
- **Maven 3.6 or higher** - [Download here](https://maven.apache.org/)
- The link above takes you to the official Apache Maven website
- Next to 'Use' on the front page, click "Download"
- Under the section titled "Files", find "Binary zip archive"
- Click the link for "apache-maven-3.9.11-bin.zip" to download the correct file
- Save this file somewhere you will remember, preferably close to your squad ride app folder
- Once saved, right-click the .zip file and choose "Extract All..." to extract the contents to your current file location
- Once extracted, the file location you are in will now have a folder called "apache-maven-3.9.11-bin"
- Go inside this folder, you will see another folder called "apache-maven-3.9.11"
- Go inside this folder, you will now see multiple folders and files
- Select the folder called "bin", right-click it, and select "Copy as Path"
- Click your Windows button (or press your Windows key on your keyboard) to open the Windows search
- Type "Environment Variables" and select the option "Edit the system environment variables" for the control panel
- This will open a "System Properties" window. Click the button near the bottom called "Environment Variables"
- The bottom half of the Environment Variables window is sectioned as "System Variables". Find the variable called "Path" and double click it to edit it
- On the "Edit environment variable" window, click the "New" button and paste in the File path that you copied for the Maven bin folder from earlier. Remove the quotations so that it matches the other lines you see.
- It should look something like this: C:\Squad Ride\apache-maven-3.9.11-bin\apache-maven-3.9.11\bin
- Click OK to close the window
- **OPTIONAL** In the System Variables section, double check that you installed Java correctly by looking for the variable called "JAVA_HOME". It should be set to a value similar to this: C:\Program Files\Eclipse Adoptium\jdk-21.0.8.9-hotspot\
- Click OK to close the Environment Variables 
- Click OK to close System Properties and complete your Maven installation

### Git Install Instructions
- **Git** - [Download here](https://git-scm.com/)
- On the website link above, find the orange "Downloads" button
- In the Downloads section, click "Windows"
- At the top of the page titled "Download for Windows" click where it say "Click here to download" the latest
- Save this file somewhere you will remember, preferably close to your squad ride app folder
- Run the downloaded .exe and follow the instructions to install git
- When it asks for your preferred method for editing git files, I recommend **NOT** selecting "Vim" and choosing a more standard editor like Notepad or VS Code

### Docker Install Instructions
- **Docker Desktop** - [Download here](https://www.docker.com/products/docker-desktop/)
- At the link above, hover "Download Docker Desktop" and select "Download for Windows - AMD64". **DO NOT SELECT ARM64**
- Save this file somewhere you will remember, preferably close to your squad ride app folder
- Run the downloaded .exe and follow the instructions to install Docker Desktop
- Once installed, you will need to log out of Windows and log back in to finish the installation
- When prompted to login to Docker, it is suggested to login with GitHub as that will keep the entire project aligned and allow for ease-of-use between all systems
- Once logged in and Docker Desktop is open, click the Settings Gear in the top right
- Find the 'Kubernetes' tab and turn on the option "Enable Kubernetes"
- Select "Apply" in the bottom right and allow it a few minutes to download Kubernetes and set it up for you. You can navigate to the Kubernetes tab in the settings to see the progress. When "docker-desktop" says "Running" with a green circle, you're done in the settings

### Visual Studio Code Install Instructions
- **VS Code** - [Download Here](https://code.visualstudio.com/download)
- Go to the link above and click the big blue button "Windows" to download VS Code for Windows
- Save this file somewhere you will remember, preferably close to your squad ride app folder
- Run the .exe file to install VS Code, follow the instructions to complete installation

### VS Code Plugin Install Instructions
- Once you have VS Code installed and open, don't worry about opening a project or pulling in a repository yet. Those will be the next steps after we get the necessary plugins installed.
- Click the icon on the left side of VS Code that looks like four square blocks with the top right block slightly tilted. It should be labelled "Extensions" when you hover it.
- In the search bar type "Vue" and install the extension titled "Vue (Official)" with the checkmark icon below it
- Now, search for "GitHub Actions" and install the extension with the checkmark icon below it
- Now, search for "Docker" and install the extension with the checkmark icon below it
- Now, search for "Kubernetes" and install the extension with the checkmark icon below it
- The rest of our plugins should automatically install when we pull in our project.
- Let's do that now.

## Code Installation Steps

### 1. Clone the Repository
It's time to get all the code in your system.

- In VS Code, select the icon on the left side of your screen that has three circles and two lines that is called "Source Control" when you hover it
- Select the option to Clone a Repository
- Start typing the name of our project on GitHub, "squad-ride-app" until you see the option for a project by "pwoodru". Select that option to choose this GitHub repository as your codebase
- VS Code should load for a second while it pulls in the project
- Once setup, you will now have the entire code project in VS Code with all of the correct prerequisites installed

### 2. Backend Setup
We have everything installed. We have all the code pulled. This is the moment of truth. If you installed everything correctly there won't be any issues. If you run into errors here... your name must be Dades. Good luck hoss.

- With VS Code open, you'll see the standard bar at the top of the screen starting with File, Edit... etc.
- Select "Terminal" from these options and choose "New Terminal"
- You should see a command terminal pop up at the bottom of your VS Code
- Click in the terminal and type the command "pwd", then press enter ('pwd' stands for "Print Working Directory", it shows you the file path your terminal is looking at right now)
- Now, type the command "ls", then press enter ('ls' stands for "list", it shows you a list of all the files and folders in your current file location)
- If you see the folder named "backend", type the command "cd backend" and press enter ('cd' stands for "Change Directory", this moves your terminal location to the folder you specify)
- If the path shows that you are inside the "backend" folder (you can type "pwd" again and press enter to double check), you can proceed to the final step.
- **NOTE** You can type the command "cd .." to move backwards a folder if you end up changing to the wrong place.
- Once inside the backend folder, type the command "mvn clean install" to run Maven and build the backend portion of the code. Good luck. If you run into errors, go to the project file location "target/surefire-reports/" and put the error log files into an AI like Claude or ChatGPT. It can help you more than anyone at this stage.
- If you see "Build Successful", you've gotten through the hardest part of this entire installation. Congratulations Dades.
- Now run the following command to generate the final files Maven will need: "mvn -N wrapper:wrapper"

### 3. Frontend Setup
This is a similar process to the backend setup, but considerably easier and less prone to errors than Maven.

- Open a New Terminal (or use your currently opened terminal and the commands provided above)
- Navigate to the "frontend" folder by typing the command "cd frontend" and pressing enter
- Install the required frontend dependencies by typing the command "npm install" and pressing enter 
- As long as you don't see red errors, your frontend is now built properly.
- We're on the home stretch

### Final Build
- Navigate in your terminal to the root directory "squad-ride-app"
- Make sure your Docker Desktop app is open and the engine is running
- Type the command "docker-compose build --no-cache"
- If you see a success, move to the final step: Running the app.

## Run the Application

Run the entire application with Docker:
- Make sure your Docker Desktop app is up and the engine is running
- In the terminal of VS Code, navigate to the home folder location "squad-ride-app". Do **NOT** run the following command inside the "backend" or "frontend" folder.
- Type the command "docker-compose up" and press enter

This will start both frontend and backend services.

- The backend will start on `http://localhost:8080`
- The frontend will start on `http://localhost:3000`

#### Verify Backend is running
- Visit `http://localhost:8080/api/health` to check if the API is running
- Access H2 Console at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`, Username: `sa`, Password: empty)

#### Verify Frontend is running
- Visit `http://localhost:3000` to see the Squad Ride application
- The app should display the dark-themed mobile interface

- Launching the application without Docker is possible. Refer to the bottom of these instructions for those commands.

#### Final Comments
- Once you have everything built and you can successfully view the application from the frontend link, you can begin actively coding.
- Create a new branch from the respective release, give it a name recognizable by everyone that it is your branch.
- Begin making code changes. When you edit code anywhere and press CTRL+S to save the file, the frontend webpage will automatically update with your changes.
- If you made a mistake and caused an error, the frontend page will display the location of the error along with a brief description so you can attempt to fix it quickly.
- Happy Coding!

## Extras: Development Workflow

### Backend Development
- Main application class: `src/main/java/com/squadride/app/SquadRideApplication.java`
- Controllers: `src/main/java/com/squadride/app/controller/`
- Services: `src/main/java/com/squadride/app/service/`
- Entities: `src/main/java/com/squadride/app/entity/`

### Frontend Development
- Main Vue app: `src/App.vue`
- Components: `src/components/`
- API composables: `src/composables/`
- Stores: `src/stores/`

### Testing
# Backend tests
- cd backend
- mvn test

# Frontend tests (if configured)
- cd frontend
- npm run test

## Production Build

### Backend
- cd backend
- mvn clean package
- java -jar target/squad-ride-backend-1.0.0.jar

### Frontend
- cd frontend
- npm run build
# Serve the dist folder with your preferred web server

## Troubleshooting

### Common Issues

1. **Port Already in Use**
   - Backend: Change port in `application.yml`
   - Frontend: Change port in `vite.config.js`

2. **Java Version Issues**
   - Ensure Java 17+ is installed and set as JAVA_HOME

3. **Node.js Version Issues**
   - Use Node.js 16+ for compatibility

4. **Database Connection Issues**
   - H2 database runs in memory - no external setup needed
   - Check H2 console at `http://localhost:8080/h2-console`

5. **CORS Issues**
   - CORS is configured in the backend for localhost:3000
   - If using different ports, update `SquadRideApplication.java`

### Performance Tips
- Use `mvn spring-boot:run` for development
- Use `npm run serve` for development
- Use `java -jar` for production
- Enable production builds for better performance