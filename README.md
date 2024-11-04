# Dish Delight App

## Overview

The Dish Delight is a user-friendly application designed to help users discover, manage, and enjoy various recipes. With features such as Google Single Sign-On (SSO) authentication, a searchable recipe database, and timer functionalities, this app aims to enhance the cooking experience.

## Features

- **Google SSO Authentication**: Users can easily log in and authenticate using their Google accounts through Firebase Authentication.
- **Dashboard**: View a comprehensive list of recipes available in the app.
- **Recipe Search**: Search for recipes by:
  - Ingredients
  - Cuisine types (e.g., American, Thai)
- **Timer Functionality**: Start, stop, and delete timers for precise cooking.
- **Recipe Management**:
  - View recipes saved in folders
  - Add or organize recipes into customizable folders
  - Share recipes
  - Add personalized notes to recipes
  - Download recipes, and enjoy then in offline mode
- **User Settings**: 
  - Change user settings, including username and dietary preferences
  - Change language prefernce (Zulu, Afrikaans, English)
  - Logout option to securely exit the app

## Architecture
- **REST API**: The backend of the app is built using Node.js and Express.
- **Database**: Firestore is used as the database, allowing for real-time data synchronization and storage of user data, recipes, and preferences.
- **Real-Time Notifications**: Firebase Cloud messaging for push-notifications.
- **Authentication**: Firebase authentication for email and password, and google sso.
- **Hosting**: The Rest API is hosted on Vercel.

## API 
The API is now running smothly and with no problems This is the api repo:
https://github.com/JunivevaAlcandra/DishDelightAPI.git and this is apidomain: https://dish-delight-api-v1.vercel.app

## Testing credentials
**Email and Password**
- Email: test5@example.com
- Password: test12345</br>
**Google SSO**
- Email: testdishdelight@gmail.com 
- Password: testdishdelight2024

## Video demostration of the app

