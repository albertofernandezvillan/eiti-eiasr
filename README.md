# eiti-eiasr (under construction)

## Goal
Main goal of project is enabling tranffic signs recognition utilizing own implementation of SVM (Support Vector Machine) algorithm running on HOG features (Histograms of Oriented Gradients). Approach is to be implemented in OpenCV libraries for Java. Ready artefact (like opencv HOGDescrptor) or pretrained models are not to be used.

## Resources

### OpenCV for Java

Building OpenCV artefacts for Java is done according to [tutorial](http://opencv-java-tutorials.readthedocs.io/en/latest/01-installing-opencv-for-java.html#install-opencv-3-x-under-linux)

Unfortutely documentation and community around OpenCV in Java is not as widely available as in case of C++ or Python. Lot of details have to be figured about combining information from OpenCV javadoc and C++ documentation.

### Source of images
In project images of traffic sign are used. There are sets of images available online in framework of INI Benchmark
project. Images are splitted into two datasets data [GTSRB](http://benchmark.ini.rub.de/?section=gtsrb&subsection=news) (for classification) and [GTSDB](http://benchmark.ini.rub.de/?section=gtsdb&subsection=news) (for localization).

### References
The best reference for undersanding Histogram of Oriented Gradients is original [paper by Dalal and Triggs](https://lear.inrialpes.fr/people/triggs/pubs/Dalal-cvpr05.pdf). It gives holistic view on HOG ad explains why and how.

Second, easier resource is [article by Satya Mallick](https://www.learnopencv.com/histogram-of-oriented-gradients/) - not everything is said straightfully, but author gives good tips about the way to vizualize HOGs (this has been implemented)

## HOG descriptor
- visualisic images
