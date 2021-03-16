# Image processing SDK

Simple SDK that allows to recognise text and faces on pictures.

## Usage

```
val sdk = SdkBuilder()
    .context(context)
    .awsCredentials(awsApiKey, awsSecretKey)
    .build()

val result = sdk.retrieveText(photoUri)
```

Please note that all SDK functions are executed on the caller thread. Don't use main thread for this.

## Build sample project

Before building project please set your AWS account credentials in your `gradle.properties` file:

```
awsApiKey="AKIASB6L3YQ263FRGA2T"
awsSecretKey="hSx05RgbKzPt/7qsoTjtXppJ/7VbEkGrEi3OgkIQ"
```

As `gradle.properties` is not included in this project please add `android.useAndroidX=true` to your file to support `androidX` dependencies.

No other additional steps are required to build the project. Just open it in your favourite IDE and run.

## Links

1. [Amazon Rekognition Overview](https://aws.amazon.com/rekognition/)
1. [Amazon Rekognition SDK](https://github.com/aws-amplify/aws-sdk-android/)
1. [Setting up AWS account and create an IAM user guide](https://docs.aws.amazon.com/rekognition/latest/dg/setting-up.html)
1. [ML Kit Text Recognition SDK](https://developers.google.com/ml-kit/vision/text-recognition)
