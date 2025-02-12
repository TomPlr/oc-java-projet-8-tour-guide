# Tour Guide Application

Tour Guide Application is a Java-based project designed to assist users in exploring new cities by providing personalized recommendations based on their preferences and location. This repository contains the source code and necessary configurations for the project.

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Installing Dependencies](#installing-dependencies)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Location Tracking**: Retrieve the current location of a user.
- **Nearby Attractions**: Get a list of nearby attractions based on the user's location.
- **Rewards System**: View rewards earned by the user for visiting attractions.
- **Trip Deals**: Access trip deals offered by various providers.

## Getting Started

### Prerequisites

Before you begin, ensure you have met the following requirements:
- Java Development Kit (JDK) 17 or later
- Maven
- An Integrated Development Environment (IDE) such as IntelliJ IDEA or Eclipse

### Installation

1. **Clone the repository**:
   ```sh
   git clone https://github.com/TomPlr/oc-java-projet-8-tour-guide.git
   ```

2. **Navigate to the project directory**:
   ```sh
   cd oc-java-projet-8-tour-guide
   ```

3. **Configure environment variables**:
   Set up necessary environment variables for API keys and other configurations.

4. **Build the project**:
   ```sh
   mvn clean install
   ```

### Installing Dependencies

This project relies on several external JAR files that need to be installed manually into your local Maven repository. Follow these steps to install the required dependencies:

1. **Install `gpsUtil`**:
   ```sh
   mvn install:install-file -Dfile=/libs/gpsUtil.jar -DgroupId=gpsUtil -DartifactId=gpsUtil -Dversion=1.0.0 -Dpackaging=jar
   ```

2. **Install `RewardCentral`**:
   ```sh
   mvn install:install-file -Dfile=/libs/RewardCentral.jar -DgroupId=rewardCentral -DartifactId=rewardCentral -Dversion=1.0.0 -Dpackaging=jar
   ```

3. **Install `TripPricer`**:
   ```sh
   mvn install:install-file -Dfile=/libs/TripPricer.jar -DgroupId=tripPricer -DartifactId=tripPricer -Dversion=1.0.0 -Dpackaging=jar
   ```

### Run the application

After installing the dependencies, you can run the application using:
```sh
mvn spring-boot:run
```

## Usage

Once the application is running, you can access the API endpoints to perform various operations. Below are the available endpoints:

- **Home**:
  - `GET /`: Returns a greeting message.

- **Location Management**:
  - `GET /getLocation?userName={userName}`: Retrieve the current location of a user by their username.

- **Nearby Attractions**:
  - `GET /getNearbyAttractions?userName={userName}`: Get a list of nearby attractions based on the user's location.

- **Rewards Management**:
  - `GET /getRewards?userName={userName}`: View rewards earned by the user for visiting attractions.

- **Trip Deals**:
  - `GET /getTripDeals?userName={userName}`: Access trip deals offered by various providers.

## Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

Distributed under the MIT License. See `LICENSE` for more information.
