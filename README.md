# World-Bank Demographic Visualizer

## About
The goal of this project is to design and implement a software application capable of interacting with the 
World Bank data repository, and acquiring data pertaining to either public health or environmental impact 
in the selected country per capita. This information will then be presented to the user through a selection 
of visual aids like bar graphs and pie charts. To aid with the acquisition of data, the Java API for REST will 
be used, as this interface is how the World Bank provides most of its datasets to the requester.
The application is built using the Java programming language and sees the user/customer interact with a 
drop-down menu system GUI for selecting various analysis criteria. These include which country they wish to view 
the demographic data for, how they wish to analyze this data, i.e. health metrics vs environmental metrics, 
and finally how they wish to display the data. The application utilizes the REST-API interface to 
communicate with the World Bank database, and proceeds to collect, analyze, and render the results 
based on the user-defined settings. The user may then proceed to either add or remove different viewer styles 
for showcasing the data through the GUI. Hence, the main objectives of this project are as follows:
* Retrieve demographic data for a country selected from the World Bank’s data repository.
* Process this data based on at least eight different health, and environment evaluation metrics.
* Render these results using multiple appropriately selected visualization means, such as bar 
graphs, pie charts, scatter plots etc.

## Structure
The repository is split into two directories for convinience. The *Maven Project* directory is an amalgamation of all the source files
as seen in the Eclipse IDE, along with supporting files needed for making it a portable Maven project for ease of access. The *pom.xml* and *.project* files initiates the project as it needs to build in the IDE, and was the main source for editing and compiling the application.

The *docs* repository is a colection of all the Javadoc generated HTML documentation about the project, should the TAs ever want to learn about the particular implementation of the project as a whole, or any individual class file.

## Building
The project is most easily assessible by downloading the latest version of Eclipse IDE on your local machine and importing
the Maven project through the **pom.xml** file to set everything up in terms of verisioning and dependencies.

The main function can be found in the **Application.java** file under the src folder. Execute the application by running this file as a Java application through the IDE. 

## Application
The application launches by presenting the user with the login screen. Note that the dimensions of the login screen may vary based on your machine, but the window may be resized as needed to visualize the text fields and login button.

The valid login credential combinations may be found in the *credential_database.txt* file. There are currently four valid login credentials, representing one per group member of this project. Note that there are no spaces in either the usernames or the passwords. Providing the valid set of credentials will then launch the main UI window and the user may proceed to interact with the drop-down menus as needed for data visualization.

### Database Files
The application makes use of a total of five text-based database files for performing its functions. These are described as follows:
* **country_analysis:** A collection of what countries may be used for each analysis type. Each line represents a different analysis type.
* **country_list:** A collection of all the countries that the application can attempt to fetch the data for from the World-Bank.
* **credential_database:** A collection of all the valid login credentials used for getting access to the application.
* **viewer_analysis:** A collection of what viewers may be used for each analysis type. Each line represents a different analysis type.
* **year_analysis:** A collection of the valid year range for each analysis type. Each line represents a different analysis type.

## Assistance 
Should the marking TAs have any questions about how the repository is structured, or problems compiling any of the programs, please
do reach out to any one of the team members and we can offer an alternative solution for demonstrating the work we have accomplished.

## Team Members
* Ali Sarfraz
* Matthew Bertuzzi
* Mohammad Iqbal
* Sanjayan Kulendran
