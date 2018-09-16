# Android Application for Activity prediction

Developed an application which recognizes human activities using machine learning. To recognize the activities, gathering actual human actions during daily life is the first step. We will solve this gathering issue using the smartphone. Nowadays, most people have their smartphone and the smartphones have many sensors such as accelerometer, gyroscope, orientation, GPS, proximity, etc. By using these sensors, we can obtain the datasets and classify the dataset using support vector machine (SVM).

# Steps taken to complete the asssignment

1. Generated the database file to classify three different activities: walking, running, and jumping by collecting data using accelerometer sensors.
2. Based on the database which was generated before, your application should classify the activities using Support Vector Machine and K-fold cross validation.
3. Drawn all X, Y, Z sensor data as dots in 3D plane and display each single activity as a line. The total number of lines is 60. Each line has 50 dots. Also, the graph view can be rotated and scaled (Zoom in and out) to help the user understand the decision boundary of SVM and created three checker boxes corresponding to each activity. As checking and unchecking each box, each activity graph should be displayed and removed in the axis.
4. Drawn two graphs whose X axis is the execution time and Y axis is the power consumption in the same android activity.
