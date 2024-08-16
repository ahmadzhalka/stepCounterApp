# MyStepCounter

## Table of Contents

- [Introduction](#Introduction)
- [Features](#Features)
- [Activities](#Activities)
- [Services](#Services)
- [Database](#Database)
- [Prerequisites](#Prerequisites)
- [ScreenShot](#ScreenShot)
- [vedio](#vedio)

## Introduction

MyStepCounter is a mobile application designed to track and display step counts using device sensors. It features a user-friendly interface to monitor daily physical activity, visualize data through charts, and manage step records. The app includes a background service for continuous step tracking and utilizes a Room SQL database for data persistence.

## Features

- **Real-Time Step Tracking:** Continuously monitors and updates step counts.
- **Historical Data Visualization:** Displays step counts over time using charts.
- **Daily Statistics:** Shows step counts, calories burned, and distance traveled.
- **Background Service:** Runs a foreground service to track steps even when the app is not in the foreground.
- **Data Persistence:** Uses Room SQL database for storing and managing step count data.

## Components

### Activities

- **LoginActivity**
  - Handles user permissions for activity recognition.
  - Sets up the step detector and starts the background service.

- **MainActivity**
  - Displays current step count, calories burned, and distance traveled.
  - Launches the `GraphActivity` to view historical step data.

- **GraphActivity**
  - Visualizes step counts using a bar chart.
  - Updates the chart with historical data from the Room SQL database.

### Services

- **StepCounterService**
  - A foreground service that continuously tracks steps.
  - Runs in the background to ensure step counting is consistent even when the app is not actively used.

### Database

- **Room SQL Database**
  - **Entity:** `StepCount` - Represents a record of step counts with date and counter fields.
  - **DAO:** Provides methods to access and manipulate step count data.
  - **Database:** Manages the database instance and provides access to DAO.


### Prerequisites

- Android Studio
- Android device or emulator


### ScreenShot


<img width="351" alt="צילום מסך 2024-08-16 ב-17 26 40" src="https://github.com/user-attachments/assets/5e5e5c26-8b73-4ba5-87bc-f4d8f26823b2">

![1723817921312](https://github.com/user-attachments/assets/efeed976-85b6-4f76-a00e-e2649eb02262)

![1723817921302](https://github.com/user-attachments/assets/0b9fbf96-132a-4ad2-a063-67f89b80b4ec)

![1723817921291](https://github.com/user-attachments/assets/25970dc8-cd87-47c1-be51-8917703a354d)

<img width="350" alt="צילום מסך 2024-08-16 ב-17 27 21" src="https://github.com/user-attachments/assets/36e32661-e95a-4cff-9698-992032082f32">


### vedio

https://github.com/user-attachments/assets/eba04261-fa35-4833-8dbf-3c2948bffac3

