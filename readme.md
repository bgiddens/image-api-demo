# Image API Demo

This repository includes a simple demo API with a single 
controller for managing image data, including tags determined
by a third-party computer vision API.

## Setup

An Imagga API key is needed to run the image processing 
component of this API. A free key can be obtained by
creating an [Imagga](https://imagga.com/) account.

You can set the key by creating the file `src/main/resources/application-default.properties` 
and adding the following value: 
in 

```heb.imagga-api-key=<myKey>```

The application will run at `http://localhost:{{port}}` where the port
can be specified in the same properties file. For example:

```server.port=8000```

Start the application with `./gradlew bootRun`

## Warnings

This API is intended for demonstrative local use only. 
The API employs no authentication or authorization.

For ease of use, the API initializes an in-memory database
on startup and performs operations against it. This saves users
the step of starting a separate database; however, all 
data is reset any time the service restarts.
