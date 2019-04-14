# paintShopApp

## How to build and install app
Installs the Debug build

    ./gradlew installDebug

## How to test
Connect Android device or launch emulator

Install and run instrumentation tests on connected devices

    ./gradlew connectedAndroidTest

Run unit tests

    ./gradlew test
    
## App description
Application has 4 screens:
1. Colors. User can pick colors and switch to customers screen
2. Customers. User can create customer, switch to customer wishlist screen and switch to colors calculation screen
3. Customer details. User can specify colors in customer wishlist.
4. Colors calculation result. Results of picking colors for customers, using paintShopLib library
