# Asset Ticker App 📈

## Overview
This Java application provides a simple asset ticker that fetches real-time data for specified cryptocurrencies from the Coingecko API. The asset information is displayed in a graphical user interface (GUI) and published to an MQTT topic. Additionally, rate limiting handling is implemented to ensure smooth API requests.

## Features 🎯
- Fetches real-time asset data from Coingecko API.
- Displays asset information in a GUI with periodic updates.
- Publishes asset data to an MQTT topic using the Eclipse Paho MQTT library.
- Implements rate limiting handling for API requests.

## Technologies Used 🚀
- Swing: Java's GUI toolkit used to create the graphical user interface.
- Gson: A Java library for JSON parsing, utilized to handle Coingecko API responses.
- Eclipse Paho MQTT: A library for implementing MQTT communication, used for publishing asset data to an MQTT topic.
