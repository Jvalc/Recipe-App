# Recipe App

## Overview

The Recipe App is a user-friendly application designed to help users discover, manage, and enjoy various recipes. With features such as Google Single Sign-On (SSO) authentication, a searchable recipe database, and timer functionalities, this app aims to enhance the cooking experience.

## Features

- **Google SSO Authentication**: Users can easily log in and authenticate using their Google accounts through Firebase Authentication.
- **Dashboard**: View a comprehensive list of recipes available in the app.
- **Recipe Search**: Search for recipes by:
  - Ingredients
  - Cuisine types
  - Dietary preferences (e.g., vegan, gluten-free)
- **Timer Functionality**: Start, stop, and delete timers for precise cooking.
- **Recipe Management**:
  - View recipes saved in folders
  - Add or organize recipes into customizable folders
- **User Settings**: 
  - Change user settings, including username and dietary preferences
  - Logout option to securely exit the app

## Architecture
- **REST API**: The backend of the app is built using Node.js and Express.
- **Database**: Firestore is used as the database, allowing for real-time data synchronization and storage of user data, recipes, and preferences.
- **Hosting**: The application is hosted on Vercel.

## API Hosting Issues
While the application is fully functional locally, the API hosting on Vercel is currently experiencing errors. When you test the API using Postman or any tool it works. But it does not work when using the hosted link. Troubleshooting steps are being taken to resolve these issues, and updates will be provided as they become available.

## API Repo
https://github.com/JunivevaAlcandra/DishDelightAPI.git
